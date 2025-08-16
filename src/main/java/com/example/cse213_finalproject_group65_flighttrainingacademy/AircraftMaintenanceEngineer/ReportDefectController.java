package com.example.cse213_finalproject_group65_flighttrainingacademy.AircraftMaintenanceEngineer;

import com.example.cse213_finalproject_group65_flighttrainingacademy.HelloApplication;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class ReportDefectController
{
    @javafx.fxml.FXML
    private ComboBox<String> defectSeverityComboBox;
    @javafx.fxml.FXML
    private ComboBox<String> aircraftIdComboBox;
    @javafx.fxml.FXML
    private TextArea defectTitleTextArea;
    @javafx.fxml.FXML
    private TableView defectTableView;
    @javafx.fxml.FXML
    private TextArea errorTextArea;
    @javafx.fxml.FXML
    private DatePicker datePicker;
    @javafx.fxml.FXML
    private TableColumn titleCol;
    @javafx.fxml.FXML
    private TableColumn idCol;
    @javafx.fxml.FXML
    private TableColumn dateCol;
    @javafx.fxml.FXML
    private TableColumn severityCol;

    private static final javafx.collections.ObservableList<DefectReport> defects =
            javafx.collections.FXCollections.observableArrayList();
    @FXML
    private TextArea successTextArea;

    @javafx.fxml.FXML
    public void initialize() {
        errorTextArea.setEditable(false);
        errorTextArea.setWrapText(true);
        idCol.setCellValueFactory(new PropertyValueFactory<>("aircraftId"));
        severityCol.setCellValueFactory(new PropertyValueFactory<>("severity"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        defectTableView.setItems(defects);
        aircraftIdComboBox.setItems(FXCollections.observableArrayList("Cessna 172","Diamond DA40","Cirrus SR20","Piper PA-28"));
        defectSeverityComboBox.setItems(FXCollections.observableArrayList("Minor","Major","Critical"));
    }

    @javafx.fxml.FXML
    public void saveButtonOnAction(ActionEvent actionEvent) {
        String id          = aircraftIdComboBox.getValue();
        String severity    = defectSeverityComboBox.getValue();
        String title       = defectTitleTextArea.getText();
        java.time.LocalDate date = datePicker.getValue();

        if (id==null) {
            errorTextArea.setText("Please enter Aircraft ID.");
            return;
        }
        else if (severity == null) {
            errorTextArea.setText("Please choose Defect Severity.");
            return;
        }

        else if (title.isBlank()) {
            errorTextArea.setText("Please enter Defect Title.");
            return;
        }

        else if (date == null) {
            errorTextArea.setText("Please pick a Date.");
            return;
        }

        defects.add(new DefectReport(id, severity, title, date));
        errorTextArea.setText("Defect added for Aircraft " + id + ".");

        aircraftIdComboBox.getSelectionModel().clearSelection();
        defectSeverityComboBox.getSelectionModel().clearSelection();
        defectTitleTextArea.clear();
        datePicker.setValue(null);

    }

    @FXML
    public void backToDashboardOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("AircraftMaintenanceEngineer/AircraftMaintenanceEngineerDashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}