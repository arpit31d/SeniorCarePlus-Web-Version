package com.example.seniorcare.servlet;

import util.DBConn;
import util.EmailUtil;

import jakarta.mail.MessagingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.*;
import java.util.Random;

@WebServlet("/AppointmentServlet")
public class AppointmentServlet extends HttpServlet {

    private String generateRandomMeetLink() {

        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        Random rnd = new Random();
        for (int i = 0; i < 12; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return "https://meet.google.com/" + sb.toString();
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            resp.sendRedirect("login.jsp");
            return;
        }
        int userId = (int) session.getAttribute("userId");
        String doctorEmail = req.getParameter("doctorEmail");
        String dateTime = req.getParameter("dateTime"); 

        String userEmail = null;
        try (Connection conn = DBConn.getConnection()) {
            String query = "SELECT email FROM users WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setInt(1, userId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        userEmail = rs.getString("email");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("error", "Failed to fetch user email: " + e.getMessage());
            req.getRequestDispatcher("appointment.jsp").forward(req, resp);
            return;
        }

        if (userEmail == null || userEmail.trim().isEmpty()) {
            req.setAttribute("error", "User email is not available.");
            req.getRequestDispatcher("appointment.jsp").forward(req, resp);
            return;
        }

        String meetLink = generateRandomMeetLink();

        try (Connection conn = DBConn.getConnection()) {
            String sql = "INSERT INTO appointments (user_id, doctor_email, appointment_time, google_meet_link) VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, userId);
                ps.setString(2, doctorEmail);
                ps.setTimestamp(3, Timestamp.valueOf(dateTime.replace("T", " ") + ":00"));
                ps.setString(4, meetLink);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("error", "Failed to save appointment: " + e.getMessage());
            req.getRequestDispatcher("appointment.jsp").forward(req, resp);
            return;
        }

        String subject = "Appointment Scheduled at " + dateTime.replace("T", " ");
        String messageUser = "Dear user,\nYour appointment has been scheduled at " + dateTime.replace("T", " ") +
                             ".\nGoogle Meet Link: " + meetLink + "\nPlease join on time.";
        String messageDoctor = "Dear doctor,\nAn appointment has been scheduled with your patient at " +
                               dateTime.replace("T", " ") + ".\nGoogle Meet Link: " + meetLink;

        try {
            EmailUtil.sendEmail(userEmail, subject, messageUser);
            EmailUtil.sendEmail(doctorEmail, subject, messageDoctor);
        } catch (MessagingException e) {
            e.printStackTrace();
            req.setAttribute("error", "Appointment saved, but failed to send email: " + e.getMessage());
            req.getRequestDispatcher("appointment.jsp").forward(req, resp);
            return;
        }

        resp.sendRedirect("index.jsp?message=Appointment scheduled and emails sent successfully.");
    }
}
