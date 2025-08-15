package com.example.cse213_finalproject_group65_flighttrainingacademy.TraineePilot;

import javafx.event.ActionEvent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ScheduleTrainingSession {
    @javafx.fxml.FXML
    private DatePicker flightDateDatePicker;
    @javafx.fxml.FXML
    private TextField flightTimeTextField;
    @javafx.fxml.FXML
    private TextField trainingTopicTextField;
    @javafx.fxml.FXML
    private TextField aircraftTypeTextField;
    @javafx.fxml.FXML
    private ListView traineeListView;
    @javafx.fxml.FXML
    private Label validationMessageLabel;

    @javafx.fxml.FXML
    public void SubmitOnClick(ActionEvent actionEvent) {
    }
}
