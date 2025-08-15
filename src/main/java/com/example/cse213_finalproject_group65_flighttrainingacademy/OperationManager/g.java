package com.example.cse213_finalproject_group65_flighttrainingacademy.OperationManager;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.ATCSystem;
import model.FlightSchedule;
import model.OperationManager;

import java.time.LocalDate;
import java.time.LocalTime;

public class OperationManagerController {

    @FXML private TextField managerIdField;
    @FXML private TextField flightIdField;
    @FXML private DatePicker scheduleDatePicker;
    @FXML private TextField departureTimeField;
    @FXML private TextField arrivalTimeField;
    @FXML private TableView<FlightSchedule> scheduleTable;
    @FXML private TableColumn<FlightSchedule, String> colFlightId;
    @FXML private TableColumn<FlightSchedule, LocalDate> colDate;
    @FXML private TableColumn<FlightSchedule, LocalTime> colDeparture;
    @FXML private TableColumn<FlightSchedule, LocalTime> colArrival;

    private OperationManager manager;
    private ATCSystem system = new ATCSystem();

    @FXML
    public void initialize(){
        colFlightId.setCellValueFactory(new PropertyValueFactory<>("flightId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colDeparture.setCellValueFactory(new PropertyValueFactory<>("departureTime"));
        colArrival.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
    }

    public void handleLogin(){
        manager = new OperationManager(managerIdField.getText(), "Operation Manager");
        show("Logged in as Operation Manager");
    }

    public void handleLogout(){
        manager = null;
        show("Logged out.");
    }

    public void handleSchedule(){
        FlightSchedule fs = new FlightSchedule(
                flightIdField.getText(),
                scheduleDatePicker.getValue(),
                LocalTime.parse(departureTimeField.getText()),
                LocalTime.parse(arrivalTimeField.getText())
        );
        system.addSchedule(fs);
        scheduleTable.getItems().add(fs);
        show("Flight Scheduled!");
    }

    private void show(String m){
        new Alert(Alert.AlertType.INFORMATION, m).showAndWait();
    }
}