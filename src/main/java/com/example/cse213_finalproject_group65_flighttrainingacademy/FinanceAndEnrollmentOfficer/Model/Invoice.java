package com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.Model;


import java.io.Serializable;
import java.time.LocalDateTime;

public class Invoice implements Serializable {
    private long id;                // auto-generated
    private int applicantId;
    private double amount;
    private double paid;            // FEO-3 starts at 0
    private LocalDateTime createdAt;

    public Invoice(long id, int applicantId, double amount, double paid) {
        this.id = id;
        this.applicantId = applicantId;
        this.amount = amount;
        this.paid = paid;
        this.createdAt = LocalDateTime.now();
    }

    public long getId() { return id; }
    public int getApplicantId() { return applicantId; }
    public double getAmount() { return amount; }
    public double getPaid() { return paid; }
    public double getDue() { return amount - paid; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // setters only if you need later goals (FEO-4)
    public void setPaid(double paid) { this.paid = paid; }
}
