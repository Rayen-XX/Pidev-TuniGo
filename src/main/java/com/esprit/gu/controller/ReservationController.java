package com.esprit.gu.controller;

import com.esprit.gu.DataBaseConnection;
import com.esprit.gu.entity.Utilisateur;
import com.esprit.gu.util.Session;
import com.esprit.gu.service.ServiceAuthentification;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class ReservationController {

    @FXML
    private TextField searchField;

    // Sections de l'interface
    @FXML
    private VBox addSection; // Section d'ajout
    @FXML
    private VBox listSection; // Section d'affichage, modification et suppression

    // Champs pour l'ajout
    @FXML
    private TextField nomUtilisateurField, idTrajetField, dateDebutField, dateFinField, montantField, statutField;

    // Champs pour la modification
    @FXML
    private TextField editIdField, editNomUtilisateurField, editDateDebutField, editDateFinField, editMontantField, editStatutField;

    // ListView pour afficher les réservations
    @FXML
    private ListView<String> reservationListView;

    // Liste observable pour les données dans ListView
    private ObservableList<String> reservations = FXCollections.observableArrayList();

    // Initialisation
    public void initialize() {
        loadReservationsFromDatabase(); // Charger les réservations dès l'initialisation
        Utilisateur currentUser = Session.getCurrentUser();
        if (currentUser != null) {
            // Affichez le nom de l'utilisateur dans le champ
            nomUtilisateurField.setText(currentUser.getNomUtilisateur());
            nomUtilisateurField.setEditable(false);
        } else {
            // Gérer le cas où aucun utilisateur n'est connecté
            showErrorAlert("Erreur", "Aucun utilisateur n'est connecté !");
        }
    }

    // *************** Navigation **********************

    // Afficher la section d'ajout
    @FXML
    private void showAddSection() {
        setSectionVisibility(true, false);
    }

    // Afficher la section de liste/modification/suppression
    @FXML
    private void showListSection() {
        setSectionVisibility(false, true);
        loadReservationsFromDatabase(); // Recharger les données
    }

    // Gestion de la visibilité des sections
    private void setSectionVisibility(boolean addVisible, boolean listVisible) {
        addSection.setVisible(addVisible);
        listSection.setVisible(listVisible);
    }

    // **************** Gestion des Réservations ***********************
    // Charger les réservations depuis la base de données
    private void loadReservationsFromDatabase() {
        reservations.clear(); // Effacer la liste avant le rechargement
        try (Connection conn = DataBaseConnection.getConnection()) {
            String query = "SELECT * FROM reservation";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String id = String.valueOf(rs.getInt("id"));
                String info = "ID: " + id + ", Utilisateur: " + rs.getString("nom_utilisateur") +
                        ", Trajet: " + rs.getInt("idTrajet") +
                        ", Début: " + rs.getString("dateDebut") +
                        ", Fin: " + rs.getString("dateFin") +
                        ", Montant: " + rs.getDouble("montant") +
                        ", Statut: " + rs.getString("statut");
                reservations.add(info);
            }
            reservationListView.setItems(reservations);
        } catch (SQLException e) {
            e.printStackTrace();
            showErrorAlert("Erreur", "Impossible de charger les réservations !");
        }
    }

    // Ajouter une réservation
    @FXML
    private void ajouterReservation() {
        if (nomUtilisateurField.getText().isEmpty() || idTrajetField.getText().isEmpty() ||
                dateDebutField.getText().isEmpty() || dateFinField.getText().isEmpty() ||
                montantField.getText().isEmpty() || statutField.getText().isEmpty()) {
            showErrorAlert("Champs obligatoires", "Veuillez remplir tous les champs !");
            return;
        }

        try (Connection conn = DataBaseConnection.getConnection()) {
            String query = "INSERT INTO reservation (nom_utilisateur, idTrajet, dateDebut, dateFin, montant, statut) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, nomUtilisateurField.getText());
            pstmt.setInt(2, Integer.parseInt(idTrajetField.getText()));
            pstmt.setString(3, dateDebutField.getText());
            pstmt.setString(4, dateFinField.getText());
            pstmt.setDouble(5, Double.parseDouble(montantField.getText()));
            pstmt.setString(6, statutField.getText());

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                showInfoAlert("Succès", "Réservation ajoutée avec succès !");
                clearFields(); // Nettoyer les champs après ajout
                loadReservationsFromDatabase(); // Recharger les données
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showErrorAlert("Erreur", "Impossible d'ajouter la réservation.");
        }
    }

    // Supprimer une réservation sélectionnée
    @FXML
    private void supprimerReservation() {
        String selected = reservationListView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showErrorAlert("Erreur", "Veuillez sélectionner une réservation à supprimer.");
            return;
        }

        try {
            String idReservation = selected.split(",")[0].split(":")[1].trim();
            try (Connection conn = DataBaseConnection.getConnection()) {
                String query = "DELETE FROM reservation WHERE id = ?";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, Integer.parseInt(idReservation));
                int rowsDeleted = pstmt.executeUpdate();
                if (rowsDeleted > 0) {
                    showInfoAlert("Succès", "Réservation supprimée avec succès !");
                    loadReservationsFromDatabase(); // Recharger les données
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showErrorAlert("Erreur", "Impossible de supprimer la réservation.");
        }
    }

    // Sélectionner une réservation pour modification
    @FXML
    private void handleReservationSelection() {
        String selected = reservationListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            String idReservation = selected.split(",")[0].split(":")[1].trim();
            try (Connection conn = DataBaseConnection.getConnection()) {
                String query = "SELECT * FROM reservation WHERE id = ?";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, Integer.parseInt(idReservation));
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    editIdField.setText(String.valueOf(rs.getInt("id")));
                    editNomUtilisateurField.setText(rs.getString("nom_utilisateur"));
                    editDateDebutField.setText(rs.getString("dateDebut"));
                    editDateFinField.setText(rs.getString("dateFin"));
                    editMontantField.setText(String.valueOf(rs.getDouble("montant")));
                    editStatutField.setText(rs.getString("statut"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showErrorAlert("Erreur", "Impossible de récupérer les détails de la réservation.");
            }
        }
    }

    // Modifier une réservation
    @FXML
    private void modifierReservation() {
        if (editIdField.getText().isEmpty() || editNomUtilisateurField.getText().isEmpty() ||
                editDateDebutField.getText().isEmpty() || editDateFinField.getText().isEmpty() ||
                editMontantField.getText().isEmpty() || editStatutField.getText().isEmpty()) {
            showErrorAlert("Champs obligatoires", "Veuillez remplir tous les champs !");
            return;
        }

        try (Connection conn = DataBaseConnection.getConnection()) {
            String query = "UPDATE reservation SET nom_utilisateur = ?, dateDebut = ?, dateFin = ?, montant = ?, statut = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, editNomUtilisateurField.getText());
            pstmt.setString(2, editDateDebutField.getText());
            pstmt.setString(3, editDateFinField.getText());
            pstmt.setDouble(4, Double.parseDouble(editMontantField.getText()));
            pstmt.setString(5, editStatutField.getText());
            pstmt.setInt(6, Integer.parseInt(editIdField.getText()));

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                showInfoAlert("Succès", "Réservation modifiée avec succès !");
                loadReservationsFromDatabase(); // Recharger les données
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showErrorAlert("Erreur", "Impossible de modifier la réservation.");
        }
    }

    // Méthode pour filtrer les réservations
    @FXML
    public void filterReservations() {
        String searchTerm = searchField.getText().toLowerCase();
        ObservableList<String> filteredReservations = FXCollections.observableArrayList();

        for (String reservation : reservations) {
            if (reservation.toLowerCase().contains(searchTerm)) {
                filteredReservations.add(reservation);
            }
        }
        reservationListView.setItems(filteredReservations);
    }

    // Nettoyer les champs après une action
    private void clearFields() {
        nomUtilisateurField.clear();
        idTrajetField.clear();
        dateDebutField.clear();
        dateFinField.clear();
        montantField.clear();
        statutField.clear();

        editIdField.clear();
        editNomUtilisateurField.clear();
        editDateDebutField.clear();
        editDateFinField.clear();
        editMontantField.clear();
        editStatutField.clear();
    }

    // Afficher une alerte d'erreur
    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Afficher une alerte d'information
    private void showInfoAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Gestion de navigation vers la gestion des réservations (si nécessaire)
    public void handleGestionReservation(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esprit/gu/view/Reservation.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}