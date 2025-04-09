package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static Connection conn;

    public static Connection getConnection() {
        if (conn == null) {
            try {
            	
                // Load the driver
                Class.forName("com.mysql.cj.jdbc.Driver");

                Properties props = new Properties();
                FileInputStream fis = new FileInputStream("src/db.properties");
                props.load(fis);

                String host = props.getProperty("hostname");
                String port = props.getProperty("port");
                String db = props.getProperty("dbname");
                String user = props.getProperty("username");
                String pass = props.getProperty("password");

                String url = "jdbc:mysql://" + host + ":" + port + "/" + db + "?useSSL=false&serverTimezone=UTC";
                conn = DriverManager.getConnection(url, user, pass);
                System.out.println(" Database connected successfully.");
            } catch (ClassNotFoundException e) {
                System.out.println(" JDBC Driver not found: " + e.getMessage());
            } catch (IOException | SQLException e) {
                System.out.println(" Connection failed: " + e.getMessage());
            }
        }
        return conn;
    }
}
