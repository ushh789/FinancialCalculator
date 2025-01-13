package com.netrunners.financialcalculator.logic.time;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDate;

public class LocalDateAdapter extends TypeAdapter<LocalDate> {
    private static final Logger logger = LoggerFactory.getLogger(LocalDateAdapter.class);
    @Override
    public void write(final JsonWriter jsonWriter, final LocalDate localDate) {
        try {
            if (localDate == null) {
                jsonWriter.nullValue();
            } else {
                jsonWriter.value(localDate.toString());
            }
        } catch (IOException e) {
            logger.error("Error while writing LocalDate in LocalDateAdapter", e);
        }
    }

    @Override
    public LocalDate read(final JsonReader jsonReader) {
        try {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            } else {
                return LocalDate.parse(jsonReader.nextString());
            }
        } catch (IOException e) {
            logger.error("Error while reading LocalDate in LocalDateAdapter", e);
            return null;
        }
    }
}
