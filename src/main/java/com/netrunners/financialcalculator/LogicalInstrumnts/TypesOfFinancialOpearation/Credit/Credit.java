package com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation.Credit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.netrunners.financialcalculator.LogicalInstrumnts.FileInstruments.LogHelper;
import com.netrunners.financialcalculator.LogicalInstrumnts.FileInstruments.Savable;
import com.netrunners.financialcalculator.LogicalInstrumnts.TimeFunctions.DateTimeFunctions;
import com.netrunners.financialcalculator.LogicalInstrumnts.TimeFunctions.LocalDateAdapter;
import com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation.OperationType;
import com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation.ResultTableSender;
import com.netrunners.financialcalculator.VisualInstruments.MenuActions.LanguageManager;
import com.netrunners.financialcalculator.VisualInstruments.WindowsOpener;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.logging.Level;


public class Credit implements Savable, ResultTableSender {
    protected float loan;
    protected String currency;
    protected float annualPercent;
    protected LocalDate startDate;
    protected LocalDate endDate;
    protected OperationType paymentType;
    protected int contractDuration;

    public Credit(float loan, String currency, float annualPercent, LocalDate startDate, LocalDate endDate, OperationType paymentType) {
        this.loan = loan;
        this.currency = currency;
        this.annualPercent = annualPercent;
        this.startDate = startDate;
        this.endDate = endDate;
        this.paymentType = paymentType;
        this.contractDuration = DateTimeFunctions.countDaysBetweenDates(startDate, endDate);
    }

    public float countLoan() {
        return loan * (1f / 365f) * (annualPercent / 100f);
    }

    public float countCreditBodyPerDay() {
        return loan / contractDuration;
    }


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
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(json);
            } catch (IOException e) {
                LogHelper.log(Level.SEVERE, "Error while saving Credit to json", e);
            }
        }
    }


    protected JsonObject getJsonObject() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("loan", this.loan);
        jsonObject.addProperty("annualPercent", this.annualPercent);
        jsonObject.addProperty("currency", this.currency);
        jsonObject.addProperty("paymentType", this.paymentType.getKey());
        jsonObject.addProperty("startDate", this.startDate.toString());
        jsonObject.addProperty("endDate", this.endDate.toString());
        return jsonObject;
    }

    public float getLoan() {
        return loan;
    }

    public String getCurrency() {
        return currency;
    }

    public float getAnnualPercent() {
        return annualPercent;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public OperationType getPaymentType() {
        return paymentType;
    }

    public void setLoan(float loan) {
        this.loan = loan;
    }

    public String getNameOfPaymentType() {
        if (paymentType == null) {
            return LanguageManager.getInstance().getStringBinding("None").get();
        } else {
            return paymentType.getKey();
        }
    }

    @Override
    public void sendToResultTable() {
        WindowsOpener.openResultTable(this);
    }
}
