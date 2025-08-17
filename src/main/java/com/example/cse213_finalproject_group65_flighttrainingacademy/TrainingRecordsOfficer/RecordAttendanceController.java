package com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer;

import com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer.Model.ClassSession;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.StringJoiner;

public class RecordAttendanceController {

    @FXML private Label sessionInfoLabel;
    @FXML private Label countsLabel;

    @FXML private TableView<AttendanceRow> rosterTable;
    @FXML private TableColumn<AttendanceRow, String>  colId;
    @FXML private TableColumn<AttendanceRow, String>  colName;
    @FXML private TableColumn<AttendanceRow, Boolean> colPresent;

    private ClassSession session;
    private final ObservableList<AttendanceRow> rows = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("traineeId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPresent.setCellValueFactory(cd -> cd.getValue().presentProperty());
        colPresent.setCellFactory(CheckBoxTableCell.forTableColumn(colPresent));

        rosterTable.setItems(rows);

        if (session == null) {
            sessionInfoLabel.setText("Session: (dummy) " + LocalDate.now());
            loadDummyRows();
        }
        updateCountsLabel();
    }

    public void setSession(ClassSession s) {
        this.session = s;
        if (s == null) return;

        sessionInfoLabel.setText("Session #" + s.getSessionId() + " | " + s.getDate());
        rows.setAll(new AttendanceRow(s.getTraineeId(), TraineeDirectory.nameFor(s.getTraineeId()), true));
        updateCountsLabel();
    }

    @FXML
    private void onSave() {
        if (rows.isEmpty()) {
            info("No roster data to save.");
            return;
        }
        StringJoiner sj = new StringJoiner("\n");
        sj.add("Attendance Summary:");
        for (AttendanceRow r : rows) {
            sj.add(r.getTraineeId() + " - " + r.getName() + " : " + (r.isPresent() ? "Present" : "Absent"));
        }
        info(sj.toString());
    }

    @FXML
    private void onCancel() {
        Stage st = (Stage) rosterTable.getScene().getWindow();
        if (st != null) st.close();
    }

    @FXML
    public void onBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/com/example/cse213_finalproject_group65_flighttrainingacademy/TrainingRecordsOfficer/TRODashboard.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("TRO Dashboard");
            stage.show();
        } catch (IOException e) {
            info("Could not load TRODashboard.fxml");
        }
    }

    private void loadDummyRows() {
        rows.setAll(
                new AttendanceRow("TP-001", "A. Rahman", true),
                new AttendanceRow("TP-002", "B. Islam", false),
                new AttendanceRow("TP-003", "C. Karim", true),
                new AttendanceRow("TP-004", "D. Hasan", false),
                new AttendanceRow("TP-005", "E. Chowdhury", true),
                new AttendanceRow("TP-006", "F. Alam", false)
        );
    }

    private void updateCountsLabel() {
        long present = rows.stream().filter(AttendanceRow::isPresent).count();
        long absent  = rows.size() - present;
        countsLabel.setText("Present: " + present + " | Absent: " + absent);
    }

    private void info(String m) {
        new Alert(Alert.AlertType.INFORMATION, m, ButtonType.OK).showAndWait();
    }

    public static class AttendanceRow {
        private final StringProperty traineeId = new SimpleStringProperty();
        private final StringProperty name      = new SimpleStringProperty();
        private final BooleanProperty present  = new SimpleBooleanProperty(false);

        public AttendanceRow(String traineeId, String name, boolean present) {
            this.traineeId.set(traineeId);
            this.name.set(name);
            this.present.set(present);
        }

        public String getTraineeId() { return traineeId.get(); }
        public String getName()      { return name.get(); }
        public boolean isPresent()   { return present.get(); }
        public StringProperty traineeIdProperty() { return traineeId; }
        public StringProperty nameProperty()      { return name; }
        public BooleanProperty presentProperty()  { return present; }
    }

    static class TraineeDirectory {
        static String nameFor(String id) {
            return switch (id) {
                case "TP-001" -> "A. Rahman";
                case "TP-002" -> "B. Islam";
                case "TP-003" -> "C. Karim";
                case "TP-004" -> "D. Hasan";
                case "TP-005" -> "E. Chowdhury";
                case "TP-006" -> "F. Alam";
                default -> "Unknown";
            };
        }
    }
}
