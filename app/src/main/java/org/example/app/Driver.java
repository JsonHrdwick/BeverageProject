package org.example.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Driver extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Driver.class.getResource("menuNew.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Controller controller = fxmlLoader.getController();
        stage.setTitle("Vending");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(event -> {
            try {
                controller.serializeBeverages();
                stage.close();
            } catch (IOException e) {
                stage.close();
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }
}

