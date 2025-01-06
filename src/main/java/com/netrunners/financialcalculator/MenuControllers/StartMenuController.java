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

    private final LanguageManager languageManager = LanguageManager.getInstance();


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
        ControllerUtils.initializeTextBindings(settingsButton, languageManager, aboutButton, viewButton, themeButton, newButton, fileButton, depositButtonMenu, creditButtonMenu, languageButton, darkTheme, lightTheme, aboutUs, exitApp, currency, openFileButton, saveAsButton, saveButton);
        financialCalculatorLabel.textProperty().bind(languageManager.getStringBinding("financialCalculatorLabel"));
        DepositButton.textProperty().bind(languageManager.getStringBinding("DepositButton"));
        CreditButton.textProperty().bind(languageManager.getStringBinding("CreditButton"));
    }
}

