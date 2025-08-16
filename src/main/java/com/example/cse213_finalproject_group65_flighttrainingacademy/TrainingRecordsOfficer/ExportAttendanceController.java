package com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer;

import com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer.Model.AttendanceRecord;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class ExportAttendanceController {

    private static final String ATTENDANCE_FILE = "data/attendance.bin";

    @FXML private DatePicker monthPicker;
    @FXML private Label statusLabel;

    @FXML
    public void initialize() {
        status(""); // no-op
    }

    @FXML
    private void onExport() {
        LocalDate picked = monthPicker.getValue();
        if (picked == null) {
            alert("Pick any date in the target month.");
            return;
        }
        YearMonth ym = YearMonth.from(picked);

        // Read all records
        List<AttendanceRecord> all = readAll();

        // Filter for the chosen YearMonth
        List<AttendanceRecord> monthRecs = all.stream()
                .filter(r -> r.getSessionDate() != null && YearMonth.from(r.getSessionDate()).equals(ym))
                .collect(Collectors.toList());

        // Compute totals
        long total = monthRecs.size();
        long present = monthRecs.stream().filter(AttendanceRecord::isPresent).count();
        long absent = total - present;

        // Build report text
        StringBuilder sb = new StringBuilder();
        sb.append("Attendance Summary for ").append(ym).append(System.lineSeparator());
        sb.append("--------------------------------------------------").append(System.lineSeparator());
        sb.append("Total records : ").append(total).append(System.lineSeparator());
        sb.append("Present       : ").append(present).append(System.lineSeparator());
        sb.append("Absent        : ").append(absent).append(System.lineSeparator());
        sb.append(System.lineSeparator());

        // Optional: breakdown by trainee
        Map<String, long[]> byTrainee = new TreeMap<>();
        for (AttendanceRecord r : monthRecs) {
            long[] pa = byTrainee.computeIfAbsent(r.getTraineeId(), k -> new long[2]); // [present, absent]
            if (r.isPresent()) pa[0]++; else pa[1]++;
        }
        if (!byTrainee.isEmpty()) {
            sb.append("By Trainee").append(System.lineSeparator());
            sb.append("TraineeID       Present  Absent").append(System.lineSeparator());
            for (Map.Entry<String, long[]> e : byTrainee.entrySet()) {
                String id = e.getKey();
                long[] pa = e.getValue();
                sb.append(String.format("%-15s %7d  %6d", id, pa[0], pa[1]))
                        .append(System.lineSeparator());
            }
            sb.append(System.lineSeparator());
        }

        // Suggest filename: attendance_summary_YYYY-MM.txt
        String suggested = "attendance_summary_" + ym.format(DateTimeFormatter.ofPattern("yyyy-MM")) + ".txt";

        FileChooser fc = new FileChooser();
        fc.setTitle("Save Attendance Summary");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files (*.txt)", "*.txt"));
        fc.setInitialFileName(suggested);

        File out = fc.showSaveDialog(getStage());
        if (out == null) return; // user canceled

        // Write file
        try (BufferedWriter w = Files.newBufferedWriter(out.toPath())) {
            w.write(sb.toString());
        } catch (IOException e) {
            error("Failed to write file: " + e.getMessage());
            return;
        }

        info("Exported: " + out.getAbsolutePath());
        status("Exported");
    }

    // ---------- .bin I/O ----------
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

    // ---------- helpers ----------
    private Stage getStage() {
        return (Stage) monthPicker.getScene().getWindow();
    }
    private void info(String m)  { new Alert(Alert.AlertType.INFORMATION, m, ButtonType.OK).showAndWait(); }
    private void error(String m) { new Alert(Alert.AlertType.ERROR, m, ButtonType.OK).showAndWait(); }
    private void alert(String m) { new Alert(Alert.AlertType.WARNING, m, ButtonType.OK).showAndWait(); }
    private void status(String m){ statusLabel.setText(m); }
}
