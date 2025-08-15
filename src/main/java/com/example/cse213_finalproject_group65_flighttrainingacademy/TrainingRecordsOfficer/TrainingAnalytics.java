package com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer;

import com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.util.BinStore;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

import com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer.Model.AssessmentRecord;
import com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer.Model.SessionRecord;

public class TrainingAnalytics {

    // FXML
    @FXML private DatePicker fromDatePicker, toDatePicker;
    @FXML private ComboBox<String> instructorCombo, aircraftCombo;
    @FXML private Button runBtn, exportBtn;
    @FXML private Label totalHoursLabel, avgDurationLabel, passRateLabel, simMixLabel, messageLabel;
    @FXML private TableView<AnalyticsRow> analyticsTable;
    @FXML private TableColumn<AnalyticsRow, String> colGroup, colPassRate;
    @FXML private TableColumn<AnalyticsRow, Number> colHours, colSessions;
    @FXML private BarChart<String, Number> hoursBarChart;
    @FXML private PieChart usagePieChart;

    // Files (adjust paths if needed)
    private static final String SESSIONS_FILE    = "sessions.bin";
    private static final String ASSESSMENTS_FILE = "assessments.bin";

    private final List<SessionRecord> allSessions = new ArrayList<>();
    private final List<AssessmentRecord> allAssessments = new ArrayList<>();
    private final ObservableList<AnalyticsRow> tableRows = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Load data (empty files are okay)
        allSessions.addAll(BinStore.loadList(SESSIONS_FILE, SessionRecord.class));
        allAssessments.addAll(BinStore.loadList(ASSESSMENTS_FILE, AssessmentRecord.class));

        // Populate filter combos from existing sessions so values are valid
        Set<String> instructors = new TreeSet<>();
        Set<String> aircrafts   = new TreeSet<>();
        for (SessionRecord s : allSessions) {
            if (s.getInstructorId() != null && !s.getInstructorId().isBlank()) instructors.add(s.getInstructorId());
            if (s.getAircraftId()   != null && !s.getAircraftId().isBlank())   aircrafts.add(s.getAircraftId());
        }
        instructorCombo.getItems().setAll(instructors);
        aircraftCombo.getItems().setAll(aircrafts);

        // Table mapping
        colGroup.setCellValueFactory(c -> c.getValue().groupProperty());
        colHours.setCellValueFactory(c -> c.getValue().hoursProperty());
        colSessions.setCellValueFactory(c -> c.getValue().sessionsProperty());
        colPassRate.setCellValueFactory(c -> c.getValue().passRateDisplayProperty());

        analyticsTable.setItems(tableRows);

        // Defaults (last 90 days)
        LocalDate today = LocalDate.now();
        fromDatePicker.setValue(today.minusDays(90));
        toDatePicker.setValue(today);

        messageLabel.setText("");
        clearUI();
    }

    @FXML
    private void onRun() {
        messageLabel.setText("");

        LocalDate from = fromDatePicker.getValue();
        LocalDate to   = toDatePicker.getValue();
        if (from == null || to == null) {
            showError("Please select both From and To dates.");
            return;
        }
        if (from.isAfter(to)) {
            showError("'From' must be on or before 'To'.");
            return;
        }
        Period p = Period.between(from, to);
        if (p.getYears() > 3) {
            showError("Date range should be 3 years or less.");
            return;
        }

        String selectedInstructor = instructorCombo.getValue(); // may be null
        String selectedAircraft   = aircraftCombo.getValue();   // may be null

        // Filter sessions by date and optional filters
        List<SessionRecord> sessions = allSessions.stream()
                .filter(s -> !s.getDate().isBefore(from) && !s.getDate().isAfter(to))
                .filter(s -> selectedInstructor == null || selectedInstructor.isBlank() || selectedInstructor.equals(s.getInstructorId()))
                .filter(s -> selectedAircraft   == null || selectedAircraft.isBlank()   || selectedAircraft.equals(s.getAircraftId()))
                .collect(Collectors.toList());

        // KPIs
        double totalHours = sessions.stream().mapToDouble(SessionRecord::getDurationHours).sum();
        int totalSessions = sessions.size();
        double avgDuration = totalSessions == 0 ? 0.0 : totalHours / totalSessions;

        // Pass rate (if assessments exist)
        Map<String, AssessmentRecord> assessBySession = allAssessments.stream()
                .collect(Collectors.toMap(AssessmentRecord::getSessionId, a -> a, (a, b) -> a));
        int assessed = 0, passed = 0;
        for (SessionRecord s : sessions) {
            AssessmentRecord a = assessBySession.get(s.getSessionId());
            if (a != null) {
                assessed++;
                if (a.isPassed()) passed++;
            }
        }
        double passRate = assessed == 0 ? 0.0 : (passed * 100.0 / assessed);

        // Sim mix
        double simHours = sessions.stream().filter(SessionRecord::isSimulator).mapToDouble(SessionRecord::getDurationHours).sum();
        double simMix = totalHours == 0 ? 0.0 : (simHours * 100.0 / totalHours);

        // Breakdowns — By Month (for table + bar chart)
        Map<YearMonth, List<SessionRecord>> byMonth = new TreeMap<>();
        for (SessionRecord s : sessions) {
            YearMonth ym = YearMonth.from(s.getDate());
            byMonth.computeIfAbsent(ym, k -> new ArrayList<>()).add(s);
        }

        tableRows.clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Hours");

        for (Map.Entry<YearMonth, List<SessionRecord>> e : byMonth.entrySet()) {
            String key = e.getKey().toString(); // "2025-08"
            List<SessionRecord> group = e.getValue();

            double h = group.stream().mapToDouble(SessionRecord::getDurationHours).sum();
            int c = group.size();

            int assessedG = 0, passedG = 0;
            for (SessionRecord s : group) {
                AssessmentRecord a = assessBySession.get(s.getSessionId());
                if (a != null) {
                    assessedG++;
                    if (a.isPassed()) passedG++;
                }
            }
            double pr = assessedG == 0 ? 0.0 : (passedG * 100.0 / assessedG);

            tableRows.add(new AnalyticsRow(key, h, c, pr));
            series.getData().add(new XYChart.Data<>(key, h));
        }

        hoursBarChart.getData().clear();
        hoursBarChart.getData().add(series);

        // Pie: Aircraft/Device usage by hours
        Map<String, Double> hoursByAircraft = new TreeMap<>();
        for (SessionRecord s : sessions) {
            String a = (s.getAircraftId() == null || s.getAircraftId().isBlank()) ? "(Unknown)" : s.getAircraftId();
            hoursByAircraft.merge(a, s.getDurationHours(), Double::sum);
        }
        usagePieChart.getData().clear();
        for (Map.Entry<String, Double> e : hoursByAircraft.entrySet()) {
            usagePieChart.getData().add(new PieChart.Data(e.getKey(), e.getValue()));
        }

        // Update KPI labels
        totalHoursLabel.setText(String.format("%.2f", totalHours));
        avgDurationLabel.setText(String.format("%.2f", avgDuration));
        passRateLabel.setText(String.format("%.1f%%", passRate));
        simMixLabel.setText(String.format("%.1f%%", simMix));

        messageLabel.setText("Analytics computed.");
    }

    @FXML
    private void onExport() {
        LocalDate from = fromDatePicker.getValue();
        LocalDate to   = toDatePicker.getValue();
        String fname = String.format("analytics_%s_%s.txt",
                from == null ? "NA" : from.toString(),
                to   == null ? "NA" : to.toString());

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fname))) {
            bw.write("Training Analytics Report\n");
            bw.write("-------------------------\n");
            bw.write("From: " + (from == null ? "NA" : from.toString()) +
                    "  To: " + (to == null ? "NA" : to.toString()) + "\n\n");

            bw.write("KPIs:\n");
            bw.write("Total Hours: " + totalHoursLabel.getText() + "\n");
            bw.write("Avg Duration (hrs): " + avgDurationLabel.getText() + "\n");
            bw.write("Pass Rate: " + passRateLabel.getText() + "\n");
            bw.write("Sim Mix: " + simMixLabel.getText() + "\n\n");

            bw.write("Breakdown by Month:\n");
            bw.write(String.format("%-12s %-10s %-10s %-10s%n", "Month", "Hours", "Sessions", "PassRate"));
            for (AnalyticsRow r : tableRows) {
                bw.write(String.format("%-12s %-10.2f %-10d %-10s%n",
                        r.getGroup(), r.getHours(), r.getSessions(), r.getPassRateDisplay()));
            }
            bw.flush();
            messageLabel.setText("Exported: " + fname);
        } catch (IOException e) {
            showError("Failed to export: " + e.getMessage());
        }
    }

    private void clearUI() {
        totalHoursLabel.setText("0");
        avgDurationLabel.setText("0");
        passRateLabel.setText("0%");
        simMixLabel.setText("0%");
        analyticsTable.getItems().clear();
        hoursBarChart.getData().clear();
        usagePieChart.getData().clear();
    }

    private void showError(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        a.setHeaderText("Validation/Verification");
        a.showAndWait();
        messageLabel.setText("");
    }

    // --- Simple Table DTO
    public static class AnalyticsRow {
        private final javafx.beans.property.StringProperty group =
                new javafx.beans.property.SimpleStringProperty("");
        private final javafx.beans.property.DoubleProperty hours =
                new javafx.beans.property.SimpleDoubleProperty(0);
        private final javafx.beans.property.IntegerProperty sessions =
                new javafx.beans.property.SimpleIntegerProperty(0);
        private final javafx.beans.property.StringProperty passRateDisplay =
                new javafx.beans.property.SimpleStringProperty("0%");

        public AnalyticsRow(String group, double hours, int sessions, double passRatePercent) {
            this.group.set(group);
            this.hours.set(hours);
            this.sessions.set(sessions);
            this.passRateDisplay.set(String.format("%.1f%%", passRatePercent));
        }

        public String getGroup() { return group.get(); }
        public double getHours() { return hours.get(); }
        public int getSessions() { return sessions.get(); }
        public String getPassRateDisplay() { return passRateDisplay.get(); }

        public javafx.beans.property.StringProperty groupProperty() { return group; }
        public javafx.beans.property.DoubleProperty hoursProperty() { return hours; }
        public javafx.beans.property.IntegerProperty sessionsProperty() { return sessions; }
        public javafx.beans.property.StringProperty passRateDisplayProperty() { return passRateDisplay; }
    }
}
