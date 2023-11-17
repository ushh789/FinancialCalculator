module com.netrunners.financialcalculator {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires java.datatransfer;
    requires java.desktop;
    requires java.base;
    requires com.google.gson;
    requires com.opencsv;
    requires java.logging;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;

    opens com.netrunners.financialcalculator to javafx.fxml, com.google.gson;
    opens com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation.Deposit to com.google.gson;
    opens com.netrunners.financialcalculator.LogicalInstrumnts.TimeFunctions to com.google.gson;



    exports com.netrunners.financialcalculator;
    exports com.netrunners.financialcalculator.MenuControllers;
    opens com.netrunners.financialcalculator.MenuControllers to com.google.gson, javafx.fxml;
    exports com.netrunners.financialcalculator.VisualInstruments.MenuActions;
    opens com.netrunners.financialcalculator.VisualInstruments.MenuActions to com.google.gson, javafx.fxml;
    exports com.netrunners.financialcalculator.LogicalInstrumnts.FileInstruments;
    opens com.netrunners.financialcalculator.LogicalInstrumnts.FileInstruments to com.google.gson, javafx.fxml;

}
