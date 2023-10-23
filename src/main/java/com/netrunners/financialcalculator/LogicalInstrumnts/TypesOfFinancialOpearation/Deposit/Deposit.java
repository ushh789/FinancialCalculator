package com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation.Deposit;

import java.time.LocalDate;

public abstract class Deposit {
    protected float investment;
    protected float annualPercent;
    protected LocalDate startDate;
    protected LocalDate endDate;
    protected int withdrawalOption;
    protected boolean earlyWithdrawal;
    protected LocalDate earlyWithdrawalDate;
    protected abstract float countProfit();
    public Deposit(int investment, float annualPercent, LocalDate startDate, LocalDate endDate, LocalDate earlyWithdrawalDate){
        this.investment = investment;
        this.annualPercent = annualPercent;
        this.startDate = startDate;
        this.endDate = endDate;
        this.earlyWithdrawalDate = earlyWithdrawalDate;
    }
    public Deposit(int investment, float annualPercent, LocalDate startDate, LocalDate endDate){
        this.investment = investment;
        this.annualPercent = annualPercent;
        this.startDate = startDate;
        this.endDate = endDate;
    }

}