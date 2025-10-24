package util;

import java.sql.*;

public class DBUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/";
    private static final String USER = "root"; 
    private static final String PASS = "Ar#310103"; 
    private static final String DB_NAME = "seniorcare";

    public static void createDatabaseAndTables() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (Connection conn = DriverManager.getConnection(DB_URL + DB_NAME, USER, PASS);
             Statement stmt = conn.createStatement()) {

            String sqlUsers = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(100)," +
                    "username VARCHAR(50) UNIQUE," +
                    "password VARCHAR(100)," +
                    "age INT," +
                    "height FLOAT," +
                    "weight FLOAT," +
                    "contact VARCHAR(15)," +
                    "email VARCHAR(100)" +
                    ")";
            stmt.executeUpdate(sqlUsers);

            String sqlMedications = "CREATE TABLE IF NOT EXISTS medication_reminders (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "user_id INT," +
                    "med_name VARCHAR(100)," +
                    "dosage VARCHAR(50)," +
                    "frequency INT," +
                    "time TIME," +
                    "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE" +
                    ")";
            stmt.executeUpdate(sqlMedications);

            String sqlAppointments = "CREATE TABLE IF NOT EXISTS appointments (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "user_id INT," +
                    "doctor_email VARCHAR(100)," +
                    "appointment_time DATETIME," +
                    "google_meet_link VARCHAR(255)," +
                    "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE" +
                    ")";
            stmt.executeUpdate(sqlAppointments);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
