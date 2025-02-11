package models;

public class Equipement {
    private int idEquipement;
    private int qteDispo;
    private String nomEquip;
    private double prix;
    private String image;

    public Equipement() {}
    public Equipement(int idEquipement,int qteDispo, String nomEquip, double prix, String image) {
        this.idEquipement = idEquipement;
        this.qteDispo = qteDispo;
        this.nomEquip = nomEquip;
        this.prix = prix;
        this.image = image;
    }

    public Equipement(int qteDispo, String nomEquip, double prix, String image) {

        this.qteDispo = qteDispo;
        this.nomEquip = nomEquip;
        this.prix = prix;
        this.image = image;

    }
    public int getIdEquipement() {
        return idEquipement;
    }
    public void setIdEquipement(int idEquipement) {
        this.idEquipement = idEquipement;
    }
    public int getQteDispo() {
        return qteDispo;
    }
    public void setQteDispo(int qteDispo) {
        this.qteDispo = qteDispo;
    }
    public String getNomEquip() {
        return nomEquip;
    }
    public void setNomEquip(String nomEquip) {
        this.nomEquip = nomEquip;
    }
    public double getPrix() {
        return prix;
    }
    public void setPrix(double prix) {
        this.prix = prix;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    @Override
    public String toString() {
        return "Equipement {" +
                "idEquipement =" + idEquipement +
                ", prix = " + prix +
                ", nomEquip ='" + nomEquip + '\'' +
                ", qteDispo =" + qteDispo +
                ", image ='" + image + '\'' +
                '}';
    }
}
