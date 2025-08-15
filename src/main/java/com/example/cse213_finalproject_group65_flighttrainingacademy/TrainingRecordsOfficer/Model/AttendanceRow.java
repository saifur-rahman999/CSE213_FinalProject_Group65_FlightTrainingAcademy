package com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer.Model;


import java.io.Serializable;

public class AttendanceRow implements Serializable {
    private int traineeId;
    private String name;
    private boolean present;

    public AttendanceRow(int traineeId, String name, boolean present) {
        this.traineeId = traineeId; this.name = name; this.present = present;
    }

    public int getTraineeId() { return traineeId; }
    public String getName() { return name; }
    public boolean isPresent() { return present; }
    public void setPresent(boolean present) { this.present = present; }
}
