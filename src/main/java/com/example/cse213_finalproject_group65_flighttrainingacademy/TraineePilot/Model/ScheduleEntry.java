package com.example.cse213_finalproject_group65_flighttrainingacademy.TraineePilot.Model;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ScheduleEntry {
    private final StringProperty sessionId;
    private final StringProperty flightDate;
    private final StringProperty flightTime;
    private final StringProperty sessionType;
    private final StringProperty traineeId;
    private final StringProperty aircraftType;
    private final StringProperty trainingLoc;

    private final LocalDate flightDateValue;

    public ScheduleEntry(String sessionId, LocalDate flightDate, LocalTime flightTime,
                         String sessionType, String traineeId, String aircraftType, String trainingLoc) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter tf = DateTimeFormatter.ofPattern("HH:mm");

        this.sessionId = new SimpleStringProperty(sessionId);
        this.flightDate = new SimpleStringProperty(df.format(flightDate));
        this.flightTime = new SimpleStringProperty(tf.format(flightTime));
        this.sessionType = new SimpleStringProperty(sessionType);
        this.traineeId = new SimpleStringProperty(traineeId);
        this.aircraftType = new SimpleStringProperty(aircraftType);
        this.trainingLoc = new SimpleStringProperty(trainingLoc);

        this.flightDateValue = flightDate;
    }

    // Getters
    public String getSessionId() { return sessionId.get(); }
    public String getFlightDate() { return flightDate.get(); }
    public String getFlightTime() { return flightTime.get(); }
    public String getSessionType() { return sessionType.get(); }
    public String getTraineeId() { return traineeId.get(); }
    public String getAircraftType() { return aircraftType.get(); }
    public String getTrainingLoc() { return trainingLoc.get(); }
    public LocalDate getFlightDateValue() { return flightDateValue; }

    // Properties for TableView binding
    public StringProperty sessionIdProperty() { return sessionId; }
    public StringProperty flightDateProperty() { return flightDate; }
    public StringProperty flightTimeProperty() { return flightTime; }
    public StringProperty sessionTypeProperty() { return sessionType; }
    public StringProperty traineeIdProperty() { return traineeId; }
    public StringProperty aircraftTypeProperty() { return aircraftType; }
    public StringProperty trainingLocProperty() { return trainingLoc; }
}

