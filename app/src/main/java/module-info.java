module org.example.app {
    requires org.example.core;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens org.example.app to javafx.fxml;
    exports org.example.app;
}