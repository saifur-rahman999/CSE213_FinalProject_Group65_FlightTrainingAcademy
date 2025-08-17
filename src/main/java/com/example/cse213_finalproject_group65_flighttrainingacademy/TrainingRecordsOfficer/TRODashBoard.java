package com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer;

import com.example.cse213_finalproject_group65_flighttrainingacademy.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TRODashBoard {

    @javafx.fxml.FXML
    public void initialize() {
    }

    @javafx.fxml.FXML
    public void openAddSession(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                HelloApplication.class.getResource("TrainingRecordsOfficer/AddSession.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @javafx.fxml.FXML
    public void openEditSessions(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                HelloApplication.class.getResource("TrainingRecordsOfficer/EditSessions.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    // Provided in case some FXML uses #openEditSession (singular)
    @javafx.fxml.FXML
    public void openEditSession(ActionEvent actionEvent) throws IOException {
        openEditSessions(actionEvent);
    }

    @javafx.fxml.FXML
    public void openUpdateSession(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                HelloApplication.class.getResource("TrainingRecordsOfficer/UpdateSession.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @javafx.fxml.FXML
    public void openSessionsTable(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                HelloApplication.class.getResource("TrainingRecordsOfficer/SessionsTable.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @javafx.fxml.FXML
    public void openScheduleView(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                HelloApplication.class.getResource("TrainingRecordsOfficer/ScheduleView.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @javafx.fxml.FXML
    public void openRecordAttendance(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                HelloApplication.class.getResource("TrainingRecordsOfficer/RecordAttendance.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @javafx.fxml.FXML
    public void openExportAttendance(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                HelloApplication.class.getResource("TrainingRecordsOfficer/ExportAttendance.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @javafx.fxml.FXML
    public void openInstructorRatings(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                HelloApplication.class.getResource("TrainingRecordsOfficer/InstructorRatings.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @javafx.fxml.FXML
    public void openQuickAnalytics(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                HelloApplication.class.getResource("TrainingRecordsOfficer/QuickAnalyticsView.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @javafx.fxml.FXML
    public void openSearchTraineeProfile(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                HelloApplication.class.getResource("TrainingRecordsOfficer/SearchTraineeProfile.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    // EXACT sign-out pattern used by AircraftMaintenanceEngineer
    @javafx.fxml.FXML
    public void signoutButtonOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                HelloApplication.class.getResource("login.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
