package com.netrunners.financialcalculator.MenuControllers;

import static com.netrunners.financialcalculator.MenuControllers.closeWindow.closeCurrentWindow;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

import com.netrunners.financialcalculator.ErrorHandling.ErrorChecker;
import com.netrunners.financialcalculator.LogicalInstrumnts.TimeFunctions.DatePickerRestrictions;
import com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation.Deposit.CapitalisedDeposit;
import com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation.Deposit.Deposit;
import com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation.Deposit.UncapitalisedDeposit;
import com.netrunners.financialcalculator.StartMenu;
import com.netrunners.financialcalculator.VisualInstruments.MenuActions.*;
import com.netrunners.financialcalculator.VisualInstruments.WindowsOpener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class DepositMenuController {

    private Parent root;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

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

    private String userSelectedCurrency;

    public void updateText() {
        creditButtonMenu.setText(LanguageManager.getInstance().getTranslation("creditButtonMenu"));
        depositButtonMenu.setText(LanguageManager.getInstance().getTranslation("depositButtonMenu"));
        languageButton.setText(LanguageManager.getInstance().getTranslation("languageButton"));
        darkTheme.setText(LanguageManager.getInstance().getTranslation("darkTheme"));
        lightTheme.setText(LanguageManager.getInstance().getTranslation("lightTheme"));
        aboutUs.setText(LanguageManager.getInstance().getTranslation("aboutUs"));
        exitApp.setText(LanguageManager.getInstance().getTranslation("exitApp"));
        currency.setText(LanguageManager.getInstance().getTranslation("currency"));
        openFileButton.setText(LanguageManager.getInstance().getTranslation("openFileButton"));
        saveAsButton.setText(LanguageManager.getInstance().getTranslation("saveAsButton"));
        saveButton.setText(LanguageManager.getInstance().getTranslation("saveButton"));
        themeButton.setText(LanguageManager.getInstance().getTranslation("themeButton"));
        viewButton.setText(LanguageManager.getInstance().getTranslation("viewButton"));
        newButton.setText(LanguageManager.getInstance().getTranslation("newButton"));
        fileButton.setText(LanguageManager.getInstance().getTranslation("fileButton"));
        settingsButton.setText(LanguageManager.getInstance().getTranslation("settingsButton"));
        aboutButton.setText(LanguageManager.getInstance().getTranslation("aboutButton"));
        closeWindow.setText(LanguageManager.getInstance().getTranslation("closeWindow"));
        depositLabel.setText(LanguageManager.getInstance().getTranslation("DepositButton"));
        depositCapitalizationCheck.setText(LanguageManager.getInstance().getTranslation("depositCapitalizationCheck"));
        depositEarlyWithdrawalCheck.setText(LanguageManager.getInstance().getTranslation("depositEarlyWithdrawalCheck"));
        depositWithdrawalOption.setText(LanguageManager.getInstance().getTranslation("depositWithdrawalOption"));
        depositSaveResult.setText(LanguageManager.getInstance().getTranslation("creditSaveResult"));
        depositViewResult.setText(LanguageManager.getInstance().getTranslation("creditViewResult"));
        depositAnnualPercentInput.setPromptText(LanguageManager.getInstance().getTranslation("depositAnnualPercentInput"));
        investInput.setPromptText(LanguageManager.getInstance().getTranslation("investInput"));
        depositContractBeginning.setPromptText(LanguageManager.getInstance().getTranslation("depositContractBeginning"));
        depositContractEnding.setPromptText(LanguageManager.getInstance().getTranslation("depositContractEnding"));
        depositWithdrawalDate.setPromptText(LanguageManager.getInstance().getTranslation("depositWithdrawalDate"));
        WithdrawalOption1.setText(LanguageManager.getInstance().getTranslation("WithdrawalOption1"));
        WithdrawalOption2.setText(LanguageManager.getInstance().getTranslation("WithdrawalOption2"));
        WithdrawalOption3.setText(LanguageManager.getInstance().getTranslation("WithdrawalOption3"));
        WithdrawalOption4.setText(LanguageManager.getInstance().getTranslation("WithdrawalOption4"));
    }

    @FXML
    void initialize() {
        StartMenu.datePickers.add(depositContractBeginning);
        StartMenu.datePickers.add(depositContractEnding);
        StartMenu.datePickers.add(depositWithdrawalDate);
        for (DatePicker datePicker : StartMenu.datePickers) {
            ErrorChecker.WrongDateListener(datePicker);
        }
        DatePickerRestrictions.setDatePickerRestrictionsWithdrawalHolidays(depositContractBeginning, depositContractEnding, depositWithdrawalDate);
        userSelectedCurrency = "USD";

        updateText();
        LanguageManager.getInstance().languageProperty().addListener((observable, oldValue, newValue) -> {
            updateText();
        });
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
        WithdrawalOption1.setOnAction(event -> depositWithdrawalOption.setText(WithdrawalOption1.getText()));
        WithdrawalOption2.setOnAction(event -> depositWithdrawalOption.setText(WithdrawalOption2.getText()));
        WithdrawalOption3.setOnAction(event -> depositWithdrawalOption.setText(WithdrawalOption3.getText()));
        WithdrawalOption4.setOnAction(event -> depositWithdrawalOption.setText(WithdrawalOption4.getText()));

        darkTheme.setOnAction(event -> ThemeSelector.setDarkTheme());
        lightTheme.setOnAction(event -> ThemeSelector.setLightTheme());
        aboutUs.setOnAction(event -> AboutUsAlert.showAboutUs());
        exitApp.setOnAction(event -> ExitApp.exitApp());
        depositButtonMenu.setOnAction(event -> WindowsOpener.depositOpener());
        creditButtonMenu.setOnAction(event -> WindowsOpener.creditOpener());

        currency.setOnAction(event -> {
            List<String> choices = new ArrayList<>();
            choices.add("UAH");
            choices.add("USD");
            choices.add("EUR");

            ChoiceDialog<String> dialog = new ChoiceDialog<>("UAH", choices);
            dialog.setTitle("Choose Currency");
            Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("file:src/main/resources/com/netrunners/financialcalculator/assets/Logo.png"));
            dialog.setHeaderText(null);
            dialog.setContentText("Choose your currency:");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                switch (result.get()) {
                    case "UAH":
                        break;
                    case "USD":
                        break;
                    case "EUR":
                        break;

                }
            }
            userSelectedCurrency = dialog.getSelectedItem();
        });


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
                switch (result.get()) {
                    case "English" -> setLanguage("en");
                    case "Українська" -> setLanguage("uk");
                    case "Español" -> setLanguage("es");
                    case "Français" -> setLanguage("fr");
                    case "Deutsch" -> setLanguage("de");
                    case "Czech" -> setLanguage("cs");
                    case "Polski" -> setLanguage("pl");
                    case "Nederlands" -> setLanguage("nl");
                    case "日本語" -> setLanguage("ja");
                    case "中国人" -> setLanguage("zh");
                }
            }
        });

        depositSaveResult.setOnAction(event -> {
            if (ErrorChecker.areFieldsValid(investInput, depositAnnualPercentInput, depositWithdrawalOption)) {
                float investment = Float.parseFloat(investInput.getText());
                float annualPercent = Float.parseFloat(depositAnnualPercentInput.getText());
                String depositCurrency = userSelectedCurrency;
                int withdrawalOptionSelected = -1000;
                switch (depositWithdrawalOption.getText()) {
                    case "Monthly" -> withdrawalOptionSelected = 1;
                    case "Quarterly" -> withdrawalOptionSelected = 2;
                    case "Yearly" -> withdrawalOptionSelected = 3;
                    case "At the end" -> withdrawalOptionSelected = 4;
                }
                LocalDate contractStartDate = depositContractBeginning.getValue();
                LocalDate contractEndDate = depositContractEnding.getValue();
                boolean earlyWithdrawalOption = depositEarlyWithdrawalCheck.isSelected();
                if (depositCapitalizationCheck.isSelected()) {
                    if (depositEarlyWithdrawalCheck.isSelected()) {
                        LocalDate earlyWithdrawal = depositWithdrawalDate.getValue();
                        Deposit deposit = new CapitalisedDeposit(investment, depositCurrency, annualPercent, contractStartDate, contractEndDate, earlyWithdrawalOption, earlyWithdrawal, withdrawalOptionSelected);
                        deposit.save();
                    } else {
                        Deposit deposit = new CapitalisedDeposit(investment, depositCurrency, annualPercent, contractStartDate, contractEndDate, earlyWithdrawalOption, withdrawalOptionSelected);
                        deposit.save();
                    }
                } else {
                    Deposit deposit = new UncapitalisedDeposit(investment, depositCurrency, annualPercent, contractStartDate, contractEndDate, earlyWithdrawalOption, withdrawalOptionSelected);
                    deposit.save();
                }
            }
        });
        depositViewResult.setOnAction(event -> {
            if (ErrorChecker.areFieldsValid(investInput, depositAnnualPercentInput, depositWithdrawalOption)) {
                float investment = Float.parseFloat(investInput.getText());
                float annualPercent = Float.parseFloat(depositAnnualPercentInput.getText());
                String depositCurrency = userSelectedCurrency;
                int withdrawalOptionSelected = -1000;
                switch (depositWithdrawalOption.getText()) {
                    case "Monthly" -> withdrawalOptionSelected = 1;
                    case "Quarterly" -> withdrawalOptionSelected = 2;
                    case "Yearly" -> withdrawalOptionSelected = 3;
                    case "At the end" -> withdrawalOptionSelected = 4;
                }
                LocalDate contractStartDate = depositContractBeginning.getValue();
                LocalDate contractEndDate = depositContractEnding.getValue();
                boolean earlyWithdrawalOption = depositEarlyWithdrawalCheck.isSelected();
                if (depositCapitalizationCheck.isSelected()) {
                    if (depositEarlyWithdrawalCheck.isSelected()) {
                        LocalDate earlyWithdrawal = depositWithdrawalDate.getValue();
                        Deposit deposit = new CapitalisedDeposit(investment, depositCurrency, annualPercent, contractStartDate, contractEndDate, earlyWithdrawalOption, earlyWithdrawal, withdrawalOptionSelected);
                        deposit.sendDepositToResultTable();
                    } else {
                        Deposit deposit = new CapitalisedDeposit(investment, depositCurrency, annualPercent, contractStartDate, contractEndDate, earlyWithdrawalOption, withdrawalOptionSelected);
                        deposit.sendDepositToResultTable();
                    }
                } else {
                    Deposit deposit = new UncapitalisedDeposit(investment, depositCurrency, annualPercent, contractStartDate, contractEndDate, earlyWithdrawalOption, withdrawalOptionSelected);
                    deposit.sendDepositToResultTable();
                }
            }
        });



    }

    public void setLanguage(String language) {
        LanguageManager.getInstance().setLanguage(language);
        creditButtonMenu.setText(LanguageManager.getInstance().getTranslation("creditButtonMenu"));
        depositButtonMenu.setText(LanguageManager.getInstance().getTranslation("depositButtonMenu"));
        languageButton.setText(LanguageManager.getInstance().getTranslation("languageButton"));
        darkTheme.setText(LanguageManager.getInstance().getTranslation("darkTheme"));
        lightTheme.setText(LanguageManager.getInstance().getTranslation("lightTheme"));
        aboutUs.setText(LanguageManager.getInstance().getTranslation("aboutUs"));
        exitApp.setText(LanguageManager.getInstance().getTranslation("exitApp"));
        currency.setText(LanguageManager.getInstance().getTranslation("currency"));
        openFileButton.setText(LanguageManager.getInstance().getTranslation("openFileButton"));
        saveAsButton.setText(LanguageManager.getInstance().getTranslation("saveAsButton"));
        saveButton.setText(LanguageManager.getInstance().getTranslation("saveButton"));
        themeButton.setText(LanguageManager.getInstance().getTranslation("themeButton"));
        viewButton.setText(LanguageManager.getInstance().getTranslation("viewButton"));
        newButton.setText(LanguageManager.getInstance().getTranslation("newButton"));
        fileButton.setText(LanguageManager.getInstance().getTranslation("fileButton"));
        settingsButton.setText(LanguageManager.getInstance().getTranslation("settingsButton"));
        aboutButton.setText(LanguageManager.getInstance().getTranslation("aboutButton"));
        closeWindow.setText(LanguageManager.getInstance().getTranslation("closeWindow"));
        depositLabel.setText(LanguageManager.getInstance().getTranslation("DepositButton"));
        depositCapitalizationCheck.setText(LanguageManager.getInstance().getTranslation("depositCapitalizationCheck"));
        depositEarlyWithdrawalCheck.setText(LanguageManager.getInstance().getTranslation("depositEarlyWithdrawalCheck"));
        depositWithdrawalOption.setText(LanguageManager.getInstance().getTranslation("depositWithdrawalOption"));
        depositSaveResult.setText(LanguageManager.getInstance().getTranslation("creditSaveResult"));
        depositViewResult.setText(LanguageManager.getInstance().getTranslation("creditViewResult"));
        depositAnnualPercentInput.setPromptText(LanguageManager.getInstance().getTranslation("depositAnnualPercentInput"));
        investInput.setPromptText(LanguageManager.getInstance().getTranslation("investInput"));
        depositContractBeginning.setPromptText(LanguageManager.getInstance().getTranslation("depositContractBeginning"));
        depositContractEnding.setPromptText(LanguageManager.getInstance().getTranslation("depositContractEnding"));
        depositWithdrawalDate.setPromptText(LanguageManager.getInstance().getTranslation("depositWithdrawalDate"));
        WithdrawalOption1.setText(LanguageManager.getInstance().getTranslation("WithdrawalOption1"));
        WithdrawalOption2.setText(LanguageManager.getInstance().getTranslation("WithdrawalOption2"));
        WithdrawalOption3.setText(LanguageManager.getInstance().getTranslation("WithdrawalOption3"));
        WithdrawalOption4.setText(LanguageManager.getInstance().getTranslation("WithdrawalOption4"));

    }
//    public Deposit sendDeposit(Deposit deposit) {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/netrunners/financialcalculator/ResultTable.fxml"));
//        try {
//            root = loader.load();
//            ResultTableController resultTableController = loader.getController();
//            resultTableController.updateTable(deposit);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return deposit;
//    }
}




