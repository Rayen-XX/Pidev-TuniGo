package com.esprit.gu.controller.gestion_utilisateur_controllers;

import com.esprit.gu.entity.gestion_utilisateur_entity.Utilisateur;
import com.esprit.gu.util.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BusController implements Initializable {

    @FXML
    private Label welcomeLabel;

    @FXML
    private Hyperlink logoutLink;
    
    @FXML
    private ComboBox<String> departComboBox;
    
    @FXML
    private ComboBox<String> arriveeComboBox;
    
    @FXML
    private DatePicker dateDepartPicker;
    
    @FXML
    private TextField passagersTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Get the current user from session and display his/her name
        Utilisateur currentUser = Session.getCurrentUser();
        if (currentUser != null) {
            welcomeLabel.setText("Bonjour, " + currentUser.getNomUtilisateur());
        } else {
            welcomeLabel.setText("Utilisateur non connecté");
        }

        // Set up logout action
        logoutLink.setOnAction(e -> {
            Session.clear();  // Clear the current session
            try {
                // Load the login view and set it as the current scene
                Stage stage = (Stage) logoutLink.getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource("/views/gestion_utilisateur_views/login.fxml"));
                stage.setScene(new Scene(root));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        
        // Initialize combo boxes with city data
        initializeComboBoxes();
    }
    
    private void initializeComboBoxes() {
        // Sample city data for Tunisia
        ObservableList<String> cities = FXCollections.observableArrayList(
            "Tunis", "Sfax", "Sousse", "Kairouan", "Bizerte", 
            "Gabès", "Ariana", "Gafsa", "Monastir", "Tataouine",
            "Médenine", "Nabeul", "Hammamet", "Kef", "Mahdia",
            "Tozeur", "Kasserine", "Béja", "Jendouba", "Siliana"
        );
        
        departComboBox.setItems(cities);
        arriveeComboBox.setItems(cities);
    }

    @FXML
    private void verifierDisponibilite() {
        // Check if all fields are filled
        if (isFormValid()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Disponibilité");
            alert.setHeaderText(null);
            alert.setContentText("Des bus sont disponibles pour votre itinéraire !\nVous pouvez procéder à la réservation.");
            alert.showAndWait();
        }
    }
    
    @FXML
    private void reserverBus() {
        // Check if all fields are filled
        if (isFormValid()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de réservation");
            alert.setHeaderText("Confirmer votre réservation");
            alert.setContentText("Voulez-vous confirmer la réservation de bus de " + 
                    departComboBox.getValue() + " à " + arriveeComboBox.getValue() + 
                    " pour le " + dateDepartPicker.getValue() + " ?");
            
            alert.showAndWait().ifPresent(result -> {
                if (result == ButtonType.OK) {
                    // Reservation confirmed
                    Alert success = new Alert(Alert.AlertType.INFORMATION);
                    success.setTitle("Réservation réussie");
                    success.setHeaderText(null);
                    success.setContentText("Votre réservation a été enregistrée avec succès !");
                    success.showAndWait();
                    
                    // Redirect to profile or another appropriate page
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("/views/gestion_utilisateur_views/profile.fxml"));
                        Stage stage = (Stage) departComboBox.getScene().getWindow();
                        stage.setScene(new Scene(root));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
    
    private boolean isFormValid() {
        StringBuilder errorMessage = new StringBuilder();
        
        if (departComboBox.getValue() == null) {
            errorMessage.append("Veuillez sélectionner une ville de départ.\n");
        }
        
        if (arriveeComboBox.getValue() == null) {
            errorMessage.append("Veuillez sélectionner une ville d'arrivée.\n");
        }
        
        if (dateDepartPicker.getValue() == null) {
            errorMessage.append("Veuillez sélectionner une date de départ.\n");
        }
        
        if (passagersTextField.getText().isEmpty()) {
            errorMessage.append("Veuillez indiquer le nombre de passagers.\n");
        } else {
            try {
                int passagers = Integer.parseInt(passagersTextField.getText());
                if (passagers <= 0) {
                    errorMessage.append("Le nombre de passagers doit être positif.\n");
                }
            } catch (NumberFormatException e) {
                errorMessage.append("Le nombre de passagers doit être un nombre entier.\n");
            }
        }
        
        // Check if departure and arrival cities are the same
        if (departComboBox.getValue() != null && arriveeComboBox.getValue() != null && 
            departComboBox.getValue().equals(arriveeComboBox.getValue())) {
            errorMessage.append("Les villes de départ et d'arrivée ne peuvent pas être identiques.\n");
        }
        
        if (errorMessage.length() > 0) {
            // Show error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de formulaire");
            alert.setHeaderText("Veuillez corriger les erreurs suivantes:");
            alert.setContentText(errorMessage.toString());
            alert.showAndWait();
            return false;
        }
        
        return true;
    }

    @FXML
    private void retourTransportPublic() {
        try {
            // Return to transport public page
            Parent root = FXMLLoader.load(getClass().getResource("/views/gestion_utilisateur_views/transportPublic.fxml"));
            Stage stage = (Stage) welcomeLabel.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
} 