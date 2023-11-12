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
    private TableColumn<Object[], String> investmentloanColumn;

    @FXML
    private MenuItem openFileButton;

    @FXML
    private TableColumn<Object[], String> periodProfitLoanColumn;

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
    private TableColumn<Object[], String> totalColumn;

    @FXML
    private TableColumn<Object[], String> periodPercentsColumn;

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
    }

    public void updateTable(Credit credit) {
        fillColumns(credit);
    }

    private void fillColumns(Credit credit) {
        resultTable.getColumns().clear();
        periodPercentsColumn.setVisible(true);
        periodColumn.setCellValueFactory(cellData -> cellData.getValue()[0] == null ? null : new SimpleObjectProperty<>((Integer) cellData.getValue()[0]));
        investmentloanColumn.setCellValueFactory(cellData -> cellData.getValue()[1] == null ? null : new SimpleObjectProperty<>((String) cellData.getValue()[1]));
        periodProfitLoanColumn.setCellValueFactory(cellData -> cellData.getValue()[2] == null ? null : new SimpleObjectProperty<>((String) cellData.getValue()[2]));
        totalColumn.setCellValueFactory(cellData -> cellData.getValue()[3] == null ? null : new SimpleObjectProperty<>((String) cellData.getValue()[3]));
        periodPercentsColumn.setCellValueFactory(cellData -> cellData.getValue()[4] == null ? null : new SimpleObjectProperty<>((String) cellData.getValue()[4]));
        List<Object[]> data = new ArrayList<>();
        resultTable.getColumns().addAll(periodColumn, investmentloanColumn, periodProfitLoanColumn, totalColumn, periodPercentsColumn);
        if (credit instanceof CreditWithoutHolidays) {
            data = countCreditWithoutHolidaysData(credit);
        } else if (credit instanceof CreditWithHolidays) {
            data = countCreditWithHolidaysData((CreditWithHolidays) credit);
        }
        ObservableList<Object[]> observableData = FXCollections.observableArrayList(data);
        resultTable.setItems(observableData);
    }

    private List<Object[]> countCreditWithHolidaysData(CreditWithHolidays credit) {
        LocalDate tempDate = credit.getStartDate();
        List<Object[]> data = new ArrayList<>();
        int numbersColumnFlag = 0;
        float dailyBodyPart = credit.countCreditBodyPerDay();
        float tempLoan = 0;
        while (!tempDate.equals(credit.getEndDate())) {
            tempLoan = credit.getLoan();
            float totalLoan = tempLoan;
            float creditBody = 0;
            float periodPercents = 0;
            int daysToNextPeriod = DateTimeFunctions.countDaysToNextPeriod(credit, tempDate);
            if (numbersColumnFlag == 0) {
                periodColumn.setText(credit.getNameOfPaymentType());
                investmentloanColumn.setText("Loan");
                periodProfitLoanColumn.setText("Period loan");
                totalColumn.setText("Total loan");
            } else {
                for (int i = 0; i < daysToNextPeriod; i++) {
                    if (!DateTimeFunctions.isDateBetweenDates(tempDate, credit.getHolidaysStart(), credit.getHolidaysEnd())) {
                        creditBody += dailyBodyPart;
                    }
                    periodPercents += credit.countLoan();
                    tempDate = tempDate.plusDays(1);
                }
                if (tempDate.equals(credit.getEndDate())){
                    creditBody = tempLoan;
                }
                totalLoan -= creditBody;
                credit.setLoan(totalLoan);
            }
            data.add(new Object[]{
                    numbersColumnFlag,
                    String.format("%.2f", tempLoan),
                    String.format("%.2f", creditBody),
                    String.format("%.2f", totalLoan),
                    String.format("%.2f", periodPercents)
            });
            numbersColumnFlag++;
        }
        return data;
    }

    private List<Object[]> countCreditWithoutHolidaysData(Credit credit) {
        LocalDate tempDate = credit.getStartDate();
        List<Object[]> data = new ArrayList<>();
        int numbersColumnFlag = 0;
        float dailyBodyPart = credit.countCreditBodyPerDay();
        float tempLoan;
        while (tempDate.isBefore(credit.getEndDate())) {
            tempLoan = credit.getLoan();
            float totalLoan = tempLoan;
            float creditBody = 0;
            float periodPercents = 0;
            int daysToNextPeriod = DateTimeFunctions.countDaysToNextPeriod(credit, tempDate);
            if (numbersColumnFlag == 0) {
                periodColumn.setText(credit.getNameOfPaymentType());
                investmentloanColumn.setText("Loan");
                periodProfitLoanColumn.setText("Period loan");
                totalColumn.setText("Total loan");
            } else {
                for (int i = 0; i < daysToNextPeriod; i++) {
                    periodPercents += credit.countLoan();
                    creditBody += dailyBodyPart;
                    tempDate = tempDate.plusDays(1);
                }
                if (tempDate.equals(credit.getEndDate())){
                    creditBody = tempLoan;
                }
                totalLoan -= creditBody;
                credit.setLoan(totalLoan);
            }

            data.add(new Object[]{
                    numbersColumnFlag,
                    String.format("%.2f", tempLoan),
                    String.format("%.2f", creditBody),
                    String.format("%.2f", totalLoan),
                    String.format("%.2f", periodPercents)
            });
            numbersColumnFlag++;
        }
        return data;
    }


    private void fillColumns(Deposit deposit) {
        resultTable.getColumns().clear();
        periodPercentsColumn.setVisible(false);
        periodColumn.setCellValueFactory(cellData -> cellData.getValue()[0] == null ? null : new SimpleObjectProperty<>((Integer) cellData.getValue()[0]));
        investmentloanColumn.setCellValueFactory(cellData -> cellData.getValue()[1] == null ? null : new SimpleObjectProperty<>((String) cellData.getValue()[1]));
        periodProfitLoanColumn.setCellValueFactory(cellData -> cellData.getValue()[2] == null ? null : new SimpleObjectProperty<>((String) cellData.getValue()[2]));
        totalColumn.setCellValueFactory(cellData -> cellData.getValue()[3] == null ? null : new SimpleObjectProperty<>((String) cellData.getValue()[3]));
        List<Object[]> data = countDepositData(deposit);
        resultTable.getColumns().addAll(periodColumn, investmentloanColumn, periodProfitLoanColumn, totalColumn);
        ObservableList<Object[]> observableData = FXCollections.observableArrayList(data);
        resultTable.setItems(observableData);
    }
    private List<Object[]> countDepositData(Deposit deposit){
        List<Object[]> data = new ArrayList<>();
        LocalDate tempDate = deposit.getStartDate();
        float tempInvestment = deposit.getInvestment();
        float totalInvestment = tempInvestment;
        int numbersColumnFlag = 0;
        LocalDate endOfContract;
        if (deposit.isEarlyWithdrawal()) {
            endOfContract = deposit.getEarlyWithdrawalDate();
        } else {
            endOfContract = deposit.getEndDate();
        }
        boolean capitalize = deposit instanceof CapitalisedDeposit;
        while (!tempDate.equals(endOfContract)) {
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
            data.add(new Object[]{numbersColumnFlag,
                    String.format("%.2f", tempInvestment),
                    String.format("%.2f", periodProfit),
                    String.format("%.2f", totalInvestment)});
            if (capitalize) {
                tempInvestment = totalInvestment;
                deposit.setInvestment(tempInvestment);
            }
            tempDate = tempDate.plusDays(daysToNextPeriod);
            numbersColumnFlag++;
        }
        return data;
    }

}


