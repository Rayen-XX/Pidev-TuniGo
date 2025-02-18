package com.esprit.gu.entity;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


public class Bus {
    private int idBus;
    private String numeroBus;

    public int getIdBus() {
        return this.idBus;
    }

    public void setIdBus(int idBus) {
        this.idBus = idBus;
    }

    public String getNumeroBus() {
        return this.numeroBus;
    }

    public void setNumeroBus(String numeroBus) {
        this.numeroBus = numeroBus;
    }

    public Bus() {
    }

    public Bus(int idBus, String numeroBus) {
        this.idBus = idBus;
        this.numeroBus = numeroBus;
    }

    public String toString() {
        return "Metro{idBus=" + this.idBus + ", numeroBus='" + this.numeroBus + "'}";
    }
}
