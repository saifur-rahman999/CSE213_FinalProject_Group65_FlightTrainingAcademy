package com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer;

import com.example.cse213_finalproject_group65_flighttrainingacademy.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class FEODashBoard {
    private void go(ActionEvent e, String fxmlName) {
        try {
            URL url = getClass().getResource(fxmlName);
            Parent root = FXMLLoader.load(Objects.requireNonNull(url, "Missing FXML: " + fxmlName));
            Stage st = (Stage) ((Node) e.getSource()).getScene().getWindow();
            st.setScene(new Scene(root));
            st.show();
        } catch (Exception ex) { ex.printStackTrace(); }
    }
    public void openNewApplication(ActionEvent e){ go(e,"NewApplicationView.fxml"); }
    public void openSearchApplicants(ActionEvent e){ go(e,"SearchApplicantsView.fxml"); }
    public void openGenerateInvoice(ActionEvent e){ go(e,"GenerateInvoiceView.fxml"); }
    public void openRecordPayment(ActionEvent e){ go(e,"RecordPaymentView.fxml"); }
    public void openTrackOutstanding(ActionEvent e){ go(e,"TrackOutstandingView.fxml"); }
    public void openEnrollmentActions(ActionEvent e){ go(e,"EnrollmentActionsView.fxml"); }
    public void openIntakeSummary(ActionEvent e){ go(e,"IntakeSummaryView.fxml"); }
    public void openReminderPreview(ActionEvent e){ go(e,"ReminderPreviewView.fxml"); }
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

