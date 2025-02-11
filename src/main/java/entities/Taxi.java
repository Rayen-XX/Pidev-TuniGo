package entities;

public class Taxi {
    private int idTaxi;
    private String numeroTaxi;
    private String numeroChauffeur;
    private String prenomChauffeur;
    private String nomChauffeur;

    // Constructeurs
    public Taxi() {
    }

    public Taxi(int idTaxi, String numeroTaxi, String numeroChauffeur, String prenomChauffeur, String nomChauffeur) {
        this.idTaxi = idTaxi;
        this.numeroTaxi = numeroTaxi;
        this.numeroChauffeur = numeroChauffeur;
        this.prenomChauffeur = prenomChauffeur;
        this.nomChauffeur = nomChauffeur;
    }

    // Getters et Setters
    public int getIdTaxi() {
        return idTaxi;
    }

    public void setIdTaxi(int idTaxi) {
        this.idTaxi = idTaxi;
    }

    public String getNumeroTaxi() {
        return numeroTaxi;
    }

    public void setNumeroTaxi(String numeroTaxi) {
        this.numeroTaxi = numeroTaxi;
    }

    public String getNumeroChauffeur() {
        return numeroChauffeur;
    }

    public void setNumeroChauffeur(String numeroChauffeur) {
        this.numeroChauffeur = numeroChauffeur;
    }

    public String getPrenomChauffeur() {
        return prenomChauffeur;
    }

    public void setPrenomChauffeur(String prenomChauffeur) {
        this.prenomChauffeur = prenomChauffeur;
    }

    public String getNomChauffeur() {
        return nomChauffeur;
    }

    public void setNomChauffeur(String nomChauffeur) {
        this.nomChauffeur = nomChauffeur;
    }

    @Override
    public String toString() {
        return "Taxi{" +
                "idTaxi=" + idTaxi +
                ", numeroTaxi='" + numeroTaxi + '\'' +
                ", numeroChauffeur='" + numeroChauffeur + '\'' +
                ", prenomChauffeur='" + prenomChauffeur + '\'' +
                ", nomChauffeur='" + nomChauffeur + '\'' +
                '}';
    }
}
