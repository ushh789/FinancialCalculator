package com.netrunners.financialcalculator.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Button;

import static com.netrunners.financialcalculator.controllers.closeWindow.closeCurrentWindow;

public class CreditMenuController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MenuItem PaymentOption1;

    @FXML
    private MenuItem PaymentOption2;

    @FXML
    private MenuItem PaymentOption3;

    @FXML
    private MenuItem PaymentOption4;

    @FXML
    private Button creditSaveResult;

    @FXML
    private Button creditViewResult;

    @FXML
    private TextField annualPercentInput;

    @FXML
    private CheckBox checkPaymentHolidays;

    @FXML
    private Button closeWindow;

    @FXML
    private DatePicker contractBeginning;

    @FXML
    private DatePicker contractEnding;

    @FXML
    private DatePicker holidaysBeginning;

    @FXML
    private DatePicker holidaysEnding;

    @FXML
    private TextField loanInput;

    @FXML
    private MenuButton paymentOption;

    @FXML
    void initialize() {
        holidaysBeginning.setVisible(false);
        holidaysBeginning.setDisable(true);
        holidaysEnding.setVisible(false);
        holidaysEnding.setDisable(true);
        checkPaymentHolidays.setOnAction(event ->{
            if(checkPaymentHolidays.isSelected()){
                holidaysBeginning.setVisible(true);
                holidaysEnding.setVisible(true);
                holidaysBeginning.setDisable(false);
                holidaysEnding.setDisable(false);
            }
            else{
                holidaysBeginning.setVisible(false);
                holidaysEnding.setVisible(false);
                holidaysBeginning.setDisable(true);
                holidaysEnding.setDisable(true);
            }
        });
        PaymentOption1.setOnAction(event ->{
            paymentOption.setText(PaymentOption1.getText());
        });
        PaymentOption2.setOnAction(event ->{
            paymentOption.setText(PaymentOption2.getText());
        });
        PaymentOption3.setOnAction(event ->{
            paymentOption.setText(PaymentOption3.getText());
        });
        PaymentOption4.setOnAction(event ->{
            paymentOption.setText(PaymentOption4.getText());
        });

        closeWindow.setOnAction(event -> {
            closeCurrentWindow(closeWindow.getScene());
        });
    }

}
