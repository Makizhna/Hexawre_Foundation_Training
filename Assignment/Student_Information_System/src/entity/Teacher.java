package entity;

import exception.*;
import java.util.ArrayList;
import java.util.List;

//Task 1
//Represents a Teacher with personal details and assigned courses
public class Teacher {
    private int teacher_id;
    private String first_name;
    private String last_name;
    private String email;
    private List<Course> assignedCourses;         //Task - 5 List of courses assigned to the teacher


    //Task 2
    // Constructor to initialize teacher details
    public Teacher(int teacher_id, String first_name, String last_name, String email) throws InvalidTeacherDataException{
        
        //Ensures that the email format is valid before assigning values
        if (email == null || !email.contains("@")) {
            throw new InvalidTeacherDataException("Invalid email format");
        }

        this.teacher_id = teacher_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.assignedCourses = new ArrayList<>();       // Task - 5 Initialize assigned courses list
    }


    //Task 3 - Methods

    //Updates teacher information such as name and email.
    
    public void updateTeacherInfo(String first_name, String last_name, String email) throws InvalidTeacherDataException {

        //Ensures first name and last name are not empty before updating.

        if (first_name == null || first_name.isEmpty() || last_name == null || last_name.isEmpty()) {
            throw new InvalidTeacherDataException("First name and last name cannot be empty.");
        }
        
        this.first_name = first_name;
        this.last_name = last_name;
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
                System.out.println(first_name + " has been assigned to " + course.getCourseName());
            } catch (InvalidTeacherDataException e) {
                System.out.println("Error assigning instructor: " + e.getMessage());
            }             
            System.out.println(first_name + " has been assigned to " + course.getCourseName());
        } else {
            System.out.println(first_name + " is already assigned to " + course.getCourseName());
        }
    }


    //Displays basic teacher information such as ID and full name
    public void displayTeacherInfo() {
        System.out.println("Teacher ID: " + teacher_id + ", Name: " + first_name + " " + last_name);
    }

    // task - 5 returns the list of courses assigned to the teacher.
    public List<Course> getAssignedCourses() {
        return assignedCourses;
    }

    //returns First name of the teacher
    public String getFirstName() {
        return first_name;
    }

    public String getName() {
        return first_name + " " + last_name;
    }

    //return Teacher's ID
    public int getTeacherId() { 
        return teacher_id; 
    }

    //return lastname of the teacher
    public String getLastName() { 
        return last_name; 
    }

    //return Email address of the teacher
    public String getEmail() { 
        return email; 
    }
    
}


