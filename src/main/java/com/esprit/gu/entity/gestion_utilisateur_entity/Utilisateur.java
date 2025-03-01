package com.esprit.gu.entity.gestion_utilisateur_entity;

public class Utilisateur {
    private int idUtilisateur;
    private String nomUtilisateur;
    private String prenomUtilisateur;
    private String emailUtilisateur;
    private String motDePasseUtilisateur;
    private String numeroTelephoneUtilisateur;
    private String roleUtilisateur;
    // New fields for security question and answer
    private String questionSecurite;
    private String reponseSecurite;

    // Existing constructors...
    public Utilisateur(String nom, String prenom, String email, String motdepasse, String telephone) { }

    public Utilisateur(String nomUtilisateur, String prenomUtilisateur, String emailUtilisateur,
                       String motDePasseUtilisateur, String numeroTelephoneUtilisateur,
                       String roleUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
        this.prenomUtilisateur = prenomUtilisateur;
        this.emailUtilisateur = emailUtilisateur;
        this.motDePasseUtilisateur = motDePasseUtilisateur;
        this.numeroTelephoneUtilisateur = numeroTelephoneUtilisateur;
        this.roleUtilisateur = roleUtilisateur;
    }

    // New constructor including security question and answer
    public Utilisateur(String nomUtilisateur, String prenomUtilisateur, String emailUtilisateur,
                       String motDePasseUtilisateur, String numeroTelephoneUtilisateur,
                       String roleUtilisateur, String questionSecurite, String reponseSecurite) {
        this.nomUtilisateur = nomUtilisateur;
        this.prenomUtilisateur = prenomUtilisateur;
        this.emailUtilisateur = emailUtilisateur;
        this.motDePasseUtilisateur = motDePasseUtilisateur;
        this.numeroTelephoneUtilisateur = numeroTelephoneUtilisateur;
        this.roleUtilisateur = roleUtilisateur;
        this.questionSecurite = questionSecurite;
        this.reponseSecurite = reponseSecurite;
    }

    public Utilisateur(int idUtilisateur, String nomUtilisateur, String prenomUtilisateur, String emailUtilisateur,
                       String motDePasseUtilisateur, String numeroTelephoneUtilisateur, String roleUtilisateur,
                       String questionSecurite, String reponseSecurite) {
        this.idUtilisateur = idUtilisateur;
        this.nomUtilisateur = nomUtilisateur;
        this.prenomUtilisateur = prenomUtilisateur;
        this.emailUtilisateur = emailUtilisateur;
        this.motDePasseUtilisateur = motDePasseUtilisateur;
        this.numeroTelephoneUtilisateur = numeroTelephoneUtilisateur;
        this.roleUtilisateur = roleUtilisateur;
        this.questionSecurite = questionSecurite;
        this.reponseSecurite = reponseSecurite;
    }

    // Getters and setters for new fields
    public String getQuestionSecurite() {
        return questionSecurite;
    }

    public void setQuestionSecurite(String questionSecurite) {
        this.questionSecurite = questionSecurite;
    }

    public String getReponseSecurite() {
        return reponseSecurite;
    }

    public void setReponseSecurite(String reponseSecurite) {
        this.reponseSecurite = reponseSecurite;
    }

    // Existing getters and setters...
    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    public String getPrenomUtilisateur() {
        return prenomUtilisateur;
    }

    public void setPrenomUtilisateur(String prenomUtilisateur) {
        this.prenomUtilisateur = prenomUtilisateur;
    }

    public String getEmailUtilisateur() {
        return emailUtilisateur;
    }

    public void setEmailUtilisateur(String emailUtilisateur) {
        this.emailUtilisateur = emailUtilisateur;
    }

    public String getMotDePasseUtilisateur() {
        return motDePasseUtilisateur;
    }

    public void setMotDePasseUtilisateur(String motDePasseUtilisateur) {
        this.motDePasseUtilisateur = motDePasseUtilisateur;
    }

    public String getNumeroTelephoneUtilisateur() {
        return numeroTelephoneUtilisateur;
    }

    public void setNumeroTelephoneUtilisateur(String numeroTelephoneUtilisateur) {
        this.numeroTelephoneUtilisateur = numeroTelephoneUtilisateur;
    }

    public String getRoleUtilisateur() {
        return roleUtilisateur;
    }

    public void setRoleUtilisateur(String roleUtilisateur) {
        this.roleUtilisateur = roleUtilisateur;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "idUtilisateur=" + idUtilisateur +
                ", nomUtilisateur='" + nomUtilisateur + '\'' +
                ", prenomUtilisateur='" + prenomUtilisateur + '\'' +
                ", emailUtilisateur='" + emailUtilisateur + '\'' +
                ", numeroTelephoneUtilisateur='" + numeroTelephoneUtilisateur + '\'' +
                ", roleUtilisateur='" + roleUtilisateur + '\'' +
                ", questionSecurite='" + questionSecurite + '\'' +
                ", reponseSecurite='" + reponseSecurite + '\'' +
                '}';
    }
}
