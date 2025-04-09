package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyUtil {
    public static String getConnectionString(String filename) throws IOException {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(filename)) {
            props.load(fis);
        }

        String host = props.getProperty("hostname");
        String port = props.getProperty("port");
        String db = props.getProperty("dbname");
        String user = props.getProperty("username");
        String pass = props.getProperty("password");

        return "jdbc:mysql://" + host + ":" + port + "/" + db + "?user=" + user + "&password=" + pass;
    }
}
