package com.esprit.gu.controller;

import com.esprit.gu.entity.Utilisateur;
import com.esprit.gu.service.UtilisateurService;
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
    @FXML
    private PasswordField passwordField;

    // Error Labels for validation feedback
    @FXML
    private Label emailErrorLabel;
    @FXML
    private Label passwordErrorLabel;

    private UtilisateurService utilisateurService = new UtilisateurService();

    @FXML
    private void handleLogin() {
        // Validate inputs before attempting login
        if (!validateLoginInputs()) {
            return;
        }

        String email = emailField.getText();
        String password = passwordField.getText();

        Utilisateur user = utilisateurService.login(email, password);
        if (user != null) {
            Session.setCurrentUser(user);

            if ("admin".equalsIgnoreCase(user.getRoleUtilisateur())) {
                loadStage("/views/admin_dashboard.fxml");
            } else if ("utilisateur".equalsIgnoreCase(user.getRoleUtilisateur())) {
                loadStage("/views/profile.fxml");
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Failed", "Unknown user role.");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid email or password.");
        }
    }

    // Basic email pattern for demonstration purposes
    private boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(regex);
    }

    private boolean validateLoginInputs() {
        boolean valid = true;
        String email = emailField.getText();
        String password = passwordField.getText();

        // Validate Email
        if (email == null || email.isEmpty() || !isValidEmail(email)) {
            emailErrorLabel.setText("Please enter a valid email.");
            emailErrorLabel.setVisible(true);
            valid = false;
        } else {
            emailErrorLabel.setVisible(false);
        }

        // Validate Password
        if (password == null || password.isEmpty()) {
            passwordErrorLabel.setText("Password is required.");
            passwordErrorLabel.setVisible(true);
            valid = false;
        } else {
            passwordErrorLabel.setVisible(false);
        }

        return valid;
    }

    @FXML
    private void goToRegister() throws IOException {
        Stage stage = (Stage) emailField.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/views/register.fxml"));
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
}
