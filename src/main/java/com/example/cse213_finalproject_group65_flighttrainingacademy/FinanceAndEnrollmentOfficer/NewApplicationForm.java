package com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

/**
 * New Application Form — Goal 1 (FEO)
 * Implements:
 *  - event-2: capture inputs
 *  - event-4: validation (name/email/phone/docs)
 *  - event-5: verification (duplicate email/phone, intake exists)
 *  - event-6: processing (create record, status=Pending, index by intake, append to applications.bin)
 *  - event-7: output (toast + status label; provides a simple refresh hook)
 */
public class NewApplicationForm {

    // ====== Constants / Files ======
    private static final Path APPLICATIONS_BIN = Path.of("applications.bin");   // binary file of ApplicationRecord list
    private static final Path INTAKES_TXT = Path.of("intakes.txt");             // text file with one intake per line, e.g., 2025-09
    private static final Path INDEX_BY_INTAKE_BIN = Path.of("applications_index_by_intake.bin"); // Map<String,List<String>> intake -> appIds

    private static final Pattern EMAIL_RX = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern PHONE_11_DIGITS_RX = Pattern.compile("^\\d{11}$");

    // ====== FXML fields ======
    @FXML private TextField appIdField;
    @FXML private ComboBox<String> courseCombo;
    @FXML private CheckBox docTranscriptCheck;
    @FXML private TextArea notesArea;
    @FXML private TextField nameField;
    @FXML private CheckBox docPhotoCheck;
    @FXML private TextField emailField;
    @FXML private ComboBox<String> intakeCombo;
    @FXML private CheckBox docMedicalCheck;
    @FXML private CheckBox docIdCopyCheck;
    @FXML private Label statusLabel;
    @FXML private TextField phoneField;

    // ====== Optional refresh hook ======
    // If the parent controller sets this (e.g., via FXMLLoader controller communication),
    // we’ll call it after a successful save so the Applications table can refresh.
    private Runnable onSavedRefreshHook;

    public void setOnSavedRefreshHook(Runnable hook) {
        this.onSavedRefreshHook = hook;
    }

    // ====== Initialize ======
    @FXML
    private void initialize() {
        // Auto-generate App ID (auto)
        appIdField.setText(generateAppId());
        appIdField.setEditable(false);

        // Populate course combo (you can load from file/db if needed)
        courseCombo.getItems().setAll(
                "Private Pilot License (PPL)",
                "Commercial Pilot License (CPL)",
                "Airline Transport Pilot License (ATPL)",
                "Flight Instructor (FI)"
        );

        // Populate intake combo from intakes.txt (if present)
        Set<String> intakes = loadIntakes();
        if (!intakes.isEmpty()) {
            intakeCombo.getItems().setAll(intakes);
        }

        // Optional placeholder
        statusLabel.setText("Ready.");
    }

    // ====== Event Handlers ======
    @FXML
    public void onBack(ActionEvent actionEvent) {
        // Close this window and return to the dashboard (event‑1 back navigation)
        Node src = (Node) actionEvent.getSource();
        Stage stage = (Stage) src.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void onSave(ActionEvent actionEvent) {
        // event‑3: Enter details — we already have user input bound to fields.

        // event‑4: VALIDATION
        List<String> errors = new ArrayList<>();

        String appId = safeText(appIdField);
        String name = safeText(nameField);
        String email = safeText(emailField);
        String phone = safeText(phoneField);
        String course = valueOf(courseCombo);
        String intake = valueOf(intakeCombo);
        boolean docTranscript = docTranscriptCheck.isSelected();
        boolean docPhoto = docPhotoCheck.isSelected();
        boolean docMedical = docMedicalCheck.isSelected();
        boolean docIdCopy = docIdCopyCheck.isSelected();
        String notes = notesArea == null ? "" : notesArea.getText().trim();

        // VL-1: Name non-empty
        if (name.isBlank()) errors.add("Name is required.");

        // VL-2: Email format
        if (!EMAIL_RX.matcher(email).matches()) errors.add("Invalid email format.");

        // VL-3: Phone must be 11 digits
        if (!PHONE_11_DIGITS_RX.matcher(phone).matches()) errors.add("Phone must be exactly 11 digits.");

        // VL-4: At least mandatory docs checked (assume: Photo + ID Copy mandatory; Transcript typically required too)
        if (!(docPhoto && docIdCopy)) {
            errors.add("Mandatory docs missing: Photo and ID Copy are required.");
        }

        if (course == null || course.isBlank()) errors.add("Please select Intended Course.");
        if (intake == null || intake.isBlank()) errors.add("Please select Intended Intake.");

        if (!errors.isEmpty()) {
            showError("Validation errors", String.join("\n", errors));
            statusLabel.setText("Fix validation errors and try again.");
            return;
        }

        // event‑5: VERIFICATION
        // - No duplicate email/phone in applications.bin
        // - Intake exists in intakes.txt
        Set<String> validIntakes = loadIntakes();
        if (!validIntakes.contains(intake)) {
            showError("Verification failed", "Selected intake does not exist in intakes.txt");
            statusLabel.setText("Intake not found.");
            return;
        }

        List<ApplicationRecord> existing = loadApplications();
        boolean dupEmail = existing.stream().anyMatch(a -> a.email.equalsIgnoreCase(email));
        boolean dupPhone = existing.stream().anyMatch(a -> a.phone.equals(phone));
        if (dupEmail || dupPhone) {
            StringBuilder sb = new StringBuilder("Duplicate detected:\n");
            if (dupEmail) sb.append("• Email already used in another application\n");
            if (dupPhone) sb.append("• Phone already used in another application\n");
            showError("Verification failed", sb.toString());
            statusLabel.setText("Duplicate found.");
            return;
        }

        // event‑6: PROCESSING
        ApplicationRecord newRec = new ApplicationRecord(
                appId,
                name,
                email,
                phone,
                course,
                intake,
                new Docs(docTranscript, docPhoto, docMedical, docIdCopy),
                notes,
                "Pending", // status=Pending
                LocalDateTime.now()
        );

        existing.add(newRec);
        if (!saveApplications(existing)) {
            showError("Save failed", "Could not write to applications.bin");
            statusLabel.setText("Save failed.");
            return;
        }

        // update simple intake index
        updateIntakeIndex(newRec);

        // event‑7: OUTPUT
        statusLabel.setText("Application saved.");
        showInfoToast("Application saved");

        // Optional: let parent refresh the Applications table
        if (onSavedRefreshHook != null) {
            try {
                onSavedRefreshHook.run();
            } catch (Exception ignored) { /* fail-safe */ }
        }

        // Reset form for next entry (keep intake/course selections for faster data entry)
        resetFormPreservingCombos();
        // Generate a fresh App ID for the next record
        appIdField.setText(generateAppId());
    }

    // ====== Helpers ======

    private String generateAppId() {
        // Example: APP-20250815-143512-XYZ (date-time + random suffix)
        String ts = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));
        String suffix = UUID.randomUUID().toString().substring(0, 6).toUpperCase(Locale.ROOT);
        return "APP-" + ts + "-" + suffix;
    }

    private String safeText(TextField tf) {
        return tf == null ? "" : tf.getText().trim();
    }

    private String valueOf(ComboBox<String> cb) {
        return cb == null || cb.getValue() == null ? null : cb.getValue().trim();
    }

    private void resetFormPreservingCombos() {
        nameField.clear();
        emailField.clear();
        phoneField.clear();
        notesArea.clear();
        docTranscriptCheck.setSelected(false);
        docPhotoCheck.setSelected(false);
        docMedicalCheck.setSelected(false);
        docIdCopyCheck.setSelected(false);
    }

    // ====== Files: intakes ======
    private Set<String> loadIntakes() {
        try {
            if (!Files.exists(INTAKES_TXT)) return Collections.emptySet();
            List<String> lines = Files.readAllLines(INTAKES_TXT, StandardCharsets.UTF_8);
            Set<String> out = new LinkedHashSet<>();
            for (String s : lines) {
                String t = s.trim();
                if (!t.isEmpty() && !t.startsWith("#")) out.add(t);
            }
            return out;
        } catch (IOException e) {
            return Collections.emptySet();
        }
    }

    // ====== Files: applications (Serializable list) ======
    @SuppressWarnings("unchecked")
    private List<ApplicationRecord> loadApplications() {
        if (!Files.exists(APPLICATIONS_BIN)) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(Files.newInputStream(APPLICATIONS_BIN)))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                return (List<ApplicationRecord>) obj;
            }
        } catch (Exception ignored) { }
        return new ArrayList<>();
    }

    private boolean saveApplications(List<ApplicationRecord> list) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(Files.newOutputStream(APPLICATIONS_BIN)))) {
            oos.writeObject(list);
            oos.flush();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    // ====== Simple intake index: Map<String, List<String>> ======
    @SuppressWarnings("unchecked")
    private void updateIntakeIndex(ApplicationRecord rec) {
        Map<String, List<String>> index = new LinkedHashMap<>();
        if (Files.exists(INDEX_BY_INTAKE_BIN)) {
            try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(Files.newInputStream(INDEX_BY_INTAKE_BIN)))) {
                Object obj = ois.readObject();
                if (obj instanceof Map<?, ?>) {
                    index = (Map<String, List<String>>) obj;
                }
            } catch (Exception ignored) { }
        }
        index.computeIfAbsent(rec.intake, k -> new ArrayList<>()).add(rec.appId);
        try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(Files.newOutputStream(INDEX_BY_INTAKE_BIN)))) {
            oos.writeObject(index);
            oos.flush();
        } catch (IOException ignored) { }
    }

    // ====== UI Alerts (toast-ish) ======
    private void showInfoToast(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void showError(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    // ====== Serializable Record Types ======
    private static final class Docs implements Serializable {
        final boolean transcript;
        final boolean photo;
        final boolean medical;
        final boolean idCopy;

        Docs(boolean transcript, boolean photo, boolean medical, boolean idCopy) {
            this.transcript = transcript;
            this.photo = photo;
            this.medical = medical;
            this.idCopy = idCopy;
        }
    }

    private static final class ApplicationRecord implements Serializable {
        final String appId;
        final String name;
        final String email;
        final String phone;
        final String course;
        final String intake;
        final Docs docs;
        final String notes;
        final String status; // "Pending" on create
        final LocalDateTime createdAt;

        ApplicationRecord(String appId,
                          String name,
                          String email,
                          String phone,
                          String course,
                          String intake,
                          Docs docs,
                          String notes,
                          String status,
                          LocalDateTime createdAt) {
            this.appId = appId;
            this.name = name;
            this.email = email;
            this.phone = phone;
            this.course = course;
            this.intake = intake;
            this.docs = docs;
            this.notes = notes;
            this.status = status;
            this.createdAt = createdAt;
        }
    }
}
