package com.esprit.gu.controller;

import com.esprit.gu.entity.Utilisateur;
import com.esprit.gu.util.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminDashboardAllController implements Initializable {


    @FXML
    private Label welcomeLabel;

    @FXML
    private Hyperlink logoutLink;

    @FXML
    private Button gestionUtilisateurButton;



    public void initialize(URL location, ResourceBundle resources) {
        // Get the current user from session and display his/her name.
        Utilisateur currentUser = Session.getCurrentUser();
        if (currentUser != null) {
            welcomeLabel.setText("Bonjour, " + currentUser.getNomUtilisateur() + " "
                    + currentUser.getPrenomUtilisateur());
        } else {
            welcomeLabel.setText("Utilisateur non connecté");
        }

        // Set up logout action.
        logoutLink.setOnAction(e -> {
            Session.clear();  // Clear the current session
            try {
                // Load the login view and set it as the current scene.
                Stage stage = (Stage) logoutLink.getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
                stage.setScene(new Scene(root));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
    @FXML
    private void handleGestionUtilisateurButtonClick() {
        try {
            // Load the admin_dashboard_gu.fxml file
            Parent root = FXMLLoader.load(getClass().getResource("/views/admin_dashboard_gu.fxml"));
            Stage stage = (Stage) gestionUtilisateurButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
