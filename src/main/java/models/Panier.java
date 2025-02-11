package models;

public class Panier {
    private int id,id_equip,qte_com,id_utilisateur;
    private double prix_total;

    public Panier() {}
    public Panier(int id, int id_equip, int qte_com, double prix_total) {
        this.id = id;
        this.id_equip = id_equip;
        this.qte_com = qte_com;
        this.prix_total = prix_total;
    }




}
