package com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation.Credit;

import java.time.LocalDate;

public class Credit {
    protected float loan;
    protected float annualPercent;
    protected LocalDate startDate;
    protected LocalDate endDate;
    public Credit(float loan, float annualPercent, LocalDate startDate, LocalDate endDate){
        this.loan = loan;
        this.annualPercent = annualPercent;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    protected float countLoan(){
        return 0;
    }

}
