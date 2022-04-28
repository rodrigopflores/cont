module br.com.jmpaj.cont {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires static lombok;
    requires org.apache.poi.poi;
    requires java.sql;
    requires org.apache.poi.ooxml;
    requires fr.opensagres.poi.xwpf.converter.pdf;
    requires fr.opensagres.poi.xwpf.converter.core;
    requires fr.opensagres.xdocreport.itext.extension;
    requires org.apache.poi.ooxml.schemas;
    requires org.apache.logging.log4j;
    requires slf4j.api;

    opens br.com.jmpaj.cont to javafx.fxml;
    exports br.com.jmpaj.cont;
}