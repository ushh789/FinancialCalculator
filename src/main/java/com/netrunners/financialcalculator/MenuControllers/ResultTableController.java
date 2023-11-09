package com.netrunners.financialcalculator.MenuControllers;


import com.netrunners.financialcalculator.LogicalInstrumnts.TimeFunctions.DateTimeFunctions;
import com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation.Deposit.*;
import com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation.Credit.*;
import com.netrunners.financialcalculator.VisualInstruments.MenuActions.AboutUsAlert;
import com.netrunners.financialcalculator.VisualInstruments.MenuActions.ExitApp;
import com.netrunners.financialcalculator.VisualInstruments.MenuActions.ThemeSelector;
import com.netrunners.financialcalculator.VisualInstruments.WindowsOpener;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


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
    private TableColumn<Object[], Integer> periodColumn;

    @FXML
    private MenuItem languageButton;

    @FXML
    private MenuItem lightTheme;

    @FXML
    private Menu newButton;

    @FXML
    private TableColumn<Object[], Float> investmentloanColumn;

    @FXML
    private MenuItem openFileButton;

    @FXML
    private TableColumn<Object[], Float> periodProfitLoanColumn;

    @FXML
    private TableView<Object[]> resultTable;

    @FXML
    private MenuItem saveAsButton;

    @FXML
    private MenuItem saveButton;

    @FXML
    private Menu settingsButton;

    @FXML
    private Menu themeButton;

    @FXML
    private TableColumn<Object[], Float> totalColumn;

    @FXML
    private Menu viewButton;

    @FXML
    void initialize() {
        darkTheme.setOnAction(event -> ThemeSelector.setDarkTheme());
        lightTheme.setOnAction(event -> ThemeSelector.setLightTheme());
        aboutUs.setOnAction(event -> AboutUsAlert.showAboutUs());
        exitApp.setOnAction(event -> ExitApp.exitApp());
        depositButtonMenu.setOnAction(event -> WindowsOpener.depositOpener());
        creditButtonMenu.setOnAction(event -> WindowsOpener.creditOpener());
    }

    public void updateTable(Deposit deposit) {
        fillColumns(deposit);
        System.out.println(deposit);
    }

    public void updateTable(Credit credit) {
        fillColumns(credit);
        System.out.println(credit);
    }


    private void fillColumns(Credit credit) {
        LocalDate tempDate = credit.getStartDate();
        float tempLoan = credit.getLoan();
        int numbersColumnFlag = 0;

        periodColumn.setCellValueFactory(cellData -> cellData.getValue()[0] == null ? null : new SimpleObjectProperty<>((Integer) cellData.getValue()[0]));
        investmentloanColumn.setCellValueFactory(cellData -> cellData.getValue()[1] == null ? null : new SimpleObjectProperty<>((Float) cellData.getValue()[1]));
        periodProfitLoanColumn.setCellValueFactory(cellData -> cellData.getValue()[2] == null ? null : new SimpleObjectProperty<>((Float) cellData.getValue()[2]));
        totalColumn.setCellValueFactory(cellData -> cellData.getValue()[3] == null ? null : new SimpleObjectProperty<>((Float) cellData.getValue()[3]));
        List<Object[]> data = new ArrayList<>();
        resultTable.getColumns().addAll(periodColumn, investmentloanColumn, periodProfitLoanColumn, totalColumn);

        while (!tempDate.equals(credit.getEndDate())) {
            float periodLoan = 0;
            int daysToNextPeriod = DateTimeFunctions.countDaysToNextPeriod(credit, tempDate);
            if (numbersColumnFlag == 0) {
                daysToNextPeriod = 0;
                //set names for columns and set the zero row
                periodColumn.setText(credit.getNameOfPaymentType());
                investmentloanColumn.setText("Loan");
                periodProfitLoanColumn.setText("Period loan");
                totalColumn.setText("Total loan");

            } else {
                if (credit instanceof CreditWithHolidays) {
                    //code for holidays
                } else {
                    for (int i = 0; i <= daysToNextPeriod; i++) {
                        periodLoan += credit.countLoan();
                    }
                }
            }

            float totalLoan = tempLoan - periodLoan;
            tempLoan -= periodLoan;
            tempLoan = Math.round(tempLoan);
            periodLoan = Math.round(periodLoan);
            totalLoan = Math.round(totalLoan);

            data.add(new Object[]{numbersColumnFlag, tempLoan, periodLoan, totalLoan});

            tempDate = tempDate.plusDays(daysToNextPeriod);
            numbersColumnFlag++;
        }
        ObservableList<Object[]> observableData = FXCollections.observableArrayList(data);
        resultTable.setItems(observableData);
    }

    private void fillColumns(Deposit deposit) {
        resultTable.getColumns().clear();
        LocalDate tempDate = deposit.getStartDate();
        float tempInvestment = deposit.getInvestment();
        float totalInvestment = tempInvestment;
        int numbersColumnFlag = 0;

        periodColumn.setCellValueFactory(cellData -> cellData.getValue()[0] == null ? null : new SimpleObjectProperty<>((Integer) cellData.getValue()[0]));
        investmentloanColumn.setCellValueFactory(cellData -> cellData.getValue()[1] == null ? null : new SimpleObjectProperty<>((Float) cellData.getValue()[1]));
        periodProfitLoanColumn.setCellValueFactory(cellData -> cellData.getValue()[2] == null ? null : new SimpleObjectProperty<>((Float) cellData.getValue()[2]));
        totalColumn.setCellValueFactory(cellData -> cellData.getValue()[3] == null ? null : new SimpleObjectProperty<>((Float) cellData.getValue()[3]));
        List<Object[]> data = new ArrayList<>();
        resultTable.getColumns().addAll(periodColumn, investmentloanColumn, periodProfitLoanColumn, totalColumn);

        LocalDate endOfContract;
        if (deposit.isEarlyWithdrawal()) {
            endOfContract = deposit.getEarlyWithdrawalDate();
        } else {
            endOfContract = deposit.getEndDate();
        }
        boolean capitalize = deposit instanceof CapitalisedDeposit;
        while (tempDate.isBefore(endOfContract)) {
            float periodProfit = 0;
            int daysToNextPeriod = DateTimeFunctions.countDaysToNextPeriod(deposit, tempDate, endOfContract);
            if (numbersColumnFlag == 0) {
                daysToNextPeriod = 0;
                periodColumn.setText(deposit.getNameOfWithdrawalType());
                investmentloanColumn.setText("Investment");
                periodProfitLoanColumn.setText("Period profit");
                totalColumn.setText("Total profit");
            } else {
                if (capitalize) {
                    for (int i = 0; i < daysToNextPeriod; i++) {
                        periodProfit += deposit.countProfit();
                    }
                } else {
                    for (int i = 0; i < daysToNextPeriod; i++) {
                        periodProfit += deposit.countProfit();
                    }
                }
            }
            totalInvestment += periodProfit;
            data.add(new Object[]{numbersColumnFlag, tempInvestment, periodProfit, totalInvestment});
            if (capitalize) {
                tempInvestment = totalInvestment;
                deposit.setInvestment(tempInvestment);
            }
            tempDate = tempDate.plusDays(daysToNextPeriod);
            numbersColumnFlag++;
        }
        ObservableList<Object[]> observableData = FXCollections.observableArrayList(data);
        resultTable.setItems(observableData);
    }
}



