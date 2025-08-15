package com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer;

import com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer.Model.ArchivedTrainee;
import com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer.Model.Trainee;
import com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer.Model.TraineeRepository;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Predicate;

public class ArchiveTraineeRecords {

    // FXML refs
    @FXML private TableView<Trainee> activeTable;
    @FXML private TableColumn<Trainee, String> colId, colName, colCourse, colStatus;
    @FXML private TableColumn<Trainee, Integer> colOpen;
    @FXML private TextField searchField;
    @FXML private Label activeCountLabel, statusLabel;
    @FXML private Button archiveSelectedBtn;

    private FilteredList<Trainee> filtered;

    @FXML
    private void initialize() {
        // event‑2 (OP): load active trainees
        colId.setCellValueFactory(c -> c.getValue().idProperty());
        colName.setCellValueFactory(c -> c.getValue().nameProperty());
        colCourse.setCellValueFactory(c -> c.getValue().courseProperty());
        colStatus.setCellValueFactory(c -> c.getValue().statusProperty());
        colOpen.setCellValueFactory(c -> c.getValue().openSessionsProperty().asObject());

        filtered = new FilteredList<>(TraineeRepository.getInstance().getActiveTrainees(), t -> true);
        activeTable.setItems(filtered);
        refreshCounts();
        status("Ready.");
    }

    // event‑1 (UIE): open screen → already handled by navigation in your app

    // Search/filter
    @FXML
    private void onSearch() {
        String q = searchField.getText();
        Predicate<Trainee> p = (q == null || q.isBlank())
                ? t -> true
                : t -> t.getId().toLowerCase().contains(q.toLowerCase())
                || t.getName().toLowerCase().contains(q.toLowerCase());
        filtered.setPredicate(p);
        refreshCounts();
    }

    // event‑3 (UIE): select trainee → click Archive Selected
    @FXML
    private void onArchiveSelected() {
        Trainee selected = activeTable.getSelectionModel().getSelectedItem();

        // event‑5 (VL): basic validation
        if (selected == null) { alertError("Please select a trainee to archive."); return; }

        // event‑4 (UID): reason & note dialog
        ArchiveDialogData data = showArchiveDialog();
        if (data == null) return; // cancelled

        if (selected.getOpenSessions() > 0) {
            alertError("Close or cancel open sessions first (OpenSessions = " + selected.getOpenSessions() + ").");
            return;
        }
        if (data.reason == null) {
            alertError("Please choose an archive reason (Completed / Withdrawn).");
            return;
        }

        // event‑6 (VR): verify against current data (still active? pending assessments? holds?)
        TraineeRepository repo = TraineeRepository.getInstance();
        if (!repo.isCurrentlyActive(selected.getId())) {
            alertError("This trainee is no longer in the active set (possibly archived by someone else).");
            refreshCounts();
            return;
        }
        if (repo.hasPendingAssessments(selected.getId())) {
            alertError("There are pending assessments tied to open sessions. Resolve them before archiving.");
            return;
        }
        if (repo.hasFinanceHold(selected.getId())) {
            // Optional policy: block or warn. Here we block.
            alertError("Finance hold is unresolved for this trainee.");
            return;
        }

        // event‑7 (DP): process the change
        ArchivedTrainee archived = new ArchivedTrainee(
                selected.getId(),
                selected.getName(),
                selected.getCourse(),
                data.reason,                  // finalStatus
                LocalDate.now(),              // archiveDate
                data.note == null ? "" : data.note.trim()
        );
        repo.moveToArchive(selected.getId(), archived);

        // event‑8 (OP): update UI & feedback
        refreshCounts();
        status("Archived successfully: " + selected.getName() + " — " + data.reason + ".");
        alertInfo("Archived successfully:\n" + selected.getName() + " — " + data.reason);
    }

    @FXML
    private void onViewArchive() {
        // Simple viewer dialog; in a bigger app, navigate to dedicated screen
        Dialog<Void> dlg = new Dialog<>();
        dlg.setTitle("Archived Trainees");
        dlg.initModality(Modality.APPLICATION_MODAL);

        TableView<ArchivedTrainee> tv = new TableView<>(TraineeRepository.getInstance().getArchivedTrainees());
        TableColumn<ArchivedTrainee, String> c1 = new TableColumn<>("ID");
        c1.setCellValueFactory(c -> c.getValue().traineeIdProperty());
        TableColumn<ArchivedTrainee, String> c2 = new TableColumn<>("Name");
        c2.setCellValueFactory(c -> c.getValue().nameProperty());
        TableColumn<ArchivedTrainee, String> c3 = new TableColumn<>("Course");
        c3.setCellValueFactory(c -> c.getValue().courseProperty());
        TableColumn<ArchivedTrainee, String> c4 = new TableColumn<>("Final Status");
        c4.setCellValueFactory(c -> c.getValue().finalStatusProperty());
        TableColumn<ArchivedTrainee, String> c5 = new TableColumn<>("Archive Date");
        c5.setCellValueFactory(c -> c.getValue().archiveDateProperty());
        TableColumn<ArchivedTrainee, String> c6 = new TableColumn<>("Note");
        c6.setCellValueFactory(c -> c.getValue().noteProperty());
        tv.getColumns().addAll(c1,c2,c3,c4,c5,c6);
        tv.setPrefSize(760, 420);

        dlg.getDialogPane().setContent(tv);
        dlg.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dlg.showAndWait();
    }

    @FXML
    private void onBack() {
        // Close this window; your app can navigate differently
        Stage st = (Stage) activeTable.getScene().getWindow();
        st.close();
    }

    private ArchiveDialogData showArchiveDialog() {
        Dialog<ArchiveDialogData> dlg = new Dialog<>();
        dlg.setTitle("Archive Trainee");
        dlg.initModality(Modality.APPLICATION_MODAL);

        // Controls
        ComboBox<String> reason = new ComboBox<>();
        reason.getItems().addAll("Completed", "Withdrawn");
        reason.setPromptText("Select reason");

        TextArea note = new TextArea();
        note.setPromptText("Note (optional)");
        note.setPrefRowCount(3);

        GridPane gp = new GridPane();
        gp.setHgap(8); gp.setVgap(8); gp.setPadding(new Insets(10));
        gp.addRow(0, new Label("Archive Reason:"), reason);
        gp.addRow(1, new Label("Note:"), note);

        dlg.getDialogPane().setContent(gp);
        dlg.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.OK);

        dlg.setResultConverter(bt -> {
            if (bt == ButtonType.OK) return new ArchiveDialogData(reason.getValue(), note.getText());
            return null;
        });

        Optional<ArchiveDialogData> res = dlg.showAndWait();
        return res.orElse(null);
    }

    private void refreshCounts() {
        activeCountLabel.setText("(" + filtered.size() + ")");
        activeTable.refresh();
    }

    private void status(String s) { statusLabel.setText(s); }

    private void alertError(String m) { new Alert(Alert.AlertType.ERROR, m).showAndWait(); }
    private void alertInfo(String m)  { new Alert(Alert.AlertType.INFORMATION, m).showAndWait(); }

    // small data holder for dialog
    private record ArchiveDialogData(String reason, String note) {}
}
