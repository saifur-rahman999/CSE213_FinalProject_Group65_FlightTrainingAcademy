package com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer.Model;


import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Popup;
import javafx.util.Duration;

public final class Toast {
    private Toast(){}

    public static void show(Node anyNodeInScene, String message) {
        Scene scene = anyNodeInScene.getScene();
        if (scene == null) return;

        Popup popup = new Popup();
        Label lbl = new Label(message);
        lbl.setStyle("-fx-background-color: rgba(30,30,30,0.92); -fx-text-fill: white; -fx-padding: 10 14; -fx-background-radius: 8; -fx-font-size: 12;");
        StackPane wrap = new StackPane(lbl);
        wrap.setPadding(new Insets(8));
        popup.getContent().add(wrap);

        popup.show(scene.getWindow());
        // center-bottom
        wrap.setPrefWidth(scene.getWidth());
        StackPane.setAlignment(lbl, Pos.BOTTOM_CENTER);
        wrap.setTranslateY(-30);

        FadeTransition ft = new FadeTransition(Duration.millis(1800), wrap);
        ft.setFromValue(1);
        ft.setToValue(0);
        ft.setOnFinished(e -> popup.hide());
        ft.play();
    }
}
