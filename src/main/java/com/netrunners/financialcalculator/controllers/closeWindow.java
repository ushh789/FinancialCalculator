package com.netrunners.financialcalculator.Controllers;

import javafx.scene.Scene;


public interface closeWindow {
    static void closeCurrentWindow(Scene scene){
        scene.getWindow().hide();
    }
}
