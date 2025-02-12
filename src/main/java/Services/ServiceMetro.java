package Services;

import entities.Metro;
import tools.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceMetro {
    private Connection cnx;

    public ServiceMetro() {
        this.cnx = DataSource.getInstance().getConnection();
    }

    // Ajouter un métro
    public void ajouter(Metro metro) {
        String req = "INSERT INTO metro (numeroMetro) VALUES (?)";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setString(1, metro.getNumeroMetro());
            pst.executeUpdate();
            System.out.println("🚆 Métro ajouté avec succès !");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de l'ajout du métro : " + ex.getMessage());
        }
    }

    public Metro getOne(int id) {
        Metro metro = null;
        String req = "SELECT * FROM `metro` WHERE `idMetro` = " + id;
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(req);
            if (rs.next()) {
                metro = new Metro();
                metro.setIdMetro(rs.getInt("idMetro"));
                metro.setNumeroMetro(rs.getString("numeroMetro"));
            }
        } catch (SQLException ex) {
            System.out.println("Erreur SQL : " + ex.getMessage());
        }
        return metro;
    }

    // Récupérer tous les métros
    public List<Metro> getAll() {
        List<Metro> metros = new ArrayList<>();
        String req = "SELECT * FROM metro";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(req);
            while (rs.next()) {
                metros.add(new Metro(rs.getInt("idMetro"), rs.getString("numeroMetro")));
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la récupération des métros : " + ex.getMessage());
        }
        return metros;
    }

    // Supprimer un métro par ID
    public void supprimer(int idMetro) {
        String req = "DELETE FROM metro WHERE idMetro = ?";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, idMetro);
            pst.executeUpdate();
            System.out.println("🚆 Métro supprimé avec succès !");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la suppression du métro : " + ex.getMessage());
        }
    }

    // Modifier un métro
    public void modifier(Metro metro) {
        String req = "UPDATE metro SET numeroMetro = ? WHERE idMetro = ?";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setString(1, metro.getNumeroMetro());
            pst.setInt(2, metro.getIdMetro());
            pst.executeUpdate();
            System.out.println("🚆 Métro modifié avec succès !");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la modification du métro : " + ex.getMessage());
        }
    }
}
