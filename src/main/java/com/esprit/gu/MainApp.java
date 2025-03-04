/*package com.esprit.gu;

import com.esprit.gu.firebaseconfig.FCMService;
import com.esprit.gu.firebaseconfig.FirebaseConfig;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        FirebaseConfig.initialize();
        System.out.println("Firebase is working correctly!");

        String deviceToken = "TON_TOKEN_ICI";

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
*/

package com.esprit.gu;

import com.esprit.gu.entity.Utilisateur;
import com.esprit.gu.firebaseconfig.FirebaseConfig;
import com.esprit.gu.util.Session;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws ExecutionException, InterruptedException {
        // Initialiser Firebase

        FirebaseConfig.initFirebase();
        System.out.println("Firebase est bien configuré !");

        // Récupérer l'utilisateur connecté depuis la session
        Utilisateur currentUser = Session.getCurrentUser();

        if (currentUser != null) {
            String userId = String.valueOf(currentUser.getIdUtilisateur()); // Supposons que getId() renvoie l'ID de l'utilisateur
           // String deviceToken = FirebaseTokenRetriever.getUserFcmToken(userId);


        } else {
            System.out.println("⚠️ Aucun utilisateur connecté !");
        }

        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/views/register.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("TuniGo");
            primaryStage.setMinWidth(800);
            primaryStage.setMinHeight(600);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("❌ Erreur lors du chargement du fichier FXML !");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
