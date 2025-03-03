package com.esprit.gu.controller.gestion_utilisateur_controllers;

import com.esprit.gu.entity.gestion_utilisateur_entity.Utilisateur;
import com.esprit.gu.service.gestion_utilisateur_service.FacebookAuthService;
import com.esprit.gu.service.gestion_utilisateur_service.GoogleAuthService;
import com.esprit.gu.service.gestion_utilisateur_service.UtilisateurService;
import com.esprit.gu.service.gestion_utilisateur_service.GitHubAuthService;
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
import javafx.application.Platform;

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
    private GoogleAuthService googleAuthService = new GoogleAuthService();
    private GitHubAuthService gitHubAuthService = new GitHubAuthService();

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
        
        try {
            // Get the current stage
            Stage stage = (Stage) emailField.getScene().getWindow();
            
            // Start the Facebook login process
            System.out.println("Starting Facebook login process...");
            CompletableFuture<Utilisateur> userFuture = facebookAuthService.startFacebookLogin(stage);
            
            // Handle the result when the login completes
            userFuture.thenAccept(user -> {
                System.out.println("Facebook login completed, user: " + (user != null ? "found" : "null"));
                
                if (user != null) {
                    try {
                        // Set the current user in the session
                        Session.setCurrentUser(user);
                        
                        System.out.println("Facebook login successful for user with role: " + user.getRoleUtilisateur());
                        
                        // Redirect to profile page - ensure UI updates run on JavaFX thread
                        javafx.application.Platform.runLater(() -> {
                            try {
                                // Always go to profile page for Facebook login
                                System.out.println("Loading profile page after Facebook login");
                                directLoadProfile();
                            } catch (Exception e) {
                                System.out.println("Error loading profile page: " + e.getMessage());
                                e.printStackTrace();
                                // Try an alternative loading method
                                try {
                                    System.out.println("Trying alternative profile loading method");
                                    createAndShowProfileScene();
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                    showAlert(Alert.AlertType.ERROR, "Navigation Error", 
                                            "Error loading profile page. Try regular login instead.");
                                }
                            }
                        });
                    } catch (Exception e) {
                        System.out.println("Error in session handling after Facebook login: " + e.getMessage());
                        e.printStackTrace();
                        javafx.application.Platform.runLater(() -> {
                            showAlert(Alert.AlertType.ERROR, "Session Error", 
                                    "Error handling user session after Facebook login: " + e.getMessage());
                        });
                    }
                } else {
                    javafx.application.Platform.runLater(() -> {
                        showAlert(Alert.AlertType.ERROR, "Facebook Login Failed", 
                                "Could not login with Facebook. Please try again or use email/password.");
                    });
                }
            }).exceptionally(ex -> {
                System.out.println("Facebook login exception: " + ex.getMessage());
                ex.printStackTrace();
                
                // Get the root cause of the exception
                Throwable rootCause = ex;
                while (rootCause.getCause() != null) {
                    rootCause = rootCause.getCause();
                }
                
                final String errorMessage = rootCause.getMessage();
                
                javafx.application.Platform.runLater(() -> {
                    showAlert(Alert.AlertType.ERROR, "Facebook Login Error", 
                            "Error during Facebook login: " + errorMessage);
                });
                return null;
            });
        } catch (Exception e) {
            System.out.println("Exception initiating Facebook login: " + e.getMessage());
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Facebook Login Error",
                    "Could not start Facebook login: " + e.getMessage());
        }
    }
    
    // Direct method to load profile page, bypassing other checks
    private void directLoadProfile() {
        try {
            System.out.println("Loading profile view directly");
            Stage stage = (Stage) emailField.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/gestion_utilisateur_views/profile.fxml"));
            Parent root = loader.load();
            System.out.println("Profile view loaded successfully");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            System.out.println("Scene set, showing profile stage");
        } catch (IOException e) {
            System.out.println("IOException in directLoadProfile: " + e.getMessage());
            throw new RuntimeException("Failed to load profile page: " + e.getMessage(), e);
        }
    }
    
    // Alternative method to create and show profile scene
    private void createAndShowProfileScene() {
        try {
            System.out.println("Creating new profile scene");
            Stage stage = (Stage) emailField.getScene().getWindow();
            
            // Create a simple profile scene if FXML loading fails
            javafx.scene.layout.VBox root = new javafx.scene.layout.VBox(10);
            root.setAlignment(javafx.geometry.Pos.CENTER);
            root.setPadding(new javafx.geometry.Insets(20));
            root.setStyle("-fx-background-color: white;");
            
            Utilisateur user = Session.getCurrentUser();
            
            Label titleLabel = new Label("TuniGo - Profil");
            titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #B22222;");
            
            Label nameLabel = new Label(user != null ? 
                user.getNomUtilisateur() + " " + user.getPrenomUtilisateur() : "Utilisateur Facebook");
            nameLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
            
            Label emailLabel = new Label(user != null ? 
                "Email: " + user.getEmailUtilisateur() : "Email: Facebook User");
            
            Button reserverButton = new Button("Réserver maintenant");
            reserverButton.setStyle("-fx-background-color: #B22222; -fx-text-fill: white;");
            reserverButton.setOnAction(e -> {
                try {
                    Parent transportRoot = FXMLLoader.load(getClass().getResource("/views/gestion_utilisateur_views/moyTransport.fxml"));
                    stage.setScene(new Scene(transportRoot));
                } catch (IOException ex) {
                    ex.printStackTrace();
                    showAlert(Alert.AlertType.ERROR, "Navigation Error", "Could not load transport page.");
                }
            });
            
            Button logoutButton = new Button("Déconnecter");
            logoutButton.setOnAction(e -> {
                Session.clear();
                try {
                    Parent loginRoot = FXMLLoader.load(getClass().getResource("/views/gestion_utilisateur_views/login.fxml"));
                    stage.setScene(new Scene(loginRoot));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            
            root.getChildren().addAll(titleLabel, nameLabel, emailLabel, reserverButton, logoutButton);
            
            Scene scene = new Scene(root, 800, 600);
            stage.setScene(scene);
            System.out.println("Alternative profile scene created and set");
        } catch (Exception e) {
            System.out.println("Exception in createAndShowProfileScene: " + e.getMessage());
            throw new RuntimeException("Failed to create profile scene: " + e.getMessage(), e);
        }
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
            System.out.println("Loading view: " + fxmlPath);
            Stage stage = (Stage) emailField.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            System.out.println("View loaded successfully");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            System.out.println("Scene set, showing stage");
        } catch (IOException e) {
            System.out.println("IOException in loadStage for path " + fxmlPath + ": " + e.getMessage());
            e.printStackTrace();
            if (e.getCause() != null) {
                System.out.println("Root cause: " + e.getCause().getMessage());
                e.getCause().printStackTrace();
            }
            showAlert(Alert.AlertType.ERROR, "Navigation Error", 
                    "Could not load the requested view (" + fxmlPath + "): " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception in loadStage for path " + fxmlPath + ": " + e.getMessage());
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigation Error", 
                    "An unexpected error occurred while loading the view: " + e.getMessage());
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

    @FXML
    private void handleGoogleLogin() {
        System.out.println("Google login button clicked");
        
        try {
            // Get the current stage
            Stage stage = (Stage) emailField.getScene().getWindow();
            
            // Start the Google login process
            googleAuthService.startGoogleLogin(stage)
                .thenAccept(user -> {
                    if (user != null) {
                        System.out.println("Google login successful for: " + user.getEmailUtilisateur());
                        
                        // Set the current user in the session
                        Session.setCurrentUser(user);
                        
                        // Redirect to the appropriate page based on user role
                        Platform.runLater(() -> {
                            try {
                                if ("admin".equalsIgnoreCase(user.getRoleUtilisateur())) {
                                    loadStage("/views/gestion_utilisateur_views/admin_dashboard_all.fxml");
                                } else {
                                    loadStage("/views/gestion_utilisateur_views/profile.fxml");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                showAlert(Alert.AlertType.ERROR, "Navigation Error", 
                                        "An error occurred while navigating to the next screen: " + e.getMessage());
                            }
                        });
                    } else {
                        Platform.runLater(() -> {
                            showAlert(Alert.AlertType.ERROR, "Login Failed", 
                                    "Failed to login with Google. Please try again.");
                        });
                    }
                })
                .exceptionally(ex -> {
                    ex.printStackTrace();
                    Platform.runLater(() -> {
                        showAlert(Alert.AlertType.ERROR, "Google Login Error", 
                                "An error occurred during Google login: " + ex.getMessage());
                    });
                    return null;
                });
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Google Login Error", 
                    "An error occurred during Google login: " + e.getMessage());
        }
    }

    @FXML
    private void handleGitHubLogin() {
        System.out.println("GitHub login button clicked");
        
        try {
            Stage currentStage = (Stage) loginButton.getScene().getWindow();
            
            // Start the GitHub login process and get the user
            gitHubAuthService.startGitHubLogin(currentStage)
                .thenAccept(user -> {
                    if (user != null) {
                        System.out.println("GitHub login successful for user: " + user.getEmailUtilisateur());
                        
                        // Set the current user in the session
                        Session.setCurrentUser(user);
                        
                        // Redirect based on user role
                        Platform.runLater(() -> {
                            try {
                                if ("admin".equalsIgnoreCase(user.getRoleUtilisateur())) {
                                    System.out.println("Loading admin dashboard...");
                                    loadStage("/views/gestion_utilisateur_views/admin_dashboard_all.fxml");
                                } else {
                                    System.out.println("Loading user profile...");
                                    loadStage("/views/gestion_utilisateur_views/profile.fxml");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                showAlert(Alert.AlertType.ERROR, "Navigation Error", 
                                         "Error navigating after GitHub login: " + e.getMessage());
                            }
                        });
                    } else {
                        Platform.runLater(() -> {
                            showAlert(Alert.AlertType.ERROR, "GitHub Login Failed", 
                                     "Unable to login with GitHub.");
                        });
                    }
                })
                .exceptionally(ex -> {
                    ex.printStackTrace();
                    Platform.runLater(() -> {
                        showAlert(Alert.AlertType.ERROR, "GitHub Login Error", 
                                 "An error occurred during GitHub login: " + ex.getMessage());
                    });
                    return null;
                });
            
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "GitHub Login Error", 
                     "An error occurred: " + e.getMessage());
        }
    }
}
