package com.esprit.gu.controller;

import com.esprit.gu.entity.Utilisateur;
import com.esprit.gu.service.UtilisateurService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class UpdateUserController {

    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private TextField emailField;
    @FXML private PasswordField motdepasseField;
    @FXML private TextField telephoneField;
    @FXML private ComboBox<String> roleComboBox;

    private UtilisateurService utilisateurService = new UtilisateurService();
    private Utilisateur user; // The user to update

    // This method is called by the AdminDashboardController to pass in the selected user.
    public void setUser(Utilisateur user) {
        this.user = user;
        // Populate fields with the user's current data.
        nomField.setText(user.getNomUtilisateur());
        prenomField.setText(user.getPrenomUtilisateur());
        emailField.setText(user.getEmailUtilisateur());
        motdepasseField.setText(user.getMotDePasseUtilisateur());
        telephoneField.setText(user.getNumeroTelephoneUtilisateur());
        roleComboBox.setValue(user.getRoleUtilisateur());
    }

    @FXML
    private void handleUpdate() {
        // Basic validation: ensure none of the fields are empty.
        if(nomField.getText().isEmpty() || prenomField.getText().isEmpty() ||
                emailField.getText().isEmpty() || motdepasseField.getText().isEmpty() ||
                telephoneField.getText().isEmpty() || roleComboBox.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Please fill all fields.");
            return;
        }

        // Update the user object.
        user.setNomUtilisateur(nomField.getText());
        user.setPrenomUtilisateur(prenomField.getText());
        user.setEmailUtilisateur(emailField.getText());
        user.setMotDePasseUtilisateur(motdepasseField.getText());
        user.setNumeroTelephoneUtilisateur(telephoneField.getText());
        user.setRoleUtilisateur(roleComboBox.getValue());

        // Call the service to update the user.
        boolean success = utilisateurService.updateUtilisateur(user);
        if(success) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "User updated successfully.");
            // Close the update window.
            Stage stage = (Stage) nomField.getScene().getWindow();
            stage.close();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to update user.");
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
