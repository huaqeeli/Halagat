package Controlleres;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;
import javax.swing.JOptionPane;

public class DatabaseConnector {

    private static Connection con;
    InputStream inputStream;

    static String[] data = new String[4];

    public static Connection dbConnector() throws IOException {
        DatabaseConnector dbcon = new DatabaseConnector();
        dbcon.getPropValues(data);
        String userName = data[0];
        String password = data[1];
        String dbName = data[2];
        String hostName = data[3];
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://" + hostName + ":3306/" + dbName + "?useUnicode=yes&characterEncoding=UTF-8", userName, password);
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return con;
    }

    public void getPropValues(String[] data) throws IOException {
        try {
            Properties prop = new Properties();
            String propFileName = "config.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            Date time = new Date(System.currentTimeMillis());

            // get the property value and print it out
            String userName = prop.getProperty("userName");
            String password = prop.getProperty("password");
            String dbName = prop.getProperty("dbName");
            String hostName = prop.getProperty("hostName");

            data[0] = userName;
            data[1] = password;
            data[2] = dbName;
            data[3] = hostName;
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}
