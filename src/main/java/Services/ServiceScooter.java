package Services;

import entities.Scooter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import tools.DataSource;

public class ServiceScooter {
    private Connection cnx;

    public ServiceScooter() {
        this.cnx = DataSource.getInstance().getConnection();
    }

    // Ajouter un scooter
    public void ajouter(Scooter scooter) {
        try {
            String req = "INSERT INTO `scooter`(`numeroScooter`, `localisationScooter`) VALUES ('" +
                    scooter.getNumeroScooter() + "', '" + scooter.getLocalisationScooter() + "')";
            Statement stm = cnx.createStatement();
            stm.executeUpdate(req);
        } catch (SQLException ex) {
            System.out.println("Erreur SQL : " + ex.getMessage());
        }
    }

    // Modifier un scooter
    public void modifier(Scooter scooter) {
        try {
            String req = "UPDATE `scooter` SET `numeroScooter` = '" + scooter.getNumeroScooter() + "', `localisationScooter` = '" +
                    scooter.getLocalisationScooter() + "' WHERE `idScooter` = " + scooter.getIdScooter();
            Statement stm = cnx.createStatement();
            stm.executeUpdate(req);
        } catch (SQLException ex) {
            System.out.println("Erreur SQL : " + ex.getMessage());
        }
    }

    // Supprimer un scooter
    public void supprimer(int id) {
        try {
            String req = "DELETE FROM `scooter` WHERE `idScooter` = " + id;
            Statement stm = cnx.createStatement();
            stm.executeUpdate(req);
        } catch (SQLException ex) {
            System.out.println("Erreur SQL : " + ex.getMessage());
        }
    }

    // Récupérer un scooter par son ID
    public Scooter getOne(int id) {
        Scooter scooter = null;
        String req = "SELECT * FROM `scooter` WHERE `idScooter` = " + id;
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(req);
            if (rs.next()) {
                scooter = new Scooter();
                scooter.setIdScooter(rs.getInt("idScooter"));
                scooter.setNumeroScooter(rs.getString("numeroScooter"));
                scooter.setLocalisationScooter(rs.getString("localisationScooter"));
            }
        } catch (SQLException ex) {
            System.out.println("Erreur SQL : " + ex.getMessage());
        }
        return scooter;
    }

    // Récupérer tous les scooters
    public List<Scooter> getAll() {
        List<Scooter> scooters = new ArrayList<>();
        String req = "SELECT * FROM `scooter`";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(req);
            while (rs.next()) {
                Scooter scooter = new Scooter();
                scooter.setIdScooter(rs.getInt("idScooter"));
                scooter.setNumeroScooter(rs.getString("numeroScooter"));
                scooter.setLocalisationScooter(rs.getString("localisationScooter"));
                scooters.add(scooter);
            }
        } catch (SQLException ex) {
            System.out.println("Erreur SQL : " + ex.getMessage());
        }
        return scooters;
    }
}
