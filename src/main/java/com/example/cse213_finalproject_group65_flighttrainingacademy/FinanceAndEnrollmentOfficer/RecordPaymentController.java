package com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer;

import com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.Model.Invoice;
import com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.Model.InvoiceBinStore;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

// Back navigation imports
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.URL;
import java.util.Objects;

public class RecordPaymentController {

    @FXML private TextField invoiceIdField;
    @FXML private TextField amountField;

    private final DecimalFormat money = new DecimalFormat("#,##0.00");

    @FXML
    private void recordPayment() {
        // parse inputs
        Long id = parseLong(invoiceIdField.getText(), "Invoice ID must be a whole number.");
        if (id == null) return;

        Double amt = parseDouble(amountField.getText(), "Amount must be a number.");
        if (amt == null) return;
        if (amt <= 0) {
            error("Invalid amount", "Payment must be greater than 0.");
            return;
        }

        // load & find invoice
        List<Invoice> list = InvoiceBinStore.readAll();
        Optional<Invoice> hit = InvoiceBinStore.findById(list, id);
        if (hit.isEmpty()) {
            error("Not found", "No invoice with ID " + id + " in " + InvoiceBinStore.path());
            return;
        }

        Invoice inv = hit.get();
        double due = inv.getDue();
        if (amt - due > 1e-6) {
            error("Too much", "Payment (" + money.format(amt) + ") exceeds due (" + money.format(due) + ").");
            return;
        }

        // apply & persist
        inv.setPaid(inv.getPaid() + amt);
        String status = (inv.getDue() <= 1e-6) ? "Paid"
                : (inv.getPaid() <= 1e-6 ? "Unpaid" : "PartiallyPaid");

        try {
            InvoiceBinStore.writeAll(list);
        } catch (IOException ex) {
            error("I/O Error", "Failed to write invoices.bin.\n" + ex.getMessage());
            return;
        }

        // confirmation
        info("Payment recorded",
                "Invoice #" + inv.getId() +
                        "\nPaid just now: " + money.format(amt) +
                        "\nNew balance: " + money.format(inv.getDue()) +
                        "\nStatus: " + status +
                        "\n\nFile: " + InvoiceBinStore.path().toAbsolutePath());

        amountField.clear();
    }

    // Back → FEODashBoard.fxml
    @FXML
    private void onBack(ActionEvent e) {
        try {
            // If you don't have FEODashBoard class, use RecordPaymentController.class.getResource("/.../FEODashBoard.fxml")
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

    // helpers
    private Long parseLong(String s, String err) {
        try { return Long.parseLong(s.trim()); }
        catch (Exception e) { error("Invalid input", err); return null; }
    }

    private Double parseDouble(String s, String err) {
        try { return Double.parseDouble(s.trim()); }
        catch (Exception e) { error("Invalid input", err); return null; }
    }

    private void error(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(title); a.setHeaderText(null); a.setContentText(msg); a.showAndWait();
    }

    private void info(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title); a.setHeaderText(null); a.setContentText(msg); a.showAndWait();
    }
}
