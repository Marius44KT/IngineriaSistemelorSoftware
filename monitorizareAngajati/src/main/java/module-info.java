module com.example.monitorizareangajati {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.monitorizareangajati to javafx.fxml;
    opens com.example.monitorizareangajati.Controllers to javafx.fxml;


    exports com.example.monitorizareangajati;
    exports com.example.monitorizareangajati.Model;
    exports com.example.monitorizareangajati.Controllers;
}