package com.netrunners.financialcalculator.ErrorHandling;

import javafx.scene.control.*;

import java.util.Objects;

public class InputFieldErrors {
    public static boolean checkIfCorrectNumberGiven(TextField field){
        String input = field.getText();
        return !(input.isEmpty()) && RegexChecker.checkIfOnlyNumbersGiven(field.getText());
    }
    public static boolean withdrawalOptionIsSelected(MenuButton menuButton){
        return !(menuButton.getText().contains("Withdrawal option"));
    }
}
