package com.netrunners.financialcalculator.logic.deposit;

import com.google.gson.JsonObject;
import com.netrunners.financialcalculator.logic.entity.OperationPeriodType;
import com.netrunners.financialcalculator.logic.entity.deposit.CapitalisedDeposit;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CapitalisedDepositTest {

    @Test
    void testFieldsAndGetters() {
        LocalDate start = LocalDate.of(2024, 1, 1);
        LocalDate end = LocalDate.of(2025, 1, 1);
        LocalDate early = LocalDate.of(2024, 6, 1);
        CapitalisedDeposit deposit = new CapitalisedDeposit(1500f, "$", 8f, start, end, true, early, OperationPeriodType.MONTHS);

        assertEquals(1500f, deposit.getInvestment());
        assertEquals(1500f, deposit.getInitialInvestment());
        assertEquals("$", deposit.getCurrency());
        assertEquals(8f, deposit.getAnnualPercent());
        assertEquals(start, deposit.getStartDate());
        assertEquals(end, deposit.getEndDate());
        assertEquals(OperationPeriodType.MONTHS, deposit.getWithdrawalOption());
        assertTrue(deposit.isEarlyWithdrawal());
        assertEquals(early, deposit.getEarlyWithdrawalDate());
    }

    @Test
    void testJsonSerializationAndDeserialization() {
        LocalDate start = LocalDate.of(2024, 1, 1);
        LocalDate end = LocalDate.of(2025, 1, 1);
        LocalDate early = LocalDate.of(2024, 6, 1);
        CapitalisedDeposit deposit = new CapitalisedDeposit(1500f, "$", 8f, start, end, true, early, OperationPeriodType.MONTHS);

        JsonObject json = deposit.getJsonObject();
        assertEquals("Deposit", json.get("operation").getAsString());
        assertEquals("Capitalized", json.get("type").getAsString());
        assertEquals(1500f, json.get("initialInvestment").getAsFloat());
        assertEquals(1500f, json.get("investment").getAsFloat());
        assertEquals(8f, json.get("annualPercent").getAsFloat());
        assertEquals("$", json.get("currency").getAsString());
        assertEquals("2024-01-01", json.get("startDate").getAsString());
        assertEquals("2025-01-01", json.get("endDate").getAsString());
        assertEquals("MONTHS", json.get("withdrawalOption").getAsString());
        assertTrue(json.get("earlyWithdrawal").getAsBoolean());
        assertEquals("2024-06-01", json.get("earlyWithdrawalDate").getAsString());

        // Тест конструктора з JsonObject
        CapitalisedDeposit deposit2 = new CapitalisedDeposit(json);
        assertEquals(1500f, deposit2.getInitialInvestment());
        assertEquals(1500f, deposit2.getInvestment());
    }
}