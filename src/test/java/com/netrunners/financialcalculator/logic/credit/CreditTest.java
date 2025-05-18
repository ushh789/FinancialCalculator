package com.netrunners.financialcalculator.logic.credit;

import com.google.gson.JsonObject;
import com.netrunners.financialcalculator.logic.entity.OperationPeriodType;
import com.netrunners.financialcalculator.logic.entity.credit.Credit;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CreditTest {

    static class TestCredit extends Credit {
        public TestCredit(float loan, String currency, float annualPercent, LocalDate startDate, LocalDate endDate, OperationPeriodType paymentType) {
            super(loan, currency, annualPercent, startDate, endDate, paymentType);
        }
        public TestCredit(JsonObject jsonObject) { super(jsonObject); }
    }

    @Test
    void testFieldsAndGettersSetters() {
        LocalDate start = LocalDate.of(2024, 1, 1);
        LocalDate end = LocalDate.of(2025, 1, 1);
        Credit credit = new TestCredit(1000f, "$", 12f, start, end, OperationPeriodType.MONTHS);

        assertEquals(1000f, credit.getLoan());
        assertEquals("$", credit.getCurrency());
        assertEquals(12f, credit.getAnnualPercent());
        assertEquals(start, credit.getStartDate());
        assertEquals(end, credit.getEndDate());
        assertEquals(OperationPeriodType.MONTHS, credit.getPaymentType());
        assertEquals(1000f ,credit.getBody());

        credit.setLoan(500f);
        assertEquals(500f, credit.getLoan());
    }

    @Test
    void testCountLoan() {
        Credit credit = new TestCredit(1000f, "$", 12f,
                LocalDate.of(2024, 1, 1), LocalDate.of(2025, 1, 1), OperationPeriodType.MONTHS);
        float expected = 1000f * (1f / 365f) * (12f / 100f);
        assertEquals(expected, credit.countLoan(), 1e-6);
    }

    @Test
    void testCountCreditBodyPerDay() {
        Credit credit = new TestCredit(100f, "$", 12f,
                LocalDate.of(2024, 1, 1), LocalDate.of(2024, 2, 1), OperationPeriodType.MONTHS);
        int contractDuration = (int) (LocalDate.of(2024, 2, 1).toEpochDay() - LocalDate.of(2024, 1, 1).toEpochDay());
        assertEquals(100f / contractDuration, credit.countCreditBodyPerDay(), 1e-6);
    }

    @Test
    void testJsonSerializationAndDeserialization() {
        LocalDate start = LocalDate.of(2024, 1, 1);
        LocalDate end = LocalDate.of(2025, 1, 1);
        Credit credit = new TestCredit(1000f, "$", 12f, start, end, OperationPeriodType.MONTHS);

        JsonObject json = credit.getJsonObject();
        assertEquals(1000f, json.get("loan").getAsFloat());
        assertEquals(12f, json.get("annualPercent").getAsFloat());
        assertEquals("$", json.get("currency").getAsString());
        assertEquals("2024-01-01", json.get("startDate").getAsString());
        assertEquals("2025-01-01", json.get("endDate").getAsString());
        assertEquals("MONTHS", json.get("paymentType").getAsString());

        // Тест конструктора з JsonObject
        Credit credit2 = new TestCredit(json);
        assertEquals(1000f, credit2.getLoan());
        assertEquals("$", credit2.getCurrency());
        assertEquals(12f, credit2.getAnnualPercent());
        assertEquals(start, credit2.getStartDate());
        assertEquals(end, credit2.getEndDate());
        assertEquals(OperationPeriodType.MONTHS, credit2.getPaymentType());
    }
}