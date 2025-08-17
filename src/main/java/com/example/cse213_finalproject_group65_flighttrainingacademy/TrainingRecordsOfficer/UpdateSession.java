package com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

public class UpdateSession {

    @FXML private TableView<Row> sessionsTable;
    @FXML private TableColumn<Row, Integer> idCol;
    @FXML private TableColumn<Row, String>  dateCol;
    @FXML private TableColumn<Row, String>  traineeCol;
    @FXML private TableColumn<Row, Integer> durationCol; // minutes
    @FXML private TableColumn<Row, String>  aircraftCol;
    @FXML private TableColumn<Row, String>  editedCol;
    @FXML private TableColumn<Row, String>  remarksCol;

    @FXML private Label statusLabel;

    private final ObservableList<Row> data = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Map columns to Row getters
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));             // simple String date
        traineeCol.setCellValueFactory(new PropertyValueFactory<>("trainee"));
        durationCol.setCellValueFactory(new PropertyValueFactory<>("durationMin")); // int minutes
        aircraftCol.setCellValueFactory(new PropertyValueFactory<>("aircraft"));
        editedCol.setCellValueFactory(new PropertyValueFactory<>("edited"));         // "Yes"/"No"
        remarksCol.setCellValueFactory(new PropertyValueFactory<>("remarks"));

        // Dummy rows
        data.addAll(
                new Row(101, "2025-08-04", "TP-001", 90,  "AC-C172-01", "No", "Circuit training"),
                new Row(102, "2025-08-05", "TP-002", 60,  "SIM-ATD-01", "No", "IFR basic"),
                new Row(103, "2025-08-06", "TP-003", 120, "AC-DA42-02", "No", "Nav exercise"),
                new Row(104, "2025-08-07", "TP-004", 45,  "SIM-FTD-02", "No", "Procedures"),
                new Row(105, "2025-08-08", "TP-005", 80,  "AC-C172-03", "No", "Crosswind")
        );

        sessionsTable.setItems(data);
        statusLabel.setText("Loaded " + data.size() + " sessions.");
    }

    @FXML
    public void editButtonOnAction(ActionEvent e) {
        Row target = sessionsTable.getSelectionModel().getSelectedItem();
        if (target == null && !data.isEmpty()) {
            target = data.get(0);
        }
        if (target == null) {
            info("Edit Session", "No session available to edit.");
            return;
        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Edit Session");
        dialog.setHeaderText("Update fields and click Update");

        ButtonType updateBtn = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(updateBtn, ButtonType.CANCEL);

        TextField dateField     = new TextField(target.getDate());
        TextField traineeField  = new TextField(target.getTrainee());
        TextField durationField = new TextField(String.valueOf(target.getDurationMin()));
        TextField aircraftField = new TextField(target.getAircraft());
        TextField remarksField  = new TextField(target.getRemarks());

        GridPane grid = new GridPane();
        grid.setHgap(8); grid.setVgap(8);
        grid.addRow(0, new Label("Date (YYYY-MM-DD):"), dateField);
        grid.addRow(1, new Label("Trainee:"),           traineeField);
        grid.addRow(2, new Label("Duration (min):"),    durationField);
        grid.addRow(3, new Label("Aircraft/Device:"),   aircraftField);
        grid.addRow(4, new Label("Remarks:"),           remarksField);
        dialog.getDialogPane().setContent(grid);

        var result = dialog.showAndWait();
        if (result.isPresent() && result.get() == updateBtn) {
            int mins;
            try {
                mins = Integer.parseInt(durationField.getText().trim());
                if (mins <= 0) throw new NumberFormatException();
            } catch (Exception ex) {
                info("Validation", "Duration must be a positive integer (minutes).");
                return;
            }

            target.setDate(dateField.getText().trim());
            target.setTrainee(traineeField.getText().trim());
            target.setDurationMin(mins);
            target.setAircraft(aircraftField.getText().trim());
            target.setRemarks(remarksField.getText().trim());
            target.setEdited("Yes");

            statusLabel.setText("Edited session #" + target.getId() + ". Click Reload to refresh table view.");
        }
    }


    @FXML
    public void reloadButtonOnAction(ActionEvent e) {
        sessionsTable.refresh();
        statusLabel.setText("Table reloaded.");
    }

    private void info(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        a.setTitle(title);
        a.setHeaderText(null);
        a.showAndWait();
    }

    // --- Minimal Row model (plain getters/setters to match PropertyValueFactory names) ---
    public static class Row {
        private int id;
        private String date;
        private String trainee;
        private int durationMin;
        private String aircraft;
        private String edited;   // "Yes"/"No"
        private String remarks;

        public Row(int id, String date, String trainee, int durationMin, String aircraft, String edited, String remarks) {
            this.id = id;
            this.date = date;
            this.trainee = trainee;
            this.durationMin = durationMin;
            this.aircraft = aircraft;
            this.edited = edited;
            this.remarks = remarks;
        }

        // --- getters (must match the PropertyValueFactory names) ---
        public int getId() { return id; }
        public String getDate() { return date; }
        public String getTrainee() { return trainee; }
        public int getDurationMin() { return durationMin; }
        public String getAircraft() { return aircraft; }
        public String getEdited() { return edited; }
        public String getRemarks() { return remarks; }

        // --- setters (used when updating) ---
        public void setId(int id) { this.id = id; }
        public void setDate(String date) { this.date = date; }
        public void setTrainee(String trainee) { this.trainee = trainee; }
        public void setDurationMin(int durationMin) { this.durationMin = durationMin; }
        public void setAircraft(String aircraft) { this.aircraft = aircraft; }
        public void setEdited(String edited) { this.edited = edited; }
        public void setRemarks(String remarks) { this.remarks = remarks; }
    }
}
