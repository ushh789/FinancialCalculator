package com.netrunners.financialcalculator.ErrorHandling;

import com.netrunners.financialcalculator.StartMenu;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;


public class ErrorChecker {
    public static void updateDatePickerStyle(DatePicker datePicker){
        if(StartMenu.currentTheme.equals("file:src/main/resources/com/netrunners/financialcalculator/assets/darkTheme.css")){
            datePicker.setStyle("-fx-focus-color: red");
            datePicker.setStyle("-fx-faint-focus-color: red");
        }else {
            datePicker.setStyle("-fx-focus-color: red");
        }
    }
    public static void WrongDateListener(DatePicker datePicker){
        datePicker.getEditor().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.isEmpty() && datePicker.isFocused()) {
                    updateDatePickerStyle(datePicker);
                } else {
                    datePicker.setStyle(null);
                }
            }
        });

        datePicker.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue && datePicker.getEditor().getText().isEmpty()) { // Коли DatePicker отримує фокус
                    updateDatePickerStyle(datePicker);
                } else {
                    datePicker.setStyle(null);
                }
            }
        });
    }


    public static boolean areFieldsValid(TextField investInput, TextField depositAnnualPercentInput, MenuButton depositWithdrawalOption) {
        boolean investValid = InputFieldErrors.checkIfCorrectNumberGiven(investInput);
        boolean annualPercentValid = InputFieldErrors.checkIfCorrectNumberGiven(depositAnnualPercentInput);
        boolean withdrawalOptionValid = InputFieldErrors.withdrawalOptionIsSelected(depositWithdrawalOption);

        if (!investValid) {
            highlightError(investInput);
            investInput.setText("");
        } else {
            removeHighlight(investInput);
        }

        if (!annualPercentValid) {
            highlightError(depositAnnualPercentInput);
            depositAnnualPercentInput.setText("");
        } else {
            removeHighlight(depositAnnualPercentInput);
        }

        if (!withdrawalOptionValid) {
            highlightError(depositWithdrawalOption);
        } else {
            removeHighlight(depositWithdrawalOption);
        }

        return investValid && annualPercentValid && withdrawalOptionValid;
    }

    public static void highlightError(Control field) {
        field.setStyle("-fx-border-color:red;-fx-border-radius:3px");
        field.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    removeHighlight(field);
                }
            }
        });
    }

    public static void removeHighlight(Control field) {
        field.setStyle(null);
    }


}
