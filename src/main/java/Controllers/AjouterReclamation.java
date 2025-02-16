package Controllers;/*package Entitiesjavafx;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AjouterReclamation {

    @FXML
    private TextField nomField;

    @FXML
    private TextField prenomField;

    @FXML
    private TextArea descriptionField;

    @FXML
    private RadioButton urgentRadio;

    @FXML
    private RadioButton normalRadio;

    @FXML
    private RadioButton normalRadio1;

    @FXML
    private ToggleGroup toggleGroup;

    @FXML
    private Button submitButton;

    @FXML
    private void initialize() {
        // Action du bouton "Envoyer"
        submitButton.setOnAction(event -> envoyerReclamation());
    }

    private void envoyerReclamation() {
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String description = descriptionField.getText();
        String type;

        if (urgentRadio.isSelected()) {
            type = "Service";
        } else if (normalRadio.isSelected()) {
            type = "Disponibilité";
        } else if (normalRadio1.isSelected()) {
            type = "Payment";
        } else {
            showAlert("Erreur", "Veuillez sélectionner un type de réclamation !");
            return;
        }

        if (nom.isEmpty() || prenom.isEmpty() || description.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs !");
            return;
        }

        // 🔹 Ici, ajouter la logique d'enregistrement des données dans la base de données
        System.out.println("Nom: " + nom);
        System.out.println("Prénom: " + prenom);
        System.out.println("Description: " + description);
        System.out.println("Type: " + type);

        showAlert("Succès", "Réclamation envoyée avec succès !");

        // Fermer la fenêtre après soumission
        Stage stage = (Stage) submitButton.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
*/
/*
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import tools.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AjouterReclamation {

    @FXML
    private TextField nomField;

    @FXML
    private TextField prenomField;

    @FXML
    private TextArea descriptionField;

    @FXML
    private RadioButton urgentRadio, normalRadio, normalRadio1;

    @FXML
    private ToggleGroup toggleGroup;

    @FXML
    private Button submitButton;

    @FXML
    private void initialize() {
        // Optionally load user data with ID=1 by default
        remplirFormulaireUtilisateur(1);
        submitButton.setOnAction(event -> envoyerReclamation());
    }

    private void remplirFormulaireUtilisateur(int id) {
        try (Connection conn = DataSource.getInstance().getConnection()) {
            String query = "SELECT nomUtilisateur, prenomUtilisateur FROM Utilisateur WHERE idUtilisateur = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, id);

            var rs = pstmt.executeQuery();
            if (rs.next()) {
                nomField.setText(rs.getString("nomUtilisateur"));
                prenomField.setText(rs.getString("prenomUtilisateur"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible de charger les informations de l'utilisateur.");
        }
    }

    private void envoyerReclamation() {
        String nom_utilisateur = nomField.getText();
        String prenom_utilisateur = prenomField.getText();
        String description = descriptionField.getText();
        String type = getTypeFromRadioButtons();

        // Check if all fields are filled
        if (nom_utilisateur.isEmpty() || prenom_utilisateur.isEmpty() || description.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs !");
            return;
        }

        if (type == null) {
            showAlert("Erreur", "Veuillez sélectionner un type de réclamation !");
            return;
        }

        // Insert data into the database
        try (Connection conn = DataSource.getInstance().getConnection()) {
            String insertQuery = "INSERT INTO Reclamation (nom_utilisateur, prenom_utilisateur, description, type, statutReclamation) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                pstmt.setString(1, nom_utilisateur);
                pstmt.setString(2, prenom_utilisateur);
                pstmt.setString(3, description);
                pstmt.setString(4, type);
                pstmt.setString(5, "En attente");

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    showAlert("Succès", "Réclamation envoyée avec succès !");
                } else {
                    showAlert("Erreur", "Une erreur est survenue lors de l'envoi de la réclamation.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible d'enregistrer la réclamation.");
        }
    }

    private String getTypeFromRadioButtons() {
        if (urgentRadio.isSelected()) {
            return "Service";
        } else if (normalRadio.isSelected()) {
            return "Disponibilité";
        } else if (normalRadio1.isSelected()) {
            return "Payment";
        }
        return null; // Return null if no type is selected
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
*/
import entities.Reclamation;
import entities.Utilisateur;
import Services.ServiceReclamation;
import Services.ServiceUtilisateur;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;

import java.util.Date;

public class AjouterReclamation {

    @FXML
    private TextField nomField, prenomField;

    @FXML
    private ComboBox<String> cbTypeReclamation;

    @FXML
    private TextArea descriptionField;

    @FXML
    private Button submitButton, btnAnnuler;

    private ServiceReclamation serviceReclamation;
    private ServiceUtilisateur serviceUtilisateur;
    private int idUtilisateur = 1; // ID utilisateur par défaut

    public AjouterReclamation() {
        serviceReclamation = new ServiceReclamation();
        serviceUtilisateur = new ServiceUtilisateur();
    }

    @FXML
    public void initialize() {
        // Charger les informations de l'utilisateur avec l'ID = 1
        Utilisateur utilisateur = serviceUtilisateur.getOne(idUtilisateur);
        if (utilisateur != null) {
            nomField.setText(utilisateur.getNomUtilisateur());
            prenomField.setText(utilisateur.getPrenomUtilisateur());
        }

        // Ajouter les types de réclamation possibles
        cbTypeReclamation.getItems().addAll("Service", "Disponibilte", "Payement");
    }

    @FXML
    private void ajouterReclamation(ActionEvent event) {
        // Vérification des champs requis
        if (cbTypeReclamation.getValue() == null || descriptionField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs !");
            return;
        }

        // Créer l'objet réclamation
        Reclamation reclamation = new Reclamation();
        reclamation.setNom_utilisateur(nomField.getText());
        reclamation.setPrenom_utilisateur(prenomField.getText());
        reclamation.setTypeReclamation(cbTypeReclamation.getValue());
        reclamation.setDescriptionReclamation(descriptionField.getText());
        reclamation.setStatutReclamation("En attente");


        // Définir la date actuelle de la réclamation
        reclamation.setDateReclamation(new Date());

        // Ajouter la réclamation via le service
        serviceReclamation.ajouter(reclamation);
        showAlert(Alert.AlertType.INFORMATION, "Succès", "Réclamation ajoutée avec succès !");
    }

    @FXML
    private void annuler(ActionEvent event) {
        // Effacer les champs
        nomField.clear();
        prenomField.clear();
        cbTypeReclamation.getSelectionModel().clearSelection();
        descriptionField.clear();
    }

    // Affichage des alertes
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
