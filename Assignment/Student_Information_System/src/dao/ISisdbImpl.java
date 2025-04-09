package dao;

import util.DBConnUtil;
import util.DBPropertyUtil;
import entity.Course;
import entity.Enrollment;
import entity.Payment;
import entity.Student;
import entity.Teacher;
import exception.*;
import java.sql.SQLException;

import java.sql.Date;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ISisdbImpl implements ISisdb {
	
	

    // Add a new student to the database
    @Override
    public void addStudent(Student student) throws InvalidStudentDataException, SQLException {
        String checkEmailSQL = "SELECT COUNT(*) FROM students WHERE email = ?";
        String insertSQL = "INSERT INTO students (student_id, first_name, last_name, date_of_birth, email, phone_number) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkEmailSQL);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            // Check if the email already exists
            checkStmt.setString(1, student.getEmail());
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                System.err.println("Email already exists: " + student.getEmail());
                return;
            }

            // Proceed with insert if email doesn't exist
            pstmt.setInt(1, student.getStudentId());
            pstmt.setString(2, student.getFirstName());
            pstmt.setString(3, student.getLastName());
            pstmt.setDate(4, new java.sql.Date(student.getDateOfBirth().getTime()));
            pstmt.setString(5, student.getEmail());
            pstmt.setString(6, student.getPhoneNumber());

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted == 0) {
                throw new InvalidStudentDataException("Failed to insert student.");
            }

            System.out.println(" Student added successfully!");
        }
    }


    // Retrieve a student by ID
 // Retrieve a student by ID
    @Override
    public Student getStudentById(int studentId) throws StudentNotFoundException, SQLException {
        String sql = "SELECT * FROM Students WHERE student_id = ?";

        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, studentId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                try {
                    return new Student(
                        rs.getInt("student_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getDate("date_of_birth"),
                        rs.getString("email"),
                        rs.getString("phone_number") // corrected column name
                    );
                } catch (InvalidStudentDataException e) {
                    throw new SQLException("Invalid student data found in database.", e);
                }
            } else {
                throw new StudentNotFoundException("Student with ID " + studentId + " not found.");
            }
        }
    }


    // Retrieve all students from the database
    @Override
    public List<Student> getAllStudents() throws SQLException {
        String sql = "SELECT * FROM Students";
        List<Student> students = new ArrayList<>();

        try (Connection conn = DBConnUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                try {
                    Student student = new Student(
                            rs.getInt("student_id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getDate("date_of_birth"),      
                            rs.getString("email"),
                            rs.getString("phone_number")      
                    );
                    students.add(student);
                } catch (InvalidStudentDataException e) {
                    // Optional: log and skip the faulty record
                    System.err.println("Skipping invalid student record: " + e.getMessage());
                }
            }
        }
        return students;
    }

    
    // Update student information
    @Override
    public void updateStudentBalance(Student student) throws SQLException {
        String totalPaymentsQuery = "SELECT SUM(amount) AS total_paid FROM Payments WHERE student_id = ?";
        String updateBalanceQuery = "UPDATE Students SET balance = ? WHERE student_id = ?";

        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement pstmtTotal = conn.prepareStatement(totalPaymentsQuery);
             PreparedStatement pstmtUpdate = conn.prepareStatement(updateBalanceQuery)) {

            pstmtTotal.setInt(1, student.getStudentId());
            ResultSet rs = pstmtTotal.executeQuery();

            double totalPaid = 0.0;
            if (rs.next()) {
                totalPaid = rs.getDouble("total_paid");
            }

            // Example total fee
            double totalFee = 1000.0;
            double balance = totalFee - totalPaid;

            pstmtUpdate.setDouble(1, balance);
            pstmtUpdate.setInt(2, student.getStudentId());
            pstmtUpdate.executeUpdate();

            System.out.println("Updated balance for " + student.getFirstName() + ": ₹" + balance);
        }
    }



    // Delete student by ID
    @Override
    public void deleteStudent(int studentId) throws StudentNotFoundException, SQLException {
        String sql = "DELETE FROM students WHERE student_id = ?";

        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, studentId);
            int rowsDeleted = pstmt.executeUpdate();

            if (rowsDeleted == 0) {
                throw new StudentNotFoundException("No student found with ID: " + studentId);
            }

            System.out.println(" Student deleted successfully!");
        }
    }
    
    
    
    @Override
    public Student getStudentByEmail(String email) throws StudentNotFoundException, SQLException {
        Student student = null;

        String query = "SELECT * FROM Students WHERE email = ?";
        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("student_id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                Date dob = rs.getDate("date_of_birth");
                String phone = rs.getString("phone_number");

                try {
					student = new Student(id, firstName, lastName, dob, email, phone);
				} catch (InvalidStudentDataException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            } else {
                throw new StudentNotFoundException("Student with email " + email + " not found.");
            }
        }

        return student;
    }

    
    
 
 // Create a new course
    public void addCourse(Course course) throws SQLException {
        String sql = "INSERT INTO courses (course_id, course_name, credits, teacher_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, course.getCourseId());
            pstmt.setString(2, course.getCourseName());
            pstmt.setInt(3, course.getCredits());
            pstmt.setInt(4, course.getInstructor().getTeacherId());

            pstmt.executeUpdate();
            System.out.println("Course added successfully!");
        }
    }


    public Course getCourseById(String courseId) throws SQLException {
        String sql = "SELECT * FROM courses WHERE course_id = ?";
        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, courseId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                try {
                    Teacher instructor = new Teacher(
                        rs.getInt("teacher_id"),  // ✅ corrected here
                        "Dummy", "Instructor",
                        "dummy@teacher.com"
                    );

                    return new Course(
                        rs.getString("course_id"),
                        rs.getString("course_name"),
                        rs.getString("course_code"), // ⚠️ Make sure this column exists in DB!
                        rs.getInt("credits"),
                        instructor
                    );
                } catch (InvalidTeacherDataException | InvalidCourseDataException e) {
                    throw new SQLException("Invalid data found in course record.", e);
                }
            } else {
                throw new SQLException("Course not found with ID: " + courseId);
            }
        }
    }

    
 // Update course details
    public void updateCourse(Course course) throws SQLException {
        String sql = "UPDATE courses SET course_name = ?, course_code = ?, credits = ?, teacher_id = ? WHERE course_id = ?";
        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, course.getCourseName());
            pstmt.setString(2, course.getCourseCode());
            pstmt.setInt(3, course.getCredits());
            pstmt.setInt(4, course.getInstructor().getTeacherId());  //  use setInt here
            pstmt.setString(5, course.getCourseId());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Course updated successfully!");
            } else {
                System.out.println("No course found with ID: " + course.getCourseId());
            }
        }
    }


    // Delete course by ID
    public void deleteCourse(String courseId) throws SQLException {
        String sql = "DELETE FROM Courses WHERE course_id = ?";
        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, courseId);
            int rows = pstmt.executeUpdate();
            System.out.println(rows > 0 ? "Course deleted successfully!" : "No course found to delete.");
        }
    }

    // List all courses
    @Override
    public List<Course> getAllCourses() throws SQLException, InvalidCourseDataException, InvalidTeacherDataException {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT c.course_id, c.course_name, c.credits, c.teacher_id, " +
                     "t.first_name, t.last_name, t.email " +
                     "FROM Courses c LEFT JOIN Teacher t ON c.teacher_id = t.teacher_id";

        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                // Construct the teacher object
                Teacher instructor = null;
                int teacherId = rs.getInt("teacher_id");

                if (teacherId != 0) { // check if teacher_id is not null
                    instructor = new Teacher(
                        teacherId,
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email")
                    );
                }

                // Construct the course object
                Course course = new Course(
                    rs.getString("course_id"),
                    rs.getString("course_name"),
                    rs.getString("course_code"),
                    rs.getInt("credits"),
                    instructor
                );

                courses.add(course);
            }
        }

        return courses;
    }
    
    // Task - 9 
    @Override
    public Course getCourseByCode(String code) throws SQLException, InvalidCourseDataException {
    	Connection conn = DBConnUtil.getConnection();      // 

        String sql = "SELECT * FROM Courses WHERE course_code = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, code);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            String courseId = rs.getString("course_id");             // use String, not int
            String title = rs.getString("course_name");
            String courseCode = rs.getString("course_code");
            int credits = rs.getInt("credits");
            int teacherId = rs.getInt("teacher_id");

            Teacher teacher = (teacherId != 0) ? getTeacherById(teacherId) : null;
            return new Course(courseId, title, courseCode, credits, teacher);
        }
        return null;
    }




    
    
    @Override
    public void enrollStudentInCourse(Student student, Course course) {
        String enrollmentId = UUID.randomUUID().toString();              // Generate unique ID
        java.util.Date currentDate = new java.util.Date();               
        java.sql.Date enrollmentDate = new java.sql.Date(currentDate.getTime()); // Convert to SQL Date

        try {
            Enrollment enrollment = new Enrollment(enrollmentId, student, course, enrollmentDate);

            String sql = "INSERT INTO Enrollments (enrollment_id, student_id, course_id, enrollment_date) VALUES (?, ?, ?, ?)";

            try (Connection conn = DBConnUtil.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, enrollmentId);
                pstmt.setInt(2, student.getStudentId());
                pstmt.setString(3, course.getCourseId());
                pstmt.setDate(4, enrollmentDate);

                int rows = pstmt.executeUpdate();
                if (rows > 0) {
                    System.out.println("Student enrolled successfully in course: " + course.getCourseName());
                }
            }

        } catch (Exception e) {
            System.err.println("Enrollment failed: " + e.getMessage());
        }
    }

    
    
    @Override
    public void enrollStudentInCourse(int studentId, String courseId) {
        try {
            Student student = getStudentById(studentId);
            Course course = getCourseById(courseId);

            if (student != null && course != null) {
                enrollStudentInCourse(student, course); // Call existing logic
            } else {
                System.out.println(" Student or course not found.");
            }
        } catch (Exception e) {
            System.err.println(" Error enrolling student: " + e.getMessage());
        }
    }

    

    
    @Override
    public List<Enrollment> getEnrollmentsByStudentId(int studentId) {
        List<Enrollment> enrollments = new ArrayList<>();
        String sql = "SELECT * FROM Enrollments WHERE student_id = ?";

        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, studentId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String enrollmentId = rs.getString("enrollment_id");
                    String courseId = rs.getString("course_id");
                    Date date = rs.getDate("enrollment_date");

                    Student student = getStudentById(studentId);
                    Course course = getCourseById(courseId);

                    enrollments.add(new Enrollment(enrollmentId, student, course, date));
                }
            }
        } catch (Exception e) {
            System.err.println("Error getting enrollments: " + e.getMessage());
        }
        return enrollments;
    }
    
    
    
    @Override
    public List<Enrollment> getEnrollmentsByCourseId(String courseId) {
        List<Enrollment> enrollments = new ArrayList<>();
        String sql = "SELECT * FROM Enrollments WHERE course_id = ?";

        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, courseId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String enrollmentId = rs.getString("enrollment_id");
                    int studentId = rs.getInt("student_id");
                    Date date = rs.getDate("enrollment_date");

                    Student student = getStudentById(studentId);
                    Course course = getCourseById(courseId);

                    enrollments.add(new Enrollment(enrollmentId, student, course, date));
                }
            }
        } catch (Exception e) {
            System.err.println("Error getting enrollments: " + e.getMessage());
        }
        return enrollments;
    }

    
    @Override
    public void addTeacher(Teacher teacher) {
        String sql = "INSERT INTO Teacher (teacher_id, first_name, last_name, email) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, teacher.getTeacherId());
            pstmt.setString(2, teacher.getFirstName());
            pstmt.setString(3, teacher.getLastName());
            pstmt.setString(4, teacher.getEmail());

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Teacher added successfully.");
            }

        } catch (SQLException e) {
            System.err.println("Error adding teacher: " + e.getMessage());
        }
    }
    
    
    @Override
    public void updateTeacher(Teacher teacher) {
        String sql = "UPDATE Teacher SET first_name = ?, last_name = ?, email = ? WHERE teacher_id = ?";

        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, teacher.getFirstName());
            pstmt.setString(2, teacher.getLastName());
            pstmt.setString(3, teacher.getEmail());
            pstmt.setInt(4, teacher.getTeacherId());

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Teacher updated successfully.");
            }

        } catch (SQLException e) {
            System.err.println("Error updating teacher: " + e.getMessage());
        }
    }

    
    @Override
    public Teacher getTeacherById(int teacherId) {
        String sql = "SELECT * FROM Teacher WHERE teacher_id = ?";
        Teacher teacher = null;

        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, teacherId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");

                teacher = new Teacher(teacherId, firstName, lastName, email);
            }

        } catch (Exception e) {
            System.err.println("Error fetching teacher: " + e.getMessage());
        }

        return teacher;
    }
    
    
    //get teacher by Email
    @Override
    public Teacher getTeacherByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM Teacher WHERE email = ?";
        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                try {
					return new Teacher(
					    rs.getInt("teacher_id"),
					    rs.getString("first_name"),
					    rs.getString("last_name"),
					    rs.getString("email")
					);
				} catch (InvalidTeacherDataException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        }
        return null; // Not found
    }

    

    @Override
    public List<Teacher> getAllTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        String sql = "SELECT * FROM Teacher";

        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("teacher_id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");

                Teacher teacher = new Teacher(id, firstName, lastName, email);
                teachers.add(teacher);
            }

        } catch (Exception e) {
            System.err.println("Error retrieving teachers: " + e.getMessage());
        }

        return teachers;
    }
    
    
    @Override
    public void assignCourseToTeacher(int teacherId, String courseId) {
        String sql = "UPDATE courses SET instructor_id = ? WHERE course_id = ?";

        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, teacherId);
            pstmt.setString(2, courseId);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Teacher assigned to course successfully.");
            }

        } catch (SQLException e) {
            System.err.println("Error assigning teacher to course: " + e.getMessage());
        }
    }

    
    
    //Payment
    
    @Override
    public void addPayment(Payment payment) throws PaymentValidationException {
        String insertSql = "INSERT INTO Payments (payment_id, student_id, amount, payment_date) VALUES (?, ?, ?, ?)";
        String updateBalanceSql = "UPDATE Students SET outstanding_balance = outstanding_balance - ? WHERE student_id = ?";

        try (Connection conn = DBConnUtil.getConnection()) {
            conn.setAutoCommit(false); // Transaction start

            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                 PreparedStatement updateBalanceStmt = conn.prepareStatement(updateBalanceSql)) {

                insertStmt.setInt(1, payment.getPaymentId());
                insertStmt.setInt(2, payment.getStudent().getStudentId());
                insertStmt.setDouble(3, payment.getPaymentAmount());
                insertStmt.setDate(4, new java.sql.Date(payment.getPaymentDate().getTime()));
                insertStmt.executeUpdate();

                updateBalanceStmt.setDouble(1, payment.getPaymentAmount());
                updateBalanceStmt.setInt(2, payment.getStudent().getStudentId());
                updateBalanceStmt.executeUpdate();

                conn.commit(); // Success
            } catch (Exception e) {
                conn.rollback(); // Revert changes if anything fails
                throw new PaymentValidationException("Failed to add payment and update balance: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Database error during payment: " + e.getMessage());
        }
    }

   
    
    
    @Override
    public List<Payment> getPaymentsByStudentId(int studentId) {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM Payments WHERE student_id = ?";

        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, studentId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int paymentId = rs.getInt("payment_id");
                double amount = rs.getDouble("amount");
                Date paymentDate = rs.getDate("payment_date");

                Student student = getStudentById(studentId); // Assuming this method exists
                Payment payment = new Payment(paymentId, student, amount, paymentDate);
                payments.add(payment);
            }

        } catch (Exception e) {
            System.err.println("Error retrieving payments: " + e.getMessage());
        }

        return payments;
    }
    
    @Override
    public double getTotalPaymentsByCourseId(String courseId) {
        double total = 0;

        String sql = """
            SELECT SUM(p.amount) AS total_amount
            FROM payments p
            JOIN enrollments e ON p.student_id = e.student_id
            WHERE e.course_id = ?
        """;

        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, courseId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                total = rs.getDouble("total_amount");
            }

        } catch (SQLException e) {
            System.err.println("Error calculating total payments by course: " + e.getMessage());
        }

        return total;
    }
    
    
    // Task 11: to generate an enrollment report for a course name
    @Override
    public List<Enrollment> getEnrollmentsByCourseName(String courseName)
            throws SQLException, CourseNotFoundException, InvalidEnrollmentDataException,
                   InvalidStudentDataException, InvalidCourseDataException {

        List<Enrollment> enrollments = new ArrayList<>();

        String query = "SELECT e.enrollment_id, e.student_id, e.course_id, e.enrollment_date " +
                       "FROM Enrollments e " +
                       "JOIN Courses c ON e.course_id = c.course_id " +
                       "WHERE c.course_name = ?";

        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, courseName);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String enrollmentId = rs.getString("enrollment_id");
                    int studentId = rs.getInt("student_id");
                    String courseId = rs.getString("course_id");
                    Date enrollmentDate = rs.getDate("enrollment_date");

                    // Retrieve student safely
                    Student student = null;
                    try {
                        student = getStudentById(studentId);
                    } catch (StudentNotFoundException e) {
                        System.out.println("Student not found for ID: " + studentId);
                        continue;           // Skip this record if student not found
                    }

                    Course course = getCourseById(courseId);

                    Enrollment enrollment = new Enrollment(enrollmentId, student, course, enrollmentDate);
                    enrollments.add(enrollment);
                }
            }
        }

        if (enrollments.isEmpty()) {
            throw new CourseNotFoundException("No enrollments found for course: " + courseName);
        }

        return enrollments;
    }


	@Override
	public void updateStudent(Student student)
			throws InvalidStudentDataException, SQLException, exception.SQLException {
		// TODO Auto-generated method stub
		
	}

}






   
  
   
