package com.example.cse213_finalproject_group65_flighttrainingacademy.TraineePilot.Model;

public class TrainingSession {
    private String sessionId;
    private String sessionType;
    private String location;
    private String flightDate;
    private String aircraft;
    private String traineeBatch;
    private String flightTime;

    public TrainingSession(String sessionId, String sessionType, String location, String flightDate,
                   String aircraft, String traineeBatch, String flightTime) {
        this.sessionId = sessionId;
        this.sessionType = sessionType;
        this.location = location;
        this.flightDate = flightDate;
        this.aircraft = aircraft;
        this.traineeBatch = traineeBatch;
        this.flightTime = flightTime;
    }

    // Getters and setters
    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public String getSessionType() { return sessionType; }
    public void setSessionType(String sessionType) { this.sessionType = sessionType; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getFlightDate() { return flightDate; }
    public void setFlightDate(String flightDate) { this.flightDate = flightDate; }

    public String getAircraft() { return aircraft; }
    public void setAircraft(String aircraft) { this.aircraft = aircraft; }

    public String getTraineeBatch() { return traineeBatch; }
    public void setTraineeBatch(String traineeBatch) { this.traineeBatch = traineeBatch; }

    public String getFlightTime() { return flightTime; }
    public void setFlightTime(String flightTime) { this.flightTime = flightTime; }
}
