package com.example.cse213_finalproject_group65_flighttrainingacademy.AircraftMaintenanceEngineer;

import com.example.cse213_finalproject_group65_flighttrainingacademy.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AircraftMaintenanceEngineerDashboardController
{
    @javafx.fxml.FXML
    public void initialize() {
    }

    @javafx.fxml.FXML
    public void returnToServiceButtonOA(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void reportDefectButtonOA(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("AircraftMaintenanceEngineer/ReportDefect.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @javafx.fxml.FXML
    public void viewAircraftStatusButtonOA(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void scheduleMaintenanceButtonOA(ActionEvent actionEvent) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("AircraftMaintenanceEngineer/ScheduleMaintenance.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @javafx.fxml.FXML
    public void markOutOfServiceButtonOA(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void signoutButtonOnAction(ActionEvent actionEvent) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @javafx.fxml.FXML
    public void viewMaintenanceHistoryButtonOA(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void updateMaintenanceRecordButtonOA(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void viewMaintenanceScheduleButtonOA(ActionEvent actionEvent) {
    }
}