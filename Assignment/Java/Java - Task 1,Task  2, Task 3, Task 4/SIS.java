import java.util.*;

public class SIS {
    private List<Student> students;             // List to store students
    private List<Course> courses;               // List to store courses
    private List<Enrollment> enrollments;       // List to store student enrollments
    private List<Teacher> teachers;             //List to store teachers
    private List<Payment> payments;             // List to store payment records

    // Constructor to initialize lists
    public SIS() {
        this.students = new ArrayList<>();
        this.courses = new ArrayList<>();
        this.enrollments = new ArrayList<>();
        this.teachers = new ArrayList<>();
        this.payments = new ArrayList<>();
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
        return null;     // Returns null if student is not found
    }

    //Check if a course exists
    public boolean isCourseAvailable(String courseId) {
        for (Course course : courses) {
            if (course.getCourseId().equals(courseId)) {
                return true;
            }
        }
        return false;
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

    // Assigns a teacher to a course
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

    // Records a payment made by a student
    public void recordPayment(Student student, double amount, Date paymentDate) throws PaymentValidationException {

         // Validates payment details
        if (student == null || amount <= 0) {
            throw new PaymentValidationException("Invalid payment details");
        }

        // Creates a new payment record and stores it
        Payment payment = new Payment(payments.size() + 1, student, amount, paymentDate);
        payments.add(payment);
        student.getPaymentHistory().add(payment);
        System.out.println("Payment recorded for " + student.getFirstName() + ": $" + amount);
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

        // Counts the total number of students enrolled in the course
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getCourse().equals(course)) {
                totalEnrollments++;
            }
        }

        // Calculates total payments made for the course
        for (Payment payment : payments) {
            if (payment.getStudent().getCourses().contains(course)) {
                totalPayments += payment.getPaymentAmount();

            }
        }

        // Displays course statistics
        System.out.println("Statistics for " + course.getCourseName());
        System.out.println("Total Enrollments: " + totalEnrollments);
        System.out.println("Total Payments: " + totalPayments);
    }
}
