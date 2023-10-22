package com.netrunners.financialcalculator;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;


public class StartMenu extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader =new FXMLLoader(StartMenu.class.getResource("StartMenu.fxml"));
        primaryStage.setTitle("Financial Calculator");
        primaryStage.setScene(new Scene(fxmlLoader.load(), 800, 600));
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
