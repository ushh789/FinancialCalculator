package com.netrunners.financialcalculator.controller;

import com.netrunners.financialcalculator.logic.files.LogHelper;
import com.netrunners.financialcalculator.logic.time.DateTimeUtils;
import com.netrunners.financialcalculator.logic.entity.deposit.Deposit;
import com.netrunners.financialcalculator.logic.entity.deposit.CapitalisedDeposit;
import com.netrunners.financialcalculator.logic.entity.credit.Credit;
import com.netrunners.financialcalculator.logic.entity.credit.CreditWithHolidays;
import com.netrunners.financialcalculator.logic.entity.credit.CreditWithoutHolidays;
import com.netrunners.financialcalculator.logic.entity.ResultTableSender;
import com.netrunners.financialcalculator.ui.FilesActions;
import com.netrunners.financialcalculator.ui.LanguageManager;
import com.netrunners.financialcalculator.ui.CurrencyManager;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Level;

import static com.netrunners.financialcalculator.constants.FileConstants.*;
import static com.netrunners.financialcalculator.constants.StringConstants.FORMATTED_TIME_NOW;


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
    private Menu fileButton;

    @FXML
    private Menu aboutButton;

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
    private TableColumn<Object[], String> investmentLoanColumn;

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
    private Menu viewButton;

    @FXML
    private Menu settingsButton;

    @FXML
    private Menu themeButton;

    @FXML
    private TableColumn<Object[], String> totalColumn;

    @FXML
    private TableColumn<Object[], String> periodPercentsColumn;

    @FXML
    private Button exportButton;

    @FXML
    private Menu newButton;
    @FXML
    private Label financialCalculatorLabel;
    @FXML
    private Button convertButton;

    float loan;
    float dailyPart;
    private final LanguageManager languageManager = LanguageManager.getInstance();
    List<Integer> daysToNextPeriodWithHolidays = new ArrayList<>();
    List<Integer> daysToNextPeriod = new ArrayList<>();
    private String userSelectedCurrency;
    float tempInvest;

    @FXML
    void initialize() {
        openFileButton.setDisable(true);
        currency.setDisable(true);

        // Menu items initialization
        ControllerUtils.initializeMenuItems(darkTheme, lightTheme, aboutUs, exitApp, depositButtonMenu, creditButtonMenu, languageButton, currency);
        exportButton.setOnAction(event -> {

        });
        convertButton.setOnAction(event -> CurrencyManager.updateResultTable(userSelectedCurrency, loan, investmentLoanColumn, periodProfitLoanColumn, totalColumn, periodPercentsColumn, resultTable));


        // Menu text bindings
        ControllerUtils.initializeTextBindings(settingsButton, aboutButton, viewButton, themeButton, newButton, fileButton, depositButtonMenu, creditButtonMenu, languageButton, darkTheme, lightTheme, aboutUs, exitApp, currency, openFileButton, saveAsButton, saveButton);
        ControllerUtils.provideTranslation(financialCalculatorLabel, "ResultTableLabel");
        ControllerUtils.provideTranslation(totalColumn, "totalColumn");
        ControllerUtils.provideTranslation(periodPercentsColumn, "PeriodPercents");
        ControllerUtils.provideTranslation(exportButton, "Export");
        ControllerUtils.provideTranslation(convertButton, "ConvertTitle");
    }

    public void updateTable(ResultTableSender financialOperation) {
        if (financialOperation instanceof Credit) {
            fillColumns((Credit) financialOperation);
        } else if (financialOperation instanceof Deposit) {
            fillColumns((Deposit) financialOperation);
        }
    }

    private void fillColumns(Credit credit) {
        userSelectedCurrency = credit.getCurrency();

        // Table columns initialization
        resultTable.getColumns().clear();
        periodPercentsColumn.setVisible(true);
        initializeTableColumns(periodColumn, investmentLoanColumn, periodProfitLoanColumn, totalColumn);
        periodPercentsColumn.setCellValueFactory(cellData -> cellData.getValue()[4] == null ? null : new SimpleObjectProperty<>((String) cellData.getValue()[4]));

        List<Object[]> data = new ArrayList<>();
        
        resultTable.getColumns().addAll(periodColumn, investmentLoanColumn, periodProfitLoanColumn, totalColumn, periodPercentsColumn);
        
        if (credit instanceof CreditWithoutHolidays) {
            data = countCreditWithoutHolidaysData(credit);
        } else if (credit instanceof CreditWithHolidays) {
            data = countCreditWithHolidaysData((CreditWithHolidays) credit);
        }
        
        ObservableList<Object[]> observableData = FXCollections.observableArrayList(data);
        resultTable.setItems(observableData);

        List<Object[]> finalData = data;
        exportButton.setOnAction(event -> {
            File selectedFile = FilesActions.showFileChooserSaver("Export", saveFileTypes(), saveExtensions());

            if (selectedFile != null && selectedFile.getName().endsWith(".xlsx")) {
                writeDataToExcel(finalData, credit, selectedFile);
            } else {
                writeDataToCSV(finalData, credit, selectedFile);
            }
        });
        
        saveButton.setOnAction(event -> {
            File file = new File(SAVES_PATH + "/Credit_result_" + FORMATTED_TIME_NOW + ".csv");
            writeDataToCSV(finalData, credit, file);
            FilesActions.showSavingAlert();
        });
        
        saveAsButton.setOnAction(event -> {
            File selectedFile = FilesActions.showFileChooserSaver("saveAsButton", saveFileTypes(), saveExtensions());

            if (selectedFile != null && selectedFile.getName().endsWith(".xlsx")) {
                writeDataToExcel(finalData, credit, selectedFile);
            } else {
                writeDataToCSV(finalData, credit, selectedFile);
            }
        });
    }

    private void fillColumns(Deposit deposit) {
        userSelectedCurrency = deposit.getCurrency();

        // Table columns initialization
        resultTable.getColumns().clear();
        periodPercentsColumn.setVisible(false);
        initializeTableColumns(periodColumn, investmentLoanColumn, periodProfitLoanColumn, totalColumn);

        List<Object[]> data = countDepositData(deposit);
        resultTable.getColumns().addAll(periodColumn, investmentLoanColumn, periodProfitLoanColumn, totalColumn);
        ObservableList<Object[]> observableData = FXCollections.observableArrayList(data);
        resultTable.setItems(observableData);

        exportButton.setOnAction(event -> {
            File selectedFile = FilesActions.showFileChooserSaver("Export", saveFileTypes(), saveExtensions());

            if (selectedFile != null && selectedFile.getName().endsWith(".xlsx")) {
                writeDataToExcel(data, deposit, selectedFile);
            } else {
                writeDataToCSV(data, deposit, selectedFile);
            }
        });
        saveButton.setOnAction(event -> {
            File file = new File(SAVES_PATH + "/Deposit_result_" + FORMATTED_TIME_NOW + ".csv");
            writeDataToCSV(data, deposit, file);
            FilesActions.showSavingAlert();
        });
        saveAsButton.setOnAction(event -> {
            File selectedFile = FilesActions.showFileChooserSaver("saveAsButton", saveFileTypes(), saveExtensions());

            if (selectedFile != null && selectedFile.getName().endsWith(".xlsx")) {
                writeDataToExcel(data, deposit, selectedFile);
            } else {
                writeDataToCSV(data, deposit, selectedFile);
            }
        });
    }


    private List<Object[]> countDepositData(Deposit deposit) {
        daysToNextPeriod.add(0);
        List<Object[]> data = new ArrayList<>();
        LocalDate depositStartDate = deposit.getStartDate();
        float depositInvestment = deposit.getInvestment();
        tempInvest = depositInvestment;
        float totalInvestment = depositInvestment;
        int numbersColumnFlag = 0;
        boolean capitalize = deposit instanceof CapitalisedDeposit;
        
        LocalDate endOfContract;
        if (deposit.isEarlyWithdrawal()) {
            endOfContract = deposit.getEarlyWithdrawalDate();
        } else {
            endOfContract = deposit.getEndDate();
        }
        
        while (!depositStartDate.equals(endOfContract)) {
            float periodProfit = 0;
            int daysToNextPeriodCount = DateTimeUtils.countDaysToNextPeriod(deposit, depositStartDate, endOfContract);

            if (numbersColumnFlag == 0) {
                ControllerUtils.provideTranslation(periodColumn, deposit.getNameOfWithdrawalType());
                ControllerUtils.provideTranslation(investmentLoanColumn, "InvestInput");
                ControllerUtils.provideTranslation(periodProfitLoanColumn, "ProfitColumn");
                daysToNextPeriodCount = 0;
            } else {
                daysToNextPeriod.add(daysToNextPeriodCount);
                if (capitalize) {
                    for (int i = 0; i < daysToNextPeriodCount; i++) {
                        periodProfit += deposit.countProfit();
                    }
                }
            }
            totalInvestment += periodProfit;
            String currency = deposit.getCurrency();
            data.add(new Object[]{
                    numbersColumnFlag,
                    formatCurrency(depositInvestment, currency),
                    formatCurrency(periodProfit, currency),
                    formatCurrency(totalInvestment, currency)
            });
            if (capitalize) {
                depositInvestment = totalInvestment;
                deposit.setInvestment(depositInvestment);
            }
            depositStartDate = depositStartDate.plusDays(daysToNextPeriodCount);
            numbersColumnFlag++;
        }
        return data;
    }


    private List<Object[]> countCreditWithHolidaysData(CreditWithHolidays credit) {
        daysToNextPeriod.add(0);
        daysToNextPeriodWithHolidays.add(0);
        LocalDate tempDate = credit.getStartDate();
        List<Object[]> data = new ArrayList<>();
        int numbersColumnFlag = 0;
        float dailyBodyPart = credit.countCreditBodyPerDay();
        dailyPart = credit.countCreditBodyPerDay();
        float tempLoan;
        loan = credit.getLoan();
        while (!tempDate.equals(credit.getEndDate())) {
            tempLoan = credit.getLoan();
            float totalLoan = tempLoan;
            float creditBody = 0;
            float periodPercents = 0;
            int daysToNextPeriodCount = DateTimeUtils.countDaysToNextPeriod(credit, tempDate);
            if (numbersColumnFlag == 0) {
                periodColumn.textProperty().bind(languageManager.getStringBinding(credit.getNameOfPaymentType()));
                investmentLoanColumn.textProperty().bind(languageManager.getStringBinding("LoanInput"));
                periodProfitLoanColumn.textProperty().bind(languageManager.getStringBinding("PaymentLoan"));
            } else {
                daysToNextPeriod.add(daysToNextPeriodCount);
                daysToNextPeriodWithHolidays.add(daysToNextPeriodCount);
                for (int i = 0; i < daysToNextPeriodCount; i++) {
                    if (!DateTimeUtils.isDateBetweenDates(tempDate, credit.getHolidaysStart(), credit.getHolidaysEnd())) {
                        creditBody += dailyBodyPart;
                    } else {
                        daysToNextPeriod.set(numbersColumnFlag, daysToNextPeriod.get(numbersColumnFlag) - 1);
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
            String currency = credit.getCurrency();
            data.add(new Object[]{numbersColumnFlag,
                    formatCurrency(tempLoan, currency),
                    formatCurrency(creditBody, currency),
                    formatCurrency(totalLoan, currency),
                    formatCurrency(periodPercents, currency)
            });
            numbersColumnFlag++;
        }
        return data;
    }

    private List<Object[]> countCreditWithoutHolidaysData(Credit credit) {
        daysToNextPeriod.add(0);
        daysToNextPeriodWithHolidays.add(0);
        LocalDate tempDate = credit.getStartDate();
        List<Object[]> data = new ArrayList<>();
        int numbersColumnFlag = 0;
        float dailyBodyPart = credit.countCreditBodyPerDay();
        dailyPart = credit.countCreditBodyPerDay();
        loan = credit.getLoan();
        float tempLoan;
        while (tempDate.isBefore(credit.getEndDate())) {
            tempLoan = credit.getLoan();
            float totalLoan = tempLoan;
            float creditBody = 0;
            float periodPercents = 0;
            int daysToNextPeriodCount = DateTimeUtils.countDaysToNextPeriod(credit, tempDate);
            if (numbersColumnFlag == 0) {
                periodColumn.textProperty().bind(languageManager.getStringBinding(credit.getNameOfPaymentType()));
                investmentLoanColumn.textProperty().bind(languageManager.getStringBinding("LoanInput"));
                periodProfitLoanColumn.textProperty().bind(languageManager.getStringBinding("PaymentLoan"));
            } else {
                daysToNextPeriod.add(daysToNextPeriodCount);
                daysToNextPeriodWithHolidays.add(daysToNextPeriodCount);
                for (int i = 0; i < daysToNextPeriodCount; i++) {
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
            String currency = credit.getCurrency();
            data.add(new Object[]{numbersColumnFlag,
                    formatCurrency(tempLoan, currency),
                    formatCurrency(creditBody, currency),
                    formatCurrency(totalLoan, currency),
                    formatCurrency(periodPercents, currency)
            });
            numbersColumnFlag++;
        }
        return data;
    }

    private void writeDataToCSV(List<Object[]> data, Deposit deposit, File file) {
        if (file != null) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                writer.println(deposit.getNameOfWithdrawalType() + ";" + investmentLoanColumn.getText() + ";" + periodProfitLoanColumn.getText() + ";" + totalColumn.getText());

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
        } else {
            LogHelper.log(Level.SEVERE, "Error while writing Deposit to CSV", null);
        }
    }

    private void writeDataToCSV(List<Object[]> data, Credit credit, File file) {
        if (file != null) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                writer.println(credit.getNameOfPaymentType() + ";" + investmentLoanColumn.getText() + ";" + periodProfitLoanColumn.getText() + ";" + totalColumn.getText() + ";" + periodPercentsColumn.getText());
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
        } else {
            LogHelper.log(Level.SEVERE, "Error while writing Credit to CSV", null);
        }
    }

    public void writeDataToExcel(List<Object[]> data, Deposit deposit, File file) {
        try (Workbook workbook = new XSSFWorkbook(); FileOutputStream fileOut = new FileOutputStream(file)) {
            int infoStartRow = data.size() + 2;
            Sheet sheet = workbook.createSheet("Deposit Data");
            boolean capitalize = deposit instanceof CapitalisedDeposit;

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue(periodColumn.getText());
            headerRow.createCell(1).setCellValue(investmentLoanColumn.getText());
            headerRow.createCell(2).setCellValue(periodProfitLoanColumn.getText());
            headerRow.createCell(3).setCellValue(totalColumn.getText());
            headerRow.createCell(4).setCellValue("Days in period");

            for (int f = 0; f < 5; f++) {
                int width = sheet.getColumnWidth(f);
                sheet.setColumnWidth(f, width * 2);
            }
            for (int i = 0; i < data.size(); i++) {

                Row row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue((int) data.get(i)[0]);
                if (capitalize) {
                    if (i == 0) {
                        row.createCell(1).setCellValue(tempInvest);
                        row.createCell(2).setCellFormula("B" + (i + 2) + "*(1/365)*" + "B" + (infoStartRow + 1) + "/100" + "*" + "E" + (i + 2));
                        row.createCell(3).setCellFormula("B" + (i + 2));
                    } else {
                        row.createCell(1).setCellFormula("D" + (i + 1));
                        row.createCell(2).setCellFormula("B" + (i + 2) + "*(1/365)*" + "B" + (infoStartRow + 1) + "/100" + "*" + "E" + (i + 2));
                        row.createCell(3).setCellFormula("D" + (i + 1) + "+C" + (i + 2));
                    }
                } else {
                    row.createCell(1).setCellValue(tempInvest);
                    row.createCell(2).setCellFormula("B" + (i + 2) + "*(1/365)*" + "B" + (infoStartRow + 1) + "/100" + "*" + "E" + (i + 2));
                    if (i == 0) {
                        row.createCell(3).setCellFormula("B" + (i + 2));
                    } else {
                        row.createCell(3).setCellFormula("D" + (i + 1) + "+C" + (i + 2));
                    }
                }
                row.createCell(4).setCellValue(daysToNextPeriod.get(i));
            }

            Row row = sheet.createRow(infoStartRow);
            row.createCell(0).setCellValue("Annual percent of deposit: ");
            row.createCell(1).setCellValue(deposit.getAnnualPercent());

            row = sheet.createRow(infoStartRow + 1);
            row.createCell(0).setCellValue("Currency: ");
            row.createCell(1).setCellValue(userSelectedCurrency);

            row = sheet.createRow(infoStartRow + 2);
            row.createCell(0).setCellValue("Start date: ");
            row.createCell(1).setCellValue(deposit.getStartDate().toString());

            row = sheet.createRow(infoStartRow + 3);
            row.createCell(0).setCellValue("End date: ");
            row.createCell(1).setCellValue(deposit.getEndDate().toString());

            if (deposit.isEarlyWithdrawal()) {
                row = sheet.createRow(infoStartRow + 4);
                row.createCell(0).setCellValue("Early withdrawal date: ");
                row.createCell(1).setCellValue(deposit.getEarlyWithdrawalDate().toString());
            }

            workbook.write(fileOut);
        } catch (IOException e) {
            LogHelper.log(Level.SEVERE, "Error while writing Deposit to Excel", e);
        }
    }

    public void writeDataToExcel(List<Object[]> data, Credit credit, File file) {
        try (Workbook workbook = new XSSFWorkbook(); FileOutputStream fileOut = new FileOutputStream(file)) {
            int infoStartRow = data.size() + 2;
            
            Sheet sheet = workbook.createSheet("Credit Data");
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue(credit.getNameOfPaymentType());
            headerRow.createCell(1).setCellValue(investmentLoanColumn.getText());
            headerRow.createCell(2).setCellValue(periodProfitLoanColumn.getText());
            headerRow.createCell(3).setCellValue(totalColumn.getText());
            headerRow.createCell(4).setCellValue(periodPercentsColumn.getText());
            headerRow.createCell(5).setCellValue("Payment days");
            headerRow.createCell(6).setCellValue("Days in period");
            
            for (int i = 0; i < data.size(); i++) {
                Row row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue((int) data.get(i)[0]);
                if (i == 0) {
                    row.createCell(1).setCellValue(loan);
                    row.createCell(2).setCellFormula("B" + (i + 2) + "*(1/365)*" + "B" + (infoStartRow + 1) + "/100" + "*" + "F" + (i + 2));
                    row.createCell(3).setCellFormula("B" + (i + 2));
                    row.createCell(4).setCellFormula("B" + (i + 2) + "*(1/365)*" + "B" + (infoStartRow + 1) + "/100" + "*" + "F" + (i + 2));
                } else {
                    row.createCell(1).setCellFormula("D" + (i + 1));
                    row.createCell(2).setCellFormula("B" + (infoStartRow + 5) + "*" + "F" + (i + 2));
                    row.createCell(3).setCellFormula("D" + (i + 1) + "-C" + (i + 2));
                    row.createCell(4).setCellFormula("B" + (i + 2) + "*(1/365)*" + "B" + (infoStartRow + 1) + "/100" + "*" + "G" + (i + 2));
                    if (i == data.size() - 1) {
                        row.createCell(2).setCellFormula("B" + (i + 2));
                        row.createCell(3).setCellValue(0);
                    }
                }
                row.createCell(5).setCellValue(daysToNextPeriod.get(i));
                row.createCell(6).setCellValue(daysToNextPeriodWithHolidays.get(i));
            }

            Row row = sheet.createRow(infoStartRow);
            row.createCell(0).setCellValue("Annual percent of credit: ");
            row.createCell(1).setCellValue(credit.getAnnualPercent());

            row = sheet.createRow(infoStartRow + 1);
            row.createCell(0).setCellValue("Currency: ");
            row.createCell(1).setCellValue(userSelectedCurrency);

            row = sheet.createRow(infoStartRow + 2);
            row.createCell(0).setCellValue("Start date: ");
            row.createCell(1).setCellValue(credit.getStartDate().toString());

            row = sheet.createRow(infoStartRow + 3);
            row.createCell(0).setCellValue("End date: ");
            row.createCell(1).setCellValue(credit.getEndDate().toString());

            row = sheet.createRow(infoStartRow + 4);
            row.createCell(0).setCellValue("Daily credit body part: ");
            row.createCell(1).setCellValue(dailyPart);

            if (credit instanceof CreditWithHolidays) {
                row = sheet.createRow(infoStartRow + 5);
                row.createCell(0).setCellValue("Holidays start date: ");
                row.createCell(1).setCellValue(((CreditWithHolidays) credit).getHolidaysStart().toString());

                row = sheet.createRow(infoStartRow + 6);
                row.createCell(0).setCellValue("Holidays end date: ");
                row.createCell(1).setCellValue(((CreditWithHolidays) credit).getHolidaysEnd().toString());
            }
            workbook.write(fileOut);
        } catch (IOException e) {
            LogHelper.log(Level.SEVERE, "Error while writing Deposit to Excel", e);
        }
    }

    private static void initializeTableColumns(TableColumn<Object[], Integer> periodColumn, TableColumn<Object[], String> investmentLoanColumn, TableColumn<Object[], String> periodProfitLoanColumn, TableColumn<Object[], String> totalColumn) {
        periodColumn.setCellValueFactory(cellData -> cellData.getValue()[0] == null ? null : new SimpleObjectProperty<>((Integer) cellData.getValue()[0]));
        investmentLoanColumn.setCellValueFactory(cellData -> cellData.getValue()[1] == null ? null : new SimpleObjectProperty<>((String) cellData.getValue()[1]));
        periodProfitLoanColumn.setCellValueFactory(cellData -> cellData.getValue()[2] == null ? null : new SimpleObjectProperty<>((String) cellData.getValue()[2]));
        totalColumn.setCellValueFactory(cellData -> cellData.getValue()[3] == null ? null : new SimpleObjectProperty<>((String) cellData.getValue()[3]));
    }

    private ArrayList<String> saveFileTypes() {
        ArrayList<String> fileTypes = new ArrayList<>();
        fileTypes.add(CSV_FILE);
        fileTypes.add(EXCEL_FILE);
        return fileTypes;
    }

    private ArrayList<String> saveExtensions() {
        ArrayList<String> extensions = new ArrayList<>();
        extensions.add(CSV_EXTENSION);
        extensions.add(EXCEL_EXTENSION);
        return extensions;
    }

    private String formatCurrency(float amount, String currency) {
        return String.format("%.2f", amount) + currency;
    }

}


