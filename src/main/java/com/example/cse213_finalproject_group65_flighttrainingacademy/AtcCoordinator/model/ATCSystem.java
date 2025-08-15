package com.example.cse213_finalproject_group65_flighttrainingacademy.AtcCoordinator.model;

import java.util.ArrayList;
import java.util.List;

public class ATCSystem {
    private List<IUser> userList = new ArrayList<>();
    private List<FlightSchedule> scheduleList = new ArrayList<>();

    public void registerUser(IUser user) {
        userList.add(user);
    }

    public void assignFlight(FlightSchedule fs) {
        scheduleList.add(fs);
    }
}