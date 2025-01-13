package com.netrunners.financialcalculator.logic.entity;

public enum OperationPeriodType {
    MONTHS("Months"),
    QUARTERS("Quarters"),
    YEARS("Years"),
    END_OF_TERM("EndofTerm");

    private final String key;

    OperationPeriodType(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}
