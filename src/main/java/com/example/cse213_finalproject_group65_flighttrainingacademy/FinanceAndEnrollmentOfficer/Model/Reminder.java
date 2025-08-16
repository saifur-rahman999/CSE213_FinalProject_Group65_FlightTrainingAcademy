package com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.Model;


/** Row model for the Reminder preview table. */
public class Reminder {
    private final int studentId;
    private final String name;
    private final String email;
    private final double amount;   // total invoice amount
    private final double paid;     // amount already paid
    private final double due;      // amount still due

    public Reminder(int studentId, String name, String email, double amount, double paid, double due) {
        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.amount = amount;
        this.paid = paid;
        this.due = due;
    }

    public int getStudentId() { return studentId; }
    public String getName()    { return name; }
    public String getEmail()   { return email; }
    public double getAmount()  { return amount; }
    public double getPaid()    { return paid; }
    public double getDue()     { return due; }

    /** Handy one-liner for the popup list. */
    public String toLine() {
        return studentId + " — " + name + " <" + email + ">  Due: " + String.format("%,.2f", due);
    }
}
