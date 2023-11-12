package com.netrunners.financialcalculator.MenuControllers;


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
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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

    }
    @FXML
    void initialize() {
        updateText();
        LanguageManager.getInstance().languageProperty().addListener((observable, oldValue, newValue) -> {
            updateText();
        });
        darkTheme.setOnAction(event -> ThemeSelector.setDarkTheme());
        lightTheme.setOnAction(event -> ThemeSelector.setLightTheme());
        aboutUs.setOnAction(event -> AboutUsAlert.showAboutUs());
        exitApp.setOnAction(event -> ExitApp.exitApp());
        depositButtonMenu.setOnAction(event -> WindowsOpener.depositOpener());
        creditButtonMenu.setOnAction(event -> WindowsOpener.creditOpener());
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
    }

    public void updateTable(Deposit deposit) {
        fillColumns(deposit);
        System.out.println(deposit);
    }

    public void updateTable(Credit credit) {
        fillColumns(credit);
        System.out.println(credit);
    }


    private void writeDataToCSV(List<Object[]> data, Deposit deposit) {
        String csvFile = "depositResult.csv";
        try (PrintWriter writer = new PrintWriter(new FileWriter(csvFile))) {
            writer.println(deposit.getNameOfWithdrawalType()  + ";" + investmentloanColumn.getText() + ";" + periodProfitLoanColumn.getText() + ";" + totalColumn.getText());

            for (Object[] row : data) {
                writer.println(row[0] + ";" + row[1] + ";" + row[2] + ";" + row[3]);
                writer.flush();
            }
            writer.println("Annual percent of deposit: " + deposit.getAnnualPercent());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void writeDataToCSV(List<Object[]> data, Credit credit){
        String csvFile = "creditResult.csv";
        try (PrintWriter writer = new PrintWriter(new FileWriter(csvFile))) {
            writer.println(credit.getNameOfPaymentType()  + ";" + investmentloanColumn.getText() + ";" + periodProfitLoanColumn.getText() + ";" + totalColumn.getText());

            for (Object[] row : data) {
                writer.println(row[0] + ";" + row[1] + ";" + row[2] + ";" + row[3]);
                writer.flush();
            }
            //Write a Annual percent of deposit
            writer.println("Annual percent of deposit: " + credit.getAnnualPercent());
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        writeDataToCSV(data, credit);
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
        writeDataToCSV(data, deposit);
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
        investmentloanColumn.setText(LanguageManager.getInstance().getTranslation("investmentColumn"));
        financialCalculatorLabel.setText(LanguageManager.getInstance().getTranslation("resultTablelabel"));
        periodColumn.setText(LanguageManager.getInstance().getTranslation("periodColumn"));
    }
}



