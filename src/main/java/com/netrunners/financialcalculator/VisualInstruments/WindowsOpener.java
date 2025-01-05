package com.netrunners.financialcalculator.VisualInstruments;

import com.netrunners.financialcalculator.LogicalInstrumnts.FileInstruments.LogHelper;
import com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation.ResultTableSender;
import com.netrunners.financialcalculator.MenuControllers.ResultTableController;
import com.netrunners.financialcalculator.StartMenu;
import com.netrunners.financialcalculator.VisualInstruments.MenuActions.FileConstants;
import com.netrunners.financialcalculator.VisualInstruments.MenuActions.LanguageManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;

import static com.netrunners.financialcalculator.VisualInstruments.MenuActions.FileConstants.RESULT_TABLE;

public class WindowsOpener{
    private final static int WINDOW_HEIGHT = 820;
    private final static int WINDOW_WIDTH = 620;
    public static void depositOpener(){
        try {
            openWindow("DepositMenu.fxml", "DepositButton");
        } catch (Exception e) {
            LogHelper.log(Level.SEVERE, "Error opening DepositMenu.fxml: ", e);
        }
    }
    public static void creditOpener(){
        try {
            openWindow("CreditMenu.fxml", "CreditButton");
        } catch (Exception e) {
            LogHelper.log(Level.SEVERE, "Error opening CreditMenu.fxml: ", e);
        }
    }
    public static void openResultTable(ResultTableSender financialOperation){
        try {
            sendToResultTable(financialOperation);
        } catch (Exception e) {
            LogHelper.log(Level.SEVERE, "Error opening ResultTable.fxml: ", e);
        }
    }

    private static void openWindow(String resource, String languageKey) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartMenu.class.getResource(resource));
        Stage stage = new Stage();
        setStageParams(stage, languageKey);
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(StartMenu.getCurrentTheme());
        stage.setScene(scene);
        StartMenu.openScenes.add(scene);
        stage.show();
    }

    private static void sendToResultTable(ResultTableSender financialOperation) throws IOException {
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
