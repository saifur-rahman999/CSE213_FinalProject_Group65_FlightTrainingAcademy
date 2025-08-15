package com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer;

import com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer.Model.AddSessionModel;
import javafx.beans.binding.BooleanBinding;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class AddSession {

    // ====== FXML fields ======
    @FXML private DatePicker datePicker;
    @FXML private ComboBox<String> typeCombo;
    @FXML private ComboBox<String> aircraftCombo;
    @FXML private TextField durationField;
    @FXML private ComboBox<String> traineeCombo;
    @FXML private ComboBox<String> instructorCombo;
    @FXML private TextArea remarksArea;
    @FXML private Label errorLabel;
    @FXML private Button saveButton; // make sure to add fx:id="saveButton" in FXML

    // ====== Config / styling ======
    private static final PseudoClass PSEUDO_CLASS_ERROR = PseudoClass.getPseudoClass("error");
    private static final Pattern DURATION_PATTERN = Pattern.compile("^([0-9]{0,2})(\\.[0-9]?)?$");
    private static final double MIN_HOURS = 0.1;
    private static final double MAX_HOURS = 10.0;
    private static final int MAX_REMARKS = 500;
    private static final boolean ALLOW_FUTURE_UP_TO_1Y = false; // set true if scheduling is allowed

    @FXML
    private void initialize() {
        // Type
        typeCombo.getItems().setAll("FLIGHT", "SIM");

        // TODO load from repositories/services instead of hard-coded
        aircraftCombo.getItems().setAll("C172 - S2", "PA-28 - Archer", "DA-42 - TwinStar", "SIM-AATD-01");
        traineeCombo.getItems().setAll("TR-1001 | Nabila", "TR-1002 | Ahsan", "TR-1003 | Riad");
        instructorCombo.getItems().setAll("INS-2001 | Rahman", "INS-2002 | Khan", "INS-2003 | Alam");

        // Date
        datePicker.setValue(LocalDate.now());
        datePicker.setDayCellFactory(dp -> new DateCell() {
            @Override public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (empty) return;
                if (ALLOW_FUTURE_UP_TO_1Y) {
                    if (date.isAfter(LocalDate.now().plusYears(1))) setDisable(true);
                } else {
                    if (date.isAfter(LocalDate.now())) setDisable(true);
                }
            }
        });

        // Duration: live numeric filter
        final UnaryOperator<TextFormatter.Change> hoursFilter = change -> {
            String next = change.getControlNewText();
            if (next.isBlank()) return change;
            return DURATION_PATTERN.matcher(next).matches() ? change : null;
        };
        durationField.setTextFormatter(new TextFormatter<>(hoursFilter));

        // Type vs aircraft sanity assist
        typeCombo.valueProperty().addListener((obs, oldV, newV) -> {
            if (newV == null) return;
            if ("SIM".equals(newV)) {
                aircraftCombo.getSelectionModel().clearSelection();
                aircraftCombo.getItems().stream()
                        .filter(s -> s.toUpperCase().contains("SIM"))
                        .findFirst().ifPresent(aircraftCombo.getSelectionModel()::select);
            } else if ("FLIGHT".equals(newV)) {
                if (aircraftCombo.getValue() != null && aircraftCombo.getValue().toUpperCase().contains("SIM")) {
                    aircraftCombo.getSelectionModel().clearSelection();
                }
            }
            clearProblem(aircraftCombo);
        });

        // Save button enable/disable
        if (saveButton != null) {
            BooleanBinding canSave = datePicker.valueProperty().isNotNull()
                    .and(typeCombo.valueProperty().isNotNull())
                    .and(aircraftCombo.valueProperty().isNotNull())
                    .and(traineeCombo.valueProperty().isNotNull())
                    .and(instructorCombo.valueProperty().isNotNull())
                    .and(durationField.textProperty().isNotEmpty());
            saveButton.disableProperty().bind(canSave.not());
        }

        // Clear error text on edits
        Runnable clear = () -> errorLabel.setText("");
        datePicker.valueProperty().addListener((o, a, b) -> clear.run());
        typeCombo.valueProperty().addListener((o, a, b) -> clear.run());
        aircraftCombo.valueProperty().addListener((o, a, b) -> clear.run());
        traineeCombo.valueProperty().addListener((o, a, b) -> clear.run());
        instructorCombo.valueProperty().addListener((o, a, b) -> clear.run());
        durationField.textProperty().addListener((o, a, b) -> clear.run());
        remarksArea.textProperty().addListener((o, a, b) -> clear.run());
    }

    @FXML
    private void onSave() {
        List<String> errs = validateForm();
        if (!errs.isEmpty()) {
            errorLabel.setText(String.join("\n", errs));
            return;
        }

        // Build model
        AddSessionModel model = AddSessionModel.of(
                datePicker.getValue(),
                typeCombo.getValue(),
                aircraftCombo.getValue(),
                Double.parseDouble(durationField.getText().trim()),
                extractId(traineeCombo.getValue()),
                extractId(instructorCombo.getValue()),
                remarksArea.getText()
        );

        // ===== VERIFICATION (replace with real repository/service calls) =====
        if (!verifyReferences(model)) {
            // verifyReferences shows its own alert; stop here
            return;
        }

        // ===== PERSIST (plug in your repo/service) =====
        // Example:
        // SessionsRepository.append(model);
        // TraineeTotalsRepository.updateWith(model);

        // Optional: toast/snackbar here
        closeWindow();
    }

    @FXML
    private void onCancel() { closeWindow(); }

    // ===== Validation =====
    private List<String> validateForm() {
        List<String> errors = new ArrayList<>();
        clearProblem(datePicker, typeCombo, aircraftCombo, traineeCombo, instructorCombo, durationField, remarksArea);

        // Date
        LocalDate d = datePicker.getValue();
        if (d == null) {
            errors.add("Date is required.");
            markProblem(datePicker);
        } else if (ALLOW_FUTURE_UP_TO_1Y ? d.isAfter(LocalDate.now().plusYears(1)) : d.isAfter(LocalDate.now())) {
            errors.add(ALLOW_FUTURE_UP_TO_1Y ? "Date cannot be more than +1 year ahead." : "Date cannot be in the future.");
            markProblem(datePicker);
        }

        // Type
        String type = typeCombo.getValue();
        if (type == null) {
            errors.add("Type is required.");
            markProblem(typeCombo);
        }

        // Aircraft/device
        String craft = aircraftCombo.getValue();
        if (craft == null || craft.isBlank()) {
            errors.add("Aircraft/Device is required.");
            markProblem(aircraftCombo);
        } else {
            if ("FLIGHT".equals(type) && craft.toUpperCase().contains("SIM")) {
                errors.add("For FLIGHT, select a real aircraft (not a SIM device).");
                markProblem(aircraftCombo);
            }
            if ("SIM".equals(type) && !craft.toUpperCase().contains("SIM")) {
                errors.add("For SIM, select a simulator device.");
                markProblem(aircraftCombo);
            }
        }

        // Trainee & Instructor
        String trainee = traineeCombo.getValue();
        String instructor = instructorCombo.getValue();
        if (trainee == null) { errors.add("Trainee is required."); markProblem(traineeCombo); }
        if (instructor == null) { errors.add("Instructor is required."); markProblem(instructorCombo); }
        if (trainee != null && instructor != null && Objects.equals(extractId(trainee), extractId(instructor))) {
            errors.add("Instructor and Trainee must be different persons.");
            markProblem(traineeCombo); markProblem(instructorCombo);
        }

        // Duration
        String durTxt = durationField.getText();
        Double hours = parseHours(durTxt);
        if (durTxt == null || durTxt.isBlank()) {
            errors.add("Duration is required."); markProblem(durationField);
        } else if (hours == null) {
            errors.add("Duration must be a valid number (e.g., 1.0 or 1.5)."); markProblem(durationField);
        } else if (hours < MIN_HOURS || hours > MAX_HOURS) {
            errors.add(String.format("Duration must be between %.1f and %.1f hours.", MIN_HOURS, MAX_HOURS));
            markProblem(durationField);
        }

        // Remarks (optional)
        String remarks = remarksArea.getText();
        if (remarks != null && remarks.length() > MAX_REMARKS) {
            errors.add("Remarks is too long (max " + MAX_REMARKS + " chars).");
            markProblem(remarksArea);
        }

        return errors;
    }

    // ===== Reference verification (stub out with your repositories) =====
    private boolean verifyReferences(AddSessionModel m) {
        // Example repo checks — replace with your real stores:
        boolean traineeOk = existsIn("trainee", m.getTraineeId());
        boolean instructorOk = existsIn("instructor", m.getInstructorId());
        boolean craftOk = existsIn("fleetOrDevices", m.getAircraftOrDevice());

        StringBuilder sb = new StringBuilder();
        if (!traineeOk) sb.append("Unknown trainee: ").append(m.getTraineeId()).append('\n');
        if (!instructorOk) sb.append("Unknown instructor: ").append(m.getInstructorId()).append('\n');
        if (!craftOk) sb.append("Unknown aircraft/device: ").append(m.getAircraftOrDevice()).append('\n');

        if (sb.length() > 0) {
            new Alert(Alert.AlertType.ERROR, sb.toString().trim()).showAndWait();
            return false;
        }
        return true;
    }

    // Dummy lookup; replace with repositories (e.g., Repositories.trainees.exists(id))
    private boolean existsIn(String which, String key) {
        // TODO: real implementations
        return key != null && !key.isBlank();
    }

    // ===== Helpers =====
    private Double parseHours(String txt) {
        try {
            if (txt == null) return null;
            String trimmed = txt.trim();
            return trimmed.isEmpty() ? null : Double.parseDouble(trimmed);
        } catch (Exception e) { return null; }
    }
    private String extractId(String comboDisplay) {
        if (comboDisplay == null) return null;
        int idx = comboDisplay.indexOf('|');
        return (idx > 0) ? comboDisplay.substring(0, idx).trim() : comboDisplay.trim();
    }
    private void markProblem(Control c) {
        c.pseudoClassStateChanged(PSEUDO_CLASS_ERROR, true);
        if (c.getTooltip() == null) c.setTooltip(new Tooltip("Please review this field."));
    }
    private void clearProblem(Control... controls) {
        for (Control c : controls) if (c != null) c.pseudoClassStateChanged(PSEUDO_CLASS_ERROR, false);
    }
    private void closeWindow() { ((Stage) errorLabel.getScene().getWindow()).close(); }
}
