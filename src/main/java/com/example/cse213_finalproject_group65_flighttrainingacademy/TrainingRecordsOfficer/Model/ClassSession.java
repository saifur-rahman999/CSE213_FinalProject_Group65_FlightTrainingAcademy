package com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer.Model;


import java.io.Serializable;
import java.time.LocalDate;

public class ClassSession implements Serializable {
    private int sessionId;
    private LocalDate date;
    private SessionType type;
    private String traineeId;
    private String aircraftOrDeviceId;
    private double durationHours;
    private String remarks;

    public ClassSession() {}

    public ClassSession(int sessionId, LocalDate date, SessionType type,
                        String traineeId, String aircraftOrDeviceId,
                        double durationHours, String remarks) {
        this.sessionId = sessionId;
        this.date = date;
        this.type = type;
        this.traineeId = traineeId;
        this.aircraftOrDeviceId = aircraftOrDeviceId;
        this.durationHours = durationHours;
        this.remarks = remarks;
    }

    public int getSessionId() { return sessionId; }
    public LocalDate getDate() { return date; }
    public SessionType getType() { return type; }
    public String getTraineeId() { return traineeId; }
    public String getAircraftOrDeviceId() { return aircraftOrDeviceId; }
    public double getDurationHours() { return durationHours; }
    public String getRemarks() { return remarks; }

    public void setSessionId(int sessionId) { this.sessionId = sessionId; }
    public void setDate(LocalDate date) { this.date = date; }
    public void setType(SessionType type) { this.type = type; }
    public void setTraineeId(String traineeId) { this.traineeId = traineeId; }
    public void setAircraftOrDeviceId(String aircraftOrDeviceId) { this.aircraftOrDeviceId = aircraftOrDeviceId; }
    public void setDurationHours(double durationHours) { this.durationHours = durationHours; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
}
