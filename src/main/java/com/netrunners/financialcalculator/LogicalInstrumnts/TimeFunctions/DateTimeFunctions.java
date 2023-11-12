package com.netrunners.financialcalculator.LogicalInstrumnts.TimeFunctions;

import com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation.Credit.Credit;
import com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation.Deposit.Deposit;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateTimeFunctions {
    public static LocalDate dateFormer(String dateString) {
        try {
            String dateRegex = "(\\d{1,2}).(\\d{1,2}).(\\d{4})";
            Pattern pattern = Pattern.compile(dateRegex);
            Matcher matcher = pattern.matcher(dateString);
            if (matcher.find()) {
                int day = Integer.parseInt(matcher.group(1));
                int month = Integer.parseInt(matcher.group(2));
                int year = Integer.parseInt(matcher.group(3));
                return LocalDate.of(year, month, day);
            } else {
                System.err.println("Помилка: " + "Неправильний формат дати!");
                System.exit(9);
                return null;
            }
        } catch (DateTimeException e) {
            System.err.println("Помилка: " + "Неправильний формат дати!");
            System.exit(9);
            return null;
        }
    }

    public static LocalDate getFirstDayOfNextMonth(LocalDate currentDate) {
        return currentDate.withDayOfMonth(1).plusMonths(1);
    }

    public static LocalDate getFirstDayOfNextQuarter(LocalDate currentDate) {
        int currentQuarter = (currentDate.getMonthValue() - 1) / 3 + 1;
        int nextQuarter = currentQuarter % 4 + 1;
        int yearToAdd = (nextQuarter == 1) ? 1 : 0;
        int monthToAdd = (nextQuarter - 1) * 3;
        return LocalDate.of(currentDate.getYear() + yearToAdd, monthToAdd + 1, 1);
    }

    public static LocalDate getFirstDayOfNextYear(LocalDate currentDate) {
        return LocalDate.of(currentDate.getYear() + 1, 1, 1);
    }

    public static int countDaysBetweenDates(LocalDate startDate, LocalDate endDate) {
        return (int) ChronoUnit.DAYS.between(startDate, endDate);
    }

    public static int countMonthsBetweenDates(LocalDate startDate, LocalDate endDate) {
        long monthsBetween = ChronoUnit.MONTHS.between(startDate, endDate);
        if (!startDate.isEqual(endDate) && (endDate.getMonth() == startDate.getMonth() || endDate.getDayOfMonth() > startDate.getDayOfMonth())) {
            monthsBetween++;
        }
        return (int) monthsBetween;
    }

    public static int countYearsBetweenDates(LocalDate startDate, LocalDate endDate) {
        long yearsBetween = ChronoUnit.YEARS.between(startDate, endDate);
        if (endDate.getMonthValue() > startDate.getMonthValue() ||
                (endDate.getMonthValue() == startDate.getMonthValue() && endDate.getDayOfMonth() > startDate.getDayOfMonth())) {
            yearsBetween++;
        }
        return (int) yearsBetween;
    }

    public static int countQuartersBetweenDates(LocalDate startDate, LocalDate endDate) {
        long monthsBetween = ChronoUnit.MONTHS.between(
                startDate.withDayOfMonth(1),
                endDate.withDayOfMonth(1));
        int quarters = (int) monthsBetween / 3;
        LocalDate lastFullQuarterEnd = startDate.plusMonths(quarters * 3);
        LocalDate lastDayOfQuarter = lastFullQuarterEnd
                .withMonth(((lastFullQuarterEnd.getMonthValue() - 1) / 3 + 1) * 3)
                .with(TemporalAdjusters.lastDayOfMonth());
        if (!endDate.isBefore(lastDayOfQuarter)) {
            quarters++;
        }
        return quarters;
    }

    public static int countDaysToNextPeriod(Credit credit, LocalDate date) {
        switch (credit.getPaymentType()) {
            case 1 -> {
                if (date.plusMonths(1).withDayOfMonth(1).isAfter(credit.getEndDate())) {
                    return countDaysBetweenDates(date, credit.getEndDate());
                }
                else {
                    return countDaysBetweenDates(date, date.plusMonths(1).withDayOfMonth(1));
                }
            }
            case 2 -> {
                if (date.plusMonths(3).withDayOfMonth(1).isAfter(credit.getEndDate())) {
                    return countDaysBetweenDates(date, credit.getEndDate());
                }
                else {
                    return countDaysBetweenDates(date, date.plusMonths(3).withDayOfMonth(1));
                }
            }
            case 3 -> {
                if (date.plusYears(1).withMonth(1).withDayOfMonth(1).isAfter(credit.getEndDate())) {
                    return countDaysBetweenDates(date, credit.getEndDate());
                }
                else {
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
    public static boolean isDateBetweenDates(LocalDate dateToCheck, LocalDate start, LocalDate end){
        return !dateToCheck.isBefore(start) && !dateToCheck.isAfter(end);
    }
}
