package com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

/**
 * FEO-7 — Intake Summary (Dummy, no file I/O)
 * DatePicker is intentionally ignored: "View" shows the same data every time.
 */
public class IntakeSummaryController {

    // UI refs
    @FXML private DatePicker intakePicker; // ignored by design
    @FXML private Label lblTotal, lblActive, lblPending, lblDeferred, lblWithdrawn;

    @FXML private TableView<ApplicantRow> table;
    @FXML private TableColumn<ApplicantRow, Number> colId;
    @FXML private TableColumn<ApplicantRow, String> colName;
    @FXML private TableColumn<ApplicantRow, String> colIntake;
    @FXML private TableColumn<ApplicantRow, String> colStatus;

    // Static/dummy dataset (always shown)
    private final ObservableList<ApplicantRow> allApplicants = FXCollections.observableArrayList(
            new ApplicantRow(101, "Ayesha Karim",   LocalDate.of(2025, 8,  5), Status.Pending),
            new ApplicantRow(102, "Tanvir Ahmed",   LocalDate.of(2025, 8, 12), Status.Active),
            new ApplicantRow(103, "Shafin Rahman",  LocalDate.of(2025, 8, 20), Status.Deferred),
            new ApplicantRow(104, "Mithila Noor",   LocalDate.of(2025, 9,  3), Status.Active),
            new ApplicantRow(105, "Arif Hasan",     LocalDate.of(2025, 9, 15), Status.Pending),
            new ApplicantRow(106, "Raka Chowdhury", LocalDate.of(2025,10,  1), Status.Withdrawn),
            new ApplicantRow(107, "Nadia Islam",    LocalDate.of(2025,10, 21), Status.Active)
    );

    @FXML
    public void initialize() {
        // Table bindings
        colId.setCellValueFactory(c -> new SimpleLongProperty(c.getValue().getId()));
        colName.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getName()));
        colIntake.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getIntakeDate().toString().substring(0, 7)) // yyyy-MM
        );
        colStatus.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getStatus().label));

        table.setItems(FXCollections.observableArrayList());

        // Optional: visually indicate the picker is ignored
        // intakePicker.setDisable(true);
        // intakePicker.setPromptText("Ignored in this demo");
    }

    @FXML
    private void onView() {
        // IGNORE intakePicker — always show the same dataset
        table.setItems(FXCollections.observableArrayList(allApplicants));

        // KPI counts over the full dataset
        Map<Status, Long> counts = new EnumMap<>(Status.class);
        for (Status s : Status.values()) counts.put(s, 0L);
        allApplicants.forEach(a -> counts.computeIfPresent(a.getStatus(), (k, v) -> v + 1));

        long total = allApplicants.size();
        lblTotal.setText(String.valueOf(total));
        lblActive.setText(String.valueOf(counts.get(Status.Active)));
        lblPending.setText(String.valueOf(counts.get(Status.Pending)));
        lblDeferred.setText(String.valueOf(counts.get(Status.Deferred)));
        lblWithdrawn.setText(String.valueOf(counts.get(Status.Withdrawn)));
    }

    // Back → FEODashBoard.fxml
    @FXML
    private void backToDashboard(ActionEvent e) {
        try {
            // If you have a FEODashBoard class:
            URL url = FEODashBoard.class.getResource("FEODashBoard.fxml");

            // If not, use absolute path instead:
            // URL url = IntakeSummaryController.class.getResource(
            //     "/com/example/cse213_finalproject_group65_flighttrainingacademy/FinanceAndEnrollmentOfficer/FEODashBoard.fxml"
            // );

            Parent root = FXMLLoader.load(Objects.requireNonNull(url));
            Stage st = (Stage) ((Node) e.getSource()).getScene().getWindow();
            st.setScene(new Scene(root));
            st.show();
        } catch (Exception ex) {
            ex.printStackTrace();
            showInfo("Navigation", "Could not load FEODashBoard.fxml");
        }
    }

    private void showInfo(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }

    // ---- Simple DTO for the table ----
    public static class ApplicantRow {
        private final long id;
        private final String name;
        private final LocalDate intakeDate; // still stored, but not used for filtering
        private final Status status;

        public ApplicantRow(long id, String name, LocalDate intakeDate, Status status) {
            this.id = id; this.name = name; this.intakeDate = intakeDate; this.status = status;
        }

        public long getId() { return id; }
        public String getName() { return name; }
        public LocalDate getIntakeDate() { return intakeDate; }
        public Status getStatus() { return status; }
    }

    public enum Status {
        Pending("Pending"),
        Active("Active"),
        Deferred("Deferred"),
        Withdrawn("Withdrawn");

        public final String label;
        Status(String label) { this.label = label; }
        @Override public String toString() { return label; }
    }
}
