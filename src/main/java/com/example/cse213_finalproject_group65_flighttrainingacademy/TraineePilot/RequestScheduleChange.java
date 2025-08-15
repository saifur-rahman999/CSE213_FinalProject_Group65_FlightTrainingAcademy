package com.example.cse213_finalproject_group65_flighttrainingacademy.TraineePilot;

import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class RequestScheduleChange {
    @javafx.fxml.FXML
    private TextField newTimeTextField;
    @javafx.fxml.FXML
    private ComboBox assignedSessionComboBox;
    @javafx.fxml.FXML
    private Label requestStatusLabel;
    @javafx.fxml.FXML
    private DatePicker newDatePicker;
    @javafx.fxml.FXML
    private TextField reasonTextField;

    @javafx.fxml.FXML
    public void submitOnClick(ActionEvent actionEvent) {
    }
}
