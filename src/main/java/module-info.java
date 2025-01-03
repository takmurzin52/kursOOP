module com.example.kurs {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens com.example.kurs to javafx.fxml;
    exports com.example.kurs;
    exports com.example.kurs.Sensors;
    opens com.example.kurs.Sensors to javafx.fxml;
}