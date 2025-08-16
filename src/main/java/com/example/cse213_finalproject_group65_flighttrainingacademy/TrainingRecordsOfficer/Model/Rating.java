package com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer.Model;


import java.time.LocalDate;

public class Rating {
    private String label;       // e.g., "CPL", "IR", "ME"
    private LocalDate expiry;   // must be >= today

    public Rating() {}
    public Rating(String label, LocalDate expiry) {
        this.label = label;
        this.expiry = expiry;
    }

    public String getLabel() { return label; }
    public LocalDate getExpiry() { return expiry; }

    public void setLabel(String label) { this.label = label; }
    public void setExpiry(LocalDate expiry) { this.expiry = expiry; }
}
