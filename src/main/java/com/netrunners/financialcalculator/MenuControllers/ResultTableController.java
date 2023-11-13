package com.netrunners.financialcalculator.MenuControllers;

import com.netrunners.financialcalculator.LogicalInstrumnts.FileInstruments.LogHelper;
import com.netrunners.financialcalculator.LogicalInstrumnts.TimeFunctions.DateTimeFunctions;
import com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation.Deposit.*;
import com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation.Credit.*;
import com.netrunners.financialcalculator.VisualInstruments.MenuActions.AboutUsAlert;
import com.netrunners.financialcalculator.VisualInstruments.MenuActions.ExitApp;
import com.netrunners.financialcalculator.VisualInstruments.MenuActions.LanguageManager;
import com.netrunners.financialcalculator.VisualInstruments.MenuActions.ThemeSelector;
import com.netrunners.financialcalculator.VisualInstruments.WindowsOpener;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;



public class ResultTableController {

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
    private TableColumn<Object[], Integer> periodColumn;

    @FXML
    private MenuItem languageButton;

    @FXML
    private MenuItem lightTheme;

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
    private TableColumn<Object[], String> totalColumn;

    @FXML
    private TableColumn<Object[], String> periodPercentsColumn;

    @FXML
    private Button saveFileButton;

    @FXML
    private Label financialCalculatorLabel;

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
        financialCalculatorLabel.setText(LanguageManager.getInstance().getTranslation("resultTablelabel"));
        periodProfitLoanColumn.setText(LanguageManager.getInstance().getTranslation("profitColumn"));
        totalColumn.setText(LanguageManager.getInstance().getTranslation("totalColumn"));
    }
    @FXML
    void initialize() {
        openFileButton.setDisable(true);
        updateText();
        LanguageManager.getInstance().languageProperty().addListener((observable, oldValue, newValue) -> updateText());
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
        List<Object[]> finalData = data;
        saveFileButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Data");
            File initialDirectory = new File("saves/");
            fileChooser.setInitialDirectory(initialDirectory);
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
            File file = fileChooser.showSaveDialog(null);
            if (file != null) {
                writeDataToCSV(finalData, credit, file);
            }
        });
        saveButton.setOnAction(event -> {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
            String formattedNow = now.format(formatter);
            File file = new File("saves/Credit_result_" + formattedNow + ".csv");
            writeDataToCSV(finalData, credit, file);
        });
        saveAsButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Data");
            File initialDirectory = new File("saves/");
            fileChooser.setInitialDirectory(initialDirectory);
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
            File file = fileChooser.showSaveDialog(null);
            if (file != null) {
                writeDataToCSV(finalData, credit, file);
            }
        });
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
        saveFileButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Data");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
            File file = fileChooser.showSaveDialog(null);
            if (file != null) {
                writeDataToCSV(data, deposit, file);
            }
        });
        saveButton.setOnAction(event -> {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
            String formattedNow = now.format(formatter);
            File file = new File("saves/Deposit_result_" + formattedNow + ".csv");
            writeDataToCSV(data, deposit, file);
        });
        saveAsButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Data");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
            File file = fileChooser.showSaveDialog(null);
            if (file != null) {
                writeDataToCSV(data, deposit, file);
            }
        });
    }

    private List<Object[]> countDepositData(Deposit deposit) {
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
                    String.format("%.2f", tempInvestment) + deposit.getCurrency(),
                    String.format("%.2f", periodProfit) + deposit.getCurrency(),
                    String.format("%.2f", totalInvestment) + deposit.getCurrency()
            });
            if (capitalize) {
                tempInvestment = totalInvestment;
                deposit.setInvestment(tempInvestment);
            }
            tempDate = tempDate.plusDays(daysToNextPeriod);
            numbersColumnFlag++;
        }
        return data;
    }

    private List<Object[]> countCreditWithHolidaysData(CreditWithHolidays credit) {
        LocalDate tempDate = credit.getStartDate();
        List<Object[]> data = new ArrayList<>();
        int numbersColumnFlag = 0;
        float dailyBodyPart = credit.countCreditBodyPerDay();
        float tempLoan;
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
                if (tempDate.equals(credit.getEndDate())) {
                    creditBody = tempLoan;
                }
                totalLoan -= creditBody;
                credit.setLoan(totalLoan);
            }
            data.add(new Object[]{numbersColumnFlag,
                    String.format("%.2f", tempLoan) + credit.getCurrency(),
                    String.format("%.2f", creditBody) + credit.getCurrency(),
                    String.format("%.2f", totalLoan) + credit.getCurrency(),
                    String.format("%.2f", periodPercents) + credit.getCurrency()
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
                if (tempDate.equals(credit.getEndDate())) {
                    creditBody = tempLoan;
                }
                totalLoan -= creditBody;
                credit.setLoan(totalLoan);
            }

            data.add(new Object[]{numbersColumnFlag,
                    String.format("%.2f", tempLoan) + credit.getCurrency(),
                    String.format("%.2f", creditBody) + credit.getCurrency(),
                    String.format("%.2f", totalLoan) + credit.getCurrency(),
                    String.format("%.2f", periodPercents) + credit.getCurrency()
            });
            numbersColumnFlag++;
        }
        return data;
    }

    private void writeDataToCSV(List<Object[]> data, Deposit deposit, File file) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            writer.println(deposit.getNameOfWithdrawalType() + ";" + investmentloanColumn.getText() + ";" + periodProfitLoanColumn.getText() + ";" + totalColumn.getText());

            for (Object[] row : data) {
                writer.println(row[0] + ";" + row[1] + ";" + row[2] + ";" + row[3]);
                writer.flush();
            }
            writer.println("Annual percent of deposit: " + deposit.getAnnualPercent());
            writer.println("Currency: " + deposit.getCurrency());
            writer.println("Start date: " + deposit.getStartDate());
            writer.println("End date: " + deposit.getEndDate());
            if (deposit.isEarlyWithdrawal()) {
                writer.println("Early withdrawal date: " + deposit.getEarlyWithdrawalDate());
            }
        } catch (IOException e) {
            LogHelper.log(Level.SEVERE, "Error while writing Deposit to CSV", e);
        }
    }

    private void writeDataToCSV(List<Object[]> data, Credit credit, File file) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            writer.println(credit.getNameOfPaymentType() + ";" + investmentloanColumn.getText() + ";" + periodProfitLoanColumn.getText() + ";" + totalColumn.getText() + ";" + periodPercentsColumn.getText());

            for (Object[] row : data) {
                writer.println(row[0] + ";" + row[1] + ";" + row[2] + ";" + row[3] + ";" + row[4]);
                writer.flush();
            }
            writer.println("Annual percent of credit: " + credit.getAnnualPercent());
            writer.println("Currency: " + credit.getCurrency());
            writer.println("Start date: " + credit.getStartDate());
            writer.println("End date: " + credit.getEndDate());
            if (credit instanceof CreditWithHolidays) {
                writer.println("Holidays start date: " + ((CreditWithHolidays) credit).getHolidaysStart());
                writer.println("Holidays end date: " + ((CreditWithHolidays) credit).getHolidaysEnd());
            }
        } catch (IOException e) {
            LogHelper.log(Level.SEVERE, "Error while writing Credit to CSV", e);
        }
    }

}


