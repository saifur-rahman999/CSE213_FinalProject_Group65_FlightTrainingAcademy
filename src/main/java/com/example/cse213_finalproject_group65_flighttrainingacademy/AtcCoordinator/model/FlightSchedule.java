package com.example.cse213_finalproject_group65_flighttrainingacademy.AtcCoordinator.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class FlightSchedule {
    private final String scheduleId;
    private String flightId;
    private LocalDate date;
    private LocalTime departureTime;
    private LocalTime arrivalTime;

    public FlightSchedule(String scheduleId, String flightId, LocalDate date, LocalTime departureTime, LocalTime arrivalTime) {
        this.scheduleId = scheduleId;
        this.flightId = flightId;
        this.date = date;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public String getScheduleId() {
        return scheduleId;
    }

}