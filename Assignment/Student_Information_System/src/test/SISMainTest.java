package test;

import java.text.SimpleDateFormat;

import dao.ISisdbImpl;
import entity.Course;
import entity.Student;
import entity.Teacher;

import java.util.Date;
import java.util.List;

public class SISMainTest {
    public static void main(String[] args) {
        ISisdbImpl dao = new ISisdbImpl();

        try {
            System.out.println("===== STUDENT CRUD TESTS =====");

            // Add a student
            Student s = new Student(2001, "Test1", "User1", new Date(), "test@example.com", "9876543210");
            dao.addStudent(s);

            // Fetch and print student
            Student retrieved = dao.getStudentById(s.getStudentId());
            System.out.println("Student retrieved: " + retrieved.getFirstName());

            // List all students
            List<Student> students = dao.getAllStudents();
            System.out.println("All Students:");
            students.forEach(stu -> System.out.println("- " + stu.getFirstName()));

            // Update the student
            try {
                Student updatedStudent = new Student(s.getStudentId(), "TestUpdated", "UserUpdated", new Date(), "updated@example.com", "9999999999");
                dao.updateStudent(updatedStudent);
                System.out.println(" Student updated successfully!");
            } catch (Exception e) {
                System.err.println("Error updating student: " + e.getMessage());
            }

            // Fetch again to confirm update
            Student updated = dao.getStudentById(s.getStudentId());
            System.out.println("Updated Student Name: " + updated.getFirstName());

            // Delete the student
            try {
                dao.deleteStudent(s.getStudentId());
                System.out.println("Student deleted successfully!");
            } catch (Exception e) {
                System.err.println("Error deleting student: " + e.getMessage());
            }

            System.out.println("\n===== TEACHER & COURSE CRUD TESTS =====");

            // Add a teacher
            Teacher t = new Teacher(301, "Alan", "Turing", "turing@cs.com");
            dao.addTeacher(t);

            // Add a course with that teacher
            Course c = new Course("CSE201", "Machine Learning", "ML101", 3, t);
            dao.addCourse(c);

            System.out.println("Teacher and Course added successfully!");

            // List all teachers
            List<Teacher> teachers = dao.getAllTeachers();
            System.out.println("All Teachers:");
            teachers.forEach(teach -> System.out.println("- " + teach.getFirstName()));

            // List all courses
            List<Course> courses = dao.getAllCourses();
            System.out.println("All Courses:");
            courses.forEach(course -> System.out.println("- " + course.getCourseName()));

            // Update teacher
            try {
                Teacher updatedTeacher = new Teacher(301, "AlanUpdated", "Turing", "alanupdated@cs.com");
                dao.updateTeacher(updatedTeacher);
                System.out.println(" Teacher updated successfully!");
            } catch (Exception e) {
                System.err.println(" Error updating teacher: " + e.getMessage());
            }

            // Update course
            try {
                Course updatedCourse = new Course("CSE201", "Advanced ML", "ML201", 4, t);
                dao.updateCourse(updatedCourse);
                System.out.println(" Course updated successfully!");
            } catch (Exception e) {
                System.err.println("Error updating course: " + e.getMessage());
            }

            // Delete course
            try {
                dao.deleteCourse("CSE201");
                System.out.println(" Course deleted successfully!");
            } catch (Exception e) {
                System.err.println(" Error deleting course: " + e.getMessage());
            }

           } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
        
        
        
       

 
