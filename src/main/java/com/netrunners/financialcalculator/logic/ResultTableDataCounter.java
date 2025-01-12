package com.netrunners.financialcalculator.logic;

import com.netrunners.financialcalculator.controller.ControllerUtils;
import com.netrunners.financialcalculator.logic.entity.ResultTableSender;
import com.netrunners.financialcalculator.logic.entity.credit.Credit;
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

    public List<Object[]> countData(){
        daysToNextPeriod.add(0);
        List<Object[]> data = new ArrayList<>();
        LocalDate financialOperationStartDate = financialOperation.getStartDate();
        float financialOperationBody = financialOperation.getBody();
        int numbersColumnFlag = 0;
        if (financialOperation instanceof Deposit) {
            return countData((Deposit) financialOperation, data, financialOperationStartDate,financialOperationBody, financialOperationBody, numbersColumnFlag);
        } else{
            if (financialOperation instanceof CreditWithHolidays) {
                return countData((CreditWithHolidays) financialOperation, data, financialOperationStartDate, numbersColumnFlag);
            } else {
                return countData((CreditWithoutHolidays) financialOperation, data, financialOperationStartDate, numbersColumnFlag);
            }
        }
    }

    public List<Object[]> countData(Deposit deposit, List<Object[]> data, LocalDate depositStartDate, float depositInvestment, float totalInvestment, int numbersColumnFlag) {
        boolean capitalize = deposit instanceof CapitalisedDeposit;
        LocalDate endOfContract;
        if (deposit.isEarlyWithdrawal()) {
            endOfContract = deposit.getEarlyWithdrawalDate();
        } else {
            endOfContract = deposit.getEndDate();
        }

        while (!depositStartDate.equals(endOfContract)) {
            float periodProfit = 0;
            int daysToNextPeriodCount = DateTimeUtils.countDaysToNextPeriod(deposit, depositStartDate, endOfContract);

            if (numbersColumnFlag == 0) {
                ControllerUtils.provideTranslation(periodColumn, deposit.getNameOfWithdrawalType());
                ControllerUtils.provideTranslation(investmentLoanColumn, "InvestInput");
                ControllerUtils.provideTranslation(periodProfitLoanColumn, "ProfitColumn");
                daysToNextPeriodCount = 0;
            } else {
                daysToNextPeriod.add(daysToNextPeriodCount);
                if (capitalize) {
                    for (int i = 0; i < daysToNextPeriodCount; i++) {
                        periodProfit += deposit.countProfit();
                    }
                }
            }
            totalInvestment += periodProfit;
            String currency = deposit.getCurrency();
            data.add(new Object[]{
                    numbersColumnFlag,
                    formatCurrency(depositInvestment, currency),
                    formatCurrency(periodProfit, currency),
                    formatCurrency(totalInvestment, currency)
            });
            if (capitalize) {
                depositInvestment = totalInvestment;
                deposit.setInvestment(depositInvestment);
            }
            depositStartDate = depositStartDate.plusDays(daysToNextPeriodCount);
            numbersColumnFlag++;
        }
        return data;
    }


    public List<Object[]> countData(CreditWithHolidays credit, List<Object[]> data, LocalDate tempDate, int numbersColumnFlag) {
        float dailyBodyPart = credit.countCreditBodyPerDay();
        while (!tempDate.equals(credit.getEndDate())) {
            float tempLoan = credit.getLoan();
            float totalLoan = tempLoan;
            float creditBody = 0;
            float periodPercents = 0;
            int daysToNextPeriodCount = DateTimeUtils.countDaysToNextPeriod(credit, tempDate);
            if (numbersColumnFlag == 0) {
                ControllerUtils.provideTranslation(periodColumn, credit.getNameOfPaymentType());
                ControllerUtils.provideTranslation(investmentLoanColumn, "LoanInput");
                ControllerUtils.provideTranslation(periodProfitLoanColumn, "PaymentLoan");
            } else {
                daysToNextPeriod.add(daysToNextPeriodCount);
                daysToNextPeriodWithHolidays.add(daysToNextPeriodCount);
                for (int i = 0; i < daysToNextPeriodCount; i++) {
                    if (!DateTimeUtils.isDateBetweenDates(tempDate, credit.getHolidaysStart(), credit.getHolidaysEnd())) {
                        creditBody += dailyBodyPart;
                    } else {
                        daysToNextPeriod.set(numbersColumnFlag, daysToNextPeriod.get(numbersColumnFlag) - 1);
                    }
                    periodPercents += credit.countLoan();
                    tempDate = tempDate.plusDays(1);
                }
                if (tempDate.equals(credit.getEndDate())) {
                    creditBody = tempLoan;
                }
                totalLoan -= creditBody;
                credit.setLoan(totalLoan);
            }
            String currency = credit.getCurrency();
            data.add(new Object[]{numbersColumnFlag,
                    formatCurrency(tempLoan, currency),
                    formatCurrency(creditBody, currency),
                    formatCurrency(totalLoan, currency),
                    formatCurrency(periodPercents, currency)
            });
            numbersColumnFlag++;
        }
        return data;
    }

    public List<Object[]> countData(Credit credit, List<Object[]> data, LocalDate tempDate, int numbersColumnFlag) {
        float dailyBodyPart = credit.countCreditBodyPerDay();
        while (tempDate.isBefore(credit.getEndDate())) {
            float tempLoan = credit.getLoan();
            float totalLoan = tempLoan;
            float creditBody = 0;
            float periodPercents = 0;
            int daysToNextPeriodCount = DateTimeUtils.countDaysToNextPeriod(credit, tempDate);
            if (numbersColumnFlag == 0) {
                ControllerUtils.provideTranslation(periodColumn, credit.getNameOfPaymentType());
                ControllerUtils.provideTranslation(investmentLoanColumn, "LoanInput");
                ControllerUtils.provideTranslation(periodProfitLoanColumn, "PaymentLoan");
            } else {
                daysToNextPeriod.add(daysToNextPeriodCount);
                daysToNextPeriodWithHolidays.add(daysToNextPeriodCount);
                for (int i = 0; i < daysToNextPeriodCount; i++) {
                    periodPercents += credit.countLoan();
                    creditBody += dailyBodyPart;
                    tempDate = tempDate.plusDays(1);
                }
                if (tempDate.equals(credit.getEndDate())) {
                    creditBody = tempLoan;
                }
                totalLoan -= creditBody;
                credit.setLoan(totalLoan);
            }
            String currency = credit.getCurrency();
            data.add(new Object[]{numbersColumnFlag,
                    formatCurrency(tempLoan, currency),
                    formatCurrency(creditBody, currency),
                    formatCurrency(totalLoan, currency),
                    formatCurrency(periodPercents, currency)
            });
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
