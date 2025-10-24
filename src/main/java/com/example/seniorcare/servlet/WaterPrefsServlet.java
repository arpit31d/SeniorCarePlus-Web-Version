package com.example.seniorcare.servlet;

import util.EmailUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;

@WebServlet("/water-prefs")
public class WaterPrefsServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer userId = (Integer) req.getSession(false).getAttribute("userId");
        String email = req.getParameter("email");
        int intervalMinutes = Integer.parseInt(req.getParameter("intervalMinutes"));
        String stopDateStr = req.getParameter("stopDate");

        Date stopDate = null;
        if (stopDateStr != null && !stopDateStr.isBlank()) {
            stopDate = Date.valueOf(stopDateStr);
        }

        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = util.DBConn.getConnection();
            String sql = "INSERT INTO water_intake_settings (user_id, email, interval_minutes, stop_date) VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE email=?, interval_minutes=?, stop_date=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setString(2, email);
            ps.setInt(3, intervalMinutes);
            if (stopDate != null) {
                ps.setDate(4, stopDate);
            } else {
                ps.setNull(4, java.sql.Types.DATE);
            }

            ps.setString(5, email);
            ps.setInt(6, intervalMinutes);
            if (stopDate != null) {
                ps.setDate(7, stopDate);
            } else {
                ps.setNull(7, java.sql.Types.DATE);
            }

            ps.executeUpdate();
        } catch (Exception e) {
            throw new ServletException("Error saving water intake settings", e);
        } finally {
            if (ps != null) { try { ps.close(); } catch (Exception ignore) {} }
            if (conn != null) { try { conn.close(); } catch (Exception ignore) {} }
        }

        if (stopDate == null || !LocalDate.now().isAfter(stopDate.toLocalDate())) {
            try {
                EmailUtil.sendEmail(email, "Hydration Reminder", "Stay hydrated! Time to drink water.");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        resp.sendRedirect("waterPrefs.jsp?msg=Preferences saved and first reminder sent");
    }
}
