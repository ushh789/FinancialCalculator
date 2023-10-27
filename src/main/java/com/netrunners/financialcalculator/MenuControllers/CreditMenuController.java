package com.netrunners.financialcalculator.MenuControllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.netrunners.financialcalculator.ErrorHandling.ErrorChecker;
import com.netrunners.financialcalculator.LogicalInstrumnts.TimeFunctions.DatePickerRestrictions;
import com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation.Credit.Credit;
import com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation.Credit.CreditWithHolidays;
import com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation.Credit.CreditWithoutHolidays;
import com.netrunners.financialcalculator.StartMenu;
import com.netrunners.financialcalculator.VisualInstruments.LanguageManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import static com.netrunners.financialcalculator.ErrorHandling.ErrorChecker.updateDatePickerStyle;
import static com.netrunners.financialcalculator.MenuControllers.closeWindow.closeCurrentWindow;

public class CreditMenuController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

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
        creditLabel.setText(LanguageManager.getInstance().getTranslation("CreditButton"));
        loanInput.setPromptText(LanguageManager.getInstance().getTranslation("loanInput"));
        annualPercentInput.setPromptText(LanguageManager.getInstance().getTranslation("annualPercentInput"));
        contractBeginning.setPromptText(LanguageManager.getInstance().getTranslation("contractBeginning"));
        contractEnding.setPromptText(LanguageManager.getInstance().getTranslation("contractEnding"));
        paymentOption.setText(LanguageManager.getInstance().getTranslation("paymentOption"));
        checkPaymentHolidays.setText(LanguageManager.getInstance().getTranslation("checkPaymentHolidays"));
        holidaysBeginning.setPromptText(LanguageManager.getInstance().getTranslation("holidaysBeginning"));
        holidaysEnding.setPromptText(LanguageManager.getInstance().getTranslation("holidaysEnding"));
        creditSaveResult.setText(LanguageManager.getInstance().getTranslation("creditSaveResult"));
        creditViewResult.setText(LanguageManager.getInstance().getTranslation("creditViewResult"));
        PaymentOption1.setText(LanguageManager.getInstance().getTranslation("PaymentOption1"));
        PaymentOption2.setText(LanguageManager.getInstance().getTranslation("PaymentOption2"));
        PaymentOption3.setText(LanguageManager.getInstance().getTranslation("PaymentOption3"));
        PaymentOption4.setText(LanguageManager.getInstance().getTranslation("PaymentOption4"));
    }
    @FXML
    void initialize() {
        StartMenu.datePickers.add(contractBeginning);
        StartMenu.datePickers.add(contractEnding);
        StartMenu.datePickers.add(holidaysBeginning);
        StartMenu.datePickers.add(holidaysEnding);
        for (DatePicker datePicker : StartMenu.datePickers) {
            ErrorChecker.WrongDateListener(datePicker);
        }
        DatePickerRestrictions.setDatePickerRestrictionsHolidays(contractBeginning, contractEnding, holidaysBeginning, holidaysEnding);
        userSelectedCurrency = "USD";

        updateText();
        LanguageManager.getInstance().languageProperty().addListener((observable, oldValue, newValue) -> {
            updateText();
        });
        holidaysBeginning.setVisible(false);
        holidaysBeginning.setDisable(true);
        holidaysEnding.setVisible(false);
        holidaysEnding.setDisable(true);
        checkPaymentHolidays.setOnAction(event ->{
            if(checkPaymentHolidays.isSelected()){
                holidaysBeginning.setVisible(true);
                holidaysEnding.setVisible(true);
                holidaysBeginning.setDisable(false);
                holidaysEnding.setDisable(false);
            }
            else{
                holidaysBeginning.setVisible(false);
                holidaysEnding.setVisible(false);
                holidaysBeginning.setDisable(true);
                holidaysEnding.setDisable(true);
            }
        });
        PaymentOption1.setOnAction(event -> paymentOption.setText(PaymentOption1.getText()));
        PaymentOption2.setOnAction(event -> paymentOption.setText(PaymentOption2.getText()));
        PaymentOption3.setOnAction(event -> paymentOption.setText(PaymentOption3.getText()));
        PaymentOption4.setOnAction(event -> paymentOption.setText(PaymentOption4.getText()));

        closeWindow.setOnAction(event -> closeCurrentWindow(closeWindow.getScene()));
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
        exitApp.setOnAction(event ->{
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
                userSelectedCurrency = dialog.getSelectedItem();
            }
        });
        depositButtonMenu.setOnAction(event ->{
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
        creditButtonMenu.setOnAction(event ->{
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

        creditSaveResult.setOnAction(event -> {
            if (ErrorChecker.areFieldsValid(loanInput, annualPercentInput, paymentOption)) {
                float creditLoan = Float.parseFloat(loanInput.getText());
                float creditAnnualPercent = Float.parseFloat(annualPercentInput.getText());
                String creditCurrency = userSelectedCurrency;
                int paymentOptionSelected = -1000;
                switch (paymentOption.getText()) {
                    case "Monthly" -> paymentOptionSelected = 1;
                    case "Quarterly" -> paymentOptionSelected = 2;
                    case "Yearly" -> paymentOptionSelected = 3;
                    case "At the end" -> paymentOptionSelected = 4;
                }
                LocalDate contractStartDate = contractBeginning.getValue();
                LocalDate contractEndDate = contractEnding.getValue();
                if (checkPaymentHolidays.isSelected()){
                    LocalDate holidaysStartDate = holidaysBeginning.getValue();
                    LocalDate holidaysEndDate = holidaysEnding.getValue();
                    Credit credit = new CreditWithHolidays(creditLoan, creditCurrency, creditAnnualPercent, contractStartDate, contractEndDate, paymentOptionSelected, holidaysStartDate, holidaysEndDate);
                    credit.save();
                }
                else {
                    Credit credit = new CreditWithoutHolidays(creditLoan, creditCurrency, creditAnnualPercent, contractStartDate, contractEndDate, paymentOptionSelected);
                    credit.save();
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
        creditLabel.setText(LanguageManager.getInstance().getTranslation("CreditButton"));
        loanInput.setPromptText(LanguageManager.getInstance().getTranslation("loanInput"));
        annualPercentInput.setPromptText(LanguageManager.getInstance().getTranslation("annualPercentInput"));
        contractBeginning.setPromptText(LanguageManager.getInstance().getTranslation("contractBeginning"));
        contractEnding.setPromptText(LanguageManager.getInstance().getTranslation("contractEnding"));
        paymentOption.setText(LanguageManager.getInstance().getTranslation("paymentOption"));
        checkPaymentHolidays.setText(LanguageManager.getInstance().getTranslation("checkPaymentHolidays"));
        holidaysBeginning.setPromptText(LanguageManager.getInstance().getTranslation("holidaysBeginning"));
        holidaysEnding.setPromptText(LanguageManager.getInstance().getTranslation("holidaysEnding"));
        creditSaveResult.setText(LanguageManager.getInstance().getTranslation("creditSaveResult"));
        creditViewResult.setText(LanguageManager.getInstance().getTranslation("creditViewResult"));
        PaymentOption1.setText(LanguageManager.getInstance().getTranslation("PaymentOption1"));
        PaymentOption2.setText(LanguageManager.getInstance().getTranslation("PaymentOption2"));
        PaymentOption3.setText(LanguageManager.getInstance().getTranslation("PaymentOption3"));
        PaymentOption4.setText(LanguageManager.getInstance().getTranslation("PaymentOption4"));
    }

}





