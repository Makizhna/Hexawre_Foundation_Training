package src.entity;

import java.util.*;
import src.entity.Enrollment;
import src.entity.Course;
import src.entity.Payment;
import src.entity.Student;
import src.entity.Teacher;
import src.exception.*;

public class SIS {
    private List<Student> students; // List to store students
    private List<Course> courses; // List to store courses
    private List<Enrollment> enrollments; // List to store student enrollments
    private List<Teacher> teachers; // List to store teachers
    private List<Payment> payments; // List to store payment records

    // Constructor to initialize lists
    public SIS() {
        this.students = new ArrayList<>();
        this.courses = new ArrayList<>();
        this.enrollments = new ArrayList<>();
        this.teachers = new ArrayList<>();
        this.payments = new ArrayList<>();
    }

    //Task 6: Method that adds an enrollment to both the Student's and Course's enrollment lists
    public void addEnrollment(Student student, Course course, Date enrollmentDate) throws DuplicateEnrollmentException {
        for (Enrollment e : enrollments) {
            if (e.getStudent().equals(student) && e.getCourse().equals(course)) {
                throw new DuplicateEnrollmentException("Student is already enrolled in this course.");
            }
        }

        try {
            Enrollment newEnrollment = new Enrollment("ENR" + enrollments.size(), student, course, enrollmentDate);
            enrollments.add(newEnrollment);
            student.addEnrollment(newEnrollment);
            course.addEnrollment(newEnrollment);
        } catch (InvalidEnrollmentDataException e) {
            System.out.println("Error in enrollment: " + e.getMessage());
        }

    }

    // Adds a student to the system
    public void addStudent(Student student) {
        if (student != null) {
            students.add(student);
            System.out.println(student.getFirstName() + " added to the system.");
        }
    }

    // Adds a course to the system
    public void addCourse(Course course) {
        if (course != null) {
            courses.add(course);
            System.out.println(course.getCourseName() + " added to the system.");
        }
    }

    // Adds a teacher to the system
    public void addTeacher(Teacher teacher) {
        if (teacher != null) {
            teachers.add(teacher);
            System.out.println(teacher.getName() + " added to the system.");
        }
    }

    // Getters for accessing student, course, and teacher lists
    public List<Student> getStudents() {
        return students;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    // Find student by ID
    public Student getStudentById(int studentId) {
        for (Student student : students) {
            if (student.getStudentId() == studentId) {
                return student;
            }
        }
        return null; // Returns null if student is not found
    }

    // Check if a course exists
    public boolean isCourseAvailable(String courseId) {
        for (Course course : courses) {
            if (course.getCourseId().equals(courseId)) {
                return true;
            }
        }
        return false;
    }


    //Task 6: Method to retrieve all enrollments for a specific student. 
    public List<Enrollment> getEnrollmentsForStudent(Student student) {
        return student.getEnrollments();
    }



    // Enrolls a student in a course
    public void enrollStudentInCourse(String enrollmentId, Student student, Course course)
            throws CustomExceptions, InvalidEnrollmentDataException {

        // Validates student and course before enrollment
        if (student == null || course == null) {
            throw new CustomExceptions("Student or Course cannot be null");
        }

        // Creates a new enrollment and adds it to the system
        Enrollment enrollment = new Enrollment(enrollmentId, student, course, new Date());
        enrollments.add(enrollment);
        System.out.println(student.getFirstName() + " has been enrolled in " + course.getCourseName());
    }

    

    //Task 6: Assigns a teacher to a course
    public void assignTeacherToCourse(Teacher teacher, Course course) throws InvalidTeacherDataException {

        // Validates teacher and course before assignment
        if (teacher == null || course == null) {
            System.out.println("Teacher or Course cannot be null.");
            return;
        }

        // Assigns the teacher to the course
        course.setInstructor(teacher);
        System.out.println(teacher.getName() + " has been assigned to teach " + course.getCourseName());
    }



    //Task 6:  Records a payment made by a student
    public void recordPayment(Student student, double amount, Date paymentDate) throws PaymentValidationException {
        if (student == null || amount <= 0) {
            throw new PaymentValidationException("Invalid payment details");
        }

        // Create payment and add it to student history
        Payment payment = new Payment(payments.size() + 1, student, amount, paymentDate);
        payments.add(payment);
        student.getPaymentHistory().add(payment);

        // Debugging: Print payment history to verify
        System.out.println("Payment recorded for " + student.getFirstName() + ": $" + amount);
        System.out
                .println("Current Payment History for " + student.getFirstName() + ": " + student.getPaymentHistory());
    }



    //Task 6: Method to retrieve all courses assigned to a specific teacher
    public List<Course> getCoursesForTeacher(Teacher teacher) {
        List<Course> assignedCourses = new ArrayList<>();
        for (Course course : courses) {
            if (course.getInstructor() != null && course.getInstructor().equals(teacher)) {
                assignedCourses.add(course);
            }
        }
        return assignedCourses;
    }
    


    // Generates an enrollment report for a specific course
    public void generateEnrollmentReport(Course course) {
        System.out.println("Enrollment Report for Course: " + course.getCourseName());

        // Iterates through enrollments to find students in the course
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getCourse().equals(course)) {
                System.out.println("Student: " + enrollment.getStudent().getFirstName() + " "
                        + enrollment.getStudent().getLastName());
            }
        }
    }

    // Generates a payment report for a specific student
    public void generatePaymentReport(Student student) {
        System.out.println("Payment Report for: " + student.getFirstName() + " " + student.getLastName());

        // Iterates through payments made by the student
        for (Payment payment : payments) {
            if (payment.getStudent().equals(student)) {
                System.out.println("Amount: $" + payment.getPaymentAmount() + " Date: " + payment.getPaymentDate());

            }
        }
    }

    // Calculates statistics for a specific course
    public void calculateCourseStatistics(Course course) {
        int totalEnrollments = 0;
        double totalPayments = 0;

        System.out.println("Calculating statistics for " + course.getCourseName());

        // Count total enrollments
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getCourse().equals(course)) {
                totalEnrollments++;
            }
        }

        // Get payments from enrolled students
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getCourse().equals(course)) {
                Student student = enrollment.getStudent();

                System.out.println("Checking payments for: " + student.getFirstName());

                // Fetch payments from student's payment history
                for (Payment payment : student.getPaymentHistory()) {
                    System.out.println("Payment found: $" + payment.getPaymentAmount());
                    totalPayments += payment.getPaymentAmount();
                }
            }
        }

        // Display course statistics
        System.out.println("Statistics for " + course.getCourseName());
        System.out.println("Total Enrollments: " + totalEnrollments);
        System.out.println("Total Payments: " + totalPayments);
    }
}