package com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer;

import com.example.cse213_finalproject_group65_flighttrainingacademy.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FEODashBoard {

    @javafx.fxml.FXML
    public void initialize() {
    }

    @javafx.fxml.FXML
    public void newApplicationButtonOA(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                HelloApplication.class.getResource("FinanceAndEnrollmentOfficer/NewApplicationView.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @javafx.fxml.FXML
    public void searchApplicantsButtonOA(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                HelloApplication.class.getResource("FinanceAndEnrollmentOfficer/SearchApplicantsView.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @javafx.fxml.FXML
    public void generateInvoiceButtonOA(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                HelloApplication.class.getResource("FinanceAndEnrollmentOfficer/GenerateInvoiceView.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @javafx.fxml.FXML
    public void recordPaymentButtonOA(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                HelloApplication.class.getResource("FinanceAndEnrollmentOfficer/RecordPaymentView.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @javafx.fxml.FXML
    public void trackOutstandingButtonOA(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                HelloApplication.class.getResource("FinanceAndEnrollmentOfficer/TrackOutstandingView.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @javafx.fxml.FXML
    public void enrollmentActionsButtonOA(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                HelloApplication.class.getResource("FinanceAndEnrollmentOfficer/EnrollmentActionsView.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @javafx.fxml.FXML
    public void intakeSummaryButtonOA(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                HelloApplication.class.getResource("FinanceAndEnrollmentOfficer/IntakeSummaryView.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @javafx.fxml.FXML
    public void reminderPreviewButtonOA(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                HelloApplication.class.getResource("FinanceAndEnrollmentOfficer/ReminderPreviewView.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

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
