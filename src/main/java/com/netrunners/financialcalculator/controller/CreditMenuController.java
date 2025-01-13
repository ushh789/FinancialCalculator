package com.netrunners.financialcalculator.controller;

import com.netrunners.financialcalculator.errorhandling.ErrorChecker;
import com.netrunners.financialcalculator.logic.files.OpenFile;
import com.netrunners.financialcalculator.logic.time.DatePickerRestrictions;
import com.netrunners.financialcalculator.logic.entity.credit.Credit;
import com.netrunners.financialcalculator.logic.entity.credit.CreditWithHolidays;
import com.netrunners.financialcalculator.logic.entity.credit.CreditWithoutHolidays;
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

public class CreditMenuController {
    @FXML
    private MenuItem PaymentOption1;

    @FXML
    private MenuItem PaymentOption2;

    @FXML
    private MenuItem PaymentOption3;

    @FXML
    private MenuItem PaymentOption4;

    @FXML
    private Menu aboutButton;

    @FXML
    private MenuItem aboutUs;

    @FXML
    private TextField annualPercentInput;

    @FXML
    private CheckBox checkPaymentHolidays;

    @FXML
    private Button closeWindow;

    @FXML
    private DatePicker contractBeginning;

    @FXML
    private DatePicker contractEnding;

    @FXML
    private MenuItem creditButtonMenu;

    @FXML
    private Label creditLabel;

    @FXML
    private Button creditSaveResult;

    @FXML
    private Button creditViewResult;

    @FXML
    private MenuItem currency;

    @FXML
    private MenuItem darkTheme;

    @FXML
    private MenuItem depositButtonMenu;

    @FXML
    private MenuItem exitApp;

    @FXML
    private Menu fileButton;

    @FXML
    private DatePicker holidaysBeginning;

    @FXML
    private DatePicker holidaysEnding;

    @FXML
    private MenuItem languageButton;

    @FXML
    private MenuItem lightTheme;

    @FXML
    private TextField loanInput;

    @FXML
    private MenuItem openFileButton;

    @FXML
    private MenuButton paymentOption;

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

    @FXML
    private MenuItem newButton;

    private final CurrencyManager currencyManager = CurrencyManager.getInstance();

    @FXML
    void initialize() {
        DatePickerRestrictions.setDatePickerRestrictions(contractBeginning, contractEnding, holidaysBeginning, holidaysEnding);
        holidaysBeginning.setVisible(false);
        holidaysBeginning.setDisable(true);
        holidaysEnding.setVisible(false);
        holidaysEnding.setDisable(true);
        saveButton.setDisable(true);
        saveAsButton.setDisable(true);
        checkPaymentHolidays.setOnAction(event -> {
            if (checkPaymentHolidays.isSelected()) {
                holidaysBeginning.setVisible(true);
                holidaysEnding.setVisible(true);
                holidaysBeginning.setDisable(false);
                holidaysEnding.setDisable(false);
            } else {
                holidaysBeginning.setVisible(false);
                holidaysEnding.setVisible(false);
                holidaysBeginning.setDisable(true);
                holidaysEnding.setDisable(true);
            }
        });
        PaymentOption1.setOnAction(event -> {
            paymentOption.textProperty().unbind();
            paymentOption.setText(PaymentOption1.getText());
            ControllerUtils.provideTranslation(paymentOption, "Option1");
        });
        PaymentOption2.setOnAction(event -> {
            paymentOption.textProperty().unbind();
            paymentOption.setText(PaymentOption2.getText());
            ControllerUtils.provideTranslation(paymentOption, "Option2");
        });
        PaymentOption3.setOnAction(event -> {
            paymentOption.textProperty().unbind();
            paymentOption.setText(PaymentOption3.getText());
            ControllerUtils.provideTranslation(paymentOption, "Option3");
        });
        PaymentOption4.setOnAction(event -> {
            paymentOption.textProperty().unbind();
            paymentOption.setText(PaymentOption4.getText());
            ControllerUtils.provideTranslation(paymentOption, "Option4");
        });

        // Menu items
        ControllerUtils.initializeMenuItems(darkTheme, lightTheme, aboutUs, exitApp, depositButtonMenu, creditButtonMenu, languageButton, currency);
        openFileButton.setOnAction(event -> OpenFile.openFromSave());
        closeWindow.setOnAction(event -> ExitApp.closeCurrentWindow(closeWindow.getScene()));

        // Menu text bindings
        ControllerUtils.initializeTextBindings(settingsButton, aboutButton, viewButton, themeButton, newButton, fileButton, depositButtonMenu, creditButtonMenu, languageButton, darkTheme, lightTheme, aboutUs, exitApp, currency, openFileButton, saveAsButton, saveButton);
        ControllerUtils.provideTranslation(loanInput, "LoanInput");
        ControllerUtils.provideTranslation(annualPercentInput, "AnnualPercent");
        ControllerUtils.provideTranslation(contractBeginning, "ContractBeginning");
        ControllerUtils.provideTranslation(contractEnding, "ContractEnding");
        ControllerUtils.provideTranslation(paymentOption, "PaymentOption");
        ControllerUtils.provideTranslation(checkPaymentHolidays, "CreditHolidaysCheck");
        ControllerUtils.provideTranslation(holidaysBeginning, "HolidaysBeginning");
        ControllerUtils.provideTranslation(holidaysEnding, "HolidaysEnding");
        ControllerUtils.provideTranslation(creditSaveResult, "SaveResult");
        ControllerUtils.provideTranslation(creditViewResult, "ViewResult");
        ControllerUtils.provideTranslation(PaymentOption1, "Option1");
        ControllerUtils.provideTranslation(PaymentOption2, "Option2");
        ControllerUtils.provideTranslation(PaymentOption3, "Option3");
        ControllerUtils.provideTranslation(PaymentOption4, "Option4");
        ControllerUtils.provideTranslation(closeWindow, "closeWindow");
        ControllerUtils.provideTranslation(creditLabel, "CreditButton");

        creditSaveResult.setOnAction(event -> {
            if (ErrorChecker.areFieldsValidInCredit(loanInput, annualPercentInput, paymentOption, contractBeginning, contractEnding, checkPaymentHolidays, holidaysBeginning, holidaysEnding)) {
                if (checkPaymentHolidays.isSelected()) {
                    Credit credit = new CreditWithHolidays(
                            Float.parseFloat(loanInput.getText()),
                            currencyManager.getCurrency(),
                            Float.parseFloat(annualPercentInput.getText()),
                            contractBeginning.getValue(),
                            contractEnding.getValue(),
                            LanguageManager.checkOption(paymentOption.getText()),
                            holidaysBeginning.getValue(),
                            holidaysEnding.getValue()
                    );
                    credit.save();
                } else {
                    Credit credit = new CreditWithoutHolidays(
                            Float.parseFloat(loanInput.getText()),
                            currencyManager.getCurrency(),
                            Float.parseFloat(annualPercentInput.getText()),
                            contractBeginning.getValue(),
                            contractEnding.getValue(),
                            LanguageManager.checkOption(paymentOption.getText())
                    );
                    credit.save();
                }
            }
        });
        creditViewResult.setOnAction(event -> {
            if (ErrorChecker.areFieldsValidInCredit(loanInput, annualPercentInput, paymentOption, contractBeginning, contractEnding, checkPaymentHolidays, holidaysBeginning, holidaysEnding)) {
                if (checkPaymentHolidays.isSelected()) {
                    Credit credit = new CreditWithHolidays(
                            Float.parseFloat(loanInput.getText()),
                            currencyManager.getCurrency(),
                            Float.parseFloat(annualPercentInput.getText()), contractBeginning.getValue(),
                            contractEnding.getValue(), LanguageManager.checkOption(paymentOption.getText()),
                            holidaysBeginning.getValue(),
                            holidaysEnding.getValue()
                    );
                    credit.sendToResultTable();
                } else {
                    Credit credit = new CreditWithoutHolidays(
                            Float.parseFloat(loanInput.getText()),
                            currencyManager.getCurrency(),
                            Float.parseFloat(annualPercentInput.getText()),
                            contractBeginning.getValue(),
                            contractEnding.getValue(),
                            LanguageManager.checkOption(paymentOption.getText())
                    );
                    credit.sendToResultTable();
                }
            }
        });
    }
}





