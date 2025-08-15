package com.example.cse213_finalproject_group65_flighttrainingacademy.TraineePilot;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ProgressTracker {
    @javafx.fxml.FXML
    private TableColumn gradeCo;
    @javafx.fxml.FXML
    private BarChart flightHoursBarChart;
    @javafx.fxml.FXML
    private TableView moduleProgressTableView;
    @javafx.fxml.FXML
    private Label AttendanceLabel;
    @javafx.fxml.FXML
    private TableColumn statusCo;
    @javafx.fxml.FXML
    private Label flightHrsLabel;
    @javafx.fxml.FXML
    private TableColumn lastUpdatedCo;
    @javafx.fxml.FXML
    private ListView moduleChecklistListView;
    @javafx.fxml.FXML
    private PieChart attendancePieChart;
    @javafx.fxml.FXML
    private TableColumn moduleNameCo;
}
