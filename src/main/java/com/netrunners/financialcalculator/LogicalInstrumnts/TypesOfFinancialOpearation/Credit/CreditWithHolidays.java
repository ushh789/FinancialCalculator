package com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation.Credit;

import java.time.LocalDate;

public class CreditWithHolidays extends Credit{
    private LocalDate holidaysStart;
    private LocalDate holidaysEnd;
    public CreditWithHolidays(float loan, float annualPercent, LocalDate startDate, LocalDate endDate, LocalDate holidaysStart, LocalDate holidaysEnd){
        super(loan, annualPercent, startDate, endDate);
        this.holidaysStart = holidaysStart;
        this.holidaysEnd = holidaysEnd;
    }

    @Override
    protected float countLoan() {
        return 0;
    }
}
