package entity;

import java.util.*;
import exception.*;
import entity.Course;

// Student class to store student details and related operations
public class Student {
    private int student_id;
    private String first_name;
    private String last_name;
    private Date date_of_birth;
    private String email;
    private String phone_number;

    private List<Course> enrolledCourses;     // List of courses the student is enrolled in
    private List<Payment> paymentHistory;     // List of payments made by the student
    private List<Enrollment> enrollments;     // Task - 5 Tracks Enrollment objects

    
    // Constructor to initialize student attributes
    public Student(int student_id, String first_name, String last_name, Date date_of_birth, String email, String phone_number)
            throws InvalidStudentDataException {

        // exception for invalid mail format
        if (email == null || !email.contains("@")) {
            throw new InvalidStudentDataException("Invalid email format");
        }

        this.student_id = student_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.date_of_birth = date_of_birth;
        this.email = email;
        this.phone_number = phone_number;

        this.enrolledCourses = new ArrayList<>(); // Initialize enrollments list
        this.paymentHistory = new ArrayList<>(); // Initialize payments list
        this.enrollments = new ArrayList<>(); // Initialize enrollments (Task 5)

    }


    // Method to enroll student in a course
    public void enrollInCourse(Course course)
            throws DuplicateEnrollmentException, CourseNotFoundException, InvalidEnrollmentDataException {

        // Check if the course exists
        if (course == null) {
            throw new CourseNotFoundException("Course not found");
        }

        // Check if the student is already enrolled in the course using enrollments
        for (Enrollment e : enrollments) {
            if (e.getCourse().equals(course)) {
                throw new DuplicateEnrollmentException("Student already enrolled in " + course.getCourseName());
            }
        }
        
        // Add course to the enrolled list
        enrolledCourses.add(course);

        // Task 6: Create Enrollment object and store in enrollments list
        Date enrollmentDate = new Date(); // Get current date
        Enrollment enrollment = new Enrollment(UUID.randomUUID().toString(), this, course, enrollmentDate); //use UUID  to   generate   unique ID
        enrollments.add(enrollment);
        course.addEnrollment(enrollment);
        System.out.println(first_name + " has enrolled in " + course.getCourseName());
        
    }



    // Method to update student information
    public void updateStudentInfo(String first_name, String last_name, Date date_of_birth, String email, String phone_number)
            throws InvalidStudentDataException {

        // Validate email format
        if (email == null || !email.contains("@")) {
            throw new InvalidStudentDataException("Invalid email format");
        }

        // Update student details
        this.first_name = first_name;
        this.last_name = last_name;
        this.date_of_birth = date_of_birth;
        this.email = email;
        this.phone_number = phone_number;
        System.out.println("Student information updated successfully.");
    }

    // Method to make a payment
    public void makePayment(double amount, Date paymentDate)
            throws PaymentValidationException, InsufficientFundsException {

        // Validate payment amount
        if (amount <= 0) {
            throw new PaymentValidationException("Payment amount must be greater than zero");
        }

        // Create and record payment
        Payment payment = new Payment(paymentHistory.size() + 1, this, amount, paymentDate);
        paymentHistory.add(payment);
        System.out.println("Payment of $" + amount + " recorded for " + first_name);
    }


    // Task 6: Add an enrollment to the course
    public void addEnrollment(Enrollment enrollment) {
        if (enrollment != null && !enrollments.contains(enrollment)) {
            enrollments.add(enrollment);
            // enrolledStudents.add(enrollment.getStudent()); // Track student separately
        }
    }

    // Task 6: Retrieve all enrollments for a student
    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    // Task 6: Retrieve enrolled courses
    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    // Method to display student information
    public void displayStudentInfo() {
        System.out.println("Student ID: " + student_id + ", Name: " + first_name + " " + last_name);
    }


    public void addPayment(Payment payment) {
        paymentHistory.add(payment);
    }
    
    // Getter method to retrieve payment history
    public List<Payment> getPaymentHistory() {
        return paymentHistory;
    }

    // Getter method to retrieve first name
    public String getFirstName() {
        return first_name;
    }

    // Getter method to retrieve last name
    public String getLastName() {
        return last_name;
    }

    // Method to get courses (fix for missing getCourses method)
    public List<Course> getCourses() {
        return enrolledCourses;
    }

    // Getter method to retrieve student ID
    public int getStudentId() {
        return student_id;
    }

    // Getter method to retrieve date of birth
    public Date getDateOfBirth() {
        return date_of_birth;
    }

    // Getter method to retrieve email
    public String getEmail() {
        return email;
    }

    // Getter method to retrieve phone number
    public String getPhoneNumber() {
        return phone_number;
    }
    
    public Student(int studentId) {
        this.student_id = studentId;
    }


	public void setFirstName(String string) {
		
		this.first_name = first_name;
	}

    
}
