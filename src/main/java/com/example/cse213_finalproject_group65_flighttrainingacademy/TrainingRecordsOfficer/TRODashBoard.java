package com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer;

import javafx.event.ActionEvent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

public class TRODashBoard {
    @javafx.fxml.FXML
    private PieChart statusPieChart;
    @javafx.fxml.FXML
    private DatePicker fromDatePicker;
    @javafx.fxml.FXML
    private DatePicker toDatePicker;
    @javafx.fxml.FXML
    private ToggleGroup navToggleGroup;
    @javafx.fxml.FXML
    private Label statusBarLabel;
    @javafx.fxml.FXML
    private Label activeTraineesLabel;
    @javafx.fxml.FXML
    private TableColumn hoursCol;
    @javafx.fxml.FXML
    private TabPane mainTabPane;
    @javafx.fxml.FXML
    private Label flightsThisMonthLabel;
    @javafx.fxml.FXML
    private TableColumn lastFlightCol;
    @javafx.fxml.FXML
    private TextField globalSearchField;
    @javafx.fxml.FXML
    private ComboBox statusFilterCombo;
    @javafx.fxml.FXML
    private TableColumn licenseCol;
    @javafx.fxml.FXML
    private Label certsDueLabel;
    @javafx.fxml.FXML
    private TableColumn idCol;
    @javafx.fxml.FXML
    private ComboBox licenseFilterCombo;
    @javafx.fxml.FXML
    private TableColumn statusCol;
    @javafx.fxml.FXML
    private BarChart hoursBarChart;
    @javafx.fxml.FXML
    private TableColumn nameCol;
    @javafx.fxml.FXML
    private ProgressIndicator busyIndicator;
    @javafx.fxml.FXML
    private TableView traineeTable;

    @javafx.fxml.FXML
    public void onNavReports(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void onOpenProfile(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void onBackupData(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void onNavFlights(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void onExportCsv(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void onExportPdf(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void onRestoreData(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void onNewRecord(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void onResetFilters(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void onSignOut(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void onRefreshTable(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void onDeleteSelected(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void onNavCertificates(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void onNavTrainees(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void onEditSelected(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void onApplyFilters(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void onNavInstructors(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void onNavOverview(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void onOpenSettings(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void onGlobalSearch(ActionEvent actionEvent) {
    }
}
