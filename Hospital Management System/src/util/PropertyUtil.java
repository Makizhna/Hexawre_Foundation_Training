package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

// Utility class to read DB properties from file

public class PropertyUtil {
    public static String getPropertyString() throws IOException {
        Properties props = new Properties();

        // Load from classpath 
        InputStream input = PropertyUtil.class.getClassLoader().getResourceAsStream("db.properties");

        if (input == null) {
            throw new IOException("db.properties not found in classpath");
        }

        props.load(input);

        String host = props.getProperty("hostname");
        String port = props.getProperty("port");
        String db = props.getProperty("dbname");
        String user = props.getProperty("username");
        String pass = props.getProperty("password");

        return "jdbc:mysql://" + host + ":" + port + "/" + db + "?user=" + user + "&password=" + pass;
    }
}
