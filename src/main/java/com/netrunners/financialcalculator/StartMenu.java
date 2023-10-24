package com.netrunners.financialcalculator;

import com.google.gson.Gson;
import com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation.Credit.Credit;
import com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation.Credit.CreditWithHolidays;
import com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation.Deposit.CapitalisedDeposit;
import com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation.Deposit.Deposit;
import javafx.application.Application;

import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StartMenu extends Application {
    public static Scene startMenuScene;
    public static List<Scene> openScenes = new ArrayList<>();
    public static String currentTheme = "file:src/main/resources/com/netrunners/financialcalculator/assets/lightTheme.css";
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader =new FXMLLoader(StartMenu.class.getResource("StartMenu.fxml"));
        primaryStage.setTitle("Financial Calculator by netrunners");
        primaryStage.getIcons().add(new Image("file:src/main/resources/com/netrunners/financialcalculator/assets/Logo.png"));
        if (Taskbar.isTaskbarSupported()) {
            var taskbar = Taskbar.getTaskbar();
            if (taskbar.isSupported(Taskbar.Feature.ICON_IMAGE)) {
                final Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
                var dockIcon = defaultToolkit.getImage("src/main/resources/com/netrunners/financialcalculator/assets/Logo.png");
                taskbar.setIconImage(dockIcon);
            }

        }
        primaryStage.setScene(new Scene(fxmlLoader.load(), 600, 600));
        primaryStage.maxHeightProperty().setValue(600);
        primaryStage.maxWidthProperty().setValue(600);
        primaryStage.minHeightProperty().setValue(620);
        primaryStage.minWidthProperty().setValue(620);
        startMenuScene = primaryStage.getScene();
        StartMenu.openScenes.add(startMenuScene);
        primaryStage.show();

        LocalDate l1 = LocalDate.now();
        LocalDate l2 = LocalDate.now();
        Deposit d = new CapitalisedDeposit(1200,"UAH", 5.0f, l1,l2, 2);
        d.save();

        Credit c = new CreditWithHolidays(1200, "UAH", 5.0f, l1,l2, 2, l1,l2);
        c.save();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
