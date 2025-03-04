package com.esprit.gu.controller;

import com.esprit.gu.entity.Utilisateur;
import com.esprit.gu.service.UtilisateurService;
import com.esprit.gu.util.Session;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdateProfileController implements Initializable {

    @FXML
    private TextField nomField;
    @FXML
    private TextField prenomField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField numeroField;
    @FXML
    private PasswordField motdepasseField;
    @FXML
    private Button updateButton;


    private UtilisateurService utilisateurService = new UtilisateurService();
    private Utilisateur currentUser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Get the current user from session and populate the form fields.
        currentUser = Session.getCurrentUser();
        if (currentUser != null) {
            nomField.setText(currentUser.getNomUtilisateur());
            prenomField.setText(currentUser.getPrenomUtilisateur());
            emailField.setText(currentUser.getEmailUtilisateur());
            motdepasseField.setText(currentUser.getMotDePasseUtilisateur());
            numeroField.setText(currentUser.getNumeroTelephoneUtilisateur());
        }
    }

    @FXML
    private void handleUpdate() {
        if (currentUser == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "No user is logged in.");
            return;
        }
        // Get updated values.
        String newNom = nomField.getText();
        String newPrenom = prenomField.getText();
        String newEmail = emailField.getText();
        String newNumeroDeTelephone = numeroField.getText();
        String newPassword = motdepasseField.getText();

        // Basic validation.
        if (newNom.isEmpty() || newNumeroDeTelephone.isEmpty() || newPrenom.isEmpty() || newEmail.isEmpty() || newPassword.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "All fields are required.");
            return;
        }

        // Update the user object.
        currentUser.setNomUtilisateur(newNom);
        currentUser.setPrenomUtilisateur(newPrenom);
        currentUser.setEmailUtilisateur(newEmail);
        currentUser.setMotDePasseUtilisateur(newPassword);
        currentUser.setNumeroTelephoneUtilisateur(newNumeroDeTelephone);

        // Call the service to update the user.
        boolean success = utilisateurService.updateUtilisateur(currentUser);
        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "Profile updated successfully.");
            
            Session.setCurrentUser(currentUser);

            Stage stage = (Stage) updateButton.getScene().getWindow();
            stage.close();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to update profile.");
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
