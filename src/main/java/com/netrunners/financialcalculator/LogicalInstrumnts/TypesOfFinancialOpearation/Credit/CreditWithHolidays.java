package com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation.Credit;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.netrunners.financialcalculator.LogicalInstrumnts.FileInstruments.Savable;
import com.netrunners.financialcalculator.LogicalInstrumnts.TimeFunctions.DateTimeFunctions;

import java.time.LocalDate;
import java.util.Map;

public class CreditWithHolidays extends Credit implements Savable {
    private LocalDate holidaysStart;
    private LocalDate holidaysEnd;

    public CreditWithHolidays(float loan, String currency, float annualPercent, LocalDate startDate, LocalDate endDate, int paymentType, LocalDate holidaysStart, LocalDate holidaysEnd) {
        super(loan, currency, annualPercent, startDate, endDate, paymentType);
        this.holidaysStart = holidaysStart;
        this.holidaysEnd = holidaysEnd;
    }

    @Override
    public float countLoan() {
        return super.countLoan();
    }

    @Override
    public float countCreditBodyPerDay(){
        return loan / (this.contractDuration - this.countHolidaysDuration());
    }

    @Override
    public void save() {
        super.save();
    }

    @Override
    protected JsonObject getJsonObject() {
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

    @Override
    public void open() {
        super.open();
    }

    public LocalDate getHolidaysStart() {
        return holidaysStart;
    }

    public LocalDate getHolidaysEnd() {
        return holidaysEnd;
    }

    public int countHolidaysDuration() {
        return DateTimeFunctions.countDaysBetweenDates(holidaysStart, holidaysEnd);
    }
    private int countDaysToHolidays(){
        return DateTimeFunctions.countDaysBetweenDates(super.startDate, holidaysStart);
    }

}
