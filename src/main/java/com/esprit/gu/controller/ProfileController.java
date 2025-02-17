package com.esprit.gu.controller;

import com.esprit.gu.entity.Utilisateur;
import com.esprit.gu.util.Session;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    private Hyperlink logoutLink;

    @Override
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
    private void handleUpdateProfile() {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/views/updateProfile.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Update Profile");
            stage.showAndWait();

            // Optionally, refresh the welcome label with updated info:
            Utilisateur currentUser = Session.getCurrentUser();
            if (currentUser != null) {
                welcomeLabel.setText("Bonjour, " + currentUser.getNomUtilisateur() + " "
                        + currentUser.getPrenomUtilisateur());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
