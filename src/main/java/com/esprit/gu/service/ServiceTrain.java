package com.esprit.gu.service;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.esprit.gu.entity.Train;
import com.esprit.gu.util.DBUtil;

public class ServiceTrain {
    private Connection cnx = DBUtil.getConnection();

    public ServiceTrain() throws Exception {
    }

    public void ajouter(Train train) {
        String req = "INSERT INTO train (numeroTrain) VALUES (?)";

        try {
            PreparedStatement pst = this.cnx.prepareStatement(req);
            pst.setString(1, train.getNumeroTrain());
            pst.executeUpdate();
            System.out.println("\ud83d\ude84 Train ajouté avec succès !");
        } catch (SQLException var4) {
            SQLException ex = var4;
            System.out.println("Erreur lors de l'ajout du train : " + ex.getMessage());
        }

    }

    public Train getOne(int id) {
        Train train = null;
        String req = "SELECT * FROM `train` WHERE `idTrain` = " + id;

        try {
            Statement stm = this.cnx.createStatement();
            ResultSet rs = stm.executeQuery(req);
            if (rs.next()) {
                train = new Train();
                train.setIdTrain(rs.getInt("idTrain"));
                train.setNumeroTrain(rs.getString("numeroTrain"));
            }
        } catch (SQLException var6) {
            SQLException ex = var6;
            System.out.println("Erreur SQL : " + ex.getMessage());
        }

        return train;
    }

    public List<Train> getAll() {
        List<Train> trains = new ArrayList();
        String req = "SELECT * FROM train";

        try {
            Statement stm = this.cnx.createStatement();
            ResultSet rs = stm.executeQuery(req);

            while(rs.next()) {
                trains.add(new Train(rs.getInt("idTrain"), rs.getString("numeroTrain")));
            }
        } catch (SQLException var5) {
            SQLException ex = var5;
            System.out.println("Erreur lors de la récupération des trains : " + ex.getMessage());
        }

        return trains;
    }

    public void supprimer(int idTrain) {
        String req = "DELETE FROM train WHERE idTrain = ?";

        try {
            PreparedStatement pst = this.cnx.prepareStatement(req);
            pst.setInt(1, idTrain);
            pst.executeUpdate();
            System.out.println("\ud83d\ude84 Train supprimé avec succès !");
        } catch (SQLException var4) {
            SQLException ex = var4;
            System.out.println("Erreur lors de la suppression du train : " + ex.getMessage());
        }

    }

    public void modifier(Train train) {
        String req = "UPDATE train SET numeroTrain = ? WHERE idTrain = ?";

        try {
            PreparedStatement pst = this.cnx.prepareStatement(req);
            pst.setString(1, train.getNumeroTrain());
            pst.setInt(2, train.getIdTrain());
            pst.executeUpdate();
            System.out.println("\ud83d\ude84 Train modifié avec succès !");
        } catch (SQLException var4) {
            SQLException ex = var4;
            System.out.println("Erreur lors de la modification du train : " + ex.getMessage());
        }

    }
}
