package com.netrunners.financialcalculator.VisualInstruments.MenuActions;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class SavingAlert {
    public static void showSavingAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(LanguageManager.getInstance().getStringBinding("saveButton").get());
        alert.setHeaderText(null);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("file:src/main/resources/com/netrunners/financialcalculator/assets/Logo.png"));
        alert.setContentText(LanguageManager.getInstance().getStringBinding("saveSuccess").get());
        alert.showAndWait();
    }
}
