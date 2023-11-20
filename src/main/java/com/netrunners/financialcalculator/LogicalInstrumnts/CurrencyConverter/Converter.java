package com.netrunners.financialcalculator.LogicalInstrumnts.CurrencyConverter;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.netrunners.financialcalculator.LogicalInstrumnts.FileInstruments.LogHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;

public class Converter {
    public static JsonArray getRates() throws IOException {
        URL url = new URL("https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode != 200) {
            LogHelper.log(Level.WARNING, "Failed to get json from National Bank of Ukraine, response code: " + responseCode, null);
            return null;
        }

        InputStream inputStream = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder json = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            json.append(line);
        }
        reader.close();
        inputStream.close();
        Gson gson = new Gson();

        return gson.fromJson(json.toString(), JsonArray.class).getAsJsonArray();
    }

    public static float getRateByCC(String cc) {
        try {
            JsonArray rates = getRates();
            if (rates == null) {
                LogHelper.log(Level.WARNING, "Failed to retrieve exchange rates", null);
                throw new RuntimeException("Failed to retrieve exchange rates");
            }
            if (cc.equals("UAH")){
                return 1;
            }
            for (JsonElement element : rates) {
                JsonObject obj = element.getAsJsonObject();
                if (obj.get("cc").getAsString().equals(cc)) {
                    return obj.get("rate").getAsFloat();
                }
            }
            LogHelper.log(Level.WARNING, "Currency code " + cc + " not found", null);
            throw new IllegalArgumentException("Currency code " + cc + " not found");
        } catch (IOException e) {
            LogHelper.log(Level.WARNING, "IOException in Converter.getRateByCC, caused by input: " + cc, null);
            throw new RuntimeException(e);
        }
    }

    public static String getCC(String userSelectedCurrency) {
        switch (userSelectedCurrency) {
            case "$" -> {
                return "USD";
            }
            case "£" -> {
                return "GBP";
            }
            case "€" -> {
                return "EUR";
            }
            case "zł" ->{
                return "PLN";
            }
            case "₴" ->{
                return "UAH";
            }
            default -> {
                return "ERROR";
            }
        }
    }
}

