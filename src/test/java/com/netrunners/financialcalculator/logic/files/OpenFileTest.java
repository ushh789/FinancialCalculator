package com.netrunners.financialcalculator.logic.files;

import com.google.gson.JsonObject;
import com.netrunners.financialcalculator.logic.entity.OperationPeriodType;
import com.netrunners.financialcalculator.logic.entity.credit.CreditWithoutHolidays;
import com.netrunners.financialcalculator.logic.entity.deposit.CapitalisedDeposit;
import com.netrunners.financialcalculator.logic.entity.deposit.UncapitalisedDeposit;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class OpenFileTest {

    @Test
    void testOpenCreditSave_WithoutHolidays() throws Exception {
        JsonObject json = new JsonObject();
        json.addProperty("operation", "Credit");
        json.addProperty("type", "WithoutHolidays");
        json.addProperty("loan", 1000f);
        json.addProperty("annualPercent", 10f);
        json.addProperty("currency", "$");
        json.addProperty("paymentType", "MONTHS");
        json.addProperty("startDate", "2024-01-01");
        json.addProperty("endDate", "2025-01-01");

        CreditWithoutHolidays credit = (CreditWithoutHolidays) OpenFileTestHelper.openCreditSave(json);
        assertEquals(1000f, credit.getLoan());
        assertEquals("$", credit.getCurrency());
        assertEquals(10f, credit.getAnnualPercent());
        assertEquals(LocalDate.of(2024, 1, 1), credit.getStartDate());
        assertEquals(LocalDate.of(2025, 1, 1), credit.getEndDate());
        assertEquals(OperationPeriodType.MONTHS, credit.getPaymentType());
    }

    @Test
    void testOpenDepositSave_Capitalised() throws Exception {
        JsonObject json = new JsonObject();
        json.addProperty("operation", "Deposit");
        json.addProperty("type", "Capitalized");
        json.addProperty("investment", 2000f);
        json.addProperty("annualPercent", 10f);
        json.addProperty("currency", "$");
        json.addProperty("startDate", "2024-01-01");
        json.addProperty("endDate", "2025-01-01");
        json.addProperty("withdrawalOption", "MONTHS");
        json.addProperty("earlyWithdrawal", false);
        json.addProperty("earlyWithdrawalDate", "None");
        json.addProperty("initialInvestment", 2000f);

        CapitalisedDeposit deposit = (CapitalisedDeposit) OpenFileTestHelper.openDepositSave(json);
        assertEquals(2000f, deposit.getInvestment());
        assertEquals("$", deposit.getCurrency());
        assertEquals(10f, deposit.getAnnualPercent());
        assertEquals(LocalDate.of(2024, 1, 1), deposit.getStartDate());
        assertEquals(LocalDate.of(2025, 1, 1), deposit.getEndDate());
        assertEquals(OperationPeriodType.MONTHS, deposit.getWithdrawalOption());
        assertEquals(2000f, deposit.getInitialInvestment());
    }

    @Test
    void testOpenDepositSave_Uncapitalised() throws Exception {
        JsonObject json = new JsonObject();
        json.addProperty("operation", "Deposit");
        json.addProperty("type", "Uncapitalized");
        json.addProperty("investment", 1500f);
        json.addProperty("annualPercent", 8f);
        json.addProperty("currency", "$");
        json.addProperty("startDate", "2024-02-01");
        json.addProperty("endDate", "2025-02-01");
        json.addProperty("withdrawalOption", "MONTHS");
        json.addProperty("earlyWithdrawal", false);
        json.addProperty("earlyWithdrawalDate", "None");

        UncapitalisedDeposit deposit = (UncapitalisedDeposit) OpenFileTestHelper.openDepositSave(json);
        assertEquals(1500f, deposit.getInvestment());
        assertEquals("$", deposit.getCurrency());
        assertEquals(8f, deposit.getAnnualPercent());
        assertEquals(LocalDate.of(2024, 2, 1), deposit.getStartDate());
        assertEquals(LocalDate.of(2025, 2, 1), deposit.getEndDate());
        assertEquals(OperationPeriodType.MONTHS, deposit.getWithdrawalOption());
    }
}

class OpenFileTestHelper extends OpenFile {
    public static Object openCreditSave(JsonObject jsonObject) throws Exception {
        var method = OpenFile.class.getDeclaredMethod("openCreditSave", JsonObject.class);
        method.setAccessible(true);
        return method.invoke(null, jsonObject);
    }
    public static Object openDepositSave(JsonObject jsonObject) throws Exception {
        var method = OpenFile.class.getDeclaredMethod("openDepositSave", JsonObject.class);
        method.setAccessible(true);
        return method.invoke(null, jsonObject);
    }
}