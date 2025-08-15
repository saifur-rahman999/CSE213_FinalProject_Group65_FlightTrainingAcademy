package com.example.cse213_finalproject_group65_flighttrainingacademy.FlightInstructor;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;

public class InstructorDashboard {

    @FXML
    private void handleViewUpcomingSessions() {
        showInfo("View Upcoming Sessions", "This will display all upcoming training sessions.");
    }

    @FXML
    private void handleUploadLessonPlan() {
        showInfo("Upload Lesson Plan", "This will allow the instructor to upload lesson plans.");
    }

    @FXML
    private void handleViewTraineeProgress() {
        showInfo("View Trainee Progress", "This will display progress reports of trainees.");
    }

    @FXML
    private void handleGradeExams() {
        showInfo("Grade Exams", "This will allow grading of exams and quizzes.");
    }

    @FXML
    private void handleManageSchedule() {
        showInfo("Manage Schedule", "This will allow schedule management for sessions.");
    }

    @FXML
    private void handleLogout() {
        showInfo("Logout", "You have been logged out successfully.");
    }

    // Helper method to show info popups
    private void showInfo(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
