package com.netrunners.financialcalculator.errorhandling;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.netrunners.financialcalculator.constants.StringConstants.REGEX_ONLY_NUMBERS;

public class RegexChecker {
    protected static boolean checkIfOnlyNumbersGiven(String input) {
        Pattern pattern = Pattern.compile(REGEX_ONLY_NUMBERS);
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }
}
