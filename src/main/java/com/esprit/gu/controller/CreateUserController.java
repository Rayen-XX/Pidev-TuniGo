package com.esprit.gu.controller;

import com.esprit.gu.entity.Utilisateur;
import com.esprit.gu.service.UtilisateurService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class CreateUserController {

    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private TextField emailField;
    @FXML private PasswordField motdepasseField;
    @FXML private TextField telephoneField;
    @FXML private ComboBox<String> roleComboBox;

    private UtilisateurService utilisateurService = new UtilisateurService();

    @FXML
    private void handleCreate() {
        // Basic validation: check that all fields are filled.
        if(nomField.getText().isEmpty() || prenomField.getText().isEmpty() ||
                emailField.getText().isEmpty() || motdepasseField.getText().isEmpty() ||
                telephoneField.getText().isEmpty() || roleComboBox.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Please fill all fields.");
            return;
        }

        // Create a new Utilisateur object.
        Utilisateur newUser = new Utilisateur(
                nomField.getText(),
                prenomField.getText(),
                emailField.getText(),
                motdepasseField.getText(),
                telephoneField.getText(),
                roleComboBox.getValue()
        );

        // Call the service to register the new user.
        boolean success = utilisateurService.register(newUser);
        if(success) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "User created successfully.");
            // Close the create window.
            Stage stage = (Stage) nomField.getScene().getWindow();
            stage.close();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to create user. The email may already exist.");
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
