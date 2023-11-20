package com.netrunners.financialcalculator.VisualInstruments.MenuActions;

import java.io.*;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import javafx.beans.binding.StringBinding;

public class LanguageManager {
    private static LanguageManager instance;
    private ObservableResourceFactory resourceFactory;
    private Properties properties;
    private static final String PROPERTIES_FILE = "config.properties";


    private LanguageManager() {
        resourceFactory = new ObservableResourceFactory();
        properties = new Properties();
        loadLanguage();
    }
    public Locale getLocale() {
        return resourceFactory.getResources().getLocale();
    }

    public static LanguageManager getInstance() {
        if (instance == null) {
            instance = new LanguageManager();
        }
        return instance;
    }
    private void loadLanguage() {
        try (InputStream input = new FileInputStream(PROPERTIES_FILE)) {
            properties.load(input);
            if (properties.getProperty("language") != null) {
                resourceFactory.setResources(ResourceBundle.getBundle("com/netrunners/financialcalculator/assets/messages", new Locale(properties.getProperty("language"))));
            } else {
                resourceFactory.setResources(ResourceBundle.getBundle("com/netrunners/financialcalculator/assets/messages", new Locale("en")));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void saveLanguage() {
        try (OutputStream output = new FileOutputStream(PROPERTIES_FILE)) {
            properties.setProperty("language", resourceFactory.getResources().getLocale().getLanguage());
            properties.store(output, null);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void changeLanguage(Locale locale) {
        resourceFactory.setResources(ResourceBundle.getBundle("com/netrunners/financialcalculator/assets/messages", locale));
    }

    public StringBinding getStringBinding(String key) {
        return resourceFactory.getStringBinding(key);
    }

    public static int checkOption(String chosenOption){
        LanguageManager languageManager = LanguageManager.getInstance();
        String option1 = languageManager.getStringBinding("Option1").get();
        String option2 = languageManager.getStringBinding("Option2").get();
        String option3 = languageManager.getStringBinding("Option3").get();
        String option4 = languageManager.getStringBinding("Option4").get();

        if (chosenOption.equals(option1)) {
            return 1;
        } else if (chosenOption.equals(option2)) {
            return 2;
        } else if (chosenOption.equals(option3)) {
            return 3;
        } else if (chosenOption.equals(option4)) {
            return 4;
        } else {
            return -1000;
        }
    }
}


