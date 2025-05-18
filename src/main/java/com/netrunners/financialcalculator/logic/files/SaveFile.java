package com.netrunners.financialcalculator.logic.files;

import com.netrunners.financialcalculator.errorhandling.exceptions.SavingFileException;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static com.netrunners.financialcalculator.constants.StringConstants.SEMI_COLON;
import static com.netrunners.financialcalculator.constants.StringConstants.ERROR_SAVING_FILE;
import static com.netrunners.financialcalculator.constants.StringConstants.ERROR_SELECTED_FILE_IS_NULL;
import static com.netrunners.financialcalculator.constants.StringConstants.INFO_FILE_SAVED;

public class SaveFile {
    private static final Logger logger = LoggerFactory.getLogger(SaveFile.class);
    public static void writeDataToCSV(List<Object[]> data, File file, ResultTableSender financialOperation, TableColumn<Object[], String> investmentLoanColumn, TableColumn<Object[], String> periodProfitLoanColumn, TableColumn<Object[], String> totalColumn, TableColumn<Object[], String> periodPercentsColumn) throws SavingFileException {
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
                logger.info(INFO_FILE_SAVED, file.getName());
            } catch (IOException e) {
                throw new SavingFileException(ERROR_SAVING_FILE + file.getName());
            }
        } else {
            throw new SavingFileException(ERROR_SELECTED_FILE_IS_NULL);
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
    public static void writeDataToXLS(List<Object[]> data, ResultTableSender financialOperation, File file, TableColumn<Object[], String> investmentLoanColumn, TableColumn<Object[], String> periodProfitLoanColumn, TableColumn<Object[], String> totalColumn, TableColumn<Object[], Integer> periodColumn, TableColumn<Object[], String> periodPercentsColumn, List<Integer> daysToNextPeriod, List<Integer> daysToNextPeriodWithHolidays) throws SavingFileException {
        if (file != null) {
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

                    boolean capitalize;
                    float initialInvestment;
                    if (deposit instanceof CapitalisedDeposit capitalisedDeposit) {
                        capitalize = true;
                        initialInvestment = capitalisedDeposit.getInitialInvestment();
                    } else {
                        capitalize = false;
                        initialInvestment = deposit.getInvestment();
                    }

                    writeDataToXLS(data, sheet, infoStartRow, initialInvestment, daysToNextPeriod, capitalize);

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
                logger.info(INFO_FILE_SAVED, file.getName());
            } catch (IOException e) {
                throw new SavingFileException(ERROR_SAVING_FILE + file.getName());
            }
        } else {
            throw new SavingFileException(ERROR_SELECTED_FILE_IS_NULL);
        }
    }

    // TODO: ensure everything is correct
    public static void writeDataToXLS(
            List<Object[]> data,
            Sheet sheet,
            int infoStartRow,
            float tempInvest,
            List<Integer> daysToNextPeriod,
            boolean capitalize) {

        String rateCell = "B" + (infoStartRow + 1);

        // пробігаємося виключно по реальних елементах data: i=0..data.size()-1
        for (int i = 0; i < data.size(); i++) {
            // Excel-рядок = i+2 (бо i=0 → рядок 2; i=1 → рядок 3; ...)
            int excelRow = i + 2;
            // А індекс у createRow = excelRow - 1
            Row row = sheet.createRow(excelRow - 1);

            // Номер періоду
            row.createCell(0).setCellValue((int) data.get(i)[0]);

            // Дні
            row.createCell(4).setCellValue(daysToNextPeriod.get(i));

            // Investment у колонці B
            if (capitalize && i > 0) {
                // капіталізована сума з попереднього Total (D)
                row.createCell(1).setCellFormula("D" + (excelRow - 1));
            } else {
                // початкова сума
                row.createCell(1).setCellValue(tempInvest);
            }

            // Profit у колонці C
            String profitFormula = String.format(
                    "B%d*(%s/100)*E%d/365",
                    excelRow, rateCell, excelRow
            );
            row.createCell(2).setCellFormula(profitFormula);

            // TotalInvestment у колонці D
            String totalFormula = (i == 0)
                    ? String.format("B%d+C%d", excelRow, excelRow)
                    : String.format("D%d+C%d", excelRow - 1, excelRow);
            row.createCell(3).setCellFormula(totalFormula);
        }
    }


    public static void writeDataToXLS(List<Object[]> data, Sheet sheet, int infoStartRow, float loan, List<Integer> daysToNextPeriod, List<Integer> daysToNextPeriodWithHolidays) {
        // Додаємо заголовки
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Період");
        headerRow.createCell(1).setCellValue("Залишок кредиту");
        headerRow.createCell(2).setCellValue("Платіж по тілу");
        headerRow.createCell(3).setCellValue("Залишок після платежу");
        headerRow.createCell(4).setCellValue("Відсотки за період");
        headerRow.createCell(5).setCellValue("Днів до наступного платежу");
        headerRow.createCell(6).setCellValue("Днів у періоді з урахуванням свят");
    
        for (int i = 0; i < data.size(); i++) {
            Row row = sheet.createRow(i + 1);
            Object[] rowData = data.get(i);
    
            row.createCell(0).setCellValue((Integer) rowData[0]);
            row.createCell(1).setCellValue((String) rowData[1]);
            row.createCell(2).setCellValue((String) rowData[2]);
            row.createCell(3).setCellValue((String) rowData[3]);
            row.createCell(4).setCellValue((String) rowData[4]);
            // Якщо індекс виходить за межі — підставляємо 0
            row.createCell(5).setCellValue(i < daysToNextPeriod.size() ? daysToNextPeriod.get(i) : 0);
            row.createCell(6).setCellValue(i < daysToNextPeriodWithHolidays.size() ? daysToNextPeriodWithHolidays.get(i) : 0);
        }
    }

    
}
