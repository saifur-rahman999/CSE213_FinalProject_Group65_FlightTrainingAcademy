package com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.Model;

import java.time.LocalDate;

public class IntakeApplicant {
    private final long id;
    private final String name;
    private final LocalDate intakeDate;          // any date within the intake month
    private final EnrollmentStatus status;

    public IntakeApplicant(long id, String name, LocalDate intakeDate, EnrollmentStatus status) {
        this.id = id;
        this.name = name;
        this.intakeDate = intakeDate;
        this.status = status;
    }

    public long getId() { return id; }
    public String getName() { return name; }
    public LocalDate getIntakeDate() { return intakeDate; }
    public EnrollmentStatus getStatus() { return status; }
}