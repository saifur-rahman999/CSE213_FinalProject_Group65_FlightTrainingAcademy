package com.example.cse213_finalproject_group65_flighttrainingacademy.TraineePilot;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class TraineeDashboard {

    @FXML
    private Label welcomeLabel;
    @FXML
    private Button resourcesButton;
    @FXML
    private Button requestRescheduleButton;
    @FXML
    private Button takeExamButton;
    @FXML
    private Button viewGradesButton;
    @FXML
    private Button joinGroundSchoolButton;
    @FXML
    private Button trackProgressButton;
    @FXML
    private Button viewScheduleButton;
    @FXML
    private Button bookSessionButton;

    @FXML
    private void onViewScheduleClick(ActionEvent event) {
        // Code to open view_schedule.fxml
    }

    @FXML
    private void onBookSessionClick(ActionEvent event) {
        // Code to open book_session_form.fxml
    }

    @FXML
    private void onJoinGroundSchoolClick(ActionEvent event) {
        // Code to join ground school
    }

    @FXML
    private void onResourcesClick(ActionEvent event) {
        // Code to open resources.fxml
    }

    @FXML
    private void onTakeExamClick(ActionEvent event) {
        // Code to open take_exam.fxml
    }

    @FXML
    private void onViewGradesClick(ActionEvent event) {
        // Code to open view_grades.fxml
    }

    @FXML
    private void onTrackProgressClick(ActionEvent event) {
        // Code to open track_progress.fxml
    }

    @FXML
    private void onRequestRescheduleClick(ActionEvent event) {
        // Code to open request_schedule_change.fxml
    }
}
