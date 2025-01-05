package com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation.Deposit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.netrunners.financialcalculator.LogicalInstrumnts.FileInstruments.LogHelper;
import com.netrunners.financialcalculator.LogicalInstrumnts.FileInstruments.Savable;
import com.netrunners.financialcalculator.LogicalInstrumnts.TimeFunctions.LocalDateAdapter;
import com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation.ResultTableSender;
import com.netrunners.financialcalculator.VisualInstruments.MenuActions.LanguageManager;
import com.netrunners.financialcalculator.VisualInstruments.WindowsOpener;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.logging.Level;

public abstract class Deposit implements Savable, ResultTableSender {
    protected float investment;
    protected float annualPercent;
    protected String currency;
    protected LocalDate startDate;
    protected LocalDate endDate;
    protected int withdrawalOption;
    protected boolean earlyWithdrawal;
    protected LocalDate earlyWithdrawalDate;


    public float countProfit() {
        return investment * (1f / 365f) * (annualPercent / 100f);
    }

    public Deposit(float investment, String currency, float annualPercent, LocalDate startDate, LocalDate endDate, boolean earlyWithdrawal, LocalDate earlyWithdrawalDate, int withdrawalOption) {
        this.investment = investment;
        this.currency = currency;
        this.annualPercent = annualPercent;
        this.startDate = startDate;
        this.endDate = endDate;
        this.earlyWithdrawal = earlyWithdrawal;
        this.earlyWithdrawalDate = earlyWithdrawalDate;
        this.withdrawalOption = withdrawalOption;
    }

    public Deposit(float investment, String currency, float annualPercent, LocalDate startDate, LocalDate endDate, boolean earlyWithdrawal, int withdrawalOption) {
        this.investment = investment;
        this.currency = currency;
        this.annualPercent = annualPercent;
        this.startDate = startDate;
        this.endDate = endDate;
        this.earlyWithdrawal = earlyWithdrawal;
        this.withdrawalOption = withdrawalOption;
    }

    protected JsonObject getJsonObject() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("investment", this.investment);
        jsonObject.addProperty("annualPercent", this.annualPercent);
        jsonObject.addProperty("currency", this.currency);
        jsonObject.addProperty("startDate", this.startDate.toString());
        jsonObject.addProperty("endDate", this.endDate.toString());
        jsonObject.addProperty("withdrawalOption", this.withdrawalOption);
        if (this.earlyWithdrawal) {
            jsonObject.addProperty("earlyWithdrawalDate", this.earlyWithdrawalDate.toString());
        } else {
            jsonObject.addProperty("earlyWithdrawalDate", "None");
        }
        jsonObject.addProperty("earlyWithdrawal", this.earlyWithdrawal);
        return jsonObject;
    }

    @Override
    public void save() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        JsonObject jsonObject = getJsonObject();
        String json = gson.toJson(jsonObject);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(LanguageManager.getInstance().getStringBinding("saveButton").get());
        File initialDirectory = new File("saves/");
        fileChooser.setInitialDirectory(initialDirectory);
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JSON Files", "*.json"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try {
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(json);
                fileWriter.close();
            } catch (IOException e) {
                LogHelper.log(Level.SEVERE, "Error while saving Deposit to json", e);
            }
        }
    }

    public String getNameOfWithdrawalType() {
        LanguageManager languageManager = LanguageManager.getInstance();
        switch (withdrawalOption) {
            case 1 -> {
                return "Months";
            }
            case 2 -> {
                return "Quarters";
            }
            case 3 -> {
                return "Years";
            }
            case 4 -> {
                return "EndofTerm";
            }
        }
        return languageManager.getStringBinding("None").get();
    }

    @Override
    public void sendToResultTable() {
        WindowsOpener.openResultTable(this);
    }


    public float getInvestment() {
        return investment;
    }

    public void setInvestment(float investment) {
        this.investment = investment;
    }

    public float getAnnualPercent() {
        return annualPercent;
    }

    public String getCurrency() {
        return currency;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public int getWithdrawalOption() {
        return withdrawalOption;
    }

    public boolean isEarlyWithdrawal() {
        return earlyWithdrawal;
    }

    public LocalDate getEarlyWithdrawalDate() {
        return earlyWithdrawalDate;
    }
}