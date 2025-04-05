package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    
    private static final String URL = PropertyUtil.getPropertyString();  // Get connection string from properties

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL);  // Always return a new connection
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error establishing database connection.");
        }
    }
}
