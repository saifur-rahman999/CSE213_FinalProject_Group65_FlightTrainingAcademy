package com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer;

import com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer.Model.ClassSession;
import com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer.Model.DummyStore;
import com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer.Model.SessionType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ScheduleViewController {

    @FXML private DatePicker fromDate, toDate;
    @FXML private ComboBox<String> typeCombo;     // "All", "FLIGHT", "SIMULATOR", "GROUND"
    @FXML private ComboBox<String> traineeCombo;  // "All", "TP-001", ...
    @FXML private TableView<ClassSession> table;
    @FXML private TableColumn<ClassSession, Integer> colId;
    @FXML private TableColumn<ClassSession, LocalDate> colDate;
    @FXML private TableColumn<ClassSession, SessionType> colType;
    @FXML private TableColumn<ClassSession, String> colTrainee, colEquip, colRemarks;
    @FXML private TableColumn<ClassSession, Double> colDur;
    @FXML private Label summaryLabel;
    @FXML private javafx.scene.chart.BarChart<String, Number> hoursByTraineeChart;
    @FXML private PieChart sessionsByTypeChart;
    @FXML private Button btnBack;

    private final ObservableList<ClassSession> viewData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        setupTableColumns();
        table.setItems(viewData);

        // Filters (Run will ignore dates; we keep selectors for UI completeness)
        typeCombo.setItems(FXCollections.observableArrayList("All", "FLIGHT", "SIMULATOR", "GROUND"));
        typeCombo.getSelectionModel().select("All");

        Set<String> trainees = DummyStore.sessions.stream()
                .map(ClassSession::getTraineeId)
                .collect(Collectors.toCollection(TreeSet::new));
        List<String> traineeOpts = new ArrayList<>();
        traineeOpts.add("All");
        traineeOpts.addAll(trainees);
        traineeCombo.setItems(FXCollections.observableArrayList(traineeOpts));
        traineeCombo.getSelectionModel().select("All");

        // Start empty
        viewData.clear();
        updateSummary(viewData);
        refreshCharts(viewData);
    }

    private void setupTableColumns() {
        colId.setCellValueFactory(new PropertyValueFactory<>("sessionId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colTrainee.setCellValueFactory(new PropertyValueFactory<>("traineeId"));
        colEquip.setCellValueFactory(new PropertyValueFactory<>("aircraftOrDeviceId"));
        colDur.setCellValueFactory(new PropertyValueFactory<>("durationHours"));
        colRemarks.setCellValueFactory(new PropertyValueFactory<>("remarks"));
    }

    // === Produce a fixed dummy list regardless of date pickers ===
    private List<ClassSession> makeDummy() {
        LocalDate today = LocalDate.now();
        return Arrays.asList(
                new ClassSession(101, today.minusDays(6), SessionType.FLIGHT,    "TP-001","AC-C172-01",1.3,"Circuits"),
                new ClassSession(102, today.minusDays(5), SessionType.SIMULATOR, "TP-002","SIM-ATD-01",1.0,"IFR holds"),
                new ClassSession(103, today.minusDays(4), SessionType.FLIGHT,    "TP-001","AC-C172-01",1.2,"Local nav"),
                new ClassSession(104, today.minusDays(3), SessionType.GROUND,    "TP-003","ROOM-A",     2.0,"Preflight brief"),
                new ClassSession(105, today.minusDays(2), SessionType.SIMULATOR, "TP-002","SIM-ATD-01",1.2,"ILS"),
                new ClassSession(106, today.minusDays(1), SessionType.FLIGHT,    "TP-004","AC-DA40-02",1.5,"Crosswind"),
                new ClassSession(107, today,              SessionType.FLIGHT,    "TP-003","AC-C172-02",1.1,"Pattern work")
        );
    }

    @FXML
    private void onRun() {
        // Ignore date pickers: just load dummy data.
        List<ClassSession> dummy = makeDummy();
        viewData.setAll(dummy);
        updateSummary(dummy);
        refreshCharts(dummy);
    }

    @FXML
    private void onReset() {
        fromDate.setValue(null);
        toDate.setValue(null);
        typeCombo.getSelectionModel().select("All");
        traineeCombo.getSelectionModel().select("All");
        viewData.clear();
        updateSummary(viewData);
        refreshCharts(viewData);
    }

    private void updateSummary(List<ClassSession> list) {
        double totalHrs = list.stream().mapToDouble(ClassSession::getDurationHours).sum();
        summaryLabel.setText(list.size() + " sessions, " + String.format("%.1f", totalHrs) + " total hours");
    }

    private void refreshCharts(List<ClassSession> list) {
        // Bar: hours per trainee
        Map<String, Double> hoursByTrainee = new TreeMap<>();
        for (ClassSession s : list) {
            hoursByTrainee.merge(s.getTraineeId(), s.getDurationHours(), Double::sum);
        }
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (Map.Entry<String, Double> e : hoursByTrainee.entrySet()) {
            series.getData().add(new XYChart.Data<>(e.getKey(), e.getValue()));
        }
        // Avoid varargs-based setAll(...) to silence "unchecked generics array creation"
        hoursByTraineeChart.getData().clear();
        hoursByTraineeChart.getData().add(series);

        // Pie: sessions by type
        Map<SessionType, Long> countByType = list.stream()
                .collect(Collectors.groupingBy(ClassSession::getType, TreeMap::new, Collectors.counting()));
        ObservableList<PieChart.Data> pie = FXCollections.observableArrayList();
        for (Map.Entry<SessionType, Long> e : countByType.entrySet()) {
            pie.add(new PieChart.Data(e.getKey().name() + " (" + e.getValue() + ")", e.getValue()));
        }
        sessionsByTypeChart.setData(pie);
    }

    @FXML
    public void onBack() {
        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.close();
    }
}
