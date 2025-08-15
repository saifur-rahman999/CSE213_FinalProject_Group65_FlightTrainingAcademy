package com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer.Model;

import javafx.beans.property.*;

public class Trainee {
    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty course = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();      // "Active"
    private final IntegerProperty openSessions = new SimpleIntegerProperty();

    public Trainee(String id, String name, String course, String status, int openSessions) {
        this.id.set(id);
        this.name.set(name);
        this.course.set(course);
        this.status.set(status);
        this.openSessions.set(openSessions);
    }

    public String getId() { return id.get(); }
    public StringProperty idProperty() { return id; }

    public String getName() { return name.get(); }
    public StringProperty nameProperty() { return name; }

    public String getCourse() { return course.get(); }
    public StringProperty courseProperty() { return course; }

    public String getStatus() { return status.get(); }
    public StringProperty statusProperty() { return status; }

    public int getOpenSessions() { return openSessions.get(); }
    public IntegerProperty openSessionsProperty() { return openSessions; }

    public void setOpenSessions(int v) { this.openSessions.set(v); }
}
