package com.example.cse213_finalproject_group65_flighttrainingacademy.TraineePilot;

import com.example.cse213_finalproject_group65_flighttrainingacademy.TraineePilot.Model.ScheduleEntry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.LocalTime;

public class ViewSchedule {

    @FXML private TableView<ScheduleEntry> tableView;
    @FXML private TableColumn<ScheduleEntry, String> sessionidco;
    @FXML private TableColumn<ScheduleEntry, String> flightDateCo;
    @FXML private TableColumn<ScheduleEntry, String> flightTimeCo;
    @FXML private TableColumn<ScheduleEntry, String> sessionTypeCo;
    @FXML private TableColumn<ScheduleEntry, String> trauneeIDco;
    @FXML private TableColumn<ScheduleEntry, String> aircraftTypeCo;
    @FXML private TableColumn<ScheduleEntry, String> trainingLocCo;

    @FXML private ComboBox<String> sessionTypeComboBox;
    @FXML private DatePicker StartDatePicker;
    @FXML private DatePicker endDatePicker;
    @FXML private Label dateRangeValidationLabel;

    private ObservableList<ScheduleEntry> masterList;
    private FilteredList<ScheduleEntry> filteredList;

    @FXML
    public void initialize() {
        // Bind columns to model properties
        sessionidco.setCellValueFactory(c -> c.getValue().sessionIdProperty());
        flightDateCo.setCellValueFactory(c -> c.getValue().flightDateProperty());
        flightTimeCo.setCellValueFactory(c -> c.getValue().flightTimeProperty());
        sessionTypeCo.setCellValueFactory(c -> c.getValue().sessionTypeProperty());
        trauneeIDco.setCellValueFactory(c -> c.getValue().traineeIdProperty());
        aircraftTypeCo.setCellValueFactory(c -> c.getValue().aircraftTypeProperty());
        trainingLocCo.setCellValueFactory(c -> c.getValue().trainingLocProperty());

        // Load dummy data
        masterList = FXCollections.observableArrayList(
                new ScheduleEntry("S1", LocalDate.now().plusDays(1), LocalTime.of(9,0), "Flight", "TP01", "Cessna 172", "Dhaka"),
                new ScheduleEntry("S2", LocalDate.now().plusDays(2), LocalTime.of(14,30), "Simulator", "TP02", "A320 Sim", "Simulator Bay"),
                new ScheduleEntry("S3", LocalDate.now().plusDays(3), LocalTime.of(10,0), "Ground", "Batch-1", "-", "Classroom B")
        );

        filteredList = new FilteredList<>(masterList, p -> true);
        tableView.setItems(filteredList);

        sessionTypeComboBox.setItems(FXCollections.observableArrayList("All", "Flight", "Simulator", "Ground"));
        sessionTypeComboBox.getSelectionModel().selectFirst();
    }

    @FXML
    public void ApplyFilterOnClick() {
        String type = sessionTypeComboBox.getValue();
        LocalDate start = StartDatePicker.getValue();
        LocalDate end = endDatePicker.getValue();

        if (start != null && end != null && end.isBefore(start)) {
            dateRangeValidationLabel.setText("End date cannot be before start date!");
            return;
        }
        dateRangeValidationLabel.setText("");

        filteredList.setPredicate(entry -> {
            boolean typeMatch = type.equals("All") || entry.getSessionType().equalsIgnoreCase(type);
            boolean startOk = (start == null) || !entry.getFlightDateValue().isBefore(start);
            boolean endOk = (end == null) || !entry.getFlightDateValue().isAfter(end);
            return typeMatch && startOk && endOk;
        });
    }

    @FXML
    public void exportScheduleOnClick() {
        StringBuilder sb = new StringBuilder("Exporting Schedule:\n\n");
        for (ScheduleEntry entry : tableView.getItems()) {
            sb.append(entry.getSessionId()).append(" | ")
                    .append(entry.getFlightDate()).append(" | ")
                    .append(entry.getFlightTime()).append(" | ")
                    .append(entry.getSessionType()).append(" | ")
                    .append(entry.getTraineeId()).append(" | ")
                    .append(entry.getAircraftType()).append(" | ")
                    .append(entry.getTrainingLoc()).append("\n");
        }
        sb.append("\nExport Success!");

        Alert alert = new Alert(Alert.AlertType.INFORMATION, sb.toString(), ButtonType.OK);
        alert.setTitle("Export Schedule");
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
