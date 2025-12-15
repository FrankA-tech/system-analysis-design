#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>
#include <string>
#include <algorithm>
using namespace std;

// Course structure
struct Course {
    string courseNumber;
    string courseName;
    vector<string> prerequisites;
};

// Node structure for BST
struct Node {
    Course course;
    Node* left;
    Node* right;

    Node(Course c) {
        course = c;
        left = nullptr;
        right = nullptr;
    }
};

// Binary Search Tree class
class BinarySearchTree {
private:
    Node* root;

    void inOrder(Node* node) {
        if (node != nullptr) {
            inOrder(node->left);
            cout << node->course.courseNumber << ", " << node->course.courseName << endl;
            inOrder(node->right);
        }
    }

    Node* insert(Node* node, Course course) {
        if (node == nullptr) {
            return new Node(course);
        }
        if (course.courseNumber < node->course.courseNumber) {
            node->left = insert(node->left, course);
        } else {
            node->right = insert(node->right, course);
        }
        return node;
    }

    Course* search(Node* node, string courseNumber) {
        if (node == nullptr) return nullptr;
        if (node->course.courseNumber == courseNumber) return &node->course;
        if (courseNumber < node->course.courseNumber) {
            return search(node->left, courseNumber);
        } else {
            return search(node->right, courseNumber);
        }
    }

    void destroyTree(Node* node) {
        if (node != nullptr) {
            destroyTree(node->left);
            destroyTree(node->right);
            delete node;
        }
    }

public:
    BinarySearchTree() {
        root = nullptr;
    }

    ~BinarySearchTree() {
        destroyTree(root);
    }

    void Insert(Course course) {
        root = insert(root, course);
    }

    Course* Search(string courseNumber) {
        return search(root, courseNumber);
    }

    void PrintInOrder() {
        inOrder(root);
    }
};

// Helper to trim whitespace
string trim(const string& str) {
    size_t first = str.find_first_not_of(" \t\r\n");
    size_t last = str.find_last_not_of(" \t\r\n");
    return (first == string::npos || last == string::npos) ? "" : str.substr(first, last - first + 1);
}

// Load file and insert courses into BST
void loadCourses(string filename, BinarySearchTree& bst) {
    ifstream file(filename);
    if (!file.is_open()) {
        cout << "Error opening file: " << filename << endl;
        return;
    }

    string line;
    while (getline(file, line)) {
        stringstream ss(line);
        string token;
        Course course;

        // Get course number
        getline(ss, token, ',');
        course.courseNumber = trim(token);

        // Get course name
        getline(ss, token, ',');
        course.courseName = trim(token);

        // Get prerequisites
        while (getline(ss, token, ',')) {
            string prereq = trim(token);
            if (!prereq.empty()) {
                course.prerequisites.push_back(prereq);
            }
        }

        bst.Insert(course);
    }

    file.close();
    cout << "Courses loaded successfully.\n";
}

// Print course info and its prerequisites
void printCourseInfo(BinarySearchTree& bst, string courseNumber) {
    transform(courseNumber.begin(), courseNumber.end(), courseNumber.begin(), ::toupper);
    Course* course = bst.Search(courseNumber);
    if (course == nullptr) {
        cout << "Course not found.\n";
        return;
    }

    cout << course->courseNumber << ", " << course->courseName << endl;
    if (!course->prerequisites.empty()) {
        cout << "Prerequisites: ";
        for (size_t i = 0; i < course->prerequisites.size(); ++i) {
            cout << course->prerequisites[i];
            if (i < course->prerequisites.size() - 1) cout << ", ";
        }
        cout << endl;
    } else {
        cout << "Prerequisites: None\n";
    }
}

// Main menu
int main() {
    BinarySearchTree bst;
    string filename;
    int choice;

    cout << "Welcome to the course planner.\n";

    do {
        cout << "\n1. Load Data Structure.\n";
        cout << "2. Print Course List.\n";
        cout << "3. Print Course.\n";
        cout << "9. Exit\n";
        cout << "What would you like to do? ";
        cin >> choice;
        cin.ignore();

        switch (choice) {
            case 1:
                cout << "Enter file name: ";
                getline(cin, filename);
                loadCourses(filename, bst);
                break;
            case 2:
                cout << "Here is a sample schedule:\n";
                bst.PrintInOrder();
                break;
            case 3:
                cout << "What course do you want to know about? ";
                getline(cin, filename);
                printCourseInfo(bst, filename);
                break;
            case 9:
                cout << "Thank you for using the course planner!\n";
                break;
            default:
                cout << choice << " is not a valid option.\n";
        }
    } while (choice != 9);

    return 0;
}
