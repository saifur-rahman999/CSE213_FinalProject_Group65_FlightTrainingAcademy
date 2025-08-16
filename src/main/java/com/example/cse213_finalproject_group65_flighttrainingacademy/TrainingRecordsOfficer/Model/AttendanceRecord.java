package com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer.Model;


import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public class AttendanceRecord implements Serializable {
    @Serial private static final long serialVersionUID = 1L;

    private int sessionId;
    private LocalDate sessionDate;
    private String traineeId;
    private String traineeName;
    private boolean present;

    public AttendanceRecord() {}

    public AttendanceRecord(int sessionId, LocalDate sessionDate,
                            String traineeId, String traineeName, boolean present) {
        this.sessionId = sessionId;
        this.sessionDate = sessionDate;
        this.traineeId = traineeId;
        this.traineeName = traineeName;
        this.present = present;
    }

    public int getSessionId() { return sessionId; }
    public LocalDate getSessionDate() { return sessionDate; }
    public String getTraineeId() { return traineeId; }
    public String getTraineeName() { return traineeName; }
    public boolean isPresent() { return present; }

    public void setSessionId(int v) { this.sessionId = v; }
    public void setSessionDate(LocalDate v) { this.sessionDate = v; }
    public void setTraineeId(String v) { this.traineeId = v; }
    public void setTraineeName(String v) { this.traineeName = v; }
    public void setPresent(boolean v) { this.present = v; }
}
