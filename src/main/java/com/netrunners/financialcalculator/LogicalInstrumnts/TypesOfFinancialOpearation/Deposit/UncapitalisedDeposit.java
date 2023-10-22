package com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation.Deposit;

public class UncapitalisedDeposit extends Deposit{
    @Override
    protected float countProfit() {
        return 0;
    }
}
