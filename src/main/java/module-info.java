module com.example.weatherapp {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires org.json;

    opens com.example.weatherapp to javafx.fxml;
    exports com.example.weatherapp;
}