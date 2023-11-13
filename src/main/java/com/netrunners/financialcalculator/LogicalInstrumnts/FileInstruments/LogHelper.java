package com.netrunners.financialcalculator.LogicalInstrumnts.FileInstruments;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.*;

public class LogHelper {

    // Додаємо об'єкт логування
    private static final Logger logger = Logger.getLogger(LogHelper.class.getName());

    static {
        try {
            // Налаштовуємо об'єкт логування для запису в файл "log.txt"
            FileHandler fileHandler = new FileHandler("logs/log"+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss"))+".log");
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод для логування повідомлень
    public static void log(Level level, String message, Throwable throwable) {
        logger.log(level, message, throwable);
    }
}

