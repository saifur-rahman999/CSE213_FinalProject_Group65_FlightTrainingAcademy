package com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer.Model;


import java.io.Serial;
import java.io.Serializable;

public class AssessmentRecord implements Serializable {
    @Serial private static final long serialVersionUID = 1L;

    private String sessionId; // link to SessionRecord.sessionId (simple join key)
    private boolean passed;

    public AssessmentRecord() {}

    public AssessmentRecord(String sessionId, boolean passed) {
        this.sessionId = sessionId;
        this.passed = passed;
    }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public boolean isPassed() { return passed; }
    public void setPassed(boolean passed) { this.passed = passed; }
}
