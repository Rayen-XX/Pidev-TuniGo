package Services;

import entities.Bus;
import tools.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceBus {
    private Connection cnx;

    public ServiceBus() {
        this.cnx = DataSource.getInstance().getConnection();
    }

    // Ajouter un bus
    public void ajouter(Bus bus) {
        String req = "INSERT INTO bus (numeroBus) VALUES (?)";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setString(1, bus.getNumeroBus());
            pst.executeUpdate();
            System.out.println("🚌 Bus ajouté avec succès !");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de l'ajout du bus : " + ex.getMessage());
        }
    }

    public Bus getOne(int id) {
        Bus bus = null;
        String req = "SELECT * FROM `bus` WHERE `idBus` = " + id;
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(req);
            if (rs.next()) {
                bus = new Bus();
                bus.setIdBus(rs.getInt("idBus"));
                bus.setNumeroBus(rs.getString("numeroBus"));
            }
        } catch (SQLException ex) {
            System.out.println("Erreur SQL : " + ex.getMessage());
        }
        return bus;
    }

    // Récupérer tous les bus
    public List<Bus> getAll() {
        List<Bus> busList = new ArrayList<>();
        String req = "SELECT * FROM bus";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(req);
            while (rs.next()) {
                busList.add(new Bus(rs.getInt("idBus"), rs.getString("numeroBus")));
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la récupération des bus : " + ex.getMessage());
        }
        return busList;
    }

    // Supprimer un bus par ID
    public void supprimer(int idBus) {
        String req = "DELETE FROM bus WHERE idBus = ?";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, idBus);
            pst.executeUpdate();
            System.out.println("🚌 Bus supprimé avec succès !");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la suppression du bus : " + ex.getMessage());
        }
    }

    // Modifier un bus
    public void modifier(Bus bus) {
        String req = "UPDATE bus SET numeroBus = ? WHERE idBus = ?";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setString(1, bus.getNumeroBus());
            pst.setInt(2, bus.getIdBus());
            pst.executeUpdate();
            System.out.println("🚌 Bus modifié avec succès !");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la modification du bus : " + ex.getMessage());
        }
    }
}
