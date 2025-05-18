package com.netrunners.financialcalculator.logic.entity.credit;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.netrunners.financialcalculator.logic.time.DateTimeUtils;
import com.netrunners.financialcalculator.logic.entity.OperationPeriodType;

import java.time.LocalDate;
import java.util.Map;

public class CreditWithHolidays extends Credit{
    private final LocalDate holidaysStart;
    private final LocalDate holidaysEnd;

    public CreditWithHolidays(float loan, String currency, float annualPercent, LocalDate startDate, LocalDate endDate, OperationPeriodType paymentType, LocalDate holidaysStart, LocalDate holidaysEnd) {
        super(loan, currency, annualPercent, startDate, endDate, paymentType);
        this.holidaysStart = holidaysStart;
        this.holidaysEnd = holidaysEnd;
    }

    public CreditWithHolidays (JsonObject jsonObject){
        super(jsonObject);
        this.holidaysStart = LocalDate.parse(jsonObject.get("holidaysStart").getAsString());
        this.holidaysEnd = LocalDate.parse(jsonObject.get("holidaysEnd").getAsString());
    }

    @Override
    public float countCreditBodyPerDay() {
        return this.loan / (this.contractDuration - this.countHolidaysDuration());
    }


    @Override
    public JsonObject getJsonObject() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("operation", "Credit");
        jsonObject.addProperty("type", "WithHolidays");
        JsonObject superJsonObject = super.getJsonObject();
        for (Map.Entry<String, JsonElement> entry : superJsonObject.entrySet()) {
            jsonObject.add(entry.getKey(), entry.getValue());
        }
        jsonObject.addProperty("holidaysStart", holidaysStart.toString());
        jsonObject.addProperty("holidaysEnd", holidaysEnd.toString());
        return jsonObject;
    }

    public LocalDate getHolidaysStart() {
        return holidaysStart;
    }

    public LocalDate getHolidaysEnd() {
        return holidaysEnd;
    }

    public int countHolidaysDuration() {
        return DateTimeUtils.countDaysBetweenDates(holidaysStart, holidaysEnd);
    }

}
