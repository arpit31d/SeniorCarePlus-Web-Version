package util;

import java.sql.*;

public class DBConn {
    private static final String URL = "jdbc:mysql://localhost:3306/seniorcare";
    private static final String USER = "root"; 
    private static final String PASS = "Ar#310103"; 

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
