package com.netrunners.financialcalculator.controller;

import com.netrunners.financialcalculator.ui.CurrencyManager;
import com.netrunners.financialcalculator.ui.AboutUsAlert;
import com.netrunners.financialcalculator.ui.ExitApp;
import com.netrunners.financialcalculator.ui.LanguageManager;
import com.netrunners.financialcalculator.ui.ThemeSelector;
import com.netrunners.financialcalculator.ui.WindowsOpener;

import javafx.scene.control.*;

public class ControllerUtils {
    private static final LanguageManager languageManager = LanguageManager.getInstance();
    public static void initializeMenuItems(MenuItem darkTheme, MenuItem lightTheme, MenuItem aboutUs, MenuItem exitApp, MenuItem depositButtonMenu, MenuItem creditButtonMenu, MenuItem languageButton, MenuItem currency) {
        darkTheme.setOnAction(event -> ThemeSelector.setDarkTheme());
        lightTheme.setOnAction(event -> ThemeSelector.setLightTheme());
        aboutUs.setOnAction(event -> AboutUsAlert.showAboutUs());
        exitApp.setOnAction(event -> ExitApp.exitApp());
        depositButtonMenu.setOnAction(event -> WindowsOpener.depositOpener());
        creditButtonMenu.setOnAction(event -> WindowsOpener.creditOpener());
        languageButton.setOnAction(event -> LanguageManager.changeLanguage());
        currency.setOnAction(event -> CurrencyManager.handleCurrencySelection());
    }

    public static void initializeTextBindings(Menu settingsButton, Menu aboutButton, Menu viewButton, Menu themeButton, MenuItem newButton, Menu fileButton, MenuItem depositButtonMenu, MenuItem creditButtonMenu, MenuItem languageButton, MenuItem darkTheme, MenuItem lightTheme, MenuItem aboutUs, MenuItem exitApp, MenuItem currency, MenuItem openFileButton, MenuItem saveAsButton, MenuItem saveButton) {
        provideTranslation(settingsButton, "settingsButton");
        provideTranslation(aboutButton, "aboutButton");
        provideTranslation(viewButton, "viewButton");
        provideTranslation(themeButton, "themeButton");
        provideTranslation(newButton, "newButton");
        provideTranslation(fileButton, "fileButton");
        provideTranslation(depositButtonMenu, "DepositButton");
        provideTranslation(creditButtonMenu, "CreditButton");
        provideTranslation(languageButton, "languageButton");
        provideTranslation(darkTheme, "darkTheme");
        provideTranslation(lightTheme, "lightTheme");
        provideTranslation(aboutUs, "aboutUs");
        provideTranslation(exitApp, "exitApp");
        provideTranslation(currency, "currency");
        provideTranslation(openFileButton, "openFileButton");
        provideTranslation(saveAsButton, "saveAsButton");
        provideTranslation(saveButton, "saveButton");
    }

    public static void provideTranslation(Labeled labeled, String key) {
        labeled.textProperty().bind(languageManager.getStringBinding(key));
    }

    public static void provideTranslation(MenuItem menuItem, String key) {
        menuItem.textProperty().bind(languageManager.getStringBinding(key));
    }

    public static void provideTranslation(TextField textField, String key) {
        textField.promptTextProperty().bind(languageManager.getStringBinding(key));
    }
    public static void provideTranslation(DatePicker datePicker, String key) {
        datePicker.promptTextProperty().bind(languageManager.getStringBinding(key));
    }
    public static void provideTranslation(TableColumn<Object[], ?> datePicker, String key) {
        datePicker.textProperty().bind(languageManager.getStringBinding(key));
    }



}
