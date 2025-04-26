package com.netrunners.financialcalculator.logic.entity.credit;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.netrunners.financialcalculator.logic.entity.OperationPeriodType;

import java.time.LocalDate;
import java.util.Map;

public class CreditWithoutHolidays extends Credit{

    public CreditWithoutHolidays(float loan, String currency, float annualPercent, LocalDate startDate, LocalDate endDate, OperationPeriodType paymentType) {
        super(loan, currency, annualPercent, startDate, endDate, paymentType);
    }

    public CreditWithoutHolidays (JsonObject jsonObject){
        super(jsonObject);
    }

    @Override
    public JsonObject getJsonObject() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("operation", "Credit");
        jsonObject.addProperty("type", "WithoutHolidays");
        JsonObject superJsonObject = super.getJsonObject();
        for (Map.Entry<String, JsonElement> entry : superJsonObject.entrySet()) {
            jsonObject.add(entry.getKey(), entry.getValue());
        }
        return jsonObject;
    }

}
