package com.example.seniorcare.model;
import java.time.LocalDate;


public class MedicationPlan {
    public String user;
    public String email;
    public String name;
    public int totalUnits;
    public int dosePerIntake;
    public int timesPerDay;
    public LocalDate startDate;

    public MedicationPlan(String user, String email, String name, int totalUnits, int dosePerIntake, int timesPerDay, LocalDate startDate) {
        this.user = user;
        this.email = email;
        this.name = name;
        this.totalUnits = totalUnits;
        this.dosePerIntake = dosePerIntake;
        this.timesPerDay = timesPerDay;
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        int dailyUsage = dosePerIntake * timesPerDay;
        int days = Math.max(1, (int) Math.ceil((double) totalUnits / dailyUsage));
        return startDate.plusDays(days - 1);
    }

    public LocalDate getRefillReminderDate() {
        return getEndDate().minusDays(1);
    }
}
