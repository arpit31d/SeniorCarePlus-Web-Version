package com.example.seniorcare.servlet;

import com.example.seniorcare.model.VitalSign;
import com.example.store.VitalStore;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/vitals-data")
public class VitalsDataServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = String.valueOf(req.getSession().getAttribute("userId"));
        if (userId == null || userId.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        List<VitalSign> vitalsList = VitalStore.getVitals(userId);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        Gson gson = new Gson();
        String json = gson.toJson(vitalsList);
        resp.getWriter().write(json);
    }
}
