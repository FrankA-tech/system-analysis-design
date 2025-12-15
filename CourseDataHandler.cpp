#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>
#include <algorithm>
#include <cctype>

using namespace std;

// Struct to hold course information
struct Course {
    string courseNumber;
    string courseTitle;
    vector<string> prerequisites;
};

// Global vector to store all courses
vector<Course> courses;

// Helper function to convert string to uppercase
string ToUpper(string str) {
    transform(str.begin(), str.end(), str.begin(), ::toupper);
    return str;
}

// Load course data from file into vector
void LoadDataVector(const string& fileName) {
    ifstream file(fileName);
    if (!file.is_open()) {
        cout << "Error opening file." << endl;
        return;
    }

    string line;
    while (getline(file, line)) {
        stringstream ss(line);
        string token;
        vector<string> tokens;

        while (getline(ss, token, ',')) {
            // Trim whitespace
            token.erase(0, token.find_first_not_of(" \t\r\n"));
            token.erase(token.find_last_not_of(" \t\r\n") + 1);
            tokens.push_back(token);
        }

        if (tokens.size() >= 2) {
            Course course;
            course.courseNumber = ToUpper(tokens[0]);
            course.courseTitle = tokens[1];
            for (size_t i = 2; i < tokens.size(); ++i) {
                if (!tokens[i].empty()) {
                    course.prerequisites.push_back(ToUpper(tokens[i]));
                }
            }
            courses.push_back(course);
        } else {
            cout << "Formatting error in line: " << line << endl;
        }
    }

    file.close();
    cout << "Courses loaded successfully." << endl;
}

// Print sorted list of all courses
void PrintCourseListVector() {
    sort(courses.begin(), courses.end(), [](const Course& a, const Course& b) {
        return a.courseNumber < b.courseNumber;
    });

    cout << "\nHere is a sample schedule:" << endl;
    for (const auto& course : courses) {
        cout << course.courseNumber << ", " << course.courseTitle << endl;
    }
}

// Print course details and prerequisites
void PrintCourseInfoVector(const string& courseNumber) {
    string searchKey = ToUpper(courseNumber);
    for (const auto& course : courses) {
        if (course.courseNumber == searchKey) {
            cout << course.courseNumber << ", " << course.courseTitle << endl;
            cout << "Prerequisites: ";
            if (course.prerequisites.empty()) {
                cout << "None";
            } else {
                for (size_t i = 0; i < course.prerequisites.size(); ++i) {
                    cout << course.prerequisites[i];
                    if (i < course.prerequisites.size() - 1) cout << ", ";
                }
            }
            cout << endl;
            return;
        }
    }
    cout << "Course not found." << endl;
}

// Main menu loop
int main() {
    int choice;
    string fileName;

    cout << "Welcome to the course planner." << endl;

    while (true) {
        cout << "\n    1. Load Data Structure." << endl;
        cout << "    2. Print Course List." << endl;
        cout << "    3. Print Course." << endl;
        cout << "    9. Exit" << endl;
        cout << "\nWhat would you like to do? ";
        cin >> choice;

        switch (choice) {
            case 1:
                cout << "Enter file name: ";
                cin >> fileName;
                LoadDataVector(fileName);
                break;
            case 2:
                if (courses.empty()) {
                    cout << "Please load data first (Option 1)." << endl;
                } else {
                    PrintCourseListVector();
                }
                break;
            case 3: {
                if (courses.empty()) {
                    cout << "Please load data first (Option 1)." << endl;
                } else {
                    string courseNumber;
                    cout << "What course do you want to know about? ";
                    cin >> courseNumber;
                    PrintCourseInfoVector(courseNumber);
                }
                break;
            }
            case 9:
                cout << "Thank you for using the course planner!" << endl;
                return 0;
            default:
                cout << choice << " is not a valid option." << endl;
        }
    }
}
