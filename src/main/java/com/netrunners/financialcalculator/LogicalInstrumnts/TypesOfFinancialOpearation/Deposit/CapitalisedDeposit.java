package com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation.Deposit;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation.OperationType;


import java.time.LocalDate;
import java.util.Map;

public class CapitalisedDeposit extends Deposit {
    public CapitalisedDeposit(float investment, String currency, float annualPercent, LocalDate startDate, LocalDate endDate, boolean earlyWithdrawal, LocalDate earlyWithdrawalDate, OperationType withdrawalOption) {
        super(investment, currency, annualPercent, startDate, endDate, earlyWithdrawal, earlyWithdrawalDate, withdrawalOption);
    }

    public CapitalisedDeposit(float investment, String currency, float annualPercent, LocalDate startDate, LocalDate endDate, boolean earlyWithdrawal, OperationType withdrawalOption) {
        super(investment, currency, annualPercent, startDate, endDate, earlyWithdrawal, withdrawalOption);
    }

    @Override
    public float countProfit() {
        return super.countProfit();
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

}
