package com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.Model;

import java.time.format.DateTimeFormatter;

public class OutstandingRow {
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final String invoiceId;
    private final String name;
    private final String email;
    private final String intake;
    private final String course;
    private final String dueDateStr;
    private final Double balance;
    private final String ageBucket;

    public OutstandingRow(String invoiceId, String name, String email, String intake,
                          String course, String dueDateStr, Double balance, String ageBucket) {
        this.invoiceId = invoiceId;
        this.name = name;
        this.email = email;
        this.intake = intake;
        this.course = course;
        this.dueDateStr = dueDateStr;
        this.balance = balance;
        this.ageBucket = ageBucket;
    }

    public static OutstandingRow from(InvoiceRecord inv, String bucket) {
        return new OutstandingRow(
                inv.invoiceId,
                inv.name,
                inv.email,
                inv.intake,
                inv.course,
                inv.dueDate.format(FMT),
                inv.balance,
                bucket
        );
    }

    public String getInvoiceId() { return invoiceId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getIntake() { return intake; }
    public String getCourse() { return course; }
    public String getDueDateStr() { return dueDateStr; }
    public Double getBalance() { return balance; }
    public String getAgeBucket() { return ageBucket; }
}
