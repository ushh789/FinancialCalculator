package com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation.Deposit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.netrunners.financialcalculator.LogicalInstrumnts.TimeFunctions.LocalDateAdapter;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class CapitalisedDeposit extends Deposit{
    public CapitalisedDeposit(float investment, String currency, float annualPercent, LocalDate startDate, LocalDate endDate, LocalDate earlyWithdrawalDate, int withdrawalOption) {
        super(investment,currency, annualPercent, startDate, endDate, earlyWithdrawalDate, withdrawalOption);
    }

    public CapitalisedDeposit(float investment, String currency, float annualPercent, LocalDate startDate, LocalDate endDate, int withdrawalOption) {
        super(investment, currency, annualPercent, startDate, endDate, withdrawalOption);
    }
    @Override
    protected float countProfit() {
        return 0;
    }

    @Override
    public void save() {
        super.save();
    }

    protected JsonObject getJsonObject() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("operation", "Deposit");
        jsonObject.addProperty("type", "Capitalized");
        JsonObject superJsonObject = super.getJsonObject();
        for (Map.Entry<String, JsonElement> entry : superJsonObject.entrySet()) {
            jsonObject.add(entry.getKey(), entry.getValue());
        }
        return jsonObject;
    }

    @Override
    public void open() {

    }
}
