package com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation.Credit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.netrunners.financialcalculator.LogicalInstrumnts.FileInstruments.Savable;
import com.netrunners.financialcalculator.LogicalInstrumnts.TimeFunctions.LocalDateAdapter;

import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;


public class Credit implements Savable {
    protected float loan;
    protected String currency;
    protected float annualPercent;
    protected LocalDate startDate;
    protected LocalDate endDate;
    protected int paymentType;

    public Credit(float loan, String currency, float annualPercent, LocalDate startDate, LocalDate endDate, int paymentType) {
        this.loan = loan;
        this.currency = currency;
        this.annualPercent = annualPercent;
        this.startDate = startDate;
        this.endDate = endDate;
        this.paymentType = paymentType;
    }

    protected float countLoan() {
        return 0;
    }


    public void save() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        JsonObject jsonObject = getJsonObject();
        String json = gson.toJson(jsonObject);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Data");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JSON Files", "*.json"));
        File file = fileChooser.showSaveDialog(null); // stage is your JavaFX stage

        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(json);
            } catch (IOException e) {
                e.getMessage();
            }
        }
    }

    protected JsonObject getJsonObject() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("investment", this.loan);
        jsonObject.addProperty("annualPercent", this.annualPercent);
        jsonObject.addProperty("currency", this.currency);
        jsonObject.addProperty("startDate", this.startDate.toString());
        jsonObject.addProperty("endDate", this.endDate.toString());
        return jsonObject;
    }


    @Override
    public void open() {

    }
}
