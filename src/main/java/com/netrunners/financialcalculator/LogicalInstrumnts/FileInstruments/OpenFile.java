package com.netrunners.financialcalculator.LogicalInstrumnts.FileInstruments;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation.Credit.Credit;
import com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation.Credit.CreditWithHolidays;
import com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation.Credit.CreditWithoutHolidays;
import com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation.Deposit.CapitalisedDeposit;
import com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation.Deposit.Deposit;
import com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation.Deposit.UncapitalisedDeposit;
import com.netrunners.financialcalculator.VisualInstruments.MenuActions.LanguageManager;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.logging.Level;

import static com.netrunners.financialcalculator.StartMenu.startMenuScene;
public class OpenFile {

    public static void openFromSave() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(LanguageManager.getInstance().getStringBinding("ChooseOpenFile").get());
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json"));
        File initialDirectory = new File("saves/");
        fileChooser.setInitialDirectory(initialDirectory);
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try (FileReader reader = new FileReader(selectedFile)) {
                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);

                String operationType = jsonObject.get("operation").getAsString();
                switch (operationType) {
                    case "Credit" -> {
                        Credit credit = createCredit(jsonObject);
                        credit.sendCreditToResultTable();
                    }
                    case "Deposit" -> {
                        Deposit deposit = createDeposit(jsonObject);
                        deposit.sendDepositToResultTable();
                    }
                    default ->  LogHelper.log(Level.WARNING, "Can't open file with operation type: " + operationType, null);
                }
            } catch (IOException | JsonParseException e) {
                LogHelper.log(Level.SEVERE, "Error while opening file", e);
            }
        }
    }

    private static Credit createCredit(JsonObject jsonObject) {
        Credit credit = null;
        try {
            float loan = jsonObject.get("loan").getAsFloat();
            float annualPercent = jsonObject.get("annualPercent").getAsFloat();
            int paymentType = jsonObject.get("paymentType").getAsInt();
            String currency = jsonObject.get("currency").getAsString();
            String startDateString = jsonObject.get("startDate").getAsString();
            LocalDate startDate = LocalDate.parse(startDateString);
            String endDateString = jsonObject.get("endDate").getAsString();
            LocalDate endDate = LocalDate.parse(endDateString);
            if (jsonObject.get("type").getAsString().equals("WithHolidays")) {
                String holidaysStartString = jsonObject.get("holidaysStart").getAsString();
                LocalDate holidaysStart = LocalDate.parse(holidaysStartString);
                String holidaysEndString = jsonObject.get("holidaysEnd").getAsString();
                LocalDate holidaysEnd = LocalDate.parse(holidaysEndString);
                credit = new CreditWithHolidays(loan, currency, annualPercent, startDate, endDate, paymentType, holidaysStart, holidaysEnd);
            } else {
                credit = new CreditWithoutHolidays(loan, currency, annualPercent, startDate, endDate, paymentType);
            }

        } catch (Exception e) {
            LogHelper.log(Level.SEVERE, "Error while opening file", e);
        }
        return credit;
    }

    private static Deposit createDeposit(JsonObject jsonObject) {
        Deposit deposit = null;
        try {
            float investment = jsonObject.get("investment").getAsFloat();
            float annualPercent = jsonObject.get("annualPercent").getAsFloat();
            int withdrawalOption = jsonObject.get("withdrawalOption").getAsInt();
            boolean earlyWithdrawal = jsonObject.get("earlyWithdrawal").getAsBoolean();
            String currency = jsonObject.get("currency").getAsString();
            String startDateString = jsonObject.get("startDate").getAsString();
            LocalDate startDate = LocalDate.parse(startDateString);
            String endDateString = jsonObject.get("endDate").getAsString();
            LocalDate endDate = LocalDate.parse(endDateString);
            if (jsonObject.get("type").getAsString().equals("Capitalized") && earlyWithdrawal) {
                String earlyWithdrawalDateString = jsonObject.get("earlyWithdrawalDate").getAsString();
                LocalDate earlyWithdrawalDate = LocalDate.parse(earlyWithdrawalDateString);
                deposit = new CapitalisedDeposit(investment, currency, annualPercent, startDate, endDate, earlyWithdrawal, earlyWithdrawalDate, withdrawalOption);
            } else if (jsonObject.get("type").getAsString().equals("Capitalized") && !earlyWithdrawal) {
                deposit = new CapitalisedDeposit(investment, currency, annualPercent, startDate, endDate, earlyWithdrawal, withdrawalOption);
            } else if (jsonObject.get("type").getAsString().equals("Uncapitalized") && earlyWithdrawal) {
                String earlyWithdrawalDateString = jsonObject.get("earlyWithdrawalDate").getAsString();
                LocalDate earlyWithdrawalDate = LocalDate.parse(earlyWithdrawalDateString);
                deposit = new UncapitalisedDeposit(investment, currency, annualPercent, startDate, endDate, earlyWithdrawal, earlyWithdrawalDate, withdrawalOption);
            } else {
                deposit = new UncapitalisedDeposit(investment, currency, annualPercent, startDate, endDate, earlyWithdrawal, withdrawalOption);
            }
        } catch (Exception e) {
            LogHelper.log(Level.SEVERE, "Error while creating deposit", e);
        }
        return deposit;
    }
}
