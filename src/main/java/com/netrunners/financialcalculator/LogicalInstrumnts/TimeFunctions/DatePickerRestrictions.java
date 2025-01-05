package com.netrunners.financialcalculator.LogicalInstrumnts.TimeFunctions;

import com.netrunners.financialcalculator.StartMenu;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;
import java.time.LocalDate;

import static com.netrunners.financialcalculator.VisualInstruments.MenuActions.FileConstants.DARK_THEME;

public class DatePickerRestrictions {

    public static void setDatePickerRestrictionsWithdrawalHolidays(DatePicker start, DatePicker end, DatePicker newDate) {
        final Callback<DatePicker, DateCell> dayCellFactory =
                new Callback<>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);

                                if (end.getValue() != null && item.isAfter(end.getValue().minusDays(1))) {
                                    setDisable(true);
                                    if (StartMenu.getCurrentTheme().equals(DARK_THEME)) {
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
        start.valueProperty().addListener((observable, oldValue, newValue) -> start.setDayCellFactory(dayCellFactory));

        final Callback<DatePicker, DateCell> dayCellFactoryEnd =
                new Callback<>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);

                                if (start.getValue() != null && item.isBefore(start.getValue().plusDays(1))) {
                                    setDisable(true);
                                    if (StartMenu.getCurrentTheme().equals(DARK_THEME)) {
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
        end.valueProperty().addListener((observable, oldValue, newValue) -> end.setDayCellFactory(dayCellFactoryEnd));

        start.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (end.getValue() != null && start.getValue() != null && start.getValue().isAfter(end.getValue())) {
                start.setValue(null);

            }
        });

        end.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (start.getValue() != null && end.getValue() != null && end.getValue().isBefore(start.getValue())) {
                end.setValue(null);
            }

        });
        final Callback<DatePicker, DateCell> dayCellFactoryNew =
                new Callback<>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);

                                if ((start.getValue() != null && item.isBefore(start.getValue())) ||
                                        (end.getValue() != null && item.isAfter(end.getValue()))) {
                                    setDisable(true);
                                    if (StartMenu.getCurrentTheme().equals(DARK_THEME)) {
                                        setStyle("-fx-background-color: gray;");
                                    } else {
                                        setStyle("-fx-background-color: dimgray;-fx-text-fill: white;");
                                    }
                                }
                            }
                        };
                    }
                };
        newDate.setDayCellFactory(dayCellFactoryNew);
        newDate.valueProperty().addListener((observable, oldValue, newValue) -> newDate.setDayCellFactory(dayCellFactoryNew));
        newDate.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (start.getValue() != null && end.getValue() != null && newDate.getValue() != null && (newDate.getValue().isBefore(start.getValue()) || newDate.getValue().isAfter(end.getValue()))) {
                newDate.setValue(null);
            }
        });
    }

    public static void setDatePickerRestrictionsHolidays(DatePicker start, DatePicker end, DatePicker newDate1, DatePicker newDate2) {
        final Callback<DatePicker, DateCell> dayCellFactory =
                new Callback<>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);

                                if (end.getValue() != null && item.isAfter(end.getValue().minusDays(1))) {
                                    setDisable(true);
                                    if (StartMenu.getCurrentTheme().equals(DARK_THEME)) {
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
        start.valueProperty().addListener((observable, oldValue, newValue) -> start.setDayCellFactory(dayCellFactory));

        final Callback<DatePicker, DateCell> dayCellFactoryEnd =
                new Callback<>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);

                                if (start.getValue() != null && item.isBefore(start.getValue().plusDays(1))) {
                                    setDisable(true);
                                    if (StartMenu.getCurrentTheme().equals(DARK_THEME)) {
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
        end.valueProperty().addListener((observable, oldValue, newValue) -> end.setDayCellFactory(dayCellFactoryEnd));

        start.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (end.getValue() != null && start.getValue() != null && start.getValue().isAfter(end.getValue())) {
                start.setValue(null);

            }
            if (start.getValue() != null && end.getValue() != null && start.getValue().isEqual(end.getValue())) {
                start.setValue(null);
            }
        });

        end.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (start.getValue() != null && end.getValue() != null && end.getValue().isBefore(start.getValue())) {
                end.setValue(null);
            }
            if (start.getValue() != null && end.getValue() != null && end.getValue().isEqual(start.getValue())) {
                end.setValue(null);
            }

        });
        final Callback<DatePicker, DateCell> dayCellFactoryNew =
                new Callback<>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);

                                if ((start.getValue() != null && !item.isAfter(start.getValue().minusDays(1))) ||
                                        (end.getValue() != null && !item.isBefore(end.getValue().plusDays(1))) ||
                                        (newDate2.getValue() != null && item.isAfter(newDate2.getValue()))) {
                                    setDisable(true);
                                    if (StartMenu.getCurrentTheme().equals(DARK_THEME)) {
                                        setStyle("-fx-background-color: gray;");
                                    } else {
                                        setStyle("-fx-background-color: dimgray;-fx-text-fill: white;");
                                    }
                                }
                            }
                        };
                    }
                };
        newDate1.setDayCellFactory(dayCellFactoryNew);
        newDate1.valueProperty().addListener((observable, oldValue, newValue) -> newDate1.setDayCellFactory(dayCellFactoryNew));

        final Callback<DatePicker, DateCell> dayCellFactoryNew2 =
                new Callback<>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);

                                if ((start.getValue() != null && item.isBefore(start.getValue().minusDays(1))) ||
                                        (end.getValue() != null && item.isAfter(end.getValue().plusDays(1))) ||
                                        (newDate1.getValue() != null && item.isBefore(newDate1.getValue()))) {
                                    setDisable(true);
                                    if (StartMenu.getCurrentTheme().equals(DARK_THEME)) {
                                        setStyle("-fx-background-color: gray;");
                                    } else {
                                        setStyle("-fx-background-color: dimgray;-fx-text-fill: white;");
                                    }
                                }
                            }
                        };
                    }
                };
        newDate2.setDayCellFactory(dayCellFactoryNew2);
        newDate2.valueProperty().addListener((observable, oldValue, newValue) -> newDate2.setDayCellFactory(dayCellFactoryNew2));

        newDate2.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (start.getValue() != null && end.getValue() != null && newDate2.getValue() != null && (newDate2.getValue().isBefore(start.getValue()) || newDate2.getValue().isAfter(end.getValue()))) {
                newDate2.setValue(null);
            }
            if (newDate1.getValue() != null && newDate2.getValue() != null && newDate2.getValue().isBefore(newDate1.getValue())) {
                newDate2.setValue(null);
            }
            if (newDate1.getValue() != null && newDate2.getValue() != null && newDate2.getValue().isEqual(newDate1.getValue())) {
                newDate2.setValue(null);
            }
        });

        newDate1.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (start.getValue() != null && end.getValue() != null && newDate1.getValue() != null && (newDate1.getValue().isBefore(start.getValue()) || newDate1.getValue().isAfter(end.getValue()))) {
                newDate1.setValue(null);
            }
            if (newDate2.getValue() != null && newDate1.getValue() != null && newDate1.getValue().isAfter(newDate2.getValue())) {
                newDate1.setValue(null);
            }
        });


    }


}
