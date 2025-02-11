package models;

public class Likes {

    private int id,publicationId, userId ;
    private String date ;

    public Likes() {}

    public Likes(int id, int publicationId, int userId, String date) {
        this.id = id;
        this.publicationId = publicationId;
        this.userId = userId;
        this.date = date;
    }

    public Likes(int publicationId, int userId) {
        this.publicationId = publicationId;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPublicationId() {
        return publicationId;
    }

    public void setPublicationId(int publicationId) {
        this.publicationId = publicationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Likes{" +
                "id=" + id +
                ", publicationId=" + publicationId +
                ", userId=" + userId +
                ", date='" + date + '\'' +
                '}';
    }
}
