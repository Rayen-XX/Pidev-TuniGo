package com.esprit.gu.entity;

public class Reservation {
    private int id;
    private String nomUtilisateur; // Remplace idUser
    private int idTrajet;
    private String dateDebut;
    private String dateFin;
    private double montant;
    private String statut;

    // Constructeurs
    public Reservation(String nomUtilisateur, int idTrajet, String dateDebut, String dateFin, double montant, String statut) {
        this.nomUtilisateur = nomUtilisateur; // Nouveau champ
        this.idTrajet = idTrajet;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.montant = montant;
        this.statut = statut;
    }

    public Reservation(int id, String nomUtilisateur, int idTrajet, String dateDebut, String dateFin, double montant, String statut) {
        this(nomUtilisateur, idTrajet, dateDebut, dateFin, montant, statut);
        this.id = id;
    }

    // Getters
    public int getId() { return id; }
    public String getNomUtilisateur() { return nomUtilisateur; } // Nouveau getter
    public int getIdTrajet() { return idTrajet; }
    public String getDateDebut() { return dateDebut; }
    public String getDateFin() { return dateFin; }
    public double getMontant() { return montant; }
    public String getStatut() { return statut; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setNomUtilisateur(String nomUtilisateur) { this.nomUtilisateur = nomUtilisateur; } // Nouveau setter
    public void setIdTrajet(int idTrajet) { this.idTrajet = idTrajet; }
    public void setDateDebut(String dateDebut) { this.dateDebut = dateDebut; }
    public void setDateFin(String dateFin) { this.dateFin = dateFin; }
    public void setMontant(double montant) { this.montant = montant; }
    public void setStatut(String statut) { this.statut = statut; }
}