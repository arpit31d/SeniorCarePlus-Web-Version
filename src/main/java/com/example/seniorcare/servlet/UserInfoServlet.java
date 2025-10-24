package com.example.seniorcare.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.*;
import util.DBConn;

@WebServlet("/UserInfoServlet")
public class UserInfoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            resp.sendRedirect("login.jsp");
            return;
        }
        String username = (String) session.getAttribute("username");

        float weight = Float.parseFloat(req.getParameter("weight"));
        float height = Float.parseFloat(req.getParameter("height"));
        int age = Integer.parseInt(req.getParameter("age"));
        String contact = req.getParameter("contact");
        String email = req.getParameter("email");
        String emergencyEmail = req.getParameter("emergencyEmail");

        try (Connection conn = DBConn.getConnection()) {
            String sql = "UPDATE users SET weight=?, height=?, age=?, contact=?, email=?, emergency_email=? WHERE username=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setFloat(1, weight);
                ps.setFloat(2, height);
                ps.setInt(3, age);
                ps.setString(4, contact);
                ps.setString(5, email);
                ps.setString(6, emergencyEmail);
                ps.setString(7, username);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("error", "Unable to update user info: " + e.getMessage());
            req.getRequestDispatcher("enterInfo.jsp").forward(req, resp);
            return;
        }
        resp.sendRedirect("index.jsp");
    }
}
