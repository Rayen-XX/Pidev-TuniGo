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

import com.esprit.gu.entity.Metro;
import com.esprit.gu.util.DBUtil;

public class ServiceMetro {
    private Connection cnx = DBUtil.getConnection();

    public ServiceMetro() throws Exception {
    }

    public void ajouter(Metro metro) {
        String req = "INSERT INTO metro (numeroMetro) VALUES (?)";

        try {
            PreparedStatement pst = this.cnx.prepareStatement(req);
            pst.setString(1, metro.getNumeroMetro());
            pst.executeUpdate();
            System.out.println("\ud83d\ude86 Métro ajouté avec succès !");
        } catch (SQLException var4) {
            SQLException ex = var4;
            System.out.println("Erreur lors de l'ajout du métro : " + ex.getMessage());
        }

    }

    public Metro getOne(int id) {
        Metro metro = null;
        String req = "SELECT * FROM `metro` WHERE `idMetro` = " + id;

        try {
            Statement stm = this.cnx.createStatement();
            ResultSet rs = stm.executeQuery(req);
            if (rs.next()) {
                metro = new Metro();
                metro.setIdMetro(rs.getInt("idMetro"));
                metro.setNumeroMetro(rs.getString("numeroMetro"));
            }
        } catch (SQLException var6) {
            SQLException ex = var6;
            System.out.println("Erreur SQL : " + ex.getMessage());
        }

        return metro;
    }

    public List<Metro> getAll() {
        List<Metro> metros = new ArrayList();
        String req = "SELECT * FROM metro";

        try {
            Statement stm = this.cnx.createStatement();
            ResultSet rs = stm.executeQuery(req);

            while(rs.next()) {
                metros.add(new Metro(rs.getInt("idMetro"), rs.getString("numeroMetro")));
            }
        } catch (SQLException var5) {
            SQLException ex = var5;
            System.out.println("Erreur lors de la récupération des métros : " + ex.getMessage());
        }

        return metros;
    }

    public void supprimer(int idMetro) {
        String req = "DELETE FROM metro WHERE idMetro = ?";

        try {
            PreparedStatement pst = this.cnx.prepareStatement(req);
            pst.setInt(1, idMetro);
            pst.executeUpdate();
            System.out.println("\ud83d\ude86 Métro supprimé avec succès !");
        } catch (SQLException var4) {
            SQLException ex = var4;
            System.out.println("Erreur lors de la suppression du métro : " + ex.getMessage());
        }

    }

    public void modifier(Metro metro) {
        String req = "UPDATE metro SET numeroMetro = ? WHERE idMetro = ?";

        try {
            PreparedStatement pst = this.cnx.prepareStatement(req);
            pst.setString(1, metro.getNumeroMetro());
            pst.setInt(2, metro.getIdMetro());
            pst.executeUpdate();
            System.out.println("\ud83d\ude86 Métro modifié avec succès !");
        } catch (SQLException var4) {
            SQLException ex = var4;
            System.out.println("Erreur lors de la modification du métro : " + ex.getMessage());
        }

    }
}
