package com.netrunners.financialcalculator.logic.files;

import com.netrunners.financialcalculator.logic.entity.ResultTableSender;
import com.netrunners.financialcalculator.logic.entity.credit.Credit;
import com.netrunners.financialcalculator.logic.entity.credit.CreditWithHolidays;
import com.netrunners.financialcalculator.logic.entity.deposit.CapitalisedDeposit;
import com.netrunners.financialcalculator.logic.entity.deposit.Deposit;
import javafx.scene.control.TableColumn;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.List;
import java.util.logging.Level;

import static com.netrunners.financialcalculator.constants.StringConstants.ASTERISK;
import static com.netrunners.financialcalculator.constants.StringConstants.SEMI_COLON;

public class SaveFile {

    public static void writeDataToCSV(List<Object[]> data, File file, ResultTableSender financialOperation, TableColumn<Object[], String> investmentLoanColumn, TableColumn<Object[], String> periodProfitLoanColumn, TableColumn<Object[], String> totalColumn, TableColumn<Object[], String> periodPercentsColumn) {
        if (file != null) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                if (financialOperation instanceof Deposit deposit) {
                    writer.println(deposit.getNameOfWithdrawalType() + SEMI_COLON + investmentLoanColumn.getText() + SEMI_COLON + periodProfitLoanColumn.getText() + SEMI_COLON + totalColumn.getText());
                    for (Object[] row : data) {
                        writer.println(row[0] + SEMI_COLON + row[1] + SEMI_COLON + row[2] + SEMI_COLON + row[3]);
                        writer.flush();
                    }
                    writeDataToCSV(writer, deposit);
                } else if (financialOperation instanceof Credit credit) {
                    writer.println(credit.getNameOfPaymentType() + SEMI_COLON + investmentLoanColumn.getText() + SEMI_COLON + periodProfitLoanColumn.getText() + SEMI_COLON + totalColumn.getText() + SEMI_COLON + periodPercentsColumn.getText());
                    for (Object[] row : data) {
                        writer.println(row[0] + SEMI_COLON + row[1] + SEMI_COLON + row[2] + SEMI_COLON + row[3] + SEMI_COLON + row[4]);
                        writer.flush();
                    }
                    writeDataToCSV(writer, credit);
                }
                writer.println("Currency: " + financialOperation.getCurrency());
                writer.println("Start date: " + financialOperation.getStartDate());
                writer.println("End date: " + financialOperation.getEndDate());
            } catch (IOException e) {
                LogHelper.log(Level.SEVERE, "Error while writing Deposit to CSV", e);
            }
        } else {
            LogHelper.log(Level.SEVERE, "Error while writing Deposit to CSV", null);
        }
    }

    private static void writeDataToCSV(PrintWriter writer, Deposit deposit) {
        writer.println("Annual percent of deposit: " + deposit.getAnnualPercent());
        if (deposit.isEarlyWithdrawal()) {
            writer.println("Early withdrawal date: " + deposit.getEarlyWithdrawalDate());
        }
    }

    private static void writeDataToCSV(PrintWriter writer, Credit credit) {
        writer.println("Annual percent of credit: " + credit.getAnnualPercent());
        if (credit instanceof CreditWithHolidays) {
            writer.println("Holidays start date: " + ((CreditWithHolidays) credit).getHolidaysStart());
            writer.println("Holidays end date: " + ((CreditWithHolidays) credit).getHolidaysEnd());
        }
    }

    //TODO: fix Excel saving issues
    public static void writeDataToXLS(List<Object[]> data, ResultTableSender financialOperation, File file, TableColumn<Object[], String> investmentLoanColumn, TableColumn<Object[], String> periodProfitLoanColumn, TableColumn<Object[], String> totalColumn, TableColumn<Object[], Integer> periodColumn, TableColumn<Object[], String> periodPercentsColumn, List<Integer> daysToNextPeriod, List<Integer> daysToNextPeriodWithHolidays){
        String userSelectedCurrency = financialOperation.getCurrency();
        try (Workbook workbook = new XSSFWorkbook(); FileOutputStream fileOut = new FileOutputStream(file)) {
            int infoStartRow = data.size() + 2;
            if (financialOperation instanceof Deposit deposit) {
                Sheet sheet = workbook.createSheet("Deposit Data");

                Row headerRow = sheet.createRow(0);
                headerRow.createCell(0).setCellValue(periodColumn.getText());
                headerRow.createCell(1).setCellValue(investmentLoanColumn.getText());
                headerRow.createCell(2).setCellValue(periodProfitLoanColumn.getText());
                headerRow.createCell(3).setCellValue(totalColumn.getText());
                headerRow.createCell(4).setCellValue("Days in period");

                for (int f = 0; f < 5; f++) {
                    int width = sheet.getColumnWidth(f);
                    sheet.setColumnWidth(f, width * 2);
                }

                boolean capitalize = deposit instanceof CapitalisedDeposit;

                writeDataToXLS(data, sheet, infoStartRow, deposit.getInvestment(), daysToNextPeriod, capitalize);

                Row row = sheet.createRow(infoStartRow);
                row.createCell(0).setCellValue("Annual percent of deposit: ");
                row.createCell(1).setCellValue(deposit.getAnnualPercent());

                row = sheet.createRow(infoStartRow + 1);
                row.createCell(0).setCellValue("Currency: ");
                row.createCell(1).setCellValue(userSelectedCurrency);

                row = sheet.createRow(infoStartRow + 2);
                row.createCell(0).setCellValue("Start date: ");
                row.createCell(1).setCellValue(deposit.getStartDate().toString());

                row = sheet.createRow(infoStartRow + 3);
                row.createCell(0).setCellValue("End date: ");
                row.createCell(1).setCellValue(deposit.getEndDate().toString());

                if (deposit.isEarlyWithdrawal()) {
                    row = sheet.createRow(infoStartRow + 4);
                    row.createCell(0).setCellValue("Early withdrawal date: ");
                    row.createCell(1).setCellValue(deposit.getEarlyWithdrawalDate().toString());
                }

            } else if (financialOperation instanceof Credit credit) {
                float dailyPart = credit.countCreditBodyPerDay();

                Sheet sheet = workbook.createSheet("Credit Data");
                Row headerRow = sheet.createRow(0);
                headerRow.createCell(0).setCellValue(credit.getNameOfPaymentType());
                headerRow.createCell(1).setCellValue(investmentLoanColumn.getText());
                headerRow.createCell(2).setCellValue(periodProfitLoanColumn.getText());
                headerRow.createCell(3).setCellValue(totalColumn.getText());
                headerRow.createCell(4).setCellValue(periodPercentsColumn.getText());
                headerRow.createCell(5).setCellValue("Payment days");
                headerRow.createCell(6).setCellValue("Days in period");

                writeDataToXLS(data, sheet, infoStartRow, credit.getLoan(), daysToNextPeriod, daysToNextPeriodWithHolidays);

                Row row = sheet.createRow(infoStartRow);
                row.createCell(0).setCellValue("Annual percent of credit: ");

                row = sheet.createRow(infoStartRow + 4);
                row.createCell(0).setCellValue("Daily credit body part: ");
                row.createCell(1).setCellValue(dailyPart);

                if (credit instanceof CreditWithHolidays) {
                    row = sheet.createRow(infoStartRow + 5);
                    row.createCell(0).setCellValue("Holidays start date: ");
                    row.createCell(1).setCellValue(((CreditWithHolidays) credit).getHolidaysStart().toString());

                    row = sheet.createRow(infoStartRow + 6);
                    row.createCell(0).setCellValue("Holidays end date: ");
                    row.createCell(1).setCellValue(((CreditWithHolidays) credit).getHolidaysEnd().toString());
                }
            }

            workbook.write(fileOut);

        } catch (IOException e) {
            LogHelper.log(Level.SEVERE, "Error while writing Deposit to Excel", e);
        }
    }

    // TODO: ensure everything is correct
    public static void writeDataToXLS(List<Object[]> data, Sheet sheet, int infoStartRow, float tempInvest, List<Integer> daysToNextPeriod, boolean capitalize) {
        for (int i = 0; i < data.size(); i++) {
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue((int) data.get(i)[0]);
            if (capitalize) {
                if (i == 0) {
                    row.createCell(1).setCellValue(tempInvest);
                    row.createCell(2).setCellFormula(cellFormula(i, infoStartRow, "E"));
                    row.createCell(3).setCellFormula("B" + (i + 2));
                } else {
                    row.createCell(1).setCellFormula("D" + (i + 1));
                    row.createCell(2).setCellFormula(cellFormula(i, infoStartRow, "E"));
                    row.createCell(3).setCellFormula("D" + (i + 1) + "+C" + (i + 2));
                }
            } else {
                row.createCell(1).setCellValue(tempInvest);
                row.createCell(2).setCellFormula(cellFormula(i, infoStartRow, "E"));
                if (i == 0) {
                    row.createCell(3).setCellFormula("B" + (i + 2));
                } else {
                    row.createCell(3).setCellFormula("D" + (i + 1) + "+C" + (i + 2));
                }
            }
            row.createCell(4).setCellValue(daysToNextPeriod.get(i));
        }
    }

    // TODO: ensure everything is correct
    public static void writeDataToXLS(List<Object[]> data, Sheet sheet, int infoStartRow, float loan, List<Integer> daysToNextPeriod, List<Integer> daysToNextPeriodWithHolidays) {
        for (int i = 0; i < data.size(); i++) {
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue((int) data.get(i)[0]);
            if (i == 0) {
                row.createCell(1).setCellValue(loan);
                row.createCell(2).setCellFormula(cellFormula(i, infoStartRow, "F"));
                row.createCell(3).setCellFormula("B" + (i + 2));
                row.createCell(4).setCellFormula(cellFormula(i, infoStartRow, "F"));
            } else {
                row.createCell(1).setCellFormula("D" + (i + 1));
                row.createCell(2).setCellFormula("B" + (infoStartRow + 5) + ASTERISK + "F" + (i + 2));
                row.createCell(3).setCellFormula("D" + (i + 1) + "-C" + (i + 2));
                row.createCell(4).setCellFormula(cellFormula(i, infoStartRow, "G"));
                if (i == data.size() - 1) {
                    row.createCell(2).setCellFormula("B" + (i + 2));
                    row.createCell(3).setCellValue(0);
                }
            }
            row.createCell(5).setCellValue(daysToNextPeriod.get(i));
            row.createCell(6).setCellValue(daysToNextPeriodWithHolidays.get(i));
        }
    }

    private static String cellFormula(int i, int infoStartRow, String column){
        return "B" + (i + 2) + "*(1/365)*" + "B" + (infoStartRow + 1) + "/100" + ASTERISK + column + (i + 2);
    }
}
