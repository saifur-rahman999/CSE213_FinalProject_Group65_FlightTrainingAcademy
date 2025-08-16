package com.example.cse213_finalproject_group65_flighttrainingacademy.AircraftMaintenanceEngineer;

import java.time.LocalDate;

public class ScheduleMaintenance {
    private final String aircraftId;
    private final String priority;
    private final LocalDate date;

    public ScheduleMaintenance(String aircraftId, String priority, LocalDate date) {
        this.aircraftId = aircraftId;
        this.priority = priority;
        this.date = date;
    }

    public String getAircraftId() {
        return aircraftId;
    }

    public String getPriority() {
        return priority;
    }

    public LocalDate getDate() {
        return date;
    }
}
