package com.netrunners.financialcalculator.logic.files;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.netrunners.financialcalculator.errorhandling.exceptions.LoadSaveException;
import com.netrunners.financialcalculator.logic.entity.credit.Credit;
import com.netrunners.financialcalculator.logic.entity.credit.CreditWithHolidays;
import com.netrunners.financialcalculator.logic.entity.credit.CreditWithoutHolidays;
import com.netrunners.financialcalculator.logic.entity.deposit.CapitalisedDeposit;
import com.netrunners.financialcalculator.logic.entity.deposit.Deposit;
import com.netrunners.financialcalculator.logic.entity.deposit.UncapitalisedDeposit;
import com.netrunners.financialcalculator.ui.FilesActions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.netrunners.financialcalculator.constants.FileConstants.JSON_EXTENSION;
import static com.netrunners.financialcalculator.constants.FileConstants.JSON_FILE;
import static com.netrunners.financialcalculator.constants.StringConstants.*;

public class OpenFile {
    private static final Logger logger = LoggerFactory.getLogger(OpenFile.class);

    public static void openFromSave() throws LoadSaveException {
        List<String> fileTypes = new ArrayList<>();
        fileTypes.add(JSON_FILE);

        List<String> extensions = new ArrayList<>();
        extensions.add(ASTERISK + JSON_EXTENSION);

        File selectedFile = FilesActions.showFileChooser("ChooseOpenFile", fileTypes, extensions);
        if (selectedFile != null) {
            logger.info(INFO_FILE_CHOSEN, selectedFile.getName());
            try (FileReader reader = new FileReader(selectedFile)) {
                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);

                switch (jsonObject.get("operation").getAsString()) {
                    case "Credit" -> {
                        Credit credit = openCreditSave(jsonObject);
                        credit.sendToResultTable();
                    }
                    case "Deposit" -> {
                        Deposit deposit = openDepositSave(jsonObject);
                        deposit.sendToResultTable();
                    }
                    default -> throw new LoadSaveException(ERROR_LOADING_SAVE + selectedFile.getName());
                }
            } catch (IOException | JsonParseException | LoadSaveException e) {
                logger.error(ERROR_OPENING_FILE, selectedFile.getName(), e);
            }
            logger.info(INFO_FILE_OPENED, selectedFile.getName());
        }
    }

    private static Credit openCreditSave(JsonObject jsonObject) throws LoadSaveException {
        if (jsonObject.get("type").getAsString().equals("WithHolidays")) {
            return new CreditWithHolidays(jsonObject);
        } else if (jsonObject.get("type").getAsString().equals("WithoutHolidays")) {
            return new CreditWithoutHolidays(jsonObject);
        } else {
            throw new LoadSaveException(ERROR_LOADING_SAVE);
        }
    }

    private static Deposit openDepositSave(JsonObject jsonObject) throws LoadSaveException {
        String type = jsonObject.get("type").getAsString();
        if (type.equals("Capitalized")) {
            return new CapitalisedDeposit(jsonObject);
        } else if (type.equals("Uncapitalized")) {
            return new UncapitalisedDeposit(jsonObject);
        } else {
            throw new LoadSaveException(ERROR_LOADING_SAVE);
        }
    }

}
