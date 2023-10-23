module com.netrunners.financialcalculator {
    requires javafx.controls;
    requires javafx.fxml;


    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires java.datatransfer;
    requires java.desktop;


    opens com.netrunners.financialcalculator to javafx.fxml;
    opens com.netrunners.financialcalculator.controllers to javafx.fxml;
    exports com.netrunners.financialcalculator;
    exports com.netrunners.financialcalculator.controllers;
}
