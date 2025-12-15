define Course object:
    courseNumber : string
    courseName   : string
    prerequisites : list of strings
function loadFile(filename):
    open file
    for each line in file:
        parse line into tokens (courseNumber, courseName, prerequisites)
        if formatting error:
            print "Invalid line format"
        else:
            create Course object
            insert Course into data structure
    close file
function insertCourse(vector<Course> courses, Course course):
    append course to courses
 
function searchCourse(vector<Course> courses, string courseNumber):
    for each course in courses:
        if course.courseNumber == courseNumber:
            print course.courseNumber, course.courseName
            for each prereq in course.prerequisites:
                print prereq
 
function printAllCourses(vector<Course> courses):
    sort courses by courseNumber
    for each course in courses:
        print course.courseNumber, course.courseName
function insertCourse(HashTable<Course> table, Course course):
    table.put(course.courseNumber, course)
 
function searchCourse(HashTable<Course> table, string courseNumber):
    course = table.get(courseNumber)
    if course exists:
        print course.courseNumber, course.courseName
        for each prereq in course.prerequisites:
            print prereq
 
function printAllCourses(HashTable<Course> table):
    list = table.values()
    sort list by courseNumber
    for each course in list:
        print course.courseNumber, course.courseName
function insertCourse(Tree<Course> tree, Course course):
    tree.insert(course.courseNumber, course)
 
function searchCourse(Tree<Course> tree, string courseNumber):
    course = tree.search(courseNumber)
    if course exists:
        print course.courseNumber, course.courseName
        for each prereq in course.prerequisites:
            print prereq
 
function printAllCourses(Tree<Course> tree):
    tree.inOrderTraversal():
        print course.courseNumber, course.courseName
function menu():
    while true:
        print "1. Load File"
        print "2. Print All Courses"
        print "3. Print Course Info"
        print "9. Exit"
        choice = user input
 
        if choice == 1:
            filename = user input
            loadFile(filename)
        else if choice == 2:
            printAllCourses(dataStructure)
        else if choice == 3:
            courseNumber = user input
            searchCourse(dataStructure, courseNumber)
        else if choice == 9:
            exit program
        else:
            print "Invalid choice"
