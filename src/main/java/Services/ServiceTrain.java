package Services;

import entities.Train;
import tools.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceTrain {
    private Connection cnx;

    public ServiceTrain() {
        this.cnx = DataSource.getInstance().getConnection();
    }

    // Ajouter un train
    public void ajouter(Train train) {
        String req = "INSERT INTO train (numeroTrain) VALUES (?)";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setString(1, train.getNumeroTrain());
            pst.executeUpdate();
            System.out.println("🚄 Train ajouté avec succès !");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de l'ajout du train : " + ex.getMessage());
        }
    }
    public Train getOne(int id) {
        Train train = null;
        String req = "SELECT * FROM `train` WHERE `idTrain` = " + id;
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(req);
            if (rs.next()) {
                train = new Train();
                train.setIdTrain(rs.getInt("idTrain"));
                train.setNumeroTrain(rs.getString("numeroTrain"));
            }
        } catch (SQLException ex) {
            System.out.println("Erreur SQL : " + ex.getMessage());
        }
        return train;
    }


    // Récupérer tous les trains
    public List<Train> getAll() {
        List<Train> trains = new ArrayList<>();
        String req = "SELECT * FROM train";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(req);
            while (rs.next()) {
                trains.add(new Train(rs.getInt("idTrain"), rs.getString("numeroTrain")));
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la récupération des trains : " + ex.getMessage());
        }
        return trains;
    }

    // Supprimer un train par ID
    public void supprimer(int idTrain) {
        String req = "DELETE FROM train WHERE idTrain = ?";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, idTrain);
            pst.executeUpdate();
            System.out.println("🚄 Train supprimé avec succès !");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la suppression du train : " + ex.getMessage());
        }
    }

    // Modifier un train
    public void modifier(Train train) {
        String req = "UPDATE train SET numeroTrain = ? WHERE idTrain = ?";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setString(1, train.getNumeroTrain());
            pst.setInt(2, train.getIdTrain());
            pst.executeUpdate();
            System.out.println("🚄 Train modifié avec succès !");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la modification du train : " + ex.getMessage());
        }
    }
}
