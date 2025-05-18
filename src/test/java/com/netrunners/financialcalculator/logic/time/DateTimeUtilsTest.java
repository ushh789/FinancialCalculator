package com.netrunners.financialcalculator.logic.time;

import com.netrunners.financialcalculator.logic.entity.OperationPeriodType;
import com.netrunners.financialcalculator.logic.entity.credit.CreditWithoutHolidays;
import com.netrunners.financialcalculator.logic.entity.deposit.UncapitalisedDeposit;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DateTimeUtilsTest {

    @Test
    void testCountDaysBetweenDates() {
        LocalDate d1 = LocalDate.of(2024, 1, 1);
        LocalDate d2 = LocalDate.of(2024, 1, 10);
        assertEquals(9, DateTimeUtils.countDaysBetweenDates(d1, d2));
        assertEquals(0, DateTimeUtils.countDaysBetweenDates(d1, d1));
        assertEquals(-9, DateTimeUtils.countDaysBetweenDates(d2, d1));
    }

    @Test
    void testCountDaysToNextPeriod_Credit_Months() {
        CreditWithoutHolidays credit = new CreditWithoutHolidays(1000f, "$", 10f,
                LocalDate.of(2024, 1, 1), LocalDate.of(2024, 4, 1), OperationPeriodType.MONTHS);
        LocalDate date = LocalDate.of(2024, 1, 1);
        int days = DateTimeUtils.countDaysToNextPeriod(credit, date);
        assertEquals(31, days);
    }

    @Test
    void testCountDaysToNextPeriod_Credit_Quarters() {
        CreditWithoutHolidays credit = new CreditWithoutHolidays(1000f, "$", 10f,
                LocalDate.of(2024, 1, 1), LocalDate.of(2024, 7, 1), OperationPeriodType.QUARTERS);
        LocalDate date = LocalDate.of(2024, 1, 1);
        int days = DateTimeUtils.countDaysToNextPeriod(credit, date);
        assertEquals(91, days);
    }

    @Test
    void testCountDaysToNextPeriod_Credit_Years() {
        CreditWithoutHolidays credit = new CreditWithoutHolidays(1000f, "$", 10f,
                LocalDate.of(2024, 1, 1), LocalDate.of(2025, 1, 1), OperationPeriodType.YEARS);
        LocalDate date = LocalDate.of(2024, 1, 1);
        int days = DateTimeUtils.countDaysToNextPeriod(credit, date);
        assertEquals(366, days);
    }

    @Test
    void testCountDaysToNextPeriod_Credit_EndOfTerm() {
        CreditWithoutHolidays credit = new CreditWithoutHolidays(1000f, "$", 10f,
                LocalDate.of(2024, 1, 1), LocalDate.of(2024, 2, 1), OperationPeriodType.END_OF_TERM);
        LocalDate date = LocalDate.of(2024, 1, 1);
        int days = DateTimeUtils.countDaysToNextPeriod(credit, date);
        assertEquals(31, days);
    }

    @Test
    void testCountDaysToNextPeriod_Deposit_Months() {
        UncapitalisedDeposit deposit = new UncapitalisedDeposit(1000f, "$", 10f,
                LocalDate.of(2024, 1, 1), LocalDate.of(2024, 4, 1), false, OperationPeriodType.MONTHS);
        LocalDate date = LocalDate.of(2024, 1, 1);
        int days = DateTimeUtils.countDaysToNextPeriod(deposit, date, LocalDate.of(2024, 4, 1));
        assertEquals(31, days);
    }

    @Test
    void testCountDaysToNextPeriod_Deposit_Quarters() {
        UncapitalisedDeposit deposit = new UncapitalisedDeposit(1000f, "$", 10f,
                LocalDate.of(2024, 1, 1), LocalDate.of(2024, 7, 1), false, OperationPeriodType.QUARTERS);
        LocalDate date = LocalDate.of(2024, 1, 1);
        int days = DateTimeUtils.countDaysToNextPeriod(deposit, date, LocalDate.of(2024, 7, 1));
        assertEquals(91, days);
    }

    @Test
    void testCountDaysToNextPeriod_Deposit_Years() {
        UncapitalisedDeposit deposit = new UncapitalisedDeposit(1000f, "$", 10f,
                LocalDate.of(2024, 1, 1), LocalDate.of(2025, 1, 1), false, OperationPeriodType.YEARS);
        LocalDate date = LocalDate.of(2024, 1, 1);
        int days = DateTimeUtils.countDaysToNextPeriod(deposit, date, LocalDate.of(2025, 1, 1));
        assertEquals(366, days);
    }

    @Test
    void testCountDaysToNextPeriod_Deposit_EndOfTerm() {
        UncapitalisedDeposit deposit = new UncapitalisedDeposit(1000f, "$", 10f,
                LocalDate.of(2024, 1, 1), LocalDate.of(2024, 2, 1), false, OperationPeriodType.END_OF_TERM);
        LocalDate date = LocalDate.of(2024, 1, 1);
        int days = DateTimeUtils.countDaysToNextPeriod(deposit, date, LocalDate.of(2024, 2, 1));
        assertEquals(31, days);
    }

    @Test
    void testIsDateBetweenDates() {
        LocalDate start = LocalDate.of(2024, 1, 1);
        LocalDate end = LocalDate.of(2024, 1, 10);
        assertTrue(DateTimeUtils.isDateBetweenDates(LocalDate.of(2024, 1, 5), start, end));
        assertTrue(DateTimeUtils.isDateBetweenDates(start, start, end));
        assertTrue(DateTimeUtils.isDateBetweenDates(end, start, end));
        assertFalse(DateTimeUtils.isDateBetweenDates(LocalDate.of(2023, 12, 31), start, end));
        assertFalse(DateTimeUtils.isDateBetweenDates(LocalDate.of(2024, 1, 11), start, end));
    }
}