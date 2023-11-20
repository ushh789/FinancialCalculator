package com.netrunners.financialcalculator.MenuControllers;

import static com.netrunners.financialcalculator.MenuControllers.closeWindow.closeCurrentWindow;

import java.time.LocalDate;
import java.util.*;

import com.netrunners.financialcalculator.ErrorHandling.ErrorChecker;
import com.netrunners.financialcalculator.LogicalInstrumnts.FileInstruments.OpenFile;
import com.netrunners.financialcalculator.LogicalInstrumnts.TimeFunctions.DatePickerRestrictions;
import com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation.Deposit.CapitalisedDeposit;
import com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation.Deposit.Deposit;
import com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation.Deposit.UncapitalisedDeposit;
import com.netrunners.financialcalculator.VisualInstruments.MenuActions.*;
import com.netrunners.financialcalculator.VisualInstruments.WindowsOpener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class DepositMenuController implements CurrencyController {

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

    private LanguageManager languageManager = LanguageManager.getInstance();


    @FXML
    void initialize() {
        System.out.println(CurrencyManager.getInstance().getCurrency());
        DatePickerRestrictions.setDatePickerRestrictionsWithdrawalHolidays(depositContractBeginning, depositContractEnding, depositWithdrawalDate);
        depositWithdrawalDate.setVisible(false);
        depositWithdrawalDate.setDisable(true);
        closeWindow.setOnAction(event -> closeCurrentWindow(closeWindow.getScene()));
        depositEarlyWithdrawalCheck.setOnAction(event -> {
            if (depositEarlyWithdrawalCheck.isSelected()) {
                depositWithdrawalDate.setVisible(true);
                depositWithdrawalDate.setDisable(false);
            } else {
                depositWithdrawalDate.setVisible(false);
                depositWithdrawalDate.setDisable(true);
            }
        });
        WithdrawalOption1.setOnAction(event ->{
            depositWithdrawalOption.textProperty().unbind();
            depositWithdrawalOption.setText(WithdrawalOption1.getText());
            depositWithdrawalOption.textProperty().bind(languageManager.getStringBinding("Option1"));
        } );
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
        newButton.textProperty().bind(languageManager.getStringBinding("newButton"));
        fileButton.textProperty().bind(languageManager.getStringBinding("fileButton"));
        settingsButton.textProperty().bind(languageManager.getStringBinding("settingsButton"));
        aboutButton.textProperty().bind(languageManager.getStringBinding("aboutButton"));
        viewButton.textProperty().bind(languageManager.getStringBinding("viewButton"));
        themeButton.textProperty().bind(languageManager.getStringBinding("themeButton"));
        closeWindow.textProperty().bind(languageManager.getStringBinding("closeWindow"));


        depositSaveResult.setOnAction(event -> {
            if (ErrorChecker.areFieldsValidInDeposit(investInput, depositAnnualPercentInput, depositWithdrawalOption, depositContractBeginning, depositContractEnding, depositWithdrawalDate, depositEarlyWithdrawalCheck)) {
                float investment = Float.parseFloat(investInput.getText());
                float annualPercent = Float.parseFloat(depositAnnualPercentInput.getText());
                String depositCurrency = CurrencyManager.getInstance().getCurrency();
                int withdrawalOptionSelected = languageManager.checkOption(depositWithdrawalOption.getText());

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
                int withdrawalOptionSelected = languageManager.checkOption(depositWithdrawalOption.getText());
                LocalDate contractStartDate = depositContractBeginning.getValue();
                LocalDate contractEndDate = depositContractEnding.getValue();
                boolean earlyWithdrawalOption = depositEarlyWithdrawalCheck.isSelected();
                LocalDate earlyWithdrawal;
                earlyWithdrawal = depositWithdrawalDate.getValue();
                if (depositCapitalizationCheck.isSelected()) {
                    if (depositEarlyWithdrawalCheck.isSelected()) {
                        Deposit deposit = new CapitalisedDeposit(investment, depositCurrency, annualPercent, contractStartDate, contractEndDate, earlyWithdrawalOption, earlyWithdrawal, withdrawalOptionSelected);
                        deposit.sendDepositToResultTable();
                    } else {
                        Deposit deposit = new CapitalisedDeposit(investment, depositCurrency, annualPercent, contractStartDate, contractEndDate, earlyWithdrawalOption, withdrawalOptionSelected);
                        deposit.sendDepositToResultTable();
                    }
                } else {
                    if (depositEarlyWithdrawalCheck.isSelected()) {
                        Deposit deposit = new UncapitalisedDeposit(investment, depositCurrency, annualPercent, contractStartDate, contractEndDate, earlyWithdrawalOption, earlyWithdrawal, withdrawalOptionSelected);
                        deposit.sendDepositToResultTable();
                    } else {
                        Deposit deposit = new UncapitalisedDeposit(investment, depositCurrency, annualPercent, contractStartDate, contractEndDate, earlyWithdrawalOption, withdrawalOptionSelected);
                        deposit.sendDepositToResultTable();
                    }
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

        CurrencyManager currencyManager = CurrencyManager.getInstance();
        String defaultCurrency = currencyManager.getCurrency();

        ChoiceDialog<String> dialog = new ChoiceDialog<>(defaultCurrency, choices);
        dialog.setTitle("Choose Currency");
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("file:src/main/resources/com/netrunners/financialcalculator/assets/Logo.png"));
        dialog.setHeaderText(null);
        dialog.setContentText("Choose your currency:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String selectedCurrency = result.get();
            currencyManager.changeCurrency(selectedCurrency);
        }
    }
}




