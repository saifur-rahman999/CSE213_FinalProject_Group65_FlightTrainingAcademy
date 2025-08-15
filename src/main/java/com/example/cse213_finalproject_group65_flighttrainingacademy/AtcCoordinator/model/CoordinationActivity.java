package com.example.cse213_finalproject_group65_flighttrainingacademy.AtcCoordinator.model;

import java.util.List;

public class CoordinationActivity extends UserActivity {
    private String coordinationId;
    private List<String> involvedFlights;
    private String notes;

    public CoordinationActivity(String coordinationId, List<String> involvedFlights, String notes) {
        super();
        this.coordinationId = coordinationId;
        this.involvedFlights = involvedFlights;
        this.notes = notes;
    }

    @Override
    public String getSummary() {
        return "Coordination " + coordinationId + " | Flights: " + involvedFlights + " | Notes: " + notes;
    }
}