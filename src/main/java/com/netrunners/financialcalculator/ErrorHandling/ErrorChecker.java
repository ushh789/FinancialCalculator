package com.netrunners.financialcalculator.ErrorHandling;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.DatePicker;

public class ErrorChecker {
    public static void ErrorListener(DatePicker datePicker){
        datePicker.getEditor().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.isEmpty()) {
                    datePicker.setStyle("-fx-border-color: red; -fx-border-width: 1px;-fx-focus-color:transparent;-fx-border-radius: 2px");
                } else {
                    datePicker.setStyle(null);
                }
            }
        });
    }
}
