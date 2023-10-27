package com.netrunners.financialcalculator.VisualInstruments.MenuActions;

import com.netrunners.financialcalculator.StartMenu;
import javafx.scene.Scene;

import static com.netrunners.financialcalculator.MenuControllers.closeWindow.closeCurrentWindow;

public class ExitApp {
    public static void exitApp(){
        for (Scene scene : StartMenu.openScenes) {
            closeCurrentWindow(scene);
        }
        System.exit(0);
    }
}
