package dao;

import entity.Student;
import entity.Teacher;
import entity.Course;
import entity.Enrollment;
import entity.Payment;
import exception.*;

import java.sql.SQLException;
import java.util.List;

public interface ISisdb {
    
	void addStudent(Student student) throws InvalidStudentDataException, SQLException;
    Student getStudentById(int studentId) throws StudentNotFoundException, SQLException, exception.SQLException;
    List<Student> getAllStudents() throws SQLException, exception.SQLException;
    void updateStudent(Student student) throws InvalidStudentDataException, SQLException, exception.SQLException;
    void deleteStudent(int studentId) throws StudentNotFoundException, SQLException, exception.SQLException;
    Student getStudentByEmail(String email) throws StudentNotFoundException, SQLException;
    void updateStudentBalance(Student student) throws SQLException;
    

    // Course Operations
    void addCourse(Course course) throws InvalidCourseDataException, SQLException, exception.SQLException;
    Course getCourseById(String courseId) throws CourseNotFoundException, SQLException, exception.SQLException;
    List<Course> getAllCourses() throws SQLException, InvalidCourseDataException, InvalidTeacherDataException;
    void updateCourse(Course course) throws InvalidCourseDataException, CourseNotFoundException, SQLException, exception.SQLException;
    void deleteCourse(String courseId) throws CourseNotFoundException, exception.SQLException, SQLException;
    Course getCourseByCode(String code) throws SQLException, InvalidCourseDataException;
 
    
    
    
    // Enrollment Operations
    void enrollStudentInCourse(Student student, Course course) throws DuplicateEnrollmentException, InvalidEnrollmentDataException;
    List<Enrollment> getEnrollmentsByStudentId(int studentId);
    List<Enrollment> getEnrollmentsByCourseId(String courseId);
    void enrollStudentInCourse(int studentId, String courseId) throws SQLException;
 // Task 11 - Enrollment Report Generation
    public List<Enrollment> getEnrollmentsByCourseName(String courseName)
            throws SQLException, CourseNotFoundException, InvalidEnrollmentDataException,
                   InvalidStudentDataException, InvalidCourseDataException;



    
    // Teacher Operations
    void addTeacher(Teacher teacher) throws InvalidTeacherDataException, exception.SQLException;
    Teacher getTeacherById(int teacherId) throws TeacherNotFoundException, exception.SQLException, InvalidTeacherDataException, SQLException;
    List<Teacher> getAllTeachers() throws exception.SQLException, SQLException, InvalidTeacherDataException;
    void updateTeacher(Teacher teacher) throws InvalidTeacherDataException, TeacherNotFoundException, exception.SQLException, SQLException;
    void assignCourseToTeacher(int teacherId, String courseId);
    Teacher getTeacherByEmail(String email) throws SQLException;

    //void deleteTeacher(int teacherId) throws TeacherNotFoundException, exception.SQLException, SQLException;

    // Payment Operations
    void addPayment(Payment payment) throws PaymentValidationException, PaymentException, exception.SQLException, InsufficientFundsException, SQLException;
    List<Payment> getPaymentsByStudentId(int studentId);
	double getTotalPaymentsByCourseId(String courseId);
	//List<Payment> getAllPayments() throws exception.SQLException, SQLException, PaymentValidationException;
	
	
	
	
    
}