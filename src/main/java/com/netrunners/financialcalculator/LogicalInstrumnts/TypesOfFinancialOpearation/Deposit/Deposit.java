package com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation.Deposit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.netrunners.financialcalculator.LogicalInstrumnts.FileInstruments.Savable;
import com.netrunners.financialcalculator.LogicalInstrumnts.TimeFunctions.LocalDateAdapter;


import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Deposit implements Savable {
    protected float investment;
    protected float annualPercent;
    protected String currency;
    protected LocalDate startDate;
    protected LocalDate endDate;
    protected int withdrawalOption;
    protected boolean earlyWithdrawal;
    protected LocalDate earlyWithdrawalDate;

    protected abstract float countProfit();
    public Deposit(int investment, String currency, float annualPercent, LocalDate startDate, LocalDate endDate, LocalDate earlyWithdrawalDate, int withdrawalOption){
        this.investment = investment;
        this.currency = currency;
        this.annualPercent = annualPercent;
        this.startDate = startDate;
        this.endDate = endDate;
        this.earlyWithdrawalDate = earlyWithdrawalDate;
        this.withdrawalOption = withdrawalOption;
    }
    public Deposit(int investment, String currency, float annualPercent, LocalDate startDate, LocalDate endDate, int withdrawalOption){
        this.investment = investment;
        this.currency = currency;
        this.annualPercent = annualPercent;
        this.startDate = startDate;
        this.endDate = endDate;
        this.withdrawalOption = withdrawalOption;
    }

    protected JsonObject getJsonObject(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("investment", this.investment);
        jsonObject.addProperty("annualPercent", this.annualPercent);
        jsonObject.addProperty("currency", this.currency);
        jsonObject.addProperty("startDate", this.startDate.toString());
        jsonObject.addProperty("endDate", this.endDate.toString());
        jsonObject.addProperty("withdrawalOption", this.withdrawalOption);
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
        try (FileWriter writer = new FileWriter("saves/credit" + ".json")){
            writer.write(json);
        }
        catch (IOException e){
            e.getMessage();
        }
    }


    @Override
    public void open(){}
}