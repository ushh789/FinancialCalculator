package com.netrunners.financialcalculator;

import com.netrunners.financialcalculator.LogicalInstrumnts.FileInstruments.LogHelper;
import com.netrunners.financialcalculator.VisualInstruments.MenuActions.LanguageManager;
import javafx.application.Application;

import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class StartMenu extends Application {
    public static Scene startMenuScene;
    public static List<Scene> openScenes = new ArrayList<>();
    public static String currentTheme = "file:src/main/resources/com/netrunners/financialcalculator/assets/lightTheme.css";
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(StartMenu.class.getResource("StartMenu.fxml"));
            primaryStage.titleProperty().bind(LanguageManager.getInstance().getStringBinding("CalcLabel"));
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
            primaryStage.getScene().getStylesheets().add(currentTheme);
            startMenuScene = primaryStage.getScene();
            StartMenu.openScenes.add(startMenuScene);
            primaryStage.show();

        } catch (Exception e) {
            LogHelper.log(Level.SEVERE, "Error opening StartMenu.fxml: ", e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() {
        LanguageManager.getInstance().saveLanguage();
    }
}
