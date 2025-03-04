package com.esprit.gu.entity;

public class Scooter {
    private int idScooter;
    private String numeroScooter;
    private String localisationScooter;
    private boolean Isdisponible;

    public Scooter() {
        this.idScooter = 0;
    }

    public Scooter(int idScooter, String numeroScooter, String localisationScooter) {
        this.idScooter = idScooter;
        this.numeroScooter = numeroScooter;
        this.localisationScooter = localisationScooter;
    }

    public boolean isIsdisponible() {
        return Isdisponible;
    }

    public void setIsdisponible(boolean isdisponible) {
        Isdisponible = isdisponible;
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

    @Override
    public String toString() {
        return "Scooter{" +
                "idScooter=" + idScooter +
                ", numeroScooter='" + numeroScooter + '\'' +
                ", localisationScooter='" + localisationScooter + '\'' +
                ", Isdisponible=" + Isdisponible +
                '}';
    }
}
