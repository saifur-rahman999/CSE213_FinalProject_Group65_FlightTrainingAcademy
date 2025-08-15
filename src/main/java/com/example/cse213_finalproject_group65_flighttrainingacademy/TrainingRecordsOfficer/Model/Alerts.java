package com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer.Model;


import javafx.scene.control.Alert;

public class Alerts {
    public static void info(String msg){ show(Alert.AlertType.INFORMATION, "Info", msg); }
    public static void warn(String msg){ show(Alert.AlertType.WARNING, "Warning", msg); }
    public static void error(String msg){ show(Alert.AlertType.ERROR, "Error", msg); }
    private static void show(Alert.AlertType type, String title, String msg){
        Alert a = new Alert(type); a.setTitle(title); a.setHeaderText(null); a.setContentText(msg); a.showAndWait();
    }
}
