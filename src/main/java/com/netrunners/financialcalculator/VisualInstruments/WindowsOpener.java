package com.netrunners.financialcalculator.VisualInstruments;

import com.netrunners.financialcalculator.StartMenu;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class WindowsOpener{
    public static void depositOpener(){
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
    }
    public static void creditOpener(){
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
    }
}
