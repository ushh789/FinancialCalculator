package com.netrunners.financialcalculator.VisualInstruments.MenuActions;

import com.netrunners.financialcalculator.LogicalInstrumnts.CurrencyConverter.Converter;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
        Optional<String> result = openCurrencySelector();
        if (result.isPresent()) {
            String selectedCurrency = result.get();
            instance.changeCurrency(selectedCurrency);
        }
    }

    public static void updateResultTable(String userSelectedCurrency, float loan, TableColumn<Object[], String> investmentLoanColumn, TableColumn<Object[], String> periodProfitLoanColumn, TableColumn<Object[], String> totalColumn, TableColumn<Object[], String> periodPercentsColumn, TableView<Object[]> resultTable) {
        Optional<String> result = openCurrencySelector();
        if (result.isPresent()) {
            String selectedConvertCurrency = result.get();
            float rate = Converter.getRateByCC(Converter.getCC(userSelectedCurrency)) / Converter.getRateByCC(Converter.getCC(selectedConvertCurrency));
            ObservableList<Object[]> investmentLoanColumnItems = investmentLoanColumn.getTableView().getItems();
            ObservableList<Object[]> periodProfitLoanColumnItems = periodProfitLoanColumn.getTableView().getItems();
            ObservableList<Object[]> totalColumnItems = totalColumn.getTableView().getItems();

            for (Object[] item : investmentLoanColumnItems) {
                item[1] = extractFloatValue((String) item[1]) * rate;
                String newValue = String.format("%.2f", item[1]) + selectedConvertCurrency;
                item[1] = newValue;
                if (!investmentLoanColumnItems.isEmpty()) {
                    if (loan == 0) {
                        float tempInvest = extractFloatValue((String) investmentLoanColumnItems.get(0)[1]);
                    } else {
                        loan = extractFloatValue((String) investmentLoanColumnItems.get(0)[1]);
                    }

                }
            }
            for (Object[] item : periodProfitLoanColumnItems) {
                item[2] = extractFloatValue((String) item[2]) * rate;
                String newValue = String.format("%.2f", item[2]) + selectedConvertCurrency;
                item[2] = newValue;
            }
            for (Object[] item : totalColumnItems) {
                item[3] = extractFloatValue((String) item[3]) * rate;
                String newValue = String.format("%.2f", item[3]) + selectedConvertCurrency;
                item[3] = newValue;
            }

            if (periodPercentsColumn.isVisible()) {
                ObservableList<Object[]> periodPercentsColumnItems = periodPercentsColumn.getTableView().getItems();
                for (Object[] item : periodPercentsColumnItems) {
                    item[4] = extractFloatValue((String) item[4]) * rate;
                    String newValue = String.format("%.2f", item[4]) + selectedConvertCurrency;
                    item[4] = newValue;
                }
            }
            resultTable.refresh();
        }
    }

    private static Optional<String> openCurrencySelector() {
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

        return dialog.showAndWait();
    }

    private static float extractFloatValue(String cellValue) {
        String numericValue = cellValue.replace(',', '.');
        numericValue = numericValue.substring(0, numericValue.length() - 1);
        return Float.parseFloat(numericValue);
    }
}
