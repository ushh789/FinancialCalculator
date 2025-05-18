package com.netrunners.financialcalculator.logic.credit;

import com.google.gson.JsonObject;
import com.netrunners.financialcalculator.logic.entity.OperationPeriodType;
import com.netrunners.financialcalculator.logic.entity.credit.CreditWithoutHolidays;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CreditWithoutHolidaysTest {

    @Test
    void testFieldsAndGettersSetters() {
        LocalDate start = LocalDate.of(2024, 1, 1);
        LocalDate end = LocalDate.of(2025, 1, 1);
        CreditWithoutHolidays credit = new CreditWithoutHolidays(2000f, "$", 10f, start, end, OperationPeriodType.QUARTERS);

        assertEquals(2000f, credit.getLoan());
        assertEquals("$", credit.getCurrency());
        assertEquals(10f, credit.getAnnualPercent());
        assertEquals(start, credit.getStartDate());
        assertEquals(end, credit.getEndDate());
        assertEquals(OperationPeriodType.QUARTERS, credit.getPaymentType());

        credit.setLoan(1500f);
        assertEquals(1500f, credit.getLoan());
    }

    @Test
    void testJsonSerializationAndDeserialization() {
        LocalDate start = LocalDate.of(2024, 1, 1);
        LocalDate end = LocalDate.of(2025, 1, 1);
        CreditWithoutHolidays credit = new CreditWithoutHolidays(2000f, "$", 10f, start, end, OperationPeriodType.QUARTERS);

        JsonObject json = credit.getJsonObject();
        assertEquals("Credit", json.get("operation").getAsString());
        assertEquals("WithoutHolidays", json.get("type").getAsString());
        assertEquals(2000f, json.get("loan").getAsFloat());
        assertEquals(10f, json.get("annualPercent").getAsFloat());
        assertEquals("$", json.get("currency").getAsString());
        assertEquals("2024-01-01", json.get("startDate").getAsString());
        assertEquals("2025-01-01", json.get("endDate").getAsString());
        assertEquals("QUARTERS", json.get("paymentType").getAsString());

        // Тест конструктора з JsonObject
        CreditWithoutHolidays credit2 = new CreditWithoutHolidays(json);
        assertEquals(2000f, credit2.getLoan());
        assertEquals("$", credit2.getCurrency());
        assertEquals(10f, credit2.getAnnualPercent());
        assertEquals(start, credit2.getStartDate());
        assertEquals(end, credit2.getEndDate());
        assertEquals(OperationPeriodType.QUARTERS, credit2.getPaymentType());
    }
}