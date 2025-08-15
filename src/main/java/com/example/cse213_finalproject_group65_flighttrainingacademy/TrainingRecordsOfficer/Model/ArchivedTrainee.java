package com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer.Model;

import javafx.beans.property.*;

public class ArchivedTrainee {
    private final StringProperty traineeId = new SimpleStringProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty course = new SimpleStringProperty();
    private final StringProperty finalStatus = new SimpleStringProperty();   // "Completed"/"Withdrawn"
    private final StringProperty archiveDate = new SimpleStringProperty();   // store as yyyy‑MM‑dd for table
    private final StringProperty note = new SimpleStringProperty();

    public ArchivedTrainee(String traineeId, String name, String course,
                           String finalStatus, java.time.LocalDate archiveDate, String note) {
        this.traineeId.set(traineeId);
        this.name.set(name);
        this.course.set(course);
        this.finalStatus.set(finalStatus);
        this.archiveDate.set(archiveDate.toString());
        this.note.set(note);
    }

    public StringProperty traineeIdProperty() { return traineeId; }
    public StringProperty nameProperty() { return name; }
    public StringProperty courseProperty() { return course; }
    public StringProperty finalStatusProperty() { return finalStatus; }
    public StringProperty archiveDateProperty() { return archiveDate; }
    public StringProperty noteProperty() { return note; }
}
