package com.netrunners.financialcalculator.ui;

import com.netrunners.financialcalculator.StartMenu;
import javafx.scene.Scene;

public class ExitApp {
    public static void exitApp(){
        for (Scene scene : StartMenu.openScenes) {
            closeCurrentWindow(scene);
        }
        System.exit(0);
    }

    public static void closeCurrentWindow(Scene scene) {
        scene.getWindow().hide();
    }
}
