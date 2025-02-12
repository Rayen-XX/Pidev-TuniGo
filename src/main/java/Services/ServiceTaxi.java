package Services;

import entities.Taxi;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import tools.DataSource;

public class ServiceTaxi {
    private Connection cnx;

    public ServiceTaxi() {
        this.cnx = DataSource.getInstance().getConnection();
    }

    // Ajouter un taxi
    public void ajouter(Taxi taxi) {
        try {
            String req = "INSERT INTO `taxi`(`numeroTaxi`, `numeroChauffeur`, `prenomChauffeur`, `nomChauffeur`) " +
                    "VALUES ('" + taxi.getNumeroTaxi() + "', '" + taxi.getNumeroChauffeur() + "', '" + taxi.getPrenomChauffeur() + "', '" + taxi.getNomChauffeur() + "')";
            Statement stm = cnx.createStatement();
            stm.executeUpdate(req);
        } catch (SQLException ex) {
            System.out.println("Erreur SQL : " + ex.getMessage());
        }
    }

    // Modifier un taxi
    public void modifier(Taxi taxi) {
        try {
            String req = "UPDATE `taxi` SET `numeroTaxi` = '" + taxi.getNumeroTaxi() + "', `numeroChauffeur` = '" +
                    taxi.getNumeroChauffeur() + "', `prenomChauffeur` = '" + taxi.getPrenomChauffeur() + "', `nomChauffeur` = '" + taxi.getNomChauffeur() + "' WHERE `idTaxi` = " + taxi.getIdTaxi();
            Statement stm = cnx.createStatement();
            stm.executeUpdate(req);
        } catch (SQLException ex) {
            System.out.println("Erreur SQL : " + ex.getMessage());
        }
    }

    // Supprimer un taxi
    public void supprimer(int id) {
        try {
            String req = "DELETE FROM `taxi` WHERE `idTaxi` = " + id;
            Statement stm = cnx.createStatement();
            stm.executeUpdate(req);
        } catch (SQLException ex) {
            System.out.println("Erreur SQL : " + ex.getMessage());
        }
    }

    // Récupérer un taxi par son ID
    public Taxi getOne(int id) {
        Taxi taxi = null;
        String req = "SELECT * FROM `taxi` WHERE `idTaxi` = " + id;
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(req);
            if (rs.next()) {
                taxi = new Taxi();
                taxi.setIdTaxi(rs.getInt("idTaxi"));
                taxi.setNumeroTaxi(rs.getString("numeroTaxi"));
                taxi.setNumeroChauffeur(rs.getString("numeroChauffeur"));
                taxi.setPrenomChauffeur(rs.getString("prenomChauffeur"));
                taxi.setNomChauffeur(rs.getString("nomChauffeur"));
            }
        } catch (SQLException ex) {
            System.out.println("Erreur SQL : " + ex.getMessage());
        }
        return taxi;
    }

    // Récupérer tous les taxis
    public List<Taxi> getAll() {
        List<Taxi> taxis = new ArrayList<>();
        String req = "SELECT * FROM `taxi`";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(req);
            while (rs.next()) {
                Taxi taxi = new Taxi();
                taxi.setIdTaxi(rs.getInt("idTaxi"));
                taxi.setNumeroTaxi(rs.getString("numeroTaxi"));
                taxi.setNumeroChauffeur(rs.getString("numeroChauffeur"));
                taxi.setPrenomChauffeur(rs.getString("prenomChauffeur"));
                taxi.setNomChauffeur(rs.getString("nomChauffeur"));
                taxis.add(taxi);
            }
        } catch (SQLException ex) {
            System.out.println("Erreur SQL : " + ex.getMessage());
        }
        return taxis;
    }
}
