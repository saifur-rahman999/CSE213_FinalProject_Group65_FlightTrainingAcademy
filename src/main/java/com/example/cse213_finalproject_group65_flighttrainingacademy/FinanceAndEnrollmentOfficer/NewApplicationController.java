package com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer;

import com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.Model.ApplicationStatus;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Path; // IMPORTANT

public class NewApplicationController {

    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private ComboBox<String> intakeCombo;
    @FXML private ComboBox<String> courseCombo;

    @FXML
    public void initialize() {
        // Replace with your real options if available
        intakeCombo.getItems().setAll("Sep 2025", "Oct 2025", "Nov 2025", "Dec 2025", "Jan 2026");
        courseCombo.getItems().setAll("Ground School", "Simulator Training", "PPL", "CPL", "IR");
    }

    @FXML
    private void onCreate() {
        final String name   = safeTrim(nameField.getText());
        final String email  = safeTrim(emailField.getText());
        final String intake = intakeCombo.getValue();
        final String course = courseCombo.getValue();

        // VL
        if (name.isEmpty()) { showError("Name cannot be blank."); return; }
        if (email.isEmpty() || !email.contains("@")) { showError("Enter a valid email (must contain '@')."); return; }
        if (intake == null || intake.isBlank()) { showError("Please select an intake month."); return; }
        if (course == null || course.isBlank()) { showError("Please select a course."); return; }

        try {
            int newId = com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.ApplicationTextStore.nextId();

            // IMPORTANT: refer to YOUR model class, not javafx.application.Application.
            com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.Model.Application app =
                    new com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.Model.Application(
                            newId, name, email, intake, course, ApplicationStatus.PENDING
                    );

            com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.ApplicationTextStore.append(app);

            String where = com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.ApplicationTextStore.storePath()
                    .map(Path::toString)
                    .orElse("data/applications.txt");

            Alert ok = new Alert(Alert.AlertType.INFORMATION);
            ok.setTitle("Application Created");
            ok.setHeaderText(null);
            ok.setContentText("Saved #" + app.getId() + " to:\n" + where);
            ok.showAndWait();

            onReset();
        } catch (IOException io) {
            showError("Failed to write to applications.txt\n" + io.getMessage());
        }
    }

    @FXML
    private void onReset() {
        nameField.clear();
        emailField.clear();
        intakeCombo.setValue(null);
        courseCombo.setValue(null);
        nameField.requestFocus();
    }

    @FXML
    private void onClose() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }

    private static String safeTrim(String s) { return s == null ? "" : s.trim(); }

    private void showError(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Validation / IO Error");
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
