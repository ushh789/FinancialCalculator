package com.netrunners.financialcalculator.logic.entity.deposit;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.netrunners.financialcalculator.logic.entity.OperationPeriodType;

import java.time.LocalDate;
import java.util.Map;

public class UncapitalisedDeposit extends Deposit {
    public UncapitalisedDeposit(float investment, String currency, float annualPercent, LocalDate startDate, LocalDate endDate, boolean earlyWithdrawal, LocalDate earlyWithdrawalDate, OperationPeriodType withdrawalOption) {
        super(investment, currency, annualPercent, startDate, endDate, earlyWithdrawal, earlyWithdrawalDate, withdrawalOption);
    }

    public UncapitalisedDeposit(float investment, String currency, float annualPercent, LocalDate startDate, LocalDate endDate, boolean earlyWithdrawal, OperationPeriodType withdrawalOption) {
        super(investment, currency, annualPercent, startDate, endDate, earlyWithdrawal, withdrawalOption);
    }

    public UncapitalisedDeposit(JsonObject jsonObject) {
        super(jsonObject);
    }

    @Override
    public float countProfit() {
        return super.countProfit();
    }

    @Override
    public void save() {
        super.save();
    }

    public JsonObject getJsonObject() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("operation", "Deposit");
        jsonObject.addProperty("type", "Uncapitalized");
        JsonObject superJsonObject = super.getJsonObject();
        for (Map.Entry<String, JsonElement> entry : superJsonObject.entrySet()) {
            jsonObject.add(entry.getKey(), entry.getValue());
        }
        return jsonObject;
    }
}
