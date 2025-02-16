/*package Services;

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
            String req = "INSERT INTO `reclamation`(`typeReclamation`, `descriptionReclamation`, `statutReclamation`, `dateReclamation`,`nom_utilisateur`,prenom_utilisateur) VALUES ('" +
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
                reclamation.setNom_utilisateur(rs.getString("nom_utilisateur"));
                reclamation.getPrenom_utilisateur(rs.getString("prenom_utilisateur"));
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
*/

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

    // Ajouter une réclamation avec une requête préparée
    public void ajouter(Reclamation reclamation) {
        String req = "INSERT INTO `reclamation`(`nom_utilisateur`, `prenom_utilisateur`, `typeReclamation`, `descriptionReclamation`, `statutReclamation`, `dateReclamation`) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstm = cnx.prepareStatement(req)) {
            pstm.setString(1, reclamation.getNom_utilisateur());
            pstm.setString(2, reclamation.getPrenom_utilisateur());
            pstm.setString(3, reclamation.getTypeReclamation());
            pstm.setString(4, reclamation.getDescriptionReclamation());
            pstm.setString(5, reclamation.getStatutReclamation());
            pstm.setDate(6, new java.sql.Date(reclamation.getDateReclamation().getTime()));
            pstm.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erreur lors de l'ajout de la réclamation : " + ex.getMessage());
        }
    }

    // Modifier une réclamation avec une requête préparée
    public void modifier(Reclamation reclamation) {
        String req = "UPDATE `reclamation` SET `nom_utilisateur` = ?, `prenom_utilisateur` = ?, `typeReclamation` = ?, `descriptionReclamation` = ?, `statutReclamation` = ?, `dateReclamation` = ? WHERE `idReclamation` = ?";
        try (PreparedStatement pstm = cnx.prepareStatement(req)) {
            pstm.setString(1, reclamation.getNom_utilisateur());
            pstm.setString(2, reclamation.getPrenom_utilisateur());
            pstm.setString(3, reclamation.getTypeReclamation());
            pstm.setString(4, reclamation.getDescriptionReclamation());
            pstm.setString(5, reclamation.getStatutReclamation());
            pstm.setDate(6, new java.sql.Date(reclamation.getDateReclamation().getTime()));
            pstm.setInt(7, reclamation.getIdReclamation());
            pstm.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la modification de la réclamation : " + ex.getMessage());
        }
    }

    // Supprimer une réclamation
    public void supprimer(int id) {
        String req = "DELETE FROM `reclamation` WHERE `idReclamation` = ?";
        try (PreparedStatement pstm = cnx.prepareStatement(req)) {
            pstm.setInt(1, id);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la suppression de la réclamation : " + ex.getMessage());
        }
    }

    // Récupérer une réclamation par son ID
    public Reclamation getOne(int id) {
        Reclamation reclamation = null;
        String req = "SELECT * FROM `reclamation` WHERE `idReclamation` = ?";
        try (PreparedStatement pstm = cnx.prepareStatement(req)) {
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                reclamation = new Reclamation();
                reclamation.setIdReclamation(rs.getInt("idReclamation"));
                reclamation.setNom_utilisateur(rs.getString("nom_utilisateur"));
                reclamation.setPrenom_utilisateur(rs.getString("prenom_utilisateur"));
                reclamation.setTypeReclamation(rs.getString("typeReclamation"));
                reclamation.setDescriptionReclamation(rs.getString("descriptionReclamation"));
                reclamation.setStatutReclamation(rs.getString("statutReclamation"));
                reclamation.setDateReclamation(rs.getDate("dateReclamation"));
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la récupération de la réclamation : " + ex.getMessage());
        }
        return reclamation;
    }

    // Récupérer toutes les réclamations
    public List<Reclamation> getAll() {
        List<Reclamation> reclamations = new ArrayList<>();
        String req = "SELECT * FROM `reclamation`";
        try (Statement stm = cnx.createStatement();
             ResultSet rs = stm.executeQuery(req)) {
            while (rs.next()) {
                Reclamation reclamation = new Reclamation();
                reclamation.setIdReclamation(rs.getInt("idReclamation"));
                reclamation.setNom_utilisateur(rs.getString("nom_utilisateur"));
                reclamation.setPrenom_utilisateur(rs.getString("prenom_utilisateur"));
                reclamation.setTypeReclamation(rs.getString("typeReclamation"));
                reclamation.setDescriptionReclamation(rs.getString("descriptionReclamation"));
                reclamation.setStatutReclamation(rs.getString("statutReclamation"));
                reclamation.setDateReclamation(rs.getDate("dateReclamation"));
                reclamations.add(reclamation);
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la récupération des réclamations : " + ex.getMessage());
        }
        return reclamations;
    }
}
