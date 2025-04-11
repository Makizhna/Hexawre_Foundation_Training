package main;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import entity.Teacher;
import entity.Course;
import entity.Enrollment;
import entity.Payment;
import entity.Student;
import entity.SIS;
import exception.InvalidTeacherDataException;
import util.DBConnUtil;
import exception.InvalidCourseDataException;
import exception.InvalidStudentDataException;
import exception.StudentNotFoundException;
import exception.PaymentException;
import exception.PaymentValidationException;
import exception.InsufficientFundsException;

import java.util.Date;
import java.util.List;

import dao.ISisdb;
import dao.ISisdbImpl;

public class Main {
    public static void main(String[] args) {
        try {
        	
        	
        	 try {
                 // Test DB connection
                 DBConnUtil.getConnection("Test DB Connection");
             } catch (SQLException e) {
                 System.err.println("Connection test failed: " + e.getMessage());
             }

            // Initialize SIS system
            SIS sis = new SIS();

            // Create teacher
            Teacher teacher1 = new Teacher(201, "Dr.", "Smith", "drsmith@school.com");

            // Create courses
            Course course1 = new Course("101", "Java Programming", "CS101", 4, teacher1);
            Course course2 = new Course("102", "Database Systems", "CS102", 3, teacher1);


            // Create students
            Student student1 = new Student(1, "Annie", "January", new Date(), "anniejan@gmail.com", "9876543210");
            Student student2 = new Student(2, "Billy", "Butcher", new Date(), "billy_butcher@gmail.com", "1234567890");

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

        } catch (InvalidTeacherDataException | InvalidCourseDataException | InvalidStudentDataException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        
        // ---------------------- Task 8: Student Enrollment ----------------------
        System.out.println("\n---------------------- Task 8: Student Enrollment ----------------------");

        try {
            // 1. Create John Doe's student object
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dob = sdf.parse("1995-08-15");

            Student john = new Student(311, "John", "Doe", dob, "john.doe@example.com", "123-456-7890");

            // 2. Get instance of ISisdbImpl (not static)
            ISisdb sisdb = new ISisdbImpl();

            // 3. Add student to the database
            sisdb.addStudent(john);

            // 4. Retrieve the student ID using email                           
            Student johnFromDb = sisdb.getStudentByEmail("john.doe@example.com");
            int studentId = johnFromDb.getStudentId();

            // 5. Enroll John in "Introduction to Programming"
            Course introProgramming = sisdb.getCourseByCode("Introduction to Programming");
            if (introProgramming != null) {
                sisdb.enrollStudentInCourse(studentId, introProgramming.getCourseId());
            }

            // 6. Enroll John in "Mathematics 101"
            Course math101 = sisdb.getCourseByCode("Mathematics 101");
            if (math101 != null) {
                sisdb.enrollStudentInCourse(studentId, math101.getCourseId());
            }

            System.out.println("John Doe has been enrolled in both courses successfully.");

        } catch (ParseException e) {
            System.err.println("Error parsing date of birth: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error enrolling student: " + e.getMessage());
        }



        

     // ---------------------- Task 9: Teacher Assignment ----------------------
        System.out.println("\n--- Task 9: Assigning Teacher Sarah Smith ---");

        try {
            ISisdb sis = new ISisdbImpl();

            // Check if Sarah already exists
            Teacher sarah = null;
            try {
                sarah = sis.getTeacherByEmail("sarah.smith@example.com");
            } catch (Exception e) {
                // Teacher not found, create and insert
                sarah = new Teacher(11, "Sarah", "Smith", "sarah.smith@example.com");
                sis.addTeacher(sarah);
            }

            // Now assign her to the course
            Course advDb = sis.getCourseByCode("CS302");
            if (advDb != null) {
                sis.assignCourseToTeacher(sarah.getTeacherId(), advDb.getCourseId());
                System.out.println("Teacher Sarah Smith has been successfully assigned to Advanced Database Management.");
            } else {
                System.out.println("Course not found for assignment.");
            }
         } catch (Exception e) {
            System.err.println("Error during teacher assignment: " + e.getMessage());
        }

     


        
     
     // ---------------------- Task 10: Payment Record ----------------------
        System.out.println("\n--- Task 10: Recording Payment for Jane Johnson ---");

        try {
        	
        	ISisdb sis = new ISisdbImpl();
            // Step 1: Retrieve Jane Johnson's record using Student ID
            int janeId = 101;
            Student jane = sis.getStudentById(janeId);

            if (jane != null) {
                // Step 2: Create Payment instance
                int paymentId = 1001; // Assign a valid payment ID manually
                double amountPaid = 500.00;

                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
                java.util.Date paymentDate = sdf.parse("2023-04-10");

                // Create payment object using valid constructor
                Payment payment = new Payment(paymentId, jane, amountPaid, paymentDate);

                // Step 3: Record payment using DAO method
                sis.addPayment(payment);
                System.out.println("Payment recorded successfully for Jane Johnson.");

                // Step 4: Display updated payment details
                List<Payment> janePayments = sis.getPaymentsByStudentId(janeId);
                System.out.println("Updated Payments for Jane Johnson:");
                for (Payment p : janePayments) {
                    p.displayPaymentInfo();
                }
            } else {
                System.out.println("Student Jane Johnson not found.");
            }

        } catch (StudentNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (PaymentValidationException e) {
            System.out.println("Payment validation failed: " + e.getMessage());
        } catch (exception.SQLException | java.sql.SQLException e) {
            System.out.println("Database error occurred: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }

    
    
    
 // ===================== Task 11: Enrollment Report Generation =====================
    
        System.out.println("\n--- Task 11: Generating Enrollment Report for 'Computer Science 101' ---");
        try {
        	
        	ISisdb sis = new ISisdbImpl();
        // Define the course name for which report needs to be generated
        String courseName = "Computer Science 101";

        // Call the method to fetch enrollment details by course name
        List<Enrollment> enrollments = sis.getEnrollmentsByCourseName(courseName);

        // Print header for the report
        System.out.println("\n================ ENROLLMENT REPORT =================");
        System.out.println("Course: " + courseName);
        System.out.println("----------------------------------------------------");

        // Display each enrollment record
        for (Enrollment enrollment : enrollments) {
            enrollment.displayEnrollmentInfo();           // displays Enrollment ID, Student name, Course name
        }

        // Print total number of students enrolled
        System.out.println("----------------------------------------------------");
        System.out.println("Total Students Enrolled: " + enrollments.size());
        System.out.println("====================================================\n");

       } catch (Exception e) {
        // Catch and display any errors during report generation
        System.out.println("Error generating enrollment report: " + e.getMessage());
       }

        
        
        
        
     // ---------------------- Task 12: Assign Sarah to 'Cloud Computing' ----------------------
        System.out.println("\n--- Task 12: Adding 'Cloud Computing' Course and Assigning Sarah Smith ---");

        try {
        	
        	ISisdb sis = new ISisdbImpl();
            // Step 1: Create new course object with no assigned teacher
        	Course cloudCourse = new Course("111", "Cloud Computing", "C11", 4, null);
        	// or overload constructor without teacher if you have one

            sis.addCourse(cloudCourse); // Add course to DB

            // Step 2: Assign Sarah Smith (teacherId = 11) to this new course
            sis.assignCourseToTeacher(11, "111");

            System.out.println("Teacher Sarah Smith assigned to 'Cloud Computing' successfully.");

        } catch (Exception e) {
            System.out.println("Error assigning Sarah to Cloud Computing: " + e.getMessage());
        }



        
   }
}
               



