package com.example.cse213_finalproject_group65_flighttrainingacademy;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

public class LoginController
{
    @javafx.fxml.FXML
    private ComboBox<String> userTypeComboBox;
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
        userTypeComboBox.setItems(FXCollections.observableArrayList("Aircraft Maintenance Engineer", "ATC Coordinator", "Finance and Enrollment Officer", "Flight Instructor", "Ground Instructor", "Operation Manager", "Trainee Pilot", "Training Records Officer"));
    }

    @javafx.fxml.FXML
    public void loginButtonOnAction(ActionEvent actionEvent) {
        String id, password, userType;
        boolean flag = true;
        Alert erroralert= new Alert(Alert.AlertType.ERROR) ;

        id = userIdTextField.getText();
        password = passwordField.getText();
        userType = userTypeComboBox.getValue();

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

        //vr


        if (userType.equals("Aircraft Maintenance Engineer")) {
            if (id.equals("0001") && password.equals("engineer")){
                System.out.print("gg");
            }

            else {
                System.out.print("Wrong User ID or Password for Ground Instructor.");
            }
        }

        if (userType.equals("ATC Coordinator")) {
            if (id.equals("0002") && password.equals("atc")){
                System.out.print("gg");
            }

            else {
                System.out.print("Wrong User ID or Password for ATC Coordinator.");
            }
        }

        if (userType.equals("Finance and Enrollment Officer")) {
            if (id.equals("0003") && password.equals("feo")){
                System.out.print("gg");
            }

            else {
                System.out.print("Wrong User ID or Password for Finance and Enrollment Officer.");
            }
        }

        if (userType.equals("Flight Instructor")) {
            if (id.equals("0004") && password.equals("finstructor")){
                System.out.print("gg");
            }

            else {
                System.out.print("Wrong User ID or Password for Flight Instructor.");
            }
        }

        if (userType.equals("Ground Instructor")) {
            if (id.equals("0005") && password.equals("ginstructor")){
                System.out.print("gg");
            }

            else {
                System.out.print("Wrong User ID or Password for Ground Instructor");
            }
        }

        if (userType.equals("Operation Manager")) {
            if (id.equals("0006") && password.equals("manager")){
                System.out.print("gg");
            }

            else {
                System.out.print("Wrong User ID or Password for Operation Manager.");
            }
        }

        if (userType.equals("Trainee Pilot")) {
            if (id.equals("0007") && password.equals("pilot")){
                System.out.print("gg");
            }

            else {
                System.out.print("Wrong User ID or Password for Trainee Pilot.");
            }
        }

        if (userType.equals("Training Records Officer")) {
            if (id.equals("0002") && password.equals("tro")){
                System.out.print("gg");
            }

            else {
                System.out.print("Wrong User ID or Password for Training Records Officer.");
            }
        }

    }
}