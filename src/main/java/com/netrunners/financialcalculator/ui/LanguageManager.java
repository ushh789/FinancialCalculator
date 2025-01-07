package com.netrunners.financialcalculator.ui;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

import com.netrunners.financialcalculator.logic.entity.OperationPeriodType;
import javafx.beans.binding.StringBinding;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import static com.netrunners.financialcalculator.constants.FileConstants.*;

public class LanguageManager {
    private static LanguageManager instance;
    private final ObservableResourceFactory resourceFactory;
    private final Properties properties;


    private LanguageManager() {
        resourceFactory = new ObservableResourceFactory();
        properties = new Properties();
        loadLanguage();
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
                resourceFactory.setResources(ResourceBundle.getBundle(MESSAGES_PATH, new Locale(properties.getProperty("language"))));
            } else {
                resourceFactory.setResources(ResourceBundle.getBundle(MESSAGES_PATH, new Locale("en")));
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
        resourceFactory.setResources(ResourceBundle.getBundle(MESSAGES_PATH, locale));
    }

    public StringBinding getStringBinding(String key) {
        return resourceFactory.getStringBinding(key);
    }

    public static OperationPeriodType checkOption(String chosenOption) {
        LanguageManager languageManager = LanguageManager.getInstance();
        String option1 = languageManager.getStringBinding("Option1").get();
        String option2 = languageManager.getStringBinding("Option2").get();
        String option3 = languageManager.getStringBinding("Option3").get();
        String option4 = languageManager.getStringBinding("Option4").get();

        if (chosenOption.equals(option1)) {
            return OperationPeriodType.MONTHS;
        } else if (chosenOption.equals(option2)) {
            return OperationPeriodType.QUARTERS;
        } else if (chosenOption.equals(option3)) {
            return OperationPeriodType.YEARS;
        } else if (chosenOption.equals(option4)) {
            return OperationPeriodType.END_OF_TERM;
        } else {
            return null;
        }
    }

    public static void changeLanguage() {
        List<String> choices = new ArrayList<>();
        choices.add("English");
        choices.add("Українська");
        choices.add("Español");
        choices.add("Français");
        choices.add("Deutsch");
        choices.add("Czech");
        choices.add("Polski");
        choices.add("Nederlands");
        choices.add("日本語");
        choices.add("中国人");
        ChoiceDialog<String> dialog = new ChoiceDialog<>("English", choices);
        dialog.setTitle(LanguageManager.getInstance().getStringBinding("LanguageTitle").get());
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(LOGO));
        dialog.setHeaderText(null);
        dialog.setContentText(LanguageManager.getInstance().getStringBinding("ChooseLanguage").get());

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            Locale locale = getLocale(result.get());
            instance.changeLanguage(locale);
        }
    }

    private static Locale getLocale(String chosenLanguage) {
        return switch (chosenLanguage) {
            case "Українська" -> new Locale("uk");
            case "Español" -> new Locale("es");
            case "Français" -> new Locale("fr");
            case "Deutsch" -> new Locale("de");
            case "Czech" -> new Locale("cs");
            case "Polski" -> new Locale("pl");
            case "Nederlands" -> new Locale("nl");
            case "日本語" -> new Locale("ja");
            case "中国人" -> new Locale("zh");
            default -> new Locale("en");
        };
    }


}


