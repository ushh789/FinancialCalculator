package com.netrunners.financialcalculator.MenuControllers;

import java.time.LocalDate;

import com.netrunners.financialcalculator.ErrorHandling.ErrorChecker;
import com.netrunners.financialcalculator.LogicalInstrumnts.FileInstruments.OpenFile;
import com.netrunners.financialcalculator.LogicalInstrumnts.TimeFunctions.DatePickerRestrictions;
import com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation.Deposit.CapitalisedDeposit;
import com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation.Deposit.Deposit;
import com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation.Deposit.UncapitalisedDeposit;
import com.netrunners.financialcalculator.VisualInstruments.MenuActions.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class DepositMenuController {

    @FXML
    private MenuItem WithdrawalOption1;

    @FXML
    private MenuItem WithdrawalOption2;

    @FXML
    private MenuItem WithdrawalOption3;

    @FXML
    private MenuItem WithdrawalOption4;

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

    private final LanguageManager languageManager = LanguageManager.getInstance();


    @FXML
    void initialize() {
        DatePickerRestrictions.setDatePickerRestrictionsWithdrawalHolidays(depositContractBeginning, depositContractEnding, depositWithdrawalDate);
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
        WithdrawalOption1.setOnAction(event -> {
            depositWithdrawalOption.textProperty().unbind();
            depositWithdrawalOption.setText(WithdrawalOption1.getText());
            depositWithdrawalOption.textProperty().bind(languageManager.getStringBinding("Option1"));
        });
        WithdrawalOption2.setOnAction(event -> {
            depositWithdrawalOption.textProperty().unbind();
            depositWithdrawalOption.setText(WithdrawalOption2.getText());
            depositWithdrawalOption.textProperty().bind(languageManager.getStringBinding("Option2"));
        });
        WithdrawalOption3.setOnAction(event -> {
            depositWithdrawalOption.textProperty().unbind();
            depositWithdrawalOption.setText(WithdrawalOption3.getText());
            depositWithdrawalOption.textProperty().bind(languageManager.getStringBinding("Option3"));
        });
        WithdrawalOption4.setOnAction(event -> {
            depositWithdrawalOption.textProperty().unbind();
            depositWithdrawalOption.setText(WithdrawalOption4.getText());
            depositWithdrawalOption.textProperty().bind(languageManager.getStringBinding("Option4"));
        });

        // Menu text bindings
        ControllerUtils.initializeTextBindings(settingsButton, languageManager, aboutButton, viewButton, themeButton, newButton, fileButton, depositButtonMenu, creditButtonMenu, languageButton, darkTheme, lightTheme, aboutUs, exitApp, currency, openFileButton, saveAsButton, saveButton);
        closeWindow.textProperty().bind(languageManager.getStringBinding("closeWindow"));
        depositLabel.textProperty().bind(languageManager.getStringBinding("DepositButton"));
        depositCapitalizationCheck.textProperty().bind(languageManager.getStringBinding("DepositCapitalization"));
        depositEarlyWithdrawalCheck.textProperty().bind(languageManager.getStringBinding("DepositEarlyWithdrawal"));
        depositWithdrawalOption.textProperty().bind(languageManager.getStringBinding("WithdrawalOption"));
        depositSaveResult.textProperty().bind(languageManager.getStringBinding("SaveResult"));
        depositViewResult.textProperty().bind(languageManager.getStringBinding("ViewResult"));
        depositAnnualPercentInput.promptTextProperty().bind(languageManager.getStringBinding("AnnualPercent"));
        investInput.promptTextProperty().bind(languageManager.getStringBinding("InvestInput"));
        depositContractBeginning.promptTextProperty().bind(languageManager.getStringBinding("ContractBeginning"));
        depositContractEnding.promptTextProperty().bind(languageManager.getStringBinding("ContractEnding"));
        depositWithdrawalDate.promptTextProperty().bind(languageManager.getStringBinding("DepositEarlyWithdrawalDate"));
        WithdrawalOption1.textProperty().bind(languageManager.getStringBinding("Option1"));
        WithdrawalOption2.textProperty().bind(languageManager.getStringBinding("Option2"));
        WithdrawalOption3.textProperty().bind(languageManager.getStringBinding("Option3"));
        WithdrawalOption4.textProperty().bind(languageManager.getStringBinding("Option4"));


        depositSaveResult.setOnAction(event -> {
            if (ErrorChecker.areFieldsValidInDeposit(investInput, depositAnnualPercentInput, depositWithdrawalOption, depositContractBeginning, depositContractEnding, depositWithdrawalDate, depositEarlyWithdrawalCheck)) {
                float investment = Float.parseFloat(investInput.getText());
                float annualPercent = Float.parseFloat(depositAnnualPercentInput.getText());
                String depositCurrency = CurrencyManager.getInstance().getCurrency();
                int withdrawalOptionSelected = LanguageManager.checkOption(depositWithdrawalOption.getText());
                LocalDate contractStartDate = depositContractBeginning.getValue();
                LocalDate contractEndDate = depositContractEnding.getValue();
                boolean earlyWithdrawalOption = depositEarlyWithdrawalCheck.isSelected();
                LocalDate earlyWithdrawal;
                earlyWithdrawal = depositWithdrawalDate.getValue();
                if (depositCapitalizationCheck.isSelected()) {
                    if (depositEarlyWithdrawalCheck.isSelected()) {
                        Deposit deposit = new CapitalisedDeposit(investment, depositCurrency, annualPercent, contractStartDate, contractEndDate, earlyWithdrawalOption, earlyWithdrawal, withdrawalOptionSelected);
                        deposit.save();
                    } else {
                        Deposit deposit = new CapitalisedDeposit(investment, depositCurrency, annualPercent, contractStartDate, contractEndDate, earlyWithdrawalOption, withdrawalOptionSelected);
                        deposit.save();
                    }
                } else {
                    if (depositEarlyWithdrawalCheck.isSelected()) {
                        Deposit deposit = new UncapitalisedDeposit(investment, depositCurrency, annualPercent, contractStartDate, contractEndDate, earlyWithdrawalOption, earlyWithdrawal, withdrawalOptionSelected);
                        deposit.save();
                    } else {
                        Deposit deposit = new UncapitalisedDeposit(investment, depositCurrency, annualPercent, contractStartDate, contractEndDate, earlyWithdrawalOption, withdrawalOptionSelected);
                        deposit.save();
                    }
                }
            }
        });
        depositViewResult.setOnAction(event -> {
            if (ErrorChecker.areFieldsValidInDeposit(investInput, depositAnnualPercentInput, depositWithdrawalOption, depositContractBeginning, depositContractEnding, depositWithdrawalDate, depositEarlyWithdrawalCheck)) {
                float investment = Float.parseFloat(investInput.getText());
                float annualPercent = Float.parseFloat(depositAnnualPercentInput.getText());
                String depositCurrency = CurrencyManager.getInstance().getCurrency();
                int withdrawalOptionSelected = LanguageManager.checkOption(depositWithdrawalOption.getText());
                LocalDate contractStartDate = depositContractBeginning.getValue();
                LocalDate contractEndDate = depositContractEnding.getValue();
                boolean earlyWithdrawalOption = depositEarlyWithdrawalCheck.isSelected();
                LocalDate earlyWithdrawal;
                earlyWithdrawal = depositWithdrawalDate.getValue();
                if (depositCapitalizationCheck.isSelected()) {
                    if (depositEarlyWithdrawalCheck.isSelected()) {
                        Deposit deposit = new CapitalisedDeposit(investment, depositCurrency, annualPercent, contractStartDate, contractEndDate, earlyWithdrawalOption, earlyWithdrawal, withdrawalOptionSelected);
                        deposit.sendToResultTable();
                    } else {
                        Deposit deposit = new CapitalisedDeposit(investment, depositCurrency, annualPercent, contractStartDate, contractEndDate, earlyWithdrawalOption, withdrawalOptionSelected);
                        deposit.sendToResultTable();
                    }
                } else {
                    if (depositEarlyWithdrawalCheck.isSelected()) {
                        Deposit deposit = new UncapitalisedDeposit(investment, depositCurrency, annualPercent, contractStartDate, contractEndDate, earlyWithdrawalOption, earlyWithdrawal, withdrawalOptionSelected);
                        deposit.sendToResultTable();
                    } else {
                        Deposit deposit = new UncapitalisedDeposit(investment, depositCurrency, annualPercent, contractStartDate, contractEndDate, earlyWithdrawalOption, withdrawalOptionSelected);
                        deposit.sendToResultTable();
                    }
                }
            }
        });
    }
}




