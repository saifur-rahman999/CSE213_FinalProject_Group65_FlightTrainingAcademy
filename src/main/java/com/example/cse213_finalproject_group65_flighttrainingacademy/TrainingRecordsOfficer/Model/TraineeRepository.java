package com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Iterator;

public class TraineeRepository {
    private static final TraineeRepository INSTANCE = new TraineeRepository();

    private final ObservableList<Trainee> active = FXCollections.observableArrayList();
    private final ObservableList<ArchivedTrainee> archived = FXCollections.observableArrayList();

    private TraineeRepository() {
        // Seed demo data
        active.addAll(
                new Trainee("TR-1001", "Nabila Rahman", "PPL", "Active", 0),
                new Trainee("TR-1002", "Ahsan Karim", "CPL", "Active", 2),
                new Trainee("TR-1003", "Riad Ahmed", "IR",  "Active", 0)
        );
    }

    public static TraineeRepository getInstance() { return INSTANCE; }

    public ObservableList<Trainee> getActiveTrainees() { return active; }
    public ObservableList<ArchivedTrainee> getArchivedTrainees() { return archived; }

    public boolean isCurrentlyActive(String traineeId) {
        return active.stream().anyMatch(t -> t.getId().equals(traineeId));
    }

    public boolean hasPendingAssessments(String traineeId) {
        // TODO: hook into your assessments store; demo: if openSessions>0, we assume pending
        return active.stream()
                .filter(t -> t.getId().equals(traineeId))
                .anyMatch(t -> t.getOpenSessions() > 0);
    }

    public boolean hasFinanceHold(String traineeId) {
        // TODO: connect to finance subsystem; demo: false
        return false;
    }

    public void moveToArchive(String traineeId, ArchivedTrainee snapshot) {
        // remove from active
        for (Iterator<Trainee> it = active.iterator(); it.hasNext(); ) {
            if (it.next().getId().equals(traineeId)) {
                it.remove();
                break;
            }
        }
        // add to archive
        archived.add(snapshot);

        // TODO (optional): write to files
        // BinStore.append("archive/trainees.bin", snapshot);
        // and move/summary sessions as needed
    }
}
