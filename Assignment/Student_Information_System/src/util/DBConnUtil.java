package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnUtil {

    private static final String DB_CONFIG_FILE = "src/db.properties";
    
    
    private static final boolean DEBUG = false;

    
    private static boolean firstConnection = true;

    public static Connection getConnection(String context) throws SQLException {
        Connection conn = null;
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Build connection string using properties
            String connectionString = DBPropertyUtil.getConnectionString(DB_CONFIG_FILE);

            if (connectionString != null) {
                conn = DriverManager.getConnection(connectionString);

                if (conn != null && DEBUG && firstConnection) {
                    System.out.println("[" + context + "] Database connected successfully.");
                    firstConnection = false;                    // Suppress further success messages
                } else if (conn == null) {
                    System.out.println(" ! [" + context + "] Database connection returned null.");
                }
            } else {
                throw new SQLException("[" + context + "] Failed to build database connection string.");
            }

        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("[" + context + "] Database connection failed: " + e.getMessage());
            throw e;
        }

        return conn;
    }
}
