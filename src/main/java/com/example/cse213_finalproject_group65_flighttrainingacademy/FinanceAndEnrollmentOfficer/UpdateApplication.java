package com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer;

import com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.Model.ApplicationModel;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class UpdateApplication {

    private static final Path APPLICATIONS_BIN = Path.of("applications.bin");
    private static final Path DOCS_BIN = Path.of("docs.bin");
    private static final Path ELIGIBILITY_AUDIT_TXT = Path.of("eligibility_audit.txt");

    @FXML private TextField emailReadonly;
    @FXML private TextField currentStatusReadonly;
    @FXML private CheckBox docsVerifiedCheck;
    @FXML private TextField appIdReadonly;
    @FXML private TextField phoneReadonly;
    @FXML private ComboBox<String> decisionCombo;
    @FXML private TextField courseIntakeReadonly;
    @FXML private Label statusLabel;
    @FXML private CheckBox backgroundCheck;
    @FXML private TextField nameReadonly;
    @FXML private CheckBox prereqCheck;

    private ApplicationModel selectedApplication;

    public void setSelectedApplication(ApplicationModel app) {
        this.selectedApplication = app;
        if (app != null) {
            appIdReadonly.setText(app.getAppId());
            nameReadonly.setText(app.getName());
            emailReadonly.setText(app.getEmail());
            phoneReadonly.setText(app.getPhone());
            courseIntakeReadonly.setText(app.getCourse() + " / " + app.getIntake());
            currentStatusReadonly.setText(app.getStatus());
        }
    }

    @FXML
    private void initialize() {
        decisionCombo.getItems().setAll("Pending", "Accepted", "Rejected");
    }

    @FXML
    public void onSave(javafx.event.ActionEvent actionEvent) {
        if (selectedApplication == null) {
            showError("No application selected.");
            return;
        }

        String decision = decisionCombo.getValue();
        if (decision == null) {
            showError("Please choose a decision.");
            return;
        }
        if (!"Pending".equals(decision)) {
            if (!prereqCheck.isSelected() || !docsVerifiedCheck.isSelected() || !backgroundCheck.isSelected()) {
                showError("All checklist items must be completed before Accept/Reject.");
                return;
            }
        }

        if (docsVerifiedCheck.isSelected() && !verifyDocs(selectedApplication)) {
            showError("Document verification failed — IDs not found in docs.bin.");
            return;
        }

        if (!updateApplicationStatus(selectedApplication.getAppId(), decision)) {
            showError("Failed to update applications.bin.");
            return;
        }
        appendAuditLine(selectedApplication.getAppId(), decision);

        statusLabel.setText("Status updated to " + decision);
        showInfo("Eligibility status updated.");
        closeWindow(actionEvent);
    }

    @FXML
    public void onCancel(javafx.event.ActionEvent actionEvent) {
        closeWindow(actionEvent);
    }

    private boolean verifyDocs(ApplicationModel app) {
        if (!Files.exists(DOCS_BIN)) return false;
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(DOCS_BIN))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                List<?> list = (List<?>) obj;
                return list.stream().anyMatch(x -> Objects.toString(x, "").contains(app.getAppId())
                        || Objects.toString(x, "").contains(app.getEmail()));
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    private boolean updateApplicationStatus(String appId, String newStatus) {
        try {
            List<ApplicationModel> apps;
            if (Files.exists(APPLICATIONS_BIN)) {
                try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(APPLICATIONS_BIN))) {
                    Object obj = ois.readObject();
                    apps = (List<ApplicationModel>) obj;
                }
            } else {
                return false;
            }
            for (ApplicationModel a : apps) {
                if (a.getAppId().equals(appId)) {
                    a.setStatus(newStatus);
                    break;
                }
            }
            try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(APPLICATIONS_BIN))) {
                oos.writeObject(apps);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void appendAuditLine(String appId, String decision) {
        String ts = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String line = String.format("%s | AppID: %s | Decision: %s%n", ts, appId, decision);
        try {
            Files.writeString(ELIGIBILITY_AUDIT_TXT, line, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException ignored) {}
    }

    private void closeWindow(javafx.event.ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private void showError(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        a.setHeaderText(null);
        a.showAndWait();
    }

    private void showInfo(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        a.setHeaderText(null);
        a.showAndWait();
    }
}
