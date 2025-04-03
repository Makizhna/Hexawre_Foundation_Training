package src.entity;

import java.util.*;
import src.exception.*;

// Student class to store student details and related operations
public class Student {
    private int studentId;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String email;
    private String phoneNumber;

    private List<Course> enrolledCourses; // List of courses the student is enrolled in
    private List<Payment> paymentHistory; // List of payments made by the student
    private List<Enrollment> enrollments; // Task - 5 Tracks Enrollment objects

    // Constructor to initialize student attributes
    public Student(int studentId, String firstName, String lastName, Date dateOfBirth, String email, String phoneNumber)
            throws InvalidStudentDataException {

        // exception for invalid mail format
        if (email == null || !email.contains("@")) {
            throw new InvalidStudentDataException("Invalid email format");
        }

        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phoneNumber = phoneNumber;

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
        Enrollment enrollment = new Enrollment(UUID.randomUUID().toString(), this, course, enrollmentDate); // use UUID
                                                                                                            // to
                                                                                                            // generate
                                                                                                            // unique ID
        enrollments.add(enrollment);
        course.addEnrollment(enrollment);
        System.out.println(firstName + " has enrolled in " + course.getCourseName());

    }

    // Method to update student information
    public void updateStudentInfo(String firstName, String lastName, Date dateOfBirth, String email, String phoneNumber)
            throws InvalidStudentDataException {

        // Validate email format
        if (email == null || !email.contains("@")) {
            throw new InvalidStudentDataException("Invalid email format");
        }

        // Update student details
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phoneNumber = phoneNumber;
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
        System.out.println("Payment of $" + amount + " recorded for " + firstName);
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
        System.out.println("Student ID: " + studentId + ", Name: " + firstName + " " + lastName);
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
        return firstName;
    }

    // Getter method to retrieve last name
    public String getLastName() {
        return lastName;
    }

    // Method to get courses (fix for missing getCourses method)
    public List<Course> getCourses() {
        return enrolledCourses;
    }

    // Getter method to retrieve student ID
    public int getStudentId() {
        return studentId;
    }

    // Getter method to retrieve date of birth
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    // Getter method to retrieve email
    public String getEmail() {
        return email;
    }

    // Getter method to retrieve phone number
    public String getPhoneNumber() {
        return phoneNumber;
    }

}
