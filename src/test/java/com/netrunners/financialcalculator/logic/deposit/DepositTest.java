package com.netrunners.financialcalculator.logic.deposit;

import com.google.gson.JsonObject;
import com.netrunners.financialcalculator.logic.entity.OperationPeriodType;
import com.netrunners.financialcalculator.logic.entity.deposit.Deposit;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DepositTest {

    static class TestDeposit extends Deposit {
        public TestDeposit(float investment, String currency, float annualPercent, LocalDate startDate, LocalDate endDate, boolean earlyWithdrawal, LocalDate earlyWithdrawalDate, OperationPeriodType withdrawalOption) {
            super(investment, currency, annualPercent, startDate, endDate, earlyWithdrawal, earlyWithdrawalDate, withdrawalOption);
        }
        public TestDeposit(JsonObject jsonObject) { super(jsonObject); }
    }

    @Test
    void testFieldsAndGettersSetters() {
        LocalDate start = LocalDate.of(2024, 1, 1);
        LocalDate end = LocalDate.of(2025, 1, 1);
        LocalDate early = LocalDate.of(2024, 6, 1);
        Deposit deposit = new TestDeposit(1000f, "$", 10f, start, end, true, early, OperationPeriodType.MONTHS);

        assertEquals(1000f, deposit.getInvestment());
        assertEquals("$", deposit.getCurrency());
        assertEquals(10f, deposit.getAnnualPercent());
        assertEquals(start, deposit.getStartDate());
        assertEquals(end, deposit.getEndDate());
        assertEquals(OperationPeriodType.MONTHS, deposit.getWithdrawalOption());
        assertTrue(deposit.isEarlyWithdrawal());
        assertEquals(early, deposit.getEarlyWithdrawalDate());
        assertEquals(1000f, deposit.getBody());

        deposit.setInvestment(2000f);
        assertEquals(2000f, deposit.getInvestment());
    }

    @Test
    void testCountProfit() {
        Deposit deposit = new TestDeposit(1000f, "$", 10f,
                LocalDate.of(2024, 1, 1), LocalDate.of(2025, 1, 1), false, null, OperationPeriodType.MONTHS);
        float expected = 1000f * (1f / 365f) * (10f / 100f);
        assertEquals(expected, deposit.countProfit(), 1e-6);
    }

    @Test
    void testJsonSerializationAndDeserialization() {
        LocalDate start = LocalDate.of(2024, 1, 1);
        LocalDate end = LocalDate.of(2025, 1, 1);
        LocalDate early = LocalDate.of(2024, 6, 1);
        Deposit deposit = new TestDeposit(1000f, "$", 10f, start, end, true, early, OperationPeriodType.MONTHS);

        JsonObject json = deposit.getJsonObject();
        assertEquals(1000f, json.get("investment").getAsFloat());
        assertEquals(10f, json.get("annualPercent").getAsFloat());
        assertEquals("$", json.get("currency").getAsString());
        assertEquals("2024-01-01", json.get("startDate").getAsString());
        assertEquals("2025-01-01", json.get("endDate").getAsString());
        assertEquals("MONTHS", json.get("withdrawalOption").getAsString());
        assertTrue(json.get("earlyWithdrawal").getAsBoolean());
        assertEquals("2024-06-01", json.get("earlyWithdrawalDate").getAsString());

        // Тест конструктора з JsonObject
        Deposit deposit2 = new TestDeposit(json);
        assertEquals(1000f, deposit2.getInvestment());
        assertEquals("$", deposit2.getCurrency());
        assertEquals(10f, deposit2.getAnnualPercent());
        assertEquals(start, deposit2.getStartDate());
        assertEquals(end, deposit2.getEndDate());
        assertEquals(OperationPeriodType.MONTHS, deposit2.getWithdrawalOption());
        assertTrue(deposit2.isEarlyWithdrawal());
        assertEquals(early, deposit2.getEarlyWithdrawalDate());
    }
}