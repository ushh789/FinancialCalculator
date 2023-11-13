package com.netrunners.financialcalculator.ErrorHandling;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.scene.control.*;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class InputFieldErrors {
    private static List<String> paymentOptions;
    private static List<String> withdrawalOptions;

    static {
        Gson gson = new Gson();
        try {
            InputStreamReader reader = new InputStreamReader(new FileInputStream("languages.json"), StandardCharsets.UTF_8);
            Type type = new TypeToken<Map<String, List<String>>>(){}.getType();
            Map<String, List<String>> map = gson.fromJson(reader, type);
            paymentOptions = map.get("paymentOption");
            withdrawalOptions = map.get("depositWithdrawalOption");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static boolean checkIfCorrectNumberGiven(TextField field) {
        String input = field.getText();
        return !(input.isEmpty()) && RegexChecker.checkIfOnlyNumbersGiven(field.getText());
    }
    public static boolean checkIfCorrectPercentGiven(TextField field) {
        String input = field.getText();
        return !(input.isEmpty()) && RegexChecker.checkIfOnlyNumbersGiven(field.getText()) && Float.parseFloat(input) <= 100;
    }
    public static boolean withdrawalOptionIsSelected(MenuButton menuButton) {
        for (String option : withdrawalOptions) {
            if (menuButton.getText().contains(option)) {
                return false;
            }
        }
        return true;
    }

    public static boolean paymentOptionIsSelected(MenuButton menuButton) {
        for (String option : paymentOptions) {
            if (menuButton.getText().contains(option)) {
                return false;
            }
        }
        return true;
    }

}
