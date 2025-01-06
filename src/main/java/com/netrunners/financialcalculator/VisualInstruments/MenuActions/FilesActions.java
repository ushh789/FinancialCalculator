package com.netrunners.financialcalculator.VisualInstruments.MenuActions;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

import static com.netrunners.financialcalculator.VisualInstruments.MenuActions.FileConstants.LOGO;

public class FilesActions {
    public static File showFileChooser(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(LanguageManager.getInstance().getStringBinding("ChooseOpenFile").get());
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json"));
        File initialDirectory = new File("saves/");
        fileChooser.setInitialDirectory(initialDirectory);
        return fileChooser.showOpenDialog(null);
    }

    public static void showSavingAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(LanguageManager.getInstance().getStringBinding("saveButton").get());
        alert.setHeaderText(null);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(LOGO));
        alert.setContentText(LanguageManager.getInstance().getStringBinding("saveSuccess").get());
        alert.showAndWait();
    }
}
