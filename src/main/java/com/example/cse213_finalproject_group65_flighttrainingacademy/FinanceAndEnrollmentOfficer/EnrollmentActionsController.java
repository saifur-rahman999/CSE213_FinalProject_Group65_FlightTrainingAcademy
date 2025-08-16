package com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer;


import com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.Model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class EnrollmentActionsController {

    @FXML private TableView<Student> table;
    @FXML private TableColumn<Student, Integer> colId;
    @FXML private TableColumn<Student, String>  colName;
    @FXML private TableColumn<Student, Integer> colMissed;
    @FXML private TableColumn<Student, String>  colGrade;
    @FXML private Label statusLabel;

    // Dummy data (now using the separated model class)
    private final ObservableList<Student> data = FXCollections.observableArrayList(
            new Student(2001, "Ayesha Karim",   5,  "B"),
            new Student(2002, "Tanvir Ahmed",  14,  "C"),
            new Student(2003, "Shafin Rahman",  2,  "F"),
            new Student(2004, "Mithila Noor",  11,  "D"),
            new Student(2005, "Arif Hasan",     9,  "E"),
            new Student(2006, "Raka Chowdhury",16,  "A"),
            new Student(2007, "Nadia Islam",    8,  "C")
    );

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colMissed.setCellValueFactory(new PropertyValueFactory<>("attendanceMissed"));
        colGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));

        table.setItems(data);
        statusLabel.setText("Loaded " + data.size() + " students.");
    }

    @FXML
    private void onWithdraw() {
        // Withdraw students with attendance missed < 12 (as requested)
        List<Student> affected = data.stream()
                .filter(s -> s.getAttendanceMissed() < 12)
                .collect(Collectors.toList());

        showResult("Withdraw",
                affected,
                "No students matched (attendance missed < 12).",
                "Withdrawn (" + affected.size() + "):\n" + formatList(affected));
    }

    @FXML
    private void onDefer() {
        // Defer students with grade less than D (E/F, etc.)
        List<Student> affected = data.stream()
                .filter(s -> gradeRank(s.getGrade()) < gradeRank("D"))
                .collect(Collectors.toList());

        showResult("Defer",
                affected,
                "No students matched (grade < D).",
                "Deferred (" + affected.size() + "):\n" + formatList(affected));
    }

    // ------------ helpers ------------
    private void showResult(String action, List<Student> affected, String emptyMsg, String successMsg) {
        if (affected.isEmpty()) {
            info(action, emptyMsg);
            statusLabel.setText("No matches for " + action + ".");
        } else {
            info(action, successMsg);
            statusLabel.setText(action + " — affected: " + affected.size());
        }
    }

    private String formatList(List<Student> list) {
        return list.stream()
                .map(s -> s.getId() + " — " + s.getName()
                        + "  (missed: " + s.getAttendanceMissed()
                        + ", grade: " + s.getGrade() + ")")
                .collect(Collectors.joining("\n"));
    }

    /** Higher is better: A=4, B=3, C=2, D=1, others (E/F) = 0. */
    private int gradeRank(String g) {
        if (g == null) return 0;
        switch (g.trim().toUpperCase(Locale.ROOT)) {
            case "A": return 4;
            case "B": return 3;
            case "C": return 2;
            case "D": return 1;
            default:  return 0; // E/F or unknown
        }
    }

    private void info(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
