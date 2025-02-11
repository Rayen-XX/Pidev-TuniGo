package models;

public class Panier {
    private int id,id_equip,qte_com,id_utilisateur;
    private double prix_total;

    public Panier() {}

    public Panier(int id, int id_equip,int id_utilisateur ,int qte_com, double prix_total) {
        this.id = id;
        this.id_equip = id_equip;
        this.id_utilisateur=id_utilisateur;
        this.qte_com = qte_com;
        this.prix_total = prix_total;
    }
    public Panier(int id_equip,int id_utilisateur,int qte_com, double prix_total) {

        this.id_equip = id_equip;
        this.id_utilisateur=id_utilisateur;
        this.qte_com = qte_com;
        this.prix_total = prix_total;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getId_equip() {
        return id_equip;
    }
    public void setId_equip(int id_equip) {
        this.id_equip = id_equip;
    }
    public int getQte_com() {
        return qte_com;
    }
    public void setQte_com(int qte_com) {
        this.qte_com = qte_com;
    }
    public double getPrix_total() {
        return prix_total;
    }
    public void setPrix_total(double prix_total) {
        this.prix_total = prix_total;

    }
    public int getId_utilisateur() {
        return id_utilisateur;
    }
    public void setId_utilisateur(int id_utilisateur) {
        this.id_utilisateur = id_utilisateur;
    }

    @Override
    public String toString() {
        return "Panier{" +
                "id=" + id +
                ", id_equip=" + id_equip +
                ", qte_com=" + qte_com +
                ", id_utilisateur=" + id_utilisateur +
                ", prix_total=" + prix_total +
                '}';
    }
}
