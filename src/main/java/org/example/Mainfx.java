package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Mainfx extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("")); // Nom de la premiere interface
        Parent root = loader.load();
        Scene sc = new Scene(root);
        stage.setScene(sc);
        stage.setTitle("Premiere interface");
        stage.show();
    }
}
