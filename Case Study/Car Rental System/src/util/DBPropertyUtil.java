package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DBPropertyUtil {
	public static String getConnectionString(String propertyFile) {
	    Properties props = new Properties();
	    try (FileInputStream fis = new FileInputStream(propertyFile)) {
	        props.load(fis);
	    } catch (IOException e) {
	        e.printStackTrace();  
	        throw new RuntimeException("Error reading property file.");
	    }

	    return "jdbc:mysql://" + props.getProperty("hostname") + ":" + props.getProperty("port") + "/" +
	            props.getProperty("dbname") + "?user=" + props.getProperty("username") +
	            "&password=" + props.getProperty("password");
	}

}
