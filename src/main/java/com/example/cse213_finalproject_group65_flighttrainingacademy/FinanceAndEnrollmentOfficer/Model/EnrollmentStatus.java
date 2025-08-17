package com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.Model;


public enum EnrollmentStatus {
    Pending("Pending"),
    Active("Active"),
    Deferred("Deferred"),
    Withdrawn("Withdrawn");

    public final String label;
    EnrollmentStatus(String label) { this.label = label; }

    @Override public String toString() { return label; }
}
