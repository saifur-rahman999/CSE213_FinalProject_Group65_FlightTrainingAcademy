package com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer.Model;

import java.io.Serializable;
import java.time.LocalDate;

public class Session implements Serializable {
    private final int id;
    private final String title;
    private final LocalDate date;

    public Session(int id, String title, LocalDate date) {
        this.id = id; this.title = title; this.date = date;
    }
    public int getId() { return id; }
    public String getTitle() { return title; }
    public LocalDate getDate() { return date; }
    public String getDateString(){ return date==null? "": date.toString(); }

    @Override public String toString() {
        return title + " — " + getDateString();
    }
}
