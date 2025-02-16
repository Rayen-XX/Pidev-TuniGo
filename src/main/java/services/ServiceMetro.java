package services;

import entities.MetroTrajet;
import tools.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceMetro implements IService<MetroTrajet> {
    private Connection con = DataSource.getConnection();

    @Override
    public void ajouter(MetroTrajet t) {
        String sql = "INSERT INTO metro_trajet (gare_depart, gare_arrivee, heure_depart, heure_arrivee) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, t.getGareDepart());
            ps.setString(2, t.getGareArrivee());
            ps.setString(3, t.getHeureDepart());
            ps.setString(4, t.getHeureArrivee());
            ps.executeUpdate();
            System.out.println("Trajet ajouté !");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void modifier(MetroTrajet t) {
        String sql = "UPDATE metro_trajet SET gare_depart = ?, gare_arrivee = ?, heure_depart = ?, heure_arrivee = ? WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, t.getGareDepart());
            ps.setString(2, t.getGareArrivee());
            ps.setString(3, t.getHeureDepart());
            ps.setString(4, t.getHeureArrivee());
            ps.setInt(5, t.getId());
            ps.executeUpdate();
            System.out.println("Trajet modifié !");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }




    @Override
    public void supprimer(int id) {
        String sql = "DELETE FROM metro_trajet WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Trajet supprimé !");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public MetroTrajet getOne(int id) {
        String sql = "SELECT * FROM metro_trajet WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new MetroTrajet(
                        rs.getInt("id"),
                        rs.getString("gare_depart"),
                        rs.getString("gare_arrivee"),
                        rs.getString("heure_depart"),
                        rs.getString("heure_arrivee")
                );
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<MetroTrajet> getAll() {
        List<MetroTrajet> trajets = new ArrayList<>();
        String sql = "SELECT * FROM metro_trajet";
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                trajets.add(new MetroTrajet(
                        rs.getInt("id"),
                        rs.getString("gare_depart"),
                        rs.getString("gare_arrivee"),
                        rs.getString("heure_depart"),
                        rs.getString("heure_arrivee")
                ));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return trajets;
    }


}
