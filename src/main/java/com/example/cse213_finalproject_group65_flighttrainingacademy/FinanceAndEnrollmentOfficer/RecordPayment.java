package com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer;

import com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.Model.PaymentModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Goal 4: Add Payment controller
 * - Validates: amount > 0 and <= current balance
 * - Verifies: invoice exists and status != Closed
 * - Processing: append to payments.bin; update invoice balance & status in invoices.bin
 * - Output: shows receipt text and saves optional receipts/receipt_<inv>_<pid>.txt
 *
 * Storage format (simple pipe-separated lines):
 *   invoices.bin:
 *      invId|appId|total|paid|balance|dueDate(yyyy-MM-dd)|status
 *   payments.bin:
 *      payId|invId|amount|method|date(yyyy-MM-dd)|refNo
 */
public class RecordPayment {

    // --- FXML bindings ---
    @FXML private Label invoiceIdLabel;
    @FXML private Label currentBalanceLabel;
    @FXML private Label invoiceStatusLabel;
    @FXML private TextField amountField;
    @FXML private ComboBox<String> methodCombo;
    @FXML private DatePicker datePicker;
    @FXML private TextField refField;
    @FXML private Label statusLabel;
    @FXML private TextArea receiptArea;

    // --- Files & helpers ---
    private static final Path DATA_DIR = Paths.get("data");
    private static final Path INVOICES_FILE = DATA_DIR.resolve("invoices.bin");
    private static final Path PAYMENTS_FILE = DATA_DIR.resolve("payments.bin");
    private static final Path RECEIPTS_DIR = Paths.get("receipts");

    private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Context set by caller (selected invoice id)
    private String invoiceId;       // inv-123
    private String appId;           // (from invoices.bin row)
    private double currentBalance;  // parsed from invoices.bin
    private String invoiceStatus;   // Pending/Partial/Paid/Closed
    private double invoiceTotal;    // parsed
    private double invoicePaid;     // parsed

    // --- Public API: call this from parent controller after loading FXML ---
    public void setInvoiceContext(String invoiceIdFromParent) {
        this.invoiceId = invoiceIdFromParent;
        loadInvoiceRowOrFail();
        refreshHeader();
    }

    @FXML
    private void initialize() {
        // Prefill UI basics
        methodCombo.getItems().setAll("Bkash", "Card", "Cash");
        methodCombo.getSelectionModel().selectFirst();
        datePicker.setValue(LocalDate.now());

        // Ensure folders exist
        try {
            if (!Files.exists(DATA_DIR)) Files.createDirectories(DATA_DIR);
            if (!Files.exists(RECEIPTS_DIR)) Files.createDirectories(RECEIPTS_DIR);
            // Create files if missing (no headers; we use simple lines)
            if (!Files.exists(INVOICES_FILE)) Files.createFile(INVOICES_FILE);
            if (!Files.exists(PAYMENTS_FILE)) Files.createFile(PAYMENTS_FILE);
        } catch (IOException e) {
            uiError("Cannot prepare data directories/files: " + e.getMessage());
        }
    }

    // ========================= Event Handlers =========================

    @FXML
    public void onSave(ActionEvent actionEvent) {
        clearStatus();

        // ---- validation: numeric, >0, <= balance ----
        final String amtStr = amountField.getText() == null ? "" : amountField.getText().trim();
        double amt;
        try {
            amt = Double.parseDouble(amtStr);
        } catch (Exception ex) {
            uiError("Amount must be numeric.");
            return;
        }
        if (amt <= 0) {
            uiError("Amount must be > 0.");
            return;
        }
        if (amt > currentBalance) {
            uiError("Amount cannot exceed current balance (" + formatMoney(currentBalance) + ").");
            return;
        }

        // date
        LocalDate payDate = datePicker.getValue();
        if (payDate == null) {
            uiError("Please choose a payment date.");
            return;
        }

        // method & ref
        String method = methodCombo.getValue();
        String ref = safe(refField.getText());

        // ---- verification: invoice exists & status != Closed ----
        InvoiceRow row = findInvoice(invoiceId);
        if (row == null) {
            uiError("Invoice not found.");
            return;
        }
        if ("Closed".equalsIgnoreCase(row.status)) {
            uiError("Invoice is Closed; cannot accept payments.");
            return;
        }

        // ---- processing ----
        // 1) Append payment
        String payId = genId("pay");
        PaymentModel payment = new PaymentModel(payId, row.invId, amt, method, payDate, ref);
        boolean appendOk = appendPayment(payment);
        if (!appendOk) {
            uiError("Failed to write payment.");
            return;
        }

        // 2) Update invoice (paid, balance, status)
        double newPaid = row.paid + amt;
        double newBalance = Math.max(0.0, round2(row.total - newPaid));
        String newStatus = (newBalance == 0.0) ? "Paid" : ("Pending".equalsIgnoreCase(row.status) ? "Partial" : row.status);
        if (newBalance == 0.0) {
            // Treat Paid as terminal (not "Closed", but no balance).
            newStatus = "Paid";
        }

        boolean updateOk = updateInvoiceNumbers(row.invId, newPaid, newBalance, newStatus);
        if (!updateOk) {
            uiError("Failed to update invoice.");
            return;
        }

        // 3) Output: receipt + UI refresh
        String receiptText = payment.toReceiptText(
                row.appId,
                row.total,
                newPaid,
                newBalance,
                newStatus
        );
        receiptArea.setText(receiptText);
        saveReceiptToFile(row.invId, payment.payId, receiptText);

        // Reload header line to reflect updated numbers
        loadInvoiceRowOrFail();
        refreshHeader();

        statusLabel.setStyle("-fx-text-fill:#16a34a;");
        statusLabel.setText("Payment saved. Invoice updated.");
    }

    @FXML
    public void onCancel(ActionEvent actionEvent) {
        // Close this window (if shown in a dialog/stage)
        Node src = (Node) actionEvent.getSource();
        Stage st = (Stage) src.getScene().getWindow();
        st.close();
    }

    // ========================= Internal Helpers =========================

    private void refreshHeader() {
        invoiceIdLabel.setText(safe(invoiceId));
        currentBalanceLabel.setText(formatMoney(currentBalance));
        invoiceStatusLabel.setText(safe(invoiceStatus));
        invoiceStatusLabel.setStyle("-fx-text-fill:" + statusColor(invoiceStatus) + "; -fx-font-weight:bold;");
    }

    private String statusColor(String status) {
        if (status == null) return "#e5e7eb";
        switch (status.toLowerCase()) {
            case "pending": return "#fde68a"; // amber-200
            case "partial": return "#93c5fd"; // blue-300
            case "paid":    return "#86efac"; // green-300
            case "closed":  return "#fca5a5"; // red-300
            default:        return "#e5e7eb";
        }
    }

    private void loadInvoiceRowOrFail() {
        InvoiceRow row = findInvoice(invoiceId);
        if (row == null) {
            uiError("Invoice not found. Make sure invoices.bin has: invId|appId|total|paid|balance|dueDate|status");
            this.appId = "-";
            this.currentBalance = 0;
            this.invoiceStatus = "Closed";
            this.invoiceTotal = 0;
            this.invoicePaid = 0;
            return;
        }
        this.appId = row.appId;
        this.currentBalance = row.balance;
        this.invoiceStatus = row.status;
        this.invoiceTotal = row.total;
        this.invoicePaid = row.paid;
    }

    private void uiError(String msg) {
        statusLabel.setStyle("-fx-text-fill:#ef4444;");
        statusLabel.setText(msg);
    }

    private void clearStatus() {
        statusLabel.setText("");
    }

    private static String safe(String s) { return s == null ? "" : s.trim(); }

    private static String formatMoney(double v) {
        return String.format(Locale.US, "%.2f", v);
    }

    private static String genId(String prefix) {
        // Simple unique id: prefix-YYYYMMDDHHMMSS-<random4>
        String ts = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        int rnd = new java.util.Random().nextInt(9000) + 1000;
        return prefix + "-" + ts + "-" + rnd;
    }

    private static double round2(double v) {
        return Math.round(v * 100.0) / 100.0;
    }

    // ---- File ops ----

    private InvoiceRow findInvoice(String invId) {
        try (BufferedReader br = Files.newBufferedReader(INVOICES_FILE)) {
            String line;
            while ((line = br.readLine()) != null) {
                InvoiceRow r = InvoiceRow.parse(line);
                if (r != null && invId.equalsIgnoreCase(r.invId)) return r;
            }
        } catch (IOException ignored) {}
        return null;
    }

    private boolean updateInvoiceNumbers(String invId, double newPaid, double newBalance, String newStatus) {
        try {
            List<String> all = Files.readAllLines(INVOICES_FILE);
            boolean changed = false;
            for (int i = 0; i < all.size(); i++) {
                InvoiceRow r = InvoiceRow.parse(all.get(i));
                if (r != null && invId.equalsIgnoreCase(r.invId)) {
                    r.paid = round2(newPaid);
                    r.balance = round2(newBalance);
                    r.status = newStatus;
                    all.set(i, r.toRecord());
                    changed = true;
                    break;
                }
            }
            if (changed) {
                Files.write(INVOICES_FILE, all, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
            }
            return changed;
        } catch (IOException e) {
            return false;
        }
    }

    private boolean appendPayment(PaymentModel p) {
        try (BufferedWriter bw = Files.newBufferedWriter(PAYMENTS_FILE, StandardOpenOption.APPEND)) {
            bw.write(p.toRecord());
            bw.newLine();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private void saveReceiptToFile(String invId, String payId, String content) {
        try {
            Path f = RECEIPTS_DIR.resolve("receipt_" + invId + "_" + payId + ".txt");
            Files.writeString(f, content, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException ignored) {}
    }

    // ========================= Local DTOs =========================

    /**
     * Minimal view of an invoice row from invoices.bin
     * Format: invId|appId|total|paid|balance|dueDate|status
     */
    private static class InvoiceRow {
        String invId, appId, dueDate, status;
        double total, paid, balance;

        static InvoiceRow parse(String line) {
            if (line == null || line.isEmpty()) return null;
            String[] p = line.split("\\|");
            if (p.length < 7) return null;
            try {
                InvoiceRow r = new InvoiceRow();
                r.invId   = p[0].trim();
                r.appId   = p[1].trim();
                r.total   = Double.parseDouble(p[2].trim());
                r.paid    = Double.parseDouble(p[3].trim());
                r.balance = Double.parseDouble(p[4].trim());
                r.dueDate = p[5].trim(); // yyyy-MM-dd
                r.status  = p[6].trim();
                return r;
            } catch (Exception ex) {
                return null;
            }
        }

        String toRecord() {
            return String.join("|",
                    invId,
                    appId,
                    String.format(Locale.US, "%.2f", total),
                    String.format(Locale.US, "%.2f", paid),
                    String.format(Locale.US, "%.2f", balance),
                    dueDate,
                    status
            );
        }
    }
}
