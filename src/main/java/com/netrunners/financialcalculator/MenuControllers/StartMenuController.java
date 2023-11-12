package com.netrunners.financialcalculator.MenuControllers;

import com.netrunners.financialcalculator.StartMenu;
import com.netrunners.financialcalculator.VisualInstruments.MenuActions.AboutUsAlert;
import com.netrunners.financialcalculator.VisualInstruments.MenuActions.ExitApp;
import com.netrunners.financialcalculator.VisualInstruments.MenuActions.LanguageManager;
import com.netrunners.financialcalculator.VisualInstruments.MenuActions.ThemeSelector;
import com.netrunners.financialcalculator.VisualInstruments.WindowsOpener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.*;
import java.util.*;
import java.util.List;

import static com.netrunners.financialcalculator.StartMenu.startMenuScene;
import static com.netrunners.financialcalculator.MenuControllers.closeWindow.closeCurrentWindow;

public class StartMenuController implements LanguageUpdater {

    @FXML
    private Button CreditButton;

    @FXML
    private Button DepositButton;

    @FXML
    private MenuItem depositButtonMenu;

    @FXML
    private MenuItem creditButtonMenu;

    @FXML
    private MenuItem languageButton;

    @FXML
    private MenuItem darkTheme;

    @FXML
    private MenuItem lightTheme;

    @FXML
    private MenuItem aboutUs;

    @FXML
    private MenuItem exitApp;

    @FXML
    private MenuItem currency;

    @FXML
    private MenuItem openFileButton;

    @FXML
    private MenuItem saveAsButton;

    @FXML
    private MenuItem saveButton;

    @FXML
    private Menu themeButton;

    @FXML
    private Menu viewButton;

    @FXML
    private Menu newButton;

    @FXML
    private Menu fileButton;

    @FXML
    private Menu settingsButton;

    @FXML
    private Menu aboutButton;

    @FXML
    private Label financialCalculatorLabel;

    public void updateText() {
        DepositButton.setText(LanguageManager.getInstance().getTranslation("DepositButton"));
        CreditButton.setText(LanguageManager.getInstance().getTranslation("CreditButton"));
        creditButtonMenu.setText(LanguageManager.getInstance().getTranslation("creditButtonMenu"));
        depositButtonMenu.setText(LanguageManager.getInstance().getTranslation("depositButtonMenu"));
        languageButton.setText(LanguageManager.getInstance().getTranslation("languageButton"));
        darkTheme.setText(LanguageManager.getInstance().getTranslation("darkTheme"));
        lightTheme.setText(LanguageManager.getInstance().getTranslation("lightTheme"));
        aboutUs.setText(LanguageManager.getInstance().getTranslation("aboutUs"));
        exitApp.setText(LanguageManager.getInstance().getTranslation("exitApp"));
        currency.setText(LanguageManager.getInstance().getTranslation("currency"));
        openFileButton.setText(LanguageManager.getInstance().getTranslation("openFileButton"));
        saveAsButton.setText(LanguageManager.getInstance().getTranslation("saveAsButton"));
        saveButton.setText(LanguageManager.getInstance().getTranslation("saveButton"));
        themeButton.setText(LanguageManager.getInstance().getTranslation("themeButton"));
        viewButton.setText(LanguageManager.getInstance().getTranslation("viewButton"));
        newButton.setText(LanguageManager.getInstance().getTranslation("newButton"));
        fileButton.setText(LanguageManager.getInstance().getTranslation("fileButton"));
        settingsButton.setText(LanguageManager.getInstance().getTranslation("settingsButton"));
        aboutButton.setText(LanguageManager.getInstance().getTranslation("aboutButton"));
        financialCalculatorLabel.setText(LanguageManager.getInstance().getTranslation("financialCalculatorLabel"));
    }

    @FXML
    void initialize() {
        saveButton.setDisable(true);
        saveAsButton.setDisable(true);
        updateText();
        LanguageManager.getInstance().languageProperty().addListener((observable, oldValue, newValue) -> updateText());

        DepositButton.setOnAction(event -> WindowsOpener.depositOpener());
        CreditButton.setOnAction(event -> WindowsOpener.creditOpener());
        depositButtonMenu.setOnAction(event -> WindowsOpener.depositOpener());
        creditButtonMenu.setOnAction(event -> WindowsOpener.creditOpener());

        darkTheme.setOnAction(event -> ThemeSelector.setDarkTheme());
        lightTheme.setOnAction(event -> ThemeSelector.setLightTheme());
        aboutUs.setOnAction(event -> AboutUsAlert.showAboutUs());
        exitApp.setOnAction(event -> ExitApp.exitApp());

        currency.setOnAction(event -> {
            List<String> choices = new ArrayList<>();
            choices.add("UAH");
            choices.add("USD");
            choices.add("EUR");

            ChoiceDialog<String> dialog = new ChoiceDialog<>("UAH", choices);
            dialog.setTitle("Choose Currency");
            Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("file:src/main/resources/com/netrunners/financialcalculator/assets/Logo.png"));
            dialog.setHeaderText(null);
            dialog.setContentText("Choose your currency:");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                switch (result.get()) {
                    case "UAH":
                        break;
                    case "USD":
                        break;
                    case "EUR":
                        break;

                }
            }
        });
        openFileButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json"));
            File file = fileChooser.showOpenDialog(startMenuScene.getWindow());
            if (file != null) {
                System.out.println("File opened: " + file.getName());
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        saveAsButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save As");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("JSON Files", "*.json"),
                    new FileChooser.ExtensionFilter("All Files", "*.*")
            );
            File file = fileChooser.showSaveDialog(null);

            if (file != null) {
                try {
                    FileWriter fileWriter = new FileWriter(file);
                    fileWriter.write("Your content here");
                    fileWriter.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        languageButton.setOnAction(event -> {
            List<String> choices = new ArrayList<>();
            choices.add("English");
            choices.add("Українська");
            choices.add("Español");
            choices.add("Français");
            choices.add("Deutsch");
            choices.add("Czech");
            choices.add("Polski");
            choices.add("Nederlands");
            choices.add("日本語");
            choices.add("中国人");
            ChoiceDialog<String> dialog = new ChoiceDialog<>("English", choices);
            dialog.setTitle("Choose Language");
            Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("file:src/main/resources/com/netrunners/financialcalculator/assets/Logo.png"));
            dialog.setHeaderText(null);
            dialog.setContentText("Choose your language:");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                switch (result.get()) {
                    case "English" -> setLanguage("en");
                    case "Українська" -> setLanguage("uk");
                    case "Español" -> setLanguage("es");
                    case "Français" -> setLanguage("fr");
                    case "Deutsch" -> setLanguage("de");
                    case "Czech" -> setLanguage("cs");
                    case "Polski" -> setLanguage("pl");
                    case "Nederlands" -> setLanguage("nl");
                    case "日本語" -> setLanguage("ja");
                    case "中国人" -> setLanguage("zh");
                }
            }
        });


    }

    public void setLanguage(String language) {
        LanguageManager.getInstance().setLanguage(language);
        DepositButton.setText(LanguageManager.getInstance().getTranslation("DepositButton"));
        CreditButton.setText(LanguageManager.getInstance().getTranslation("CreditButton"));
        creditButtonMenu.setText(LanguageManager.getInstance().getTranslation("creditButtonMenu"));
        depositButtonMenu.setText(LanguageManager.getInstance().getTranslation("depositButtonMenu"));
        languageButton.setText(LanguageManager.getInstance().getTranslation("languageButton"));
        darkTheme.setText(LanguageManager.getInstance().getTranslation("darkTheme"));
        lightTheme.setText(LanguageManager.getInstance().getTranslation("lightTheme"));
        aboutUs.setText(LanguageManager.getInstance().getTranslation("aboutUs"));
        exitApp.setText(LanguageManager.getInstance().getTranslation("exitApp"));
        currency.setText(LanguageManager.getInstance().getTranslation("currency"));
        openFileButton.setText(LanguageManager.getInstance().getTranslation("openFileButton"));
        saveAsButton.setText(LanguageManager.getInstance().getTranslation("saveAsButton"));
        saveButton.setText(LanguageManager.getInstance().getTranslation("saveButton"));
        themeButton.setText(LanguageManager.getInstance().getTranslation("themeButton"));
        viewButton.setText(LanguageManager.getInstance().getTranslation("viewButton"));
        newButton.setText(LanguageManager.getInstance().getTranslation("newButton"));
        fileButton.setText(LanguageManager.getInstance().getTranslation("fileButton"));
        settingsButton.setText(LanguageManager.getInstance().getTranslation("settingsButton"));
        aboutButton.setText(LanguageManager.getInstance().getTranslation("aboutButton"));
        financialCalculatorLabel.setText(LanguageManager.getInstance().getTranslation("financialCalculatorLabel"));

    }
}

