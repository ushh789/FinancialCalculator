package com.netrunners.financialcalculator.logic.time;

import com.netrunners.financialcalculator.logic.entity.credit.Credit;
import com.netrunners.financialcalculator.logic.entity.deposit.Deposit;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


public class DateTimeUtils {

    public static int countDaysBetweenDates(LocalDate startDate, LocalDate endDate) {
        return (int) ChronoUnit.DAYS.between(startDate, endDate);
    }

    public static int countDaysToNextPeriod(Credit credit, LocalDate date) {
        switch (credit.getPaymentType()) {
            case MONTHS -> {
                if (date.plusMonths(1).withDayOfMonth(1).isAfter(credit.getEndDate())) {
                    return countDaysBetweenDates(date, credit.getEndDate());
                } else {
                    return countDaysBetweenDates(date, date.plusMonths(1).withDayOfMonth(1));
                }
            }
            case QUARTERS -> {
                if (date.plusMonths(3).withDayOfMonth(1).isAfter(credit.getEndDate())) {
                    return countDaysBetweenDates(date, credit.getEndDate());
                } else {
                    return countDaysBetweenDates(date, date.plusMonths(3).withDayOfMonth(1));
                }
            }
            case YEARS -> {
                if (date.plusYears(1).withMonth(1).withDayOfMonth(1).isAfter(credit.getEndDate())) {
                    return countDaysBetweenDates(date, credit.getEndDate());
                } else {
                    return countDaysBetweenDates(date, date.plusYears(1).withMonth(1).withDayOfMonth(1));
                }
            }
            case END_OF_TERM -> {
                return countDaysBetweenDates(date, credit.getEndDate());
            }
        }
        return 0;
    }

    public static int countDaysToNextPeriod(Deposit deposit, LocalDate tempDate, LocalDate endDate) {
        switch (deposit.getWithdrawalOption()) {
            case MONTHS -> {
                if (tempDate.plusMonths(1).withDayOfMonth(1).isAfter(endDate)) {
                    return countDaysBetweenDates(tempDate, endDate);
                } else {
                    return countDaysBetweenDates(tempDate, tempDate.plusMonths(1).withDayOfMonth(1));
                }
            }
            case QUARTERS -> {
                if (tempDate.plusMonths(3).withDayOfMonth(1).isAfter(endDate)) {
                    return countDaysBetweenDates(tempDate, endDate);
                } else {
                    return countDaysBetweenDates(tempDate, tempDate.plusMonths(3).withDayOfMonth(1));
                }
            }
            case YEARS -> {
                if (tempDate.plusYears(1).withMonth(1).withDayOfMonth(1).isAfter(endDate)) {
                    return countDaysBetweenDates(tempDate, endDate);
                } else {
                    return countDaysBetweenDates(tempDate, tempDate.plusYears(1).withMonth(1).withDayOfMonth(1));
                }
            }
            case END_OF_TERM -> {
                return countDaysBetweenDates(tempDate, endDate);
            }
        }
        return 0;
    }

    public static boolean isDateBetweenDates(LocalDate dateToCheck, LocalDate start, LocalDate end) {
        return !dateToCheck.isBefore(start) && !dateToCheck.isAfter(end);
    }

}
