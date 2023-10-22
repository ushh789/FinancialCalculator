package com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation.Deposit;

import java.time.LocalDate;

public abstract class Deposit {
    protected float investment;
    protected float annualPercent;
    protected LocalDate startDate;
    protected LocalDate endDate;
    protected int wirthdrawalOption;
    protected boolean earlyWirtdrawal;
    protected abstract float countProfit();

}
