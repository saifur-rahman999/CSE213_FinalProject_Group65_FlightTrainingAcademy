package com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer;

import com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer.Model.ClassSession;
import com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer.Model.DummyStore;
import com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer.Model.SessionType;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class AddSessionController {

    @FXML private DatePicker datePicker;
    @FXML private ComboBox<SessionType> typeCombo;
    @FXML private ComboBox<String> traineeCombo;
    @FXML private ComboBox<String> aircraftCombo;
    @FXML private TextField durationField;
    @FXML private TextArea remarksArea;

    @FXML
    public void initialize() {
        typeCombo.setItems(FXCollections.observableArrayList(SessionType.values()));
        typeCombo.getSelectionModel().select(SessionType.FLIGHT);

        // dummy picklists
        traineeCombo.setItems(FXCollections.observableArrayList("TP-001","TP-002","TP-003"));
        aircraftCombo.setItems(FXCollections.observableArrayList("AC-C172-01","SIM-ATD-01"));
    }

    @FXML
    private void onSave() {
        LocalDate date = datePicker.getValue();
        if (date == null) { alert("Pick a date."); return; }

        String trainee = traineeCombo.getValue();
        String aircraft = aircraftCombo.getValue();
        if (trainee == null || aircraft == null) { alert("Select trainee and aircraft."); return; }

        double duration;
        try {
            duration = Double.parseDouble(durationField.getText().trim());
            if (duration <= 0) throw new NumberFormatException();
        } catch (Exception e) {
            alert("Duration must be >0");
            return;
        }

        String remarks = remarksArea.getText() == null ? "" : remarksArea.getText().trim();

        // create dummy session in memory
        int nextId = DummyStore.sessions.size() + 1;
        ClassSession s = new ClassSession(nextId, date, typeCombo.getValue(), trainee, aircraft, duration, remarks);
        DummyStore.sessions.add(s);

        alert("Session added (dummy), id=" + nextId);
        onClear();
    }

    @FXML
    private void onClear() {
        datePicker.setValue(null);
        typeCombo.getSelectionModel().select(SessionType.FLIGHT);
        traineeCombo.getSelectionModel().clearSelection();
        aircraftCombo.getSelectionModel().clearSelection();
        durationField.clear();
        remarksArea.clear();
    }

    private void alert(String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK).showAndWait();
    }
}
