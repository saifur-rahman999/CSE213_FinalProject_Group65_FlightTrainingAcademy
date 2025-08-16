package com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer;

import com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer.Model.DummyStore;
import com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer.Model.Instructor;
import com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer.Model.Rating;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;

public class InstructorRatingsController {

    @FXML private TableView<Instructor> instructorTable;
    @FXML private TableColumn<Instructor, String> colInstrId;
    @FXML private TableColumn<Instructor, String> colInstrName;

    @FXML private TableView<Rating> ratingTable;
    @FXML private TableColumn<Rating, String> colRatingLabel;
    @FXML private TableColumn<Rating, LocalDate> colExpiry;

    @FXML private TextField ratingField;
    @FXML private DatePicker expiryPicker;
    @FXML private Label statusLabel;

    private final ObservableList<Instructor> instructors = FXCollections.observableArrayList();
    private final ObservableList<Rating> ratingsView = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Instructors table
        colInstrId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colInstrName.setCellValueFactory(new PropertyValueFactory<>("name"));
        instructorTable.setItems(instructors);

        // Ratings table
        colRatingLabel.setCellValueFactory(new PropertyValueFactory<>("label"));
        colExpiry.setCellValueFactory(new PropertyValueFactory<>("expiry"));
        ratingTable.setItems(ratingsView);

        // Load dummy instructors into left table
        if (DummyStore.instructors.isEmpty()) {
            DummyStore.seedInstructors(); // create a few demo rows if needed
        }
        instructors.setAll(DummyStore.instructors);

        // When you select an instructor -> show their ratings
        instructorTable.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            loadRatingsFor(newV);
        });

        // Select first instructor, if any
        if (!instructors.isEmpty()) {
            instructorTable.getSelectionModel().select(0);
        }
    }

    private void loadRatingsFor(Instructor instr) {
        ratingsView.clear();
        if (instr == null) return;
        ratingsView.addAll(instr.getRatings());
        status("");
    }

    @FXML
    private void onAdd() {
        Instructor instr = instructorTable.getSelectionModel().getSelectedItem();
        if (instr == null) { error("Select an instructor first."); return; }

        String label = (ratingField.getText() == null) ? "" : ratingField.getText().trim();
        if (label.isEmpty()) { error("Rating cannot be blank."); return; }

        LocalDate expiry = expiryPicker.getValue();
        if (expiry == null || expiry.isBefore(LocalDate.now())) {
            error("Expiry must be today or later.");
            return;
        }

        // DP: edit in memory
        Rating r = new Rating(label, expiry);
        instr.getRatings().add(r);

        // Refresh right table
        loadRatingsFor(instr);
        clearInputs();
        info("Saved (dummy).");
    }

    @FXML
    private void onUpdate() {
        Instructor instr = instructorTable.getSelectionModel().getSelectedItem();
        Rating sel = ratingTable.getSelectionModel().getSelectedItem();
        if (instr == null) { error("Select an instructor first."); return; }
        if (sel == null)   { error("Select a rating to update."); return; }

        String label = (ratingField.getText() == null) ? "" : ratingField.getText().trim();
        if (label.isEmpty()) { error("Rating cannot be blank."); return; }

        LocalDate expiry = expiryPicker.getValue();
        if (expiry == null || expiry.isBefore(LocalDate.now())) {
            error("Expiry must be today or later.");
            return;
        }

        // DP: update in-memory object
        sel.setLabel(label);
        sel.setExpiry(expiry);

        ratingTable.refresh();
        clearInputs();
        info("Saved (dummy).");
    }

    @FXML
    private void onDelete() {
        Instructor instr = instructorTable.getSelectionModel().getSelectedItem();
        Rating sel = ratingTable.getSelectionModel().getSelectedItem();
        if (instr == null) { error("Select an instructor first."); return; }
        if (sel == null)   { error("Select a rating to delete."); return; }

        instr.getRatings().remove(sel);
        loadRatingsFor(instr);
        clearInputs();
        info("Deleted (dummy).");
    }

    private void clearInputs() {
        ratingField.clear();
        expiryPicker.setValue(null);
        status("");
    }

    private void info(String m)  { statusLabel.setText(m); new Alert(Alert.AlertType.INFORMATION, m, ButtonType.OK).showAndWait(); }
    private void error(String m) { statusLabel.setText(m); new Alert(Alert.AlertType.ERROR, m, ButtonType.OK).showAndWait(); }
    private void status(String m){ statusLabel.setText(m); }

    // Optional: when selecting a rating, populate inputs for quick update
    @FXML
    private void initializeRatingSelectionFill() {
        ratingTable.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            if (newV != null) {
                ratingField.setText(newV.getLabel());
                expiryPicker.setValue(newV.getExpiry());
            }
        });
    }
}
