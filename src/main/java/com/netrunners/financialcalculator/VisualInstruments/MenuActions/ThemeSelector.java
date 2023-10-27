package com.netrunners.financialcalculator.VisualInstruments.MenuActions;

import com.netrunners.financialcalculator.StartMenu;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;

import static com.netrunners.financialcalculator.ErrorHandling.ErrorChecker.updateDatePickerStyle;

public class ThemeSelector {
    public static void setDarkTheme(){
        StartMenu.currentTheme = "file:src/main/resources/com/netrunners/financialcalculator/assets/darkTheme.css";
        for (Scene scene : StartMenu.openScenes) {
            scene.getStylesheets().clear();
            scene.getStylesheets().add(StartMenu.currentTheme);
        }
        for (DatePicker datePicker : StartMenu.datePickers) {
            updateDatePickerStyle(datePicker);
        }
    }
    public static void setLightTheme(){
        StartMenu.currentTheme = "file:src/main/resources/com/netrunners/financialcalculator/assets/lightTheme.css";
        for (Scene scene : StartMenu.openScenes) {
            scene.getStylesheets().clear();
            scene.getStylesheets().add(StartMenu.currentTheme);
        }
        for (DatePicker datePicker : StartMenu.datePickers) {
            updateDatePickerStyle(datePicker);
        }
    }
}
