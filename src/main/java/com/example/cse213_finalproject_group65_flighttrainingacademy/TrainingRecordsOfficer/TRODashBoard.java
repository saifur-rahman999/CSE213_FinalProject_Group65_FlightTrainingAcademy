package com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;

public class TRODashBoard {

    private static final String BASE = "/com/example/cse213_finalproject_group65_flighttrainingacademy/TrainingRecordsOfficer/";
    private static final String ADD_SESSION        = BASE + "AddSession.fxml";
    private static final String EDIT_SESSIONS      = BASE + "EditSessions.fxml";
    private static final String UPDATE_SESSION     = BASE + "UpdateSession.fxml";
    private static final String SESSIONS_TABLE     = BASE + "SessionsTable.fxml";
    private static final String SCHEDULE_VIEW      = BASE + "ScheduleView.fxml";
    private static final String RECORD_ATTENDANCE  = BASE + "RecordAttendance.fxml";
    private static final String EXPORT_ATTENDANCE  = BASE + "ExportAttendance.fxml";
    private static final String INSTRUCTOR_RATINGS = BASE + "InstructorRatings.fxml";
    private static final String QUICK_ANALYTICS    = BASE + "QuickAnalyticsView.fxml";

    public void openAddSession(ActionEvent e)       { switchTo(e, ADD_SESSION); }
    public void openEditSessions(ActionEvent e)     { switchTo(e, EDIT_SESSIONS); }
    public void openUpdateSession(ActionEvent e)    { switchTo(e, UPDATE_SESSION); }
    public void openSessionsTable(ActionEvent e)    { switchTo(e, SESSIONS_TABLE); }
    public void openScheduleView(ActionEvent e)     { switchTo(e, SCHEDULE_VIEW); }
    public void openRecordAttendance(ActionEvent e) { switchTo(e, RECORD_ATTENDANCE); }
    public void openExportAttendance(ActionEvent e) { switchTo(e, EXPORT_ATTENDANCE); }
    public void openInstructorRatings(ActionEvent e){ switchTo(e, INSTRUCTOR_RATINGS); }
    public void openQuickAnalytics(ActionEvent e)   { switchTo(e, QUICK_ANALYTICS); }

    private void switchTo(ActionEvent e, String fxmlAbsPath) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlAbsPath));
            ((Node) e.getSource()).getScene().setRoot(root);
        } catch (Exception ex) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Navigation Error");
            a.setHeaderText(null);
            a.setContentText("Failed to open view:\n" + fxmlAbsPath + "\n\n" + ex.getMessage());
            a.showAndWait();
        }
    }
}
