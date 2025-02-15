package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceReservation {


    public void ajouterReservation(Reservation res) throws SQLException {
        String sql = "INSERT INTO reservation (id_user, id_trajet, date_debut, date_fin, montant, statut) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, res.getIdUser());
            ps.setInt(2, res.getIdTrajet());
            ps.setString(3, res.getDateDebut());
            ps.setString(4, res.getDateFin());
            ps.setDouble(5, res.getMontant());
            ps.setString(6, res.getStatut());
            ps.executeUpdate();
        }
    }


    public List<Reservation> afficherReservations() throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservation";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                reservations.add(new Reservation(
                        rs.getInt("id"),
                        rs.getInt("id_user"),
                        rs.getInt("id_trajet"),
                        rs.getString("date_debut"),
                        rs.getString("date_fin"),
                        rs.getDouble("montant"),
                        rs.getString("statut")
                ));
            }
        }
        return reservations;
    }


    public void modifierReservation(Reservation res) throws SQLException {
        String sql = "UPDATE reservation SET id_user=?, id_trajet=?, date_debut=?, date_fin=?, montant=?, statut=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, res.getIdUser());
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
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }


}
