package com.example.seniorcare.servlet;

import com.example.seniorcare.model.ReminderMail;
import util.DBConn;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/MedicationReminderListServlet")
public class MedicationReminderListServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        int userId = (int) session.getAttribute("userId");

        List<ReminderMail> upcoming = new ArrayList<>();
        List<ReminderMail> sent = new ArrayList<>();

        try (Connection conn = DBConn.getConnection()) {
            String sql = "SELECT med_name, reminder_time, email, reminder_sent, reminder_send_time FROM medication_reminders WHERE user_id = ? ORDER BY reminder_time ASC";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, userId);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        ReminderMail rm = new ReminderMail(
                                rs.getString("med_name"),
                                rs.getTimestamp("reminder_time"),
                                rs.getString("email"),
                                rs.getBoolean("reminder_sent"),
                                rs.getTimestamp("reminder_send_time")
                        );
                        if (rm.isSent()) sent.add(rm);
                        else upcoming.add(rm);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("upcomingMails", upcoming);
        request.setAttribute("sentMails", sent);
        request.getRequestDispatcher("medication.jsp").forward(request, response);
    }
}
