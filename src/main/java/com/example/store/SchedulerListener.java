package com.example.store;
import com.example.seniorcare.model.MedicationPlan;
import com.example.seniorcare.model.WaterPrefs;
import jakarta.mail.MessagingException;
import util.EmailUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebListener;
import java.time.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@WebListener
public class SchedulerListener implements ServletContextListener {

    private ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(this::runChecks, 0, 10, TimeUnit.MINUTES);
    }

    private void runChecks() {
        try {
            sendWaterReminders();
            sendMedicationRefillReminders();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendWaterReminders() {
        Instant now = Instant.now();
        for (Map.Entry<String, WaterPrefs> entry : WaterStore.all().entrySet()) {
            WaterPrefs pref = entry.getValue();
            if (Duration.between(pref.lastSent, now).compareTo(pref.interval) >= 0) {
                try {
                    EmailUtil.sendEmail(pref.email, "Hydration Reminder", "Time to drink water and stay hydrated!");
                    pref.lastSent = now;
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void sendMedicationRefillReminders() {
        LocalDate today = LocalDate.now();
        for (Map.Entry<String, List<MedicationPlan>> e : MedicationStore.allPlans().entrySet()) {
            String user = e.getKey();
            for (MedicationPlan plan : e.getValue()) {
                if (plan.getRefillReminderDate().equals(today) && MedicationStore.markNotified(user, plan)) {
                    try {
                        String body = "Your medication '" + plan.name + "' will run out on " + plan.getEndDate()
                                + ". Please refill to avoid missing doses.";
                        EmailUtil.sendEmail(plan.email, "Medication Refill Reminder", body);
                    } catch (MessagingException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
        }
    }
}

