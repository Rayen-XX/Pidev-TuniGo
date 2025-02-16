package com.esprit.gu.entity;

public class Utilisateur {
    private int idUtilisateur;
    private String nomUtilisateur;
    private String prenomUtilisateur;
    private String emailUtilisateur;
    private String motDePasseUtilisateur;
    private String numeroTelephoneUtilisateur;
    private String roleUtilisateur; // Could be "admin" or "utilisateur"

    // Constructors
    public Utilisateur(String nom, String prenom, String email, String motdepasse, String telephone) { }

    // Constructor without id (for registration where id is auto-generated)
    public Utilisateur(String nomUtilisateur, String prenomUtilisateur, String emailUtilisateur,
                       String motDePasseUtilisateur, String numeroTelephoneUtilisateur,
                       String roleUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
        this.prenomUtilisateur = prenomUtilisateur;
        this.emailUtilisateur = emailUtilisateur;
        this.motDePasseUtilisateur = motDePasseUtilisateur;
        this.numeroTelephoneUtilisateur = numeroTelephoneUtilisateur;
        this.roleUtilisateur = roleUtilisateur;
    }

    // Full constructor (useful when retrieving data from DB)
    public Utilisateur(int idUtilisateur, String nomUtilisateur, String prenomUtilisateur, String emailUtilisateur,
                       String motDePasseUtilisateur, String numeroTelephoneUtilisateur, String roleUtilisateur) {
        this.idUtilisateur = idUtilisateur;
        this.nomUtilisateur = nomUtilisateur;
        this.prenomUtilisateur = prenomUtilisateur;
        this.emailUtilisateur = emailUtilisateur;
        this.motDePasseUtilisateur = motDePasseUtilisateur;
        this.numeroTelephoneUtilisateur = numeroTelephoneUtilisateur;
        this.roleUtilisateur = roleUtilisateur;
    }

    // Getters and Setters

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    public String getPrenomUtilisateur() {
        return prenomUtilisateur;
    }

    public void setPrenomUtilisateur(String prenomUtilisateur) {
        this.prenomUtilisateur = prenomUtilisateur;
    }

    public String getEmailUtilisateur() {
        return emailUtilisateur;
    }

    public void setEmailUtilisateur(String emailUtilisateur) {
        this.emailUtilisateur = emailUtilisateur;
    }

    public String getMotDePasseUtilisateur() {
        return motDePasseUtilisateur;
    }

    public void setMotDePasseUtilisateur(String motDePasseUtilisateur) {
        this.motDePasseUtilisateur = motDePasseUtilisateur;
    }

    public String getNumeroTelephoneUtilisateur() {
        return numeroTelephoneUtilisateur;
    }

    public void setNumeroTelephoneUtilisateur(String numeroTelephoneUtilisateur) {
        this.numeroTelephoneUtilisateur = numeroTelephoneUtilisateur;
    }

    public String getRoleUtilisateur() {
        return roleUtilisateur;
    }

    public void setRoleUtilisateur(String roleUtilisateur) {
        this.roleUtilisateur = roleUtilisateur;
    }

    // Optionally, override toString() for easier debugging (excluding sensitive fields like motDePasseUtilisateur)
    @Override
    public String toString() {
        return "Utilisateur{" +
                "idUtilisateur=" + idUtilisateur +
                ", nomUtilisateur='" + nomUtilisateur + '\'' +
                ", prenomUtilisateur='" + prenomUtilisateur + '\'' +
                ", emailUtilisateur='" + emailUtilisateur + '\'' +
                ", numeroTelephoneUtilisateur='" + numeroTelephoneUtilisateur + '\'' +
                ", roleUtilisateur='" + roleUtilisateur + '\'' +
                '}';
    }
}
