package com.netrunners.financialcalculator.ErrorHandling;


import javafx.scene.control.*;


public class ErrorChecker {

    public static boolean areFieldsValidInDeposit(TextField investInput, TextField depositAnnualPercentInput, MenuButton depositWithdrawalOption, DatePicker startDate, DatePicker endDate, DatePicker earlyWithdrawalDate, CheckBox earlyWithdrawal) {
        boolean investValid = InputFieldErrors.checkIfCorrectNumberGiven(investInput);
        boolean annualPercentValid = InputFieldErrors.checkIfCorrectPercentGiven(depositAnnualPercentInput);
        boolean withdrawalOptionValid = InputFieldErrors.withdrawalOptionIsSelected(depositWithdrawalOption);

        if (!investValid) {
            highlightError(investInput);
            investInput.setText("");
        } else {
            removeHighlight(investInput);
        }

        if (!annualPercentValid) {
            highlightError(depositAnnualPercentInput);
            depositAnnualPercentInput.setText("");
        } else {
            removeHighlight(depositAnnualPercentInput);
        }

        if (!withdrawalOptionValid) {
            highlightError(depositWithdrawalOption);
        } else {
            removeHighlight(depositWithdrawalOption);
        }
        if (startDate.getValue() == null) {
            highlightError(startDate);
        } else {
            removeHighlight(startDate);
        }
        if (endDate.getValue() == null) {
            highlightError(endDate);
        } else {
            removeHighlight(endDate);
        }
        if (earlyWithdrawal.isSelected() && earlyWithdrawalDate.getValue() == null) {
            highlightError(earlyWithdrawalDate);
        } else {
            removeHighlight(earlyWithdrawalDate);
        }
        return investValid && annualPercentValid && withdrawalOptionValid && startDate.getValue() != null && endDate.getValue() != null && (!earlyWithdrawal.isSelected() || earlyWithdrawalDate.getValue() != null);
    }

    public static boolean areFieldsValidInCredit(TextField creditAmount, TextField creditAnnualPercent, MenuButton paymentOption, DatePicker creditStartDate, DatePicker creditFirstPaymentDate, CheckBox paymentHolidays, DatePicker creditHolidaysStartDate, DatePicker creditHolidaysEndDate) {
        boolean creditAmountValid = InputFieldErrors.checkIfCorrectNumberGiven(creditAmount);
        boolean creditAnnualPercentValid = InputFieldErrors.checkIfCorrectPercentGiven(creditAnnualPercent);
        boolean creditPaymentOptionValid = InputFieldErrors.paymentOptionIsSelected(paymentOption);

        if (!creditAmountValid) {
            highlightError(creditAmount);
            creditAmount.setText("");
        } else {
            removeHighlight(creditAmount);
        }

        if (!creditAnnualPercentValid) {
            highlightError(creditAnnualPercent);
            creditAnnualPercent.setText("");
        } else {
            removeHighlight(creditAnnualPercent);
        }

        if (!creditPaymentOptionValid) {
            highlightError(paymentOption);
        } else {
            removeHighlight(paymentOption);
        }
        if (creditStartDate.getValue() == null) {
            highlightError(creditStartDate);
        } else {
            removeHighlight(creditStartDate);
        }
        if (creditFirstPaymentDate.getValue() == null) {
            highlightError(creditFirstPaymentDate);
        } else {
            removeHighlight(creditFirstPaymentDate);
        }
        if (paymentHolidays.isSelected() && creditHolidaysStartDate.getValue() == null) {
            highlightError(creditHolidaysStartDate);
        } else {
            removeHighlight(creditHolidaysStartDate);
        }
        if (paymentHolidays.isSelected() && creditHolidaysEndDate.getValue() == null) {
            highlightError(creditHolidaysEndDate);
        } else {
            removeHighlight(creditHolidaysEndDate);
        }
        return creditAmountValid && creditAnnualPercentValid && creditPaymentOptionValid && creditStartDate.getValue() != null && creditFirstPaymentDate.getValue() != null && (!paymentHolidays.isSelected() || creditHolidaysStartDate.getValue() != null) && (!paymentHolidays.isSelected() || creditHolidaysEndDate.getValue() != null);
    }

    public static void highlightError(Control field) {
        field.setStyle("-fx-border-color:red;-fx-border-radius:3px");
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
