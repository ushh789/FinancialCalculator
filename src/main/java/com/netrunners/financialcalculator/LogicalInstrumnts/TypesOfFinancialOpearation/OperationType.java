package com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation;

public enum OperationType {
    MONTHS("Months"),
    QUARTERS("Quarters"),
    YEARS("Years"),
    END_OF_TERM("EndofTerm");

    private final String key;

    OperationType(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}
