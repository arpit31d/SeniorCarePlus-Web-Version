package com.example.seniorcare.servlet;

import util.DBConn;
import util.EmailUtil;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.*;

@WebServlet("/MedicationServlet")
public class MedicationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            resp.sendRedirect("login.jsp");
            return;
        }
        int userId = (int) session.getAttribute("userId");
        String medName = req.getParameter("medName");
        String dosage = req.getParameter("dosage");
        int frequency = Integer.parseInt(req.getParameter("frequency"));
        String timeStr = req.getParameter("reminderTime");
        if (timeStr != null && timeStr.length() == 5) {
            timeStr = timeStr + ":00";
        }
        String stopDateStr = req.getParameter("stopDate");
        Date stopDate = null;
        if (stopDateStr != null && !stopDateStr.isEmpty()) {
            stopDate = Date.valueOf(stopDateStr);
        }
        String userEmail = (String) session.getAttribute("username");

        try (Connection conn = DBConn.getConnection()) {
            String insertSql = "INSERT INTO medication_reminders (user_id, med_name, dosage, frequency, time, stop_date, reminder_sent) VALUES (?, ?, ?, ?, ?, ?, FALSE)";
            try (PreparedStatement ps = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, userId);
                ps.setString(2, medName);
                ps.setString(3, dosage);
                ps.setInt(4, frequency);
                ps.setTime(5, Time.valueOf(timeStr));
                ps.setDate(6, stopDate);
                ps.executeUpdate();

                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        long reminderId = keys.getLong(1);

                        String subject = "Medication Reminder";
                        String message = "Your medication '" + medName + "' is due in the next 10 minutes. Dosage: " + dosage + ". Please ensure you take your medicine on time.";

                        EmailUtil.sendEmail(userEmail, subject, message);

                        String updateSql = "UPDATE medication_reminders SET reminder_sent = TRUE, reminder_send_time = NOW() WHERE id = ?";
                        try (PreparedStatement updPs = conn.prepareStatement(updateSql)) {
                            updPs.setLong(1, reminderId);
                            updPs.executeUpdate();
                        }
                    } else {
                        req.setAttribute("error", "Failed to obtain reminder ID");
                        req.getRequestDispatcher("medication.jsp").forward(req, resp);
                        return;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Error: " + e.getMessage());
            req.getRequestDispatcher("medication.jsp").forward(req, resp);
            return;
        }

        req.setAttribute("message", "Reminder scheduled and email sent successfully.");
        req.getRequestDispatcher("medication.jsp").forward(req, resp);
    }
}
