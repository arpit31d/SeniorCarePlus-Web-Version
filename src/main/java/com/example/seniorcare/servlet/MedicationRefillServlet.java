package com.example.seniorcare.servlet;

import util.EmailUtil;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

@WebServlet("/med-refill")
public class MedicationRefillServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        Integer userId = (Integer) session.getAttribute("userId");
        String medName = req.getParameter("medication_name");
        int totalPills = Integer.parseInt(req.getParameter("total_pills"));
        int dosePerIntake = Integer.parseInt(req.getParameter("dosage_per_intake"));
        int timesPerDay = Integer.parseInt(req.getParameter("frequency_per_day"));
        String startDateStr = req.getParameter("start_date");
        String email = req.getParameter("email");

        LocalDate startDate = LocalDate.parse(startDateStr);

        int dailyDose = dosePerIntake * timesPerDay;
int daysSupply = totalPills / dailyDose;
LocalDate approxEndDate = startDate.plusDays(daysSupply);
LocalDate reminderDate = approxEndDate.minusDays(1); // as before

try (Connection conn = util.DBConn.getConnection()) {
    String sql = "INSERT INTO medication_refill (user_id, medication_name, total_pills, dosage_per_intake, frequency_per_day, start_date, end_date, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, userId);
        ps.setString(2, medName);
        ps.setInt(3, totalPills);
        ps.setInt(4, dosePerIntake);
        ps.setInt(5, timesPerDay);
        ps.setDate(6, java.sql.Date.valueOf(startDate));
        ps.setDate(7, java.sql.Date.valueOf(approxEndDate));  
        ps.setString(8, email);
        ps.executeUpdate();
    }
}
         catch (SQLException e) {
            throw new ServletException("Error inserting medication refill", e);
        }

        String message = "You are subscribed to medication refill reminders for " + medName + ".\n" +
            "Based on your usage from " + startDate + ", your medication will run out on " + approxEndDate + ".\n" +
            "You will get a refill reminder email on " + reminderDate + ".";

        try {
            EmailUtil.sendEmail(email, "Medication Refill Subscription Confirmation", message);
        } catch (Exception e) {
            e.printStackTrace();
        }

        resp.sendRedirect("medRefill.jsp?msg=Subscription saved. Email sent.");
    }
}
