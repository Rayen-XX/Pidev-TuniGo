package com.esprit.gu.controller.gestion_utilisateur_controllers;

import com.esprit.gu.entity.gestion_utilisateur_entity.Utilisateur;
import com.esprit.gu.service.gestion_utilisateur_service.UtilisateurService;
import com.esprit.gu.util.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;

public class UpdateProfileController implements Initializable {

    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private TextField emailField;
    @FXML private TextField numeroField;
    @FXML private PasswordField motdepasseField;
    @FXML private TextField motdepasseVisibleField;
    @FXML private ToggleButton togglePasswordButton;
    @FXML private ComboBox<String> securityQuestionComboBox;
    @FXML private TextField securityAnswerField;
    @FXML private Label globalErrorLabel;
    @FXML private Button updateButton;
    @FXML private Button deleteAccountButton;

    private UtilisateurService utilisateurService = new UtilisateurService();
    private Utilisateur currentUser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Get the current user from session and populate the form fields
        currentUser = Session.getCurrentUser();
        if (currentUser != null) {
            nomField.setText(currentUser.getNomUtilisateur());
            prenomField.setText(currentUser.getPrenomUtilisateur());
            emailField.setText(currentUser.getEmailUtilisateur());
            motdepasseField.setText(currentUser.getMotDePasseUtilisateur());
            motdepasseVisibleField.setText(currentUser.getMotDePasseUtilisateur());
            numeroField.setText(currentUser.getNumeroTelephoneUtilisateur());
            
            // Setup security questions - only the three from register.fxml
            securityQuestionComboBox.getItems().addAll(Arrays.asList(
                "Quel est le nom de jeune fille de votre mère ?",
                "Quel est le nom de votre premier animal de compagnie ?",
                "Quel est le nom de votre école primaire ?"
            ));
            
            // Set current security question if available
            if (currentUser.getQuestionSecurite() != null && !currentUser.getQuestionSecurite().isEmpty()) {
                securityQuestionComboBox.setValue(currentUser.getQuestionSecurite());
                securityAnswerField.setText(currentUser.getReponseSecurite());
            } else {
                // Default to first question if none set
                securityQuestionComboBox.getSelectionModel().selectFirst();
            }
        }
    }

    @FXML
    private void togglePasswordVisibility() {
        if (motdepasseVisibleField.isVisible()) {
            motdepasseField.setText(motdepasseVisibleField.getText());
            motdepasseVisibleField.setVisible(false);
            motdepasseVisibleField.setManaged(false);
            motdepasseField.setVisible(true);
            motdepasseField.setManaged(true);
            togglePasswordButton.setText("Afficher");
        } else {
            motdepasseVisibleField.setText(motdepasseField.getText());
            motdepasseVisibleField.setVisible(true);
            motdepasseVisibleField.setManaged(true);
            motdepasseField.setVisible(false);
            motdepasseField.setManaged(false);
            togglePasswordButton.setText("Masquer");
        }
    }

    @FXML
    private void handleUpdate() {
        if (currentUser == null) {
            showGlobalError("Aucun utilisateur n'est connecté.");
            return;
        }
        
        // Validate inputs
        if (!validateInputs()) {
            return;
        }
        
        // Get updated values
        String newNom = nomField.getText();
        String newPrenom = prenomField.getText();
        String newEmail = emailField.getText();
        String newNumeroDeTelephone = numeroField.getText();
        String newPassword = getPassword();
        String securityQuestion = securityQuestionComboBox.getValue();
        String securityAnswer = securityAnswerField.getText();

        // Update the user object
        currentUser.setNomUtilisateur(newNom);
        currentUser.setPrenomUtilisateur(newPrenom);
        currentUser.setEmailUtilisateur(newEmail);
        currentUser.setMotDePasseUtilisateur(newPassword);
        currentUser.setNumeroTelephoneUtilisateur(newNumeroDeTelephone);
        currentUser.setQuestionSecurite(securityQuestion);
        currentUser.setReponseSecurite(securityAnswer);
        // Keep the original role - don't allow changing it
        
        // Call the service to update the user
        boolean success = utilisateurService.updateUtilisateur(currentUser);
        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Profil mis à jour avec succès.");
            
            // Update the session
            Session.setCurrentUser(currentUser);

            // Close the window
            Stage stage = (Stage) updateButton.getScene().getWindow();
            stage.close();
        } else {
            showGlobalError("Échec de la mise à jour du profil. Veuillez réessayer.");
        }
    }
    
    @FXML
    private void handleDeleteAccount() {
        // Show confirmation dialog in French
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation de suppression");
        confirmationAlert.setHeaderText("Êtes-vous sûr de vouloir supprimer votre compte ?");
        confirmationAlert.setContentText("Cette action est irréversible. Toutes vos données seront définitivement supprimées.");
        
        // Customize buttons
        ButtonType buttonTypeYes = new ButtonType("Oui, supprimer mon compte");
        ButtonType buttonTypeNo = new ButtonType("Non, annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
        confirmationAlert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == buttonTypeYes) {
            // User confirmed, proceed with account deletion
            if (currentUser != null) {
                boolean success = utilisateurService.deleteUtilisateur(currentUser.getIdUtilisateur());
                if (success) {
                    // Show success message
                    showAlert(Alert.AlertType.INFORMATION, "Compte supprimé", 
                             "Votre compte a été supprimé avec succès. L'application va maintenant se fermer.");
                    
                    // Clear session
                    Session.clear();
                    
                    try {
                        // Close the update profile window
                        Stage currentStage = (Stage) deleteAccountButton.getScene().getWindow();
                        currentStage.close();
                        
                        // Load and show the login screen
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/gestion_utilisateur_views/login.fxml"));
                        Parent root = loader.load();
                        Stage loginStage = new Stage();
                        loginStage.setScene(new Scene(root));
                        loginStage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    showGlobalError("Échec lors de la suppression du compte. Veuillez réessayer.");
                }
            }
        }
    }
    
    @FXML
    private void handleCancel() {
        Stage stage = (Stage) updateButton.getScene().getWindow();
        stage.close();
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
        if (numeroField.getText() == null || numeroField.getText().trim().isEmpty() || !isValidPhone(numeroField.getText())) {
            errorMessages.append("Veuillez entrer un numéro de téléphone valide. ");
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

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
