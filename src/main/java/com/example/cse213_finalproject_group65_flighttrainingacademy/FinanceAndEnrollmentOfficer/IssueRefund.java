package com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer;


import com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.Model.Invoice;
import com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.Model.InvoiceStatus;
import com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.Model.Payment;
import com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.Model.Invoice;
import com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.Model.InvoiceStatus;
import com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.Model.Payment;
import com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.util.FileIO;
import com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.util.FilePaths;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

public class IssueRefund {

    @FXML private TextField invoiceIdField;
    @FXML private ChoiceBox<String> typeChoice;
    @FXML private TextArea reasonArea;
    @FXML private TextField amountField;
    @FXML private TextField approverIdField;
    @FXML private Label statusLabel;

    private ArrayList<Invoice> invoices;
    private ArrayList<Payment> payments;

    @FXML
    public void initialize() {
        invoices = FileIO.readList(FilePaths.INVOICES_BIN);
        payments = FileIO.readList(FilePaths.PAYMENTS_BIN);
        if (typeChoice.getItems().isEmpty()) {
            typeChoice.getItems().addAll("Refund", "Dispute");
        }
        typeChoice.getSelectionModel().selectFirst();
    }

    private Optional<Invoice> findInvoice(String id) {
        return invoices.stream().filter(x -> x.getInvoiceId().equalsIgnoreCase(id)).findFirst();
    }

    private double totalPaid(String invoiceId) {
        return payments.stream()
                .filter(p -> p.getInvoiceId().equalsIgnoreCase(invoiceId))
                .mapToDouble(Payment::getAmount)
                .sum(); // refunds are negative; this is net paid
    }

    private LocalDate latestPaymentDate(String invoiceId) {
        return payments.stream()
                .filter(p -> p.getInvoiceId().equalsIgnoreCase(invoiceId))
                .map(Payment::getDate)
                .max(LocalDate::compareTo)
                .orElse(null);
    }

    @FXML
    private void onVerifyApproval() {
        String invoiceId = invoiceIdField.getText();
        String approverId = approverIdField.getText();

        if (invoiceId == null || invoiceId.isBlank()) {
            statusLabel.setText("Enter a valid Invoice ID.");
            return;
        }
        if (approverId == null || approverId.isBlank()) {
            statusLabel.setText("Enter Approver ID.");
            return;
        }
        if (findInvoice(invoiceId).isEmpty()) {
            statusLabel.setText("Invoice not found.");
            return;
        }

        // VR: approval must exist in approvals.txt as "approverId,invoiceId,..."
        String needle = approverId.trim() + "," + invoiceId.trim();
        boolean ok = FileIO.fileHasLine(FilePaths.APPROVALS_TXT, needle);
        statusLabel.setText(ok ? "Approval found in approvals.txt." : "Approval NOT found in approvals.txt.");
    }

    @FXML
    private void onPostResolution() {
        String invoiceId = invoiceIdField.getText();
        String typ = typeChoice.getValue();
        String reason = reasonArea.getText();
        String approverId = approverIdField.getText();

        if (invoiceId == null || invoiceId.isBlank()) {
            statusLabel.setText("Invoice ID is required.");
            return;
        }
        var invOpt = findInvoice(invoiceId);
        if (invOpt.isEmpty()) {
            statusLabel.setText("Invoice not found.");
            return;
        }
        if (reason == null || reason.isBlank()) {
            statusLabel.setText("Reason is required.");
            return;
        }
        // Parse amount (allow 0 for dispute note)
        double amt = 0.0;
        try {
            amt = Double.parseDouble(amountField.getText());
            if (amt < 0) {
                statusLabel.setText("Amount must be non-negative.");
                return;
            }
        } catch (Exception ex) {
            statusLabel.setText("Enter a valid numeric Amount.");
            return;
        }

        // VL: amount ≤ amount paid
        double netPaid = totalPaid(invoiceId);
        if (amt > Math.max(netPaid, 0.0)) {
            statusLabel.setText("Amount exceeds net paid (" + String.format("%.2f", netPaid) + ").");
            return;
        }

        // VR: within policy window (30 days from latest payment)
        LocalDate latest = latestPaymentDate(invoiceId);
        if (latest == null) {
            statusLabel.setText("No payment on record; cannot refund/dispute.");
            return;
        }
        if (LocalDate.now().isAfter(latest.plusDays(30))) {
            statusLabel.setText("Outside 30-day policy window from " + latest + ".");
            return;
        }
        // VR: approval on file
        if (approverId == null || approverId.isBlank()
                || !FileIO.fileHasLine(FilePaths.APPROVALS_TXT, approverId.trim() + "," + invoiceId.trim())) {
            statusLabel.setText("Approval not found in approvals.txt.");
            return;
        }

        // DP: write negative entry to payments.bin if amount > 0
        if (amt > 0.0) {
            payments.add(new Payment(invoiceId, -amt, LocalDate.now(), typ));
            FileIO.writeList(FilePaths.PAYMENTS_BIN, payments);
        }

        // DP: recalc invoice balance & status
        Invoice inv = invOpt.get();
        double newNet = totalPaid(invoiceId);
        double newBalance = inv.getAmountDue() - newNet;
        inv.setBalance(newBalance);

        if (newBalance <= 0.0001) {
            inv.setStatus(InvoiceStatus.Paid);
        } else if (newNet > 0) {
            inv.setStatus(InvoiceStatus.PartiallyPaid);
        } else {
            inv.setStatus(InvoiceStatus.PaymentPending);
        }

        FileIO.writeList(FilePaths.INVOICES_BIN, invoices);

        // DP: write resolution note
        String line = String.join(" | ",
                "INV:" + inv.getInvoiceId(),
                "Type:" + typ,
                "Amount:" + String.format("%.2f", amt),
                "Reason:" + reason.replaceAll("\\s+", " ").trim(),
                "Approver:" + approverId,
                "Date:" + LocalDate.now());
        FileIO.appendLine(FilePaths.DISPUTES_TXT, line);

        statusLabel.setText("Resolution saved. New balance: " + String.format("%.2f", newBalance)
                + " (Status: " + inv.getStatus() + ")");
        new Alert(Alert.AlertType.INFORMATION, "Invoice updated successfully.").showAndWait();
    }
}
