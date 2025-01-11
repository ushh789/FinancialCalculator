package com.netrunners.financialcalculator.logic.time;

import com.netrunners.financialcalculator.StartMenu;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;

import java.time.LocalDate;
import java.util.function.Predicate;

import static com.netrunners.financialcalculator.constants.FileConstants.DARK_THEME;
import static com.netrunners.financialcalculator.constants.StringConstants.DATE_PICKER_RESTRICTION_DARK;
import static com.netrunners.financialcalculator.constants.StringConstants.DATE_PICKER_RESTRICTION_LIGHT;

public class DatePickerRestrictions {

    public static void setDatePickerRestrictions(DatePicker start, DatePicker end, DatePicker... others) {
        setDayCellFactory(start, item -> end.getValue() != null && item.isAfter(end.getValue().minusDays(1)));
        setDayCellFactory(end, item -> start.getValue() != null && item.isBefore(start.getValue().plusDays(1)));

        addValueChangeListener(start, end, LocalDate::isAfter, start);
        addValueChangeListener(end, start, LocalDate::isBefore, end);

        for (DatePicker other : others) {
            setDayCellFactory(other, item ->
                    (start.getValue() != null && item.isBefore(start.getValue())) ||
                            (end.getValue() != null && item.isAfter(end.getValue())));
            addValueChangeListener(other, start, end, (o, s, e) -> o.isBefore(s) || o.isAfter(e), other);
        }
    }

    private static void setDayCellFactory(DatePicker datePicker, Predicate<LocalDate> restriction) {
        Callback<DatePicker, DateCell> dayCellFactory = dp -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (restriction.test(item)) {
                    setDisable(true);
                    setDatePickerRestrictionStyle(this);
                }
            }
        };
        datePicker.setDayCellFactory(dayCellFactory);
        datePicker.valueProperty().addListener((obs, oldVal, newVal) -> datePicker.setDayCellFactory(dayCellFactory));
    }

    private static void addValueChangeListener(DatePicker dp1, DatePicker dp2,
                                               BiPredicate<LocalDate, LocalDate> condition, DatePicker target) {
        dp1.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (dp2.getValue() != null && newVal != null && condition.test(newVal, dp2.getValue())) {
                target.setValue(null);
            }
        });
    }

    private static void addValueChangeListener(DatePicker dp, DatePicker start, DatePicker end,
                                               TriPredicate<LocalDate, LocalDate, LocalDate> condition, DatePicker target) {
        dp.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (start.getValue() != null && end.getValue() != null && newVal != null &&
                    condition.test(newVal, start.getValue(), end.getValue())) {
                target.setValue(null);
            }
        });
    }

    private static void setDatePickerRestrictionStyle(DateCell dateCell) {
        String style = StartMenu.getCurrentTheme().equals(DARK_THEME) ?
                DATE_PICKER_RESTRICTION_DARK : DATE_PICKER_RESTRICTION_LIGHT;
        dateCell.setStyle(style);
    }

    @FunctionalInterface
    private interface BiPredicate<T, U> {
        boolean test(T t, U u);
    }

    @FunctionalInterface
    private interface TriPredicate<T, U, V> {
        boolean test(T t, U u, V v);
    }
}