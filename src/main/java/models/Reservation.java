package models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Reservation {
    private int idReservation;
    private LocalDateTime dateReservation;
    private int idUtilisateur=1;
    private Integer idTrajetTrain;
    private Integer idTrajetMetro;
    private Integer idTrajetBus;
    private Integer idScooter;
    private Integer idTaxi;
    private Integer idParking;


    public Reservation() {
    }


    public Reservation(int idReservation, LocalDateTime dateReservation, int idUtilisateur, Integer idTrajetTrain,
                       Integer idTrajetMetro, Integer idTrajetBus, Integer idScooter, Integer idTaxi, Integer idParking) {
        this.idReservation = idReservation;
        this.dateReservation = dateReservation;
        this.idUtilisateur = idUtilisateur;
        this.idTrajetTrain = idTrajetTrain;
        this.idTrajetMetro = idTrajetMetro;
        this.idTrajetBus = idTrajetBus;
        this.idScooter = idScooter;
        this.idTaxi = idTaxi;
        this.idParking = idParking;
    }
    public Reservation( LocalDateTime dateReservation, int idUtilisateur,Integer idParking) {
        this.dateReservation = dateReservation;
        this.idParking = idParking;
    }


    public int getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(int idReservation) {
        this.idReservation = idReservation;
    }

    public LocalDateTime getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(LocalDateTime dateReservation) {
        this.dateReservation = dateReservation;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public Integer getIdTrajetTrain() {
        return idTrajetTrain;
    }

    public void setIdTrajetTrain(Integer idTrajetTrain) {
        this.idTrajetTrain = idTrajetTrain;
    }

    public Integer getIdTrajetMetro() {
        return idTrajetMetro;
    }

    public void setIdTrajetMetro(Integer idTrajetMetro) {
        this.idTrajetMetro = idTrajetMetro;
    }

    public Integer getIdTrajetBus() {
        return idTrajetBus;
    }

    public void setIdTrajetBus(Integer idTrajetBus) {
        this.idTrajetBus = idTrajetBus;
    }

    public Integer getIdScooter() {
        return idScooter;
    }

    public void setIdScooter(Integer idScooter) {
        this.idScooter = idScooter;
    }

    public Integer getIdTaxi() {
        return idTaxi;
    }

    public void setIdTaxi(Integer idTaxi) {
        this.idTaxi = idTaxi;
    }

    public Integer getIdParking() {
        return idParking;
    }

    public void setIdParking(Integer idParking) {
        this.idParking = idParking;
    }

    // Méthode toString
    @Override
    public String toString() {
        return "Reservation{" +
                "idReservation=" + idReservation +
                ", dateReservation=" + dateReservation +
                ", idUtilisateur=" + idUtilisateur +
                ", idTrajetTrain=" + idTrajetTrain +
                ", idTrajetMetro=" + idTrajetMetro +
                ", idTrajetBus=" + idTrajetBus +
                ", idScooter=" + idScooter +
                ", idTaxi=" + idTaxi +
                ", idParking=" + idParking +
                '}';
    }
}
