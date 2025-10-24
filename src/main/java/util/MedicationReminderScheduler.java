package util;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.*;
import jakarta.mail.MessagingException;

public class MedicationReminderScheduler {
    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public void start() {
        scheduler.scheduleAtFixedRate(() -> {
            try (Connection conn = DBConn.getConnection()) {
                String query = "SELECT mr.id, u.email, mr.med_name, mr.dosage, mr.time " +
                               "FROM medication_reminders mr JOIN users u ON mr.user_id = u.id " +
                               "WHERE mr.reminder_sent = FALSE " +
                               "AND TIME_TO_SEC(TIMEDIFF(mr.time, CURRENT_TIME())) BETWEEN 0 AND 600"; // within next 10 minutes
                
                try (PreparedStatement ps = conn.prepareStatement(query);
                     ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        int reminderId = rs.getInt("id");
                        String userEmail = rs.getString("email");
                        String medName = rs.getString("med_name");
                        String dosage = rs.getString("dosage");
                        Time medTime = rs.getTime("time");

                        String subject = "Medication Reminder";
                        String body = "Reminder to take your medication: " + medName + " Dosage: " + dosage;

                        try {
                            EmailUtil.sendEmail(userEmail, subject, body);
                            updateReminderStatus(reminderId, conn, true);
                       
                        } catch (MessagingException e) {
                            updateReminderStatus(reminderId, conn, false);
                            e.printStackTrace();
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }, 0, 1, TimeUnit.MINUTES); 
    }

    private void updateReminderStatus(int reminderId, Connection conn, boolean sent) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "UPDATE medication_reminders SET reminder_sent = ?, reminder_send_time = ? WHERE id = ?")) {
            ps.setBoolean(1, sent);
            ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            ps.setInt(3, reminderId);
            ps.executeUpdate();
        }
    }

    public void stop() {
        scheduler.shutdown();
    }
}
