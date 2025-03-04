package com.esprit.gu.service;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.esprit.gu.entity.Scooter;
import com.esprit.gu.util.DBUtil;

public class ServiceScooter {
    private Connection cnx = DBUtil.getConnection();

    public ServiceScooter() throws Exception {
    }

    public void ajouter(Scooter scooter) {
        try {
            String var10000 = scooter.getNumeroScooter();
            String req = "INSERT INTO `scooter`(`numeroScooter`, `localisationScooter`) VALUES ('" + var10000 + "', '" + scooter.getLocalisationScooter() + "')";
            Statement stm = this.cnx.createStatement();
            stm.executeUpdate(req);
        } catch (SQLException var4) {
            SQLException ex = var4;
            System.out.println("Erreur SQL : " + ex.getMessage());
        }

    }

    public void modifier(Scooter scooter) {
        try {
            String var10000 = scooter.getNumeroScooter();
            String req = "UPDATE `scooter` SET `numeroScooter` = '" + var10000 + "', `localisationScooter` = '" + scooter.getLocalisationScooter() + "' WHERE `idScooter` = " + scooter.getIdScooter();
            Statement stm = this.cnx.createStatement();
            stm.executeUpdate(req);
        } catch (SQLException var4) {
            SQLException ex = var4;
            System.out.println("Erreur SQL : " + ex.getMessage());
        }

    }

    public void supprimer(int id) {
        try {
            String req = "DELETE FROM `scooter` WHERE `idScooter` = " + id;
            Statement stm = this.cnx.createStatement();
            stm.executeUpdate(req);
        } catch (SQLException var4) {
            SQLException ex = var4;
            System.out.println("Erreur SQL : " + ex.getMessage());
        }

    }

    public Scooter getOne(int id) {
        Scooter scooter = null;
        String req = "SELECT * FROM `scooter` WHERE `idScooter` = " + id;

        try {
            Statement stm = this.cnx.createStatement();
            ResultSet rs = stm.executeQuery(req);
            if (rs.next()) {
                scooter = new Scooter();
                scooter.setIdScooter(rs.getInt("idScooter"));
                scooter.setNumeroScooter(rs.getString("numeroScooter"));
                scooter.setLocalisationScooter(rs.getString("localisationScooter"));
            }
        } catch (SQLException var6) {
            SQLException ex = var6;
            System.out.println("Erreur SQL : " + ex.getMessage());
        }

        return scooter;
    }

    public List<Scooter> getAll() {
        List<Scooter> scooters = new ArrayList();
        String req = "SELECT * FROM `scooter`";

        try {
            Statement stm = this.cnx.createStatement();
            ResultSet rs = stm.executeQuery(req);

            while(rs.next()) {
                Scooter scooter = new Scooter();
                scooter.setIdScooter(rs.getInt("idScooter"));
                scooter.setNumeroScooter(rs.getString("numeroScooter"));
                scooter.setLocalisationScooter(rs.getString("localisationScooter"));
                scooters.add(scooter);
            }
        } catch (SQLException var6) {
            SQLException ex = var6;
            System.out.println("Erreur SQL : " + ex.getMessage());
        }

        return scooters;
    }
}
