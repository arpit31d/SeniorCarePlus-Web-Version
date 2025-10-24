package com.example.seniorcare.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.*;
import util.DBConn;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        

        try (Connection conn = DBConn.getConnection()) {
            String sql = "INSERT INTO users (name, username, password, email) VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, name);
                ps.setString(2, username);
                ps.setString(3, password);
                ps.setString(4, email);
                
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("errorMessage", "Error registering user: " + e.getMessage());
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return;
        }
        resp.sendRedirect("login.jsp");
    }
}
