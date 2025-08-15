package com.example.cse213_finalproject_group65_flighttrainingacademy.OperationManager.model;

import java.util.ArrayList;
import java.util.List;

public class ATCSystem {
    private List<FlightSchedule> schedules = new ArrayList<>();
    public void addSchedule(FlightSchedule s) {
        schedules.add(s);
    }
}