package com.netrunners.financialcalculator.ErrorHandling;

import javafx.scene.control.*;

public class InputFieldErrors {
    public static boolean checkIfCorrectNumberGiven(TextField field){
        String input = field.getText();
        return !(input.isEmpty()) && RegexChecker.checkIfOnlyNumbersGiven(field.getText());
    }

}
