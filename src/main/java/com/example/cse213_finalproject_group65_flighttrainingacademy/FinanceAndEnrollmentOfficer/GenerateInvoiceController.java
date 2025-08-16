package com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer;

import com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.Model.Applicant;
import com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.Model.Invoice;
import com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.util.InvoiceBinStore;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.List;

public class GenerateInvoiceController {

    @FXML private ComboBox<Applicant> applicantCombo;
    @FXML private TextField amountField;
    @FXML private Button btnGenerate;
    @FXML private Label hintLabel;

    // ---- Dummy data lives HERE ----
    private final ObservableList<Applicant> dummyApplicants = FXCollections.observableArrayList(
            new Applicant(1001, "Ayesha Karim", "ayesha@example.com"),
            new Applicant(1002, "Tanvir Ahmed", "tanvir@example.com"),
            new Applicant(1003, "Shafin Rahman", "shafin@example.com")
    );

    @FXML
    private void initialize() {
        applicantCombo.setItems(dummyApplicants);

        // nice rendering
        applicantCombo.setButtonCell(new ListCell<>() {
            @Override protected void updateItem(Applicant a, boolean empty) {
                super.updateItem(a, empty);
                setText(empty || a == null ? "" : a.toString()); // uses Applicant.toString()
            }
        });
        applicantCombo.setCellFactory(cb -> new ListCell<>() {
            @Override protected void updateItem(Applicant a, boolean empty) {
                super.updateItem(a, empty);
                setText(empty || a == null ? "" : a.toString());
            }
        });
    }

    @FXML
    private void onGenerate() {
        Applicant selected = applicantCombo.getValue();
        String amtText = amountField.getText() == null ? "" : amountField.getText().trim();

        // VL
        if (selected == null) { showError("Validation", "Please select an applicant."); return; }
        double amount;
        try { amount = Double.parseDouble(amtText); }
        catch (NumberFormatException nfe) { showError("Validation", "Amount must be a number > 0."); return; }
        if (amount <= 0) { showError("Validation", "Amount must be greater than 0."); return; }

        // FileChooser suggest invoices.bin
        Window owner = ((Node) btnGenerate).getScene().getWindow();
        File file = InvoiceBinStore.chooseBinFile(owner);
        if (file == null) return; // cancelled

        try {
            // Read list (deserialize; empty if none)
            List<Invoice> invoices = InvoiceBinStore.readAll(file);

            // New id = max + 1
            long nextId = invoices.stream().map(Invoice::getId).max(Comparator.naturalOrder()).orElse(0L) + 1;

            // Append new invoice
            Invoice inv = new Invoice(nextId, selected.getId(), amount, 0.0);
            invoices.add(inv);

            // Write back
            InvoiceBinStore.writeAll(file, invoices);

            // OP
            DecimalFormat df = new DecimalFormat("#,##0.00");
            Alert ok = new Alert(Alert.AlertType.INFORMATION);
            ok.setTitle("Invoice Created");
            ok.setHeaderText(null);
            ok.setContentText("Invoice saved to:\n" + file.getAbsolutePath()
                    + "\n\nID: " + inv.getId()
                    + "\nApplicant: " + selected.getName() + " (#" + selected.getId() + ")"
                    + "\nAmount: " + df.format(inv.getAmount()));
            ok.showAndWait();

            amountField.clear();
        } catch (IOException ex) {
            showError("File Error", ex.getMessage());
        }
    }

    private void showError(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
