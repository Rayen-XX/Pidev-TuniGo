package services;

import entities.MetroTrajet;
import tools.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceMetro {
    private Connection connection;

    public ServiceMetro() {
        connection = DataSource.getConnection();
    }

    public void ajouter(MetroTrajet trajet) {
        String query = "INSERT INTO metro_trajet (gare_depart, gare_arrivee, heure_depart, heure_arrivee) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, trajet.getGareDepart());
            pst.setString(2, trajet.getGareArrivee());
            pst.setString(3, trajet.getHeureDepart());
            pst.setString(4, trajet.getHeureArrivee());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modifier(MetroTrajet trajet) {
        String query = "UPDATE metro_trajet SET gare_depart=?, gare_arrivee=?, heure_depart=?, heure_arrivee=? WHERE id=?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, trajet.getGareDepart());
            pst.setString(2, trajet.getGareArrivee());
            pst.setString(3, trajet.getHeureDepart());
            pst.setString(4, trajet.getHeureArrivee());
            pst.setInt(5, trajet.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void supprimer(int id) {
        String query = "DELETE FROM metro_trajet WHERE id=?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<MetroTrajet> getAll() {
        List<MetroTrajet> trajets = new ArrayList<>();
        String query = "SELECT * FROM metro_trajet";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                MetroTrajet trajet = new MetroTrajet(
                        rs.getInt("id"),
                        rs.getString("gare_depart"),
                        rs.getString("gare_arrivee"),
                        rs.getString("heure_depart"),
                        rs.getString("heure_arrivee")
                );
                trajets.add(trajet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trajets;
    }

    public boolean isUnique(MetroTrajet trajet) {
        String query = "SELECT COUNT(*) FROM metro_trajet WHERE gare_depart=? AND gare_arrivee=? AND heure_depart=? AND heure_arrivee=?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, trajet.getGareDepart());
            pst.setString(2, trajet.getGareArrivee());
            pst.setString(3, trajet.getHeureDepart());
            pst.setString(4, trajet.getHeureArrivee());
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}