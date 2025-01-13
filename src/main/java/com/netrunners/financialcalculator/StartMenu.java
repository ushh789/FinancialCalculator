package com.netrunners.financialcalculator;

import com.netrunners.financialcalculator.ui.LanguageManager;
import javafx.application.Application;

import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Taskbar;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import static com.netrunners.financialcalculator.constants.FileConstants.LOGO;
import static com.netrunners.financialcalculator.constants.FileConstants.LOGO_PATH;
import static com.netrunners.financialcalculator.constants.FileConstants.LIGHT_THEME;
import static com.netrunners.financialcalculator.constants.StringConstants.ERROR_OPENING_FXML;

public class StartMenu extends Application {
    public static Scene startMenuScene;
    public static List<Scene> openScenes = new ArrayList<>();
    private static String currentTheme = LIGHT_THEME;
    private static final int WINDOW_SIZE = 600;
    private static final Logger logger = LoggerFactory.getLogger(StartMenu.class);

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(StartMenu.class.getResource("StartMenu.fxml"));
            primaryStage.titleProperty().bind(LanguageManager.getInstance().getStringBinding("CalcLabel"));
            primaryStage.getIcons().add(new Image(LOGO));

            if (Taskbar.isTaskbarSupported()) {
                // MacOS dock icon
                var taskbar = Taskbar.getTaskbar();
                if (taskbar.isSupported(Taskbar.Feature.ICON_IMAGE)) {
                    final Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
                    var dockIcon = defaultToolkit.getImage(LOGO_PATH);
                    taskbar.setIconImage(dockIcon);
                }
            } else {
                logger.warn("Taskbar is not supported");
            }

            primaryStage.setScene(new Scene(fxmlLoader.load(), WINDOW_SIZE, WINDOW_SIZE));
            primaryStage.maxHeightProperty().setValue(WINDOW_SIZE);
            primaryStage.maxWidthProperty().setValue(WINDOW_SIZE);
            primaryStage.minHeightProperty().setValue(WINDOW_SIZE + 20);
            primaryStage.minWidthProperty().setValue(WINDOW_SIZE + 20);
            primaryStage.getScene().getStylesheets().add(currentTheme);
            startMenuScene = primaryStage.getScene();
            StartMenu.openScenes.add(startMenuScene);
            primaryStage.show();

        } catch (Exception e) {
            logger.error(ERROR_OPENING_FXML, "StartMenu.fxml", e);
            System.exit(-1);
        }
    }

    @Override
    public void stop() {
        LanguageManager.getInstance().saveLanguage();
    }

    public static void setCurrentTheme(String currentTheme) {
        StartMenu.currentTheme = currentTheme;
    }

    public static String getCurrentTheme() {
        return currentTheme;
    }
}
