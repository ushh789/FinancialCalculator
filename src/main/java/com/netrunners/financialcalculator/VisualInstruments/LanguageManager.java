package com.netrunners.financialcalculator.VisualInstruments;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LanguageManager {
    private static LanguageManager instance;
    private final StringProperty currentLanguage;
    private Map<String, List<String>> translations;

    private LanguageManager() {
        currentLanguage = new SimpleStringProperty("en");
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, List<String>>>(){}.getType();
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream("languages.json"), StandardCharsets.UTF_8)) {
            translations = gson.fromJson(reader, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static LanguageManager getInstance() {
        if (instance == null) {
            instance = new LanguageManager();
        }
        return instance;
    }

    public void setLanguage(String language) {
        this.currentLanguage.set(language);
    }

    public String getTranslation(String key) {
        Map<String, Integer> languageIndexMap = Map.of(
                "en", 0,
                "uk", 1,
                "es", 2,
                "fr", 3,
                "de", 4,
                "cs", 5,
                "pl", 6,
                "nl", 7,
                "ja", 8,
                "zh", 9
        );
        int index = languageIndexMap.getOrDefault(currentLanguage.get(), -1);
        return translations.get(key).get(index);
    }

    public StringProperty languageProperty() {
        return currentLanguage;
    }
}

