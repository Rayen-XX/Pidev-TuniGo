package com.esprit.gu.controller.gestion_utilisateur_controllers;

import com.esprit.gu.entity.gestion_utilisateur_entity.Utilisateur;
import com.esprit.gu.service.gestion_utilisateur_service.FacebookAuthService;
import com.esprit.gu.service.gestion_utilisateur_service.UtilisateurService;
import com.esprit.gu.util.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

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

    @FXML
    private Button loginButton;

    private UtilisateurService utilisateurService = new UtilisateurService();
    private FacebookAuthService facebookAuthService = new FacebookAuthService();

    @FXML
    private void initialize() {
        System.out.println("LoginController initializing...");
        
        // Enable any buttons that might have been disabled
        if (loginButton != null) {
            System.out.println("Login button found and configured");
            loginButton.setDisable(false);
            
            // Add a direct event handler just in case there's an issue with the FXML binding
            loginButton.setOnAction(e -> {
                System.out.println("Login button clicked via direct event handler");
                handleLogin();
            });
        } else {
            System.out.println("WARNING: loginButton is null in initialize()");
        }
    }

    @FXML
    private void handleLogin() {
        try {
            System.out.println("Login button clicked");
            
            // Always read from the visible field if it's showing, else from the hidden field
            String password = passwordField.isVisible() ? passwordField.getText() : passwordVisibleField.getText();
            String email = emailField.getText();
            
            System.out.println("Email entered: " + email);
            System.out.println("Password entered: " + (password != null ? "******" : "null"));
            
            // Validate inputs
            System.out.println("Validating login inputs...");
            if (!validateLoginInputs()) {
                System.out.println("Input validation failed");
                return;
            }
            
            System.out.println("Input validation passed, attempting login...");
            
            // Attempt login
            Utilisateur user = utilisateurService.login(email, password);
            
            if (user != null) {
                System.out.println("Login successful for user: " + email + " with role: " + user.getRoleUtilisateur());
                
                // Set the current user in the session
                Session.setCurrentUser(user);
                
                // Redirect based on user role
                if ("admin".equalsIgnoreCase(user.getRoleUtilisateur())) {
                    System.out.println("Loading admin dashboard...");
                    loadStage("/views/gestion_utilisateur_views/admin_dashboard_all.fxml");
                } else if ("utilisateur".equalsIgnoreCase(user.getRoleUtilisateur())) {
                    System.out.println("Loading user profile...");
                    loadStage("/views/gestion_utilisateur_views/profile.fxml");
                } else {
                    System.out.println("Unknown user role: " + user.getRoleUtilisateur());
                    showAlert(Alert.AlertType.ERROR, "Login Failed", "Unknown user role.");
                }
            } else {
                System.out.println("Login failed: Invalid email or password");
                showAlert(Alert.AlertType.ERROR, "Échec de la connexion", "E-mail ou mot de passe invalide.");
            }
        } catch (Exception e) {
            System.out.println("Exception in handleLogin: " + e.getMessage());
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Login Error", "An error occurred during login: " + e.getMessage());
        }
    }
    
    @FXML
    private void handleFacebookLogin() {
        System.out.println("Facebook login button clicked");
        
        // Get the current stage
        Stage stage = (Stage) emailField.getScene().getWindow();
        
        // Start the Facebook login process
        System.out.println("Starting Facebook login process...");
        CompletableFuture<Utilisateur> userFuture = facebookAuthService.startFacebookLogin(stage);
        
        // Handle the result when the login completes
        userFuture.thenAccept(user -> {
            System.out.println("Facebook login completed, user: " + (user != null ? "found" : "null"));
            
            if (user != null) {
                // Set the current user in the session
                Session.setCurrentUser(user);
                
                System.out.println("Facebook login successful for user with role: " + user.getRoleUtilisateur());
                
                // Redirect based on user role
                javafx.application.Platform.runLater(() -> {
                    if ("admin".equalsIgnoreCase(user.getRoleUtilisateur())) {
                        loadStage("/views/gestion_utilisateur_views/admin_dashboard_all.fxml");
                    } else if ("utilisateur".equalsIgnoreCase(user.getRoleUtilisateur())) {
                        loadStage("/views/gestion_utilisateur_views/profile.fxml");
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Login Failed", "Unknown user role.");
                    }
                });
            } else {
                javafx.application.Platform.runLater(() -> {
                    showAlert(Alert.AlertType.ERROR, "Facebook Login Failed", 
                            "Could not login with Facebook. Please try again or use email/password.");
                });
            }
        }).exceptionally(ex -> {
            System.out.println("Facebook login exception: " + ex.getMessage());
            ex.printStackTrace();
            
            javafx.application.Platform.runLater(() -> {
                showAlert(Alert.AlertType.ERROR, "Facebook Login Error", 
                        "Error during Facebook login: " + ex.getMessage());
            });
            return null;
        });
    }

    private boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(regex);
    }

    private boolean validateLoginInputs() {
        System.out.println("In validateLoginInputs method");
        boolean valid = true;
        StringBuilder errorMessages = new StringBuilder();

        String email = emailField.getText();
        String password = passwordField.isVisible() ? passwordField.getText() : passwordVisibleField.getText();

        System.out.println("Validating email: " + email);
        if (email == null || email.trim().isEmpty() || !isValidEmail(email)) {
            errorMessages.append("Veuillez entrer une adresse e-mail valide. ");
            valid = false;
            System.out.println("Email validation failed");
        }
        
        System.out.println("Validating password");
        if (password == null || password.trim().isEmpty()) {
            errorMessages.append("Le mot de passe est obligatoire. ");
            valid = false;
            System.out.println("Password validation failed");
        }

        if (!valid) {
            System.out.println("Showing validation errors: " + errorMessages.toString());
            globalErrorLabel.setText(errorMessages.toString());
            globalErrorLabel.setVisible(true);
        } else {
            System.out.println("Validation passed");
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
