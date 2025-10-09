import os
import requests
from dotenv import load_dotenv
from flask import Flask, jsonify
from flask_sqlalchemy import SQLAlchemy

# Load environment variables from .env file (if available)
load_dotenv()

# --- Configuration ---
# Get the database URL from environment or fallback to SQLite
DATABASE_URL = os.getenv("DATABASE_URL", "sqlite:///cve.db")

# Initialize Flask app
app = Flask(__name__)
app.config['SQLALCHEMY_DATABASE_URI'] = DATABASE_URL
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False

# Initialize SQLAlchemy
db = SQLAlchemy(app)

# --- Model ---
# Define CVE model
class CVE(db.Model):
    """
    Database model to store retrieved CVE data.
    """
    id = db.Column(db.String(30), primary_key=True) # e.g., 'CVE-2021-1234'
    # Using 'status' to store the CVE data assigner for metadata
    status = db.Column(db.String(50))
    description = db.Column(db.Text)

    def to_dict(self):
        return {
            "cve_id": self.id,
            "status": self.status,
            "description": self.description
        }

# --- Utility Functions ---

def safe_extract_description(data):
    """Safely extracts the description text from the NVD API JSON response."""
    try:
        # Navigate through the nested JSON structure common to NVD 1.0
        cve_items = data.get('result', {}).get('CVE_Items', [])
        if cve_items:
            # Get the first item's description data
            description_data = cve_items[0].get('cve', {}).get('description', {}).get('description_data', [])
            if description_data:
                # Return the value of the first description entry
                return description_data[0].get('value', 'Description not available')
        return 'Description not available'
    except Exception:
        return 'Description not available (Error during parsing)'

def safe_extract_assigner(data):
    """Safely extracts the CVE assigner from the NVD API JSON response."""
    try:
        # Navigate to the CVE data meta which contains the ASSIGNER
        cve_data_meta = data.get('result', {}).get('CVE_Items', [{}])[0].get('cve', {}).get('CVE_data_meta', {})
        return cve_data_meta.get('ASSIGNER', 'N/A')
    except Exception:
        return 'N/A'


# --- Routes ---

# Health check route
@app.route('/')
def health_check():
    """Confirms the application is running."""
    return jsonify({"status": "TechApp is running"})

# Route to list all stored CVE data
@app.route('/cves')
def list_cves():
    """Lists all CVEs currently stored in the database."""
    try:
        all_cves = CVE.query.all()
        results = []
        for cve in all_cves:
            results.append({
                "id": cve.id,
                "status": cve.status,
                # Truncate description for list view
                "description_preview": cve.description[:100] + "..." if len(cve.description) > 100 else cve.description
            })
        return jsonify(results)
    except Exception as e:
        print(f"Database query error: {e}")
        return jsonify({"error": "Failed to retrieve CVE list from database"}), 500


# Route to fetch CVE data by ID (DB check first, then NVD API)
@app.route('/cve/<cve_id>')
def get_cve(cve_id):
    """
    Fetches CVE data. Tries database first, then NVD API.
    If fetched from API, it saves the result to the database.
    """
    # 1. Try to get from local database
    cve_entry = CVE.query.get(cve_id.upper()) # Ensure ID is uppercase for consistency
    if cve_entry:
        print(f"CVE {cve_id} found in database.")
        result = cve_entry.to_dict()
        result['source'] = 'database'
        return jsonify(result)

    # 2. If not in DB, fetch from NVD API
    url = f'https://services.nvd.nist.gov/rest/json/cve/1.0/{cve_id.upper()}'
    print(f"CVE {cve_id} not in database. Fetching from NVD: {url}")

    try:
        # Set a timeout for the external request
        response = requests.get(url, timeout=15)
        response.raise_for_status() # Raise HTTPError for 4xx/5xx responses

        data = response.json()
        
        description = safe_extract_description(data)
        status = safe_extract_assigner(data)
        
        # 3. Save to DB
        new_cve = CVE(id=cve_id.upper(), status=status, description=description)
        db.session.add(new_cve)
        db.session.commit()

        print(f"CVE {cve_id} successfully fetched and saved.")
        
        # Return the newly fetched data
        return jsonify({
            "source": "nvd_api_and_saved",
            "cve_id": cve_id.upper(),
            "status": status,
            "description": description
        })

    except requests.exceptions.HTTPError as e:
        if e.response.status_code == 404:
            return jsonify({"error": f"CVE {cve_id} not found in NVD or local DB."}), 404
        print(f"HTTP Error while fetching NVD data: {e}")
        return jsonify({"error": f"Error fetching data from NVD: {e.response.status_code}"}), 503
    except requests.exceptions.RequestException as e:
        print(f"Request Error (e.g., Timeout/Connection) while fetching NVD data: {e}")
        return jsonify({"error": "Failed to connect to NVD API"}), 503
    except Exception as e:
        db.session.rollback() # Rollback transaction if DB write failed
        print(f"General processing error: {e}")
        return jsonify({"error": "An unexpected error occurred during processing"}), 500


# Run the app
if __name__ == '__main__':
    # Fix for deprecated @app.before_first_request: use application context
    # This creates the database file and tables on startup if they don't exist.
    with app.app_context():
        db.create_all()
        print("Database tables created/checked.")
        
    app.run(debug=True) 

