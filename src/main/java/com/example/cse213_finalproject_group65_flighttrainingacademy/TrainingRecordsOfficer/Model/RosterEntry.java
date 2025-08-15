package com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer.Model;


import java.io.Serializable;

public class RosterEntry implements Serializable {
    private final int traineeId;
    private final String name;

    public RosterEntry(int traineeId, String name) {
        this.traineeId = traineeId; this.name = name;
    }
    public int getTraineeId() { return traineeId; }
    public String getName() { return name; }
}

