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

import com.esprit.gu.entity.Taxi;
import com.esprit.gu.util.DBUtil;

public class ServiceTaxi {
    private Connection cnx = DBUtil.getConnection();

    public ServiceTaxi() throws Exception {
    }

    public void ajouter(Taxi taxi) {
        try {
            String var10000 = taxi.getNumeroTaxi();
            String req = "INSERT INTO `taxi`(`numeroTaxi`, `numeroChauffeur`, `prenomChauffeur`, `nomChauffeur`) VALUES ('" + var10000 + "', '" + taxi.getNumeroChauffeur() + "', '" + taxi.getPrenomChauffeur() + "', '" + taxi.getNomChauffeur() + "')";
            Statement stm = this.cnx.createStatement();
            stm.executeUpdate(req);
        } catch (SQLException var4) {
            SQLException ex = var4;
            System.out.println("Erreur SQL : " + ex.getMessage());
        }

    }

    public void modifier(Taxi taxi) {
        try {
            String var10000 = taxi.getNumeroTaxi();
            String req = "UPDATE `taxi` SET `numeroTaxi` = '" + var10000 + "', `numeroChauffeur` = '" + taxi.getNumeroChauffeur() + "', `prenomChauffeur` = '" + taxi.getPrenomChauffeur() + "', `nomChauffeur` = '" + taxi.getNomChauffeur() + "' WHERE `idTaxi` = " + taxi.getIdTaxi();
            Statement stm = this.cnx.createStatement();
            stm.executeUpdate(req);
        } catch (SQLException var4) {
            SQLException ex = var4;
            System.out.println("Erreur SQL : " + ex.getMessage());
        }

    }

    public void supprimer(int id) {
        try {
            String req = "DELETE FROM `taxi` WHERE `idTaxi` = " + id;
            Statement stm = this.cnx.createStatement();
            stm.executeUpdate(req);
        } catch (SQLException var4) {
            SQLException ex = var4;
            System.out.println("Erreur SQL : " + ex.getMessage());
        }

    }

    public Taxi getOne(int id) {
        Taxi taxi = null;
        String req = "SELECT * FROM `taxi` WHERE `idTaxi` = " + id;

        try {
            Statement stm = this.cnx.createStatement();
            ResultSet rs = stm.executeQuery(req);
            if (rs.next()) {
                taxi = new Taxi();
                taxi.setIdTaxi(rs.getInt("idTaxi"));
                taxi.setNumeroTaxi(rs.getString("numeroTaxi"));
                taxi.setNumeroChauffeur(rs.getString("numeroChauffeur"));
                taxi.setPrenomChauffeur(rs.getString("prenomChauffeur"));
                taxi.setNomChauffeur(rs.getString("nomChauffeur"));
            }
        } catch (SQLException var6) {
            SQLException ex = var6;
            System.out.println("Erreur SQL : " + ex.getMessage());
        }

        return taxi;
    }

    public List<Taxi> getAll() {
        List<Taxi> taxis = new ArrayList();
        String req = "SELECT * FROM `taxi`";

        try {
            Statement stm = this.cnx.createStatement();
            ResultSet rs = stm.executeQuery(req);

            while(rs.next()) {
                Taxi taxi = new Taxi();
                taxi.setIdTaxi(rs.getInt("idTaxi"));
                taxi.setNumeroTaxi(rs.getString("numeroTaxi"));
                taxi.setNumeroChauffeur(rs.getString("numeroChauffeur"));
                taxi.setPrenomChauffeur(rs.getString("prenomChauffeur"));
                taxi.setNomChauffeur(rs.getString("nomChauffeur"));
                taxis.add(taxi);
            }
        } catch (SQLException var6) {
            SQLException ex = var6;
            System.out.println("Erreur SQL : " + ex.getMessage());
        }

        return taxis;
    }
}
