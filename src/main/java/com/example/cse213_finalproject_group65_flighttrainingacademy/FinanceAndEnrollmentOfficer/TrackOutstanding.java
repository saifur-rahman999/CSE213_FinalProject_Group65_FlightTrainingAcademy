package com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer;

import com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.util.FileIO;
import com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.util.FilePaths;
import com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.Model.Invoice;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class TrackOutstanding {
    @FXML private ComboBox<String> intakeCombo;
    @FXML private ComboBox<String> courseCombo;
    @FXML private ChoiceBox<String> bucketChoice;

    @FXML private TableView<Invoice> duesTable;
    @FXML private TableColumn<Invoice,String> colInvoiceId;
    @FXML private TableColumn<Invoice,String> colTrainee;
    @FXML private TableColumn<Invoice,String> colCourse;
    @FXML private TableColumn<Invoice,String> colIntake;
    @FXML private TableColumn<Invoice,String> colDue;
    @FXML private TableColumn<Invoice,Double> colBalance;
    @FXML private TableColumn<Invoice,String> colBucket;

    @FXML private BarChart<String, Number> bucketChart;
    @FXML private Label messageLabel;

    private final ObservableList<Invoice> master = FXCollections.observableArrayList();
    private final ObservableList<Invoice> filtered = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Table mapping
        colInvoiceId.setCellValueFactory(new PropertyValueFactory<>("invoiceId"));
        colTrainee.setCellValueFactory(new PropertyValueFactory<>("traineeName"));
        colCourse.setCellValueFactory(new PropertyValueFactory<>("course"));
        colIntake.setCellValueFactory(new PropertyValueFactory<>("intake"));
        colDue.setCellValueFactory(c -> javafx.beans.binding.Bindings.createStringBinding(
                () -> c.getValue().getDueDate() == null ? "" : c.getValue().getDueDate().toString()));
        colBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));
        colBucket.setCellValueFactory(c -> javafx.beans.binding.Bindings.createStringBinding(
                () -> bucketFor(c.getValue(), LocalDate.now())));

        // Load invoices
        master.setAll(FileIO.readList(FilePaths.INVOICES_BIN));

        // Seed combos from data (unique values + "All")
        Set<String> intakes = master.stream().map(Invoice::getIntake).filter(Objects::nonNull).collect(Collectors.toCollection(TreeSet::new));
        Set<String> courses = master.stream().map(Invoice::getCourse).filter(Objects::nonNull).collect(Collectors.toCollection(TreeSet::new));

        intakeCombo.getItems().setAll(new ArrayList<>(intakes));
        intakeCombo.getItems().add(0, "All");
        intakeCombo.getSelectionModel().selectFirst();

        courseCombo.getItems().setAll(new ArrayList<>(courses));
        courseCombo.getItems().add(0, "All");
        courseCombo.getSelectionModel().selectFirst();

        bucketChoice.getItems().addAll("All", "30+", "60+", "90+");
        bucketChoice.getSelectionModel().selectFirst();

        // Default view
        applyFilters();
    }

    @FXML
    private void onApplyFilters() {
        applyFilters();
    }

    private void applyFilters() {
        String intake = intakeCombo.getValue();
        String course = courseCombo.getValue();
        String bucket = bucketChoice.getValue();

        // VL: filters consistent – we only check null/empty quickly
        if (intake == null || course == null || bucket == null) {
            messageLabel.setText("Select valid filters.");
            return;
        }

        // VR: invoices exist for chosen filters
        List<Invoice> list = master.stream()
                .filter(inv -> "All".equals(intake) || intake.equals(inv.getIntake()))
                .filter(inv -> "All".equals(course) || course.equals(inv.getCourse()))
                .collect(Collectors.toList());

        LocalDate today = LocalDate.now();

        // Age bucket filter
        if (!"All".equals(bucket)) {
            list = list.stream()
                    .filter(inv -> bucket.equals(bucketFor(inv, today)))
                    .collect(Collectors.toList());
        }

        filtered.setAll(list);
        duesTable.setItems(filtered);

        // Chart update: sum balances by bucket
        updateChart(list, today);

        if (list.isEmpty()) {
            messageLabel.setText("No invoices found for selected filters.");
        } else {
            messageLabel.setText("Found " + list.size() + " invoice(s).");
        }
    }

    private String bucketFor(Invoice inv, LocalDate today) {
        if (inv.getDueDate() == null) return "All";
        long days = ChronoUnit.DAYS.between(inv.getDueDate(), today);
        if (days >= 90) return "90+";
        if (days >= 60) return "60+";
        if (days >= 30) return "30+";
        return "All"; // current (<30 days)
    }

    private void updateChart(List<Invoice> list, LocalDate today) {
        Map<String, Double> sums = new HashMap<>();
        sums.put("30+", 0.0); sums.put("60+", 0.0); sums.put("90+", 0.0);

        for (Invoice inv : list) {
            String b = bucketFor(inv, today);
            if (sums.containsKey(b)) {
                sums.put(b, sums.get(b) + Math.max(inv.getBalance(), 0.0));
            }
        }

        bucketChart.getData().clear();
        XYChart.Series<String, Number> s = new XYChart.Series<>();
        s.setName("Outstanding by Bucket");
        for (String key : List.of("30+", "60+", "90+")) {
            s.getData().add(new XYChart.Data<>(key, sums.get(key)));
        }
        bucketChart.getData().add(s);
    }

    @FXML
    private void onGenerateReminders() {
        if (filtered.isEmpty()) {
            messageLabel.setText("Nothing to remind for current filters.");
            return;
        }
        LocalDate today = LocalDate.now();

        for (Invoice inv : filtered) {
            String bucket = bucketFor(inv, today);
            if ("All".equals(bucket)) continue; // only overdue ones
            // DP: write a simple address/reminder line to reminders.txt
            String line = String.join(" | ",
                    "INV:" + inv.getInvoiceId(),
                    inv.getTraineeName() + " <" + inv.getEmail() + ">",
                    "Course:" + inv.getCourse(),
                    "Intake:" + inv.getIntake(),
                    "Due:" + (inv.getDueDate() == null ? "N/A" : inv.getDueDate().toString()),
                    "Balance:" + String.format("%.2f", inv.getBalance()),
                    "Bucket:" + bucket);
            FileIO.appendLine(FilePaths.REMINDERS_TXT, line);
        }
        messageLabel.setText("Reminders generated to " + FilePaths.REMINDERS_TXT);
    }
}
