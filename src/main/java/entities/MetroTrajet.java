package entities;

public class MetroTrajet extends controllers.MetroTrajet {
    private int id;
    private String gareDepart;
    private String gareArrivee;
    private String heureDepart;
    private String heureArrivee;

    public MetroTrajet(int id, String text, String text1, String text2, String text3) {
        super(text, text1, text2, text3);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGareDepart() {
        return gareDepart;
    }

    public void setGareDepart(String gareDepart) {
        this.gareDepart = gareDepart;
    }

    public String getGareArrivee() {
        return gareArrivee;
    }

    public void setGareArrivee(String gareArrivee) {
        this.gareArrivee = gareArrivee;
    }

    public String getHeureDepart() {
        return heureDepart;
    }

    public void setHeureDepart(String heureDepart) {
        this.heureDepart = heureDepart;
    }

    public String getHeureArrivee() {
        return heureArrivee;
    }

    public void setHeureArrivee(String heureArrivee) {
        this.heureArrivee = heureArrivee;
    }

    @Override
    public String toString() {
        return "MetroTrajet{" +
                "id=" + id +
                ", gareDepart='" + gareDepart + '\'' +
                ", gareArrivee='" + gareArrivee + '\'' +
                ", heureDepart='" + heureDepart + '\'' +
                ", heureArrivee='" + heureArrivee + '\'' +
                '}';
    }

}



