package src.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnUtil {

    public static Connection getConnection(String propertiesFile) {
        Connection conn = null;
        try {
            Properties props = DBPropertyUtil.loadProperties(propertiesFile);
            String url = props.getProperty("db.url");               
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");

            conn = DriverManager.getConnection(url, user, password);
            System.out.println(" Database connected successfully!");
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }
        return conn;
    }
}
