package com.example.seniorcare.model;

import java.time.LocalDateTime;

public class VitalSign {
    private int id;
    private int userId;
    private LocalDateTime timestamp;
    private Integer systolic;
    private Integer diastolic;
    private Integer heartRate;
    private Integer bloodSugar;
    private Integer spo2;


    public VitalSign(int id, int userId, LocalDateTime timestamp, Integer systolic, Integer diastolic, Integer heartRate, Integer bloodSugar, Integer spo2) {
        this.id = id;
        this.userId = userId;
        this.timestamp = timestamp;
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.heartRate = heartRate;
        this.bloodSugar = bloodSugar;
        this.spo2 = spo2;
    }

    public int getId() { return id; }
    public int getUserId() { return userId; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public Integer getSystolic() { return systolic; }
    public Integer getDiastolic() { return diastolic; }
    public Integer getHeartRate() { return heartRate; }
    public Integer getBloodSugar() { return bloodSugar; }
    public Integer getSpo2() { return spo2; }
}
