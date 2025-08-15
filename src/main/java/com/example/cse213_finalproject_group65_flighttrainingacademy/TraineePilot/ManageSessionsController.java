package com.example.cse213_finalproject_group65_flighttrainingacademy.TraineePilot;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.LocalDateStringConverter;
import javafx.util.converter.DefaultStringConverter;

import java.time.LocalDate;

public class ManageSessionsController {

    @FXML private TableView<Session> sessionsTableView;
    @FXML private TableColumn<Session, Integer> sessionIdColumn;
    @FXML private TableColumn<Session, String> sessionTypeColumn;
    @FXML private TableColumn<Session, String> locationColumn;
    @FXML private TableColumn<Session, LocalDate> flightDateColumn;
    @FXML private TableColumn<Session, String> aircraftColumn;
    @FXML private TableColumn<Session, String> traineeBatchColumn;
    @FXML private TableColumn<Session, String> flightTimeColumn;

    @FXML private Button refreshButton;
    @FXML private Button addSessionButton;
    @FXML private Button deleteSessionButton;
    @FXML private Button editSessionButton; // Not really needed since we enable inline editing

    private ObservableList<Session> sessionList;

    @FXML
    public void initialize() {
        // Configure table columns
        sessionIdColumn.setCellValueFactory(new PropertyValueFactory<>("sessionId"));
        sessionTypeColumn.setCellValueFactory(new PropertyValueFactory<>("sessionType"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        flightDateColumn.setCellValueFactory(new PropertyValueFactory<>("flightDate"));
        aircraftColumn.setCellValueFactory(new PropertyValueFactory<>("aircraft"));
        traineeBatchColumn.setCellValueFactory(new PropertyValueFactory<>("traineeBatch"));
        flightTimeColumn.setCellValueFactory(new PropertyValueFactory<>("flightTime"));

        // Enable editing
        sessionsTableView.setEditable(true);
        sessionTypeColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
        locationColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
        flightDateColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
        aircraftColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
        traineeBatchColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
        flightTimeColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));

        // Save edits
        sessionTypeColumn.setOnEditCommit(e -> e.getRowValue().setSessionType(e.getNewValue()));
        locationColumn.setOnEditCommit(e -> e.getRowValue().setLocation(e.getNewValue()));
        flightDateColumn.setOnEditCommit(e -> e.getRowValue().setFlightDate(e.getNewValue()));
        aircraftColumn.setOnEditCommit(e -> e.getRowValue().setAircraft(e.getNewValue()));
        traineeBatchColumn.setOnEditCommit(e -> e.getRowValue().setTraineeBatch(e.getNewValue()));
        flightTimeColumn.setOnEditCommit(e -> e.getRowValue().setFlightTime(e.getNewValue()));

        // Load initial data
        loadDefaultData();
    }

    private void loadDefaultData() {
        sessionList = FXCollections.observableArrayList(
                new Session(1, "Simulator", "Dhaka", LocalDate.of(2025, 8, 20), "Cessna 172", "Batch A", "10:00 AM"),
                new Session(2, "Navigation", "Chittagong", LocalDate.of(2025, 8, 25), "Piper PA-28", "Batch B", "02:00 PM"),
                new Session(3, "Landing Practice", "Sylhet", LocalDate.of(2025, 8, 28), "Beechcraft Bonanza", "Batch C", "09:00 AM")
        );
        sessionsTableView.setItems(sessionList);
    }

    @FXML
    private void onAddSession() {
        int newId = sessionList.size() + 1;
        sessionList.add(new Session(newId, "New Session", "Location", LocalDate.now(), "Aircraft", "Batch X", "Time"));
    }

    @FXML
    private void onDeleteSession() {
        Session selected = sessionsTableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            sessionList.remove(selected);
        } else {
            showAlert("No Selection", "Please select a session to delete.");
        }
    }

    @FXML
    private void onRefresh() {
        loadDefaultData();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Session model class
    public static class Session {
        private Integer sessionId;
        private String sessionType;
        private String location;
        private LocalDate flightDate;
        private String aircraft;
        private String traineeBatch;
        private String flightTime;

        public Session(Integer sessionId, String sessionType, String location, LocalDate flightDate,
                       String aircraft, String traineeBatch, String flightTime) {
            this.sessionId = sessionId;
            this.sessionType = sessionType;
            this.location = location;
            this.flightDate = flightDate;
            this.aircraft = aircraft;
            this.traineeBatch = traineeBatch;
            this.flightTime = flightTime;
        }

        public Integer getSessionId() { return sessionId; }
        public void setSessionId(Integer sessionId) { this.sessionId = sessionId; }

        public String getSessionType() { return sessionType; }
        public void setSessionType(String sessionType) { this.sessionType = sessionType; }

        public String getLocation() { return location; }
        public void setLocation(String location) { this.location = location; }

        public LocalDate getFlightDate() { return flightDate; }
        public void setFlightDate(LocalDate flightDate) { this.flightDate = flightDate; }

        public String getAircraft() { return aircraft; }
        public void setAircraft(String aircraft) { this.aircraft = aircraft; }

        public String getTraineeBatch() { return traineeBatch; }
        public void setTraineeBatch(String traineeBatch) { this.traineeBatch = traineeBatch; }

        public String getFlightTime() { return flightTime; }
        public void setFlightTime(String flightTime) { this.flightTime = flightTime; }
    }
}
