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
        messageTextArea.setEditable(false);
        messageTextArea.setWrapText(true);
        userTypeComboBox.setItems(FXCollections.observableArrayList("Aircraft Maintenance Engineer", "ATC Coordinator", "Finance and Enrollment Officer", "Flight Instructor", "Ground Instructor", "Operation Manager", "Trainee Pilot", "Training Records Officer"));
    }

    @javafx.fxml.FXML
    public void loginButtonOnAction(ActionEvent actionEvent) {
        String id, password, userType;

        id = userIdTextField.getText();
        password = passwordField.getText();
        userType = userTypeComboBox.getValue();

        if (id.isBlank()) {
            messageTextArea.setText("User ID can not be blank.");
            return;
        }

        else if (password.isBlank()) {
            messageTextArea.setText("Password can not be blank.");
            return;
        }

        else if (userType == null) {
            messageTextArea.setText("User Type must be selected.");
            return;
        }

        else {
            if (userType.equals("Aircraft Maintenance Engineer")) {
                if (id.equals("0001") && password.equals("engineer")) {
                    System.out.print("gg");
                } else {
                    messageTextArea.setText("Wrong User ID or Password for Aircraft Maintenance Engineer.");
                }
            }

            else if (userType.equals("ATC Coordinator")) {
                if (id.equals("0002") && password.equals("atc")) {
                    System.out.print("gg");
                } else {
                    messageTextArea.setText("Wrong User ID or Password for ATC Coordinator.");
                }
            }

            else if (userType.equals("Finance and Enrollment Officer")) {
                if (id.equals("0003") && password.equals("feo")) {
                    System.out.print("gg");
                } else {
                    messageTextArea.setText("Wrong User ID or Password for Finance and Enrollment Officer.");
                }
            }

            else if (userType.equals("Flight Instructor")) {
                if (id.equals("0004") && password.equals("finstructor")) {
                    System.out.print("gg");
                } else {
                    messageTextArea.setText("Wrong User ID or Password for Flight Instructor.");
                }
            }

            else if (userType.equals("Ground Instructor")) {
                if (id.equals("0005") && password.equals("ginstructor")) {
                    System.out.print("gg");
                } else {
                    messageTextArea.setText("Wrong User ID or Password for Ground Instructor.");
                }
            }

            else if (userType.equals("Operation Manager")) {
                if (id.equals("0006") && password.equals("manager")) {
                    System.out.print("gg");
                } else {
                    messageTextArea.setText("Wrong User ID or Password for Operation Manager.");
                }
            }

            else if (userType.equals("Trainee Pilot")) {
                if (id.equals("0007") && password.equals("pilot")) {
                    System.out.print("gg");
                } else {
                    messageTextArea.setText("Wrong User ID or Password for Trainee Pilot.");
                }
            }

            else if (userType.equals("Training Records Officer")) {
                if (id.equals("0008") && password.equals("tro")) {
                    System.out.print("gg");
                } else {
                    messageTextArea.setText("Wrong User ID or Password for Training Records Officer.");
                }
            }
        }
    }
}