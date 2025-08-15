package com.example.cse213_finalproject_group65_flighttrainingacademy.TraineePilot;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class TakeExam {
    @javafx.fxml.FXML
    private Button submitButton;
    @javafx.fxml.FXML
    private VBox questionContainerVBox;
    @javafx.fxml.FXML
    private Label examStatusLabel;
    @javafx.fxml.FXML
    private Label examInstructionLabel;
    @javafx.fxml.FXML
    private ComboBox examSelectorComboBox;
    @javafx.fxml.FXML
    private ScrollPane questionScrollPane;
    @javafx.fxml.FXML
    private Label timerLabel;

    @javafx.fxml.FXML
    public void onSubmitClick(ActionEvent actionEvent) {
    }
}
