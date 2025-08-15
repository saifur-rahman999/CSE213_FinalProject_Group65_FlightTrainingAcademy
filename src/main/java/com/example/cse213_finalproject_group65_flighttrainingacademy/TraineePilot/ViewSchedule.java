package com.example.cse213_finalproject_group65_flighttrainingacademy.TraineePilot;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;

public class ViewSchedule {
    @javafx.fxml.FXML
    private HBox FilterHBox;
    @javafx.fxml.FXML
    private TableColumn flightTimeCo;
    @javafx.fxml.FXML
    private TableColumn flightDateCo;
    @javafx.fxml.FXML
    private TableColumn trauneeIDco;
    @javafx.fxml.FXML
    private TableView tableView;
    @javafx.fxml.FXML
    private TableColumn aircraftTypeCo;
    @javafx.fxml.FXML
    private TableColumn trainingLocCo;
    @javafx.fxml.FXML
    private Label dateRangeValidationLabel;
    @javafx.fxml.FXML
    private TableColumn sessionidco;
    @javafx.fxml.FXML
    private ComboBox sessionTypeComboBox;
    @javafx.fxml.FXML
    private DatePicker StartDatePicker;
    @javafx.fxml.FXML
    private DatePicker endDatePicker;
    @javafx.fxml.FXML
    private TableColumn sessionTypeCo;

    @javafx.fxml.FXML
    public void exportScheduleOnClick(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void ApplyFilterOnClick(ActionEvent actionEvent) {
    }
}
