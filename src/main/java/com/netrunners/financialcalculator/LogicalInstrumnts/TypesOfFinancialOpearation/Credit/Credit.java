package com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation.Credit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.netrunners.financialcalculator.LogicalInstrumnts.FileInstruments.LogHelper;
import com.netrunners.financialcalculator.LogicalInstrumnts.FileInstruments.Savable;
import com.netrunners.financialcalculator.LogicalInstrumnts.TimeFunctions.DateTimeFunctions;
import com.netrunners.financialcalculator.LogicalInstrumnts.TimeFunctions.LocalDateAdapter;

import com.netrunners.financialcalculator.MenuControllers.ResultTableController;
import com.netrunners.financialcalculator.StartMenu;
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


public class Credit implements Savable {
    protected float loan;
    protected String currency;
    protected float annualPercent;
    protected LocalDate startDate;
    protected LocalDate endDate;
    protected int paymentType;
    protected int contractDuration;

    public Credit(float loan, String currency, float annualPercent, LocalDate startDate, LocalDate endDate, int paymentType) {
        this.loan = loan;
        this.currency = currency;
        this.annualPercent = annualPercent;
        this.startDate = startDate;
        this.endDate = endDate;
        this.paymentType = paymentType;
        this.contractDuration = DateTimeFunctions.countDaysBetweenDates(startDate, endDate);
    }

    public float countLoan() {
        return loan * (1f / 365f) * (annualPercent / 100f);
    }

    public float countCreditBodyPerDay(){
        return loan/contractDuration;
    }


    public void save() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        JsonObject jsonObject = getJsonObject();
        String json = gson.toJson(jsonObject);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Data");
        File initialDirectory = new File("saves/");
        fileChooser.setInitialDirectory(initialDirectory);
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JSON Files", "*.json"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(json);
            } catch (IOException e) {
                LogHelper.log(Level.SEVERE, "Error while saving Credit to json", e);
            }
        }
    }

    public void sendCreditToResultTable() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/netrunners/financialcalculator/ResultTable.fxml"));
        try {
            Parent root = loader.load();
            ResultTableController resultTableController = loader.getController();
            resultTableController.updateTable(this);

            Stage stage = new Stage();
            stage.setTitle("Result");
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
            LogHelper.log(Level.SEVERE, "Error while sending Credit to result table", e);
        }
    }

    protected JsonObject getJsonObject() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("loan", this.loan);
        jsonObject.addProperty("annualPercent", this.annualPercent);
        jsonObject.addProperty("currency", this.currency);
        jsonObject.addProperty("paymentType", this.paymentType);
        jsonObject.addProperty("startDate", this.startDate.toString());
        jsonObject.addProperty("endDate", this.endDate.toString());
        return jsonObject;
    }

    public float getLoan() {
        return loan;
    }

    public String getCurrency() {
        return currency;
    }

    public float getAnnualPercent() {
        return annualPercent;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public int getPaymentType() {
        return paymentType;
    }

    public void setLoan(float loan) {
        this.loan = loan;
    }

    public String getNameOfPaymentType() {
        switch (paymentType) {
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
                return "Periods";
            }
        }
        return "None";
    }
}
