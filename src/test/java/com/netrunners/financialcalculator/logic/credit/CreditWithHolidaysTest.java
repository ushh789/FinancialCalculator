package com.netrunners.financialcalculator.logic.credit;

import com.google.gson.JsonObject;
import com.netrunners.financialcalculator.logic.entity.OperationPeriodType;
import com.netrunners.financialcalculator.logic.entity.credit.CreditWithHolidays;
import com.netrunners.financialcalculator.logic.time.DateTimeUtils;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CreditWithHolidaysTest {

    @Test
    void testFieldsAndGetters() {
        LocalDate start = LocalDate.of(2024, 1, 1);
        LocalDate end = LocalDate.of(2025, 1, 1);
        LocalDate holStart = LocalDate.of(2024, 6, 1);
        LocalDate holEnd = LocalDate.of(2024, 6, 30);
        CreditWithHolidays credit = new CreditWithHolidays(3000f, "$", 15f, start, end, OperationPeriodType.YEARS, holStart, holEnd);

        assertEquals(3000f, credit.getLoan());
        assertEquals("$", credit.getCurrency());
        assertEquals(15f, credit.getAnnualPercent());
        assertEquals(start, credit.getStartDate());
        assertEquals(end, credit.getEndDate());
        assertEquals(OperationPeriodType.YEARS, credit.getPaymentType());
        assertEquals(holStart, credit.getHolidaysStart());
        assertEquals(holEnd, credit.getHolidaysEnd());
        assertTrue(credit.countHolidaysDuration() > 0);
        assertEquals(3000f / (DateTimeUtils.countDaysBetweenDates(start, end) - credit.countHolidaysDuration()), credit.countCreditBodyPerDay());

    }

    @Test
    void testJsonSerializationAndDeserialization() {
        LocalDate start = LocalDate.of(2024, 1, 1);
        LocalDate end = LocalDate.of(2025, 1, 1);
        LocalDate holStart = LocalDate.of(2024, 6, 1);
        LocalDate holEnd = LocalDate.of(2024, 6, 30);
        CreditWithHolidays credit = new CreditWithHolidays(3000f, "$", 15f, start, end, OperationPeriodType.YEARS, holStart, holEnd);

        JsonObject json = credit.getJsonObject();
        assertEquals("Credit", json.get("operation").getAsString());
        assertEquals("WithHolidays", json.get("type").getAsString());
        assertEquals(3000f, json.get("loan").getAsFloat());
        assertEquals(15f, json.get("annualPercent").getAsFloat());
        assertEquals("$", json.get("currency").getAsString());
        assertEquals("2024-01-01", json.get("startDate").getAsString());
        assertEquals("2025-01-01", json.get("endDate").getAsString());
        assertEquals("YEARS", json.get("paymentType").getAsString());
        assertEquals("2024-06-01", json.get("holidaysStart").getAsString());
        assertEquals("2024-06-30", json.get("holidaysEnd").getAsString());

        // Тест конструктора з JsonObject
        CreditWithHolidays credit2 = new CreditWithHolidays(json);
        assertEquals(3000f, credit2.getLoan());
        assertEquals("$", credit2.getCurrency());
        assertEquals(15f, credit2.getAnnualPercent());
        assertEquals(start, credit2.getStartDate());
        assertEquals(end, credit2.getEndDate());
        assertEquals(OperationPeriodType.YEARS, credit2.getPaymentType());
        assertEquals(holStart, credit2.getHolidaysStart());
        assertEquals(holEnd, credit2.getHolidaysEnd());
    }

    @Test
    void testCountHolidaysDuration() {
        LocalDate holStart = LocalDate.of(2024, 6, 1);
        LocalDate holEnd = LocalDate.of(2024, 6, 30);
        CreditWithHolidays credit = new CreditWithHolidays(1000f, "$", 12f,
                LocalDate.of(2024, 1, 1), LocalDate.of(2025, 1, 1), OperationPeriodType.MONTHS, holStart, holEnd);
        assertEquals(29, credit.countHolidaysDuration());
    }
}