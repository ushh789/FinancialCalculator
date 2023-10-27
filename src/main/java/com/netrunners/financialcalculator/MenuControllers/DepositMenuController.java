package com.netrunners.financialcalculator.MenuControllers;

import static com.netrunners.financialcalculator.ErrorHandling.ErrorChecker.updateDatePickerStyle;
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
import com.netrunners.financialcalculator.VisualInstruments.LanguageManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class DepositMenuController {

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
        darkTheme.setOnAction(event -> {
            StartMenu.currentTheme = "file:src/main/resources/com/netrunners/financialcalculator/assets/darkTheme.css";
            for (Scene scene : StartMenu.openScenes) {
                scene.getStylesheets().clear();
                scene.getStylesheets().add(StartMenu.currentTheme);
            }
            for (DatePicker datePicker : StartMenu.datePickers) {
                updateDatePickerStyle(datePicker);
            }
        });

        lightTheme.setOnAction(event -> {
            StartMenu.currentTheme = "file:src/main/resources/com/netrunners/financialcalculator/assets/lightTheme.css";
            for (Scene scene : StartMenu.openScenes) {
                scene.getStylesheets().clear();
                scene.getStylesheets().add(StartMenu.currentTheme);
            }
            for (DatePicker datePicker : StartMenu.datePickers) {
                updateDatePickerStyle(datePicker);
            }
        });
        aboutUs.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("About our application");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("file:src/main/resources/com/netrunners/financialcalculator/assets/Logo.png"));
            alert.setHeaderText(null);
            alert.setContentText("""
                    Title: Financial Calculator by Netrunners

                    Description:
                    The "Financial Calculator by Netrunners" is a versatile and user-friendly software application designed to help individuals and businesses make informed financial decisions by calculating both deposit and credit-related parameters. With a range of powerful features, this tool simplifies financial planning and provides users with valuable insights into their financial investments and liabilities.

                    Key Features:

                    Deposit Calculator:

                    Calculate Compound Interest: Determine the future value of your savings or investments with compound interest calculations.
                    Flexible Periods: Specify the deposit term in months or years to align with your financial goals.
                    Customizable Contributions: Account for regular deposits or contributions to your savings.
                    Interest Rate Options: Calculate interest with both fixed and variable interest rates.

                    Credit Calculator:

                    Loan Repayment Estimation: Calculate the monthly or periodic payments required to pay off a loan or credit.
                    Amortization Schedules: View detailed amortization schedules to track the progress of your loan repayment.
                    Interest Considerations: Analyze how interest rates impact the total cost of a loan.
                    Interactive Graphs: Visualize loan repayment and interest payments through user-friendly graphs.

                    Dark and Light Themes:

                    Choose between dark and light themes to customize the application's appearance and enhance user experience.

                    "Financial Calculator by Netrunners" empowers individuals, students, professionals, and business owners to make informed decisions about their financial affairs. Whether you're planning to save money or seeking insights into loan repayments, this versatile tool offers a comprehensive set of features to help you on your financial journey. Start making smarter financial decisions today with this powerful yet user-friendly financial calculator.""");
            alert.showAndWait();
        });
        exitApp.setOnAction(event -> {
            for (Scene scene : StartMenu.openScenes) {
                closeCurrentWindow(scene);
            }
            System.exit(0);
        });
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

        depositButtonMenu.setOnAction(event -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(StartMenu.class.getResource("DepositMenu.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Deposit Menu");
                Scene scene = new Scene(fxmlLoader.load());
                scene.getStylesheets().add(StartMenu.currentTheme);
                stage.setScene(scene);
                StartMenu.openScenes.add(scene);
                stage.getIcons().add(new Image("file:src/main/resources/com/netrunners/financialcalculator/assets/Logo.png"));
                stage.setMaxHeight(820);
                stage.setMaxWidth(620);
                stage.setMinHeight(820);
                stage.setMinWidth(620);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        creditButtonMenu.setOnAction(event -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(StartMenu.class.getResource("CreditMenu.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Credit Menu");
                Scene scene = new Scene(fxmlLoader.load());
                scene.getStylesheets().add(StartMenu.currentTheme);
                stage.setScene(scene);
                StartMenu.openScenes.add(scene);
                stage.getIcons().add(new Image("file:src/main/resources/com/netrunners/financialcalculator/assets/Logo.png"));
                stage.setMaxHeight(820);
                stage.setMaxWidth(620);
                stage.setMinHeight(820);
                stage.setMinWidth(620);
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
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
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(StartMenu.class.getResource("ResultTable.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Result");
                Scene scene = new Scene(fxmlLoader.load());
                scene.getStylesheets().add(StartMenu.currentTheme);
                stage.setScene(scene);
                StartMenu.openScenes.add(scene);
                stage.getIcons().add(new Image("file:src/main/resources/com/netrunners/financialcalculator/assets/Logo.png"));
                stage.setMaxHeight(720);
                stage.setMaxWidth(620);
                stage.setMinHeight(820);
                stage.setMinWidth(620);
                stage.show();
            }catch (Exception e){
                e.printStackTrace();
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
}




