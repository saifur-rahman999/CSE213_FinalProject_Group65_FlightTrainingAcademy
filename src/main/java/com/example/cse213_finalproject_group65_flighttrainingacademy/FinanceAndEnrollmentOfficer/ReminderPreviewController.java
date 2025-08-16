package com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.Region;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * FEO-8 (Dummy, single controller).
 * - "Load Overdue" shows students with due > 0 in a table.
 * - "Send Reminders" pops "Reminders sent to N recipient(s)." for selected rows
 *   (or all displayed rows if none selected).
 * - No file I/O; data is hard-coded here.
 */
public class ReminderPreviewController {

    @FXML private TableView<Row> table;
    @FXML private TableColumn<Row, Integer> colId;
    @FXML private TableColumn<Row, String>  colName;
    @FXML private TableColumn<Row, String>  colEmail;
    @FXML private TableColumn<Row, Double>  colAmount;
    @FXML private TableColumn<Row, Double>  colPaid;
    @FXML private TableColumn<Row, Double>  colDue;
    @FXML private Label statusLabel;

    private final ObservableList<Row> data = FXCollections.observableArrayList();
    private final DecimalFormat money = new DecimalFormat("#,##0.00");

    // ---------- Dummy data (no file I/O) ----------
    private static final List<Student> STUDENTS = List.of(
            new Student(1001, "Ayesha Karim",  "ayesha@example.com"),
            new Student(1002, "Tanvir Ahmed",  "tanvir@example.com"),
            new Student(1003, "Shafin Rahman", "shafin@example.com"),
            new Student(1004, "Mithila Noor",  "mithila@example.com"),
            new Student(1005, "Arif Hasan",    "arif@example.com")
    );

    private static final List<Invoice> INVOICES = new ArrayList<>(List.of(
            new Invoice(2001, 1001, 15000.00,  5000.00), // due 10,000
            new Invoice(2002, 1002,  8000.00,     0.00), // due 8,000
            new Invoice(2003, 1003, 12000.00, 12000.00), // paid
            new Invoice(2004, 1004,  9000.00,  1000.00), // due 8,000
            new Invoice(2005, 1005,  7000.00,  7000.00)  // paid
    ));

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colPaid.setCellValueFactory(new PropertyValueFactory<>("paid"));
        colDue.setCellValueFactory(new PropertyValueFactory<>("due"));

        table.setItems(data);
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); // enable multi-select
        statusLabel.setText("Ready");
    }

    @FXML
    private void onLoadOverdue() {
        Map<Integer, Student> stuById = STUDENTS.stream()
                .collect(Collectors.toMap(s -> s.id, s -> s));

        data.clear();
        for (Invoice inv : INVOICES) {
            double due = inv.amount - inv.paid;
            if (due > 0.000001) {
                Student s = stuById.get(inv.studentId);
                if (s != null) {
                    data.add(new Row(s.id, s.name, s.email, inv.amount, inv.paid, due));
                }
            }
        }

        double totalDue = data.stream().mapToDouble(Row::getDue).sum();
        statusLabel.setText("Overdue: " + data.size() + " student(s), total due " + money.format(totalDue));
    }

    @FXML
    private void onSendReminders() {
        List<Row> targets = table.getSelectionModel().getSelectedItems();
        if (targets == null || targets.isEmpty()) {
            targets = new ArrayList<>(data); // send to all displayed if nothing selected
        }

        if (targets.isEmpty()) {
            info("Reminders", "No overdue students to remind.");
            statusLabel.setText("No recipients.");
            return;
        }

        // Dummy behavior: just show popup
        String lines = targets.stream()
                .map(r -> r.getStudentId() + " — " + r.getName() + " <" + r.getEmail() + ">  Due: " + money.format(r.getDue()))
                .collect(Collectors.joining("\n"));

        info("Reminders sent",
                "Reminders sent to " + targets.size() + " recipient(s):\n\n" + lines);

        statusLabel.setText("Reminders sent to " + targets.size());
    }

    private void info(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        a.showAndWait();
    }

    // ---------- Local dummy DTOs ----------
    private static class Student {
        final int id; final String name; final String email;
        Student(int id, String name, String email) { this.id = id; this.name = name; this.email = email; }
    }

    private static class Invoice {
        final long id; final int studentId; final double amount; final double paid;
        Invoice(long id, int studentId, double amount, double paid) {
            this.id = id; this.studentId = studentId; this.amount = amount; this.paid = paid;
        }
    }

    /** Row shown in the table (joined Student + Invoice). */
    public static class Row {
        private final int studentId;
        private final String name;
        private final String email;
        private final double amount;
        private final double paid;
        private final double due;

        public Row(int studentId, String name, String email, double amount, double paid, double due) {
            this.studentId = studentId; this.name = name; this.email = email;
            this.amount = amount; this.paid = paid; this.due = due;
        }
        public int getStudentId() { return studentId; }
        public String getName() { return name; }
        public String getEmail() { return email; }
        public double getAmount() { return amount; }
        public double getPaid() { return paid; }
        public double getDue() { return due; }
    }
}
