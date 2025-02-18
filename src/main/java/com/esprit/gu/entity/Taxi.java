package com.esprit.gu.entity;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


public class Taxi {
    private int idTaxi;
    private String numeroTaxi;
    private String numeroChauffeur;
    private String prenomChauffeur;
    private String nomChauffeur;

    public Taxi() {
    }

    public Taxi(int idTaxi, String numeroTaxi, String numeroChauffeur, String prenomChauffeur, String nomChauffeur) {
        this.idTaxi = idTaxi;
        this.numeroTaxi = numeroTaxi;
        this.numeroChauffeur = numeroChauffeur;
        this.prenomChauffeur = prenomChauffeur;
        this.nomChauffeur = nomChauffeur;
    }

    public int getIdTaxi() {
        return this.idTaxi;
    }

    public void setIdTaxi(int idTaxi) {
        this.idTaxi = idTaxi;
    }

    public String getNumeroTaxi() {
        return this.numeroTaxi;
    }

    public void setNumeroTaxi(String numeroTaxi) {
        this.numeroTaxi = numeroTaxi;
    }

    public String getNumeroChauffeur() {
        return this.numeroChauffeur;
    }

    public void setNumeroChauffeur(String numeroChauffeur) {
        this.numeroChauffeur = numeroChauffeur;
    }

    public String getPrenomChauffeur() {
        return this.prenomChauffeur;
    }

    public void setPrenomChauffeur(String prenomChauffeur) {
        this.prenomChauffeur = prenomChauffeur;
    }

    public String getNomChauffeur() {
        return this.nomChauffeur;
    }

    public void setNomChauffeur(String nomChauffeur) {
        this.nomChauffeur = nomChauffeur;
    }

    public String toString() {
        return "Taxi{idTaxi=" + this.idTaxi + ", numeroTaxi='" + this.numeroTaxi + "', numeroChauffeur='" + this.numeroChauffeur + "', prenomChauffeur='" + this.prenomChauffeur + "', nomChauffeur='" + this.nomChauffeur + "'}";
    }
}
