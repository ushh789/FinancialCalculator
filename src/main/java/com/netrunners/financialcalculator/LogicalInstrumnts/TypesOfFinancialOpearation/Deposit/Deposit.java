package com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation.Deposit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.netrunners.financialcalculator.LogicalInstrumnts.FileInstruments.LogHelper;
import com.netrunners.financialcalculator.LogicalInstrumnts.FileInstruments.Savable;
import com.netrunners.financialcalculator.LogicalInstrumnts.TimeFunctions.LocalDateAdapter;
import com.netrunners.financialcalculator.MenuControllers.ResultTableController;
import com.netrunners.financialcalculator.StartMenu;
import com.netrunners.financialcalculator.VisualInstruments.MenuActions.LanguageManager;
import javafx.beans.binding.StringBinding;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.logging.Level;

public abstract class Deposit implements Savable {
    protected float investment;
    protected float annualPercent;
    protected String currency;
    protected LocalDate startDate;
    protected LocalDate endDate;
    protected int withdrawalOption;
    protected boolean earlyWithdrawal;
    protected LocalDate earlyWithdrawalDate;


    public float countProfit() {
        return investment * (1f / 365f) * (annualPercent / 100f);
    }

    public Deposit(float investment, String currency, float annualPercent, LocalDate startDate, LocalDate endDate, boolean earlyWithdrawal, LocalDate earlyWithdrawalDate, int withdrawalOption) {
        this.investment = investment;
        this.currency = currency;
        this.annualPercent = annualPercent;
        this.startDate = startDate;
        this.endDate = endDate;
        this.earlyWithdrawal = earlyWithdrawal;
        this.earlyWithdrawalDate = earlyWithdrawalDate;
        this.withdrawalOption = withdrawalOption;
    }

    public Deposit(float investment, String currency, float annualPercent, LocalDate startDate, LocalDate endDate, boolean earlyWithdrawal, int withdrawalOption) {
        this.investment = investment;
        this.currency = currency;
        this.annualPercent = annualPercent;
        this.startDate = startDate;
        this.endDate = endDate;
        this.earlyWithdrawal = earlyWithdrawal;
        this.withdrawalOption = withdrawalOption;
    }

    protected JsonObject getJsonObject() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("investment", this.investment);
        jsonObject.addProperty("annualPercent", this.annualPercent);
        jsonObject.addProperty("currency", this.currency);
        jsonObject.addProperty("startDate", this.startDate.toString());
        jsonObject.addProperty("endDate", this.endDate.toString());
        jsonObject.addProperty("withdrawalOption", this.withdrawalOption);
        if (this.earlyWithdrawal) {
            jsonObject.addProperty("earlyWithdrawalDate", this.earlyWithdrawalDate.toString());
        } else {
            jsonObject.addProperty("earlyWithdrawalDate", "None");
        }
        jsonObject.addProperty("earlyWithdrawal", this.earlyWithdrawal);
        return jsonObject;
    }

    @Override
    public void save() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        JsonObject jsonObject = getJsonObject();
        String json = gson.toJson(jsonObject);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(LanguageManager.getInstance().getStringBinding("saveButton").get());
        File initialDirectory = new File("saves/");
        fileChooser.setInitialDirectory(initialDirectory);
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JSON Files", "*.json"));
        File file = fileChooser.showSaveDialog(null);

        if(file != null){
            try {
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(json);
                fileWriter.close();
            } catch (IOException e) {
               LogHelper.log(Level.SEVERE, "Error while saving Deposit to json", e);
            }
        }
    }

    public void sendDepositToResultTable() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/netrunners/financialcalculator/ResultTable.fxml"));
        try {
            Parent root = loader.load();
            ResultTableController resultTableController = loader.getController();
            resultTableController.updateTable(this);

            Stage stage = new Stage();
            stage.setTitle(LanguageManager.getInstance().getStringBinding("ResultTableLabel").get());
            Scene scene = new Scene(root);
            scene.getStylesheets().add(StartMenu.currentTheme);
            stage.setScene(scene);
            StartMenu.openScenes.add(scene);
            stage.getIcons().add(new Image("file:src/main/resources/com/netrunners/financialcalculator/assets/Logo.png"));
            stage.setMaxHeight(720);
            stage.setMaxWidth(620);
            stage.setMinHeight(820);
            stage.setMinWidth(620);
            stage.show();
        } catch (Exception e) {
            LogHelper.log(Level.SEVERE, "Error while sending Deposit to result table", e);
        }
    }

    public String getNameOfWithdrawalType() {
        LanguageManager languageManager = LanguageManager.getInstance();
        switch (withdrawalOption) {
            case 1 -> {
                return "Months";
            }
            case 2 -> {
                return "Quarters";
            }
            case 3 -> {
                return "Years";
            }
            case 4 -> {
                return "EndofTerm";
            }
        }
        return languageManager.getStringBinding("None").get();
    }




    public float getInvestment() {
        return investment;
    }

    public void setInvestment(float investment) {
        this.investment = investment;
    }

    public float getAnnualPercent() {
        return annualPercent;
    }

    public String getCurrency() {
        return currency;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public int getWithdrawalOption() {
        return withdrawalOption;
    }

    public boolean isEarlyWithdrawal() {
        return earlyWithdrawal;
    }

    public LocalDate getEarlyWithdrawalDate() {
        return earlyWithdrawalDate;
    }
}