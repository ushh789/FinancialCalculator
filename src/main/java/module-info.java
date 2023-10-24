module com.netrunners.financialcalculator {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires java.datatransfer;
    requires java.desktop;
    requires java.base;
    requires com.google.gson;

    opens com.netrunners.financialcalculator to javafx.fxml, com.google.gson;
    opens com.netrunners.financialcalculator.controllers to javafx.fxml;
    opens com.netrunners.financialcalculator.LogicalInstrumnts.TypesOfFinancialOpearation.Deposit to com.google.gson;
    opens com.netrunners.financialcalculator.LogicalInstrumnts.TimeFunctions to com.google.gson;



    exports com.netrunners.financialcalculator;
    exports com.netrunners.financialcalculator.controllers;
}
