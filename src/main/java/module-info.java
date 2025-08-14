module com.example.cse213_finalproject_group65_flighttrainingacademy {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.cse213_finalproject_group65_flighttrainingacademy to javafx.fxml;
    opens com.example.cse213_finalproject_group65_flighttrainingacademy.AircraftMaintenanceEngineer to javafx.fxml, javafx.base;
    opens com.example.cse213_finalproject_group65_flighttrainingacademy.AtcCoordinator to javafx.fxml, javafx.base;
    opens com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer to javafx.fxml, javafx.base;
    opens com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer to javafx.fxml, javafx.base;
    opens com.example.cse213_finalproject_group65_flighttrainingacademy.FlightInstructor to javafx.fxml, javafx.base;
    opens com.example.cse213_finalproject_group65_flighttrainingacademy.GroundInstructor to javafx.fxml, javafx.base;
    opens com.example.cse213_finalproject_group65_flighttrainingacademy.OperationManager to javafx.fxml, javafx.base;
    opens com.example.cse213_finalproject_group65_flighttrainingacademy.TraineePilot to javafx.fxml, javafx.base;
    exports com.example.cse213_finalproject_group65_flighttrainingacademy;
}