package com.example.seniorcare.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.*;
import org.json.JSONObject; 

import util.DBConn;

@WebServlet("/MedicationReminderStatusServlet")
public class MedicationReminderStatusServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        JSONObject json = new JSONObject();
        if (session == null || session.getAttribute("userId") == null) {
            json.put("status", "no_session");
            resp.getWriter().print(json.toString());
            return;
        }
        int userId = (int) session.getAttribute("userId");

        try (Connection conn = DBConn.getConnection()) {
            String sql = "SELECT med_name, reminder_send_time FROM medication_reminders " +
                         "WHERE user_id = ? AND reminder_sent = TRUE AND reminder_send_time > NOW() - INTERVAL 15 MINUTE " +
                         "ORDER BY reminder_send_time DESC LIMIT 1";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, userId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String medName = rs.getString("med_name");
                        json.put("status", "sent");
                        json.put("medName", medName);
                    } else {
                        json.put("status", "none");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            json.put("status", "error");
            json.put("message", e.getMessage());
        }

        resp.setContentType("application/json");
        resp.getWriter().print(json.toString());
    }
}
