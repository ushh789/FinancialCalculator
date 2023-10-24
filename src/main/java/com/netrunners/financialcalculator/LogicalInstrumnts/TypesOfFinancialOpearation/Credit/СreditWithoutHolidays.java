package com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation.Credit;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.netrunners.financialcalculator.LogicalInstrumnts.FileInstruments.Savable;

import java.time.LocalDate;
import java.util.Map;

public class СreditWithoutHolidays extends Credit implements Savable{
    public СreditWithoutHolidays(float loan, String currency, float annualPercent, LocalDate startDate, LocalDate endDate, int paymentType) {
        super(loan, currency, annualPercent, startDate, endDate, paymentType);
    }

    @Override
    protected JsonObject getJsonObject() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("operation", "Credit");
        jsonObject.addProperty("type", "WithoutHolidays");
        JsonObject superJsonObject = super.getJsonObject();
        for (Map.Entry<String, JsonElement> entry : superJsonObject.entrySet()) {
            jsonObject.add(entry.getKey(), entry.getValue());
        }
        return jsonObject;
    }

    @Override
    public void save() {
        super.save();
    }

    @Override
    public void open() {
        super.open();
    }
}
