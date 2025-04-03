package src.entity;
import src.exception.*;
import java.util.ArrayList;
import java.util.List;


//Represents a course in the Student Information System (SIS)
public class Course {
    private String courseId;
    private String courseName;
    private String courseCode;
    private Teacher instructor;

    // Task 5: Maintain both enrollments & students separately
    //private List<Student> enrolledStudents;      
    private List<Enrollment> enrollments;           //Task 5

    // Constructor
    public Course(String courseId, String courseName, String courseCode, Teacher instructor) throws InvalidCourseDataException {
        
        // Validate course name before initializing
        if (courseName == null || courseName.isEmpty()) {
            throw new InvalidCourseDataException("Course name cannot be empty");
        }
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.instructor = instructor;
        //this.enrolledStudents = new ArrayList<>();
        this.enrollments = new ArrayList<>();           // Task 5: Initialize enrollments list
    }

    
    //Methods
    //Task 6: Assigns an instructor to the course.
    public void setInstructor(Teacher instructor) throws InvalidTeacherDataException {

        if (instructor == null) {
            throw new InvalidTeacherDataException("Invalid teacher data");
        }
        this.instructor = instructor;
        System.out.println("Teacher " + instructor.getFirstName() + " assigned to " + courseName);
    }



    // Task 6: Add an enrollment to the course
    public void addEnrollment(Enrollment enrollment) {
        if (enrollment != null && !enrollments.contains(enrollment)) {
            enrollments.add(enrollment);
            //enrolledStudents.add(enrollment.getStudent());  // Track student separately
        }
    }


    
    // Update Course Info
    public void updateCourseInfo(String courseCode, String courseName, Teacher instructor) throws InvalidCourseDataException {

        if (courseCode == null || courseCode.isEmpty()) {
            throw new InvalidCourseDataException("Course code cannot be empty.");
        }
        if (courseName == null || courseName.isEmpty()) {
            throw new InvalidCourseDataException("Course name cannot be empty.");
        }
        if (instructor == null) {
            throw new InvalidCourseDataException("Course must have a valid instructor.");
        }

        this.courseCode = courseCode;
        this.courseName = courseName;
        this.instructor = instructor;
        System.out.println("Course details updated.");
    }



    // Display Course Info
    public void displayCourseInfo() {
        String instructorName = (instructor != null) ? instructor.getFirstName() : "No instructor assigned";
        System.out.println("Course ID: " + courseId + ", Name: " + courseName + ", Instructor: " + instructorName);
    }


    // Method to calculate total payments received for this course
    public double getTotalPayments() {
        return enrollments.stream()
            .flatMap(enrollment -> {
                Student student = enrollment.getStudent();
                System.out.println("Checking Payments for: " + student.getFirstName());
                System.out.println("Payments: " + student.getPaymentHistory());
                return student.getPaymentHistory().stream();
            })
            .mapToDouble(Payment::getPaymentAmount)
            .sum();
    }
    
    
   

    // Task 5: Retrieve students dynamically from enrollments
    public List<Student> getEnrolledStudents() {
        List<Student> students = new ArrayList<>();
        for (Enrollment enrollment : enrollments) {
            students.add(enrollment.getStudent());
        }
        return students;
    }



    public List<Enrollment> getEnrollments() {
        return new ArrayList<>(enrollments);
    }



    // Returns the instructor of the course
    public Teacher getInstructor() {
        return instructor;
    }
    

    // Returns the course name
    public String getCourseName() {
        return courseName;
    }

    //Returns the course code
    public String getCourseCode() {
        return courseCode;
    }

    // Returns the course Id
    public String getCourseId() {
        return courseId;
    }
    
}

