package com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.Model;

import java.io.Serializable;
import java.time.LocalDate;

public class Invoice implements Serializable {
    private final long id;
    private final long applicantId;
    private final double amount;
    private double paid;                 // mutable so FEO-4 can update
    private final LocalDate issueDate;

    public Invoice(long id, long applicantId, double amount, double paid) {
        this.id = id;
        this.applicantId = applicantId;
        this.amount = amount;
        this.paid = paid;
        this.issueDate = LocalDate.now();
    }

    public long getId() { return id; }
    public long getApplicantId() { return applicantId; }
    public double getAmount() { return amount; }
    public double getPaid() { return paid; }
    public void setPaid(double paid) { this.paid = paid; }
    public LocalDate getIssueDate() { return issueDate; }

    /** Convenience for validation and display (never negative). */
    public double getDue() {
        double due = amount - paid;
        return (due < 0) ? 0.0 : due;
    }
}
