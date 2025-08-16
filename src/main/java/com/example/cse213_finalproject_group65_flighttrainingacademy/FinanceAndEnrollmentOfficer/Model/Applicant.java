package com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.Model;

import java.io.Serializable;

public class Applicant implements Serializable {
    private final int id;
    private final String name;
    private final String email;

    public Applicant(int id, String name, String email) {
        this.id = id;
        this.name = name == null ? "" : name.trim();
        this.email = email == null ? "" : email.trim();
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }

    @Override public String toString() { return name + " (#" + id + ")"; }
}
