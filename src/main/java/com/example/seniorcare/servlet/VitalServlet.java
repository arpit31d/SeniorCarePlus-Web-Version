package com.example.seniorcare.servlet;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;
import java.sql.*;
import util.DBConn;
@WebServlet("/vitals")
public class VitalServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer userId = (Integer) req.getSession(false).getAttribute("userId"); // or use String if string user ids
        int systolic = Integer.parseInt(req.getParameter("systolic"));
        int diastolic = Integer.parseInt(req.getParameter("diastolic"));
        int heartrate = Integer.parseInt(req.getParameter("heartrate"));
        int bloodsugar = Integer.parseInt(req.getParameter("bloodsugar"));
        int spo2 = Integer.parseInt(req.getParameter("spo2"));

        try (Connection conn = util.DBConn.getConnection()) {
            String sql = "INSERT INTO vital_signs (user_id, timestamp, systolic, diastolic, heartrate, bloodsugar, spo2) VALUES (?, NOW(), ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, userId);
                ps.setInt(2, systolic);
                ps.setInt(3, diastolic);
                ps.setInt(4, heartrate);
                ps.setInt(5, bloodsugar);
                ps.setInt(6, spo2);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new ServletException("Error inserting vitals", e);
        }
        resp.sendRedirect("vitals.jsp?msg=Saved");
    }
}
