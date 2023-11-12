package com.netrunners.financialcalculator.VisualInstruments.MenuActions;

import com.netrunners.financialcalculator.StartMenu;
import javafx.scene.Scene;


public class ThemeSelector {
    public static void setDarkTheme(){
        StartMenu.currentTheme = "file:src/main/resources/com/netrunners/financialcalculator/assets/darkTheme.css";
        for (Scene scene : StartMenu.openScenes) {
            scene.getStylesheets().clear();
            scene.getStylesheets().add(StartMenu.currentTheme);
        }

    }
    public static void setLightTheme(){
        StartMenu.currentTheme = "file:src/main/resources/com/netrunners/financialcalculator/assets/lightTheme.css";
        for (Scene scene : StartMenu.openScenes) {
            scene.getStylesheets().clear();
            scene.getStylesheets().add(StartMenu.currentTheme);
        }

    }
}
