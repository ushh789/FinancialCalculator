package com.netrunners.financialcalculator.errorhandling;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import static com.netrunners.financialcalculator.constants.FileConstants.LANGUAGES;
import static com.netrunners.financialcalculator.constants.StringConstants.ERROR_OPENING_FILE;

public class InputFieldErrors {
    private static List<String> paymentOptions;
    private static List<String> withdrawalOptions;

    private static final Logger logger = LoggerFactory.getLogger(InputFieldErrors.class);

    static {
        Gson gson = new Gson();
        try {
            InputStreamReader reader = new InputStreamReader(new FileInputStream(LANGUAGES), StandardCharsets.UTF_8);
            Type type = new TypeToken<Map<String, List<String>>>() {
            }.getType();
            Map<String, List<String>> map = gson.fromJson(reader, type);
            paymentOptions = map.get("paymentOption");
            withdrawalOptions = map.get("depositWithdrawalOption");
        } catch (IOException e) {
            logger.error(ERROR_OPENING_FILE, LANGUAGES, e);
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
