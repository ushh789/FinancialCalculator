package com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation.Deposit;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.time.LocalDate;
import java.util.Map;

public class UncapitalisedDeposit extends Deposit{
    public UncapitalisedDeposit(int investment, String currency, float annualPercent, LocalDate startDate, LocalDate endDate, LocalDate earlyWithdrawalDate, int withdrawalOption) {
        super(investment, currency, annualPercent, startDate, endDate, earlyWithdrawalDate, withdrawalOption);
    }

    public UncapitalisedDeposit(int investment, String currency, float annualPercent, LocalDate startDate, LocalDate endDate, int withdrawalOption) {
        super(investment, currency, annualPercent, startDate, endDate, withdrawalOption);
    }

    @Override
    protected float countProfit() {
        return 0;
    }


    @Override
    public void save(String filename) {
        super.save(filename);
    }

    protected JsonObject getJsonObject() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("operation", "Deposit");
        jsonObject.addProperty("type", "Uncapitalized");
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
