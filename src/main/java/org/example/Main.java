package org.example;

import models.Commentaire;
import models.Likes;
import models.Publication;
import services.CommentaireService;
import services.LikesService;
import services.PublicationService;
import utils.MyDB;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
 /*
        //Test Publication
        PublicationService ps = new PublicationService();
        try {
            Publication publication = new Publication(1, "contenu 3", "description 3 ", "title 3", "image");
            ps.create(publication);
            System.out.println("Publication created");
            //ps.update(publication);
            //System.out.println("Publication updated");
            //System.out.println(ps.getAll());
            //ps.delete(publication);
            //System.out.println("Publication deleted");
            //System.out.println(ps.getAll());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
 */

        //Test Commentaire
        CommentaireService cs = new CommentaireService();
        try {
            Commentaire commentaire = new Commentaire(1,1, 3, "commentaire 1 modifie","");
            //cs.create(commentaire);
            //System.out.println("Commentaire created");
            //cs.update(commentaire);
            //System.out.println("Commentaire updated");
            //System.out.println(cs.getAll());
            cs.delete(commentaire);
            System.out.println("Commentaire deleted");
            System.out.println(cs.getAll());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


/*
        //Test Likes
        LikesService ls = new LikesService();
        try {
            Likes l = new Likes(1,3,1,"");
            //ls.create(l);
            //System.out.println("Like ajoute");
            //ls.update(l);
            //System.out.println("Like modifie");
            ls.delete(l);
            System.out.println(ls.getAll());
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

*/


    }
}