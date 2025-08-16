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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class UpdateAircraftStatusController
{
    @javafx.fxml.FXML
    private TableColumn aircraftIdCol;
    @javafx.fxml.FXML
    private TableView statusTableview;
    @javafx.fxml.FXML
    private TableColumn dateCol;
    @javafx.fxml.FXML
    private ComboBox<String> aircraftIdComboBox;
    @javafx.fxml.FXML
    private ComboBox<String> aircraftStatusComboBox;
    @javafx.fxml.FXML
    private TextArea messageTextArea;
    @javafx.fxml.FXML
    private DatePicker datePicker;
    @javafx.fxml.FXML
    private TableColumn aircraftStatusCol;

    private static final ObservableList<AircraftStatus> updates =
            FXCollections.observableArrayList();

    @javafx.fxml.FXML
    public void initialize() {
        aircraftIdCol.setCellValueFactory(new PropertyValueFactory<>("aircraftId"));
        aircraftStatusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        statusTableview.setItems(updates);

        aircraftIdComboBox.setItems(FXCollections.observableArrayList(
                "Cessna 172", "Diamond DA40", "Cirrus SR20", "Piper PA-28"));
        aircraftStatusComboBox.setItems(FXCollections.observableArrayList(
                "Out of service", "Return To Service"));

        if (messageTextArea != null) {
            messageTextArea.setEditable(false);
            messageTextArea.setWrapText(true);
        }

    }

    @javafx.fxml.FXML
    public void updateStatusButtonOnAction(ActionEvent actionEvent) {
        String aircraftId = aircraftIdComboBox.getValue();
        String status     = aircraftStatusComboBox.getValue();
        LocalDate date    = datePicker.getValue();

        if (aircraftId == null) {
            messageTextArea.setText("Please select Aircraft ID.");
            return;
        }
        else if (status == null) {
            messageTextArea.setText("Please choose Aircraft Status.");
            return;
        }
        else if (date == null) {
            messageTextArea.setText("Please pick a Date.");
            return;
        }

        updates.add(new AircraftStatus(aircraftId, status, date));
        messageTextArea.setText("Status added for " + aircraftId + " on " + date + " (" + status + ").");

        aircraftIdComboBox.getSelectionModel().clearSelection();
        aircraftStatusComboBox.getSelectionModel().clearSelection();
        datePicker.setValue(null);
    }


    @javafx.fxml.FXML
    public void backToDashboardButtonOnAction(ActionEvent actionEvent) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("AircraftMaintenanceEngineer/AircraftMaintenanceEngineerDashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}