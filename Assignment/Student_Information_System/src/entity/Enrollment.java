package entity;

import java.util.*;
import exception.*;



public class Enrollment {
    private String enrollmentId;
    private Student student;
    private Course course;
    private Date enrollmentDate;

    // Constructor
    public Enrollment(String enrollmentId, Student student, Course course, Date enrollmentDate) throws InvalidEnrollmentDataException {

        if (student == null || course == null) {
            throw new InvalidEnrollmentDataException("Invalid enrollment data: student or course missing");
        }

        if (enrollmentDate == null) {
            throw new InvalidEnrollmentDataException("Enrollment date cannot be null.");
        }

        this.enrollmentId = enrollmentId;
        this.student = student;
        this.course = course;
        this.enrollmentDate = enrollmentDate;

         // Task 6: Automatically link enrollment to student & course
         student.addEnrollment(this);
         course.addEnrollment(this);
    }

    //get student name
    public Student getStudent() {
        return student;
    }
    
    //method to get student_id
    public int getStudentId() {
        return student.getStudentId();
    }

    // get course name
    public Course getCourse() {
        return course;
    }
    
    // get ernrollment date
    public Date getEnrollmentDate() {
        return enrollmentDate;
    }

    @Override
    public String toString() {
        return "Enrollment ID: " + enrollmentId + ", Student: " + student.getFirstName() +
               ", Course: " + course.getCourseName() + ", Date: " + enrollmentDate;
    }

    // display enrollment information
    public void displayEnrollmentInfo() {
        System.out.println("Enrollment ID: " + enrollmentId + ", Student: " + student.getFirstName() + ", Course: " + course.getCourseName());
    }
}
