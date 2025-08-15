package com.example.cse213_finalproject_group65_flighttrainingacademy.AtcCoordinator.model;

public class ATCCoordinator<FlightLog> implements IUser {
    private String empId;
    private String name;

    public ATCCoordinator(String empId, String name) {
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

    public void manageAirTraffic() {
        // management logic
    }

    public void logFlightDetails(FlightLog log) {
        // log flight logic
    }
}