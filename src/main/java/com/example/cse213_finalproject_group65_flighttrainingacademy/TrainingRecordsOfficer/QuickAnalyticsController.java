package com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer;

import com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer.Model.ClassSession;
import com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer.Model.DummyStore;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class QuickAnalyticsController {

    @FXML private DatePicker fromDatePicker;
    @FXML private DatePicker toDatePicker;
    @FXML private Label totalHoursLabel;
    @FXML private Label monthsCountLabel;
    @FXML private BarChart<String, Number> hoursBarChart;
    @FXML private CategoryAxis monthAxis;
    @FXML private NumberAxis hoursAxis;

    @FXML
    private void initialize() {
        // Ensure we have some in-memory sessions to show
        DummyStore.seedSessionsIfEmpty();

        // default: last 6 months inclusive
        LocalDate today = LocalDate.now();
        LocalDate sixMonthsAgo = today.minusMonths(5).withDayOfMonth(1);
        fromDatePicker.setValue(sixMonthsAgo);
        toDatePicker.setValue(today);

        // empty render
        renderChart(FXCollections.observableArrayList());
        totalHoursLabel.setText("0");
        monthsCountLabel.setText("0");
    }

    @FXML
    private void onCompute() {
        LocalDate from = fromDatePicker.getValue();
        LocalDate to   = toDatePicker.getValue();

       // if (from == null || to == null) return info("Pick both From and To dates.");
       // if (from.isAfter(to)) return info("'From' must be ≤ 'To'.");

        YearMonth ymFrom = YearMonth.from(from);
        YearMonth ymTo   = YearMonth.from(to);
      //  long spanMonths = ymFrom.until(ymTo).toTotalMonths() + 1;
       // if (spanMonths > 12) return info("Range must be ≤ 12 months.");

        // Filter by date range
        ObservableList<ClassSession> filtered = DummyStore.sessions.stream()
                .filter(s -> s.getDate() != null && !s.getDate().isBefore(from) && !s.getDate().isAfter(to))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        // Pre-seed months so the X-axis is continuous
        Map<YearMonth, Double> monthToHours = new LinkedHashMap<>();
        YearMonth cur = ymFrom;
        while (!cur.isAfter(ymTo)) {
            monthToHours.put(cur, 0.0);
            cur = cur.plusMonths(1);
        }
        // Sum hours per month
        for (ClassSession s : filtered) {
            YearMonth m = YearMonth.from(s.getDate());
            monthToHours.computeIfPresent(m, (k, v) -> v + s.getDurationHours());
        }

        // Build chart points + KPIs
        ObservableList<XYChart.Data<String, Number>> points = FXCollections.observableArrayList();
        double total = 0.0;
        for (Map.Entry<YearMonth, Double> e : monthToHours.entrySet()) {
            double hrs = round1(e.getValue());
            total += hrs;
            points.add(new XYChart.Data<>(e.getKey().toString(), hrs)); // "YYYY-MM"
        }

        totalHoursLabel.setText(String.valueOf(round1(total)));
        monthsCountLabel.setText(String.valueOf(monthToHours.size()));
        renderChart(points);
    }

    private void renderChart(ObservableList<XYChart.Data<String, Number>> points) {
        hoursBarChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Hours");
        series.setData(points);
        hoursBarChart.getData().add(series);
    }

    private void info(String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK).showAndWait();
    }

    private static double round1(double v) { return Math.round(v * 10.0) / 10.0; }
}
