package com.netrunners.financialcalculator.logic.files;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.netrunners.financialcalculator.logic.entity.credit.Credit;
import com.netrunners.financialcalculator.logic.entity.credit.CreditWithHolidays;
import com.netrunners.financialcalculator.logic.entity.credit.CreditWithoutHolidays;
import com.netrunners.financialcalculator.logic.entity.deposit.CapitalisedDeposit;
import com.netrunners.financialcalculator.logic.entity.deposit.Deposit;
import com.netrunners.financialcalculator.logic.entity.deposit.UncapitalisedDeposit;
import com.netrunners.financialcalculator.ui.FilesActions;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static com.netrunners.financialcalculator.constants.FileConstants.JSON_EXTENSION;
import static com.netrunners.financialcalculator.constants.FileConstants.JSON_FILE;
import static com.netrunners.financialcalculator.constants.StringConstants.ASTERISK;

public class OpenFile {

    public static void openFromSave() {
        List<String> fileTypes = new ArrayList<>();
        fileTypes.add(JSON_FILE);

        List<String> extensions = new ArrayList<>();
        extensions.add(ASTERISK + JSON_EXTENSION);

        File selectedFile = FilesActions.showFileChooser("ChooseOpenFile", fileTypes, extensions);
        if (selectedFile != null) {
            try (FileReader reader = new FileReader(selectedFile)) {
                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);

                switch (jsonObject.get("operation").getAsString()) {
                    case "Credit" -> {
                        Credit credit = openCreditSave(jsonObject);
                        if (credit == null) {
                            throw new Exception(MessageFormat.format("Error while opening credit save-file: {0}", selectedFile.getName()));
                        } else {
                            credit.sendToResultTable();
                        }
                    }
                    case "Deposit" -> {
                        Deposit deposit = openDepositSave(jsonObject);
                        if (deposit == null) {
                            throw new Exception(MessageFormat.format("Error while opening deposit save-file: {0}", selectedFile.getName()));
                        } else {
                            deposit.sendToResultTable();
                        }
                    }
                    default ->
                            throw new Exception(MessageFormat.format("Error while opening file: {0}", selectedFile.getName()));
                }
            } catch (IOException | JsonParseException e) {
                LogHelper.log(Level.SEVERE, "Error while opening file", e);
            } catch (Exception e) {
                LogHelper.log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }

    private static Credit openCreditSave(JsonObject jsonObject) {
        if (jsonObject.get("type").getAsString().equals("WithHolidays")) {
            return new CreditWithHolidays(jsonObject);
        } else if (jsonObject.get("type").getAsString().equals("WithoutHolidays")) {
            return new CreditWithoutHolidays(jsonObject);
        } else {
            return null;
        }
    }

    private static Deposit openDepositSave(JsonObject jsonObject) {
        String type = jsonObject.get("type").getAsString();
        if (type.equals("Capitalized")) {
            return new CapitalisedDeposit(jsonObject);
        } else if (type.equals("Uncapitalized")) {
            return new UncapitalisedDeposit(jsonObject);
        } else {
            return null;
        }
    }

}
