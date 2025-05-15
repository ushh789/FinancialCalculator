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

    public List<Object[]> countData(Deposit deposit, List<Object[]> data,
                                    LocalDate depositStartDate, float depositInvestment,
                                    float totalInvestment, int numbersColumnFlag) {

        boolean capitalize = deposit instanceof CapitalisedDeposit;
        LocalDate endOfContract = deposit.isEarlyWithdrawal() ? deposit.getEarlyWithdrawalDate() : deposit.getEndDate();

        ControllerUtils.provideTranslation(periodColumn, deposit.getNameOfWithdrawalType());
        ControllerUtils.provideTranslation(investmentLoanColumn, "InvestInput");
        ControllerUtils.provideTranslation(periodProfitLoanColumn, "ProfitColumn");

        String currency = deposit.getCurrency();

        while (!depositStartDate.isAfter(endOfContract.minusDays(1))) {
            float periodProfit = 0;
            int daysToNextPeriodCount = DateTimeUtils.countDaysToNextPeriod(deposit, depositStartDate, endOfContract);
            daysToNextPeriod.add(daysToNextPeriodCount);

            for (int i = 0; i < daysToNextPeriodCount && depositStartDate.isBefore(endOfContract); i++) {
                float dailyProfit = deposit.countProfit();
                periodProfit += dailyProfit;
                if (capitalize) {
                    totalInvestment += dailyProfit;
                    deposit.setInvestment(totalInvestment);  // оновлюємо тіло під капіталізацію
                }
                depositStartDate = depositStartDate.plusDays(1);
            }

            data.add(new Object[]{
                    numbersColumnFlag,
                    formatCurrency(depositInvestment, currency),
                    formatCurrency(periodProfit, currency),
                    formatCurrency(totalInvestment, currency)
            });

            if (capitalize) {
                depositInvestment = totalInvestment;
            }

            numbersColumnFlag++;
        }

        return data;
    }



    public List<Object[]> countData(CreditWithHolidays credit, List<Object[]> data, LocalDate tempDate, int numbersColumnFlag) {
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

                LocalDate currentDate = tempDate;
                for (int i = 0; i < daysToNextPeriodCount && currentDate.isBefore(credit.getEndDate()); i++) {
                    if (!DateTimeUtils.isDateBetweenDates(currentDate, credit.getHolidaysStart(), credit.getHolidaysEnd())) {
                        creditBody += dailyBodyPart;
                    }
                    periodPercents += credit.countLoan();
                    currentDate = currentDate.plusDays(1);
                }

                if (currentDate.equals(credit.getEndDate())) {
                    creditBody = tempLoan;
                }

                totalLoan -= creditBody;
                credit.setLoan(totalLoan);
                tempDate = currentDate;
            }

            String currency = credit.getCurrency();
            data.add(new Object[]{
                    numbersColumnFlag,
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

                LocalDate currentDate = tempDate;
                for (int i = 0; i < daysToNextPeriodCount && currentDate.isBefore(credit.getEndDate()); i++) {
                    creditBody += dailyBodyPart;
                    periodPercents += credit.countLoan();
                    currentDate = currentDate.plusDays(1);
                }

                if (currentDate.equals(credit.getEndDate())) {
                    creditBody = tempLoan;
                }

                totalLoan -= creditBody;
                credit.setLoan(totalLoan);
                tempDate = currentDate;
            }

            String currency = credit.getCurrency();
            data.add(new Object[]{
                    numbersColumnFlag,
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
