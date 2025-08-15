package com.example.cse213_finalproject_group65_flighttrainingacademy.OperationManager.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class FlightSchedule {

    private String flightId;
    private LocalDate date;
    private LocalTime departureTime;
    private LocalTime arrivalTime;

    public FlightSchedule(String flightId, LocalDate date,
                          LocalTime departureTime, LocalTime arrivalTime) {
        this.flightId = flightId;
        this.date = date;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public String getFlightId() { return flightId; }
    public LocalDate getDate()  { return date; }
    public LocalTime getDepartureTime() { return departureTime; }
    public LocalTime getArrivalTime()   { return arrivalTime; }
}