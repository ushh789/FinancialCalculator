package com.netrunners.financialcalculator.ui;

import com.netrunners.financialcalculator.StartMenu;
import javafx.scene.Scene;

import static com.netrunners.financialcalculator.constants.FileConstants.DARK_THEME;
import static com.netrunners.financialcalculator.constants.FileConstants.LIGHT_THEME;


public class ThemeSelector {
    private static void setTheme(String theme) {
        StartMenu.setCurrentTheme(theme);
        for (Scene scene : StartMenu.openScenes) {
            scene.getStylesheets().clear();
            scene.getStylesheets().add(StartMenu.getCurrentTheme());
        }
    }

    public static void setDarkTheme() {
        setTheme(DARK_THEME);
    }

    public static void setLightTheme() {
        setTheme(LIGHT_THEME);
    }
}
