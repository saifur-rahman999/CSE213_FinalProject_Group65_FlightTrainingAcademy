package com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.Model;

import java.io.Serializable;
import java.util.Objects;

public class ApplicationModel implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String appId;
    private final String name;
    private final String email;
    private final String phone;
    private final String course;
    private final String intake;
    private final String notes;
    private String status; // Pending, Active, etc.

    public ApplicationModel(String appId, String name, String email, String phone,
                            String course, String intake, String notes, String status) {
        this.appId = appId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.course = course;
        this.intake = intake;
        this.notes = notes;
        this.status = status;
    }

    public String getAppId() { return appId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getCourse() { return course; }
    public String getIntake() { return intake; }
    public String getNotes() { return notes; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "ApplicationModel{" +
                "appId='" + appId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", course='" + course + '\'' +
                ", intake='" + intake + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ApplicationModel)) return false;
        ApplicationModel that = (ApplicationModel) o;
        return Objects.equals(appId, that.appId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appId);
    }
}
