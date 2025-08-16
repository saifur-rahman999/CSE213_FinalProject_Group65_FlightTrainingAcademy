package com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer;


import com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer.Model.ClassSession;
import com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer.Model.DummyStore;
import com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer.Model.SessionType;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
        import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;

public class SessionsTable {

    @FXML private TableView<ClassSession> table;
    @FXML private TableColumn<ClassSession, Integer> colId;
    @FXML private TableColumn<ClassSession, LocalDate> colDate;
    @FXML private TableColumn<ClassSession, SessionType> colType;
    @FXML private TableColumn<ClassSession, String> colTrainee;
    @FXML private TableColumn<ClassSession, String> colEquip;
    @FXML private TableColumn<ClassSession, Double> colDur;
    @FXML private TableColumn<ClassSession, String> colRemarks;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("sessionId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colTrainee.setCellValueFactory(new PropertyValueFactory<>("traineeId"));
        colEquip.setCellValueFactory(new PropertyValueFactory<>("aircraftOrDeviceId"));
        colDur.setCellValueFactory(new PropertyValueFactory<>("durationHours"));
        colRemarks.setCellValueFactory(new PropertyValueFactory<>("remarks"));

        table.setItems(FXCollections.observableArrayList(DummyStore.sessions));
    }

    @FXML
    private void onEdit() {
        ClassSession sel = table.getSelectionModel().getSelectedItem();
        if (sel == null) {
            new Alert(Alert.AlertType.INFORMATION, "Select a row to edit.", ButtonType.OK).showAndWait();
            return;
        }
        EditSessions.openDialog(sel);
        table.refresh(); // reflect in-memory edits
    }

    @FXML
    private void onSeed() {
        if (DummyStore.sessions.isEmpty()) {
            DummyStore.sessions.add(new ClassSession(1, LocalDate.now().minusDays(2), SessionType.FLIGHT,
                    "TP-001","AC-C172-01",1.2,"Initial"));
            DummyStore.sessions.add(new ClassSession(2, LocalDate.now().minusDays(1), SessionType.SIMULATOR,
                    "TP-002","SIM-ATD-01",1.0,"IFR"));
            table.getItems().setAll(DummyStore.sessions);
        }
    }
}
