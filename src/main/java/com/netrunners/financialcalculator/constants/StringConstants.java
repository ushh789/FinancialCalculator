package com.netrunners.financialcalculator.constants;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StringConstants {

    public static final String EMPTY_STRING = "";
    public static final String ASTERISK = "*";
    public static final String COMMA = ",";
    public static final String DOT = ".";
    public static final String SEMI_COLON = ";";

    // Styles
    public static final String ERROR_STYLE = "-fx-border-color:red;-fx-border-radius:3px";
    public static final String DATE_PICKER_RESTRICTION_LIGHT = "-fx-background-color: dimgray;-fx-text-fill: white;";
    public static final String DATE_PICKER_RESTRICTION_DARK = "-fx-background-color: gray;";

    // regex
    public static final String REGEX_ONLY_NUMBERS = "^[0-9]+(\\.[0-9]{1,4})?$";
    public static final String FORMATTED_TIME_NOW = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));

    // Error messages
    public static final String ERROR_OPENING_FXML = "Error loading {} window:";
    public static final String ERROR_OPENING_RESULT = "Error sending to result table";
    public static final String ERROR_LOADING_SAVE = "Error loading save ";
    public static final String ERROR_OPENING_FILE = "Error while opening file: {}";
    public static final String ERROR_SAVING_FILE = "Error while saving file: {}";
    public static final String ERROR_SELECTED_FILE_IS_NULL = "Selected file is null";

    // Info messages
    public static final String INFO_FILE_SAVED = "File {} saved";
    public static final String INFO_FILE_OPENED = "File {} opened";
    public static final String INFO_FILE_CHOSEN = "File {} chosen";
    public static final String LANGUAGE_CHANGED = "Language changed to {}";

}
