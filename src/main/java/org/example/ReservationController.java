package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.*;

public class ReservationController {

    @FXML private TextField tfIdUser;
    @FXML private TextField tfIdTrajet;
    @FXML private TextField tfDateDebut;
    @FXML private TextField tfDateFin;
    @FXML private TextField tfMontant;
    @FXML private TextField tfStatut;
    @FXML private TableView<Reservation> tableReservations;
    @FXML private TableColumn<Reservation, Integer> colId;
    @FXML private TableColumn<Reservation, Integer> colIdUser;
    @FXML private TableColumn<Reservation, Integer> colIdTrajet;
    @FXML private TableColumn<Reservation, String> colDateDebut;
    @FXML private TableColumn<Reservation, String> colDateFin;
    @FXML private TableColumn<Reservation, Double> colMontant;
    @FXML private TableColumn<Reservation, String> colStatut;

    private Connection connection;

    public void initialize() {


        // Associer les colonnes avec les attributs de Reservation
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colIdUser.setCellValueFactory(new PropertyValueFactory<>("idUser"));
        colIdTrajet.setCellValueFactory(new PropertyValueFactory<>("idTrajet"));
        colDateDebut.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
        colDateFin.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
        colMontant.setCellValueFactory(new PropertyValueFactory<>("montant"));
        colStatut.setCellValueFactory(new PropertyValueFactory<>("statut"));

        chargerReservations();
    }

    @FXML
    private void ajouterReservation() {
        String query = "INSERT INTO reservation (id_user, id_trajet, date_debut, date_fin, montant, statut) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, Integer.parseInt(tfIdUser.getText()));
            stmt.setInt(2, Integer.parseInt(tfIdTrajet.getText()));
            stmt.setString(3, tfDateDebut.getText());
            stmt.setString(4, tfDateFin.getText());
            stmt.setDouble(5, Double.parseDouble(tfMontant.getText()));
            stmt.setString(6, tfStatut.getText());
            stmt.executeUpdate();
            chargerReservations();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void modifierReservation() {
        Reservation selected = tableReservations.getSelectionModel().getSelectedItem();
        if (selected != null) {
            String query = "UPDATE reservation SET id_user=?, id_trajet=?, date_debut=?, date_fin=?, montant=?, statut=? WHERE id=?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, Integer.parseInt(tfIdUser.getText()));
                stmt.setInt(2, Integer.parseInt(tfIdTrajet.getText()));
                stmt.setString(3, tfDateDebut.getText());
                stmt.setString(4, tfDateFin.getText());
                stmt.setDouble(5, Double.parseDouble(tfMontant.getText()));
                stmt.setString(6, tfStatut.getText());
                stmt.setInt(7, selected.getId());
                stmt.executeUpdate();
                chargerReservations();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void supprimerReservation() {
        Reservation selected = tableReservations.getSelectionModel().getSelectedItem();
        if (selected != null) {
            String query = "DELETE FROM reservation WHERE id=?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, selected.getId());
                stmt.executeUpdate();
                chargerReservations();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void chargerReservations() {
        ObservableList<Reservation> list = FXCollections.observableArrayList();
        String query = "SELECT * FROM reservation";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                list.add(new Reservation(
                        rs.getInt("id"),
                        rs.getInt("id_user"),
                        rs.getInt("id_trajet"),
                        rs.getString("date_debut"),
                        rs.getString("date_fin"),
                        rs.getDouble("montant"),
                        rs.getString("statut")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tableReservations.setItems(list);
    }
}
