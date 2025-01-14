package com.netrunners.financialcalculator.logic.currecnyconverter;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.netrunners.financialcalculator.errorhandling.exceptions.CurrencyConverterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.netrunners.financialcalculator.constants.FileConstants.NBU_EXCHANGE_CURRENCIES_URL;

public class Converter {
    private static final Logger logger = LoggerFactory.getLogger(Converter.class);

    public static JsonArray getRates() {
        try {
            URL url = new URL(NBU_EXCHANGE_CURRENCIES_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            if (responseCode != 200) {
                throw new CurrencyConverterException("Failed retrieve data from National Bank of Ukraine, response code: {}" + responseCode);
            }
            logger.info("Successfully retrieved exchange rates from NBU");

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

        } catch (CurrencyConverterException | IOException e) {
            logger.error("Failed to retrieve exchange rates:", e);
            return null;
        }
    }

    public static float getRateByCC(String cc) {
        try {
            JsonArray rates = getRates();
            if (rates == null) {
                throw new CurrencyConverterException("Failed to retrieve exchange rates");
            }
            if (cc.equals("UAH")) {
                return 1;
            }
            for (JsonElement element : rates) {
                JsonObject obj = element.getAsJsonObject();
                if (obj.get("cc").getAsString().equals(cc)) {
                    return obj.get("rate").getAsFloat();
                }
            }
            throw new CurrencyConverterException("Currency code " + cc + " not found");
        } catch (CurrencyConverterException e) {
            logger.error(e.getMessage(), e);
            return -1;
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
            case "zł" -> {
                return "PLN";
            }
            case "₴" -> {
                return "UAH";
            }
            default -> {
                return "ERROR";
            }
        }
    }
}

