package com.esprit.gu.controller;

import com.esprit.gu.entity.Utilisateur;
import com.esprit.gu.service.UtilisateurService;
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

public class RegisterController {
    @FXML
    private TextField nomField;
    @FXML
    private TextField prenomField;
    @FXML
    private TextField telephoneField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField motdepasseField;

    // Error Labels for validation feedback
    @FXML
    private Label nomErrorLabel;
    @FXML
    private Label prenomErrorLabel;
    @FXML
    private Label emailErrorLabel;
    @FXML
    private Label motdepasseErrorLabel;
    @FXML
    private Label telephoneErrorLabel;

    private UtilisateurService utilisateurService = new UtilisateurService();

    @FXML
    private void handleRegister() throws IOException {
        if (!validateRegisterInputs()) {
            return;
        }

        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String email = emailField.getText();
        String telephone = telephoneField.getText();
        String motdepasse = motdepasseField.getText();

        Utilisateur newUtilisateur = new Utilisateur(nom, prenom, email, motdepasse, telephone, "utilisateur");
        if (utilisateurService.register(newUtilisateur)) {
            showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "User " + email + " registered successfully!");
            goToLogin();
        } else {
            showAlert(Alert.AlertType.ERROR, "Registration Failed", "Email already exists or registration failed.");
        }
    }

    private boolean validateRegisterInputs() {
        boolean valid = true;

        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String email = emailField.getText();
        String password = motdepasseField.getText();
        String telephone = telephoneField.getText();

        // Validate Nom
        if (nom == null || nom.isEmpty()) {
            nomErrorLabel.setText("Name is required.");
            nomErrorLabel.setVisible(true);
            valid = false;
        } else {
            nomErrorLabel.setVisible(false);
        }

        // Validate Prenom
        if (prenom == null || prenom.isEmpty()) {
            prenomErrorLabel.setText("Prenom is required.");
            prenomErrorLabel.setVisible(true);
            valid = false;
        } else {
            prenomErrorLabel.setVisible(false);
        }

        // Validate Email
        if (email == null || email.isEmpty() || !isValidEmail(email)) {
            emailErrorLabel.setText("Please enter a valid email.");
            emailErrorLabel.setVisible(true);
            valid = false;
        } else {
            emailErrorLabel.setVisible(false);
        }

        // Validate Password (at least 6 characters)
        if (password == null || password.isEmpty() || password.length() < 6) {
            motdepasseErrorLabel.setText("Password must be at least 6 characters.");
            motdepasseErrorLabel.setVisible(true);
            valid = false;
        } else {
            motdepasseErrorLabel.setVisible(false);
        }

        // Validate Telephone (digits only; adjust regex as needed)
        if (telephone == null || telephone.isEmpty() || !isValidPhone(telephone)) {
            telephoneErrorLabel.setText("Please enter a valid phone number.");
            telephoneErrorLabel.setVisible(true);
            valid = false;
        } else {
            telephoneErrorLabel.setVisible(false);
        }

        return valid;
    }

    private boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(regex);
    }

    private boolean isValidPhone(String phone) {
        // This regex checks that the phone number consists of 8 to 15 digits.
        return phone.matches("\\d{8,15}");
    }

    @FXML
    private void goToLogin() throws IOException {
        Stage stage = (Stage) emailField.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
        stage.setScene(new Scene(root));
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
