package com.example.cse213_finalproject_group65_flighttrainingacademy.AircraftMaintenanceEngineer;

import com.example.cse213_finalproject_group65_flighttrainingacademy.HelloApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class ScheduleMaintenanceController
{
    @javafx.fxml.FXML
    private ComboBox<String> priorityComboBox;
    @javafx.fxml.FXML
    private TableColumn aircraftIdCol;
    @javafx.fxml.FXML
    private TableColumn dateCol;
    @javafx.fxml.FXML
    private ComboBox<String> aircraftIdComboBox;
    @javafx.fxml.FXML
    private TableColumn priorityCol;
    @javafx.fxml.FXML
    private TextArea messageTextArea;
    @javafx.fxml.FXML
    private DatePicker datePicker;
    @javafx.fxml.FXML
    private TableView scheduleMaintenanceTableView;

    private static final ObservableList<ScheduleMaintenance> schedules = FXCollections.observableArrayList();

    @javafx.fxml.FXML
    public void initialize() {
        aircraftIdCol.setCellValueFactory(new PropertyValueFactory<>("aircraftId"));
        priorityCol.setCellValueFactory(new PropertyValueFactory<>("priority"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        scheduleMaintenanceTableView.setItems(schedules);

        aircraftIdComboBox.setItems(FXCollections.observableArrayList("Cessna 172", "Diamond DA40", "Cirrus SR20", "Piper PA-28"));

        priorityComboBox.setItems(FXCollections.observableArrayList("Low", "Medium", "High"));

        if (messageTextArea != null) {
            messageTextArea.setEditable(false);
            messageTextArea.setWrapText(true);
        }

    }

    @javafx.fxml.FXML
    public void backToDashboardButtonOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("AircraftMaintenanceEngineer/AircraftMaintenanceEngineerDashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @javafx.fxml.FXML
    public void saveButtonOnAction(ActionEvent actionEvent) {
        String aircraftId = aircraftIdComboBox.getValue();
        String priority   = priorityComboBox.getValue();
        LocalDate date    = datePicker.getValue();

        if (aircraftId == null) {
            messageTextArea.setText("Please select Aircraft ID.");
            return;
        }
        if (priority == null) {
            messageTextArea.setText("Please choose Priority");
            return;
        }
        if (date == null) {
            messageTextArea.setText("Please pick a Date.");
            return;
        }

        for (ScheduleMaintenance s : schedules) {
            if (s.getAircraftId().equals(aircraftId) && s.getDate().equals(date)) {
                messageTextArea.setText("A schedule for " + aircraftId + " already exists on " + date + ".");
                return;
            }
        }

        schedules.add(new ScheduleMaintenance(aircraftId, priority, date));
        messageTextArea.setText("Maintenance scheduled for " + aircraftId + " on " + date + " (" + priority + ").");

        aircraftIdComboBox.getSelectionModel().clearSelection();
        priorityComboBox.getSelectionModel().clearSelection();
        datePicker.setValue(null);


    }
}