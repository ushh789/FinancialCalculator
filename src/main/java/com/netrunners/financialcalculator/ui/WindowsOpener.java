package com.netrunners.financialcalculator.ui;

import com.netrunners.financialcalculator.errorhandling.exceptions.OpenWindowException;
import com.netrunners.financialcalculator.logic.entity.ResultTableSender;
import com.netrunners.financialcalculator.controller.ResultTableController;
import com.netrunners.financialcalculator.StartMenu;
import com.netrunners.financialcalculator.constants.FileConstants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.netrunners.financialcalculator.constants.FileConstants.RESULT_TABLE;
import static com.netrunners.financialcalculator.constants.StringConstants.ERROR_OPENING_FXML;
import static com.netrunners.financialcalculator.constants.StringConstants.ERROR_OPENING_RESULT;

public class WindowsOpener{
    private static final Logger logger = LoggerFactory.getLogger(WindowsOpener.class);
    private final static int WINDOW_HEIGHT = 820;
    private final static int WINDOW_WIDTH = 620;
    public static void depositOpener(){
        try {
            openWindow("DepositMenu.fxml", "DepositButton");
        } catch (OpenWindowException e) {
            logger.error(ERROR_OPENING_FXML,e.getMessage(), e);
        }
    }
    public static void creditOpener(){
        try {
            openWindow("CreditMenu.fxml", "CreditButton");
        } catch (OpenWindowException e) {
            logger.error(ERROR_OPENING_FXML,e.getMessage(), e);
        }
    }
    public static void openResultTable(ResultTableSender financialOperation){
        try {
            sendToResultTable(financialOperation);
        } catch (OpenWindowException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private static void openWindow(String resource, String languageKey) throws OpenWindowException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(StartMenu.class.getResource(resource));
            Stage stage = new Stage();
            setStageParams(stage, languageKey);
            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(StartMenu.getCurrentTheme());
            stage.setScene(scene);
            StartMenu.openScenes.add(scene);
            stage.show();
        } catch (Exception e){
            throw new OpenWindowException(resource);
        }
    }

    private static void sendToResultTable(ResultTableSender financialOperation) throws OpenWindowException {
        try {
        FXMLLoader fxmlLoader = new FXMLLoader(StartMenu.class.getResource(RESULT_TABLE));
        Parent root = fxmlLoader.load();
        ResultTableController resultTableController = fxmlLoader.getController();
        resultTableController.updateTable(financialOperation);
        Stage stage = new Stage();
        setStageParams(stage, "ResultTableLabel");
        Scene scene = new Scene(root);
        scene.getStylesheets().add(StartMenu.getCurrentTheme());
        stage.setScene(scene);
        StartMenu.openScenes.add(scene);
        stage.show();
        } catch (Exception e){
            throw new OpenWindowException(ERROR_OPENING_RESULT);
        }
    }

    private static void setStageParams(Stage stage, String languageKey) {
        stage.titleProperty().bind(LanguageManager.getInstance().getStringBinding(languageKey));
        stage.getIcons().add(new Image(FileConstants.LOGO));
        stage.setMaxHeight(WINDOW_HEIGHT);
        stage.setMinHeight(WINDOW_HEIGHT);
        stage.setMaxWidth(WINDOW_WIDTH);
        stage.setMinWidth(WINDOW_WIDTH);
    }
}
