package entities;

public class MetroTrajet {
    private int id;
    private String gareDepart;
    private String gareArrivee;
    private String heureDepart;
    private String heureArrivee;

    public MetroTrajet(String gareDepart, String gareArrivee, String heureDepart, String heureArrivee) {
        this.gareDepart = gareDepart;
        this.gareArrivee = gareArrivee;
        this.heureDepart = heureDepart;
        this.heureArrivee = heureArrivee;
    }

    public MetroTrajet(int id, String gareDepart, String gareArrivee, String heureDepart, String heureArrivee) {
        this.id = id;
        this.gareDepart = gareDepart;
        this.gareArrivee = gareArrivee;
        this.heureDepart = heureDepart;
        this.heureArrivee = heureArrivee;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getGareDepart() { return gareDepart; }
    public void setGareDepart(String gareDepart) { this.gareDepart = gareDepart; }
    public String getGareArrivee() { return gareArrivee; }
    public void setGareArrivee(String gareArrivee) { this.gareArrivee = gareArrivee; }
    public String getHeureDepart() { return heureDepart; }
    public void setHeureDepart(String heureDepart) { this.heureDepart = heureDepart; }
    public String getHeureArrivee() { return heureArrivee; }
    public void setHeureArrivee(String heureArrivee) { this.heureArrivee = heureArrivee; }
}