package com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class GenerateInvoice {

    // ===== Files =====
    private static final Path APPLICATIONS_BIN = Path.of("applications.bin"); // List<ApplicationModel>
    private static final Path INVOICES_BIN = Path.of("invoices.bin");         // List<InvoiceModel>

    // ===== Tax Rate (change if needed) =====
    private static final BigDecimal TAX_RATE = new BigDecimal("0.05"); // 5%

    // ===== FXML fields =====
    @FXML private Label statusLabel;

    @FXML private TextField appIdReadonly;
    @FXML private TextField nameReadonly;
    @FXML private TextField emailReadonly;
    @FXML private TextField statusReadonly;

    @FXML private TextField tuitionField;
    @FXML private TextField labField;
    @FXML private TextField miscField;
    @FXML private TextField discountPercentField;
    @FXML private TextField discountAmountField;
    @FXML private TextField taxAutoField;

    @FXML private DatePicker dueDatePicker;

    @FXML private TextField subtotalPreview;
    @FXML private TextField totalPreview;

    // ===== Selected Application =====
    private ApplicationModel selectedApplication;

    public void setSelectedApplication(ApplicationModel app) {
        this.selectedApplication = app;
        if (app != null) {
            appIdReadonly.setText(app.getAppId());
            nameReadonly.setText(app.getName());
            emailReadonly.setText(app.getEmail());
            statusReadonly.setText(app.getStatus());
        }
    }

    @FXML
    private void initialize() {
        statusLabel.setText("Ready");
    }

    // ===== Buttons =====
    @FXML
    private void onCancel(javafx.event.ActionEvent e) {
        close(e);
    }

    @FXML
    private void onSave(javafx.event.ActionEvent e) {
        // --- VR pre-check: we must have an application selected ---
        if (selectedApplication == null) {
            error("No application selected.");
            return;
        }

        // === VL: Validation ===
        BigDecimal tuition = parseMoney(tuitionField.getText(), "Tuition");
        if (tuition == null) return;
        BigDecimal lab = parseMoney(labField.getText(), "Lab");
        if (lab == null) return;
        BigDecimal misc = parseMoney(miscField.getText(), "Misc");
        if (misc == null) return;

        BigDecimal discountPercent = parseOptionalPercent(discountPercentField.getText());
        BigDecimal discountAmount = parseOptionalMoney(discountAmountField.getText());
        if (discountPercent != null && discountPercent.compareTo(BigDecimal.ZERO) < 0) {
            error("Discount % cannot be negative.");
            return;
        }
        if (discountAmount != null && discountAmount.compareTo(BigDecimal.ZERO) < 0) {
            error("Discount amount cannot be negative.");
            return;
        }

        LocalDate due = dueDatePicker.getValue();
        if (due == null) {
            error("Please choose a due date.");
            return;
        }
        if (due.isBefore(LocalDate.now())) {
            error("Due date must be today or later.");
            return;
        }

        // === VR: Verification ===
        // (a) Application must be Accepted
        if (!"Accepted".equalsIgnoreCase(selectedApplication.getStatus())) {
            error("Application status must be 'Accepted' to create an invoice.");
            return;
        }

        // (b) No existing OPEN invoice for this App (Pending/PaymentPending/PartiallyPaid)
        List<InvoiceModel> invoices = loadInvoices();
        boolean hasOpen = invoices.stream().anyMatch(inv ->
                inv.getAppId().equals(selectedApplication.getAppId()) &&
                        inv.isOpen());
        if (hasOpen) {
            error("An open invoice already exists for this application.");
            return;
        }

        // === DP: Processing ===
        BigDecimal subtotal = tuition.add(lab).add(misc);

        BigDecimal percentDiscountValue = BigDecimal.ZERO;
        if (discountPercent != null) {
            percentDiscountValue = subtotal.multiply(discountPercent).divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
        }
        BigDecimal absoluteDiscount = (discountAmount != null) ? discountAmount : BigDecimal.ZERO;

        BigDecimal discountTotal = percentDiscountValue.add(absoluteDiscount);
        if (discountTotal.compareTo(subtotal) > 0) {
            error("Total discount cannot exceed subtotal.");
            return;
        }

        BigDecimal taxableBase = subtotal.subtract(discountTotal);
        if (taxableBase.compareTo(BigDecimal.ZERO) < 0) taxableBase = BigDecimal.ZERO;

        BigDecimal tax = taxableBase.multiply(TAX_RATE).setScale(2, RoundingMode.HALF_UP);
        BigDecimal total = taxableBase.add(tax).setScale(2, RoundingMode.HALF_UP);

        // Preview (helpful UX)
        taxAutoField.setText(tax.toPlainString());
        subtotalPreview.setText(subtotal.setScale(2, RoundingMode.HALF_UP).toPlainString());
        totalPreview.setText(total.toPlainString());

        // Build invoice
        String invoiceId = generateInvoiceId();
        InvoiceModel newInv = new InvoiceModel(
                invoiceId,
                selectedApplication.getAppId(),
                selectedApplication.getName(),
                selectedApplication.getEmail(),
                tuition.setScale(2, RoundingMode.HALF_UP),
                lab.setScale(2, RoundingMode.HALF_UP),
                misc.setScale(2, RoundingMode.HALF_UP),
                (discountPercent == null ? BigDecimal.ZERO : discountPercent.setScale(2, RoundingMode.HALF_UP)),
                absoluteDiscount.setScale(2, RoundingMode.HALF_UP),
                tax,
                subtotal.setScale(2, RoundingMode.HALF_UP),
                total,
                BigDecimal.ZERO,               // paid
                total,                          // balance
                due,
                "Pending",                      // status
                LocalDateTime.now()
        );

        // Append to invoices.bin
        invoices.add(newInv);
        if (!saveInvoices(invoices)) {
            error("Failed to save invoices.bin.");
            return;
        }

        // === OP: Output ===
        statusLabel.setText("Invoice created: " + invoiceId);
        info("Invoice created.");
        close(e);
    }

    // ===== Helpers =====
    private String generateInvoiceId() {
        String ts = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));
        String suffix = UUID.randomUUID().toString().substring(0, 6).toUpperCase(Locale.ROOT);
        return "INV-" + ts + "-" + suffix;
    }

    private BigDecimal parseMoney(String s, String label) {
        BigDecimal val = parseOptionalMoney(s);
        if (val == null) {
            error(label + " must be a non-negative number.");
            return null;
        }
        return val;
    }

    private BigDecimal parseOptionalMoney(String s) {
        if (s == null || s.trim().isEmpty()) return BigDecimal.ZERO;
        try {
            BigDecimal v = new BigDecimal(s.trim());
            if (v.compareTo(BigDecimal.ZERO) < 0) return null;
            return v;
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private BigDecimal parseOptionalPercent(String s) {
        if (s == null || s.trim().isEmpty()) return null;
        try {
            return new BigDecimal(s.trim());
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    private List<InvoiceModel> loadInvoices() {
        if (!Files.exists(INVOICES_BIN)) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(Files.newInputStream(INVOICES_BIN)))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                return (List<InvoiceModel>) obj;
            }
        } catch (Exception ignored) {}
        return new ArrayList<>();
    }

    private boolean saveInvoices(List<InvoiceModel> list) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(Files.newOutputStream(INVOICES_BIN)))) {
            oos.writeObject(list);
            oos.flush();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private void close(javafx.event.ActionEvent e) {
        Stage st = (Stage) ((Node) e.getSource()).getScene().getWindow();
        st.close();
    }

    private void error(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        a.setHeaderText(null);
        a.showAndWait();
        statusLabel.setText(msg);
    }

    private void info(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        a.setHeaderText(null);
        a.showAndWait();
    }
}
