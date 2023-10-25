package com.netrunners.financialcalculator.LogicalInstrumnts.FileInstruments;

public abstract interface Savable {
    void save(String filename);
    void open();
}
