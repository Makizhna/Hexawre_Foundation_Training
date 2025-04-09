package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

	

	public class DBPropertyUtil {
	    public static String getConnectionString(String propertyFile) {
	        Properties properties = new Properties();

	        try (FileInputStream input = new FileInputStream(propertyFile)) {
	            properties.load(input);
	            String url = properties.getProperty("db.url");
	            String user = properties.getProperty("db.user");
	            String password = properties.getProperty("db.password");
	            return url + "?user=" + user + "&password=" + password;
	        } catch (IOException e) {
	            System.err.println("Error loading database properties: " + e.getMessage());
	            return null;
	        }
	   }

}
