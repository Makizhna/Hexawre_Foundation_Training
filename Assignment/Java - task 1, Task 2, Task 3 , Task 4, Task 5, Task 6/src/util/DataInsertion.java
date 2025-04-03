package src.util;

import src.util.DBConnUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataInsertion {

    private static final String DB_CONFIG = "dbconfig.properties";

    public static void addStudent(String firstName, String lastName, String dob, String email, String phone) {
        String sql = "INSERT INTO Students (first_name, last_name, dob, email, phone) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnUtil.getConnection(DB_CONFIG);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setDate(3, java.sql.Date.valueOf(dob));
            pstmt.setString(4, email);
            pstmt.setString(5, phone);
            pstmt.executeUpdate();

            System.out.println("Student added successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
