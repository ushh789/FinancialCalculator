package com.netrunners.financialcalculator.logic.entity.deposit;

import com.google.gson.JsonObject;
import com.netrunners.financialcalculator.logic.files.Savable;
import com.netrunners.financialcalculator.logic.entity.OperationPeriodType;
import com.netrunners.financialcalculator.logic.entity.ResultTableSender;
import com.netrunners.financialcalculator.ui.FilesActions;
import com.netrunners.financialcalculator.ui.LanguageManager;
import com.netrunners.financialcalculator.ui.WindowsOpener;
import java.time.LocalDate;

public abstract class Deposit implements Savable, ResultTableSender {
    protected float investment;
    protected float annualPercent;
    protected String currency;
    protected LocalDate startDate;
    protected LocalDate endDate;
    protected OperationPeriodType withdrawalOption;
    protected boolean earlyWithdrawal;
    protected LocalDate earlyWithdrawalDate;


    public float countProfit() {
        return investment * (1f / 365f) * (annualPercent / 100f);
    }

    protected Deposit(float investment, String currency, float annualPercent, LocalDate startDate, LocalDate endDate, boolean earlyWithdrawal, LocalDate earlyWithdrawalDate, OperationPeriodType withdrawalOption) {
        this.investment = investment;
        this.currency = currency;
        this.annualPercent = annualPercent;
        this.startDate = startDate;
        this.endDate = endDate;
        this.earlyWithdrawal = earlyWithdrawal;
        this.earlyWithdrawalDate = earlyWithdrawalDate;
        this.withdrawalOption = withdrawalOption;
    }

    protected Deposit(float investment, String currency, float annualPercent, LocalDate startDate, LocalDate endDate, boolean earlyWithdrawal, OperationPeriodType withdrawalOption) {
        this.investment = investment;
        this.currency = currency;
        this.annualPercent = annualPercent;
        this.startDate = startDate;
        this.endDate = endDate;
        this.earlyWithdrawal = earlyWithdrawal;
        this.withdrawalOption = withdrawalOption;
    }

    protected Deposit(JsonObject jsonObject) {
        this.investment = jsonObject.get("investment").getAsFloat();
        this.annualPercent = jsonObject.get("annualPercent").getAsFloat();
        this.currency = jsonObject.get("currency").getAsString();
        this.startDate = LocalDate.parse(jsonObject.get("startDate").getAsString());
        this.endDate = LocalDate.parse(jsonObject.get("endDate").getAsString());
        this.withdrawalOption = OperationPeriodType.valueOf(jsonObject.get("withdrawalOption").getAsString());
        this.earlyWithdrawal = jsonObject.get("earlyWithdrawal").getAsBoolean();
        if (this.earlyWithdrawal) {
            this.earlyWithdrawalDate = LocalDate.parse(jsonObject.get("earlyWithdrawalDate").getAsString());
        }
    }
    @Override
    public JsonObject getJsonObject() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("investment", this.investment);
        jsonObject.addProperty("annualPercent", this.annualPercent);
        jsonObject.addProperty("currency", this.currency);
        jsonObject.addProperty("startDate", this.startDate.toString());
        jsonObject.addProperty("endDate", this.endDate.toString());
        jsonObject.addProperty("withdrawalOption", this.withdrawalOption.getKey());
        if (this.earlyWithdrawal) {
            jsonObject.addProperty("earlyWithdrawalDate", this.earlyWithdrawalDate.toString());
        } else {
            jsonObject.addProperty("earlyWithdrawalDate", "None");
        }
        jsonObject.addProperty("earlyWithdrawal", this.earlyWithdrawal);
        return jsonObject;
    }

    @Override
    public void save() {
        FilesActions.saveFileToJson(this);
    }

    public String getNameOfWithdrawalType() {
        if (withdrawalOption == null) {
            return LanguageManager.getInstance().getStringBinding("None").get();
        } else {
            return withdrawalOption.getKey();
        }
    }

    public float getInvestment() {
        return investment;
    }

    public void setInvestment(float investment) {
        this.investment = investment;
    }

    public float getAnnualPercent() {
        return annualPercent;
    }

    public OperationPeriodType getWithdrawalOption() {
        return withdrawalOption;
    }

    public boolean isEarlyWithdrawal() {
        return earlyWithdrawal;
    }

    public LocalDate getEarlyWithdrawalDate() {
        return earlyWithdrawalDate;
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
        return investment;
    }
}