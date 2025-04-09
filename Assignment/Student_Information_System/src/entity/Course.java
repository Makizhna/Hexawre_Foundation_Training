package entity;
import exception.*;
import java.util.ArrayList;
import java.util.List;

// Represents a course in the Student Information System (SIS)
public class Course {
    private String courseId;
    private String courseName;
    
    private int credits;
    private Teacher instructor;
    private List<Enrollment> enrollments;
	private String courseCode;

    // Constructor
    public Course(String courseId, String courseName, String courseCode, int credits, Teacher instructor) throws InvalidCourseDataException {
        if (courseName == null || courseName.isEmpty()) {
            throw new InvalidCourseDataException("Course name cannot be empty");
        }
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.credits = credits;
        this.instructor = instructor;
        this.enrollments = new ArrayList<>();
    }

    
 // Constructor 2: Without teacher
    public Course(String courseId, String courseName, String courseCode, int credits) throws InvalidCourseDataException {
        this(courseId, courseName, courseCode, credits, null); // reuse the other constructor
    }
    
    // Assigns an instructor to the course
    public void setInstructor(Teacher instructor) throws InvalidTeacherDataException {
        if (instructor == null) {
            throw new InvalidTeacherDataException("Invalid teacher data");
        }
        this.instructor = instructor;
        System.out.println(" Teacher " + instructor.getFirstName() + " assigned to " + courseName);
    }

    // Add an enrollment to the course
    public void addEnrollment(Enrollment enrollment) {
        if (enrollment != null && !enrollments.contains(enrollment)) {
            enrollments.add(enrollment);
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
        System.out.println(" Course details updated.");
    }

    // Display Course Info
    public void displayCourseInfo() {
        String instructorName = (instructor != null) ? instructor.getFirstName() : "No instructor assigned";
        System.out.println(" Course ID: " + courseId + " | Name: " + courseName + " | Instructor: " + instructorName);
    }

    // Method to calculate total payments received for this course
    public double getTotalPayments() {
        return enrollments.stream()
            .flatMap(enrollment -> {
                Student student = enrollment.getStudent();
                if (student.getPaymentHistory() != null) {  
                    return student.getPaymentHistory().stream();
                }
                return new ArrayList<Payment>().stream();
            })
            .mapToDouble(Payment::getPaymentAmount)
            .sum();
    }

    public List<Student> getEnrolledStudents() {
        List<Student> students = new ArrayList<>();
        for (Enrollment enrollment : enrollments) {
            students.add(enrollment.getStudent());
        }
        return students;
    }

    public String getCourseId() { return courseId; }
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public String getCourseCode() { return courseCode; }
    public Teacher getInstructor() { return instructor; }

	public int getCredits() {
		return credits;
	}

	
	
}
