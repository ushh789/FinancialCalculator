package com.netrunners.financialcalculator.logic.entity.deposit;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.netrunners.financialcalculator.logic.entity.OperationPeriodType;


import java.time.LocalDate;
import java.util.Map;

public class CapitalisedDeposit extends Deposit {
    float initialInvestment;
    public CapitalisedDeposit(float investment, String currency, float annualPercent, LocalDate startDate, LocalDate endDate, boolean earlyWithdrawal, LocalDate earlyWithdrawalDate, OperationPeriodType withdrawalOption) {
        super(investment, currency, annualPercent, startDate, endDate, earlyWithdrawal, earlyWithdrawalDate, withdrawalOption);
        this.initialInvestment = investment;
    }

    public CapitalisedDeposit(float investment, String currency, float annualPercent, LocalDate startDate, LocalDate endDate, boolean earlyWithdrawal, OperationPeriodType withdrawalOption) {
        super(investment, currency, annualPercent, startDate, endDate, earlyWithdrawal, withdrawalOption);
        this.initialInvestment = investment;
    }

    public CapitalisedDeposit(JsonObject jsonObject) {
        super(jsonObject);
        this.initialInvestment = jsonObject.get("initialInvestment").getAsFloat();
    }

    @Override
    public JsonObject getJsonObject() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("operation", "Deposit");
        jsonObject.addProperty("type", "Capitalized");
        jsonObject.addProperty("initialInvestment", this.initialInvestment);
        JsonObject superJsonObject = super.getJsonObject();
        for (Map.Entry<String, JsonElement> entry : superJsonObject.entrySet()) {
            jsonObject.add(entry.getKey(), entry.getValue());
        }
        return jsonObject;
    }

    public float getInitialInvestment() {
        return this.initialInvestment;
    }

}
