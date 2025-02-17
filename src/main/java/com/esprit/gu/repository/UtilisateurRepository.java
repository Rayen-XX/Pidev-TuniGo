package com.esprit.gu.repository;

import com.esprit.gu.entity.Utilisateur;
import com.esprit.gu.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class UtilisateurRepository {

    //registration houni
    public boolean registerUtilisateur(Utilisateur utilisateur) {
        String sql = "INSERT INTO utilisateur (nomUtilisateur, prenomUtilisateur, emailUtilisateur, motDePasseUtilisateur, numeroTelephoneUtilisateur, roleUtilisateur) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, utilisateur.getNomUtilisateur());
            stmt.setString(2, utilisateur.getPrenomUtilisateur());
            stmt.setString(3, utilisateur.getEmailUtilisateur());
            stmt.setString(4, utilisateur.getMotDePasseUtilisateur());
            stmt.setString(5, utilisateur.getNumeroTelephoneUtilisateur());
            stmt.setString(6, utilisateur.getRoleUtilisateur());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // if at least one row is inserted, registration succeeded

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            // For any errors from DBUtil.getConnection()
            e.printStackTrace();
            return false;
        }
    }

   // login bel email
    public Utilisateur getUtilisateurByEmail(String email) {
        String sql = "SELECT * FROM utilisateur WHERE emailUtilisateur = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("idUtilisateur");
                    String nom = rs.getString("nomUtilisateur");
                    String prenom = rs.getString("prenomUtilisateur");
                    String emailUtilisateur = rs.getString("emailUtilisateur");
                    String motDePasse = rs.getString("motDePasseUtilisateur");
                    String numeroTel = rs.getString("numeroTelephoneUtilisateur");
                    String role = rs.getString("roleUtilisateur");

                    return new Utilisateur(id, nom, prenom, emailUtilisateur, motDePasse, numeroTel, role);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            
            e.printStackTrace();
        }
        return null; 
    }


    // nekhou users lkol
    public List<Utilisateur> getAllUsers() {
        List<Utilisateur> users = new ArrayList<>();
        String sql = "SELECT * FROM utilisateur";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("idUtilisateur");
                String nom = rs.getString("nomUtilisateur");
                String prenom = rs.getString("prenomUtilisateur");
                String email = rs.getString("emailUtilisateur");
                String motDePasse = rs.getString("motDePasseUtilisateur");
                String numeroTel = rs.getString("numeroTelephoneUtilisateur");
                String role = rs.getString("roleUtilisateur");

                Utilisateur user = new Utilisateur(id, nom, prenom, email, motDePasse, numeroTel, role);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    // modifier user
    public boolean updateUtilisateur(Utilisateur utilisateur) {
        String sql = "UPDATE utilisateur SET nomUtilisateur = ?, prenomUtilisateur = ?, emailUtilisateur = ?, motDePasseUtilisateur = ?, numeroTelephoneUtilisateur = ?, roleUtilisateur = ? WHERE idUtilisateur = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, utilisateur.getNomUtilisateur());
            stmt.setString(2, utilisateur.getPrenomUtilisateur());
            stmt.setString(3, utilisateur.getEmailUtilisateur());
            stmt.setString(4, utilisateur.getMotDePasseUtilisateur());
            stmt.setString(5, utilisateur.getNumeroTelephoneUtilisateur());
            stmt.setString(6, utilisateur.getRoleUtilisateur());
            stmt.setInt(7, utilisateur.getIdUtilisateur());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //supprimer user
    public boolean deleteUtilisateur(int idUtilisateur) {
        String sql = "DELETE FROM utilisateur WHERE idUtilisateur = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUtilisateur);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; 

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
