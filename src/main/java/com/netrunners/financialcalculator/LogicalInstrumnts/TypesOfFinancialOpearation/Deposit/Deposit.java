package com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation.Deposit;

import java.time.LocalDate;

public abstract class Deposit {
    protected float investment;
    protected float annualPercent;
    protected char currency;
    protected LocalDate startDate;
    protected LocalDate endDate;
}
