/*package org.example;

import Services.*;
import entities.*;

import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ServiceBus serviceBus = new ServiceBus();
        ServiceTrain serviceTrain = new ServiceTrain();
        ServiceMetro serviceMetro = new ServiceMetro();
        ServiceTaxi serviceTaxi = new ServiceTaxi();
        ServiceScooter serviceScooter = new ServiceScooter();
        ServiceReclamation serviceReclamation = new ServiceReclamation();


//tester Bus

        Bus newBus = new Bus(0, "123ABC");
        serviceBus.ajouter(newBus);

        System.out.println("🔎 Liste des bus :");
        serviceBus.getAll().forEach(bus -> System.out.println(bus.getIdBus() + " - " + bus.getNumeroBus()));

        Bus modifBus = new Bus(8, "999XYZ");
        serviceBus.modifier(modifBus);
        System.out.println("🔎 Bus ID 6 : " + serviceBus.getOne(6));

        serviceBus.supprimer(5);

//tester train

        Train newTrain = new Train(0, "T123XYZ");
        serviceTrain.ajouter(newTrain);

        System.out.println("🔎 Liste des trains :");
        serviceTrain.getAll().forEach(train ->
                System.out.println(train.getIdTrain() + " - " + train.getNumeroTrain())
        );

        Train modifTrain = new Train(1, "T999ABC");
        serviceTrain.modifier(modifTrain);
        System.out.println("🔎 Train ID 1 : " + serviceTrain.getOne(1));

        serviceTrain.supprimer(1);


//tester train
        Metro newMetro = new Metro(0, "M456DE6");
        serviceMetro.ajouter(newMetro);

        System.out.println("🔎 Liste des métros :");
        serviceMetro.getAll().forEach(metro ->
                System.out.println(metro.getIdMetro() + " - " + metro.getNumeroMetro())
        );

        Metro modifMetro = new Metro(1, "M777GHI");
        serviceMetro.modifier(modifMetro);

        System.out.println("🔎 Métro ID 1 : " + serviceMetro.getOne(1));

        serviceMetro.supprimer(2);


//tester taxi

        Taxi newTaxi = new Taxi(0, "Tt123", "1345", "Jean", "Dupont");
        serviceTaxi.ajouter(newTaxi);

        System.out.println("🔎 Liste des taxis :");
        serviceTaxi.getAll().forEach(taxi ->
                System.out.println(taxi.getIdTaxi() + " - " + taxi.getNumeroTaxi() + " - " + taxi.getPrenomChauffeur() + " " + taxi.getNomChauffeur())
        );

        Taxi modifTaxi = new Taxi(2, "TX999", "67890", "Pierre", "Durand");
        serviceTaxi.modifier(modifTaxi);
        System.out.println("🔎 Taxi ID 2 : " + serviceTaxi.getOne(2));

        serviceTaxi.supprimer(1);


//tester Scooter

        Scooter newScooter = new Scooter(0, "SCO12", "Manouba");
        serviceScooter.ajouter(newScooter);

        System.out.println("🔎 Liste des scooters :");
        serviceScooter.getAll().forEach(scooter ->
                System.out.println(scooter.getIdScooter() + " - " + scooter.getNumeroScooter() + " - " + scooter.getLocalisationScooter())
        );

        Scooter modifScooter = new Scooter(2 ,"SCO999", "Ariena");
        serviceScooter.modifier(modifScooter);

        System.out.println("🔎 Scooter ID 2 : " + serviceScooter.getOne(2));

        serviceScooter.supprimer(3);



        //////Reclamation

        Reclamation newReclamation = new Reclamation(0, "Problème de Service", "Le service ne fonctionne pas correctement", "En attente", new Date());
        serviceReclamation.ajouter(newReclamation);

        System.out.println("🔎 Liste des réclamations :");
        serviceReclamation.getAll().forEach(reclamation ->
                System.out.println(reclamation.getIdReclamation() + " - " + reclamation.getTypeReclamation() + " - " +
                        reclamation.getDescriptionReclamation() + " - " + reclamation.getStatutReclamation() + " - " + reclamation.getDateReclamation())
        );

        Reclamation modifReclamation = new Reclamation(1, "problème de connexion", "Connexion impossible sur l'application", "En cours", new Date());
        serviceReclamation.modifier(modifReclamation);
        System.out.println("🔎 Réclamation ID 1 : " + serviceReclamation.getOne(1));

        serviceReclamation.supprimer(2);

        List<Reclamation> reclamations = serviceReclamation.getAll ();

        // Afficher les réclamations et les informations de l'utilisateur associé
        for (Reclamation reclamation : reclamations) {
            System.out.println("Réclamation ID: " + reclamation.getIdReclamation());
            System.out.println("Type: " + reclamation.getTypeReclamation());
            System.out.println("Description: " + reclamation.getDescriptionReclamation());
            System.out.println("Statut: " + reclamation.getStatutReclamation());
            System.out.println("Date: " + reclamation.getDateReclamation());
            System.out.println("Utilisateur: " + reclamation.getUtilisateur().getNomUtilisateur() + " " +
                    reclamation.getUtilisateur().getPrenomUtilisateur() + " - Email: " +
                    reclamation.getUtilisateur().getEmailUtilisateur());
            System.out.println("--------------------------");
        }
    }
}
*/

package org.example;

import Services.*;
import entities.*;

import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ServiceBus serviceBus = new ServiceBus();
        ServiceTrain serviceTrain = new ServiceTrain();
        ServiceMetro serviceMetro = new ServiceMetro();
        ServiceTaxi serviceTaxi = new ServiceTaxi();
        ServiceScooter serviceScooter = new ServiceScooter();
        ServiceReclamation serviceReclamation = new ServiceReclamation();

        // Tester Bus
        Bus newBus = new Bus(0, "123ABC");
        serviceBus.ajouter(newBus);

        System.out.println("🔎 Liste des bus :");
        serviceBus.getAll().forEach(bus -> System.out.println(bus.getIdBus() + " - " + bus.getNumeroBus()));

        Bus modifBus = new Bus(8, "999XYZ");
        serviceBus.modifier(modifBus);

        // Vérification si `getOne(int)` existe bien dans `ServiceBus`
        Bus bus = serviceBus.getOne(6);
        if (bus != null) {
            System.out.println("🔎 Bus ID 6 : " + bus.getNumeroBus());
        } else {
            System.out.println("⚠️ Bus ID 6 introuvable !");
        }

        serviceBus.supprimer(5);

        // Tester Train
        Train newTrain = new Train(0, "T123XYZ");
        serviceTrain.ajouter(newTrain);

        System.out.println("🔎 Liste des trains :");
        serviceTrain.getAll().forEach(train ->
                System.out.println(train.getIdTrain() + " - " + train.getNumeroTrain())
        );

        Train modifTrain = new Train(1, "T999ABC");
        serviceTrain.modifier(modifTrain);

        Train train = serviceTrain.getOne(1);
        if (train != null) {
            System.out.println("🔎 Train ID 1 : " + train.getNumeroTrain());
        } else {
            System.out.println("⚠️ Train ID 1 introuvable !");
        }

        serviceTrain.supprimer(1);

        // Tester Metro
        Metro newMetro = new Metro(0, "M456DE6");
        serviceMetro.ajouter(newMetro);

        System.out.println("🔎 Liste des métros :");
        serviceMetro.getAll().forEach(metro ->
                System.out.println(metro.getIdMetro() + " - " + metro.getNumeroMetro())
        );

        Metro modifMetro = new Metro(1, "M777GHI");
        serviceMetro.modifier(modifMetro);

        Metro metro = serviceMetro.getOne(1);
        if (metro != null) {
            System.out.println("🔎 Métro ID 1 : " + metro.getNumeroMetro());
        } else {
            System.out.println("⚠️ Métro ID 1 introuvable !");
        }

        serviceMetro.supprimer(2);

        // Tester Taxi
        Taxi newTaxi = new Taxi(0, "Tt123", "1345", "Jean", "Dupont");
        serviceTaxi.ajouter(newTaxi);

        System.out.println("🔎 Liste des taxis :");
        serviceTaxi.getAll().forEach(taxi ->
                System.out.println(taxi.getIdTaxi() + " - " + taxi.getNumeroTaxi() + " - " + taxi.getPrenomChauffeur() + " " + taxi.getNomChauffeur())
        );

        Taxi modifTaxi = new Taxi(2, "TX999", "67890", "Pierre", "Durand");
        serviceTaxi.modifier(modifTaxi);

        Taxi taxi = serviceTaxi.getOne(2);
        if (taxi != null) {
            System.out.println("🔎 Taxi ID 2 : " + taxi.getNumeroTaxi());
        } else {
            System.out.println("⚠️ Taxi ID 2 introuvable !");
        }

        serviceTaxi.supprimer(1);

        // Tester Scooter
        Scooter newScooter = new Scooter(0, "SCO12", "Manouba");
        serviceScooter.ajouter(newScooter);

        System.out.println("🔎 Liste des scooters :");
        serviceScooter.getAll().forEach(scooter ->
                System.out.println(scooter.getIdScooter() + " - " + scooter.getNumeroScooter() + " - " + scooter.getLocalisationScooter())
        );

        Scooter modifScooter = new Scooter(2, "SCO999", "Ariena");
        serviceScooter.modifier(modifScooter);

        Scooter scooter = serviceScooter.getOne(2);
        if (scooter != null) {
            System.out.println("🔎 Scooter ID 2 : " + scooter.getNumeroScooter());
        } else {
            System.out.println("⚠️ Scooter ID 2 introuvable !");
        }

        serviceScooter.supprimer(3);

        // Tester Reclamation
        //  Reclamation newReclamation = new Reclamation(0, "Problème de Service", "Le service ne fonctionne pas correctement", "En attente", new Date());
        //  serviceReclamation.ajouter(newReclamation);

        System.out.println("🔎 Liste des réclamations :");
        serviceReclamation.getAll().forEach(reclamation ->
                System.out.println(reclamation.getIdReclamation() + " - " + reclamation.getTypeReclamation() + " - " +
                        reclamation.getDescriptionReclamation() + " - " + reclamation.getStatutReclamation() + " - " + reclamation.getDateReclamation())
        );

        // Reclamation modifReclamation = new Reclamation(1, "problème de connexion", "Connexion impossible sur l'application", "En cours", new Date());
        // serviceReclamation.modifier(modifReclamation);

        Reclamation reclamation = serviceReclamation.getOne(1);
        if (reclamation != null) {
            System.out.println("🔎 Réclamation ID 1 : " + reclamation.getTypeReclamation());
        } else {
            System.out.println("⚠️ Réclamation ID 1 introuvable !");
        }

        serviceReclamation.supprimer(2);

        // Afficher les réclamations avec les informations de l'utilisateur
        List<Reclamation> reclamations = serviceReclamation.getAll();

        for (Reclamation rec : reclamations) {
            System.out.println("Réclamation ID: " + rec.getIdReclamation());
            System.out.println("Type: " + rec.getTypeReclamation());
            System.out.println("Description: " + rec.getDescriptionReclamation());
            System.out.println("Statut: " + rec.getStatutReclamation());
            System.out.println("Date: " + rec.getDateReclamation());

            if (rec.getUtilisateur() != null) {  // Éviter NullPointerException
                System.out.println("Utilisateur: " + rec.getUtilisateur().getNomUtilisateur() + " " +
                        rec.getUtilisateur().getPrenomUtilisateur() + " - Email: " +
                        rec.getUtilisateur().getEmailUtilisateur());
            } else {
                System.out.println("⚠️ Aucun utilisateur associé !");
            }
            System.out.println("--------------------------");
        }
    }
}
