package com.example.cse213_finalproject_group65_flighttrainingacademy.AircraftMaintenanceEngineer;

import java.time.LocalDate;

public class DefectReport {
    private final String aircraftId;
    private final String severity;
    private final String title;
    private final LocalDate date;

    public DefectReport(String aircraftId, String severity, String title, LocalDate date) {
        this.aircraftId = aircraftId;
        this.severity = severity;
        this.title = title;
        this.date = date;
    }

    public String getAircraftId() {
        return aircraftId;
    }

    public String getSeverity() {
        return severity;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getDate() {
        return date;
    }

}