package main;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import dao.ISisdb;
import dao.ISisdbImpl;
import entity.*;
import exception.*;
import util.DBConnUtil;

public class Main {
    public static void main(String[] args) {
        try {
            // ------------------ Database Connection Test ------------------
            try {
                DBConnUtil.getConnection("Test DB Connection");
            } catch (SQLException e) {
                System.err.println("Connection test failed: " + e.getMessage());
            }

            SIS sis = new SIS();

            // ------------------ Sample Data ------------------
            Teacher teacher1 = new Teacher(201, "Dr.", "Smith", "drsmith@school.com");
            Course course1 = new Course("101", "Java Programming", "CS101", 4, teacher1);
            Course course2 = new Course("102", "Database Systems", "CS102", 3, teacher1);

            Student student1 = new Student(1, "Annie", "January", new Date(), "anniejan@gmail.com", "9876543210");
            Student student2 = new Student(2, "Billy", "Butcher", new Date(), "billy_butcher@gmail.com", "1234567890");

            sis.enrollStudentInCourse("E101", student1, course1);
            sis.enrollStudentInCourse("E102", student2, course2);

            sis.assignTeacherToCourse(teacher1, course1);

            sis.recordPayment(student1, 500.0, new Date());
            sis.recordPayment(student2, 600.0, new Date());

            sis.generateEnrollmentReport(course1);
            sis.generatePaymentReport(student1);
            sis.calculateCourseStatistics(course1);

        } catch (InvalidTeacherDataException | InvalidCourseDataException | InvalidStudentDataException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ---------------------- Task 8: Student Enrollment ----------------------
        System.out.println("\n--- Task 8: Enrolling Nisha Deepthi ---");

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dob = sdf.parse("1995-08-15");
            Student nisha = new Student(411, "Nisha", "Deepthi", dob, "nisha_deepthi@gmail.com", "8954757489");

            ISisdb sisdb = new ISisdbImpl();
            sisdb.addStudent(nisha);

            Student fromDb = sisdb.getStudentByEmail("nisha_deepthi@gmail.com");
            int id = fromDb.getStudentId();

            Course intro = sisdb.getCourseByCode("Introduction to Programming");
            if (intro != null) sisdb.enrollStudentInCourse(id, intro.getCourseId());

            Course math = sisdb.getCourseByCode("Mathematics 101");
            if (math != null) sisdb.enrollStudentInCourse(id, math.getCourseId());

            System.out.println(" Nisha has been enrolled in both courses successfully.");

        } catch (ParseException e) {
            System.err.println("Date parsing failed: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Enrollment failed: " + e.getMessage());
        }

        
        
        // ---------------------- Task 9: Teacher Assignment ----------------------
        System.out.println("\n--- Task 9: Assigning Sarah Smith ---");

        try {
            ISisdb sis = new ISisdbImpl();

            Teacher sarah;
            try {
                sarah = sis.getTeacherByEmail("sarah.smith@example.com");
            } catch (Exception e) {
                sarah = new Teacher(11, "Sarah", "Smith", "sarah.smith@example.com");
                sis.addTeacher(sarah);
            }

            Course advDb = sis.getCourseByCode("CS302");
            if (advDb != null) {
                sis.assignCourseToTeacher(sarah.getTeacherId(), advDb.getCourseId());
                System.out.println(" Sarah Smith assigned to Advanced Database Management.");
            } else {
                System.out.println(" Course CS302 not found.");
            }

        } catch (Exception e) {
            System.err.println("Error during assignment: " + e.getMessage());
        }

        
        
        // ---------------------- Task 10: Payment Record ----------------------
        System.out.println("\n--- Task 10: Recording Payment for Jane Johnson ---");

        try {
            ISisdb sis = new ISisdbImpl();
            int janeId = 101;
            Student jane = sis.getStudentById(janeId);

            if (jane != null) {
                int paymentId = 1001;
                double amount = 500.00;
                Date paymentDate = new SimpleDateFormat("yyyy-MM-dd").parse("2023-04-10");

                Payment payment = new Payment(paymentId, jane, amount, paymentDate);
                sis.addPayment(payment);

                System.out.println(" Payment recorded for Jane Johnson.");

                List<Payment> payments = sis.getPaymentsByStudentId(janeId);
                System.out.println("Updated Payments:");
                for (Payment p : payments) p.displayPaymentInfo();

            } else {
                System.out.println(" Jane Johnson not found.");
            }

        } catch (StudentNotFoundException | PaymentValidationException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
        
        

        // ---------------------- Task 11: Enrollment Report ----------------------
        System.out.println("\n--- Task 11: Enrollment Report for 'Computer Science 101' ---");

        try {
            ISisdb sis = new ISisdbImpl();
            String courseName = "Computer Science 101";

            List<Enrollment> enrollments = sis.getEnrollmentsByCourseName(courseName);

            System.out.println("\n========= ENROLLMENT REPORT =========");
            System.out.println("Course: " + courseName);
            for (Enrollment e : enrollments) {
                e.displayEnrollmentInfo();
            }
            System.out.println("-------------------------------------");
            System.out.println("Total Enrolled: " + enrollments.size());
            System.out.println("=====================================");

        } catch (Exception e) {
            System.out.println("Report generation error: " + e.getMessage());
        }
        
        
        
        System.out.println("\n---  Adding Cloud Computing and Assigning Sarah ---");
        try {
            ISisdb sis = new ISisdbImpl();

            // 1. Create new Course object
            Course cloudCourse = new Course(null, "Cloud Computing", "CLOUD101", 3);

            // 2. Add course to the database
            sis.addCourse(cloudCourse);

            // 3. Fetch the teacher by email
            Teacher sarah = sis.getTeacherByEmail("sarah_smith@gmail.com");

            if (sarah != null) {
                // 4. Set instructor in Course object
                cloudCourse.setInstructor(sarah);

                // 5. Assign the teacher to course in DB
                sis.assignCourseToTeacher(sarah.getTeacherId(), cloudCourse.getCourseId());

                System.out.println("Sarah Smith assigned to Cloud Computing.");
            } else {
                System.out.println("Error: Sarah Smith not found in the database.");
            }

        } catch (Exception e) {
            System.out.println("Assignment error: " + e.getMessage());
        }
    }
}
