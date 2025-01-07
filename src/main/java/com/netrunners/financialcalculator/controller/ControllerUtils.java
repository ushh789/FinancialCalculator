package com.netrunners.financialcalculator.controller;

import com.netrunners.financialcalculator.ui.CurrencyManager;
import com.netrunners.financialcalculator.ui.AboutUsAlert;
import com.netrunners.financialcalculator.ui.ExitApp;
import com.netrunners.financialcalculator.ui.LanguageManager;
import com.netrunners.financialcalculator.ui.ThemeSelector;
import com.netrunners.financialcalculator.ui.WindowsOpener;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class ControllerUtils {
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

    public static void initializeTextBindings(Menu settingsButton, LanguageManager languageManager, Menu aboutButton, Menu viewButton, Menu themeButton, MenuItem newButton, Menu fileButton, MenuItem depositButtonMenu, MenuItem creditButtonMenu, MenuItem languageButton, MenuItem darkTheme, MenuItem lightTheme, MenuItem aboutUs, MenuItem exitApp, MenuItem currency, MenuItem openFileButton, MenuItem saveAsButton, MenuItem saveButton) {
        settingsButton.textProperty().bind(languageManager.getStringBinding("settingsButton"));
        aboutButton.textProperty().bind(languageManager.getStringBinding("aboutButton"));
        viewButton.textProperty().bind(languageManager.getStringBinding("viewButton"));
        themeButton.textProperty().bind(languageManager.getStringBinding("themeButton"));
        newButton.textProperty().bind(languageManager.getStringBinding("newButton"));
        fileButton.textProperty().bind(languageManager.getStringBinding("fileButton"));
        depositButtonMenu.textProperty().bind(languageManager.getStringBinding("DepositButton"));
        creditButtonMenu.textProperty().bind(languageManager.getStringBinding("CreditButton"));
        languageButton.textProperty().bind(languageManager.getStringBinding("languageButton"));
        darkTheme.textProperty().bind(languageManager.getStringBinding("darkTheme"));
        lightTheme.textProperty().bind(languageManager.getStringBinding("lightTheme"));
        aboutUs.textProperty().bind(languageManager.getStringBinding("aboutUs"));
        exitApp.textProperty().bind(languageManager.getStringBinding("exitApp"));
        currency.textProperty().bind(languageManager.getStringBinding("currency"));
        openFileButton.textProperty().bind(languageManager.getStringBinding("openFileButton"));
        saveAsButton.textProperty().bind(languageManager.getStringBinding("saveAsButton"));
        saveButton.textProperty().bind(languageManager.getStringBinding("saveButton"));
    }

}
