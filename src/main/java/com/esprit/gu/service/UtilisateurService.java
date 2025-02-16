package com.esprit.gu.service;

import com.esprit.gu.entity.Utilisateur;
import com.esprit.gu.repository.UtilisateurRepository;

import java.util.List;

public class UtilisateurService {
    private UtilisateurRepository utilisateurRepository;

    public UtilisateurService() {
        this.utilisateurRepository = new UtilisateurRepository();
    }

    // Register a new user
    public boolean register(Utilisateur utilisateur) {
        // Here you can add validation (e.g., check if username is already taken)
        if (utilisateurRepository.getUtilisateurByEmail(utilisateur.getEmailUtilisateur()) != null) {
            System.out.println("utilisateur already exists.");
            return false;
        }
        // You might want to hash the password here before storing it
        return utilisateurRepository.registerUtilisateur(utilisateur);
    }

    // Login a user
    public Utilisateur login(String email, String password) {
        Utilisateur utilisateur = utilisateurRepository.getUtilisateurByEmail(email);
        if (utilisateur != null) {
            // For simplicity, comparing plain text passwords.
            if (utilisateur.getMotDePasseUtilisateur().equals(password)) {
                return utilisateur;
            } else {
                System.out.println("Incorrect password.");
            }
        } else {
            System.out.println("User not found.");
        }
        return null;
    }


    // Update user
    public boolean updateUtilisateur(Utilisateur utilisateur) {
        return utilisateurRepository.updateUtilisateur(utilisateur);
    }

    // Delete user
    public boolean deleteUtilisateur(int idUtilisateur) {
        return utilisateurRepository.deleteUtilisateur(idUtilisateur);
    }

    public List<Utilisateur> getAllUsers() {
        return utilisateurRepository.getAllUsers();
    }


}
