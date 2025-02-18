package com.esprit.gu.entity;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


public class Metro {
    private int idMetro;
    private String numeroMetro;

    public int getIdMetro() {
        return this.idMetro;
    }

    public void setIdMetro(int idMetro) {
        this.idMetro = idMetro;
    }

    public String getNumeroMetro() {
        return this.numeroMetro;
    }

    public void setNumeroMetro(String numeroMetro) {
        this.numeroMetro = numeroMetro;
    }

    public Metro() {
    }

    public Metro(int idMetro, String numeroMetro) {
        this.idMetro = idMetro;
        this.numeroMetro = numeroMetro;
    }

    public String toString() {
        return "Metro{idMetro=" + this.idMetro + ", numeroMetro='" + this.numeroMetro + "'}";
    }
}
