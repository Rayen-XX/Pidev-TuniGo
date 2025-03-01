package com.esprit.gu.controller.gestion_utilisateur_controllers;

import com.esprit.gu.entity.gestion_utilisateur_entity.Utilisateur;
import com.esprit.gu.service.gestion_utilisateur_service.UtilisateurService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ForgotPasswordController {

    @FXML private TextField fpEmailField;
    @FXML private VBox emailSearchSection;
    @FXML private VBox resetSection;
    @FXML private Label securityQuestionLabel;
    @FXML private TextField securityAnswerField;
    @FXML private PasswordField newPasswordField;
    @FXML private TextField newPasswordVisibleField;
    @FXML private PasswordField confirmNewPasswordField;
    @FXML private TextField confirmNewPasswordVisibleField;
    @FXML private Label fpErrorLabel;

    private UtilisateurService utilisateurService = new UtilisateurService();
    private Utilisateur foundUser; // The user retrieved by email

    @FXML
    private void handleEmailSearch() {
        String email = fpEmailField.getText();
        if (email == null || email.trim().isEmpty()) {
            fpErrorLabel.setText("Veuillez entrer votre adresse e-mail.");
            fpErrorLabel.setVisible(true);
            return;
        }
        foundUser = utilisateurService.getUtilisateurByEmail(email);
        if (foundUser == null) {
            fpErrorLabel.setText("Aucun utilisateur trouvé avec cet e-mail.");
            fpErrorLabel.setVisible(true);
        } else {
            fpErrorLabel.setVisible(false);
            String question = foundUser.getQuestionSecurite();
            if (question == null || question.trim().isEmpty()) {
                question = "Quelle est votre question de sécurité ?"; // fallback default
            }
            securityQuestionLabel.setText(question);
            // Reveal the reset section.
            resetSection.setVisible(true);
            resetSection.setManaged(true);
        }
    }

    @FXML
    private void handleResetPassword() throws IOException {
        if (foundUser == null) {
            fpErrorLabel.setText("Veuillez d'abord rechercher votre e-mail.");
            fpErrorLabel.setVisible(true);
            return;
        }
        String answer = securityAnswerField.getText();
        if (answer == null || answer.trim().isEmpty()) {
            fpErrorLabel.setText("Veuillez répondre à la question de sécurité.");
            fpErrorLabel.setVisible(true);
            return;
        }
        // Validate the security answer (case-insensitive)
        if (!answer.trim().equalsIgnoreCase(foundUser.getReponseSecurite())) {
            fpErrorLabel.setText("Réponse incorrecte à la question de sécurité.");
            fpErrorLabel.setVisible(true);
            return;
        }
        String newPassword = newPasswordField.isVisible() ? newPasswordField.getText() : newPasswordVisibleField.getText();
        String confirmPassword = confirmNewPasswordField.isVisible() ? confirmNewPasswordField.getText() : confirmNewPasswordVisibleField.getText();
        if (newPassword == null || newPassword.trim().isEmpty() || newPassword.length() < 6) {
            fpErrorLabel.setText("Le nouveau mot de passe doit contenir au moins 6 caractères.");
            fpErrorLabel.setVisible(true);
            return;
        }
        if (!newPassword.equals(confirmPassword)) {
            fpErrorLabel.setText("Les nouveaux mots de passe ne correspondent pas.");
            fpErrorLabel.setVisible(true);
            return;
        }
        // Update the user's password.
        foundUser.setMotDePasseUtilisateur(newPassword);
        boolean updated = utilisateurService.updateUtilisateur(foundUser);
        if (updated) {
            showAlert(Alert.AlertType.INFORMATION, "Réinitialisation réussie", "Votre mot de passe a été réinitialisé avec succès !");
            goToLogin();
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "La réinitialisation du mot de passe a échoué.");
        }
    }

    @FXML
    private void toggleNewPasswordVisibility() {
        if (newPasswordVisibleField.isVisible()) {
            newPasswordField.setText(newPasswordVisibleField.getText());
            newPasswordVisibleField.setVisible(false);
            newPasswordVisibleField.setManaged(false);
            newPasswordField.setVisible(true);
            newPasswordField.setManaged(true);
        } else {
            newPasswordVisibleField.setText(newPasswordField.getText());
            newPasswordVisibleField.setVisible(true);
            newPasswordVisibleField.setManaged(true);
            newPasswordField.setVisible(false);
            newPasswordField.setManaged(false);
        }
    }

    @FXML
    private void toggleConfirmNewPasswordVisibility() {
        if (confirmNewPasswordVisibleField.isVisible()) {
            confirmNewPasswordField.setText(confirmNewPasswordVisibleField.getText());
            confirmNewPasswordVisibleField.setVisible(false);
            confirmNewPasswordVisibleField.setManaged(false);
            confirmNewPasswordField.setVisible(true);
            confirmNewPasswordField.setManaged(true);
        } else {
            confirmNewPasswordVisibleField.setText(confirmNewPasswordField.getText());
            confirmNewPasswordVisibleField.setVisible(true);
            confirmNewPasswordVisibleField.setManaged(true);
            confirmNewPasswordField.setVisible(false);
            confirmNewPasswordField.setManaged(false);
        }
    }

    @FXML
    private void goToLogin() throws IOException {
        Stage stage = (Stage) fpEmailField.getScene().getWindow();
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
}
