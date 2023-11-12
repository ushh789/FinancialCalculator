package com.netrunners.financialcalculator.ErrorHandling;

import javafx.scene.control.*;

public class InputFieldErrors {
    public static boolean checkIfCorrectNumberGiven(TextField field) {
        String input = field.getText();
        return !(input.isEmpty()) && RegexChecker.checkIfOnlyNumbersGiven(field.getText());
    }
    public static boolean checkIfCorrectPercentGiven(TextField field) {
        String input = field.getText();
        return !(input.isEmpty()) && RegexChecker.checkIfOnlyNumbersGiven(field.getText()) && Float.parseFloat(input) <= 100;
    }
    public static boolean withdrawalOptionIsSelected(MenuButton menuButton) {
        return !(menuButton.getText().contains("Withdrawal option"));
    }

    public static boolean paymentOptionIsSelected(MenuButton menuButton) {
        return !(menuButton.getText().contains("Payment option"));
    }

}
