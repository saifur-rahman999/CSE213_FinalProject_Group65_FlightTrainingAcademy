package com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.stream.Collectors;

public class MaintainInstructorQualifications {

    // Selection & form
    @FXML private ComboBox<String> instructorCombo;          // e.g., "INS-2001"
    @FXML private TextField instructorNameField;              // read-only
    @FXML private TextField ratingField;
    @FXML private DatePicker expiryPicker;
    @FXML private TextArea notesArea;

    // Table & filters
    @FXML private TableView<InstructorQualification> qualsTable;
    @FXML private TableColumn<InstructorQualification, String> ratingCol;
    @FXML private TableColumn<InstructorQualification, String> expiryCol;
    @FXML private TableColumn<InstructorQualification, Long> daysLeftCol;
    @FXML private TableColumn<InstructorQualification, String> notesCol;
    @FXML private CheckBox showExpiringOnlyCheck;

    // UX
    @FXML private Label errorLabel;
    @FXML private Label statusLabel;
    @FXML private Button addBtn, updateBtn, deleteBtn;

    // In-memory “repo”
    private final ObservableList<Instructor> instructors = FXCollections.observableArrayList();
    private final ObservableList<InstructorQualification> allQualifications = FXCollections.observableArrayList();
    private final ObservableList<InstructorQualification> viewForSelected = FXCollections.observableArrayList();

    private static final int EXPIRY_SOON_DAYS = 30;

    @FXML
    private void initialize() {
        // Seed demo instructors (replace with your repo later)
        instructors.addAll(
                new Instructor("INS-2001", "Rahman"),
                new Instructor("INS-2002", "Khan"),
                new Instructor("INS-2003", "Alam")
        );
        instructorCombo.setItems(FXCollections.observableArrayList(
                instructors.stream().map(Instructor::id).collect(Collectors.toList())
        ));

        // Table columns
        ratingCol.setCellValueFactory(new PropertyValueFactory<>("rating"));
        expiryCol.setCellValueFactory(c ->
                new ReadOnlyObjectWrapper<>(c.getValue().getExpiryDate() == null
                        ? ""
                        : c.getValue().getExpiryDate().toString()));
        daysLeftCol.setCellValueFactory(c ->
                new ReadOnlyObjectWrapper<>(daysLeft(c.getValue().getExpiryDate())));
        notesCol.setCellValueFactory(new PropertyValueFactory<>("notes"));

        qualsTable.setItems(viewForSelected);

        // Row highlight for near/over expiry
        qualsTable.setRowFactory(tv -> new TableRow<>() {
            @Override protected void updateItem(InstructorQualification item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null || item.getExpiryDate() == null) {
                    setStyle("");
                } else {
                    long left = daysLeft(item.getExpiryDate());
                    if (left < 0) {
                        setStyle("-fx-background-color: #fee2e2;"); // expired: red-ish
                    } else if (left <= EXPIRY_SOON_DAYS) {
                        setStyle("-fx-background-color: #fef9c3;"); // soon: yellow-ish
                    } else {
                        setStyle("");
                    }
                }
            }
        });

        // Default dates
        expiryPicker.setValue(LocalDate.now().plusMonths(6));

        // Respond to selection changes
        instructorCombo.valueProperty().addListener((obs, oldV, newV) -> onInstructorChanged(newV));

        // Buttons state
        setButtonsEnabled(false);
        qualsTable.getSelectionModel().selectedItemProperty().addListener((o, a, b) -> {
            boolean hasSel = b != null;
            updateBtn.setDisable(!hasSel);
            deleteBtn.setDisable(!hasSel);
            if (hasSel) {
                // Load into form for quick edits
                ratingField.setText(b.getRating());
                expiryPicker.setValue(b.getExpiryDate());
                notesArea.setText(b.getNotes());
            }
        });

        clearError();
        setStatus("Ready.");
    }

    private void onInstructorChanged(String instructorId) {
        clearError();
        setStatus("Loading qualifications…");

        // Map ID -> name
        Instructor ins = findInstructor(instructorId);
        instructorNameField.setText(ins != null ? ins.name() : "");

        // Filter table view for this instructor
        viewForSelected.setAll(allQualifications.stream()
                .filter(q -> Objects.equals(q.getInstructorId(), instructorId))
                .collect(Collectors.toList()));

        applyExpiringFilterIfNeeded();

        setButtonsEnabled(instructorId != null);
        setStatus("Ready.");
    }

    private void applyExpiringFilterIfNeeded() {
        if (showExpiringOnlyCheck.isSelected()) {
            String id = instructorCombo.getValue();
            viewForSelected.setAll(allQualifications.stream()
                    .filter(q -> Objects.equals(q.getInstructorId(), id))
                    .filter(q -> q.getExpiryDate() != null && daysLeft(q.getExpiryDate()) <= EXPIRY_SOON_DAYS)
                    .collect(Collectors.toList()));
        }
    }

    // ==== Actions ====

    @FXML
    private void onAdd() {
        clearError();

        String id = instructorCombo.getValue();
        if (id == null) return showError("Select an instructor first.");

        String rating = safe(ratingField.getText());
        LocalDate expiry = expiryPicker.getValue();

        // Validation
        if (rating.isEmpty()) return showError("Enter at least one rating.");
        if (expiry == null || !expiry.isAfter(LocalDate.now()))
            return showError("Expiry date must be in the future.");

        // Verification
        if (findInstructor(id) == null) return showError("Instructor not found.");
        boolean duplicate = allQualifications.stream()
                .anyMatch(q -> q.getInstructorId().equals(id)
                        && q.getRating().equalsIgnoreCase(rating));
        if (duplicate) return showError("This rating already exists for the instructor.");

        // Add
        InstructorQualification q = new InstructorQualification(id, rating, expiry, safe(notesArea.getText()));
        allQualifications.add(q);
        if (Objects.equals(q.getInstructorId(), instructorCombo.getValue())) {
            viewForSelected.add(q);
            applyExpiringFilterIfNeeded();
            qualsTable.refresh();
        }

        setStatus("Qualification added.");
        maybeWarnSoonExpiry(expiry);
        clearForm();
    }

    @FXML
    private void onUpdate() {
        clearError();

        InstructorQualification sel = qualsTable.getSelectionModel().getSelectedItem();
        if (sel == null) return showError("Select a row to update.");

        String rating = safe(ratingField.getText());
        LocalDate expiry = expiryPicker.getValue();

        if (rating.isEmpty()) return showError("Rating cannot be empty.");
        if (expiry == null || !expiry.isAfter(LocalDate.now()))
            return showError("Expiry date must be in the future.");

        // Prevent duplicate ratings for the same instructor if user changed rating text
        boolean duplicate = allQualifications.stream()
                .anyMatch(q -> q != sel
                        && q.getInstructorId().equals(sel.getInstructorId())
                        && q.getRating().equalsIgnoreCase(rating));
        if (duplicate) return showError("This rating already exists for the instructor.");

        sel.setRating(rating);
        sel.setExpiryDate(expiry);
        sel.setNotes(safe(notesArea.getText()));

        qualsTable.refresh();
        setStatus("Qualification updated.");
        maybeWarnSoonExpiry(expiry);
    }

    @FXML
    private void onDelete() {
        clearError();

        InstructorQualification sel = qualsTable.getSelectionModel().getSelectedItem();
        if (sel == null) return showError("Select a row to delete.");

        allQualifications.remove(sel);
        viewForSelected.remove(sel);
        qualsTable.getSelectionModel().clearSelection();
        setStatus("Qualification deleted.");
    }

    @FXML
    private void onBack() {
        // Close this window or navigate back (adjust to your navigation scheme)
        ((Stage) statusLabel.getScene().getWindow()).close();
    }

    @FXML
    private void onToggleExpiringOnly() {
        onInstructorChanged(instructorCombo.getValue());
    }

    // ==== Helpers ====

    private Instructor findInstructor(String id) {
        if (id == null) return null;
        return instructors.stream().filter(i -> i.id().equals(id)).findFirst().orElse(null);
    }

    private long daysLeft(LocalDate date) {
        if (date == null) return Long.MAX_VALUE;
        return ChronoUnit.DAYS.between(LocalDate.now(), date);
    }

    private void maybeWarnSoonExpiry(LocalDate expiry) {
        if (expiry != null) {
            long left = daysLeft(expiry);
            if (left >= 0 && left <= EXPIRY_SOON_DAYS) {
                setStatus("Warning: qualification expires in " + left + " day(s).");
            }
        }
    }

    private void setButtonsEnabled(boolean enabled) {
        addBtn.setDisable(!enabled);
        updateBtn.setDisable(true);
        deleteBtn.setDisable(true);
    }

    private void clearForm() {
        ratingField.clear();
        notesArea.clear();
        expiryPicker.setValue(LocalDate.now().plusMonths(6));
    }

    private void showError(String msg) {
        errorLabel.setText(msg);
    }

    private void clearError() {
        errorLabel.setText("");
    }

    private void setStatus(String msg) {
        statusLabel.setText(msg);
    }

    private String safe(String s) { return s == null ? "" : s.trim(); }

    // === Minimal demo “models” (you can move these into their own files) ===

    /** Minimal instructor record for the combo + name display. */
    public record Instructor(String id, String name) {}

    /** Model for a single qualification entry. */
    public static class InstructorQualification {
        private String instructorId;
        private String rating;
        private LocalDate expiryDate;
        private String notes;

        public InstructorQualification(String instructorId, String rating, LocalDate expiryDate, String notes) {
            this.instructorId = instructorId;
            this.rating = rating;
            this.expiryDate = expiryDate;
            this.notes = notes;
        }

        public String getInstructorId() { return instructorId; }
        public void setInstructorId(String instructorId) { this.instructorId = instructorId; }

        public String getRating() { return rating; }
        public void setRating(String rating) { this.rating = rating; }

        public LocalDate getExpiryDate() { return expiryDate; }
        public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }

        public String getNotes() { return notes; }
        public void setNotes(String notes) { this.notes = notes; }
    }
}
