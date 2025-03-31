
import java.util.Date;

public class Main {
    public static void main(String[] args)
            throws InvalidTeacherDataException, InvalidCourseDataException, InvalidStudentDataException {

        // Initialize SIS system
        SIS sis = new SIS();

        // Create teacher
        Teacher teacher1 = new Teacher(201, "Dr.", "Smith", "drsmith@school.com");

        // Create courses
        Course course1 = new Course("101", "Java Programming", "CS101", teacher1);
        Course course2 = new Course("102", "Database Management", "DB102", teacher1);

        // Create students
        Student student1 = new Student(1, "Annie", "January", new Date(), "anniejan@gmail.com", "9876543210");
        Student student2 = new Student(2, "Billy", "Butcher", new Date(), "billy_butcher@gmail.com", "1234567890");

        try {

            // Enroll students in courses
            sis.enrollStudentInCourse("E101", student1, course1);
            sis.enrollStudentInCourse("E102", student2, course2);

            // Assign teacher to course
            sis.assignTeacherToCourse(teacher1, course1);

            // Record payments
            sis.recordPayment(student1, 500.0, new Date());
            sis.recordPayment(student2, 600.0, new Date());

            // Generate reports
            sis.generateEnrollmentReport(course1);
            sis.generatePaymentReport(student1);

            // Course statistics
            sis.calculateCourseStatistics(course1);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
