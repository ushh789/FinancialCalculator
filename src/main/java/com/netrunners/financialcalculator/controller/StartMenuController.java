package com.netrunners.financialcalculator.controller;

import com.netrunners.financialcalculator.errorhandling.exceptions.LoadSaveException;
import com.netrunners.financialcalculator.logic.files.OpenFile;
import com.netrunners.financialcalculator.ui.WindowsOpener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Menu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final Logger logger = LoggerFactory.getLogger(StartMenuController.class);

    @FXML
    void initialize() {
        saveButton.setDisable(true);
        saveAsButton.setDisable(true);

        // Menu items initialization
        ControllerUtils.initializeMenuItems(darkTheme, lightTheme, aboutUs, exitApp, depositButtonMenu, creditButtonMenu, languageButton, currency);
        DepositButton.setOnAction(event -> WindowsOpener.depositOpener());
        CreditButton.setOnAction(event -> WindowsOpener.creditOpener());
        openFileButton.setOnAction(event -> {
            try {
                OpenFile.openFromSave();
            } catch (LoadSaveException e) {
                logger.error(e.getMessage(), e);
            }
        });


        // Menu text bindings
        ControllerUtils.initializeTextBindings(settingsButton, aboutButton, viewButton, themeButton, newButton, fileButton, depositButtonMenu, creditButtonMenu, languageButton, darkTheme, lightTheme, aboutUs, exitApp, currency, openFileButton, saveAsButton, saveButton);
        ControllerUtils.provideTranslation(financialCalculatorLabel, "financialCalculatorLabel");
        ControllerUtils.provideTranslation(DepositButton, "DepositButton");
        ControllerUtils.provideTranslation(CreditButton, "CreditButton");

        logger.info("Start menu successfully initialized");
    }
}

