package com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.Model;


import java.io.Serializable;
import java.time.LocalDate;

public class Invoice implements Serializable {
    private static final long serialVersionUID = 1L;

    private String invoiceId;
    private String traineeId;
    private String traineeName;
    private String email;
    private String intake;    // e.g., "Summer-25"
    private String course;    // e.g., "PPL Ground"
    private LocalDate dueDate;
    private double amountDue;
    private double balance;   // maintained by controllers upon payment changes
    private InvoiceStatus status;

    public Invoice() {}

    public Invoice(String invoiceId, String traineeId, String traineeName, String email,
                   String intake, String course, LocalDate dueDate,
                   double amountDue, double balance, InvoiceStatus status) {
        this.invoiceId = invoiceId;
        this.traineeId = traineeId;
        this.traineeName = traineeName;
        this.email = email;
        this.intake = intake;
        this.course = course;
        this.dueDate = dueDate;
        this.amountDue = amountDue;
        this.balance = balance;
        this.status = status;
    }

    // Getters/Setters (generated – keep simple)
    public String getInvoiceId() { return invoiceId; }
    public void setInvoiceId(String invoiceId) { this.invoiceId = invoiceId; }

    public String getTraineeId() { return traineeId; }
    public void setTraineeId(String traineeId) { this.traineeId = traineeId; }

    public String getTraineeName() { return traineeName; }
    public void setTraineeName(String traineeName) { this.traineeName = traineeName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getIntake() { return intake; }
    public void setIntake(String intake) { this.intake = intake; }

    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public double getAmountDue() { return amountDue; }
    public void setAmountDue(double amountDue) { this.amountDue = amountDue; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

    public InvoiceStatus getStatus() { return status; }
    public void setStatus(InvoiceStatus status) { this.status = status; }
}
