package com.netrunners.financialcalculator.logic.files;

import com.google.gson.JsonObject;

public interface Savable {
    void save();
    JsonObject getJsonObject();
}
