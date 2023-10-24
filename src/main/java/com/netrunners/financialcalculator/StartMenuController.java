package com.netrunners.financialcalculator;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.List;

import static com.netrunners.financialcalculator.StartMenu.startMenuScene;
import static com.netrunners.financialcalculator.controllers.closeWindow.closeCurrentWindow;

public class StartMenuController {

    @FXML
    private Button CreditButton;

    @FXML
    private Button DepositButton;

    @FXML
    private MenuItem depositButtonMenu;

    @FXML
    private MenuItem creditButtonMenu;

    @FXML
    private MenuItem languageButton;

    @FXML
    private MenuItem darkTheme;

    @FXML
    private MenuItem lightTheme;

    @FXML
    private MenuItem aboutUs;

    @FXML
    private MenuItem exitApp;

    @FXML
    private MenuItem currency;

    @FXML
    private MenuItem openFileButton;

    @FXML
    private MenuItem saveAsButton;

    @FXML
    private MenuItem saveButton;

    @FXML
    private Menu themeButton;

    @FXML
    private Menu viewButton;

    @FXML
    private Menu newButton;

    @FXML
    private Menu fileButton;

    @FXML
    private Menu settingsButton;

    @FXML
    private Menu aboutButton;

    @FXML
    private Label financialCalculatorLabel;

    @FXML
    void initialize() {

        DepositButton.setOnAction(event -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(StartMenu.class.getResource("DepositMenu.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Deposit Menu");
                Scene scene = new Scene(fxmlLoader.load());
                scene.getStylesheets().add(StartMenu.currentTheme);
                stage.setScene(scene);
                StartMenu.openScenes.add(scene);
                stage.getIcons().add(new Image("file:src/main/resources/com/netrunners/financialcalculator/assets/Logo.png"));
                stage.setMaxHeight(820);
                stage.setMaxWidth(620);
                stage.setMinHeight(820);
                stage.setMinWidth(620);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        CreditButton.setOnAction(event -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(StartMenu.class.getResource("CreditMenu.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Credit Menu");
                Scene scene = new Scene(fxmlLoader.load());
                scene.getStylesheets().add(StartMenu.currentTheme);
                stage.setScene(scene);
                StartMenu.openScenes.add(scene);
                stage.getIcons().add(new Image("file:src/main/resources/com/netrunners/financialcalculator/assets/Logo.png"));
                stage.setMaxHeight(820);
                stage.setMaxWidth(620);
                stage.setMinHeight(820);
                stage.setMinWidth(620);
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        depositButtonMenu.setOnAction(event -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(StartMenu.class.getResource("DepositMenu.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Deposit Menu");
                Scene scene = new Scene(fxmlLoader.load());
                scene.getStylesheets().add(StartMenu.currentTheme);
                stage.setScene(scene);
                StartMenu.openScenes.add(scene);
                stage.getIcons().add(new Image("file:src/main/resources/com/netrunners/financialcalculator/assets/Logo.png"));
                stage.setMaxHeight(820);
                stage.setMaxWidth(620);
                stage.setMinHeight(820);
                stage.setMinWidth(620);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        creditButtonMenu.setOnAction(event -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(StartMenu.class.getResource("CreditMenu.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Credit Menu");
                Scene scene = new Scene(fxmlLoader.load());
                scene.getStylesheets().add(StartMenu.currentTheme);
                stage.setScene(scene);
                StartMenu.openScenes.add(scene);
                stage.getIcons().add(new Image("file:src/main/resources/com/netrunners/financialcalculator/assets/Logo.png"));
                stage.setMaxHeight(820);
                stage.setMaxWidth(620);
                stage.setMinHeight(820);
                stage.setMinWidth(620);
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        darkTheme.setOnAction(event -> {
            StartMenu.currentTheme = "file:src/main/resources/com/netrunners/financialcalculator/assets/darkTheme.css";
            for (Scene scene : StartMenu.openScenes) {
                scene.getStylesheets().clear();
                scene.getStylesheets().add(StartMenu.currentTheme);
            }
        });

        lightTheme.setOnAction(event -> {
            StartMenu.currentTheme = "file:src/main/resources/com/netrunners/financialcalculator/assets/lightTheme.css";
            for (Scene scene : StartMenu.openScenes) {
                scene.getStylesheets().clear();
                scene.getStylesheets().add(StartMenu.currentTheme);
            }
        });
        aboutUs.setOnAction(event -> {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("About our application");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("file:src/main/resources/com/netrunners/financialcalculator/assets/Logo.png"));
            alert.setHeaderText(null);
            alert.setContentText("""
                    Title: Financial Calculator by Netrunners

                    Description:
                    The "Financial Calculator by Netrunners" is a versatile and user-friendly software application designed to help individuals and businesses make informed financial decisions by calculating both deposit and credit-related parameters. With a range of powerful features, this tool simplifies financial planning and provides users with valuable insights into their financial investments and liabilities.

                    Key Features:

                    Deposit Calculator:

                    Calculate Compound Interest: Determine the future value of your savings or investments with compound interest calculations.
                    Flexible Periods: Specify the deposit term in months or years to align with your financial goals.
                    Customizable Contributions: Account for regular deposits or contributions to your savings.
                    Interest Rate Options: Calculate interest with both fixed and variable interest rates.

                    Credit Calculator:

                    Loan Repayment Estimation: Calculate the monthly or periodic payments required to pay off a loan or credit.
                    Amortization Schedules: View detailed amortization schedules to track the progress of your loan repayment.
                    Interest Considerations: Analyze how interest rates impact the total cost of a loan.
                    Interactive Graphs: Visualize loan repayment and interest payments through user-friendly graphs.

                    Dark and Light Themes:

                    Choose between dark and light themes to customize the application's appearance and enhance user experience.

                    "Financial Calculator by Netrunners" empowers individuals, students, professionals, and business owners to make informed decisions about their financial affairs. Whether you're planning to save money or seeking insights into loan repayments, this versatile tool offers a comprehensive set of features to help you on your financial journey. Start making smarter financial decisions today with this powerful yet user-friendly financial calculator.""");
            alert.showAndWait();
        });
        exitApp.setOnAction(event -> {
            for (Scene scene : StartMenu.openScenes) {
                closeCurrentWindow(scene);
            }
            System.exit(0);
        });
        currency.setOnAction(event -> {
            List<String> choices = new ArrayList<>();
            choices.add("UAH");
            choices.add("USD");
            choices.add("EUR");

            ChoiceDialog<String> dialog = new ChoiceDialog<>("UAH", choices);
            dialog.setTitle("Choose Currency");
            Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("file:src/main/resources/com/netrunners/financialcalculator/assets/Logo.png"));
            dialog.setHeaderText(null);
            dialog.setContentText("Choose your currency:");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                switch (result.get()) {
                    case "UAH":
                        break;
                    case "USD":
                        break;
                    case "EUR":
                        break;

                }
            }
        });
        openFileButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json"));
            File file = fileChooser.showOpenDialog(startMenuScene.getWindow());
            if (file != null) {
                System.out.println("File opened: " + file.getName());
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        saveAsButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save As");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                    new FileChooser.ExtensionFilter("JSON Files", "*.json"),
                    new FileChooser.ExtensionFilter("All Files", "*.*")
            );
            File file = fileChooser.showSaveDialog(null);

            if (file != null) {
                try {
                    FileWriter fileWriter = new FileWriter(file);
                    fileWriter.write("Your content here");
                    fileWriter.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        languageButton.setOnAction(event -> {
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
            dialog.setTitle("Choose Language");
            Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("file:src/main/resources/com/netrunners/financialcalculator/assets/Logo.png"));
            dialog.setHeaderText(null);
            dialog.setContentText("Choose your language:");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                switch (result.get()) {
                    case "English" -> setLanguage("en");
                    case "Українська" -> setLanguage("uk");
                    case "Español" -> setLanguage("es");
                    case "Français" -> setLanguage("fr");
                    case "Deutsch" -> setLanguage("de");
                    case "Czech" -> setLanguage("cs");
                    case "Polski" -> setLanguage("pl");
                    case "Nederlands" -> setLanguage("nl");
                    case "日本語" -> setLanguage("ja");
                    case "中国人" -> setLanguage("zh");
                }
            }
        });


    }

    public void setLanguage(String language) {
        try {
            // Load the JSON file
            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, List<String>>>() {
            }.getType();

            // Use InputStreamReader with UTF-8 encoding
            try (InputStreamReader reader = new InputStreamReader(new FileInputStream("languages.json"), StandardCharsets.UTF_8)) {
                Map<String, List<String>> json = gson.fromJson(reader, type);

                // Define language index based on language code
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
                int index = languageIndexMap.getOrDefault(language, -1);

                // Update the text of all controls using the JSON file
                CreditButton.setText(json.get("CreditButton").get(index));
                DepositButton.setText(json.get("DepositButton").get(index));
                depositButtonMenu.setText(json.get("depositButtonMenu").get(index));
                creditButtonMenu.setText(json.get("creditButtonMenu").get(index));
                languageButton.setText(json.get("languageButton").get(index));
                saveAsButton.setText(json.get("saveAsButton").get(index));
                saveButton.setText(json.get("saveButton").get(index));
                openFileButton.setText(json.get("openFileButton").get(index));
                fileButton.setText(json.get("fileButton").get(index));
                darkTheme.setText(json.get("darkTheme").get(index));
                lightTheme.setText(json.get("lightTheme").get(index));
                themeButton.setText(json.get("themeButton").get(index));
                viewButton.setText(json.get("viewButton").get(index));
                newButton.setText(json.get("newButton").get(index));
                aboutUs.setText(json.get("aboutUs").get(index));
                exitApp.setText(json.get("exitApp").get(index));
                currency.setText(json.get("currency").get(index));
                financialCalculatorLabel.setText(json.get("financialCalculatorLabel").get(index));
                settingsButton.setText(json.get("settingsButton").get(index));
                aboutButton.setText(json.get("aboutButton").get(index));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}

