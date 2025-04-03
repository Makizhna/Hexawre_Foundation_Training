package src.util;

import src.util.DBConnUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionManager {

    private static final String DB_CONFIG = "dbconfig.properties";

    public static void enrollStudent(int studentId, int courseId) {
        String sql = "INSERT INTO Enrollments (student_id, course_id, enrollment_date) VALUES (?, ?, CURDATE())";

        try (Connection conn = DBConnUtil.getConnection(DB_CONFIG)) {
            conn.setAutoCommit(false);
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, studentId);
                pstmt.setInt(2, courseId);
                pstmt.executeUpdate();
                conn.commit();
                System.out.println("Student enrolled successfully!");
            } catch (SQLException e) {
                conn.rollback();
                System.out.println("Transaction rolled back.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
