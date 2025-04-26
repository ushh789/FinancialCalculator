package com.netrunners.financialcalculator.logic.entity.credit;

import com.google.gson.JsonObject;
import com.netrunners.financialcalculator.logic.files.Savable;
import com.netrunners.financialcalculator.logic.time.DateTimeUtils;
import com.netrunners.financialcalculator.logic.entity.OperationPeriodType;
import com.netrunners.financialcalculator.logic.entity.ResultTableSender;
import com.netrunners.financialcalculator.ui.FilesActions;
import com.netrunners.financialcalculator.ui.LanguageManager;
import com.netrunners.financialcalculator.ui.WindowsOpener;
import java.time.LocalDate;

public class Credit implements Savable, ResultTableSender {
    protected float loan;
    protected String currency;
    protected float annualPercent;
    protected LocalDate startDate;
    protected LocalDate endDate;
    protected OperationPeriodType paymentType;
    protected int contractDuration;

    protected Credit(float loan, String currency, float annualPercent, LocalDate startDate, LocalDate endDate, OperationPeriodType paymentType) {
        this.loan = loan;
        this.currency = currency;
        this.annualPercent = annualPercent;
        this.startDate = startDate;
        this.endDate = endDate;
        this.paymentType = paymentType;
        this.contractDuration = DateTimeUtils.countDaysBetweenDates(startDate, endDate);
    }

    protected Credit(JsonObject jsonObject) {
        this.loan = jsonObject.get("loan").getAsFloat();
        this.annualPercent = jsonObject.get("annualPercent").getAsFloat();
        this.paymentType = OperationPeriodType.valueOf(jsonObject.get("paymentType").getAsString());
        this.currency = jsonObject.get("currency").getAsString();
        this.startDate = LocalDate.parse(jsonObject.get("startDate").getAsString());
        this.endDate = LocalDate.parse(jsonObject.get("endDate").getAsString());
    }

    public float countLoan() {
        return loan * (1f / 365f) * (annualPercent / 100f);
    }

    public float countCreditBodyPerDay() {
        return loan / contractDuration;
    }

    @Override
    public void save() {
        FilesActions.saveFileToJson(this);
    }

    @Override
    public JsonObject getJsonObject() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("loan", this.loan);
        jsonObject.addProperty("annualPercent", this.annualPercent);
        jsonObject.addProperty("currency", this.currency);
        jsonObject.addProperty("paymentType", this.paymentType.getKey());
        jsonObject.addProperty("startDate", this.startDate.toString());
        jsonObject.addProperty("endDate", this.endDate.toString());
        return jsonObject;
    }

    public float getLoan() {
        return loan;
    }

    public float getAnnualPercent() {
        return annualPercent;
    }

    public OperationPeriodType getPaymentType() {
        return paymentType;
    }

    public void setLoan(float loan) {
        this.loan = loan;
    }

    public String getNameOfPaymentType() {
        if (paymentType == null) {
            return LanguageManager.getInstance().getStringBinding("None").get();
        } else {
            return paymentType.getKey();
        }
    }

    @Override
    public void sendToResultTable() {
        WindowsOpener.openResultTable(this);
    }

    @Override
    public String getCurrency() {
        return currency;
    }

    @Override
    public LocalDate getStartDate() {
        return startDate;
    }

    @Override
    public LocalDate getEndDate() {
        return endDate;
    }

    @Override
    public float getBody() {
        return loan;
    }

}
