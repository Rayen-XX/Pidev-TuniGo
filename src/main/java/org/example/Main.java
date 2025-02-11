package org.example;

import models.Publication;
import services.PublicationService;
import utils.MyDB;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        //Test Publication
        PublicationService ps = new PublicationService();
        try {
            Publication publication = new Publication(1, 1, "contenu 2", "description 2 ", "title 2", "video");
            //ps.create(publication);
            //System.out.println("Publication created");
            //ps.update(publication);
            //System.out.println("Publication updated");
            //System.out.println(ps.getAll());
            //ps.delete(publication);
            //System.out.println("Publication deleted");
            //System.out.println(ps.getAll());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}