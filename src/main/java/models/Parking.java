package models;

public class Parking {
    private int idParking;
    private String nomParking;
    private String localisationParking;
    private int place;


    public Parking() {
    }


    public Parking(int idParking, String nomParking, String localisationParking, int place) {
        this.idParking = idParking;
        this.nomParking = nomParking;
        this.localisationParking = localisationParking;
        this.place = place;
    }
    public Parking( String nomParking, String localisationParking, int place) {
        this.idParking = idParking;
        this.nomParking = nomParking;
        this.localisationParking = localisationParking;
        this.place = place;
    }

    public int getIdParking() {
        return idParking;
    }

    public void setIdParking(int idParking) {
        this.idParking = idParking;
    }

    public String getNomParking() {
        return nomParking;
    }

    public void setNomParking(String nomParking) {
        this.nomParking = nomParking;
    }

    public String getLocalisationParking() {
        return localisationParking;
    }

    public void setLocalisationParking(String localisationParking) {
        this.localisationParking = localisationParking;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    // Méthode toString
    @Override
    public String toString() {
        return "Parking{" +
                "idParking=" + idParking +
                ", nomParking='" + nomParking + '\'' +
                ", localisationParking='" + localisationParking + '\'' +
                ", place=" + place +
                '}';
    }
}
