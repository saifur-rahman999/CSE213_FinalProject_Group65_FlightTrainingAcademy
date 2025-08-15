package com.example.cse213_finalproject_group65_flighttrainingacademy.AtcCoordinator.model;

import java.time.LocalDateTime;

public abstract class UserActivity {
    protected LocalDateTime timestamp;

    public UserActivity() {
        this.timestamp = LocalDateTime.now();
    }

    public abstract String getSummary();
}