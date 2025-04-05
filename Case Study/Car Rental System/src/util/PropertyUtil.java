package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyUtil {
	
	private static final String PROPERTY_FILE = "src/dbconfig.properties"; 
    public static String getPropertyString() {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(PROPERTY_FILE)) {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error reading property file.");
        }

        String hostname = properties.getProperty("hostname");
        String dbname = properties.getProperty("dbname");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        String port = properties.getProperty("port");

        return "jdbc:mysql://" + hostname + ":" + port + "/" + dbname + "?user=" + username + "&password=" + password;
    }
}
