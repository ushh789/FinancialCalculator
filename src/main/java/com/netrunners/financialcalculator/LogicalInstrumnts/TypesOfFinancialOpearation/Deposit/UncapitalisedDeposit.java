package com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation.Deposit;

import java.time.LocalDate;

public class UncapitalisedDeposit extends Deposit{
    public UncapitalisedDeposit(int investment, float annualPercent, LocalDate startDate, LocalDate endDate, LocalDate earlyWithdrawalDate) {
        super(investment, annualPercent, startDate, endDate, earlyWithdrawalDate);
    }

    public UncapitalisedDeposit(int investment, float annualPercent, LocalDate startDate, LocalDate endDate) {
        super(investment, annualPercent, startDate, endDate);
    }

    @Override
    protected float countProfit() {
        return 0;
    }
}
