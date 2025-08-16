package com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer;

import com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer.Model.AttendanceRecord;
import com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer.Model.ClassSession;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RecordAttendanceController {

    // Fixed path per spec
    private static final String ATTENDANCE_FILE = "data/attendance.bin";

    @FXML private Label sessionInfoLabel;
    @FXML private Label countsLabel;

    @FXML private TableView<AttendanceRow> rosterTable;
    @FXML private TableColumn<AttendanceRow, String>  colId;
    @FXML private TableColumn<AttendanceRow, String>  colName;
    @FXML private TableColumn<AttendanceRow, Boolean> colPresent;

    private ClassSession session;                       // injected from caller
    private final ObservableList<AttendanceRow> rows = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("traineeId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));

        colPresent.setCellValueFactory(cd -> cd.getValue().presentProperty());
        colPresent.setCellFactory(CheckBoxTableCell.forTableColumn(colPresent));

        rosterTable.setItems(rows);
        updateCountsLabel();
    }

    /** Call this immediately after loading FXML. */
    public void setSession(ClassSession s) {
        this.session = s;
        if (s == null) {
            sessionInfoLabel.setText("Session: (none)");
            return;
        }
        sessionInfoLabel.setText("Session #" + s.getSessionId() +
                " | " + s.getDate() + " | " + s.getType() +
                " | Trainee " + s.getTraineeId());

        // --- Build a minimal roster for the session (dummy, from the session itself) ---
        // If you later support multi-trainee sessions, populate more rows here.
        String id = s.getTraineeId();
        String name = TraineeDirectory.nameFor(id); // dummy name resolver
        rows.setAll(new AttendanceRow(id, name, true)); // default present=true
        updateCountsLabel();
    }

    @FXML
    private void onSave() {
        if (session == null) {
            info("No session selected.");
            return;
        }

        // VL: every trainee marked (checkbox exists, defaults to false if unchecked)
        // (already satisfied — CheckBox defaults to false when not ticked)

        // VR: session id valid; trainees exist in roster (ensured by construction)
        int sessionId = session.getSessionId();

        // DP:
        // 1) Read list from attendance.bin (else empty)
        List<AttendanceRecord> all = readAll();

        // 2) Remove old records for this session (optional per spec -> we do it)
        for (Iterator<AttendanceRecord> it = all.iterator(); it.hasNext();) {
            if (it.next().getSessionId() == sessionId) it.remove();
        }

        // 3) Append new ticks
        for (AttendanceRow r : rows) {
            all.add(new AttendanceRecord(
                    sessionId,
                    session.getDate(),
                    r.getTraineeId(),
                    r.getName(),
                    r.isPresent()
            ));
        }

        // 4) Write back to attendance.bin
        writeAll(all);

        // OP:
        updateCountsLabel();
        info("Saved to attendance.bin");
        close();
    }

    @FXML
    private void onCancel() {
        close();
    }

    private void close() {
        Stage st = (Stage) rosterTable.getScene().getWindow();
        if (st != null) st.close();
    }

    private void updateCountsLabel() {
        long present = rows.stream().filter(AttendanceRow::isPresent).count();
        long absent  = rows.size() - present;
        countsLabel.setText("Present: " + present + " | Absent: " + absent);
    }

    private void info(String m) {
        new Alert(Alert.AlertType.INFORMATION, m, ButtonType.OK).showAndWait();
    }

    // ---------- Simple .bin serialization (List<AttendanceRecord>) ----------

    @SuppressWarnings("unchecked")
    private List<AttendanceRecord> readAll() {
        Path p = Path.of(ATTENDANCE_FILE);
        if (!Files.exists(p)) return new ArrayList<>();
        try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(Files.newInputStream(p)))) {
            Object o = in.readObject();
            if (o instanceof List) return (List<AttendanceRecord>) o;
        } catch (Exception ignored) { }
        return new ArrayList<>();
    }

    private void writeAll(List<AttendanceRecord> list) {
        try {
            Path p = Path.of(ATTENDANCE_FILE).toAbsolutePath();
            if (p.getParent() != null) Files.createDirectories(p.getParent());
            try (ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(Files.newOutputStream(p)))) {
                out.writeObject(list);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to write " + ATTENDANCE_FILE, e);
        }
    }

    // ---------- Table row (UI-only) ----------
    public static class AttendanceRow {
        private final StringProperty traineeId = new SimpleStringProperty();
        private final StringProperty name      = new SimpleStringProperty();
        private final BooleanProperty present  = new SimpleBooleanProperty(false);

        public AttendanceRow(String traineeId, String name, boolean present) {
            this.traineeId.set(traineeId);
            this.name.set(name);
            this.present.set(present);
        }

        public String getTraineeId() { return traineeId.get(); }
        public String getName()      { return name.get(); }
        public boolean isPresent()   { return present.get(); }

        public StringProperty traineeIdProperty() { return traineeId; }
        public StringProperty nameProperty()      { return name; }
        public BooleanProperty presentProperty()  { return present; }
    }

    // ---------- Dummy name resolver ----------
    static class TraineeDirectory {
        static String nameFor(String id) {
            return switch (id) {
                case "TP-001" -> "A. Rahman";
                case "TP-002" -> "B. Islam";
                case "TP-003" -> "C. Karim";
                default -> "Unknown";
            };
        }
    }
}
