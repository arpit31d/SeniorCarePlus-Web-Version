package com.example.seniorcare.model;

import java.sql.Timestamp;

public class Prescription {
    private int id;
    private int userId;
    private String filePath;
    private java.sql.Timestamp uploadTime;
    private String name;

    public Prescription(int id, int userId, String filePath, java.sql.Timestamp uploadTime, String name) {
        this.id = id;
        this.userId = userId;
        this.filePath = filePath;
        this.uploadTime = uploadTime;
        this.name = name;
    }
    public int getId() { return id; }
    public int getUserId() { return userId; }
    public String getFilePath() { return filePath; }
    public java.sql.Timestamp getUploadTime() { return uploadTime; }
    public String getName() { return name; }
}



