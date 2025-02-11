package models;

public class Reservation {
        private int id, campingid , utilisateurid ;
        private double montant;

        public Reservation() {}
    public Reservation(int id,int utilisateurid, int campingid, double montant) {
            this.id = id;
            this.campingid = campingid;
            this.montant = montant;
            this.utilisateurid = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCampingid() {
        return campingid;
    }

    public void setCampingid(int campingid) {
        this.campingid = campingid;
    }

    public int getUtilisateurid() {
        return utilisateurid;
    }

    public void setUtilisateurid(int utilisateurid) {
        this.utilisateurid = utilisateurid;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public Reservation(int utilisateurid, int campingid, double montant) {

        this.campingid = campingid;
        this.montant = montant;
        this.utilisateurid = id;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", campingid=" + campingid +
                ", utilisateurid=" + utilisateurid +
                ", montant=" + montant +
                '}';
    }
}
