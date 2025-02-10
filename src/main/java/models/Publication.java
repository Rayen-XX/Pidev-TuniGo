package models;

import java.util.Arrays;
import java.util.List;

public class Publication {

    private int id , userId ;
    private String contenu , date , description , title , type_pub ;

    public Publication() {}

    public Publication(int id, String contenu, String date, String description, String title, String type_pub) {
        this.id = id;
        this.contenu = contenu;
        this.date = date;
        this.description = description;
        this.title = title;
        this.type_pub = type_pub;
    }

    public Publication(String contenu, String date, String description, String title , String type_pub) {
        this.contenu = contenu;
        this.date = date;
        this.description = description;
        this.title = title;
        this.type_pub = type_pub;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType_pub() {
        return type_pub;
    }

    public void setType_pub(String type_pub) {
        this.type_pub = type_pub;
    }

    @Override
    public String toString() {
        return "Publication{" +
                "id=" + id +
                ", userId=" + userId +
                ", contenu='" + contenu + '\'' +
                ", date='" + date + '\'' +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                ", type_pub='" + type_pub + '\'' +
                '}';
    }
}
