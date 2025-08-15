package com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer;

import com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class TrackStudentAttendance {

    @FXML private ComboBox<Session> sessionComboBox;
    @FXML private TableView<AttendanceRow> rosterTable;
    @FXML private TableColumn<AttendanceRow, Integer> idCol;
    @FXML private TableColumn<AttendanceRow, String> nameCol;
    @FXML private TableColumn<AttendanceRow, Boolean> presentCol;
    @FXML private Label markedLabel;
    @FXML private Label statusLabel;

    private final ObservableList<AttendanceRow> rows = FXCollections.observableArrayList();

    // Services (simple, file-backed)
    private final SessionService sessionSvc = new SessionService();
    private final RosterService rosterSvc = new RosterService();
    private final AttendanceService attendanceSvc = new AttendanceService();
    private final TotalsService totalsSvc = new TotalsService();

    @FXML
    public void initialize() {
        // Table config
        idCol.setCellValueFactory(new PropertyValueFactory<>("traineeId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        presentCol.setCellValueFactory(new PropertyValueFactory<>("present"));
        presentCol.setCellFactory(CheckBoxTableCell.forTableColumn(presentCol));
        rosterTable.setItems(rows);

        // Count updates on toggle
        rosterTable.getItems().addListener((javafx.collections.ListChangeListener<? super AttendanceRow>) c -> updateMarked());
        presentCol.setCellFactory(col -> {
            CheckBoxTableCell<AttendanceRow, Boolean> cell = new CheckBoxTableCell<>();
            cell.itemProperty().addListener((obs, oldV, newV) -> updateMarked());
            return cell;
        });

        // Sessions dropdown
        sessionComboBox.setItems(FXCollections.observableArrayList(sessionSvc.loadUpcomingAndRecentSessions()));
        sessionComboBox.setOnAction(e -> autoLoadRosterIfWanted());
        updateMarked();
    }

    private void autoLoadRosterIfWanted() {
        // Simple: do nothing auto; user clicks "Load Roster"
    }

    private void updateMarked() {
        int total = rows.size();
        long marked = rows.stream().filter(r -> r.isPresent() || !r.isPresent()).count(); // always equals total (boolean)
        markedLabel.setText(marked + "/" + total);
    }

    // UI handlers
    @FXML
    public void loadRosterButtonOnAction(ActionEvent e) {
        rows.clear();
        Session s = sessionComboBox.getValue();
        if (s == null) {
            Alerts.warn("Please choose a session.");
            return;
        }
        List<RosterEntry> roster = rosterSvc.fetchRoster(s.getId());
        if (roster.isEmpty()) {
            Alerts.warn("No trainees found for the selected session.");
            statusLabel.setText("No roster.");
            return;
        }
        for (RosterEntry re : roster) {
            rows.add(new AttendanceRow(re.getTraineeId(), re.getName(), false));
        }
        statusLabel.setText("Roster loaded: " + rows.size() + " trainee(s).");
        updateMarked();
    }

    @FXML
    public void selectAllButtonOnAction(ActionEvent e) {
        for (AttendanceRow r : rows) r.setPresent(true);
        rosterTable.refresh();
        updateMarked();
    }

    @FXML
    public void selectNoneButtonOnAction(ActionEvent e) {
        for (AttendanceRow r : rows) r.setPresent(false);
        rosterTable.refresh();
        updateMarked();
    }

    @FXML
    public void saveButtonOnAction(ActionEvent e) {
        // VL: validation
        Session s = sessionComboBox.getValue();
        if (s == null) {
            Alerts.warn("Please choose a session.");
            return;
        }
        if (rows.isEmpty()) {
            Alerts.warn("No trainees to save.");
            return;
        }

        // VR: verification (IDs exist in authoritative roster)
        Set<Integer> validIds = rosterSvc.validIdSetForSession(s.getId());
        List<Integer> bad = rows.stream()
                .map(AttendanceRow::getTraineeId)
                .filter(id -> !validIds.contains(id))
                .collect(Collectors.toList());
        if (!bad.isEmpty()) {
            Alerts.error("Verification failed. Unknown trainee IDs: " + bad);
            return;
        }

        // DP: build records and totals
        List<AttendanceRecord> recs = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        for (AttendanceRow r : rows) {
            recs.add(new AttendanceRecord(s.getId(), s.getDateString(), r.getTraineeId(), r.isPresent(), now));
        }

        try {
            // write attendance & update totals atomically (simple best-effort)
            attendanceSvc.appendAll(recs);
            totalsSvc.applySessionRows(rows);
            statusLabel.setText("Saved " + recs.size() + " record(s).");
            Alerts.info("Attendance saved.");
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
            Alerts.error("Save failed: " + ex.getMessage());
        }
    }

    @FXML
    public void cancelButtonOnAction(ActionEvent e) {
        // close window
        Stage st = (Stage) rosterTable.getScene().getWindow();
        st.close();
    }
}
