package com.example.cse213_finalproject_group65_flighttrainingacademy.AtcCoordinator;

import com.example.cse213_finalproject_group65_flighttrainingacademy.AtcCoordinator.model.ATCCoordinator;
import com.example.cse213_finalproject_group65_flighttrainingacademy.AtcCoordinator.model.ATCSystem;
import com.example.cse213_finalproject_group65_flighttrainingacademy.AtcCoordinator.model.FlightSchedule;
import com.example.cse213_finalproject_group65_flighttrainingacademy.AtcCoordinator.model.IUser;
import com.example.cse213_finalproject_group65_flighttrainingacademy.OperationManager.model.OperationManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.LocalTime;

public class ATCSystemController {

    @FXML
    private ChoiceBox<String> roleChoiceBox;
    @FXML private TextField userIdField;

    @FXML private TextField flightIdField;
    @FXML private DatePicker scheduleDatePicker;
    @FXML private TextField departureTimeField;
    @FXML private TextField arrivalTimeField;
    @FXML private TableView<FlightSchedule> scheduleTable;

    private ATCSystem system = new ATCSystem();
    private IUser currentUser;

    @FXML
    public void initialize() {
        roleChoiceBox.getItems().addAll("Operation Manager", "ATC Coordinator");
    }

    public void handleLogin() {
        String role = roleChoiceBox.getValue();
        String id = userIdField.getText();

        if (role.equals("Operation Manager")) {
            currentUser = new IUser() {
                @Override
                public boolean login() {
                    return false;
                }

                @Override
                public void logout() {

                }
            };
        } else {
            currentUser = new ATCCoordinator(id, "Coordinator");
        }

        system.registerUser(currentUser);
        showMessage("Login successful as " + role);
    }

    public void handleLogout() {
        currentUser = null;
        showMessage("Logged out.");
    }

    public void handleScheduleFlight() {
        String flightId = flightIdField.getText();
        LocalDate date = scheduleDatePicker.getValue();
        LocalTime depTime = LocalTime.parse(departureTimeField.getText());
        LocalTime arrTime = LocalTime.parse(arrivalTimeField.getText());

        FlightSchedule fs = new FlightSchedule(
                "SCH" + System.currentTimeMillis(),
                flightId, date, depTime, arrTime
        );

        system.assignFlight(fs);
        scheduleTable.getItems().add(fs);

        showMessage("Flight scheduled!");
    }

    private void showMessage(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public void handleLogFlight(ActionEvent actionEvent) {
    }

    public void handleCoordinationLog(ActionEvent actionEvent) {
    }
}