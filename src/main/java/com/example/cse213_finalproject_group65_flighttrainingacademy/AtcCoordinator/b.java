package com.example.cse213_finalproject_group65_flighttrainingacademy.AtcCoordinator;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.*;

import java.time.LocalDate;
import java.time.LocalTime;

public class ATCSystemController {

    @FXML private ChoiceBox<String> roleChoiceBox;
    @FXML private TextField userIdField;

    @FXML private TextField flightIdField;
    @FXML private DatePicker scheduleDatePicker;
    @FXML private TextField departureTimeField;
    @FXML private TextField arrivalTimeField;
    @FXML private TableView<FlightSchedule> scheduleTable;
    @FXML private TableColumn<FlightSchedule,String> colFlightId;
    @FXML private TableColumn<FlightSchedule,LocalDate> colDate;
    @FXML private TableColumn<FlightSchedule,LocalTime> colDeparture;
    @FXML private TableColumn<FlightSchedule,LocalTime> colArrival;

    private ATCSystem system = new ATCSystem();
    private IUser currentUser;

    @FXML
    public void initialize(){
        roleChoiceBox.getItems().addAll("Operation Manager","ATC Coordinator");
        colFlightId.setCellValueFactory(new PropertyValueFactory<>("flightId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colDeparture.setCellValueFactory(new PropertyValueFactory<>("departureTime"));
        colArrival.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
    }

    public void handleLogin(){
        String id = userIdField.getText();
        String role = roleChoiceBox.getValue();

        if(role.equals("Operation Manager")){
            currentUser = new OperationManager(id,"OpManager");
        }else{
            currentUser = new ATCCoordinator(id,"Coordinator");
        }
        system.registerUser(currentUser);
        showMsg("Logged in as "+role);
    }

    public void handleLogout(){
        currentUser = null;
        showMsg("Logged out.");
    }

    public void handleScheduleFlight(){
        FlightSchedule fs = new FlightSchedule(
                "SCH"+System.currentTimeMillis(),
                flightIdField.getText(),
                scheduleDatePicker.getValue(),
                LocalTime.parse(departureTimeField.getText()),
                LocalTime.parse(arrivalTimeField.getText())
        );
        system.assignFlight(fs);
        scheduleTable.getItems().add(fs);
        showMsg("Flight scheduled ✔");
    }

    public void handleLogFlight(){
        showMsg("Flight log feature will be added next.");
    }

    public void handleCoordinationLog(){
        showMsg("Coordination feature will be added next.");
    }

    private void showMsg(String msg){
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText(msg);
        a.showAndWait();
    }
}
