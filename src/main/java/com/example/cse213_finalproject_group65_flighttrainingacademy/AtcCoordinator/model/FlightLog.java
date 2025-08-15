package com.example.cse213_finalproject_group65_flighttrainingacademy.AtcCoordinator.model;

public class FlightLog extends UserActivity {
    private String flightId;
    private String route;
    private String remarks;

    public FlightLog(String flightId, String route, String remarks) {
        super();
        this.flightId = flightId;
        this.route = route;
        this.remarks = remarks;
    }

    @Override
    public String getSummary() {
        return "Flight " + flightId + " | Route: " + route + " | Remarks: " + remarks;
    }
}