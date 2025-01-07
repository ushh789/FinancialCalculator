package com.netrunners.financialcalculator.errorhandling;

import javafx.scene.control.*;

import static com.netrunners.financialcalculator.constants.StringConstants.EMPTY_STRING;
import static com.netrunners.financialcalculator.constants.StringConstants.ERROR_STYLE;

public class ErrorChecker {

    public static boolean areFieldsValidInDeposit(TextField investInput, TextField depositAnnualPercentInput, MenuButton depositWithdrawalOption, DatePicker startDate, DatePicker endDate, DatePicker earlyWithdrawalDate, CheckBox earlyWithdrawal) {
        boolean investValid = InputFieldErrors.checkIfCorrectNumberGiven(investInput);
        boolean annualPercentValid = InputFieldErrors.checkIfCorrectPercentGiven(depositAnnualPercentInput);
        boolean withdrawalOptionValid = InputFieldErrors.withdrawalOptionIsSelected(depositWithdrawalOption);

        checkInvestmentField(investInput, investValid);
        checkAnnualPercentField(depositAnnualPercentInput, annualPercentValid);
        checkWithdrawalOptionField(depositWithdrawalOption, withdrawalOptionValid);
        checkDateField(startDate);
        checkDateField(endDate);
        checkEarlyWithdrawalField(earlyWithdrawal, earlyWithdrawalDate);

        return investValid && annualPercentValid && withdrawalOptionValid && startDate.getValue() != null && endDate.getValue() != null && (!earlyWithdrawal.isSelected() || earlyWithdrawalDate.getValue() != null);
    }

    public static boolean areFieldsValidInCredit(TextField creditAmount, TextField creditAnnualPercent, MenuButton paymentOption, DatePicker creditStartDate, DatePicker creditFirstPaymentDate, CheckBox paymentHolidays, DatePicker creditHolidaysStartDate, DatePicker creditHolidaysEndDate) {
        boolean creditAmountValid = InputFieldErrors.checkIfCorrectNumberGiven(creditAmount);
        boolean creditAnnualPercentValid = InputFieldErrors.checkIfCorrectPercentGiven(creditAnnualPercent);
        boolean creditPaymentOptionValid = InputFieldErrors.paymentOptionIsSelected(paymentOption);

        checkInvestmentField(creditAmount, creditAmountValid);
        checkAnnualPercentField(creditAnnualPercent, creditAnnualPercentValid);
        checkWithdrawalOptionField(paymentOption, creditPaymentOptionValid);
        checkDateField(creditStartDate);
        checkDateField(creditFirstPaymentDate);
        checkEarlyWithdrawalField(paymentHolidays, creditHolidaysStartDate);

        if (paymentHolidays.isSelected() && creditHolidaysEndDate.getValue() == null) {
            highlightError(creditHolidaysEndDate);
        } else {
            removeHighlight(creditHolidaysEndDate);
        }

        return creditAmountValid && creditAnnualPercentValid && creditPaymentOptionValid && creditStartDate.getValue() != null && creditFirstPaymentDate.getValue() != null && (!paymentHolidays.isSelected() || creditHolidaysStartDate.getValue() != null) && (!paymentHolidays.isSelected() || creditHolidaysEndDate.getValue() != null);
    }

    private static void checkInvestmentField(TextField field, boolean isValid) {
        if (!isValid) {
            highlightError(field);
            field.setText(EMPTY_STRING);
        } else {
            removeHighlight(field);
        }
    }

    private static void checkAnnualPercentField(TextField field, boolean isValid) {
        if (!isValid) {
            highlightError(field);
            field.setText(EMPTY_STRING);
        } else {
            removeHighlight(field);
        }
    }

    private static void checkWithdrawalOptionField(MenuButton field, boolean isValid) {
        if (!isValid) {
            highlightError(field);
        } else {
            removeHighlight(field);
        }
    }

    private static void checkDateField(DatePicker field) {
        if (field.getValue() == null) {
            highlightError(field);
        } else {
            removeHighlight(field);
        }
    }

    private static void checkEarlyWithdrawalField(CheckBox checkBox, DatePicker datePicker) {
        if (checkBox.isSelected() && datePicker.getValue() == null) {
            highlightError(datePicker);
        } else {
            removeHighlight(datePicker);
        }
    }

    public static void highlightError(Control field) {
        field.setStyle(ERROR_STYLE);
        field.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                removeHighlight(field);
            }
        });
    }

    public static void removeHighlight(Control field) {
        field.setStyle(null);
    }
}