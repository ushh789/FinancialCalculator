package com.netrunners.financialcalculator.MenuControllers;

import java.time.LocalDate;

import com.netrunners.financialcalculator.ErrorHandling.ErrorChecker;
import com.netrunners.financialcalculator.LogicalInstrumnts.FileInstruments.OpenFile;
import com.netrunners.financialcalculator.LogicalInstrumnts.TimeFunctions.DatePickerRestrictions;
import com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation.Credit.Credit;
import com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation.Credit.CreditWithHolidays;
import com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation.Credit.CreditWithoutHolidays;
import com.netrunners.financialcalculator.VisualInstruments.MenuActions.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
    private final LanguageManager languageManager = LanguageManager.getInstance();


    @FXML
    void initialize() {
        DatePickerRestrictions.setDatePickerRestrictionsHolidays(contractBeginning, contractEnding, holidaysBeginning, holidaysEnding);
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
            paymentOption.textProperty().bind(languageManager.getStringBinding("Option1"));
        });
        PaymentOption2.setOnAction(event -> {
            paymentOption.textProperty().unbind();
            paymentOption.setText(PaymentOption2.getText());
            paymentOption.textProperty().bind(languageManager.getStringBinding("Option2"));
        });
        PaymentOption3.setOnAction(event -> {
            paymentOption.textProperty().unbind();
            paymentOption.setText(PaymentOption3.getText());
            paymentOption.textProperty().bind(languageManager.getStringBinding("Option3"));
        });
        PaymentOption4.setOnAction(event -> {
            paymentOption.textProperty().unbind();
            paymentOption.setText(PaymentOption4.getText());
            paymentOption.textProperty().bind(languageManager.getStringBinding("Option4"));
        });

        // Menu items
        ControllerUtils.initializeMenuItems(darkTheme, lightTheme, aboutUs, exitApp, depositButtonMenu, creditButtonMenu, languageButton, currency);
        openFileButton.setOnAction(event -> OpenFile.openFromSave());
        closeWindow.setOnAction(event -> ExitApp.closeCurrentWindow(closeWindow.getScene()));

        // Menu text bindings
        ControllerUtils.initializeTextBindings(settingsButton, languageManager, aboutButton, viewButton, themeButton, newButton, fileButton, depositButtonMenu, creditButtonMenu, languageButton, darkTheme, lightTheme, aboutUs, exitApp, currency, openFileButton, saveAsButton, saveButton);
        closeWindow.textProperty().bind(languageManager.getStringBinding("closeWindow"));
        creditLabel.textProperty().bind(languageManager.getStringBinding("CreditButton"));
        loanInput.promptTextProperty().bind(languageManager.getStringBinding("LoanInput"));
        annualPercentInput.promptTextProperty().bind(languageManager.getStringBinding("AnnualPercent"));
        contractBeginning.promptTextProperty().bind(languageManager.getStringBinding("ContractBeginning"));
        contractEnding.promptTextProperty().bind(languageManager.getStringBinding("ContractEnding"));
        paymentOption.textProperty().bind(languageManager.getStringBinding("PaymentOption"));
        checkPaymentHolidays.textProperty().bind(languageManager.getStringBinding("CreditHolidaysCheck"));
        holidaysBeginning.promptTextProperty().bind(languageManager.getStringBinding("HolidaysBeginning"));
        holidaysEnding.promptTextProperty().bind(languageManager.getStringBinding("HolidaysEnding"));
        creditSaveResult.textProperty().bind(languageManager.getStringBinding("SaveResult"));
        creditViewResult.textProperty().bind(languageManager.getStringBinding("ViewResult"));
        PaymentOption1.textProperty().bind(languageManager.getStringBinding("Option1"));
        PaymentOption2.textProperty().bind(languageManager.getStringBinding("Option2"));
        PaymentOption3.textProperty().bind(languageManager.getStringBinding("Option3"));
        PaymentOption4.textProperty().bind(languageManager.getStringBinding("Option4"));


        creditSaveResult.setOnAction(event -> {
            if (ErrorChecker.areFieldsValidInCredit(loanInput, annualPercentInput, paymentOption, contractBeginning, contractEnding, checkPaymentHolidays, holidaysBeginning, holidaysEnding)) {
                float creditLoan = Float.parseFloat(loanInput.getText());
                float creditAnnualPercent = Float.parseFloat(annualPercentInput.getText());
                String creditCurrency = CurrencyManager.getInstance().getCurrency();
                int paymentOptionSelected = LanguageManager.checkOption(paymentOption.getText());
                LocalDate contractStartDate = contractBeginning.getValue();
                LocalDate contractEndDate = contractEnding.getValue();
                if (checkPaymentHolidays.isSelected()) {
                    LocalDate holidaysStartDate = holidaysBeginning.getValue();
                    LocalDate holidaysEndDate = holidaysEnding.getValue();
                    Credit credit = new CreditWithHolidays(creditLoan, creditCurrency, creditAnnualPercent, contractStartDate, contractEndDate, paymentOptionSelected, holidaysStartDate, holidaysEndDate);
                    credit.save();
                } else {
                    Credit credit = new CreditWithoutHolidays(creditLoan, creditCurrency, creditAnnualPercent, contractStartDate, contractEndDate, paymentOptionSelected);
                    credit.save();
                }
            }
        });
        creditViewResult.setOnAction(event -> {
            if (ErrorChecker.areFieldsValidInCredit(loanInput, annualPercentInput, paymentOption, contractBeginning, contractEnding, checkPaymentHolidays, holidaysBeginning, holidaysEnding)) {
                float creditLoan = Float.parseFloat(loanInput.getText());
                float creditAnnualPercent = Float.parseFloat(annualPercentInput.getText());
                String creditCurrency = CurrencyManager.getInstance().getCurrency();
                int paymentOptionSelected = LanguageManager.checkOption(paymentOption.getText());
                LocalDate contractStartDate = contractBeginning.getValue();
                LocalDate contractEndDate = contractEnding.getValue();
                if (checkPaymentHolidays.isSelected()) {
                    LocalDate holidaysStartDate = holidaysBeginning.getValue();
                    LocalDate holidaysEndDate = holidaysEnding.getValue();
                    Credit credit = new CreditWithHolidays(creditLoan, creditCurrency, creditAnnualPercent, contractStartDate, contractEndDate, paymentOptionSelected, holidaysStartDate, holidaysEndDate);
                    credit.sendToResultTable();
                } else {
                    Credit credit = new CreditWithoutHolidays(creditLoan, creditCurrency, creditAnnualPercent, contractStartDate, contractEndDate, paymentOptionSelected);
                    credit.sendToResultTable();
                }
            }
        });
    }
}





