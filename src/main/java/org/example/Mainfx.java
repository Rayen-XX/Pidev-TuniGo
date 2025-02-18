package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Mainfx extends Application {

    public Mainfx() {
        // Constructeur vide requis par JavaFX
        System.out.println("MainFx instance created");
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Parking.fxml"));
        Parent root = loader.load();
        Scene sc = new Scene(root);
        stage.setScene(sc);
        stage.setTitle("TuniGo");
        stage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }
}
