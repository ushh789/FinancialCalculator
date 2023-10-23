package com.netrunners.financialcalculator;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.awt.*;


public class StartMenu extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader =new FXMLLoader(StartMenu.class.getResource("StartMenu.fxml"));
        primaryStage.setTitle("Financial Calculator by netrunners");
        primaryStage.getIcons().add(new Image("file:src/main/resources/com/netrunners/financialcalculator/assets/Logo.png"));
        if (Taskbar.isTaskbarSupported()) {
            var taskbar = Taskbar.getTaskbar();
            if (taskbar.isSupported(Taskbar.Feature.ICON_IMAGE)) {
                final Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
                var dockIcon = defaultToolkit.getImage("src/main/resources/com/netrunners/financialcalculator/assets/Logo.png");
                taskbar.setIconImage(dockIcon);
            }

        }
        primaryStage.setScene(new Scene(fxmlLoader.load(), 600, 600));
        primaryStage.maxHeightProperty().setValue(600);
        primaryStage.maxWidthProperty().setValue(600);
        primaryStage.minHeightProperty().setValue(620);
        primaryStage.minWidthProperty().setValue(620);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
