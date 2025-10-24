package com.example.seniorcare.servlet;

import com.example.seniorcare.model.Prescription;
import util.DBConn;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/MedicalRecordsListServlet")
public class MedicalRecordsListServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        int userId = (int) session.getAttribute("userId");

        List<Prescription> prescriptions = new ArrayList<>();
        try (Connection conn = DBConn.getConnection()) {
            String sql = "SELECT id, user_id, file_path, upload_time, name FROM prescriptions WHERE user_id = ? ORDER BY upload_time DESC";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, userId);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    prescriptions.add(new Prescription(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("file_path"),
                        rs.getTimestamp("upload_time"),
                        rs.getString("name")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("prescriptions", prescriptions);
        request.getRequestDispatcher("medicalRecords.jsp").forward(request, response);
    }
}
