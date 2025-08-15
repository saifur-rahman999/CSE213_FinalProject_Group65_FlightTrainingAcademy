package com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.Model;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serial;
import java.io.Serializable;

public class RosterRecord implements Serializable {
    @Serial private static final long serialVersionUID = 1L;

    private String traineeId;
    private String name;
    private EnrollmentStatus status;
    private String lastNote;

    public RosterRecord() { }

    public RosterRecord(String traineeId, String name, EnrollmentStatus status, String lastNote) {
        this.traineeId = traineeId;
        this.name = name;
        this.status = status;
        this.lastNote = lastNote;
    }

    public String getTraineeId() { return traineeId; }
    public void setTraineeId(String traineeId) { this.traineeId = traineeId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public EnrollmentStatus getStatus() { return status; }
    public void setStatus(EnrollmentStatus status) { this.status = status; }

    public String getLastNote() { return lastNote; }
    public void setLastNote(String lastNote) { this.lastNote = lastNote; }

    // Convenience for TableColumn bindings (kept super simple)
    public StringProperty traineeIdProperty() { return new SimpleStringProperty(getTraineeId()); }
    public StringProperty nameProperty() { return new SimpleStringProperty(getName()); }
    public StringProperty statusProperty() { return new SimpleStringProperty(String.valueOf(getStatus())); }
    public StringProperty lastNoteProperty() { return new SimpleStringProperty(getLastNote()); }
}
