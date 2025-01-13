package com.netrunners.financialcalculator.controller;

import com.netrunners.financialcalculator.logic.ResultTableDataCounter;
import com.netrunners.financialcalculator.logic.files.SaveFile;
import com.netrunners.financialcalculator.logic.entity.deposit.Deposit;
import com.netrunners.financialcalculator.logic.entity.credit.Credit;
import com.netrunners.financialcalculator.logic.entity.credit.CreditWithHolidays;
import com.netrunners.financialcalculator.logic.entity.credit.CreditWithoutHolidays;
import com.netrunners.financialcalculator.logic.entity.ResultTableSender;
import com.netrunners.financialcalculator.ui.FilesActions;
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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.netrunners.financialcalculator.constants.FileConstants.CSV_EXTENSION;
import static com.netrunners.financialcalculator.constants.FileConstants.EXCEL_EXTENSION;
import static com.netrunners.financialcalculator.constants.FileConstants.SAVES_PATH;
import static com.netrunners.financialcalculator.constants.FileConstants.CSV_FILE;
import static com.netrunners.financialcalculator.constants.FileConstants.EXCEL_FILE;
import static com.netrunners.financialcalculator.constants.StringConstants.FORMATTED_TIME_NOW;
import static com.netrunners.financialcalculator.constants.StringConstants.ASTERISK;


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
    
    private ResultTableDataCounter resultTableDataCounter;

    @FXML
    void initialize() {
        openFileButton.setDisable(true);
        currency.setDisable(true);

        // Menu items initialization
        ControllerUtils.initializeMenuItems(darkTheme, lightTheme, aboutUs, exitApp, depositButtonMenu, creditButtonMenu, languageButton, currency);
        exportButton.setOnAction(event -> {

        });
        convertButton.setOnAction(event -> CurrencyManager.updateResultTable(resultTableDataCounter.getFinancialOperation(), investmentLoanColumn, periodProfitLoanColumn, totalColumn, periodPercentsColumn, resultTable));


        // Menu text bindings
        ControllerUtils.initializeTextBindings(settingsButton, aboutButton, viewButton, themeButton, newButton, fileButton, depositButtonMenu, creditButtonMenu, languageButton, darkTheme, lightTheme, aboutUs, exitApp, currency, openFileButton, saveAsButton, saveButton);
        ControllerUtils.provideTranslation(financialCalculatorLabel, "ResultTableLabel");
        ControllerUtils.provideTranslation(totalColumn, "totalColumn");
        ControllerUtils.provideTranslation(periodPercentsColumn, "PeriodPercents");
        ControllerUtils.provideTranslation(exportButton, "Export");
        ControllerUtils.provideTranslation(convertButton, "ConvertTitle");
    }

    public void updateTable(ResultTableSender financialOperation) {
        resultTableDataCounter = new ResultTableDataCounter(financialOperation, periodColumn, investmentLoanColumn, periodProfitLoanColumn);
        resultTable.getColumns().clear();
        if (financialOperation instanceof Credit) {
            fillColumns((Credit) financialOperation);
        } else if (financialOperation instanceof Deposit) {
            fillColumns((Deposit) financialOperation);
        }
    }

    private void fillColumns(Credit credit) {
        // Table columns initialization
        periodPercentsColumn.setVisible(true);
        initializeTableColumns(periodColumn, investmentLoanColumn, periodProfitLoanColumn, totalColumn);
        periodPercentsColumn.setCellValueFactory(cellData -> cellData.getValue()[4] == null ? null : new SimpleObjectProperty<>((String) cellData.getValue()[4]));

        List<Object[]> data = new ArrayList<>();
        
        resultTable.getColumns().addAll(periodColumn, investmentLoanColumn, periodProfitLoanColumn, totalColumn, periodPercentsColumn);
        
        if (credit instanceof CreditWithoutHolidays) {
            data = resultTableDataCounter.countData();
        } else if (credit instanceof CreditWithHolidays) {
            data = resultTableDataCounter.countData();
        }
        
        ObservableList<Object[]> observableData = FXCollections.observableArrayList(data);
        resultTable.setItems(observableData);

        List<Object[]> finalData = data;
        exportButton.setOnAction(event -> {
            File selectedFile = FilesActions.showFileChooserSaver("Export", saveFileTypes(), saveExtensions());

            if (selectedFile != null && selectedFile.getName().endsWith(EXCEL_EXTENSION)) {
                SaveFile.writeDataToXLS(finalData, resultTableDataCounter.getFinancialOperation(), selectedFile, investmentLoanColumn, periodProfitLoanColumn, totalColumn, periodColumn, periodPercentsColumn, resultTableDataCounter.getDaysToNextPeriod(), resultTableDataCounter.getDaysToNextPeriodWithHolidays());
            } else {
                SaveFile.writeDataToCSV(finalData, selectedFile, credit, investmentLoanColumn, periodProfitLoanColumn, totalColumn, periodPercentsColumn);
            }
        });
        
        saveButton.setOnAction(event -> {
            File file = new File(SAVES_PATH + "/Credit_result_" + FORMATTED_TIME_NOW + CSV_EXTENSION);
            SaveFile.writeDataToCSV(finalData, file, credit, investmentLoanColumn, periodProfitLoanColumn, totalColumn, periodPercentsColumn);
            FilesActions.showSavingAlert();
        });
        
        saveAsButton.setOnAction(event -> {
            File selectedFile = FilesActions.showFileChooserSaver("saveAsButton", saveFileTypes(), saveExtensions());

            if (selectedFile != null && selectedFile.getName().endsWith(EXCEL_EXTENSION)) {
                SaveFile.writeDataToXLS(finalData, resultTableDataCounter.getFinancialOperation(), selectedFile, investmentLoanColumn, periodProfitLoanColumn, totalColumn, periodColumn, periodPercentsColumn, resultTableDataCounter.getDaysToNextPeriod(), resultTableDataCounter.getDaysToNextPeriodWithHolidays());
            } else {
                SaveFile.writeDataToCSV(finalData, selectedFile, credit, investmentLoanColumn, periodProfitLoanColumn, totalColumn, periodPercentsColumn);
            }
        });
    }

    private void fillColumns(Deposit deposit) {
        // Table columns initialization
        periodPercentsColumn.setVisible(false);
        initializeTableColumns(periodColumn, investmentLoanColumn, periodProfitLoanColumn, totalColumn);

        List<Object[]> data = resultTableDataCounter.countData();
        resultTable.getColumns().addAll(periodColumn, investmentLoanColumn, periodProfitLoanColumn, totalColumn);
        ObservableList<Object[]> observableData = FXCollections.observableArrayList(data);
        resultTable.setItems(observableData);
        
        exportButton.setOnAction(event -> {
            File selectedFile = FilesActions.showFileChooserSaver("Export", saveFileTypes(), saveExtensions());

            if (selectedFile != null && selectedFile.getName().endsWith(EXCEL_EXTENSION)) {
                SaveFile.writeDataToXLS(data, resultTableDataCounter.getFinancialOperation(), selectedFile, investmentLoanColumn, periodProfitLoanColumn, totalColumn, periodColumn, periodPercentsColumn, resultTableDataCounter.getDaysToNextPeriod(), resultTableDataCounter.getDaysToNextPeriodWithHolidays());
            } else {
                SaveFile.writeDataToCSV(data, selectedFile, deposit, investmentLoanColumn, periodProfitLoanColumn, totalColumn, periodPercentsColumn);
            }
        });
        saveButton.setOnAction(event -> {
            File file = new File(SAVES_PATH + "/Deposit_result_" + FORMATTED_TIME_NOW + CSV_EXTENSION);
            SaveFile.writeDataToCSV(data, file, deposit, investmentLoanColumn, periodProfitLoanColumn, totalColumn, periodPercentsColumn);
            FilesActions.showSavingAlert();
        });
        saveAsButton.setOnAction(event -> {
            File selectedFile = FilesActions.showFileChooserSaver("saveAsButton", saveFileTypes(), saveExtensions());

            if (selectedFile != null && selectedFile.getName().endsWith(EXCEL_EXTENSION)) {
                SaveFile.writeDataToXLS(data, resultTableDataCounter.getFinancialOperation(), selectedFile, investmentLoanColumn, periodProfitLoanColumn, totalColumn, periodColumn, periodPercentsColumn, resultTableDataCounter.getDaysToNextPeriod(), resultTableDataCounter.getDaysToNextPeriodWithHolidays());
            } else {
                SaveFile.writeDataToCSV(data, selectedFile, deposit, investmentLoanColumn, periodProfitLoanColumn, totalColumn, periodPercentsColumn);
            }
        });
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
        extensions.add(ASTERISK + CSV_EXTENSION);
        extensions.add(ASTERISK + EXCEL_EXTENSION);
        return extensions;
    }
}


