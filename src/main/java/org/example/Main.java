package org.example;
import models.Equipement;
import models.Panier;
import services.EquipementService;
import services.PanierService;
import utils.MyDB;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {


        EquipementService equipement1 = new EquipementService();
        PanierService panier1 = new PanierService();
        try {
            Equipement equip1 = new Equipement(24, 10, "lunette", 120.50, "image.jpg");

            Panier panier = new Panier(23,1,10,200000);
            panier1.create(panier);
            panier1.update(panier);
            panier1.delete(panier);
            System.out.println(panier1.getAll());

            //////h





            //Equipement equip2 = new Equipement(1, 10, "tente", 120.50, "image.jpg");
            //System.out.println(equip.toString());
            //equipement1.create(equip1);
            //equipement1.create(equip2);
            //System.out.println("ajout avec succes");
            //equipement1.update(equip1);
            //System.out.println("update avec succes");
            //System.out.println(equipement1.getAll());


            /*equipement1.delete(equip1);
            System.out.println("equipement supprimé");*/


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }



}
