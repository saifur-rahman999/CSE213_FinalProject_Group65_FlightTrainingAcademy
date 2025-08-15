package com.example.cse213_finalproject_group65_flighttrainingacademy.AtcCoordinator.model;

import java.time.LocalDateTime;

public class LoggedFlight {
    private final String empId;
    private final String flightId;
    private LocalDateTime loggedAt;

    public LoggedFlight(String empId, String flightId, LocalDateTime loggedAt) {
        this.empId = empId;
        this.flightId = flightId;
        this.loggedAt = loggedAt;
    }
}
