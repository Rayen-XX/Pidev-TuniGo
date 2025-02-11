package models;

import java.util.Arrays;
import java.util.List;

public class Publication {

    private int id , userId ;
    private String contenu , description , title , type_pub , date_pub ;

    public Publication() {}

    public Publication(int id, int userId, String contenu, String description, String title, String type_pub) {
        this.id = id;
        this.userId = userId;
        this.contenu = contenu;
        this.description = description;
        this.title = title;
        this.type_pub = type_pub;
    }

    public Publication(int userId,String contenu, String description, String title , String type_pub) {
        this.userId = userId;
        this.contenu = contenu;
        this.description = description;
        this.title = title;
        this.type_pub = type_pub;
    }

    public String getDate_pub() {
        return date_pub;
    }

    public void setDate_pub(String date_pub) {
        this.date_pub = date_pub;
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
                ", date_pub=" + date_pub +
                ", contenu='" + contenu + '\'' +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                ", type_pub='" + type_pub + '\'' +
                '}';
    }
}
