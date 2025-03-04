package com.esprit.gu.service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.esprit.gu.DataBaseConnection;
import com.esprit.gu.entity.Reservation;
import com.esprit.gu.util.Session;

public class ServiceReservation {

    private ServiceAuthentification authService;

    // Constructeur avec service d'authentification
    public ServiceReservation(ServiceAuthentification authService) {
        this.authService = authService; // Injecte le service auth
    }

    // Méthode pour ajouter une réservation
    public void ajouterReservation(Reservation res) throws SQLException {
        String sql = "INSERT INTO reservation (nom_utilisateur, id_trajet, date_debut, date_fin, montant, statut) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // On utilise le nom de l'utilisateur authentifié depuis le service
            String nomUtilisateur = String.valueOf(Session.getCurrentUser());
            ps.setString(1, nomUtilisateur); // Récupération du nom utilisateur
            ps.setInt(2, res.getIdTrajet());
            ps.setString(3, res.getDateDebut());
            ps.setString(4, res.getDateFin());
            ps.setDouble(5, res.getMontant());
            ps.setString(6, res.getStatut());
            ps.executeUpdate();
        }
    }

    // Méthode pour afficher toutes les réservations
    public List<Reservation> afficherReservations() throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservation";
        try (Connection conn = DataBaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Lecture des données depuis la base et construction de la liste d'objets Reservation
            while (rs.next()) {
                reservations.add(new Reservation(
                        rs.getInt("id"),                    // id de la réservation
                        rs.getString("nom_utilisateur"),    // nom_utilisateur (String)
                        rs.getInt("id_trajet"),             // id_trajet
                        rs.getString("date_debut"),         // date_debut
                        rs.getString("date_fin"),           // date_fin
                        rs.getDouble("montant"),            // montant
                        rs.getString("statut")              // statut
                ));
            }
        }
        return reservations;
    }

    // Méthode pour modifier une réservation

    public void modifierReservation(Reservation res) throws SQLException {
        String sql = "UPDATE reservation SET  id_trajet=?, date_debut=?, date_fin=?, montant=?, statut=? WHERE id=?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(2, res.getIdTrajet());
            ps.setString(3, res.getDateDebut());
            ps.setString(4, res.getDateFin());
            ps.setDouble(5, res.getMontant());
            ps.setString(6, res.getStatut());
            ps.setInt(7, res.getId());
            ps.executeUpdate();
        }
    }


    public void supprimerReservation(int id) throws SQLException {
        String sql = "DELETE FROM reservation WHERE id=?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

}