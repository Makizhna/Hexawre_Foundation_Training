package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnUtil {
    public static Connection getConnection(String propertyFilePath) {
        try {
            String connStr = DBPropertyUtil.getConnectionString(propertyFilePath);
            return DriverManager.getConnection(connStr);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error establishing database connection.");
        }
    }
}
