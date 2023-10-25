package com.netrunners.financialcalculator.ErrorHandling;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexChecker {
    protected static boolean checkIfOnlyNumbersGiven(String input){
        Pattern pattern = Pattern.compile("^[0-9]+(\\.[0-9]{1,4})?$");
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }
}
