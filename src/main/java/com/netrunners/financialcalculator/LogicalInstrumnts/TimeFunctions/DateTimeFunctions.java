package com.netrunners.financialcalculator.LogicalInstrumnts.TimeFunctions;

import com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation.Credit.Credit;
import com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation.Deposit.Deposit;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


public class DateTimeFunctions {

    public static int countDaysBetweenDates(LocalDate startDate, LocalDate endDate) {
        return (int) ChronoUnit.DAYS.between(startDate, endDate);
    }

    public static int countDaysToNextPeriod(Credit credit, LocalDate date) {
        switch (credit.getPaymentType()) {
            case 1 -> {
                if (date.plusMonths(1).withDayOfMonth(1).isAfter(credit.getEndDate())) {
                    return countDaysBetweenDates(date, credit.getEndDate());
                } else {
                    return countDaysBetweenDates(date, date.plusMonths(1).withDayOfMonth(1));
                }
            }
            case 2 -> {
                if (date.plusMonths(3).withDayOfMonth(1).isAfter(credit.getEndDate())) {
                    return countDaysBetweenDates(date, credit.getEndDate());
                } else {
                    return countDaysBetweenDates(date, date.plusMonths(3).withDayOfMonth(1));
                }
            }
            case 3 -> {
                if (date.plusYears(1).withMonth(1).withDayOfMonth(1).isAfter(credit.getEndDate())) {
                    return countDaysBetweenDates(date, credit.getEndDate());
                } else {
                    return countDaysBetweenDates(date, date.plusYears(1).withMonth(1).withDayOfMonth(1));
                }
            }
            case 4 -> {
                return countDaysBetweenDates(date, credit.getEndDate());
            }
        }
        return 0;
    }

    public static int countDaysToNextPeriod(Deposit deposit, LocalDate tempDate, LocalDate endDate) {
        switch (deposit.getWithdrawalOption()) {
            case 1 -> {
                if (tempDate.plusMonths(1).withDayOfMonth(1).isAfter(endDate)) {
                    return countDaysBetweenDates(tempDate, endDate);
                } else {
                    return countDaysBetweenDates(tempDate, tempDate.plusMonths(1).withDayOfMonth(1));
                }
            }
            case 2 -> {
                if (tempDate.plusMonths(3).withDayOfMonth(1).isAfter(endDate)) {
                    return countDaysBetweenDates(tempDate, endDate);
                } else {
                    return countDaysBetweenDates(tempDate, tempDate.plusMonths(3).withDayOfMonth(1));
                }
            }
            case 3 -> {
                if (tempDate.plusYears(1).withMonth(1).withDayOfMonth(1).isAfter(endDate)) {
                    return countDaysBetweenDates(tempDate, endDate);
                } else {
                    return countDaysBetweenDates(tempDate, tempDate.plusYears(1).withMonth(1).withDayOfMonth(1));
                }
            }
            case 4 -> {
                return countDaysBetweenDates(tempDate, endDate);
            }
        }
        return 0;
    }

    public static boolean isDateBetweenDates(LocalDate dateToCheck, LocalDate start, LocalDate end) {
        return !dateToCheck.isBefore(start) && !dateToCheck.isAfter(end);
    }

}
