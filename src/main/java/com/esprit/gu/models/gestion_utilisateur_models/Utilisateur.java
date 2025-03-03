package com.esprit.gu.models.gestion_utilisateur_models;

import com.esprit.gu.models.gestion_utilisateur_models.enums.AuthProvider;

/**
 * Model class for representing a user in the application.
 * This is used for transferring user data between different parts of the application.
 */
public class Utilisateur {
    private int idUtilisateur;
    private String prenom;
    private String nom;
    private String email;
    private String motDePasse;
    private String telephone;
    private String role;
    private AuthProvider auth_provider;
    
    /**
     * Default constructor
     */
    public Utilisateur() {
        this.auth_provider = AuthProvider.LOCAL; // Default authentication provider
    }
    
    /**
     * Constructor with all fields except ID and auth provider
     */
    public Utilisateur(String prenom, String nom, String email, String motDePasse, String telephone, String role) {
        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.telephone = telephone;
        this.role = role;
        this.auth_provider = AuthProvider.LOCAL; // Default authentication provider
    }
    
    /**
     * Full constructor with all fields
     */
    public Utilisateur(int idUtilisateur, String prenom, String nom, String email, String motDePasse, String telephone, String role, AuthProvider auth_provider) {
        this.idUtilisateur = idUtilisateur;
        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.telephone = telephone;
        this.role = role;
        this.auth_provider = auth_provider;
    }
    
    // Getters and Setters
    
    public int getIdUtilisateur() {
        return idUtilisateur;
    }
    
    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }
    
    public String getPrenom() {
        return prenom;
    }
    
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    
    public String getNom() {
        return nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getMotDePasse() {
        return motDePasse;
    }
    
    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
    
    public String getTelephone() {
        return telephone;
    }
    
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    public AuthProvider getAuth_provider() {
        return auth_provider;
    }
    
    public void setAuth_provider(AuthProvider auth_provider) {
        this.auth_provider = auth_provider;
    }
    
    public void setAuthProvider(AuthProvider authProvider) {
        this.auth_provider = authProvider;
    }
    
    // For compatibility with entity class methods
    public String getNomUtilisateur() {
        return nom;
    }
    
    public String getPrenomUtilisateur() {
        return prenom;
    }
    
    public String getEmailUtilisateur() {
        return email;
    }
    
    public String getMotDePasseUtilisateur() {
        return motDePasse;
    }
    
    public String getRoleUtilisateur() {
        return role;
    }
    
    @Override
    public String toString() {
        return "Utilisateur{" +
                "idUtilisateur=" + idUtilisateur +
                ", prenom='" + prenom + '\'' +
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", authProvider=" + auth_provider +
                '}';
    }
} 