package com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.Model;

import java.time.LocalDate;

public class InvoiceRecord {
    public final String invoiceId;
    public final String appId;
    public final String name;
    public final String email;
    public final String phone;
    public final String course;
    public final String intake;
    public final double amount;
    public final double paid;
    public final double balance;
    public final LocalDate dueDate;
    public final String status; // Pending / Partial / Paid / Closed

    public InvoiceRecord(String invoiceId, String appId, String name, String email, String phone,
                         String course, String intake, double amount, double paid, double balance,
                         LocalDate dueDate, String status) {
        this.invoiceId = invoiceId;
        this.appId = appId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.course = course;
        this.intake = intake;
        this.amount = amount;
        this.paid = paid;
        this.balance = balance;
        this.dueDate = dueDate;
        this.status = status;
    }
}

