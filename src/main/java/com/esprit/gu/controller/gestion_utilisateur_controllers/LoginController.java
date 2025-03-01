package com.esprit.gu.controller.gestion_utilisateur_controllers;

import com.esprit.gu.entity.gestion_utilisateur_entity.Utilisateur;
import com.esprit.gu.service.gestion_utilisateur_service.UtilisateurService;
import com.esprit.gu.util.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField emailField;

    // For the password input, we use two fields
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField passwordVisibleField;

    // Global error label for login validation
    @FXML
    private Label globalErrorLabel;

    private UtilisateurService utilisateurService = new UtilisateurService();

    @FXML
    private void handleLogin() {
        if (!validateLoginInputs()) {
            return;
        }

        // Always read from the visible field if it's showing, else from the hidden field
        String password = passwordField.isVisible() ? passwordField.getText() : passwordVisibleField.getText();
        String email = emailField.getText();

        Utilisateur user = utilisateurService.login(email, password);
        if (user != null) {
            Session.setCurrentUser(user);
            if ("admin".equalsIgnoreCase(user.getRoleUtilisateur())) {
                loadStage("/views/gestion_utilisateur_views/admin_dashboard_all.fxml");
            } else if ("utilisateur".equalsIgnoreCase(user.getRoleUtilisateur())) {
                loadStage("/views/gestion_utilisateur_views/profile.fxml");
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Failed", "Unknown user role.");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Échec de la connexion", "E-mail ou mot de passe invalide.");
        }
    }

    private boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(regex);
    }

    private boolean validateLoginInputs() {
        boolean valid = true;
        StringBuilder errorMessages = new StringBuilder();

        String email = emailField.getText();
        String password = passwordField.isVisible() ? passwordField.getText() : passwordVisibleField.getText();

        if (email == null || email.trim().isEmpty() || !isValidEmail(email)) {
            errorMessages.append("Veuillez entrer une adresse e-mail valide. ");
            valid = false;
        }
        if (password == null || password.trim().isEmpty()) {
            errorMessages.append("Le mot de passe est obligatoire. ");
            valid = false;
        }

        if (!valid) {
            globalErrorLabel.setText(errorMessages.toString());
            globalErrorLabel.setVisible(true);
        } else {
            globalErrorLabel.setVisible(false);
        }
        return valid;
    }

    @FXML
    private void goToRegister() throws IOException {
        Stage stage = (Stage) emailField.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/views/gestion_utilisateur_views/register.fxml"));
        stage.setScene(new Scene(root));
    }

    private void loadStage(String fxmlPath) {
        try {
            Stage stage = (Stage) emailField.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Could not load the new window.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Toggle password visibility in the login form
    @FXML
    private void togglePasswordVisibility() {
        if (passwordVisibleField.isVisible()) {
            // Hide the visible field and show the PasswordField
            passwordField.setText(passwordVisibleField.getText());
            passwordVisibleField.setVisible(false);
            passwordVisibleField.setManaged(false);
            passwordField.setVisible(true);
            passwordField.setManaged(true);
        } else {
            // Show the visible field and hide the PasswordField
            passwordVisibleField.setText(passwordField.getText());
            passwordVisibleField.setVisible(true);
            passwordVisibleField.setManaged(true);
            passwordField.setVisible(false);
            passwordField.setManaged(false);
        }
    }

    @FXML
    private void goToForgotPassword() throws IOException {
        Stage stage = (Stage) emailField.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/views/gestion_utilisateur_views/forgotPassword.fxml"));
        stage.setScene(new Scene(root));
    }
}
