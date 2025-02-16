package test;
/*package test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load FXML file and store it in a Parent node
            Parent root = FXMLLoader.load(getClass().getResource("/test/.fxml"));

            // Create a scene using the root node
            Scene scene = new Scene(root);

            // Set up the primary stage
            primaryStage.setTitle("Add Personne");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            System.out.println("Error loading FXML file. Make sure  is in /test/.");
            e.printStackTrace();
        }
    }
}
*/


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainFX extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Charger le fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/test/GestionBus.fxml"));
            //FXMLLoader loader = new FXMLLoader(getClass().getResource("/test/GestionTrain.fxml"));
            //FXMLLoader loader = new FXMLLoader(getClass().getResource("/test/AjouterReclamation.fxml"));
            Parent root = loader.load();

            // Créer la scène et l'ajouter au stage
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);

            // Définir le titre de la fenêtre
            primaryStage.setTitle("Gestion des Réclamations");

            // Afficher la fenêtre
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

