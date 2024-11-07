module org.example.app {
    requires org.example.core;
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.app to javafx.fxml;
    exports org.example.app;
}