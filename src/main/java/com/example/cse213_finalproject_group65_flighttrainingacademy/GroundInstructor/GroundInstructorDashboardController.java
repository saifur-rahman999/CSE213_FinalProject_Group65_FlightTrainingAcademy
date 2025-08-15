package com.example.cse213_finalproject_group65_flighttrainingacademy.GroundInstructor;

import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

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
        groundInstructorInfoTA.setText(gi);
    }

    @javafx.fxml.FXML
    public void signoutOA(ActionEvent actionEvent) {
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
    public void viewClassSessionsOA(ActionEvent actionEvent) {
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