package com.netrunners.financialcalculator.LogicalInstrumnts.TimeFunctions;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateTimeFunctions {
    public static LocalDate dateFormer(String dateString) {
        try {
            String dateRegex = "(\\d{1,2})-(\\d{1,2})-(\\d{4})";
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

    public static int countDaysBetweenDates(LocalDate startDate, LocalDate endDate) {
        return (int) ChronoUnit.DAYS.between(startDate, endDate);
    }

    public static int countMonthsBetweenDates(LocalDate startDate, LocalDate endDate) {
        return (int) ChronoUnit.MONTHS.between(startDate, endDate);
    }

    public static int getQuarter(int month) {
        if (month >= 1 && month <= 3) {
            return 1;
        } else if (month >= 4 && month <= 6) {
            return 2;
        } else if (month >= 7 && month <= 9) {
            return 3;
        } else if (month >= 10 && month <= 12) {
            return 4;
        } else {
            throw new IllegalArgumentException("Невірний номер місяця: " + month);
        }
    }
}
