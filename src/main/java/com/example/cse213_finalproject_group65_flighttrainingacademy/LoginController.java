package com.example.cse213_finalproject_group65_flighttrainingacademy;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

public class LoginController
{
    @javafx.fxml.FXML
    private ComboBox userTypeComboBox;
    @javafx.fxml.FXML
    private Label titleLabel;
    @javafx.fxml.FXML
    private TextField userIdTextField;
    @javafx.fxml.FXML
    private Button loginButton;
    @javafx.fxml.FXML
    private TextArea messageTextArea;
    @javafx.fxml.FXML
    private PasswordField passwordField;


    @javafx.fxml.FXML
    public void initialize() {
        userTypeComboBox.setItems(FXCollections.observableArrayList("Aircraft Maintenance Engineer", "Atc Coordinator", "Finance and Enrollment Officer", "Flight Instructor", "Ground Instructor", "Operation Manager", "Trainee Pilot", "Training Records Officer"));
    }

    @javafx.fxml.FXML
    public void loginButtonOnAction(ActionEvent actionEvent) {
        String id, password, userType;
        boolean flag = true;
        Alert erroralert= new Alert(Alert.AlertType.ERROR) ;

        id = userIdTextField.getText();
        password = passwordField.getText();
        userType = userTypeComboBox.getItems().toString();

        if (id.isBlank()) {
            flag = false;
            erroralert.setTitle("User ID Error");
            erroralert.setContentText("User ID can not be blank.");
            erroralert.showAndWait();
        }

        if (password.isBlank()) {
            flag = false;
            erroralert.setTitle("Password Error");
            erroralert.setContentText("Password can not be blank.");
            erroralert.showAndWait();
        }

        if (userType.isEmpty()) {
            flag = false;
            erroralert.setTitle("UserType Error");
            erroralert.setContentText("Usertype must be selected.");
            erroralert.showAndWait();
        }


    }
}