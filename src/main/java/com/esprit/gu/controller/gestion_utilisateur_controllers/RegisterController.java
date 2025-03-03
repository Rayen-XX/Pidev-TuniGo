package com.esprit.gu.controller.gestion_utilisateur_controllers;

import com.esprit.gu.entity.gestion_utilisateur_entity.Utilisateur;
import com.esprit.gu.service.gestion_utilisateur_service.UtilisateurService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private TextField telephoneField;
    @FXML private TextField emailField;

    // Password fields for "Mot de passe"
    @FXML private PasswordField motdepasseField;
    @FXML private TextField motdepasseVisibleField;

    // Password fields for "Confirmer votre mot de passe"
    @FXML private PasswordField confirmPasswordField;
    @FXML private TextField confirmPasswordVisibleField;

    // Security question and answer fields
    @FXML private ChoiceBox<String> securityQuestionChoiceBox;
    @FXML private TextField securityAnswerField;

    // Global error label for validation feedback
    @FXML private Label globalErrorLabel;

    private UtilisateurService utilisateurService = new UtilisateurService();
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // No need for styling here anymore, handled by CSS
    }

    @FXML
    private void handleRegister() throws IOException {
        if (!validateRegisterInputs()) {
            return;
        }

        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String email = emailField.getText();
        String telephone = telephoneField.getText();
        String motdepasse = getPassword();

        // Retrieve the chosen security question and answer.
        String questionSecurite = securityQuestionChoiceBox.getValue();
        String reponseSecurite = securityAnswerField.getText();

        // Create a new user with all fields including security question and answer
        Utilisateur newUtilisateur = new Utilisateur(nom, prenom, email, motdepasse, telephone, "utilisateur", questionSecurite, reponseSecurite);

        if (utilisateurService.register(newUtilisateur)) {
            showAlert(Alert.AlertType.INFORMATION, "Inscription réussie", "L'utilisateur " + email + " a été inscrit avec succès !");
            goToLogin();
        } else {
            showAlert(Alert.AlertType.ERROR, "Échec de l'inscription", "L'e-mail existe déjà ou l'inscription a échoué.");
        }
    }

    private boolean validateRegisterInputs() {
        boolean valid = true;
        StringBuilder errorMessages = new StringBuilder();

        if (nomField.getText() == null || nomField.getText().trim().isEmpty()) {
            errorMessages.append("Le nom est obligatoire. ");
            valid = false;
        }
        if (prenomField.getText() == null || prenomField.getText().trim().isEmpty()) {
            errorMessages.append("Le prénom est obligatoire. ");
            valid = false;
        }

        String email = emailField.getText();
        if (email == null || email.trim().isEmpty() || !isValidEmail(email)) {
            errorMessages.append("Veuillez entrer une adresse e-mail valide. ");
            valid = false;
        }

        String password = getPassword();
        if (password == null || password.trim().isEmpty() || password.length() < 6) {
            errorMessages.append("Le mot de passe doit contenir au moins 6 caractères. ");
            valid = false;
        }

        String confirmPassword = getConfirmPassword();
        if (confirmPassword == null || confirmPassword.trim().isEmpty()) {
            errorMessages.append("Veuillez confirmer votre mot de passe. ");
            valid = false;
        } else if (!password.equals(confirmPassword)) {
            errorMessages.append("Les mots de passe ne correspondent pas. ");
            valid = false;
        }

        // Validate telephone
        if (telephoneField.getText() == null || telephoneField.getText().trim().isEmpty() || !isValidPhone(telephoneField.getText())) {
            errorMessages.append("Veuillez entrer un numéro de téléphone valide. ");
            valid = false;
        }

        // Validate that a security question is chosen and an answer provided.
        if (securityQuestionChoiceBox.getValue() == null || securityQuestionChoiceBox.getValue().trim().isEmpty()) {
            errorMessages.append("Veuillez sélectionner une question de sécurité. ");
            valid = false;
        }
        if (securityAnswerField.getText() == null || securityAnswerField.getText().trim().isEmpty()) {
            errorMessages.append("Veuillez répondre à la question de sécurité. ");
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

    private String getPassword() {
        return motdepasseField.isVisible() ? motdepasseField.getText() : motdepasseVisibleField.getText();
    }

    private String getConfirmPassword() {
        return confirmPasswordField.isVisible() ? confirmPasswordField.getText() : confirmPasswordVisibleField.getText();
    }

    private boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(regex);
    }

    private boolean isValidPhone(String phone) {
        return phone.matches("\\d{8,15}");
    }

    @FXML
    private void goToLogin() throws IOException {
        Stage stage = (Stage) emailField.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/views/gestion_utilisateur_views/login.fxml"));
        stage.setScene(new Scene(root));
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Toggle visibility for the main password field
    @FXML
    private void togglePasswordVisibility() {
        if (motdepasseVisibleField.isVisible()) {
            motdepasseField.setText(motdepasseVisibleField.getText());
            motdepasseVisibleField.setVisible(false);
            motdepasseVisibleField.setManaged(false);
            motdepasseField.setVisible(true);
            motdepasseField.setManaged(true);
        } else {
            motdepasseVisibleField.setText(motdepasseField.getText());
            motdepasseVisibleField.setVisible(true);
            motdepasseVisibleField.setManaged(true);
            motdepasseField.setVisible(false);
            motdepasseField.setManaged(false);
        }
    }

    // Toggle visibility for the confirm password field
    @FXML
    private void toggleConfirmPasswordVisibility() {
        if (confirmPasswordVisibleField.isVisible()) {
            confirmPasswordField.setText(confirmPasswordVisibleField.getText());
            confirmPasswordVisibleField.setVisible(false);
            confirmPasswordVisibleField.setManaged(false);
            confirmPasswordField.setVisible(true);
            confirmPasswordField.setManaged(true);
        } else {
            confirmPasswordVisibleField.setText(confirmPasswordField.getText());
            confirmPasswordVisibleField.setVisible(true);
            confirmPasswordVisibleField.setManaged(true);
            confirmPasswordField.setVisible(false);
            confirmPasswordField.setManaged(false);
        }
    }
}
