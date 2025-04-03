package src.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DBPropertyUtil {
    
    public static Properties loadProperties(String filename) {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(filename)) {
            properties.load(fis);
        } catch (IOException e) {
            System.out.println("Error loading properties file: " + e.getMessage());
        }
        return properties;
    }
    
    public static String getConnectionString(String filename) {
        Properties properties = loadProperties(filename);
        return properties.getProperty("db.url");
    }
}
