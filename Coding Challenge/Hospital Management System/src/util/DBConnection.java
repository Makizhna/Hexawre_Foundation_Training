package util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // Default method — loads from "src/db.properties"
    public static Connection getConnection() {
        try {
            return getConnection("src/db.properties");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load DB properties: " + e.getMessage());
        }
    }

    // Overloaded method — loads from given file path
    public static Connection getConnection(String fileName) throws IOException {
        String url = PropertyUtil.getConnectionString(fileName);
        try {
            Connection conn = DriverManager.getConnection(url);
            System.out.println("Database connected successfully!");
            return conn;
        } catch (SQLException e) {
            throw new RuntimeException("Connection failed: " + e.getMessage());
        }
    }

}
