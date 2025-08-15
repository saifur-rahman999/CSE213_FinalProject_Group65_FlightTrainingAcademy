package com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer.Model;


import java.io.Serializable;

public class TraineeTotals implements Serializable {
    private final int traineeId;
    private int total;
    private int present;
    private int absent;

    public TraineeTotals(int traineeId) { this.traineeId = traineeId; }

    public void apply(boolean wasPresent) {
        total++;
        if (wasPresent) present++; else absent++;
    }

    public int getTraineeId() { return traineeId; }
    public int getTotal() { return total; }
    public int getPresent() { return present; }
    public int getAbsent() { return absent; }
    public double getPercent() { return total==0 ? 0.0 : (present*100.0)/total; }
}
