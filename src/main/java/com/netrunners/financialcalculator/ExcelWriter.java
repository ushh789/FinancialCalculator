package com.netrunners.financialcalculator;

import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.Desktop;
import java.io.File;

public class ExcelWriter {
    public static void main(String[] args) {
        String csv = "data.csv";
        try (CSVWriter writer = new CSVWriter(new FileWriter(csv), ';', CSVWriter.DEFAULT_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END)) {
            String[] dataA = {"A1", "A2", "A3"};
            writer.writeNext(dataA);
            String[] dataB = {"B1", "B2", "B3"};
            writer.writeNext(dataB);
        } catch (IOException e) {
            e.printStackTrace();
        }





        if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File("data.csv");
                Desktop.getDesktop().open(myFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
