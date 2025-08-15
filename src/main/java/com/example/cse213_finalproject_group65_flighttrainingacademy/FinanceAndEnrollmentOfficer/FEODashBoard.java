package com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class FEODashBoard {
    @javafx.fxml.FXML
    private DatePicker fromDatePicker;
    @javafx.fxml.FXML
    private Label outstandingBalanceLabel;
    @javafx.fxml.FXML
    private TableColumn sumValueCol;
    @javafx.fxml.FXML
    private DatePicker toDatePicker;
    @javafx.fxml.FXML
    private TabPane mainTabPane;
    @javafx.fxml.FXML
    private Label newAppsLabel;
    @javafx.fxml.FXML
    private ListView alertsListView;
    @javafx.fxml.FXML
    private TableColumn intakeCol;
    @javafx.fxml.FXML
    private TableColumn invAppIdCol;
    @javafx.fxml.FXML
    private TableColumn payMethodCol;
    @javafx.fxml.FXML
    private ComboBox intakeFilterCombo;
    @javafx.fxml.FXML
    private TableColumn payInvIdCol;
    @javafx.fxml.FXML
    private TableView invoicesTable;
    @javafx.fxml.FXML
    private TableView summaryTable;
    @javafx.fxml.FXML
    private TableColumn payAmountCol;
    @javafx.fxml.FXML
    private TableColumn nameCol;
    @javafx.fxml.FXML
    private TableColumn payDateCol;
    @javafx.fxml.FXML
    private ToggleGroup navToggleGroup;
    @javafx.fxml.FXML
    private TableColumn appIdCol;
    @javafx.fxml.FXML
    private TableColumn emailCol;
    @javafx.fxml.FXML
    private TableColumn createdCol;
    @javafx.fxml.FXML
    private ProgressIndicator busyIndicator;
    @javafx.fxml.FXML
    private Label statusBarLabel;
    @javafx.fxml.FXML
    private TableView applicationsTable;
    @javafx.fxml.FXML
    private TableColumn invIdCol;
    @javafx.fxml.FXML
    private TableColumn invPaidCol;
    @javafx.fxml.FXML
    private Label paymentsThisMonthLabel;
    @javafx.fxml.FXML
    private TableColumn invDueCol;
    @javafx.fxml.FXML
    private ComboBox summaryPeriodCombo;
    @javafx.fxml.FXML
    private TextField globalSearchField;
    @javafx.fxml.FXML
    private TableColumn invAmountCol;
    @javafx.fxml.FXML
    private ComboBox statusFilterCombo;
    @javafx.fxml.FXML
    private TableColumn payIdCol;
    @javafx.fxml.FXML
    private TableColumn sumMetricCol;
    @javafx.fxml.FXML
    private Label activeEnrollmentsLabel;
    @javafx.fxml.FXML
    private TableColumn statusCol;
    @javafx.fxml.FXML
    private TableColumn invStatusCol;
    @javafx.fxml.FXML
    private TableColumn payRefCol;
    @javafx.fxml.FXML
    private TableColumn invBalanceCol;
    @javafx.fxml.FXML
    private PieChart appStatusPieChart;
    @javafx.fxml.FXML
    private BarChart revenueBarChart;
    @javafx.fxml.FXML
    private TableView paymentsTable;

    @javafx.fxml.FXML
    public void onOpenProfile(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void onNavApplications(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void onSetEligibility(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void onRestoreData(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void onRefund(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void onResetFilters(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void onExportApplicationsCsv(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void onAddPayment(ActionEvent actionEvent) throws IOException {

    }

    @javafx.fxml.FXML
    public void onComputeSummary(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void onOpenSettings(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void onExportSummary(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void onNavSummary(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void onEditApplication(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void onNewApplication(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void onBackupData(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void onOpenInvoiceForSelected(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void onNavPayments(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void onSignOut(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void onMarkDispute(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void onApplyFilters(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void onNavInvoices(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void onNavOverview(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void onExportPaymentsCsv(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void onNavEligibility(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void onExportApplicationsPdf(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void onRefreshInvoices(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void onRefreshPayments(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void onCreateInvoice(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void onGlobalSearch(ActionEvent actionEvent) {
    }
}
