package Services;

import entities.Reclamation;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import tools.DataSource;

public class ServiceReclamation {
    private Connection cnx;

    public ServiceReclamation() {
        this.cnx = DataSource.getInstance().getConnection();
    }

    // Ajouter une réclamation
    public void ajouter(Reclamation reclamation) {
        try {
            String req = "INSERT INTO `reclamation`(`typeReclamation`, `descriptionReclamation`, `statutReclamation`, `dateReclamation`) VALUES ('" +
                    reclamation.getTypeReclamation() + "', '" + reclamation.getDescriptionReclamation() + "', '" +
                    reclamation.getStatutReclamation() + "', '" + reclamation.getDateReclamation() + "')";
            Statement stm = cnx.createStatement();
            stm.executeUpdate(req);
        } catch (SQLException ex) {
            System.out.println("Erreur SQL : " + ex.getMessage());
        }
    }

    // Modifier une réclamation
    public void modifier(Reclamation reclamation) {
        try {
            String req = "UPDATE `reclamation` SET `typeReclamation` = '" + reclamation.getTypeReclamation() + "', `descriptionReclamation` = '" +
                    reclamation.getDescriptionReclamation() + "', `statutReclamation` = '" + reclamation.getStatutReclamation() + "', `dateReclamation` = '" +
                    reclamation.getDateReclamation() + "' WHERE `idReclamation` = " + reclamation.getIdReclamation();
            Statement stm = cnx.createStatement();
            stm.executeUpdate(req);
        } catch (SQLException ex) {
            System.out.println("Erreur SQL : " + ex.getMessage());
        }
    }

    // Supprimer une réclamation
    public void supprimer(int id) {
        try {
            String req = "DELETE FROM `reclamation` WHERE `idReclamation` = " + id;
            Statement stm = cnx.createStatement();
            stm.executeUpdate(req);
        } catch (SQLException ex) {
            System.out.println("Erreur SQL : " + ex.getMessage());
        }
    }

    // Récupérer une réclamation par son ID
    public Reclamation getOne(int id) {
        Reclamation reclamation = null;
        String req = "SELECT * FROM `reclamation` WHERE `idReclamation` = " + id;
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(req);
            if (rs.next()) {
                reclamation = new Reclamation();
                reclamation.setIdReclamation(rs.getInt("idReclamation"));
                reclamation.setTypeReclamation(rs.getString("typeReclamation"));
                reclamation.setDescriptionReclamation(rs.getString("descriptionReclamation"));
                reclamation.setStatutReclamation(rs.getString("statutReclamation"));
                reclamation.setDateReclamation(rs.getDate("dateReclamation"));
            }
        } catch (SQLException ex) {
            System.out.println("Erreur SQL : " + ex.getMessage());
        }
        return reclamation;
    }

    // Récupérer toutes les réclamations
    public List<Reclamation> getAll() {
        List<Reclamation> reclamations = new ArrayList<>();
        String req = "SELECT * FROM `reclamation`";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(req);
            while (rs.next()) {
                Reclamation reclamation = new Reclamation();
                reclamation.setIdReclamation(rs.getInt("idReclamation"));
                reclamation.setTypeReclamation(rs.getString("typeReclamation"));
                reclamation.setDescriptionReclamation(rs.getString("descriptionReclamation"));
                reclamation.setStatutReclamation(rs.getString("statutReclamation"));
                reclamation.setDateReclamation(rs.getDate("dateReclamation"));
                reclamations.add(reclamation);
            }
        } catch (SQLException ex) {
            System.out.println("Erreur SQL : " + ex.getMessage());
        }
        return reclamations;
    }
}
