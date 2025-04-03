package src.entity;

import src.exception.*;
import java.util.ArrayList;
import java.util.List;

//Task 1
//Represents a Teacher with personal details and assigned courses
public class Teacher {
    private int teacherId;
    private String firstName;
    private String lastName;
    private String email;
    private List<Course> assignedCourses;         //Task - 5 List of courses assigned to the teacher


    //Task 2
    // Constructor to initialize teacher details
    public Teacher(int teacherId, String firstName, String lastName, String email) throws InvalidTeacherDataException{
        
        //Ensures that the email format is valid before assigning values.
        if (email == null || !email.contains("@")) {
            throw new InvalidTeacherDataException("Invalid email format");
        }

        this.teacherId = teacherId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.assignedCourses = new ArrayList<>();       // Task - 5 Initialize assigned courses list
    }


    //Task 3 - Methods

    //Updates teacher information such as name and email.
    
    public void updateTeacherInfo(String firstName, String lastName, String email) throws InvalidTeacherDataException {

        //Ensures first name and last name are not empty before updating.

        if (firstName == null || firstName.isEmpty() || lastName == null || lastName.isEmpty()) {
            throw new InvalidTeacherDataException("First name and last name cannot be empty.");
        }
        
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        System.out.println("Teacher information updated.");
    }


    // Task 6: Assign a course to the teacher
    public void assignCourse(Course course) throws InvalidCourseDataException {
        if (course == null) {
            throw new InvalidCourseDataException("Course cannot be null.");
        }

        // Prevent duplicate assignments
        if (!assignedCourses.contains(course)) {
            assignedCourses.add(course);
            try {
                course.setInstructor(this);  // Handle potential exception
                System.out.println(firstName + " has been assigned to " + course.getCourseName());
            } catch (InvalidTeacherDataException e) {
                System.out.println("Error assigning instructor: " + e.getMessage());
            }             
            System.out.println(firstName + " has been assigned to " + course.getCourseName());
        } else {
            System.out.println(firstName + " is already assigned to " + course.getCourseName());
        }
    }


    //Displays basic teacher information such as ID and full name
    public void displayTeacherInfo() {
        System.out.println("Teacher ID: " + teacherId + ", Name: " + firstName + " " + lastName);
    }

    // task - 5 returns the list of courses assigned to the teacher.
    public List<Course> getAssignedCourses() {
        return assignedCourses;
    }

    //returns First name of the teacher
    public String getFirstName() {
        return firstName;
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    //return Teacher's ID
    public int getTeacherId() { 
        return teacherId; 
    }

    //return lastname of the teacher
    public String getLastName() { 
        return lastName; 
    }

    //return Email address of the teacher
    public String getEmail() { 
        return email; 
    }
    
}


