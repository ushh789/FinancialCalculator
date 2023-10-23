package com.netrunners.financialcalculator.controllers;

import static com.netrunners.financialcalculator.controllers.closeWindow.closeCurrentWindow;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class DepositMenuController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button closeWindow;

    @FXML
    private MenuItem WithdrawalOption1;

    @FXML
    private MenuItem WithdrawalOption2;

    @FXML
    private MenuItem WithdrawalOption3;

    @FXML
    private MenuItem WithdrawalOption4;

    @FXML
    private TextField depositAnnualPercentInput;

    @FXML
    private CheckBox depositCapitalizationCheck;

    @FXML
    private DatePicker depositConctractBeginning;

    @FXML
    private DatePicker depositConctractEnding;

    @FXML
    private CheckBox depositEarlyWithdrawalCheck;

    @FXML
    private Button depositSaveResult;

    @FXML
    private Button depositViewResult;

    @FXML
    private DatePicker depositWithdrawalDate;

    @FXML
    private MenuButton depositWithdrawalOption;

    @FXML
    private TextField investInput;

    @FXML
    void initialize(){
        depositWithdrawalDate.setVisible(false);
        depositWithdrawalDate.setDisable(true);
        closeWindow.setOnAction(event -> {
            closeCurrentWindow(closeWindow.getScene());
        });
        depositEarlyWithdrawalCheck.setOnAction(event -> {
            if(depositEarlyWithdrawalCheck.isSelected()){
                depositWithdrawalDate.setVisible(true);
                depositWithdrawalDate.setDisable(false);
            }else{
                depositWithdrawalDate.setVisible(false);
                depositWithdrawalDate.setDisable(true);
            }
        });
        WithdrawalOption1.setOnAction(event -> {
            depositWithdrawalOption.setText(WithdrawalOption1.getText());
        });
        WithdrawalOption2.setOnAction(event -> {
            depositWithdrawalOption.setText(WithdrawalOption2.getText());
        });
        WithdrawalOption3.setOnAction(event -> {
            depositWithdrawalOption.setText(WithdrawalOption3.getText());
        });
        WithdrawalOption4.setOnAction(event -> {
            depositWithdrawalOption.setText(WithdrawalOption4.getText());
        });
    }

}
