package com.netrunners.financialcalculator.controllers;

import static com.netrunners.financialcalculator.controllers.closeWindow.closeCurrentWindow;

import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.time.format.DateTimeFormatter;

import com.netrunners.financialcalculator.StartMenu;
import javafx.fxml.FXML;
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
    private Button closeWindow;

    @FXML
    private MenuItem WithdrawalOption1;

    @FXML
    private MenuItem WithdrawalOption2;

    @FXML
    private MenuItem WithdrawalOption3;

    @FXML
    private MenuItem WithdrawalOption4;

    @FXML
    private TextField depositAnnualPercentInput;

    @FXML
    private CheckBox depositCapitalizationCheck;

    @FXML
    private DatePicker depositContractBeginning;

    @FXML
    private DatePicker depositContractEnding;

    @FXML
    private CheckBox depositEarlyWithdrawalCheck;

    @FXML
    private Button depositSaveResult;

    @FXML
    private Button depositViewResult;

    @FXML
    private DatePicker depositWithdrawalDate;

    @FXML
    private MenuButton depositWithdrawalOption;

    @FXML
    private TextField investInput;

    @FXML
    private MenuBar menuBar;

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
    void initialize(){
        depositWithdrawalDate.setVisible(false);
        depositWithdrawalDate.setDisable(true);
        closeWindow.setOnAction(event -> closeCurrentWindow(closeWindow.getScene()));
        depositEarlyWithdrawalCheck.setOnAction(event -> {
            if(depositEarlyWithdrawalCheck.isSelected()){
                depositWithdrawalDate.setVisible(true);
                depositWithdrawalDate.setDisable(false);
            }else{
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
        });

        lightTheme.setOnAction(event -> {
            StartMenu.currentTheme = "file:src/main/resources/com/netrunners/financialcalculator/assets/lightTheme.css";
            for (Scene scene : StartMenu.openScenes) {
                scene.getStylesheets().clear();
                scene.getStylesheets().add(StartMenu.currentTheme);
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
            }
        });
        depositContractBeginning.setOnAction(event ->{
            LocalDate dateBeginning = depositContractBeginning.getValue();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            String formattedDateBeginning = dateBeginning.format(formatter);
            System.out.printf("Date of Beginning: %s%n", formattedDateBeginning);

            LocalDate dateEnding = depositContractEnding.getValue();
            if (dateEnding != null) {
                long daysBetween = ChronoUnit.DAYS.between(dateBeginning, dateEnding);
                System.out.printf("Days between: %d%n", daysBetween);
            }
        });

        depositContractEnding.setOnAction(event ->{
            LocalDate dateEnding = depositContractEnding.getValue();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            String formattedDateEnding = dateEnding.format(formatter);
            System.out.printf("Date of Ending: %s%n", formattedDateEnding);

            LocalDate dateBeginning = depositContractBeginning.getValue();
            if (dateBeginning != null) {
                long daysBetween = ChronoUnit.DAYS.between(dateBeginning, dateEnding);
                System.out.printf("Days between: %d%n", daysBetween);
            }
        });
    }

}
