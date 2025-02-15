package org.example;

public class Reservation {
    private int id;
    private int idUser;
    private int idTrajet;
    private String dateDebut;
    private String dateFin;
    private double montant;
    private String statut;


    public Reservation(int idUser, int idTrajet, String dateDebut, String dateFin, double montant, String statut) {
        this.idUser = idUser;
        this.idTrajet = idTrajet;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.montant = montant;
        this.statut = statut;
    }

    public Reservation(int id, int idUser, int idTrajet, String dateDebut, String dateFin, double montant, String statut) {
        this(idUser, idTrajet, dateDebut, dateFin, montant, statut);
        this.id = id;
    }


    public int getId() { return id; }
    public int getIdUser() { return idUser; }
    public int getIdTrajet() { return idTrajet; }
    public String getDateDebut() { return dateDebut; }
    public String getDateFin() { return dateFin; }
    public double getMontant() { return montant; }
    public String getStatut() { return statut; }

    public void setId(int id) { this.id = id; }
    public void setIdUser(int idUser) { this.idUser = idUser; }
    public void setIdTrajet(int idTrajet) { this.idTrajet = idTrajet; }
    public void setDateDebut(String dateDebut) { this.dateDebut = dateDebut; }
    public void setDateFin(String dateFin) { this.dateFin = dateFin; }
    public void setMontant(double montant) { this.montant = montant; }
    public void setStatut(String statut) { this.statut = statut; }
}

