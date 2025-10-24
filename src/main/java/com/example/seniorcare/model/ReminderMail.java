package com.example.seniorcare.model;

import java.sql.Timestamp;

public class ReminderMail {
    private String medName;
    private Timestamp reminderTime;
    private String email;
    private boolean sent;
    private Timestamp sentTime;

    public ReminderMail(String medName, Timestamp reminderTime, String email, boolean sent, Timestamp sentTime) {
        this.medName = medName;
        this.reminderTime = reminderTime;
        this.email = email;
        this.sent = sent;
        this.sentTime = sentTime;
    }

    public String getMedName() { return medName; }
    public Timestamp getReminderTime() { return reminderTime; }
    public String getEmail() { return email; }
    public boolean isSent() { return sent; }
    public Timestamp getSentTime() { return sentTime; }
}
