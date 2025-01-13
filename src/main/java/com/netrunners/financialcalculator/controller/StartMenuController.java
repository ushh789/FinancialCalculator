package com.netrunners.financialcalculator.controller;

import com.netrunners.financialcalculator.logic.files.OpenFile;
import com.netrunners.financialcalculator.ui.WindowsOpener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Menu;

public class StartMenuController {

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

    @FXML
    void initialize() {
        saveButton.setDisable(true);
        saveAsButton.setDisable(true);

        // Menu items initialization
        ControllerUtils.initializeMenuItems(darkTheme, lightTheme, aboutUs, exitApp, depositButtonMenu, creditButtonMenu, languageButton, currency);
        DepositButton.setOnAction(event -> WindowsOpener.depositOpener());
        CreditButton.setOnAction(event -> WindowsOpener.creditOpener());
        openFileButton.setOnAction(event -> OpenFile.openFromSave());


        // Menu text bindings
        ControllerUtils.initializeTextBindings(settingsButton, aboutButton, viewButton, themeButton, newButton, fileButton, depositButtonMenu, creditButtonMenu, languageButton, darkTheme, lightTheme, aboutUs, exitApp, currency, openFileButton, saveAsButton, saveButton);
        ControllerUtils.provideTranslation(financialCalculatorLabel, "financialCalculatorLabel");
        ControllerUtils.provideTranslation(DepositButton, "DepositButton");
        ControllerUtils.provideTranslation(CreditButton, "CreditButton");
    }
}

