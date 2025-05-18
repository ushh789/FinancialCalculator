package com.netrunners.financialcalculator.logic.files;

import com.netrunners.financialcalculator.errorhandling.exceptions.SavingFileException;
import com.netrunners.financialcalculator.logic.entity.credit.CreditWithoutHolidays;
import com.netrunners.financialcalculator.logic.entity.deposit.CapitalisedDeposit;
import com.netrunners.financialcalculator.logic.entity.deposit.Deposit;
import javafx.scene.control.TableColumn;
import org.junit.jupiter.api.*;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SaveFileTest {

    private File tempFile;

    @BeforeEach
    void setUp() throws IOException {
        tempFile = File.createTempFile("test", ".csv");
        tempFile.deleteOnExit();
    }

    @Test
    void testWriteDataToCSV_Deposit() {
        List<Object[]> data = new ArrayList<>();
        data.add(new Object[]{0, "1000$", "0$", "1000$"});
        Deposit deposit = new CapitalisedDeposit(1000f, "$", 10f,
                LocalDate.of(2024, 1, 1), LocalDate.of(2025, 1, 1), false, null, com.netrunners.financialcalculator.logic.entity.OperationPeriodType.MONTHS);

        TableColumn<Object[], String> col1 = new TableColumn<>("Invest");
        TableColumn<Object[], String> col2 = new TableColumn<>("Profit");
        TableColumn<Object[], String> col3 = new TableColumn<>("Total");
        TableColumn<Object[], String> col4 = new TableColumn<>("Percents");

        assertDoesNotThrow(() -> SaveFile.writeDataToCSV(data, tempFile, deposit, col1, col2, col3, col4));
        assertTrue(tempFile.length() > 0);
    }

    @Test
    void testWriteDataToCSV_Credit() {
        List<Object[]> data = new ArrayList<>();
        data.add(new Object[]{0, "1000$", "0$", "1000$", "0$"});
        CreditWithoutHolidays credit = new CreditWithoutHolidays(1000f, "$", 10f,
                LocalDate.of(2024, 1, 1), LocalDate.of(2025, 1, 1), com.netrunners.financialcalculator.logic.entity.OperationPeriodType.MONTHS);

        TableColumn<Object[], String> col1 = new TableColumn<>("Loan");
        TableColumn<Object[], String> col2 = new TableColumn<>("Payment");
        TableColumn<Object[], String> col3 = new TableColumn<>("Total");
        TableColumn<Object[], String> col4 = new TableColumn<>("Percents");

        assertDoesNotThrow(() -> SaveFile.writeDataToCSV(data, tempFile, credit, col1, col2, col3, col4));
        assertTrue(tempFile.length() > 0);
    }

    @Test
    void testWriteDataToCSV_NullFile() {
        List<Object[]> data = new ArrayList<>();
        Deposit deposit = new CapitalisedDeposit(1000f, "$", 10f,
                LocalDate.of(2024, 1, 1), LocalDate.of(2025, 1, 1), false, null, com.netrunners.financialcalculator.logic.entity.OperationPeriodType.MONTHS);

        TableColumn<Object[], String> col1 = new TableColumn<>("Invest");
        TableColumn<Object[], String> col2 = new TableColumn<>("Profit");
        TableColumn<Object[], String> col3 = new TableColumn<>("Total");
        TableColumn<Object[], String> col4 = new TableColumn<>("Percents");

        assertThrows(SavingFileException.class, () -> SaveFile.writeDataToCSV(data, null, deposit, col1, col2, col3, col4));
    }

    // Тест для writeDataToXLS можна зробити аналогічно, але для цього треба перевіряти створення Excel-файлу.
    // Для простоти тут лише smoke-тест (чи не кидає винятків):

    @Test
    void testWriteDataToXLS_Deposit() {
        List<Object[]> data = new ArrayList<>();
        data.add(new Object[]{0, "1000$", "0$", "1000$"});
        Deposit deposit = new CapitalisedDeposit(1000f, "$", 10f,
                LocalDate.of(2024, 1, 1), LocalDate.of(2025, 1, 1), false, null, com.netrunners.financialcalculator.logic.entity.OperationPeriodType.MONTHS);

        TableColumn<Object[], String> col1 = new TableColumn<>("Invest");
        TableColumn<Object[], String> col2 = new TableColumn<>("Profit");
        TableColumn<Object[], String> col3 = new TableColumn<>("Total");
        TableColumn<Object[], Integer> col4 = new TableColumn<>("Period");
        TableColumn<Object[], String> col5 = new TableColumn<>("Percents");

        List<Integer> days1 = List.of(0);
        List<Integer> days2 = List.of(0);

        File xlsFile = new File(tempFile.getAbsolutePath().replace(".csv", ".xlsx"));
        assertDoesNotThrow(() -> SaveFile.writeDataToXLS(data, deposit, xlsFile, col1, col2, col3, col4, col5, days1, days2));
        assertTrue(xlsFile.exists());
        xlsFile.delete();
    }
}