package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnUtil {

    private static final String DB_CONFIG_FILE = "src/db.properties";

    public static Connection getConnection() throws SQLException {
        Connection conn = null;
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Get the connection string from DBPropertyUtil
            String connectionString = DBPropertyUtil.getConnectionString(DB_CONFIG_FILE);

            if (connectionString != null) {
                conn = DriverManager.getConnection(connectionString);
                if (conn != null) {
                    System.out.println(" Database connected successfully.");
                } else {
                    System.out.println(" Database connection returned null.");
                }
            } else {
                throw new SQLException(" Failed to build database connection string.");
            }

        } catch (ClassNotFoundException e) {
            System.err.println("❌ JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("❌ Database connection failed: " + e.getMessage());
            throw e;
        }

        return conn;
    }
}
