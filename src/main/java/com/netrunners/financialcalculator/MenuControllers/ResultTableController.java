package com.netrunners.financialcalculator.MenuControllers;

import com.netrunners.financialcalculator.StartMenu;
import com.netrunners.financialcalculator.VisualInstruments.MenuActions.AboutUsAlert;
import com.netrunners.financialcalculator.VisualInstruments.MenuActions.ExitApp;
import com.netrunners.financialcalculator.VisualInstruments.MenuActions.ThemeSelector;
import com.netrunners.financialcalculator.VisualInstruments.WindowsOpener;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;

import static com.netrunners.financialcalculator.MenuControllers.closeWindow.closeCurrentWindow;

public class ResultTableController {

    @FXML
    private Menu aboutButton;

    @FXML
    private MenuItem aboutUs;

    @FXML
    private MenuItem creditButtonMenu;

    @FXML
    private MenuItem currency;

    @FXML
    private MenuItem darkTheme;

    @FXML
    private MenuItem depositButtonMenu;

    @FXML
    private MenuItem exitApp;

    @FXML
    private Menu fileButton;

    @FXML
    private Label financialCalculatorLabel;

    @FXML
    private TableColumn<?, ?> investmentloanColumn;

    @FXML
    private MenuItem languageButton;

    @FXML
    private MenuItem lightTheme;

    @FXML
    private Menu newButton;

    @FXML
    private TableColumn<Integer, Integer> numbersColumn;

    @FXML
    private MenuItem openFileButton;

    @FXML
    private TableColumn<?, ?> periodProfitLoan;

    @FXML
    private TableView<Integer> resultTable;

    @FXML
    private MenuItem saveAsButton;

    @FXML
    private MenuItem saveButton;

    @FXML
    private Menu settingsButton;

    @FXML
    private Menu themeButton;

    @FXML
    private TableColumn<?, ?> total;

    @FXML
    private Menu viewButton;

    @FXML
    void initialize() {
        ObservableList<Integer> data = FXCollections.observableArrayList();
        for (int i = 1; i <= 10; i++) {
            data.add(i);
        }
        numbersColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue()));
        resultTable.setItems(data);

        darkTheme.setOnAction(event -> ThemeSelector.setDarkTheme());
        lightTheme.setOnAction(event -> ThemeSelector.setLightTheme());
        aboutUs.setOnAction(event -> AboutUsAlert.showAboutUs());
        exitApp.setOnAction(event -> ExitApp.exitApp());
        depositButtonMenu.setOnAction(event -> WindowsOpener.depositOpener());
        creditButtonMenu.setOnAction(event -> WindowsOpener.creditOpener());

    }
}
