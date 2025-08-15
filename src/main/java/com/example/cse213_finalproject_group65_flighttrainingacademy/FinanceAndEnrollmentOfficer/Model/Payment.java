package com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.Model;


import java.io.Serializable;
import java.time.LocalDate;

public class Payment implements Serializable {
    private static final long serialVersionUID = 1L;

    private String invoiceId;
    private double amount;        // +ve = payment; -ve = refund
    private LocalDate date;
    private String note;          // "Refund", "Dispute", etc.

    public Payment() {}

    public Payment(String invoiceId, double amount, LocalDate date, String note) {
        this.invoiceId = invoiceId;
        this.amount = amount;
        this.date = date;
        this.note = note;
    }

    public String getInvoiceId() { return invoiceId; }
    public void setInvoiceId(String invoiceId) { this.invoiceId = invoiceId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}
