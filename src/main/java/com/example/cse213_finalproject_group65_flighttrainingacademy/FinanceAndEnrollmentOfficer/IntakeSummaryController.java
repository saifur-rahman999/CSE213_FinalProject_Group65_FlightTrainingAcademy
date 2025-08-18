package com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer;

import com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.Model.EnrollmentStatus;
import com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.Model.IntakeApplicant;
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

public class IntakeSummaryController {

    // UI refs
    @FXML private DatePicker intakePicker; // ignored by design
    @FXML private Label lblTotal, lblActive, lblPending, lblDeferred, lblWithdrawn;

    @FXML private TableView<IntakeApplicant> table;
    @FXML private TableColumn<IntakeApplicant, Number> colId;
    @FXML private TableColumn<IntakeApplicant, String> colName;
    @FXML private TableColumn<IntakeApplicant, String> colIntake;
    @FXML private TableColumn<IntakeApplicant, String> colStatus;

    // Static/dummy dataset (always shown regardless of date)
    private final ObservableList<IntakeApplicant> allApplicants = FXCollections.observableArrayList(
            new IntakeApplicant(101, "Ayesha Karim",   LocalDate.of(2025, 8,  5), EnrollmentStatus.Pending),
            new IntakeApplicant(102, "Tanvir Ahmed",   LocalDate.of(2025, 8, 12), EnrollmentStatus.Active),
            new IntakeApplicant(103, "Shafin Rahman",  LocalDate.of(2025, 8, 20), EnrollmentStatus.Deferred),
            new IntakeApplicant(104, "Mithila Noor",   LocalDate.of(2025, 9,  3), EnrollmentStatus.Active),
            new IntakeApplicant(105, "Arif Hasan",     LocalDate.of(2025, 9, 15), EnrollmentStatus.Pending),
            new IntakeApplicant(106, "Raka Chowdhury", LocalDate.of(2025,10,  1), EnrollmentStatus.Withdrawn),
            new IntakeApplicant(107, "Nadia Islam",    LocalDate.of(2025,10, 21), EnrollmentStatus.Active)
    );

    @FXML
    public void initialize() {
        colId.setCellValueFactory(c -> new SimpleLongProperty(c.getValue().getId()));
        colName.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getName()));
        colIntake.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getIntakeDate().toString().substring(0, 7)) // yyyy-MM
        );
        colStatus.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getStatus().label)
        );

        table.setItems(FXCollections.observableArrayList());

        // Optional: show that picker is ignored
        // intakePicker.setDisable(true);
        // intakePicker.setPromptText("Ignored in this demo");
    }

    @FXML
    private void onView() {
        // Ignore DatePicker — always show the same dataset
        table.setItems(FXCollections.observableArrayList(allApplicants));

        // KPI counts over full dataset
        Map<EnrollmentStatus, Long> counts = new EnumMap<>(EnrollmentStatus.class);
        for (EnrollmentStatus s : EnrollmentStatus.values()) counts.put(s, 0L);
        allApplicants.forEach(a -> counts.computeIfPresent(a.getStatus(), (k, v) -> v + 1));

        long total = allApplicants.size();
        lblTotal.setText(String.valueOf(total));
        lblActive.setText(String.valueOf(counts.get(EnrollmentStatus.Active)));
        lblPending.setText(String.valueOf(counts.get(EnrollmentStatus.Pending)));
        lblDeferred.setText(String.valueOf(counts.get(EnrollmentStatus.Deferred)));
        lblWithdrawn.setText(String.valueOf(counts.get(EnrollmentStatus.Withdrawn)));
    }

    // Back → FEODashBoard.fxml
    @FXML
    private void backToDashboard(ActionEvent e) {
        try {
            URL url = FEODashBoard.class.getResource("FEODashboard.fxml");
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
}
