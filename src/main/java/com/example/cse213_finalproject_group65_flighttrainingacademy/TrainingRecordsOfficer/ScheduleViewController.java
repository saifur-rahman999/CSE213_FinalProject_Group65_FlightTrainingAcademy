package com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer;

import com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer.Model.ClassSession;
import com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer.Model.DummyStore;
import com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer.Model.SessionType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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
    @FXML private BarChart<String, Number> hoursByTraineeChart;
    @FXML private PieChart sessionsByTypeChart;

    private final ObservableList<ClassSession> viewData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // table columns
        colId.setCellValueFactory(new PropertyValueFactory<>("sessionId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colTrainee.setCellValueFactory(new PropertyValueFactory<>("traineeId"));
        colEquip.setCellValueFactory(new PropertyValueFactory<>("aircraftOrDeviceId"));
        colDur.setCellValueFactory(new PropertyValueFactory<>("durationHours"));
        colRemarks.setCellValueFactory(new PropertyValueFactory<>("remarks"));

        table.setItems(viewData);

        // seed a couple rows if empty (for quick demo)
        if (DummyStore.sessions.isEmpty()) {
            DummyStore.sessions.add(new ClassSession(1, LocalDate.now().minusDays(5), SessionType.FLIGHT, "TP-001","AC-C172-01",1.3,"Circuit"));
            DummyStore.sessions.add(new ClassSession(2, LocalDate.now().minusDays(3), SessionType.SIMULATOR,"TP-002","SIM-ATD-01",1.0,"IFR"));
            DummyStore.sessions.add(new ClassSession(3, LocalDate.now().minusDays(1), SessionType.FLIGHT, "TP-001","AC-C172-01",1.2,"Nav"));
            DummyStore.sessions.add(new ClassSession(4, LocalDate.now(),              SessionType.GROUND,  "TP-003","ROOM-A",      2.0,"Briefing"));
        }

        // filters
        typeCombo.setItems(FXCollections.observableArrayList("All", "FLIGHT", "SIMULATOR", "GROUND"));
        typeCombo.getSelectionModel().select("All");

        // create list of trainees from sessions + All
        Set<String> trainees = DummyStore.sessions.stream()
                .map(ClassSession::getTraineeId)
                .collect(Collectors.toCollection(TreeSet::new));
        List<String> traineeOpts = new ArrayList<>();
        traineeOpts.add("All");
        traineeOpts.addAll(trainees);
        traineeCombo.setItems(FXCollections.observableArrayList(traineeOpts));
        traineeCombo.getSelectionModel().select("All");

        // initial view
        applyFilters();
    }

    @FXML
    private void onRun() {
        // VL: if both dates provided, From ≤ To
        LocalDate f = fromDate.getValue();
        LocalDate t = toDate.getValue();
        if (f != null && t != null && f.isAfter(t)) {
            alert("From date must be ≤ To date.");
            return;
        }
        applyFilters();
    }

    @FXML
    private void onReset() {
        fromDate.setValue(null);
        toDate.setValue(null);
        typeCombo.getSelectionModel().select("All");
        traineeCombo.getSelectionModel().select("All");
        applyFilters();
    }

    private void applyFilters() {
        LocalDate f = fromDate.getValue();
        LocalDate t = toDate.getValue();
        String typeSel = typeCombo.getValue();
        String traineeSel = traineeCombo.getValue();

        List<ClassSession> filtered = DummyStore.sessions.stream()
                .filter(s -> f == null || !s.getDate().isBefore(f))
                .filter(s -> t == null || !s.getDate().isAfter(t))
                .filter(s -> "All".equals(typeSel) || s.getType().name().equals(typeSel))
                .filter(s -> "All".equals(traineeSel) || s.getTraineeId().equals(traineeSel))
                .collect(Collectors.toList());

        viewData.setAll(filtered);
        updateSummary(filtered);
        refreshCharts(filtered);
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
        hoursByTraineeChart.getData().setAll(series);

        // Pie: sessions by type
        Map<SessionType, Long> countByType = list.stream()
                .collect(Collectors.groupingBy(ClassSession::getType, TreeMap::new, Collectors.counting()));
        ObservableList<PieChart.Data> pie = FXCollections.observableArrayList();
        for (Map.Entry<SessionType, Long> e : countByType.entrySet()) {
            pie.add(new PieChart.Data(e.getKey().name() + " (" + e.getValue() + ")", e.getValue()));
        }
        sessionsByTypeChart.setData(pie);
    }

    private void alert(String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK).showAndWait();
    }
}
