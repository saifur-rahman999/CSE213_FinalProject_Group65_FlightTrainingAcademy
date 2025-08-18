package com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer;

import com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.Model.Applicant;
import com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.Model.Invoice;
import com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.Model.InvoiceBinStore;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class GenerateInvoiceController {

    @FXML private ComboBox<Applicant> applicantCombo;
    @FXML private TextField amountField;

    private final ObservableList<Applicant> dummyApplicants = FXCollections.observableArrayList(
            new Applicant(1001, "Ayesha Karim", "ayesha@example.com"),
            new Applicant(1002, "Tanvir Ahmed", "tanvir@example.com"),
            new Applicant(1003, "Shafin Rahman", "shafin@example.com")
    );

    @FXML
    private void initialize() {
        applicantCombo.setItems(dummyApplicants); // uses Applicant.toString() by default
    }

    @FXML
    private void onGenerate() {
        Applicant a = applicantCombo.getValue();
        if (a == null) { alert("Select an applicant."); return; }

        double amount;
        try {
            amount = Double.parseDouble(amountField.getText().trim());
            if (amount <= 0) throw new NumberFormatException();
        } catch (Exception e) {
            alert("Enter a valid amount > 0.");
            return;
        }

        try {
            List<Invoice> list = InvoiceBinStore.readAll();
            long nextId = list.stream().map(Invoice::getId).max(Comparator.naturalOrder()).orElse(0L) + 1;
            Invoice inv = new Invoice(nextId, a.getId(), amount, 0.0);
            list.add(inv);
            InvoiceBinStore.writeAll(list);

            DecimalFormat df = new DecimalFormat("#,##0.00");
            alert("Saved to: " + InvoiceBinStore.path()
                    + "\n\nID: " + inv.getId()
                    + "\nApplicant: " + a.getName() + " (#" + a.getId() + ")"
                    + "\nAmount: " + df.format(inv.getAmount()));
            amountField.clear();
        } catch (IOException ex) {
            alert("File error: " + ex.getMessage());
        }
    }

    @FXML
    private void onBack(ActionEvent e) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(
                    FEODashBoard.class.getResource("FEODashboard.fxml")));
            Stage st = (Stage) ((Node) e.getSource()).getScene().getWindow();
            st.setScene(new Scene(root));
            st.show();
        } catch (Exception ex) {
            alert("Cannot open FEODashBoard.fxml");
        }
    }

    private void alert(String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK).showAndWait();
    }
}
