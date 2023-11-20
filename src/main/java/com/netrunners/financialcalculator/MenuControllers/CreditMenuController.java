package com.netrunners.financialcalculator.MenuControllers;

import java.time.LocalDate;
import java.util.*;

import com.netrunners.financialcalculator.ErrorHandling.ErrorChecker;
import com.netrunners.financialcalculator.LogicalInstrumnts.FileInstruments.OpenFile;
import com.netrunners.financialcalculator.LogicalInstrumnts.TimeFunctions.DatePickerRestrictions;
import com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation.Credit.Credit;
import com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation.Credit.CreditWithHolidays;
import com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation.Credit.CreditWithoutHolidays;
import com.netrunners.financialcalculator.VisualInstruments.MenuActions.*;
import com.netrunners.financialcalculator.VisualInstruments.WindowsOpener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import static com.netrunners.financialcalculator.MenuControllers.closeWindow.closeCurrentWindow;

public class CreditMenuController implements CurrencyController {
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
    private String userSelectedCurrency;
    private LanguageManager languageManager = LanguageManager.getInstance();


    @FXML
    void initialize() {
        DatePickerRestrictions.setDatePickerRestrictionsHolidays(contractBeginning, contractEnding, holidaysBeginning, holidaysEnding);
        userSelectedCurrency = "$";
        holidaysBeginning.setVisible(false);
        holidaysBeginning.setDisable(true);
        holidaysEnding.setVisible(false);
        holidaysEnding.setDisable(true);
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

        closeWindow.setOnAction(event -> closeCurrentWindow(closeWindow.getScene()));
        darkTheme.setOnAction(event -> ThemeSelector.setDarkTheme());
        lightTheme.setOnAction(event -> ThemeSelector.setLightTheme());
        aboutUs.setOnAction(event -> AboutUsAlert.showAboutUs());
        exitApp.setOnAction(event -> ExitApp.exitApp());
        depositButtonMenu.setOnAction(event -> WindowsOpener.depositOpener());
        creditButtonMenu.setOnAction(event -> WindowsOpener.creditOpener());
        openFileButton.setOnAction(event -> OpenFile.openFromSave());
        currency.setOnAction(event -> handleCurrencySelection());


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
        creditButtonMenu.textProperty().bind(languageManager.getStringBinding("CreditButton"));
        depositButtonMenu.textProperty().bind(languageManager.getStringBinding("DepositButton"));
        creditLabel.textProperty().bind(languageManager.getStringBinding("CreditButton"));
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
        closeWindow.textProperty().bind(languageManager.getStringBinding("closeWindow"));
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
                String creditCurrency = userSelectedCurrency;
                int paymentOptionSelected = languageManager.checkOption(paymentOption.getText());
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
                String creditCurrency = userSelectedCurrency;
                int paymentOptionSelected = languageManager.checkOption(paymentOption.getText());

                LocalDate contractStartDate = contractBeginning.getValue();
                LocalDate contractEndDate = contractEnding.getValue();
                if (checkPaymentHolidays.isSelected()) {
                    LocalDate holidaysStartDate = holidaysBeginning.getValue();
                    LocalDate holidaysEndDate = holidaysEnding.getValue();
                    Credit credit = new CreditWithHolidays(creditLoan, creditCurrency, creditAnnualPercent, contractStartDate, contractEndDate, paymentOptionSelected, holidaysStartDate, holidaysEndDate);
                    credit.sendCreditToResultTable();
                } else {
                    Credit credit = new CreditWithoutHolidays(creditLoan, creditCurrency, creditAnnualPercent, contractStartDate, contractEndDate, paymentOptionSelected);
                    credit.sendCreditToResultTable();
                }
            }
        });
    }


    @Override
    public void handleCurrencySelection() {
        List<String> choices = new ArrayList<>();
        choices.add("₴");
        choices.add("$");
        choices.add("£");
        choices.add("€");

        ChoiceDialog<String> dialog = new ChoiceDialog<>("₴", choices);
        dialog.setTitle("Choose Currency");
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("file:src/main/resources/com/netrunners/financialcalculator/assets/Logo.png"));
        dialog.setHeaderText(null);
        dialog.setContentText("Choose your currency:");

        dialog.showAndWait();
        userSelectedCurrency = dialog.getSelectedItem();
    }
}





