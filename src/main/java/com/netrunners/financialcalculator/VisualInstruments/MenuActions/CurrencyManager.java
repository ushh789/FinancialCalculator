package com.netrunners.financialcalculator.VisualInstruments.MenuActions;

public class CurrencyManager {
    private static CurrencyManager instance;
    private String currency;

    private CurrencyManager() {
        currency = "$";
    }

    public static CurrencyManager getInstance() {
        if (instance == null) {
            instance = new CurrencyManager();
        }
        return instance;
    }

    public String getCurrency() {
        return currency;
    }

    public void changeCurrency(String currency) {
        this.currency = currency;
    }
}
