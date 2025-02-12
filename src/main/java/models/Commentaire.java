package models;

import java.util.Arrays;
import java.util.List;

public class Commentaire {
    private int id ,userId , publicationId ;
    private String contenu , date ;

    public Commentaire() {}

    public Commentaire(int id, int userId, int publicationId, String contenu, String date) {
        this.id = id;
        this.userId = userId;
        this.publicationId = publicationId;
        this.contenu = contenu;
        this.date = date;
    }

    public Commentaire(int userId, int publicationId, String contenu) {
        this.userId = userId;
        this.publicationId = publicationId;
        this.contenu = contenu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPublicationId() {
        return publicationId;
    }

    public void setPublicationId(int publicationId) {
        this.publicationId = publicationId;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Commentaire{" +
                "id=" + id +
                ", userId=" + userId +
                ", publicationId=" + publicationId +
                ", contenu='" + contenu + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

}
