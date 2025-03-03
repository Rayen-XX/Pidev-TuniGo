package com.esprit.gu.entity;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


public class Scooter {
    private int idScooter;
    private String numeroScooter;
    private String localisationScooter;

    public Scooter() {
    }

    public Scooter(int idScooter, String numeroScooter, String localisationScooter) {
        this.idScooter = idScooter;
        this.numeroScooter = numeroScooter;
        this.localisationScooter = localisationScooter;
    }

    public int getIdScooter() {
        return this.idScooter;
    }

    public void setIdScooter(int idScooter) {
        this.idScooter = idScooter;
    }

    public String getNumeroScooter() {
        return this.numeroScooter;
    }

    public void setNumeroScooter(String numeroScooter) {
        this.numeroScooter = numeroScooter;
    }

    public String getLocalisationScooter() {
        return this.localisationScooter;
    }

    public void setLocalisationScooter(String localisationScooter) {
        this.localisationScooter = localisationScooter;
    }

    public String toString() {
        return "Scooter{idScooter=" + this.idScooter + ", numeroScooter='" + this.numeroScooter + "', localisationScooter='" + this.localisationScooter + "'}";
    }
}
