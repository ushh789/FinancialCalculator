package com.netrunners.financialcalculator;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;


public class StartMenu extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader =new FXMLLoader(StartMenu.class.getResource("StartMenu.fxml"));
        primaryStage.setTitle("Financial Calculator by netrunners");
        primaryStage.getIcons().add(new Image("file:src/main/resources/com/netrunners/financialcalculator/assets/Logo.png"));
        primaryStage.setScene(new Scene(fxmlLoader.load(), 600, 800));
        primaryStage.maxHeightProperty().setValue(800);
        primaryStage.maxWidthProperty().setValue(600);
        primaryStage.minHeightProperty().setValue(400);
        primaryStage.minWidthProperty().setValue(500);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
