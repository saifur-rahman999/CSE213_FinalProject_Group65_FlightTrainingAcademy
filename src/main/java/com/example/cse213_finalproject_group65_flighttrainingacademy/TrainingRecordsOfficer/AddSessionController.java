package com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer;

import com.example.cse213_finalproject_group65_flighttrainingacademy.HelloApplication;
import com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer.Model.ClassSession;
import com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer.Model.DummyStore;
import com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer.Model.SessionType;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AddSessionController {

    @FXML private DatePicker datePicker;
    @FXML private ComboBox<SessionType> typeCombo;
    @FXML private ComboBox<String> traineeCombo;
    @FXML private ComboBox<String> aircraftCombo;
    @FXML private TextField durationField;
    @FXML private TextArea remarksArea;

    private final Random rng = new Random();

    @FXML
    public void initialize() {
        // Types (fixed)
        typeCombo.setItems(FXCollections.observableArrayList(SessionType.values()));
        typeCombo.getSelectionModel().select(SessionType.FLIGHT);

        // Randomize picklists on each load
        traineeCombo.setItems(FXCollections.observableArrayList(randomTrainees(5)));
        aircraftCombo.setItems(FXCollections.observableArrayList(randomAircraft(4)));

        // Optional: set today as default date
        datePicker.setValue(LocalDate.now());
    }

    private List<String> randomTrainees(int n) {
        List<String> list = new ArrayList<>(n);
        for (int i = 1; i <= n; i++) {
            int id = 100 + rng.nextInt(900); // 100..999
            list.add(String.format("TP-%03d", id));
        }
        return list;
    }

    private List<String> randomAircraft(int n) {
        String[] types = {"AC-C172", "AC-DA42", "SIM-ATD", "SIM-FTD"};
        List<String> list = new ArrayList<>(n);
        for (int i = 1; i <= n; i++) {
            String t = types[rng.nextInt(types.length)];
            int idx = 1 + rng.nextInt(9);
            list.add(String.format("%s-%02d", t, idx));
        }
        return list;
    }

    @FXML
    private void onSave() {
        LocalDate date = datePicker.getValue();
        if (date == null) { info("Validation", "Pick a date."); return; }

        SessionType type = typeCombo.getValue();
        String trainee = traineeCombo.getValue();
        String aircraft = aircraftCombo.getValue();
        if (trainee == null || aircraft == null) { info("Validation", "Select trainee and aircraft."); return; }

        double duration;
        try {
            duration = Double.parseDouble(durationField.getText().trim());
            if (duration <= 0) throw new NumberFormatException();
        } catch (Exception e) {
            info("Validation", "Duration must be > 0 (e.g., 1.5)");
            return;
        }

        String remarks = remarksArea.getText() == null ? "" : remarksArea.getText().trim();

        // Persist to in-memory DummyStore (kept as in your original)
        int nextId = DummyStore.sessions.size() + 1;
        ClassSession s = new ClassSession(nextId, date, type, trainee, aircraft, duration, remarks);
        DummyStore.sessions.add(s);

        // Show single popup with all inputs + success line
        StringBuilder sb = new StringBuilder();
        sb.append("Session ID (new): ").append(nextId).append('\n');
        sb.append("Date: ").append(date).append('\n');
        sb.append("Type: ").append(type).append('\n');
        sb.append("Trainee: ").append(trainee).append('\n');
        sb.append("Aircraft/Device: ").append(aircraft).append('\n');
        sb.append("Duration (hrs): ").append(duration).append('\n');
        sb.append("Remarks: ").append(remarks.isEmpty() ? "(none)" : remarks).append("\n\n");
        sb.append("session added successfully");

        info("Add Session", sb.toString());

        onClear();
    }

    @FXML
    private void onClear() {
        datePicker.setValue(LocalDate.now());
        typeCombo.getSelectionModel().select(SessionType.FLIGHT);
        traineeCombo.getSelectionModel().clearSelection();
        aircraftCombo.getSelectionModel().clearSelection();
        durationField.clear();
        remarksArea.clear();
    }

    private void info(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }

    @FXML
    public void onBack(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    HelloApplication.class.getResource("TrainingRecordsOfficer/TRODashboard.fxml")
            );
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception ex) {
            info("Navigation Error", "Could not load TRODashBoard.fxml\n\n" + ex.getMessage());
        }
    }
}
