package com.netrunners.financialcalculator;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class StartMenuController {

    @FXML
    private Button CreditButton;

    @FXML
    private Button DepositButton;

    @FXML
    void initialize() {
        DepositButton.setOnAction(event -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(StartMenu.class.getResource("DepositMenu.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Deposit Menu");
                stage.setScene(new Scene(fxmlLoader.load()));
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

        CreditButton.setOnAction(event ->{
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(StartMenu.class.getResource("CreditMenu.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Credit Menu");
                stage.setScene(new Scene(fxmlLoader.load()));
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
    }

}
