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
import com.esprit.gu.util.DBUtil;

import com.esprit.gu.entity.Bus;
import com.esprit.gu.util.DBUtil;

public class ServiceBus {
    private Connection cnx = DBUtil.getConnection();

    public ServiceBus() throws Exception {
    }

    public void ajouter(Bus bus) {
        String req = "INSERT INTO bus (numeroBus) VALUES (?)";

        try {
            PreparedStatement pst = this.cnx.prepareStatement(req);
            pst.setString(1, bus.getNumeroBus());
            pst.executeUpdate();
            System.out.println("\ud83d\ude8c Bus ajouté avec succès !");
        } catch (SQLException var4) {
            SQLException ex = var4;
            System.out.println("Erreur lors de l'ajout du bus : " + ex.getMessage());
        }

    }

    public Bus getOne(int id) {
        Bus bus = null;
        String req = "SELECT * FROM `bus` WHERE `idBus` = " + id;

        try {
            Statement stm = this.cnx.createStatement();
            ResultSet rs = stm.executeQuery(req);
            if (rs.next()) {
                bus = new Bus();
                bus.setIdBus(rs.getInt("idBus"));
                bus.setNumeroBus(rs.getString("numeroBus"));
            }
        } catch (SQLException var6) {
            SQLException ex = var6;
            System.out.println("Erreur SQL : " + ex.getMessage());
        }

        return bus;
    }

    public List<Bus> getAll() {
        List<Bus> busList = new ArrayList();
        String req = "SELECT * FROM bus";

        try {
            Statement stm = this.cnx.createStatement();
            ResultSet rs = stm.executeQuery(req);

            while(rs.next()) {
                busList.add(new Bus(rs.getInt("idBus"), rs.getString("numeroBus")));
            }
        } catch (SQLException var5) {
            SQLException ex = var5;
            System.out.println("Erreur lors de la récupération des bus : " + ex.getMessage());
        }

        return busList;
    }

    public void supprimer(int idBus) {
        String req = "DELETE FROM bus WHERE idBus = ?";

        try {
            PreparedStatement pst = this.cnx.prepareStatement(req);
            pst.setInt(1, idBus);
            pst.executeUpdate();
            System.out.println("\ud83d\ude8c Bus supprimé avec succès !");
        } catch (SQLException var4) {
            SQLException ex = var4;
            System.out.println("Erreur lors de la suppression du bus : " + ex.getMessage());
        }

    }

    public void modifier(Bus bus) {
        String req = "UPDATE bus SET numeroBus = ? WHERE idBus = ?";

        try {
            PreparedStatement pst = this.cnx.prepareStatement(req);
            pst.setString(1, bus.getNumeroBus());
            pst.setInt(2, bus.getIdBus());
            pst.executeUpdate();
            System.out.println("\ud83d\ude8c Bus modifié avec succès !");
        } catch (SQLException var4) {
            SQLException ex = var4;
            System.out.println("Erreur lors de la modification du bus : " + ex.getMessage());
        }

    }
}
