package com.netrunners.financialcalculator.MenuControllers;

import com.netrunners.financialcalculator.LogicalInstrumnts.FileInstruments.OpenFile;
import com.netrunners.financialcalculator.VisualInstruments.MenuActions.*;
import com.netrunners.financialcalculator.VisualInstruments.WindowsOpener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.*;
import java.util.List;

public class StartMenuController implements LanguageUpdater, CurrencyController {

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

    private LanguageManager languageManager = LanguageManager.getInstance();


    @FXML
    void initialize() {
        //Sout Current currency
        System.out.println(CurrencyManager.getInstance().getCurrency());
        saveButton.setDisable(true);
        saveAsButton.setDisable(true);
        DepositButton.setOnAction(event -> WindowsOpener.depositOpener());
        CreditButton.setOnAction(event -> WindowsOpener.creditOpener());
        depositButtonMenu.setOnAction(event -> WindowsOpener.depositOpener());
        creditButtonMenu.setOnAction(event -> WindowsOpener.creditOpener());
        darkTheme.setOnAction(event -> ThemeSelector.setDarkTheme());
        lightTheme.setOnAction(event -> ThemeSelector.setLightTheme());
        aboutUs.setOnAction(event -> AboutUsAlert.showAboutUs());
        exitApp.setOnAction(event -> ExitApp.exitApp());
        currency.setOnAction(event -> handleCurrencySelection());
        openFileButton.setOnAction(event -> OpenFile.openFromSave());
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
                String chosenLanguage = result.get();
                Locale locale = switch (chosenLanguage) {
                    case "Українська" -> new Locale("uk");
                    case "Español" -> new Locale("es");
                    case "Français" -> new Locale("fr");
                    case "Deutsch" -> new Locale("de");
                    case "Czech" -> new Locale("cs");
                    case "Polski" -> new Locale("pl");
                    case "Nederlands" -> new Locale("nl");
                    case "日本語" -> new Locale("ja");
                    case "中国人" -> new Locale("zh");
                    default -> new Locale("en");
                };
                languageManager.changeLanguage(locale);
                }

        });
        depositButtonMenu.textProperty().bind(languageManager.getStringBinding("DepositButton"));
        DepositButton.textProperty().bind(languageManager.getStringBinding("DepositButton"));
        creditButtonMenu.textProperty().bind(languageManager.getStringBinding("CreditButton"));
        CreditButton.textProperty().bind(languageManager.getStringBinding("CreditButton"));
        languageButton.textProperty().bind(languageManager.getStringBinding("languageButton"));
        darkTheme.textProperty().bind(languageManager.getStringBinding("darkTheme"));
        lightTheme.textProperty().bind(languageManager.getStringBinding("lightTheme"));
        aboutUs.textProperty().bind(languageManager.getStringBinding("aboutUs"));
        exitApp.textProperty().bind(languageManager.getStringBinding("exitApp"));
        currency.textProperty().bind(languageManager.getStringBinding("currency"));
        openFileButton.textProperty().bind(languageManager.getStringBinding("openFileButton"));
        saveAsButton.textProperty().bind(languageManager.getStringBinding("saveAsButton"));
        saveButton.textProperty().bind(languageManager.getStringBinding("saveButton"));
        themeButton.textProperty().bind(languageManager.getStringBinding("themeButton"));
        viewButton.textProperty().bind(languageManager.getStringBinding("viewButton"));
        newButton.textProperty().bind(languageManager.getStringBinding("newButton"));
        fileButton.textProperty().bind(languageManager.getStringBinding("fileButton"));
        settingsButton.textProperty().bind(languageManager.getStringBinding("settingsButton"));
        aboutButton.textProperty().bind(languageManager.getStringBinding("aboutButton"));
        financialCalculatorLabel.textProperty().bind(languageManager.getStringBinding("financialCalculatorLabel"));
    }



    @Override
    public void handleCurrencySelection() {
        List<String> choices = new ArrayList<>();
        choices.add("₴");
        choices.add("$");
        choices.add("£");
        choices.add("€");

        CurrencyManager currencyManager = CurrencyManager.getInstance();
        String defaultCurrency = currencyManager.getCurrency();

        ChoiceDialog<String> dialog = new ChoiceDialog<>(defaultCurrency, choices);
        dialog.setTitle("Choose Currency");
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("file:src/main/resources/com/netrunners/financialcalculator/assets/Logo.png"));
        dialog.setHeaderText(null);
        dialog.setContentText("Choose your currency:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String selectedCurrency = result.get();
            currencyManager.changeCurrency(selectedCurrency);
        }
    }
}

