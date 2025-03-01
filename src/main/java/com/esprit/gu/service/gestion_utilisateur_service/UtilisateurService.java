package com.esprit.gu.service.gestion_utilisateur_service;

import com.esprit.gu.entity.gestion_utilisateur_entity.Utilisateur;
import com.esprit.gu.repository.UtilisateurRepository;

import java.util.List;

public class UtilisateurService {
    private UtilisateurRepository utilisateurRepository;

    public UtilisateurService() {
        this.utilisateurRepository = new UtilisateurRepository();
    }

    // Register a new user
    public boolean register(Utilisateur utilisateur) {
        if (utilisateurRepository.getUtilisateurByEmail(utilisateur.getEmailUtilisateur()) != null) {
            System.out.println("L'utilisateur existe déjà.");
            return false;
        }
        return utilisateurRepository.registerUtilisateur(utilisateur);
    }

    // Login a user
    public Utilisateur login(String email, String password) {
        Utilisateur utilisateur = utilisateurRepository.getUtilisateurByEmail(email);
        if (utilisateur != null) {
            if (utilisateur.getMotDePasseUtilisateur().equals(password)) {
                return utilisateur;
            } else {
                System.out.println("Mot de passe incorrect.");
            }
        } else {
            System.out.println("Utilisateur non trouvé.");
        }
        return null;
    }

    // New method to get a user by email.
    public Utilisateur getUtilisateurByEmail(String email) {
        return utilisateurRepository.getUtilisateurByEmail(email);
    }

    // Update an existing user
    public boolean updateUtilisateur(Utilisateur utilisateur) {
        return utilisateurRepository.updateUtilisateur(utilisateur);
    }

    // Delete a user by ID
    public boolean deleteUtilisateur(int idUtilisateur) {
        return utilisateurRepository.deleteUtilisateur(idUtilisateur);
    }

    // Retrieve all users
    public List<Utilisateur> getAllUsers() {
        return utilisateurRepository.getAllUsers();
    }
}
