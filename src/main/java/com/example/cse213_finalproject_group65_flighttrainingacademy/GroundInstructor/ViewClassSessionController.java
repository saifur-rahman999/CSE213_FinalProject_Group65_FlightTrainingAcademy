package com.example.cse213_finalproject_group65_flighttrainingacademy.GroundInstructor;

import com.example.cse213_finalproject_group65_flighttrainingacademy.HelloApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class ViewClassSessionController
{
    @javafx.fxml.FXML
    private TableColumn colTime;
    @javafx.fxml.FXML
    private TableColumn colTopic;
    @javafx.fxml.FXML
    private DatePicker datePicker;
    @javafx.fxml.FXML
    private TableView sessionTableView;
    @javafx.fxml.FXML
    private TableColumn colCourseId;
    @javafx.fxml.FXML
    private TableColumn colDate;
    @javafx.fxml.FXML
    private TextArea errorTextArea;
    @javafx.fxml.FXML
    private TextArea tableTextArea;

    private final ObservableList<ClassSession> allSessions = FXCollections.observableArrayList();
    private final ObservableList<ClassSession> shown = FXCollections.observableArrayList();

    @javafx.fxml.FXML
    public void initialize() {
        errorTextArea.setEditable(false);
        errorTextArea.setWrapText(true);
        tableTextArea.setEditable(false);
        tableTextArea.setWrapText(true);
        colCourseId.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        colTopic.setCellValueFactory(new PropertyValueFactory<>("topic"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));

        allSessions.addAll(
                new ClassSession("ADE101", "Introduction to Aerodynamics", LocalDate.now(), "08PM-10PM"),
                new ClassSession("ADE102", "Principles of Aerodynamics",   LocalDate.now().plusDays(1), "4PM-6PM"),
                new ClassSession("ASM101", "Introduction to Aircraft Systems", LocalDate.now(), "1PM-3PM"),
                new ClassSession("ASM301", "Advanced Aircraft Systems",     LocalDate.now().plusDays(2), "11:00-1PM")
        );

        shown.setAll(allSessions);
        sessionTableView.setItems(shown);
    }

    @javafx.fxml.FXML
    public void searchButtonOnAction(ActionEvent actionEvent) {
        LocalDate d = datePicker.getValue();

        if (d == null){
            errorTextArea.setText("Date must be selected");
            return;
        }
        shown.clear();
        for (ClassSession s : allSessions) {
            if (d.equals(s.getDate())) {
                shown.add(s);
            }
        }

        if (shown.isEmpty()) {
            tableTextArea.setText("no sessions found");
            return;
        }

    }

    @javafx.fxml.FXML
    public void backToDashboardButtonOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("GroundInstructor/GroundInstructorDashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }
}