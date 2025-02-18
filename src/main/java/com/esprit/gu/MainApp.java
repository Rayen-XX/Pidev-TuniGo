package com.esprit.gu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/views/register.fxml"));
            Parent root = loader.load();

            // Set up the scene
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("TuniGo");
            primaryStage.setMinWidth(800);
            primaryStage.setMinHeight(600);
            // Show the application window
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading FXML file. Make sure the file path is correct.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
