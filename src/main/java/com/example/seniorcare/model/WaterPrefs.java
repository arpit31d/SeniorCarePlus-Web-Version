package com.example.seniorcare.model;
import java.time.Duration;
import java.time.Instant;


public class WaterPrefs {
    public String user;
    public String email;
    public Duration interval;
    public Instant lastSent;

    public WaterPrefs(String user, String email, Duration interval) {
        this.user = user;
        this.email = email;
        this.interval = interval;
        this.lastSent = Instant.now();
    }
}


