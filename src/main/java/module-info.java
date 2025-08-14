module com.example.cse213_finalproject_group65_flighttrainingacademy {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cse213_finalproject_group65_flighttrainingacademy to javafx.fxml;
    exports com.example.cse213_finalproject_group65_flighttrainingacademy;
}