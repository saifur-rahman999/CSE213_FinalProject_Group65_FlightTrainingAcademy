package com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer;


import com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer.Model.ClassSession;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
        import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class EditSessions {

    @FXML private TextField idField;
    @FXML private DatePicker datePicker;      // disabled (view only)
    @FXML private TextField traineeField;     // view only
    @FXML private ComboBox<String> aircraftCombo;
    @FXML private TextField durationField;
    @FXML private TextArea remarksArea;

    private ClassSession session; // reference to the object in DummyStore

    @FXML
    public void initialize() {
        // simple picklist; you can reuse your real list later
        aircraftCombo.setItems(FXCollections.observableArrayList("AC-C172-01", "SIM-ATD-01"));
    }

    /** Call this right after loading FXML to inject the selected session */
    public void setSession(ClassSession session) {
        this.session = session;
        if (session == null) return;

        idField.setText(String.valueOf(session.getSessionId()));
        datePicker.setValue(session.getDate());
        traineeField.setText(session.getTraineeId());
        aircraftCombo.getSelectionModel().select(session.getAircraftOrDeviceId());
        durationField.setText(String.valueOf(session.getDurationHours()));
        remarksArea.setText(session.getRemarks());
    }

    @FXML
    private void onSave() {
        if (session == null) { info("No session loaded."); return; }

        // VL: duration > 0, remarks <= 200 (simple)
        double dur;
        try {
            dur = Double.parseDouble(durationField.getText().trim());
            if (dur <= 0) throw new NumberFormatException();
        } catch (Exception e) {
            error("Duration must be a positive number.");
            return;
        }
        String equip = aircraftCombo.getValue();
        if (equip == null || equip.isBlank()) { error("Pick an aircraft/device."); return; }

        String remarks = remarksArea.getText() == null ? "" : remarksArea.getText().trim();
        if (remarks.length() > 200) { error("Remarks must be ≤ 200 chars."); return; }

        // DP (in-memory only): update fields on the existing object
        session.setAircraftOrDeviceId(equip);
        session.setDurationHours(dur);
        session.setRemarks(remarks);

        info("Edited (dummy).");
        closeWindow();
    }

    @FXML
    private void onCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage st = (Stage) idField.getScene().getWindow();
        if (st != null) st.close();
    }

    private void info(String m) { new Alert(Alert.AlertType.INFORMATION, m, ButtonType.OK).showAndWait(); }
    private void error(String m){ new Alert(Alert.AlertType.ERROR, m, ButtonType.OK).showAndWait(); }

    /* --------- Helper to open the dialog from a table screen --------- */
    public static void openDialog(ClassSession selected) {
        try {
            FXMLLoader fx = new FXMLLoader(EditSessions.class.getResource("/EditSession.fxml"));
            Stage st = new Stage();
            st.initModality(Modality.APPLICATION_MODAL);
            st.setTitle("Edit Session");

            st.setScene(new Scene(fx.load()));
            EditSessions c = fx.getController();
            c.setSession(selected);

            st.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
