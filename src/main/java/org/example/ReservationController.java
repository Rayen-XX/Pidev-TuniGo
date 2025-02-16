package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import org.example.DatabaseConnection;

import java.sql.*;

public class ReservationController {

    // Sections de l'interface
    @FXML
    private VBox addSection; // Section d'ajout
    @FXML
    private VBox listSection; // Section d'affichage, modification et suppression

    // Champs pour l'ajout
    @FXML
    private TextField idUserField, idTrajetField, dateDebutField, dateFinField, montantField, statutField;

    // Champs pour la modification
    @FXML
    private TextField editIdField, editDateDebutField, editDateFinField, editMontantField, editStatutField;

    // ListView pour afficher les réservations
    @FXML
    private ListView<String> reservationListView;

    // Liste observable pour les données dans ListView
    private ObservableList<String> reservations = FXCollections.observableArrayList();

    // Initialisation
    @FXML
    private void initialize() {
        loadReservationsFromDatabase(); // Charger les réservations au lancement
    }

    // ***************** Navigation ************************

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

    // ****************** Gestion des Réservations *************************

    // Charger les réservations depuis la base de données
    private void loadReservationsFromDatabase() {
        reservations.clear(); // Effacer la liste avant le rechargement
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM reservation";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String id = String.valueOf(rs.getInt("id"));
                String info = "ID: " + id + ", Utilisateur: " + rs.getInt("idUser") +
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
        if (idUserField.getText().isEmpty() || idTrajetField.getText().isEmpty() ||
                dateDebutField.getText().isEmpty() || dateFinField.getText().isEmpty() ||
                montantField.getText().isEmpty() || statutField.getText().isEmpty()) {
            showErrorAlert("Champs obligatoires", "Veuillez remplir tous les champs !");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO reservation (idUser, idTrajet, dateDebut, dateFin, montant, statut) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);

            pstmt.setInt(1, Integer.parseInt(idUserField.getText()));
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

            try (Connection conn = DatabaseConnection.getConnection()) {
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
            try (Connection conn = DatabaseConnection.getConnection()) {
                String query = "SELECT * FROM reservation WHERE id = ?";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, Integer.parseInt(idReservation));
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    editIdField.setText(String.valueOf(rs.getInt("id")));
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
        if (editIdField.getText().isEmpty() || editDateDebutField.getText().isEmpty() ||
                editDateFinField.getText().isEmpty() || editMontantField.getText().isEmpty() ||
                editStatutField.getText().isEmpty()) {
            showErrorAlert("Champs obligatoires", "Veuillez remplir tous les champs !");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "UPDATE reservation SET dateDebut = ?, dateFin = ?, montant = ?, statut = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);

            pstmt.setString(1, editDateDebutField.getText());
            pstmt.setString(2, editDateFinField.getText());
            pstmt.setDouble(3, Double.parseDouble(editMontantField.getText()));
            pstmt.setString(4, editStatutField.getText());
            pstmt.setInt(5, Integer.parseInt(editIdField.getText()));

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

    // ********** Utilitaires **********

    // Nettoyer les champs après une action
    private void clearFields() {
        idUserField.clear();
        idTrajetField.clear();
        dateDebutField.clear();
        dateFinField.clear();
        montantField.clear();
        statutField.clear();

        editIdField.clear();
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
}