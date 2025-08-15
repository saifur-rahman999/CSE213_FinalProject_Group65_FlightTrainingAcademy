package com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer.Model;


import java.io.Serializable;
import java.time.LocalDateTime;

public class AttendanceRecord implements Serializable {
    private final int sessionId;
    private final String sessionDate; // convenient for report
    private final int traineeId;
    private final boolean present;
    private final LocalDateTime savedAt;

    public AttendanceRecord(int sessionId, String sessionDate, int traineeId, boolean present, LocalDateTime savedAt) {
        this.sessionId = sessionId;
        this.sessionDate = sessionDate;
        this.traineeId = traineeId;
        this.present = present;
        this.savedAt = savedAt;
    }

    public int getSessionId() { return sessionId; }
    public String getSessionDate() { return sessionDate; }
    public int getTraineeId() { return traineeId; }
    public boolean isPresent() { return present; }
    public LocalDateTime getSavedAt() { return savedAt; }
}

