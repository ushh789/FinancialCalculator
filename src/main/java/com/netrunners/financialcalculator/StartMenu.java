package com.netrunners.financialcalculator;

import com.netrunners.financialcalculator.LogicalInstrumnts.FileInstruments.LogHelper;
import com.netrunners.financialcalculator.VisualInstruments.MenuActions.LanguageManager;
import javafx.application.Application;

import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.awt.Taskbar;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static com.netrunners.financialcalculator.VisualInstruments.MenuActions.FileConstants.LOGO;
import static com.netrunners.financialcalculator.VisualInstruments.MenuActions.FileConstants.LOGO_PATH;
import static com.netrunners.financialcalculator.VisualInstruments.MenuActions.FileConstants.LIGHT_THEME;

public class StartMenu extends Application {
    public static Scene startMenuScene;
    public static List<Scene> openScenes = new ArrayList<>();
    private static String currentTheme = LIGHT_THEME;

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
                var taskbar = Taskbar.getTaskbar();
                if (taskbar.isSupported(Taskbar.Feature.ICON_IMAGE)) {
                    final Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
                    var dockIcon = defaultToolkit.getImage(LOGO_PATH);
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
