package com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;
import javafx.scene.control.SelectionMode;

/**
 * FEO-5 (Dummy):
 * - Dummy applicants + invoices are defined INSIDE this controller.
 * - "Find Outstanding" lists rows with due > 0.
 * - "Send Alerts" just shows an INFO popup ("Alert sent to N recipients").
 */
public class OutstandingController {

    @FXML private TableView<Row> table;
    @FXML private TableColumn<Row, Long>   colId;
    @FXML private TableColumn<Row, String> colName;
    @FXML private TableColumn<Row, String> colEmail;
    @FXML private TableColumn<Row, Double> colAmount;
    @FXML private TableColumn<Row, Double> colPaid;
    @FXML private TableColumn<Row, Double> colDue;
    @FXML private Label statusLabel;

    private final ObservableList<Row> data = FXCollections.observableArrayList();
    private final DecimalFormat money = new DecimalFormat("#,##0.00");

    // ---- Dummy data lives HERE (no file I/O) ----
    private static final List<Applicant> APPLICANTS = List.of(
            new Applicant(1001, "Ayesha Karim",  "ayesha@example.com"),
            new Applicant(1002, "Tanvir Ahmed",  "tanvir@example.com"),
            new Applicant(1003, "Shafin Rahman", "shafin@example.com"),
            new Applicant(1004, "Mithila Noor",  "mithila@example.com")
    );

    private static final List<Invoice> INVOICES = new ArrayList<>(List.of(
            new Invoice(2001, 1001, 15000.00,  5000.00), // due 10,000
            new Invoice(2002, 1002,  8000.00,     0.00), // due 8,000
            new Invoice(2003, 1003, 12000.00, 12000.00), // due 0 (paid)
            new Invoice(2004, 1004,  9000.00,  1000.00)  // due 8,000
    ));

    @FXML
    public void initialize() {

        table.setItems(data);
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Table wiring
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("applicant"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colPaid.setCellValueFactory(new PropertyValueFactory<>("paid"));
        colDue.setCellValueFactory(new PropertyValueFactory<>("due"));

        table.setItems(data);
        statusLabel.setText("Ready");
    }

    @FXML
    private void onFindOutstanding() {
        // Join invoices with applicants; keep due > 0
        Map<Integer, Applicant> byId = APPLICANTS.stream()
                .collect(Collectors.toMap(a -> a.id, a -> a));

        data.clear();
        for (Invoice inv : INVOICES) {
            double due = inv.amount - inv.paid;
            if (due > 0.000001) {
                Applicant a = byId.get(inv.applicantId);
                if (a != null) {
                    data.add(new Row(inv.id, a.name, a.email, inv.amount, inv.paid, due));
                }
            }
        }

        double totalDue = data.stream().mapToDouble(Row::getDue).sum();
        statusLabel.setText("Outstanding: " + data.size() + " invoice(s), total due " + money.format(totalDue));
    }

    @FXML
    private void onSendAlerts() {
        ObservableList<Row> selected = table.getSelectionModel().getSelectedItems();
        List<Row> targets = (selected == null || selected.isEmpty())
                ? new ArrayList<>(data) // if nothing selected, send to all outstanding
                : new ArrayList<>(selected);

        if (targets.isEmpty()) {
            showInfo("Nothing to alert", "No outstanding invoices found.");
            return;
        }

        // Dummy behavior: just show a popup saying alert sent
        String msg = "Alert sent to " + targets.size() + " recipient(s).";
        showInfo("Alerts", msg);
    }

    // --- tiny helpers ---
    private void showInfo(String title, String content) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(content);
        a.showAndWait();
    }

    // --- local row DTO for the table ---
    public static class Row {
        private final long id;
        private final String applicant;
        private final String email;
        private final double amount;
        private final double paid;
        private final double due;

        public Row(long id, String applicant, String email, double amount, double paid, double due) {
            this.id = id; this.applicant = applicant; this.email = email;
            this.amount = amount; this.paid = paid; this.due = due;
        }
        public long getId() { return id; }
        public String getApplicant() { return applicant; }
        public String getEmail() { return email; }
        public double getAmount() { return amount; }
        public double getPaid() { return paid; }
        public double getDue() { return due; }
    }

    // --- tiny dummy models (only what we need) ---
    private static class Applicant {
        final int id; final String name; final String email;
        Applicant(int id, String name, String email) { this.id = id; this.name = name; this.email = email; }
    }
    private static class Invoice {
        final long id; final int applicantId; final double amount; final double paid;
        Invoice(long id, int applicantId, double amount, double paid) {
            this.id = id; this.applicantId = applicantId; this.amount = amount; this.paid = paid;
        }
    }
}
