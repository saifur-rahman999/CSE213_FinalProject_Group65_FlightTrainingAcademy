package com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.net.URL;

public class FEODashboardController {

    private static final String BASE = "/com/example/cse213_finalproject_group65_flighttrainingacademy/FinanceAndEnrollmentOfficer/";
    private static final String NEW_APP          = BASE + "NewApplicationView.fxml";
    private static final String GEN_INVOICE      = BASE + "GenerateInvoiceView.fxml";
    private static final String RECORD_PAYMENT   = BASE + "RecordPaymentView.fxml";
    private static final String TRACK_OUTSTAND   = BASE + "TrackOutstandingView.fxml";
    private static final String ENROLL_ACTIONS   = BASE + "EnrollmentActionsView.fxml";
    private static final String REMINDER_PREVIEW = BASE + "ReminderPreviewView.fxml";

    public void openNewApplication(ActionEvent e)   { switchTo(e, NEW_APP); }
    public void openGenerateInvoice(ActionEvent e)  { switchTo(e, GEN_INVOICE); }
    public void openRecordPayment(ActionEvent e)    { switchTo(e, RECORD_PAYMENT); }
    public void openTrackOutstanding(ActionEvent e) { switchTo(e, TRACK_OUTSTAND); }
    public void openEnrollmentActions(ActionEvent e){ switchTo(e, ENROLL_ACTIONS); }
    public void openReminderPreview(ActionEvent e)  { switchTo(e, REMINDER_PREVIEW); }

    private void switchTo(ActionEvent e, String fxmlAbsPath) {
        try {
            URL url = FEODashboardController.class.getResource(fxmlAbsPath);
            if (url == null) {
                showError("Missing FXML on classpath:\n" + fxmlAbsPath);
                return;
            }
            Parent root = FXMLLoader.load(url);
            ((Node) e.getSource()).getScene().setRoot(root);
        } catch (IOException ex) {
            showError("Failed to load view:\n" + fxmlAbsPath + "\n\n" + ex.getMessage());
        }
    }

    private void showError(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Navigation Error");
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
