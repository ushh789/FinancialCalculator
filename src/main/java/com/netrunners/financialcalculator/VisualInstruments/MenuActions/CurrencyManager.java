package com.netrunners.financialcalculator.VisualInstruments.MenuActions;

import javafx.scene.control.ChoiceDialog;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.netrunners.financialcalculator.VisualInstruments.MenuActions.FileConstants.LOGO;

public class CurrencyManager {
    private static CurrencyManager instance;
    private String currency;

    private CurrencyManager() {
        currency = "$";
    }

    public static CurrencyManager getInstance() {
        if (instance == null) {
            instance = new CurrencyManager();
        }
        return instance;
    }

    public String getCurrency() {
        return currency;
    }

    public void changeCurrency(String currency) {
        this.currency = currency;
    }
    public static void handleCurrencySelection() {
        List<String> choices = new ArrayList<>();
        choices.add("₴");
        choices.add("$");
        choices.add("£");
        choices.add("€");

        CurrencyManager currencyManager = CurrencyManager.getInstance();
        String defaultCurrency = currencyManager.getCurrency();

        ChoiceDialog<String> dialog = new ChoiceDialog<>(defaultCurrency, choices);
        dialog.setTitle(LanguageManager.getInstance().getStringBinding("CurrencyTitle").get());
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(LOGO));
        dialog.setHeaderText(null);
        dialog.setContentText(LanguageManager.getInstance().getStringBinding("ChooseCurrency").get());

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String selectedCurrency = result.get();
            currencyManager.changeCurrency(selectedCurrency);
        }
    }
}
