package com.example.seniorcare.servlet;
import com.example.seniorcare.model.MedicationPlan;
import com.example.store.MedicationStore;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/med-plan")
public class MedicationPlanServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String user = req.getParameter("user");
        String email = req.getParameter("email");
        String name = req.getParameter("name");
        int totalUnits = Integer.parseInt(req.getParameter("totalUnits"));
        int dosePerIntake = Integer.parseInt(req.getParameter("dosePerIntake"));
        int timesPerDay = Integer.parseInt(req.getParameter("timesPerDay"));
        LocalDate startDate = LocalDate.parse(req.getParameter("startDate"));

        MedicationPlan plan = new MedicationPlan(user, email, name, totalUnits, dosePerIntake, timesPerDay, startDate);
        MedicationStore.addPlan(user, plan);
        resp.sendRedirect("index.jsp?msg=Medication plan saved");
    }
}
