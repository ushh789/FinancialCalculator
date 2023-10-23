module com.netrunners.financialcalculator {
    requires javafx.controls;
    requires javafx.fxml;


    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;


    opens com.netrunners.financialcalculator to javafx.fxml;
    exports com.netrunners.financialcalculator;
    opens com.netrunners.financialcalculator.controllers;

}
