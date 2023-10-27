package com.netrunners.financialcalculator.MenuControllers;

import com.netrunners.financialcalculator.StartMenu;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import static com.netrunners.financialcalculator.ErrorHandling.ErrorChecker.updateDatePickerStyle;
import static com.netrunners.financialcalculator.MenuControllers.closeWindow.closeCurrentWindow;

public class ResultTableController {

    @FXML
    private Menu aboutButton;

    @FXML
    private MenuItem aboutUs;

    @FXML
    private MenuItem creditButtonMenu;

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
    private Label financialCalculatorLabel;

    @FXML
    private TableColumn<?, ?> investmentloanColumn;

    @FXML
    private MenuItem languageButton;

    @FXML
    private MenuItem lightTheme;

    @FXML
    private Menu newButton;

    @FXML
    private TableColumn<?, ?> numersColumn;

    @FXML
    private MenuItem openFileButton;

    @FXML
    private TableColumn<?, ?> periodProfitLoan;

    @FXML
    private TableView<?> resultTable;

    @FXML
    private MenuItem saveAsButton;

    @FXML
    private MenuItem saveButton;

    @FXML
    private Menu settingsButton;

    @FXML
    private Menu themeButton;

    @FXML
    private TableColumn<?, ?> total;

    @FXML
    private Menu viewButton;

    @FXML
    void initialize(){
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
        exitApp.setOnAction(event -> {
            for (Scene scene : StartMenu.openScenes) {
                closeCurrentWindow(scene);
            }
            System.exit(0);
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
    }
}
