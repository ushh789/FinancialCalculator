package com.netrunners.financialcalculator.ui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.netrunners.financialcalculator.logic.files.LogHelper;
import com.netrunners.financialcalculator.logic.files.Savable;
import com.netrunners.financialcalculator.logic.time.LocalDateAdapter;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static com.netrunners.financialcalculator.constants.FileConstants.JSON_EXTENSION;
import static com.netrunners.financialcalculator.constants.StringConstants.ASTERISK;
import static com.netrunners.financialcalculator.constants.FileConstants.JSON_FILE;
import static com.netrunners.financialcalculator.constants.FileConstants.LOGO;
import static com.netrunners.financialcalculator.constants.FileConstants.SAVES_PATH;

public class FilesActions {
    public static File showFileChooser(String bindingKey, List<String> fileTypes, List<String> extensions) {
        FileChooser fileChooser = defineChooserProperties(bindingKey, fileTypes, extensions);
        return fileChooser.showOpenDialog(null);
    }

    public static File showFileChooserSaver(String bindingKey, List<String> fileTypes, List<String> extensions) {
        FileChooser fileChooser = defineChooserProperties(bindingKey, fileTypes, extensions);
        return fileChooser.showSaveDialog(null);
    }

    private static FileChooser defineChooserProperties(String bindingKey, List<String> fileTypes, List<String> extensions) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(LanguageManager.getInstance().getStringBinding(bindingKey).get());
        for (int i = 0; i < fileTypes.size(); i++) {
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(fileTypes.get(i), extensions.get(i)));
        }
        File initialDirectory = new File(SAVES_PATH);
        fileChooser.setInitialDirectory(initialDirectory);
        return fileChooser;
    }

    public static void showSavingAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(LanguageManager.getInstance().getStringBinding("saveButton").get());
        alert.setHeaderText(null);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(LOGO));
        alert.setContentText(LanguageManager.getInstance().getStringBinding("saveSuccess").get());
        alert.showAndWait();
    }

    public static void saveFileToJson(Savable savable) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        String json = gson.toJson(savable.getJsonObject());

        List<String> fileTypes = new ArrayList<>();
        fileTypes.add(JSON_FILE);

        List<String> extensions = new ArrayList<>();
        extensions.add(ASTERISK + JSON_EXTENSION);
        File file = FilesActions.showFileChooserSaver("saveButton", fileTypes, extensions);

        if (file != null) {
            try {
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(json);
                fileWriter.close();
            } catch (IOException e) {
                LogHelper.log(Level.SEVERE, "Error while saving file", e);
            }
        }
    }
}
