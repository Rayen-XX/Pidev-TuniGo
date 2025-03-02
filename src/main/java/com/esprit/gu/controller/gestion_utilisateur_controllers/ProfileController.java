package com.esprit.gu.controller.gestion_utilisateur_controllers;

import com.esprit.gu.entity.gestion_utilisateur_entity.Utilisateur;
import com.esprit.gu.util.Session;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.io.IOException;

public class ProfileController implements Initializable {

    @FXML
    private Label welcomeLabel;
    
    @FXML
    private Label userFullNameLabel;
    
    @FXML
    private Label userEmailLabel;
    
    @FXML
    private Label userPhoneLabel;

    @FXML
    private Hyperlink logoutLink;

    @FXML
    private Button passerReclamationButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Get the current user from session and display his/her name.
        Utilisateur currentUser = Session.getCurrentUser();
        if (currentUser != null) {
            welcomeLabel.setText("Bonjour, " + currentUser.getNomUtilisateur());
            
            // Set the user profile information
            userFullNameLabel.setText(currentUser.getNomUtilisateur() + " " + currentUser.getPrenomUtilisateur());
            userEmailLabel.setText("Email: " + currentUser.getEmailUtilisateur());
            userPhoneLabel.setText("Téléphone: " + currentUser.getNumeroTelephoneUtilisateur());
        } else {
            welcomeLabel.setText("Utilisateur non connecté");
            
            // Set default values if no user is logged in
            userFullNameLabel.setText("Non connecté");
            userEmailLabel.setText("Email: N/A");
            userPhoneLabel.setText("Téléphone: N/A");
        }

        // Set up logout action.
        logoutLink.setOnAction(e -> {
            Session.clear();  // Clear the current session
            try {
                // Load the login view and set it as the current scene.
                Stage stage = (Stage) logoutLink.getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource("/views/gestion_utilisateur_views/login.fxml"));
                stage.setScene(new Scene(root));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    @FXML
    private void handleUpdateProfile() {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/views/gestion_utilisateur_views/updateProfile.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Update Profile");
            stage.showAndWait();

            // Optionally, refresh the welcome label with updated info:
            Utilisateur currentUser = Session.getCurrentUser();
            if (currentUser != null) {
                welcomeLabel.setText("Bonjour, " + currentUser.getNomUtilisateur());
                userFullNameLabel.setText(currentUser.getNomUtilisateur() + " " + currentUser.getPrenomUtilisateur());
                userEmailLabel.setText("Email: " + currentUser.getEmailUtilisateur());
                userPhoneLabel.setText("Téléphone: " + currentUser.getNumeroTelephoneUtilisateur());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void passerReclamation() {
        try {
            // Load the reclamation page
            Parent root = FXMLLoader.load(getClass().getResource("/views/AjouterReclamation.fxml"));
            Stage stage = (Stage) passerReclamationButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void reserverMaintenant() {
        try {
            // Load the moyTransport.fxml file
            Parent root = FXMLLoader.load(getClass().getResource("/views/gestion_utilisateur_views/moyTransport.fxml"));
            Stage stage = (Stage) passerReclamationButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
