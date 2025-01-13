package com.netrunners.financialcalculator.logic.entity;

import java.time.LocalDate;

public interface ResultTableSender {
    void sendToResultTable();
    String getCurrency();
    LocalDate getStartDate();
    LocalDate getEndDate();
    float getBody();
}
