package com.netrunners.financialcalculator.controller;

import com.netrunners.financialcalculator.errorhandling.ErrorChecker;
import com.netrunners.financialcalculator.logic.files.OpenFile;
import com.netrunners.financialcalculator.logic.time.DatePickerRestrictions;
import com.netrunners.financialcalculator.logic.entity.deposit.CapitalisedDeposit;
import com.netrunners.financialcalculator.logic.entity.deposit.Deposit;
import com.netrunners.financialcalculator.logic.entity.deposit.UncapitalisedDeposit;
import com.netrunners.financialcalculator.ui.ExitApp;
import com.netrunners.financialcalculator.ui.LanguageManager;
import com.netrunners.financialcalculator.ui.CurrencyManager;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DepositMenuController {

    @FXML
    private MenuItem withdrawalOption1;

    @FXML
    private MenuItem withdrawalOption2;

    @FXML
    private MenuItem withdrawalOption3;
    @FXML
    private MenuItem withdrawalOption4;

    @FXML
    private Menu aboutButton;

    @FXML
    private MenuItem aboutUs;

    @FXML
    private Button closeWindow;

    @FXML
    private MenuItem creditButtonMenu;

    @FXML
    private MenuItem currency;

    @FXML
    private MenuItem darkTheme;

    @FXML
    private TextField depositAnnualPercentInput;

    @FXML
    private MenuItem depositButtonMenu;

    @FXML
    private CheckBox depositCapitalizationCheck;

    @FXML
    private DatePicker depositContractBeginning;

    @FXML
    private DatePicker depositContractEnding;

    @FXML
    private CheckBox depositEarlyWithdrawalCheck;

    @FXML
    private Label depositLabel;

    @FXML
    private Button depositSaveResult;

    @FXML
    private Button depositViewResult;

    @FXML
    private DatePicker depositWithdrawalDate;

    @FXML
    private MenuButton depositWithdrawalOption;

    @FXML
    private MenuItem exitApp;

    @FXML
    private Menu fileButton;

    @FXML
    private TextField investInput;

    @FXML
    private MenuItem languageButton;

    @FXML
    private MenuItem lightTheme;

    @FXML
    private Menu newButton;

    @FXML
    private MenuItem openFileButton;

    @FXML
    private MenuItem saveAsButton;

    @FXML
    private MenuItem saveButton;

    @FXML
    private Menu settingsButton;

    @FXML
    private Menu themeButton;

    @FXML
    private Menu viewButton;

    private final CurrencyManager currencyManager = CurrencyManager.getInstance();


    @FXML
    void initialize() {
        DatePickerRestrictions.setDatePickerRestrictions(depositContractBeginning, depositContractEnding, depositWithdrawalDate);
        depositWithdrawalDate.setVisible(false);
        depositWithdrawalDate.setDisable(true);
        saveButton.setDisable(true);
        saveAsButton.setDisable(true);

        // Menu items initialization
        ControllerUtils.initializeMenuItems(darkTheme, lightTheme, aboutUs, exitApp, depositButtonMenu, creditButtonMenu, languageButton, currency);
        openFileButton.setOnAction(event -> OpenFile.openFromSave());
        closeWindow.setOnAction(event -> ExitApp.closeCurrentWindow(closeWindow.getScene()));


        depositEarlyWithdrawalCheck.setOnAction(event -> {
            if (depositEarlyWithdrawalCheck.isSelected()) {
                depositWithdrawalDate.setVisible(true);
                depositWithdrawalDate.setDisable(false);
            } else {
                depositWithdrawalDate.setVisible(false);
                depositWithdrawalDate.setDisable(true);
            }
        });
        withdrawalOption1.setOnAction(event -> {
            depositWithdrawalOption.textProperty().unbind();
            depositWithdrawalOption.setText(withdrawalOption1.getText());
            ControllerUtils.provideTranslation(depositWithdrawalOption, "Option1");
        });
        withdrawalOption2.setOnAction(event -> {
            depositWithdrawalOption.textProperty().unbind();
            depositWithdrawalOption.setText(withdrawalOption2.getText());
            ControllerUtils.provideTranslation(depositWithdrawalOption, "Option2");
        });
        withdrawalOption3.setOnAction(event -> {
            depositWithdrawalOption.textProperty().unbind();
            depositWithdrawalOption.setText(withdrawalOption3.getText());
            ControllerUtils.provideTranslation(depositWithdrawalOption, "Option3");
        });
        withdrawalOption4.setOnAction(event -> {
            depositWithdrawalOption.textProperty().unbind();
            depositWithdrawalOption.setText(withdrawalOption4.getText());
            ControllerUtils.provideTranslation(depositWithdrawalOption, "Option4");
        });

        // Menu text bindings
        ControllerUtils.initializeTextBindings(settingsButton, aboutButton, viewButton, themeButton, newButton, fileButton, depositButtonMenu, creditButtonMenu, languageButton, darkTheme, lightTheme, aboutUs, exitApp, currency, openFileButton, saveAsButton, saveButton);
        ControllerUtils.provideTranslation(closeWindow, "closeWindow");
        ControllerUtils.provideTranslation(depositLabel, "DepositButton");
        ControllerUtils.provideTranslation(depositCapitalizationCheck, "DepositCapitalization");
        ControllerUtils.provideTranslation(depositEarlyWithdrawalCheck, "DepositEarlyWithdrawal");
        ControllerUtils.provideTranslation(depositWithdrawalOption, "WithdrawalOption");
        ControllerUtils.provideTranslation(depositSaveResult, "SaveResult");
        ControllerUtils.provideTranslation(depositViewResult, "ViewResult");
        ControllerUtils.provideTranslation(withdrawalOption1, "Option1");
        ControllerUtils.provideTranslation(withdrawalOption2, "Option2");
        ControllerUtils.provideTranslation(withdrawalOption3, "Option3");
        ControllerUtils.provideTranslation(withdrawalOption4, "Option4");
        ControllerUtils.provideTranslation(investInput, "InvestInput");
        ControllerUtils.provideTranslation(depositContractBeginning, "ContractBeginning");
        ControllerUtils.provideTranslation(depositAnnualPercentInput, "AnnualPercent");
        ControllerUtils.provideTranslation(depositContractEnding, "ContractEnding");
        ControllerUtils.provideTranslation(depositWithdrawalDate, "DepositEarlyWithdrawalDate");

        depositSaveResult.setOnAction(event -> {
            if (ErrorChecker.areFieldsValidInDeposit(investInput, depositAnnualPercentInput, depositWithdrawalOption, depositContractBeginning, depositContractEnding, depositWithdrawalDate, depositEarlyWithdrawalCheck)) {
                if (depositCapitalizationCheck.isSelected()) {
                    if (depositEarlyWithdrawalCheck.isSelected()) {
                        Deposit deposit = new CapitalisedDeposit(
                                Float.parseFloat(investInput.getText()),
                                currencyManager.getCurrency(),
                                Float.parseFloat(depositAnnualPercentInput.getText()),
                                depositContractBeginning.getValue(), depositContractEnding.getValue(),
                                depositEarlyWithdrawalCheck.isSelected(), depositWithdrawalDate.getValue(),
                                LanguageManager.checkOption(depositWithdrawalOption.getText())
                        );
                        deposit.save();
                    } else {
                        Deposit deposit = new CapitalisedDeposit(
                                Float.parseFloat(investInput.getText()),
                                currencyManager.getCurrency(),
                                Float.parseFloat(depositAnnualPercentInput.getText()),
                                depositContractBeginning.getValue(),
                                depositContractEnding.getValue(),
                                depositEarlyWithdrawalCheck.isSelected(),
                                LanguageManager.checkOption(depositWithdrawalOption.getText())
                        );
                        deposit.save();
                    }
                } else {
                    if (depositEarlyWithdrawalCheck.isSelected()) {
                        Deposit deposit = new UncapitalisedDeposit(
                                Float.parseFloat(investInput.getText()),
                                currencyManager.getCurrency(),
                                Float.parseFloat(depositAnnualPercentInput.getText()),
                                depositContractBeginning.getValue(),
                                depositContractEnding.getValue(),
                                depositEarlyWithdrawalCheck.isSelected(),
                                depositWithdrawalDate.getValue(),
                                LanguageManager.checkOption(depositWithdrawalOption.getText())
                        );
                        deposit.save();
                    } else {
                        Deposit deposit = new UncapitalisedDeposit(Float.parseFloat(
                                investInput.getText()),
                                currencyManager.getCurrency(),
                                Float.parseFloat(depositAnnualPercentInput.getText()),
                                depositContractBeginning.getValue(),
                                depositContractEnding.getValue(),
                                depositEarlyWithdrawalCheck.isSelected(),
                                LanguageManager.checkOption(depositWithdrawalOption.getText())
                        );
                        deposit.save();
                    }
                }
            }
        });
        depositViewResult.setOnAction(event -> {
            if (ErrorChecker.areFieldsValidInDeposit(investInput, depositAnnualPercentInput, depositWithdrawalOption, depositContractBeginning, depositContractEnding, depositWithdrawalDate, depositEarlyWithdrawalCheck)) {
                if (depositCapitalizationCheck.isSelected()) {
                    if (depositEarlyWithdrawalCheck.isSelected()) {
                        Deposit deposit = new CapitalisedDeposit(
                                Float.parseFloat(investInput.getText()),
                                currencyManager.getCurrency(),
                                Float.parseFloat(depositAnnualPercentInput.getText()),
                                depositContractBeginning.getValue(),
                                depositContractEnding.getValue(),
                                depositEarlyWithdrawalCheck.isSelected(),
                                depositWithdrawalDate.getValue(),
                                LanguageManager.checkOption(depositWithdrawalOption.getText())
                        );
                        deposit.sendToResultTable();
                    } else {
                        Deposit deposit = new CapitalisedDeposit(
                                Float.parseFloat(investInput.getText()),
                                currencyManager.getCurrency(),
                                Float.parseFloat(depositAnnualPercentInput.getText()),
                                depositContractBeginning.getValue(),
                                depositContractEnding.getValue(),
                                depositEarlyWithdrawalCheck.isSelected(),
                                LanguageManager.checkOption(depositWithdrawalOption.getText())
                        );
                        deposit.sendToResultTable();
                    }
                } else {
                    if (depositEarlyWithdrawalCheck.isSelected()) {
                        Deposit deposit = new UncapitalisedDeposit(
                                Float.parseFloat(investInput.getText()),
                                currencyManager.getCurrency(),
                                Float.parseFloat(depositAnnualPercentInput.getText()),
                                depositContractBeginning.getValue(),
                                depositContractEnding.getValue(),
                                depositEarlyWithdrawalCheck.isSelected(),
                                depositWithdrawalDate.getValue(),
                                LanguageManager.checkOption(depositWithdrawalOption.getText())
                        );
                        deposit.sendToResultTable();
                    } else {
                        Deposit deposit = new UncapitalisedDeposit(
                                Float.parseFloat(investInput.getText()),
                                currencyManager.getCurrency(),
                                Float.parseFloat(depositAnnualPercentInput.getText()),
                                depositContractBeginning.getValue(),
                                depositContractEnding.getValue(),
                                depositEarlyWithdrawalCheck.isSelected(),
                                LanguageManager.checkOption(depositWithdrawalOption.getText())
                        );
                        deposit.sendToResultTable();
                    }
                }
            }
        });
    }
}




