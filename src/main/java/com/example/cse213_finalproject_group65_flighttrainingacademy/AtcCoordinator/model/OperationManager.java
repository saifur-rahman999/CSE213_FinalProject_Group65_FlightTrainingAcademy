package com.example.cse213_finalproject_group65_flighttrainingacademy.AtcCoordinator.model;

public class OperationManager<FlightSchedule> implements IUser {
    private String empId;
    private String name;

    public OperationManager(String empId, String name) {
        this.empId = empId;
        this.name = name;
    }

    @Override
    public boolean login() {
        return true; // placeholder
    }

    @Override
    public void logout() {
        // logout logic
    }

    public void scheduleFlight(FlightSchedule fs) {
        // scheduling logic
    }

    public void reviewLogs() {
        // review logic
    }
}
