package com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer;

import com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.util.FileIO;
import com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.util.FilePaths;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static jdk.internal.org.jline.utils.Log.error;

public class NewApplicationController {

    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private DatePicker intakePicker;          // Intake Month via DatePicker (any date in that month)
    @FXML private ComboBox<String> courseCombo;     // Course MUST be a ComboBox per spec

    @FXML
    public void initialize() {
        // Predefined course options — adjust to your real list
        courseCombo.getItems().setAll("Ground School", "Simulator Training", "PPL", "CPL", "IR");

        // Optional: pick today by default, or leave null if you prefer
        // intakePicker.setValue(LocalDate.now());
    }

    @FXML
    private void onSubmit() {
        final String name   = safeTrim(nameField.getText());
        final String email  = safeTrim(emailField.getText());
        final LocalDate intakeDate = intakePicker.getValue();
        final String course = courseCombo.getValue();

        // === VL (Validation Logic) ===
        if (name.isEmpty()) {
            showError("Name must not be blank.\nEnter all informations correctly");
            return;
        }
        if (email.isEmpty() || !email.contains("@")) {
            showError("Email must contain '@'.\nEnter all informations correctly");
            return;
        }
        if (course == null || course.isBlank()) {
            showError("Please select a Course.\nEnter all informations correctly");
            return;
        }
        if (intakeDate == null) {
            showError("Please provide an Intake Month (pick any date in that month).\nEnter all informations correctly");
            return;
        }

        // Convert picked date to Year-Month (yyyy-MM) as in the example "2025-10"
        final String intakeYm = intakeDate.getYear() + "-" + String.format("%02d", intakeDate.getMonthValue());

        // Timestamp: ISO_LOCAL_DATE_TIME (truncate nanos for neatness)
        final String timestamp = LocalDateTime.now().withNano(0)
                .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        // Build one line: <timestamp>|<name>|<email>|<intake>|<course>|Pending
        final String line = String.join("|",
                timestamp,
                sanitize(name),
                sanitize(email),
                intakeYm,
                sanitize(course),
                "Pending"
        );

        try {
            // Create file if it doesn't exist, then append one line
            FileIO.appendLineThrow(FilePaths.APPLICATIONS_TXT, line);

            // OP (Output): Confirmation alert
            info("Application saved.");

            // Reset fields
            onReset();

        } catch (IOException io) {
            showError("Failed to write to " + FilePaths.APPLICATIONS_TXT + "\n" + io.getMessage());
        }
    }

    @FXML
    private void onReset() {
        nameField.clear();
        emailField.clear();
        intakePicker.setValue(null);
        courseCombo.setValue(null);
        nameField.requestFocus();
    }

    @FXML
    private void onBack(ActionEvent e) {
        try {
            URL url = FEODashBoard.class.getResource("FEODashboard.fxml");
            Parent root = FXMLLoader.load(Objects.requireNonNull(url));
            Stage st = (Stage) ((Node) e.getSource()).getScene().getWindow();
            st.setScene(new Scene(root));
            st.show();
        } catch (Exception ex) {
            ex.printStackTrace();
            error("Navigation Error", "Could not load FEODashBoard.fxml");
        }
    }

    private static String safeTrim(String s) {
        return s == null ? "" : s.trim();
    }

    // Keep pipes/newlines out of the record
    private static String sanitize(String s) {
        if (s == null) return "";
        return s.replace("|", "/").replace("\r", " ").replace("\n", " ").trim();
    }

    private void showError(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Validation / I/O Error");
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }

    private void info(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Application Created");
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
