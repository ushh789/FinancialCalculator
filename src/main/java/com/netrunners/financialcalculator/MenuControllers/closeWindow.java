package com.netrunners.financialcalculator.MenuControllers;

import javafx.scene.Scene;


public interface closeWindow {
    public static void closeCurrentWindow(Scene scene) {
        scene.getWindow().hide();
    }
}
