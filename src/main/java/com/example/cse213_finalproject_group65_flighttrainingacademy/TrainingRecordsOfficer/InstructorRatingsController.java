package com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer;

import com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer.Model.Instructor;
import com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer.Model.Rating;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InstructorRatingsController {

    // LEFT TABLE (Instructors)
    @FXML private TableView<Instructor> instructorTable;
    @FXML private TableColumn<Instructor, String> colInstrId;
    @FXML private TableColumn<Instructor, String> colInstrName;

    // RIGHT TABLE (Ratings)
    @FXML private TableView<Rating> ratingTable;
    @FXML private TableColumn<Rating, String> colRatingLabel;
    @FXML private TableColumn<Rating, LocalDate> colExpiry;

    // View-only inputs
    @FXML private TextField ratingField;
    @FXML private DatePicker expiryPicker;

    @FXML private Label statusLabel;
    @FXML private Button btnBack;

    private final ObservableList<Instructor> instructors = FXCollections.observableArrayList();
    private final ObservableList<Rating> ratingsView = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // 1) Set up table columns
        colInstrId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colInstrName.setCellValueFactory(new PropertyValueFactory<>("name"));
        instructorTable.setItems(instructors);

        colRatingLabel.setCellValueFactory(new PropertyValueFactory<>("label"));
        colExpiry.setCellValueFactory(new PropertyValueFactory<>("expiry"));
        ratingTable.setItems(ratingsView);

        // 2) Load dummy data into instructors table
        loadDummyInstructors();

        // 3) If we have at least one instructor, select the first and show their ratings
        if (!instructors.isEmpty()) {
            Instructor first = instructors.get(0);
            instructorTable.getSelectionModel().select(first);
            loadRatingsFor(first);
        } else {
            statusLabel.setText("No instructors available.");
        }

        // 4) When user selects another instructor, show their ratings
        instructorTable.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Instructor>() {
                    @Override
                    public void changed(ObservableValue<? extends Instructor> obs,
                                        Instructor oldVal, Instructor newVal) {
                        loadRatingsFor(newVal);
                    }
                }
        );

        // 5) When user selects a rating, display it in the inputs (read-only convenience)
        ratingTable.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Rating>() {
                    @Override
                    public void changed(ObservableValue<? extends Rating> obs,
                                        Rating oldVal, Rating newVal) {
                        if (newVal != null) {
                            ratingField.setText(newVal.getLabel());
                            expiryPicker.setValue(newVal.getExpiry());
                        } else {
                            ratingField.clear();
                            expiryPicker.setValue(null);
                        }
                    }
                }
        );
    }

    private void loadDummyInstructors() {
        instructors.clear();

        // Create three simple instructors with a few ratings each
        Instructor a = new Instructor("FI-001", "Alice Rahman");
        a.getRatings().add(new Rating("CPL", LocalDate.now().plusMonths(8)));
        a.getRatings().add(new Rating("IR",  LocalDate.now().plusMonths(5)));

        Instructor b = new Instructor("FI-002", "Bashir Hossain");
        b.getRatings().add(new Rating("ME",  LocalDate.now().plusMonths(10)));
        b.getRatings().add(new Rating("CFI", LocalDate.now().plusMonths(14)));

        Instructor c = new Instructor("FI-003", "Chitra Das");
        c.getRatings().add(new Rating("ATPL", LocalDate.now().plusMonths(20)));

        // Add to observable list
        instructors.add(a);
        instructors.add(b);
        instructors.add(c);
    }

    private void loadRatingsFor(Instructor instr) {
        ratingsView.clear();
        if (instr == null) {
            statusLabel.setText("Select an instructor.");
            ratingField.clear();
            expiryPicker.setValue(null);
            return;
        }
        // Copy ratings to right table
        List<Rating> rlist = instr.getRatings();
        for (int i = 0; i < rlist.size(); i++) {
            ratingsView.add(rlist.get(i));
        }
        statusLabel.setText("Ratings loaded for " + instr.getName());
        ratingField.clear();
        expiryPicker.setValue(null);
    }

    @FXML
    private void onBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/com/example/cse213_finalproject_group65_flighttrainingacademy/TrainingRecordsOfficer/TRODashboard.fxml"
            ));
            Parent root = loader.load();

            Stage stage = (Stage) btnBack.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("TRO Dashboard");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Alert a = new Alert(Alert.AlertType.ERROR, "Unable to load TRO Dashboard.", ButtonType.OK);
            a.showAndWait();
        }
    }
}
