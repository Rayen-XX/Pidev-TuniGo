package com.esprit.gu.entity;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import com.esprit.gu.entity.Utilisateur;

import java.util.Date;

public class Reclamation {
    private int idReclamation;
    private String nom_utilisateur;
    private String prenom_utilisateur;
    private String typeReclamation;
    private String descriptionReclamation;
    private String statutReclamation;
    private Date dateReclamation;
    private Utilisateur utilisateur;

    public Reclamation() {
    }

    public Reclamation(int idReclamation, String nom_utilisateur, String prenom_utilisateur, String type, String description, String statut, Date date) {
        this.idReclamation = idReclamation;
        this.nom_utilisateur = nom_utilisateur;
        this.prenom_utilisateur = prenom_utilisateur;
        this.typeReclamation = type;
        this.descriptionReclamation = description;
        this.statutReclamation = statut;
        this.dateReclamation = date;
    }

    public Reclamation(int idReclamation, String nom_utilisateur, String prenom_utilisateur, String typeReclamation, String descriptionReclamation, String statutReclamation, Date dateReclamation, Utilisateur utilisateur) {
        this.idReclamation = idReclamation;
        this.nom_utilisateur = nom_utilisateur;
        this.prenom_utilisateur = prenom_utilisateur;
        this.typeReclamation = typeReclamation;
        this.descriptionReclamation = descriptionReclamation;
        this.statutReclamation = statutReclamation;
        this.dateReclamation = dateReclamation;
        this.utilisateur = utilisateur;
    }

    public int getIdReclamation() {
        return this.idReclamation;
    }

    public void setIdReclamation(int idReclamation) {
        this.idReclamation = idReclamation;
    }

    public String getNom_utilisateur() {
        return this.nom_utilisateur;
    }

    public void setNom_utilisateur(String nom_utilisateur) {
        this.nom_utilisateur = nom_utilisateur;
    }

    public String getPrenom_utilisateur() {
        return this.prenom_utilisateur;
    }

    public void setPrenom_utilisateur(String prenom_utilisateur) {
        this.prenom_utilisateur = prenom_utilisateur;
    }

    public String getTypeReclamation() {
        return this.typeReclamation;
    }

    public void setTypeReclamation(String typeReclamation) {
        this.typeReclamation = typeReclamation;
    }

    public String getDescriptionReclamation() {
        return this.descriptionReclamation;
    }

    public void setDescriptionReclamation(String descriptionReclamation) {
        this.descriptionReclamation = descriptionReclamation;
    }

    public String getStatutReclamation() {
        return this.statutReclamation;
    }

    public void setStatutReclamation(String statutReclamation) {
        this.statutReclamation = statutReclamation;
    }

    public Date getDateReclamation() {
        return this.dateReclamation;
    }

    public void setDateReclamation(Date dateReclamation) {
        this.dateReclamation = dateReclamation;
    }

    public Utilisateur getUtilisateur() {
        return this.utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
}
