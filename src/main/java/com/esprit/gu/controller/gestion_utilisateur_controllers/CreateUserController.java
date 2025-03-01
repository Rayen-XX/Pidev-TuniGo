package com.esprit.gu.controller.gestion_utilisateur_controllers;

import com.esprit.gu.entity.gestion_utilisateur_entity.Utilisateur;
import com.esprit.gu.service.gestion_utilisateur_service.UtilisateurService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class CreateUserController {

    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private TextField emailField;
    @FXML private PasswordField motdepasseField;
    @FXML private TextField motdepasseVisibleField;
    @FXML private TextField telephoneField;
    @FXML private ComboBox<String> roleComboBox;
    @FXML private ComboBox<String> securityQuestionComboBox;
    @FXML private TextField securityAnswerField;
    @FXML private Label globalErrorLabel;

    private UtilisateurService utilisateurService = new UtilisateurService();

    @FXML
    private void handleCreate() {
        // Validate all inputs before proceeding
        if (!validateInputs()) {
            return;
        }

        // Create a new Utilisateur object with all fields
        Utilisateur newUser = new Utilisateur(
                nomField.getText(),
                prenomField.getText(),
                emailField.getText(),
                getPassword(),
                telephoneField.getText(),
                roleComboBox.getValue(),
                securityQuestionComboBox.getValue(),
                securityAnswerField.getText()
        );

        // Call the service to register the new user
        boolean success = utilisateurService.register(newUser);
        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Succès", "L'utilisateur a été créé avec succès.");
            // Close the create window
            Stage stage = (Stage) nomField.getScene().getWindow();
            stage.close();
        } else {
            showGlobalError("Échec de la création de l'utilisateur. L'email existe peut-être déjà.");
        }
    }

    /**
     * Validate all input fields
     * @return true if all inputs are valid, false otherwise
     */
    private boolean validateInputs() {
        StringBuilder errorMessages = new StringBuilder();
        boolean valid = true;

        // Validate nom
        if (nomField.getText() == null || nomField.getText().trim().isEmpty()) {
            errorMessages.append("Le nom est obligatoire. ");
            valid = false;
        }

        // Validate prénom
        if (prenomField.getText() == null || prenomField.getText().trim().isEmpty()) {
            errorMessages.append("Le prénom est obligatoire. ");
            valid = false;
        }

        // Validate email
        String email = emailField.getText();
        if (email == null || email.trim().isEmpty() || !isValidEmail(email)) {
            errorMessages.append("Veuillez entrer une adresse e-mail valide. ");
            valid = false;
        }

        // Validate password
        String password = getPassword();
        if (password == null || password.trim().isEmpty() || password.length() < 6) {
            errorMessages.append("Le mot de passe doit contenir au moins 6 caractères. ");
            valid = false;
        }

        // Validate telephone
        if (telephoneField.getText() == null || telephoneField.getText().trim().isEmpty() || !isValidPhone(telephoneField.getText())) {
            errorMessages.append("Veuillez entrer un numéro de téléphone valide. ");
            valid = false;
        }

        // Validate role
        if (roleComboBox.getValue() == null) {
            errorMessages.append("Veuillez sélectionner un rôle. ");
            valid = false;
        }

        // Validate security question
        if (securityQuestionComboBox.getValue() == null) {
            errorMessages.append("Veuillez sélectionner une question de sécurité. ");
            valid = false;
        }

        // Validate security answer
        if (securityAnswerField.getText() == null || securityAnswerField.getText().trim().isEmpty()) {
            errorMessages.append("Veuillez répondre à la question de sécurité. ");
            valid = false;
        }

        // Show error message if validation failed
        if (!valid) {
            showGlobalError(errorMessages.toString());
        } else {
            hideGlobalError();
        }

        return valid;
    }
    
    /**
     * Display an error message in the global error label
     */
    private void showGlobalError(String message) {
        globalErrorLabel.setText(message);
        globalErrorLabel.setVisible(true);
    }
    
    /**
     * Hide the global error label
     */
    private void hideGlobalError() {
        globalErrorLabel.setVisible(false);
    }

    /**
     * Get the password from either the visible or hidden field
     */
    private String getPassword() {
        return motdepasseField.isVisible() ? motdepasseField.getText() : motdepasseVisibleField.getText();
    }

    /**
     * Validate email format
     */
    private boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(regex);
    }

    /**
     * Validate phone number format
     */
    private boolean isValidPhone(String phone) {
        return phone.matches("\\d{8,15}");
    }

    /**
     * Toggle password visibility
     */
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

    /**
     * Handle cancel button action - close the window
     */
    @FXML
    private void handleCancel() {
        Stage stage = (Stage) nomField.getScene().getWindow();
        stage.close();
    }

    /**
     * Show an alert dialog
     */
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
