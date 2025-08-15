package com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer.Model;


import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public class SessionRecord implements Serializable {
    @Serial private static final long serialVersionUID = 1L;

    private String sessionId;      // unique per session
    private LocalDate date;        // session date
    private double durationHours;  // e.g., 1.5
    private String instructorId;   // simple ID (used for filter)
    private String aircraftId;     // aircraft or sim device ID
    private boolean simulator;     // true if sim

    public SessionRecord() {}

    public SessionRecord(String sessionId, LocalDate date, double durationHours,
                         String instructorId, String aircraftId, boolean simulator) {
        this.sessionId = sessionId;
        this.date = date;
        this.durationHours = durationHours;
        this.instructorId = instructorId;
        this.aircraftId = aircraftId;
        this.simulator = simulator;
    }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public double getDurationHours() { return durationHours; }
    public void setDurationHours(double durationHours) { this.durationHours = durationHours; }

    public String getInstructorId() { return instructorId; }
    public void setInstructorId(String instructorId) { this.instructorId = instructorId; }

    public String getAircraftId() { return aircraftId; }
    public void setAircraftId(String aircraftId) { this.aircraftId = aircraftId; }

    public boolean isSimulator() { return simulator; }
    public void setSimulator(boolean simulator) { this.simulator = simulator; }
}
