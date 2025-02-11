package entities;

import java.util.Date;

public class Reclamation {
    private int idReclamation;
    private String typeReclamation;
    private String descriptionReclamation;
    private String statutReclamation;
    private Date dateReclamation;
    private Utilisateur utilisateur;

    public Reclamation() {}

    public Reclamation(int idReclamation,String type, String description, String statut, Date date, Utilisateur utilisateur) {
        this.idReclamation = idReclamation;
        this.typeReclamation = type;
        this.descriptionReclamation = description;
        this.statutReclamation = statut;
        this.dateReclamation = date;
        this.utilisateur = utilisateur;
    }

    public int getIdReclamation() {
        return idReclamation;
    }

    public void setIdReclamation(int idReclamation) {
        this.idReclamation = idReclamation;
    }

    public String getTypeReclamation() {
        return typeReclamation;
    }

    public void setTypeReclamation(String typeReclamation) {
        this.typeReclamation = typeReclamation;
    }

    public String getDescriptionReclamation() {
        return descriptionReclamation;
    }

    public void setDescriptionReclamation(String descriptionReclamation) {
        this.descriptionReclamation = descriptionReclamation;
    }

    public String getStatutReclamation() {
        return statutReclamation;
    }

    public void setStatutReclamation(String statutReclamation) {
        this.statutReclamation = statutReclamation;
    }

    public Date getDateReclamation() {
        return dateReclamation;
    }

    public void setDateReclamation(Date dateReclamation) {
        this.dateReclamation = dateReclamation;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
}
