package com.netrunners.financialcalculator.constants;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StringConstants {

    public static final String EMPTY_STRING = "";

    // Styles
    public static final String ERROR_STYLE = "-fx-border-color:red;-fx-border-radius:3px";
    public static final String DATE_PICKER_RESTRICTION_LIGHT = "-fx-background-color: dimgray;-fx-text-fill: white;";
    public static final String DATE_PICKER_RESTRICTION_DARK = "-fx-background-color: gray;";

    // regex
    public static final String REGEX_ONLY_NUMBERS = "^[0-9]+(\\.[0-9]{1,4})?$";
    public static final String FORMATTED_TIME_NOW = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));

}
