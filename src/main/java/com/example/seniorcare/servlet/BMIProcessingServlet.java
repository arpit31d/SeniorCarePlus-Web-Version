package com.example.seniorcare.servlet;

import util.BMIUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet("/BMIProcessingServlet")
public class BMIProcessingServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        float height = Float.parseFloat(req.getParameter("height"));
        float weight = Float.parseFloat(req.getParameter("weight"));

        float bmi = BMIUtil.calculateBMI(height, weight);
        String status = BMIUtil.healthStatus(bmi);

        HttpSession session = req.getSession();
        session.setAttribute("bmi", String.format("%.2f", bmi));
        session.setAttribute("status", status);

        resp.sendRedirect("index.jsp"); 
    }
}
