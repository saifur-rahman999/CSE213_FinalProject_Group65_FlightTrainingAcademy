package com.example.cse213_finalproject_group65_flighttrainingacademy.GroundInstructor;

import com.example.cse213_finalproject_group65_flighttrainingacademy.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class GroundInstructorDashboardController
{
    @javafx.fxml.FXML
    private BorderPane groundInstructorInfoBP;
    @javafx.fxml.FXML
    private TextArea groundInstructorInfoTA;

    @javafx.fxml.FXML
    public void initialize() {
        groundInstructorInfoTA.setEditable(false);
        groundInstructorInfoTA.setWrapText(true);
    }

    @javafx.fxml.FXML
    public void signoutOA(ActionEvent actionEvent) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @javafx.fxml.FXML
    public void uploadMaterialsOA(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void takeAttendanceOA(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void viewTrainingProgressOA(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void viewClassSessionsOA(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("GroundInstructor/ViewClassSession.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @javafx.fxml.FXML
    public void certifyStudentOA(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void recordExamScoreOA(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void downloadStudentGradesOA(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void postAnnouncementsOA(ActionEvent actionEvent) {
    }
}