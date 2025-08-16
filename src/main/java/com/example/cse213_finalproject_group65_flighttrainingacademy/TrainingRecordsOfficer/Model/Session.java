package com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer;

import java.time.LocalDate;

public class Session {
    private final LocalDate date;
    private final String type;           // "Flight" / "Sim" (or anything you use)
    private final double durationHours;  // e.g., 1.5
    private final String trainee;        // simple string for dummy (name or id)
    private final String equipment;      // aircraft/device
    private final String remarks;

    public Session(LocalDate date, String type, double durationHours,
                   String trainee, String equipment, String remarks) {
        this.date = date;
        this.type = type;
        this.durationHours = durationHours;
        this.trainee = trainee;
        this.equipment = equipment;
        this.remarks = remarks;
    }

    public LocalDate getDate() { return date; }
    public String getType() { return type; }
    public double getDurationHours() { return durationHours; }
    public String getTrainee() { return trainee; }
    public String getEquipment() { return equipment; }
    public String getRemarks() { return remarks; }
}
