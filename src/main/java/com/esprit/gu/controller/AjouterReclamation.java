package com.esprit.gu.controller;

import com.esprit.gu.entity.Reclamation;
import com.esprit.gu.entity.Utilisateur;
import com.esprit.gu.service.ServiceReclamation;
import com.esprit.gu.service.UtilisateurService;

import java.io.IOException;
import java.util.Date;

import com.esprit.gu.util.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AjouterReclamation {
    @FXML
    private TextField nomField;
    @FXML
    private TextField prenomField;
    @FXML
    private ComboBox<String> cbTypeReclamation;
    @FXML
    private TextArea descriptionField;
    @FXML
    private Button submitButton;

    @FXML
    private Button retour;

    @FXML
    private Button btnAnnuler;
    private ServiceReclamation serviceReclamation = new ServiceReclamation();
    ;

    public AjouterReclamation() throws Exception {
    }

    @FXML
    public void initialize() {
        Utilisateur currentUser = Session.getCurrentUser();
        if (currentUser != null) {
            // Affichez le nom de l'utilisateur dans le champ
            nomField.setText(currentUser.getNomUtilisateur());
            prenomField.setText(currentUser.getPrenomUtilisateur());
           // prenomField.setEditable(false);
        } else {
            // Gérer le cas où aucun utilisateur n'est connecté
            this.showAlert(AlertType.ERROR, "Erreur", "\"Aucun utilisateur n'est connecté !");

        }
        this.cbTypeReclamation.getItems().addAll(new String[]{"Service", "Disponibilte", "Payement"});
    }

    @FXML
    private void ajouterReclamation(ActionEvent event) {
        if (this.cbTypeReclamation.getValue() != null && !this.descriptionField.getText().isEmpty()) {
            Reclamation reclamation = new Reclamation();
            reclamation.setNom_utilisateur(this.nomField.getText());
            reclamation.setPrenom_utilisateur(this.prenomField.getText());
            reclamation.setTypeReclamation((String)this.cbTypeReclamation.getValue());
            reclamation.setDescriptionReclamation(this.descriptionField.getText());
            reclamation.setStatutReclamation("En attente");
            reclamation.setDateReclamation(new Date());
            this.serviceReclamation.ajouter(reclamation);
            this.showAlert(AlertType.INFORMATION, "Succès", "Réclamation ajoutée avec succès !");
        } else {
            this.showAlert(AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs !");
        }
    }

    @FXML
    private void annuler(ActionEvent event) {
        this.nomField.clear();
        this.prenomField.clear();
        this.cbTypeReclamation.getSelectionModel().clearSelection();
        this.descriptionField.clear();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText((String)null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleRetour() {
        try {
            // Load the admin_dashboard_gu.fxml file
            Parent root = FXMLLoader.load(getClass().getResource("/views/profile.fxml"));
            Stage stage = (Stage) retour.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
