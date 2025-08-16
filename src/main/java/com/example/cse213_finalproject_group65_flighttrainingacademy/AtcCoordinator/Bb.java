package com.example.cse213_finalproject_group65_flighttrainingacademy.AtcCoordinator;

import javafx.event.ActionEvent;
import javafx.scene.control.*;

public class Bb {
    public ChoiceBox roleChoiceBox;
    public TextField userIdField;
    public TabPane mainTabPane;
    public TextField flightIdField;
    public DatePicker scheduleDatePicker;
    public TextField departureTimeField;
    public TextField arrivalTimeField;
    public TableView scheduleTable;
    public TableColumn colFlightId;
    public TableColumn colDate;
    public TableColumn colDeparture;
    public TableColumn colArrival;
    public TextField logFlightIdField;
    public TextField routeField;
    public TextField remarksField;
    public TextField coordinationIdField;
    public TextField involvedFlightsField;
    public TextField notesField;
    private ActionEvent actionEvent;

    public void handleLogin(ActionEvent actionEvent) {
        this.actionEvent = actionEvent;
    }

    public void handleLogout(ActionEvent actionEvent) {
        this.actionEvent = actionEvent;
    }

    public void handleScheduleFlight(ActionEvent actionEvent) {
        this.actionEvent = actionEvent;
    }

    public void handleLogFlight(ActionEvent actionEvent) {
        this.actionEvent = actionEvent;
    }

    public void handleCoordinationLog(ActionEvent actionEvent) {
        this.actionEvent = actionEvent;
    }

    public ActionEvent getActionEvent() {
        return actionEvent;
    }

    public void setActionEvent(ActionEvent actionEvent) {
        this.actionEvent = actionEvent;
    }
}
