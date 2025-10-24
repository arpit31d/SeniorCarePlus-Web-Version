package com.example.seniorcare.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;
import java.io.BufferedReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.json.JSONObject;
import util.EmailUtil;

@WebServlet("/SendSOSServlet")
public class SendSOSServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        JSONObject json = new JSONObject();

        try {
    
            StringBuilder sb = new StringBuilder();
            String line;
            BufferedReader reader = req.getReader();
            while ((line = reader.readLine()) != null) sb.append(line);
            if (sb.length() == 0) throw new Exception("No data received");
            JSONObject data = new JSONObject(sb.toString());

            double lat = data.optDouble("latitude", Double.NaN);
            double lon = data.optDouble("longitude", Double.NaN);
            if (Double.isNaN(lat) || Double.isNaN(lon)) {
                throw new Exception("Missing or invalid coordinates");
            }

            HttpSession session = req.getSession(false);
            if (session == null || session.getAttribute("userId") == null)
                throw new Exception("Session expired. Please login again.");
            int userId = (Integer) session.getAttribute("userId");

            String emergencyEmail = null;
            try (Connection conn = util.DBConn.getConnection();
                 PreparedStatement ps = conn.prepareStatement(
                    "SELECT emergency_email FROM users WHERE id = ?")) {
                ps.setInt(1, userId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        emergencyEmail = rs.getString("emergency_email");
                    }
                }
            }

            if (emergencyEmail == null || emergencyEmail.isBlank()) {
                throw new Exception("No emergency email found in your profile. Please update your profile.");
            }

            String msg = "SOS ALERT!\n\nLocation: https://maps.google.com/maps?q=" + lat + "," + lon + "\nPlease check immediately.";
            EmailUtil.sendEmail(emergencyEmail, "SOS Emergency Alert", msg);

            json.put("status", "success");
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("status", "error");
            json.put("message", e.getMessage());
            resp.setStatus(HttpServletResponse.SC_OK); 
        }
        resp.getWriter().print(json.toString());
    }
}
