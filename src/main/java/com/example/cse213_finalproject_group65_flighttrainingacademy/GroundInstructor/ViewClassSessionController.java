package com.example.cse213_finalproject_group65_flighttrainingacademy.GroundInstructor;

import javafx.event.ActionEvent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ViewClassSessionController
{
    @javafx.fxml.FXML
    private TableColumn colTime;
    @javafx.fxml.FXML
    private TableColumn colTopic;
    @javafx.fxml.FXML
    private DatePicker datePicker;
    @javafx.fxml.FXML
    private TableView sessionTableView;
    @javafx.fxml.FXML
    private TableColumn colCourseId;
    @javafx.fxml.FXML
    private TableColumn colDate;

    @javafx.fxml.FXML
    public void initialize() {
    }

    @javafx.fxml.FXML
    public void searchButtonOnAction(ActionEvent actionEvent) {
    }
}