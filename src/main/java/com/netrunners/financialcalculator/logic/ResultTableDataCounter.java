package com.netrunners.financialcalculator.logic;

import com.netrunners.financialcalculator.controller.ControllerUtils;
import com.netrunners.financialcalculator.logic.entity.OperationPeriodType;
import com.netrunners.financialcalculator.logic.entity.ResultTableSender;
import com.netrunners.financialcalculator.logic.entity.credit.CreditWithHolidays;
import com.netrunners.financialcalculator.logic.entity.credit.CreditWithoutHolidays;
import com.netrunners.financialcalculator.logic.entity.deposit.CapitalisedDeposit;
import com.netrunners.financialcalculator.logic.entity.deposit.Deposit;
import com.netrunners.financialcalculator.logic.time.DateTimeUtils;
import javafx.scene.control.TableColumn;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ResultTableDataCounter {
    List<Integer> daysToNextPeriodWithHolidays = new ArrayList<>();
    List<Integer> daysToNextPeriod = new ArrayList<>();
    private final ResultTableSender financialOperation;
    private final TableColumn<Object[], Integer> periodColumn;
    private final TableColumn<Object[], String> investmentLoanColumn;
    private final TableColumn<Object[], String> periodProfitLoanColumn;

    public ResultTableDataCounter(ResultTableSender financialOperation, TableColumn<Object[], Integer> periodColumn, TableColumn<Object[], String> investmentLoanColumn, TableColumn<Object[], String> periodProfitLoanColumn) {
        this.financialOperation = financialOperation;
        this.periodColumn = periodColumn;
        this.investmentLoanColumn = investmentLoanColumn;
        this.periodProfitLoanColumn = periodProfitLoanColumn;
    }

    public List<Object[]> countData() {
        daysToNextPeriod.add(0);
        List<Object[]> data = new ArrayList<>();
        LocalDate financialOperationStartDate = financialOperation.getStartDate();
        float financialOperationBody = financialOperation.getBody();
        int numbersColumnFlag = 0;
        if (financialOperation instanceof Deposit) {
            return countData((Deposit) financialOperation, data, financialOperationStartDate, financialOperationBody, numbersColumnFlag);
        } else {
            if (financialOperation instanceof CreditWithHolidays) {
                return countData((CreditWithHolidays) financialOperation, data, financialOperationStartDate, numbersColumnFlag);
            } else {
                return countData((CreditWithoutHolidays) financialOperation, data, financialOperationStartDate, numbersColumnFlag);
            }
        }
    }

    public List<Object[]> countData(Deposit deposit,
                                    List<Object[]> data,
                                    LocalDate depositStartDate,
                                    float depositInvestment,
                                    int numbersColumnFlag) {

        ControllerUtils.provideTranslation(periodColumn, deposit.getNameOfWithdrawalType());
        ControllerUtils.provideTranslation(investmentLoanColumn, "InvestInput");
        ControllerUtils.provideTranslation(periodProfitLoanColumn, "ProfitColumn");

        boolean capitalize = deposit instanceof CapitalisedDeposit;
        LocalDate endOfContract = deposit.isEarlyWithdrawal()
                ? deposit.getEarlyWithdrawalDate()
                : deposit.getEndDate();
        String currency = deposit.getCurrency();

        float initialInvestment = depositInvestment;
        float currentInvestment = depositInvestment;
        float cumulativeTotal = depositInvestment;

        data.add(new Object[]{
                numbersColumnFlag,
                formatCurrency(initialInvestment, currency),
                formatCurrency(0f, currency),
                formatCurrency(cumulativeTotal, currency)
        });
        numbersColumnFlag++;

        while (!depositStartDate.isAfter(endOfContract.minusDays(1))) {
            float periodProfit = 0f;
            int days = DateTimeUtils.countDaysToNextPeriod(deposit, depositStartDate, endOfContract);
            daysToNextPeriod.add(days);

            for (int d = 0; d < days && depositStartDate.isBefore(endOfContract); d++) {
                float dailyProfit = (capitalize ? currentInvestment : initialInvestment)
                        * (deposit.getAnnualPercent() / 100f) / 365f;
                periodProfit += dailyProfit;

                if (capitalize) {
                    cumulativeTotal += dailyProfit;
                    currentInvestment = cumulativeTotal;
                    deposit.setInvestment(currentInvestment);
                }
                depositStartDate = depositStartDate.plusDays(1);
            }

            if (!capitalize) {
                cumulativeTotal += periodProfit;
            }

            float investmentForPeriod = initialInvestment;
            float profitForPeriod = periodProfit;
            float totalForPeriod = cumulativeTotal;

            data.add(new Object[]{
                    numbersColumnFlag,
                    formatCurrency(investmentForPeriod, currency),
                    formatCurrency(profitForPeriod, currency),
                    formatCurrency(totalForPeriod, currency)
            });

            if (capitalize) {
                initialInvestment = currentInvestment;
            }
            numbersColumnFlag++;
        }

        return data;
    }

    private LocalDate addPeriod(LocalDate date, OperationPeriodType type) {
        return switch (type) {
            case MONTHS -> date.plusMonths(1);
            case QUARTERS -> date.plusMonths(3);
            case YEARS -> date.plusYears(1);
            case END_OF_TERM -> date.withDayOfYear(date.lengthOfYear());
            default -> date.plusMonths(1);
        };
    }

    private int countPeriods(LocalDate start, LocalDate end, OperationPeriodType type) {
        int periods = 0;
        LocalDate tmp = start;
        while (tmp.isBefore(end)) {
            periods++;
            tmp = addPeriod(tmp, type);
        }
        return periods == 0 ? 1 : periods;
    }

    public List<Object[]> countData(CreditWithoutHolidays credit, List<Object[]> data, LocalDate tempDate, int numbersColumnFlag) {
        ControllerUtils.provideTranslation(periodColumn, credit.getNameOfPaymentType());
        ControllerUtils.provideTranslation(investmentLoanColumn, "LoanInput");
        ControllerUtils.provideTranslation(periodProfitLoanColumn, "PaymentLoan");

        String currency = credit.getCurrency();
        float initialLoan = credit.getLoan();
        float currentLoan = initialLoan;
        float annualPercent = credit.getAnnualPercent();
        LocalDate endDate = credit.getEndDate();
        OperationPeriodType type = credit.getPaymentType();

        data.add(new Object[]{
                numbersColumnFlag,
                formatCurrency(initialLoan, currency),
                formatCurrency(0f, currency),
                formatCurrency(initialLoan, currency),
                formatCurrency(0f, currency)
        });
        numbersColumnFlag++;

        if (type == OperationPeriodType.END_OF_TERM) {
            int daysInPeriod = (int) java.time.temporal.ChronoUnit.DAYS.between(tempDate, endDate);
            float interest = currentLoan * (annualPercent / 100f) * daysInPeriod / 365f;
            float payment = currentLoan;
            float afterPayment = 0f;
            data.add(new Object[]{
                    numbersColumnFlag,
                    formatCurrency(currentLoan, currency),
                    formatCurrency(payment, currency),
                    formatCurrency(afterPayment, currency),
                    formatCurrency(interest, currency)
            });
            return data;
        }

        int periods = countPeriods(credit.getStartDate(), endDate, type);
        float bodyPayment = initialLoan / periods;

        LocalDate currentDate = credit.getStartDate();

        for (int period = 1; period <= periods; period++) {
            LocalDate nextDate = addPeriod(currentDate, type);
            if (nextDate.isAfter(endDate)) nextDate = endDate;
            int daysInPeriod = (int) java.time.temporal.ChronoUnit.DAYS.between(currentDate, nextDate);
            float interest = currentLoan * (annualPercent / 100f) * daysInPeriod / 365f;
            float afterPayment = currentLoan - bodyPayment;
            if (afterPayment < 0) afterPayment = 0;
            data.add(new Object[]{
                    numbersColumnFlag,
                    formatCurrency(currentLoan, currency),
                    formatCurrency(bodyPayment, currency),
                    formatCurrency(afterPayment, currency),
                    formatCurrency(interest, currency)
            });
            currentLoan = afterPayment;
            currentDate = nextDate;
            numbersColumnFlag++;
        }

        return data;
    }

    public List<Object[]> countData(CreditWithHolidays credit, List<Object[]> data, LocalDate tempDate, int numbersColumnFlag) {
        ControllerUtils.provideTranslation(periodColumn, credit.getNameOfPaymentType());
        ControllerUtils.provideTranslation(investmentLoanColumn, "LoanInput");
        ControllerUtils.provideTranslation(periodProfitLoanColumn, "PaymentLoan");

        String currency = credit.getCurrency();
        float initialLoan = credit.getLoan();
        float currentLoan = initialLoan;
        float annualPercent = credit.getAnnualPercent();
        LocalDate endDate = credit.getEndDate();
        OperationPeriodType type = credit.getPaymentType();

        // 0-й період
        data.add(new Object[]{
                numbersColumnFlag,
                formatCurrency(initialLoan, currency),
                formatCurrency(0f, currency),
                formatCurrency(initialLoan, currency),
                formatCurrency(0f, currency)
        });
        numbersColumnFlag++;

        if (type == OperationPeriodType.END_OF_TERM) {
            int daysInPeriod = (int) java.time.temporal.ChronoUnit.DAYS.between(tempDate, endDate);
            float interest = currentLoan * (annualPercent / 100f) * daysInPeriod / 365f;
            float payment = currentLoan;
            float afterPayment = 0f;
            data.add(new Object[]{
                    numbersColumnFlag,
                    formatCurrency(currentLoan, currency),
                    formatCurrency(payment, currency),
                    formatCurrency(afterPayment, currency),
                    formatCurrency(interest, currency)
            });
            return data;
        }

        int totalNonHolidayDays = 0;
        LocalDate iter = credit.getStartDate();
        while (iter.isBefore(endDate)) {
            if (!DateTimeUtils.isDateBetweenDates(iter, credit.getHolidaysStart(), credit.getHolidaysEnd())) {
                totalNonHolidayDays++;
            }
            iter = iter.plusDays(1);
        }
        if (totalNonHolidayDays == 0) totalNonHolidayDays = 1;

        float bodyPerNonHolidayDay = initialLoan / totalNonHolidayDays;

        LocalDate currentDate = credit.getStartDate();

        while (currentDate.isBefore(endDate)) {
            LocalDate nextDate = addPeriod(currentDate, type);
            if (nextDate.isAfter(endDate)) nextDate = endDate;
            int daysInPeriod = (int) java.time.temporal.ChronoUnit.DAYS.between(currentDate, nextDate);

            int nonHolidayDays = 0;
            LocalDate dayIter = currentDate;
            while (dayIter.isBefore(nextDate)) {
                if (!DateTimeUtils.isDateBetweenDates(dayIter, credit.getHolidaysStart(), credit.getHolidaysEnd())) {
                    nonHolidayDays++;
                }
                dayIter = dayIter.plusDays(1);
            }

            float payment = bodyPerNonHolidayDay * nonHolidayDays;

            float interest = currentLoan * (annualPercent / 100f) * daysInPeriod / 365f;

            float afterPayment = currentLoan - payment;
            if (afterPayment < 0) afterPayment = 0;

            data.add(new Object[]{
                    numbersColumnFlag,
                    formatCurrency(currentLoan, currency),
                    formatCurrency(payment, currency),
                    formatCurrency(afterPayment, currency),
                    formatCurrency(interest, currency)
            });

            currentLoan = afterPayment;
            currentDate = nextDate;
            numbersColumnFlag++;
        }

        return data;
    }

    private String formatCurrency(float amount, String currency) {
        return String.format("%.2f", amount) + currency;
    }

    public ResultTableSender getFinancialOperation() {
        return financialOperation;
    }

    public List<Integer> getDaysToNextPeriodWithHolidays() {
        return daysToNextPeriodWithHolidays;
    }

    public List<Integer> getDaysToNextPeriod() {
        return daysToNextPeriod;
    }
}
