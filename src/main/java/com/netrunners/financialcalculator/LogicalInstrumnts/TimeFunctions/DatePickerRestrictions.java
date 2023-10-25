package com.netrunners.financialcalculator.LogicalInstrumnts.TimeFunctions;

import com.netrunners.financialcalculator.StartMenu;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;

import java.time.LocalDate;

public class DatePickerRestrictions {
    public static void setDatePickerRestrictions(DatePicker start, DatePicker end) {
        final Callback<DatePicker, DateCell> dayCellFactory =
                new Callback<DatePicker, DateCell>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);

                                if (end.getValue() != null && item.isAfter(end.getValue().minusDays(1))) {
                                    setDisable(true);
                                    if(StartMenu.currentTheme.equals("file:src/main/resources/com/netrunners/financialcalculator/assets/darkTheme.css")) {
                                        setStyle("-fx-background-color: gray;");
                                    } else {
                                        setStyle("-fx-background-color: dimgray;-fx-text-fill: white;");
                                    }
                                }
                            }
                        };
                    }
                };
        start.setDayCellFactory(dayCellFactory);
        start.valueProperty().addListener((observable, oldValue, newValue) -> {
            start.setDayCellFactory(dayCellFactory);
        });

        final Callback<DatePicker, DateCell> dayCellFactoryEnd =
                new Callback<DatePicker, DateCell>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);

                                if (start.getValue() != null && item.isBefore(start.getValue().plusDays(1))) {
                                    setDisable(true);
                                    if(StartMenu.currentTheme.equals("file:src/main/resources/com/netrunners/financialcalculator/assets/darkTheme.css")) {
                                        setStyle("-fx-background-color: gray;");
                                    } else {
                                        setStyle("-fx-background-color: dimgray;-fx-text-fill: white;");
                                    }
                                }
                            }
                        };
                    }
                };
        end.setDayCellFactory(dayCellFactoryEnd);
        end.valueProperty().addListener((observable, oldValue, newValue) -> {
            end.setDayCellFactory(dayCellFactoryEnd);
        });
    }
}
