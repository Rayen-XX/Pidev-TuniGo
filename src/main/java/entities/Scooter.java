package entities;

public class Scooter {
    private int idScooter;
    private String numeroScooter;
    private String localisationScooter;

    // Constructeurs
    public Scooter() {
    }

    public Scooter(int idScooter, String numeroScooter, String localisationScooter) {
        this.idScooter = idScooter;
        this.numeroScooter = numeroScooter;
        this.localisationScooter = localisationScooter;
    }

    // Getters et Setters
    public int getIdScooter() {
        return idScooter;
    }

    public void setIdScooter(int idScooter) {
        this.idScooter = idScooter;
    }

    public String getNumeroScooter() {
        return numeroScooter;
    }

    public void setNumeroScooter(String numeroScooter) {
        this.numeroScooter = numeroScooter;
    }

    public String getLocalisationScooter() {
        return localisationScooter;
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
                '}';
    }
}
