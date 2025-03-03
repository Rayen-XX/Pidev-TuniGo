package com.esprit.gu.entity;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


public class Train {
    private int idTrain;
    private String numeroTrain;

    public int getIdTrain() {
        return this.idTrain;
    }

    public void setIdTrain(int idTrain) {
        this.idTrain = idTrain;
    }

    public String getNumeroTrain() {
        return this.numeroTrain;
    }

    public void setNumeroTrain(String numeroTrain) {
        this.numeroTrain = numeroTrain;
    }

    public Train() {
    }

    public Train(int idTrain, String numeroTrain) {
        this.idTrain = idTrain;
        this.numeroTrain = numeroTrain;
    }

    public String toString() {
        return "train{idTrain=" + this.idTrain + ", numeroTrain='" + this.numeroTrain + "'}";
    }
}
