package com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * FEO-8 — Reminder Lines Preview (Dummy, no file I/O)
 * - Single action: "Generate Reminders"
 * - Identifies overdue invoices (amount - paid > 0) from in-memory dummy data
 * - Fills a table for visual confirmation + builds reminder lines into a TextArea
 * - Shows an info toast: "Preview only."
 * - NO file I/O
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
    @FXML private TextArea previewArea;

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
        statusLabel.setText("Ready");
        previewArea.setText("");
    }

    /** Single-click generation: find overdue, populate table and preview lines. */
    @FXML
    private void onGenerate() {
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

        // Build the preview lines
        if (data.isEmpty()) {
            previewArea.setText("No overdue students.");
            statusLabel.setText("No overdue students.");
        } else {
            String lines = data.stream()
                    .map(r -> String.format(Locale.US,
                            "%s <%s>: You have an outstanding balance of %s (Student #%d).",
                            r.getName(), r.getEmail(), money.format(r.getDue()), r.getStudentId()))
                    .collect(Collectors.joining("\n"));
            previewArea.setText(lines);

            double totalDue = data.stream().mapToDouble(Row::getDue).sum();
            statusLabel.setText("Overdue: " + data.size() + " student(s), total due " + money.format(totalDue));
        }

        // Toast/info: Preview only
        info("Reminders", "Preview only.");
    }

    // ---- Back button ----
    @FXML
    private void backToDashboard(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cse213_finalproject_group65_flighttrainingacademy/FinanceAndEnrollmentOfficer/FEODashboard.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Finance & Enrollment Dashboard");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            info("Navigation Error", "Unable to return to dashboard.");
        }
    }


    private void info(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        a.showAndWait();
    }

    public void onLoadOverdue(ActionEvent actionEvent) {
    }

    public void onSendReminders(ActionEvent actionEvent) {
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
