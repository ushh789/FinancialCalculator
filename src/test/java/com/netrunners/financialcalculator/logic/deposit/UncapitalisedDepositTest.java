package com.netrunners.financialcalculator.logic.deposit;

import com.google.gson.JsonObject;
import com.netrunners.financialcalculator.logic.entity.OperationPeriodType;
import com.netrunners.financialcalculator.logic.entity.deposit.UncapitalisedDeposit;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UncapitalisedDepositTest {

    @Test
    void testFieldsAndGetters() {
        LocalDate start = LocalDate.of(2024, 2, 1);
        LocalDate end = LocalDate.of(2025, 2, 1);
        LocalDate early = LocalDate.of(2024, 7, 1);
        UncapitalisedDeposit deposit = new UncapitalisedDeposit(1200f, "$", 7f, start, end, true, early, OperationPeriodType.QUARTERS);

        assertEquals(1200f, deposit.getInvestment());
        assertEquals("$", deposit.getCurrency());
        assertEquals(7f, deposit.getAnnualPercent());
        assertEquals(start, deposit.getStartDate());
        assertEquals(end, deposit.getEndDate());
        assertEquals(OperationPeriodType.QUARTERS, deposit.getWithdrawalOption());
        assertTrue(deposit.isEarlyWithdrawal());
        assertEquals(early, deposit.getEarlyWithdrawalDate());
    }

    @Test
    void testJsonSerializationAndDeserialization() {
        LocalDate start = LocalDate.of(2024, 2, 1);
        LocalDate end = LocalDate.of(2025, 2, 1);
        LocalDate early = LocalDate.of(2024, 7, 1);
        UncapitalisedDeposit deposit = new UncapitalisedDeposit(1200f, "$", 7f, start, end, true, early, OperationPeriodType.QUARTERS);

        JsonObject json = deposit.getJsonObject();
        assertEquals("Deposit", json.get("operation").getAsString());
        assertEquals("Uncapitalized", json.get("type").getAsString());
        assertEquals(1200f, json.get("investment").getAsFloat());
        assertEquals(7f, json.get("annualPercent").getAsFloat());
        assertEquals("$", json.get("currency").getAsString());
        assertEquals("2024-02-01", json.get("startDate").getAsString());
        assertEquals("2025-02-01", json.get("endDate").getAsString());
        assertEquals("QUARTERS", json.get("withdrawalOption").getAsString());
        assertTrue(json.get("earlyWithdrawal").getAsBoolean());
        assertEquals("2024-07-01", json.get("earlyWithdrawalDate").getAsString());

        // Тест конструктора з JsonObject
        UncapitalisedDeposit deposit2 = new UncapitalisedDeposit(json);
        assertEquals(1200f, deposit2.getInvestment());
    }
}