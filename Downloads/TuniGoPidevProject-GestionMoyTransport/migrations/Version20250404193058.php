<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20250404193058 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('CREATE TABLE messenger_messages (id BIGINT AUTO_INCREMENT NOT NULL, body LONGTEXT NOT NULL, headers LONGTEXT NOT NULL, queue_name VARCHAR(190) NOT NULL, created_at DATETIME NOT NULL COMMENT \'(DC2Type:datetime_immutable)\', available_at DATETIME NOT NULL COMMENT \'(DC2Type:datetime_immutable)\', delivered_at DATETIME DEFAULT NULL COMMENT \'(DC2Type:datetime_immutable)\', INDEX IDX_75EA56E0FB7336F0 (queue_name), INDEX IDX_75EA56E0E3BD61CE (available_at), INDEX IDX_75EA56E016BA31DB (delivered_at), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('ALTER TABLE bus MODIFY idBus INT NOT NULL');
        $this->addSql('DROP INDEX `primary` ON bus');
        $this->addSql('ALTER TABLE bus ADD numero_bus VARCHAR(255) NOT NULL, DROP numeroBus, CHANGE idBus id_bus INT AUTO_INCREMENT NOT NULL, CHANGE idTrajetBus id_trajet_bus INT DEFAULT NULL');
        $this->addSql('ALTER TABLE bus ADD PRIMARY KEY (id_bus)');
        $this->addSql('ALTER TABLE bus_trajet ADD station_depart VARCHAR(255) NOT NULL, ADD station_arrivee VARCHAR(255) NOT NULL, ADD heure_depart VARCHAR(255) NOT NULL, ADD heure_arrivee VARCHAR(255) NOT NULL, ADD numero_bus VARCHAR(255) NOT NULL, DROP stationDepart, DROP stationArrivee, DROP heureDepart, DROP heureArrivee, DROP numeroBus');
        $this->addSql('ALTER TABLE metro MODIFY idMetro INT NOT NULL');
        $this->addSql('DROP INDEX `primary` ON metro');
        $this->addSql('ALTER TABLE metro ADD numero_metro VARCHAR(255) NOT NULL, DROP numeroMetro, CHANGE idMetro id_metro INT AUTO_INCREMENT NOT NULL, CHANGE idTrajetMetro id_trajet_metro INT DEFAULT NULL');
        $this->addSql('ALTER TABLE metro ADD PRIMARY KEY (id_metro)');
        $this->addSql('ALTER TABLE metro_trajet ADD gare_depart VARCHAR(255) NOT NULL, ADD gare_arrivee VARCHAR(255) NOT NULL, ADD heure_depart TIME NOT NULL, ADD heure_arrivee TIME NOT NULL, DROP gareDepart, DROP gareArrivee, DROP heureDepart, DROP heureArrivee');
        $this->addSql('ALTER TABLE parking MODIFY idParking INT NOT NULL');
        $this->addSql('DROP INDEX `primary` ON parking');
        $this->addSql('ALTER TABLE parking ADD nom_parking VARCHAR(255) DEFAULT NULL, ADD localisation_parking VARCHAR(255) DEFAULT NULL, DROP nomParking, DROP localisationParking, CHANGE idParking id_parking INT AUTO_INCREMENT NOT NULL');
        $this->addSql('ALTER TABLE parking ADD PRIMARY KEY (id_parking)');
        $this->addSql('ALTER TABLE reclamation MODIFY idReclamation INT NOT NULL');
        $this->addSql('ALTER TABLE reclamation DROP FOREIGN KEY reclamation_ibfk_1');
        $this->addSql('DROP INDEX `primary` ON reclamation');
        $this->addSql('ALTER TABLE reclamation DROP FOREIGN KEY reclamation_ibfk_1');
        $this->addSql('ALTER TABLE reclamation ADD type_reclamation VARCHAR(255) NOT NULL, ADD description_reclamation VARCHAR(255) NOT NULL, ADD statut_reclamation VARCHAR(255) NOT NULL, DROP typeReclamation, DROP descriptionReclamation, DROP statutReclamation, CHANGE idReclamation id_reclamation INT AUTO_INCREMENT NOT NULL, CHANGE dateReclamation date_reclamation DATE NOT NULL');
        $this->addSql('ALTER TABLE reclamation ADD CONSTRAINT FK_CE6064045D419CCB FOREIGN KEY (idUtilisateur) REFERENCES utilisateur (idUtilisateur)');
        $this->addSql('ALTER TABLE reclamation ADD PRIMARY KEY (id_reclamation)');
        $this->addSql('DROP INDEX reclamation_ibfk_1 ON reclamation');
        $this->addSql('CREATE INDEX IDX_CE6064045D419CCB ON reclamation (idUtilisateur)');
        $this->addSql('ALTER TABLE reclamation ADD CONSTRAINT reclamation_ibfk_1 FOREIGN KEY (idUtilisateur) REFERENCES utilisateur (idUtilisateur) ON UPDATE CASCADE ON DELETE CASCADE');
        $this->addSql('ALTER TABLE reservation MODIFY idRes INT NOT NULL');
        $this->addSql('DROP INDEX `primary` ON reservation');
        $this->addSql('ALTER TABLE reservation ADD nom_user VARCHAR(255) NOT NULL, ADD moyen_paiement VARCHAR(255) NOT NULL, ADD confirmation_code VARCHAR(255) NOT NULL, DROP nomUser, DROP moyenPaiement, DROP confirmationCode, CHANGE moyen moyen VARCHAR(255) NOT NULL, CHANGE idRes id_res INT AUTO_INCREMENT NOT NULL, CHANGE dateRes date_res DATE NOT NULL');
        $this->addSql('ALTER TABLE reservation ADD PRIMARY KEY (id_res)');
        $this->addSql('ALTER TABLE reservation_parking MODIFY idReservation INT NOT NULL');
        $this->addSql('DROP INDEX `primary` ON reservation_parking');
        $this->addSql('ALTER TABLE reservation_parking ADD id_trajet_train INT DEFAULT NULL, ADD id_trajet_metro INT DEFAULT NULL, ADD id_trajet_bus INT DEFAULT NULL, ADD id_scooter INT DEFAULT NULL, ADD id_taxi INT DEFAULT NULL, ADD id_parking INT DEFAULT NULL, DROP idTrajetTrain, DROP idTrajetMetro, DROP idTrajetBus, DROP idScooter, DROP idTaxi, DROP idParking, CHANGE idReservation id_reservation INT AUTO_INCREMENT NOT NULL, CHANGE dateReservation date_reservation DATETIME NOT NULL, CHANGE idUtilisateur id_utilisateur INT NOT NULL');
        $this->addSql('ALTER TABLE reservation_parking ADD PRIMARY KEY (id_reservation)');
        $this->addSql('ALTER TABLE scooter MODIFY idScooter INT NOT NULL');
        $this->addSql('DROP INDEX `primary` ON scooter');
        $this->addSql('ALTER TABLE scooter ADD numero_scooter VARCHAR(255) NOT NULL, ADD localisation_scooter VARCHAR(255) NOT NULL, ADD temps_reservation DATETIME NOT NULL, ADD temps_arrivee DATETIME NOT NULL, DROP numeroScooter, DROP localisationScooter, DROP tempsReservation, DROP tempsArrivee, CHANGE Isdisponible isdisponible TINYINT(1) NOT NULL, CHANGE idScooter id_scooter INT AUTO_INCREMENT NOT NULL, CHANGE idReservation id_reservation INT DEFAULT NULL');
        $this->addSql('ALTER TABLE scooter ADD PRIMARY KEY (id_scooter)');
        $this->addSql('ALTER TABLE taxi MODIFY idTaxi INT NOT NULL');
        $this->addSql('DROP INDEX `primary` ON taxi');
        $this->addSql('ALTER TABLE taxi ADD numero_taxi VARCHAR(255) NOT NULL, ADD numero_chauffeur VARCHAR(255) NOT NULL, ADD prenom_chauffeur VARCHAR(255) NOT NULL, ADD nom_chauffeur VARCHAR(255) NOT NULL, DROP numeroTaxi, DROP numeroChauffeur, DROP prenomChauffeur, DROP nomChauffeur, CHANGE Isdisponible isdisponible TINYINT(1) NOT NULL, CHANGE idTaxi id_taxi INT AUTO_INCREMENT NOT NULL, CHANGE idReservation id_reservation INT DEFAULT NULL');
        $this->addSql('ALTER TABLE taxi ADD PRIMARY KEY (id_taxi)');
        $this->addSql('ALTER TABLE train MODIFY idTrain INT NOT NULL');
        $this->addSql('DROP INDEX `primary` ON train');
        $this->addSql('ALTER TABLE train ADD numero_train VARCHAR(255) NOT NULL, DROP numeroTrain, CHANGE idTrain id_train INT AUTO_INCREMENT NOT NULL, CHANGE idTrajetTrain id_trajet_train INT DEFAULT NULL');
        $this->addSql('ALTER TABLE train ADD PRIMARY KEY (id_train)');
        $this->addSql('ALTER TABLE train_trajet CHANGE heure_depart heure_depart VARCHAR(255) NOT NULL, CHANGE heure_arrivee heure_arrivee VARCHAR(255) NOT NULL, CHANGE numero_train numero_train VARCHAR(255) NOT NULL');
        $this->addSql('ALTER TABLE trajetbus MODIFY idTrajetBus INT NOT NULL');
        $this->addSql('DROP INDEX trajetbus_ibfk_1 ON trajetbus');
        $this->addSql('DROP INDEX trajetbus_ibfk_2 ON trajetbus');
        $this->addSql('DROP INDEX `primary` ON trajetbus');
        $this->addSql('ALTER TABLE trajetbus ADD depart_trajetbus VARCHAR(255) NOT NULL, ADD arrive_trajet_bus VARCHAR(255) NOT NULL, ADD heur_depart_bus VARCHAR(255) NOT NULL, ADD heur_arrive_bus VARCHAR(255) NOT NULL, ADD prix_ticket_bus NUMERIC(10, 0) NOT NULL, ADD id_bus INT NOT NULL, ADD id_reservation INT NOT NULL, DROP departTrajetbus, DROP arriveTrajetBus, DROP heurDepartBus, DROP heurArriveBus, DROP prixTicketBus, DROP idBus, DROP idReservation, CHANGE idTrajetBus id_trajet_bus INT AUTO_INCREMENT NOT NULL');
        $this->addSql('ALTER TABLE trajetbus ADD PRIMARY KEY (id_trajet_bus)');
        $this->addSql('ALTER TABLE trajetmetro MODIFY idTrajetMetro INT NOT NULL');
        $this->addSql('DROP INDEX idReservation ON trajetmetro');
        $this->addSql('DROP INDEX idMetro ON trajetmetro');
        $this->addSql('DROP INDEX `primary` ON trajetmetro');
        $this->addSql('ALTER TABLE trajetmetro ADD depart_trajet_metro VARCHAR(255) NOT NULL, ADD arrive_trajet_metro VARCHAR(255) NOT NULL, ADD heur_depart_metro VARCHAR(255) NOT NULL, ADD heur_arrive_metro VARCHAR(255) NOT NULL, ADD prix_ticket_metro NUMERIC(10, 0) NOT NULL, ADD id_metro INT NOT NULL, ADD id_reservation INT NOT NULL, DROP departTrajetMetro, DROP arriveTrajetMetro, DROP heurDepartMetro, DROP heurArriveMetro, DROP prixTicketMetro, DROP idMetro, DROP idReservation, CHANGE idTrajetMetro id_trajet_metro INT AUTO_INCREMENT NOT NULL');
        $this->addSql('ALTER TABLE trajetmetro ADD PRIMARY KEY (id_trajet_metro)');
        $this->addSql('ALTER TABLE trajettrain MODIFY idTrajetTrain INT NOT NULL');
        $this->addSql('DROP INDEX idTrain ON trajettrain');
        $this->addSql('DROP INDEX idReservation ON trajettrain');
        $this->addSql('DROP INDEX `primary` ON trajettrain');
        $this->addSql('ALTER TABLE trajettrain ADD depart_trajet_train VARCHAR(255) NOT NULL, ADD arrive_trajet_train VARCHAR(255) NOT NULL, ADD heur_depart_train VARCHAR(255) NOT NULL, ADD heur_arrive_train VARCHAR(255) NOT NULL, ADD prix_ticket_train NUMERIC(10, 0) NOT NULL, ADD id_train INT NOT NULL, ADD id_reservation INT NOT NULL, DROP departTrajetTrain, DROP arriveTrajetTrain, DROP heurDepartTrain, DROP heurArriveTrain, DROP prixTicketTrain, DROP idTrain, DROP idReservation, CHANGE idTrajetTrain id_trajet_train INT AUTO_INCREMENT NOT NULL');
        $this->addSql('ALTER TABLE trajettrain ADD PRIMARY KEY (id_trajet_train)');
        $this->addSql('ALTER TABLE utilisateur MODIFY idUtilisateur INT NOT NULL');
        $this->addSql('DROP INDEX `primary` ON utilisateur');
        $this->addSql('ALTER TABLE utilisateur ADD nom_utilisateur VARCHAR(255) NOT NULL, ADD prenom_utilisateur VARCHAR(255) NOT NULL, ADD email_utilisateur VARCHAR(255) NOT NULL, ADD mot_de_passe_utilisateur VARCHAR(255) NOT NULL, ADD numero_telephone_utilisateur VARCHAR(255) NOT NULL, ADD role_utilisateur VARCHAR(255) NOT NULL, ADD question_securite VARCHAR(255) NOT NULL, ADD reponse_securite VARCHAR(255) NOT NULL, DROP nomUtilisateur, DROP prenomUtilisateur, DROP emailUtilisateur, DROP motDePasseUtilisateur, DROP numeroTelephoneUtilisateur, DROP roleUtilisateur, DROP questionSecurite, DROP reponseSecurite, CHANGE idUtilisateur id_utilisateur INT AUTO_INCREMENT NOT NULL');
        $this->addSql('ALTER TABLE utilisateur ADD PRIMARY KEY (id_utilisateur)');
        $this->addSql('ALTER TABLE utilisateur MODIFY idUtilisateur INT NOT NULL');
        $this->addSql('DROP INDEX `primary` ON utilisateur');
        $this->addSql('ALTER TABLE utilisateur ADD nom_utilisateur VARCHAR(255) NOT NULL, ADD prenom_utilisateur VARCHAR(255) NOT NULL, ADD email_utilisateur VARCHAR(255) NOT NULL, ADD mot_de_passe_utilisateur VARCHAR(255) NOT NULL, ADD numero_telephone_utilisateur VARCHAR(255) NOT NULL, ADD role_utilisateur VARCHAR(255) NOT NULL, DROP nomUtilisateur, DROP prenomUtilisateur, DROP emailUtilisateur, DROP motDePasseUtilisateur, DROP numeroTelephoneUtilisateur, DROP roleUtilisateur, CHANGE idUtilisateur id_utilisateur INT AUTO_INCREMENT NOT NULL');
        $this->addSql('ALTER TABLE utilisateur ADD PRIMARY KEY (id_utilisateur)');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('DROP TABLE messenger_messages');
        $this->addSql('ALTER TABLE bus MODIFY id_bus INT NOT NULL');
        $this->addSql('DROP INDEX `PRIMARY` ON bus');
        $this->addSql('ALTER TABLE bus ADD numeroBus VARCHAR(20) NOT NULL, DROP numero_bus, CHANGE id_bus idBus INT AUTO_INCREMENT NOT NULL, CHANGE id_trajet_bus idTrajetBus INT DEFAULT NULL');
        $this->addSql('ALTER TABLE bus ADD PRIMARY KEY (idBus)');
        $this->addSql('ALTER TABLE bus_trajet ADD stationDepart VARCHAR(255) NOT NULL, ADD stationArrivee VARCHAR(255) NOT NULL, ADD heureDepart VARCHAR(5) NOT NULL, ADD heureArrivee VARCHAR(5) NOT NULL, ADD numeroBus VARCHAR(50) NOT NULL, DROP station_depart, DROP station_arrivee, DROP heure_depart, DROP heure_arrivee, DROP numero_bus');
        $this->addSql('ALTER TABLE metro MODIFY id_metro INT NOT NULL');
        $this->addSql('DROP INDEX `PRIMARY` ON metro');
        $this->addSql('ALTER TABLE metro ADD numeroMetro VARCHAR(20) NOT NULL, DROP numero_metro, CHANGE id_metro idMetro INT AUTO_INCREMENT NOT NULL, CHANGE id_trajet_metro idTrajetMetro INT DEFAULT NULL');
        $this->addSql('ALTER TABLE metro ADD PRIMARY KEY (idMetro)');
        $this->addSql('ALTER TABLE metro_trajet ADD gareDepart VARCHAR(255) NOT NULL, ADD gareArrivee VARCHAR(255) NOT NULL, ADD heureDepart TIME NOT NULL, ADD heureArrivee TIME NOT NULL, DROP gare_depart, DROP gare_arrivee, DROP heure_depart, DROP heure_arrivee');
        $this->addSql('ALTER TABLE parking MODIFY id_parking INT NOT NULL');
        $this->addSql('DROP INDEX `PRIMARY` ON parking');
        $this->addSql('ALTER TABLE parking ADD nomParking VARCHAR(255) DEFAULT NULL, ADD localisationParking VARCHAR(255) DEFAULT NULL, DROP nom_parking, DROP localisation_parking, CHANGE id_parking idParking INT AUTO_INCREMENT NOT NULL');
        $this->addSql('ALTER TABLE parking ADD PRIMARY KEY (idParking)');
        $this->addSql('ALTER TABLE reclamation MODIFY id_reclamation INT NOT NULL');
        $this->addSql('ALTER TABLE reclamation DROP FOREIGN KEY FK_CE6064045D419CCB');
        $this->addSql('DROP INDEX `PRIMARY` ON reclamation');
        $this->addSql('ALTER TABLE reclamation DROP FOREIGN KEY FK_CE6064045D419CCB');
        $this->addSql('ALTER TABLE reclamation ADD typeReclamation VARCHAR(50) NOT NULL, ADD descriptionReclamation VARCHAR(50) NOT NULL, ADD statutReclamation VARCHAR(50) NOT NULL, DROP type_reclamation, DROP description_reclamation, DROP statut_reclamation, CHANGE id_reclamation idReclamation INT AUTO_INCREMENT NOT NULL, CHANGE date_reclamation dateReclamation DATE NOT NULL');
        $this->addSql('ALTER TABLE reclamation ADD CONSTRAINT reclamation_ibfk_1 FOREIGN KEY (idUtilisateur) REFERENCES utilisateur (idUtilisateur) ON UPDATE CASCADE ON DELETE CASCADE');
        $this->addSql('ALTER TABLE reclamation ADD PRIMARY KEY (idReclamation)');
        $this->addSql('DROP INDEX idx_ce6064045d419ccb ON reclamation');
        $this->addSql('CREATE INDEX reclamation_ibfk_1 ON reclamation (idUtilisateur)');
        $this->addSql('ALTER TABLE reclamation ADD CONSTRAINT FK_CE6064045D419CCB FOREIGN KEY (idUtilisateur) REFERENCES utilisateur (idUtilisateur)');
        $this->addSql('ALTER TABLE reservation MODIFY id_res INT NOT NULL');
        $this->addSql('DROP INDEX `PRIMARY` ON reservation');
        $this->addSql('ALTER TABLE reservation ADD nomUser VARCHAR(255) NOT NULL, ADD moyenPaiement VARCHAR(50) NOT NULL, ADD confirmationCode VARCHAR(255) NOT NULL, DROP nom_user, DROP moyen_paiement, DROP confirmation_code, CHANGE moyen moyen VARCHAR(50) NOT NULL, CHANGE id_res idRes INT AUTO_INCREMENT NOT NULL, CHANGE date_res dateRes DATE NOT NULL');
        $this->addSql('ALTER TABLE reservation ADD PRIMARY KEY (idRes)');
        $this->addSql('ALTER TABLE reservation_parking MODIFY id_reservation INT NOT NULL');
        $this->addSql('DROP INDEX `PRIMARY` ON reservation_parking');
        $this->addSql('ALTER TABLE reservation_parking ADD idTrajetTrain INT DEFAULT NULL, ADD idTrajetMetro INT DEFAULT NULL, ADD idTrajetBus INT DEFAULT NULL, ADD idScooter INT DEFAULT NULL, ADD idTaxi INT DEFAULT NULL, ADD idParking INT DEFAULT NULL, DROP id_trajet_train, DROP id_trajet_metro, DROP id_trajet_bus, DROP id_scooter, DROP id_taxi, DROP id_parking, CHANGE id_reservation idReservation INT AUTO_INCREMENT NOT NULL, CHANGE date_reservation dateReservation DATETIME NOT NULL, CHANGE id_utilisateur idUtilisateur INT NOT NULL');
        $this->addSql('ALTER TABLE reservation_parking ADD PRIMARY KEY (idReservation)');
        $this->addSql('ALTER TABLE scooter MODIFY id_scooter INT NOT NULL');
        $this->addSql('DROP INDEX `PRIMARY` ON scooter');
        $this->addSql('ALTER TABLE scooter ADD numeroScooter VARCHAR(25) NOT NULL, ADD localisationScooter VARCHAR(25) NOT NULL, ADD tempsReservation DATETIME DEFAULT \'current_timestamp(6)\' NOT NULL, ADD tempsArrivee DATETIME DEFAULT \'current_timestamp(6)\' NOT NULL, DROP numero_scooter, DROP localisation_scooter, DROP temps_reservation, DROP temps_arrivee, CHANGE isdisponible Isdisponible TINYINT(1) DEFAULT 1 NOT NULL, CHANGE id_scooter idScooter INT AUTO_INCREMENT NOT NULL, CHANGE id_reservation idReservation INT DEFAULT NULL');
        $this->addSql('ALTER TABLE scooter ADD PRIMARY KEY (idScooter)');
        $this->addSql('ALTER TABLE taxi MODIFY id_taxi INT NOT NULL');
        $this->addSql('DROP INDEX `PRIMARY` ON taxi');
        $this->addSql('ALTER TABLE taxi ADD numeroTaxi VARCHAR(25) NOT NULL, ADD numeroChauffeur VARCHAR(25) NOT NULL, ADD prenomChauffeur VARCHAR(25) NOT NULL, ADD nomChauffeur VARCHAR(25) NOT NULL, DROP numero_taxi, DROP numero_chauffeur, DROP prenom_chauffeur, DROP nom_chauffeur, CHANGE isdisponible Isdisponible TINYINT(1) DEFAULT 1 NOT NULL, CHANGE id_taxi idTaxi INT AUTO_INCREMENT NOT NULL, CHANGE id_reservation idReservation INT DEFAULT NULL');
        $this->addSql('ALTER TABLE taxi ADD PRIMARY KEY (idTaxi)');
        $this->addSql('ALTER TABLE train MODIFY id_train INT NOT NULL');
        $this->addSql('DROP INDEX `PRIMARY` ON train');
        $this->addSql('ALTER TABLE train ADD numeroTrain VARCHAR(20) NOT NULL, DROP numero_train, CHANGE id_train idTrain INT AUTO_INCREMENT NOT NULL, CHANGE id_trajet_train idTrajetTrain INT DEFAULT NULL');
        $this->addSql('ALTER TABLE train ADD PRIMARY KEY (idTrain)');
        $this->addSql('ALTER TABLE train_trajet CHANGE heure_depart heure_depart VARCHAR(5) NOT NULL, CHANGE heure_arrivee heure_arrivee VARCHAR(5) NOT NULL, CHANGE numero_train numero_train VARCHAR(50) NOT NULL');
        $this->addSql('ALTER TABLE trajetbus MODIFY id_trajet_bus INT NOT NULL');
        $this->addSql('DROP INDEX `PRIMARY` ON trajetbus');
        $this->addSql('ALTER TABLE trajetbus ADD departTrajetbus VARCHAR(25) NOT NULL, ADD arriveTrajetBus VARCHAR(25) NOT NULL, ADD heurDepartBus VARCHAR(25) NOT NULL, ADD heurArriveBus VARCHAR(25) NOT NULL, ADD prixTicketBus DOUBLE PRECISION NOT NULL, ADD idBus INT NOT NULL, ADD idReservation INT NOT NULL, DROP depart_trajetbus, DROP arrive_trajet_bus, DROP heur_depart_bus, DROP heur_arrive_bus, DROP prix_ticket_bus, DROP id_bus, DROP id_reservation, CHANGE id_trajet_bus idTrajetBus INT AUTO_INCREMENT NOT NULL');
        $this->addSql('CREATE INDEX trajetbus_ibfk_1 ON trajetbus (idBus)');
        $this->addSql('CREATE INDEX trajetbus_ibfk_2 ON trajetbus (idReservation)');
        $this->addSql('ALTER TABLE trajetbus ADD PRIMARY KEY (idTrajetBus)');
        $this->addSql('ALTER TABLE trajetmetro MODIFY id_trajet_metro INT NOT NULL');
        $this->addSql('DROP INDEX `PRIMARY` ON trajetmetro');
        $this->addSql('ALTER TABLE trajetmetro ADD departTrajetMetro VARCHAR(25) NOT NULL, ADD arriveTrajetMetro VARCHAR(25) NOT NULL, ADD heurDepartMetro VARCHAR(25) NOT NULL, ADD heurArriveMetro VARCHAR(25) NOT NULL, ADD prixTicketMetro DOUBLE PRECISION NOT NULL, ADD idMetro INT NOT NULL, ADD idReservation INT NOT NULL, DROP depart_trajet_metro, DROP arrive_trajet_metro, DROP heur_depart_metro, DROP heur_arrive_metro, DROP prix_ticket_metro, DROP id_metro, DROP id_reservation, CHANGE id_trajet_metro idTrajetMetro INT AUTO_INCREMENT NOT NULL');
        $this->addSql('CREATE INDEX idReservation ON trajetmetro (idReservation)');
        $this->addSql('CREATE INDEX idMetro ON trajetmetro (idMetro)');
        $this->addSql('ALTER TABLE trajetmetro ADD PRIMARY KEY (idTrajetMetro)');
        $this->addSql('ALTER TABLE trajettrain MODIFY id_trajet_train INT NOT NULL');
        $this->addSql('DROP INDEX `PRIMARY` ON trajettrain');
        $this->addSql('ALTER TABLE trajettrain ADD departTrajetTrain VARCHAR(25) NOT NULL, ADD arriveTrajetTrain VARCHAR(25) NOT NULL, ADD heurDepartTrain VARCHAR(25) NOT NULL, ADD heurArriveTrain VARCHAR(25) NOT NULL, ADD prixTicketTrain DOUBLE PRECISION NOT NULL, ADD idTrain INT NOT NULL, ADD idReservation INT NOT NULL, DROP depart_trajet_train, DROP arrive_trajet_train, DROP heur_depart_train, DROP heur_arrive_train, DROP prix_ticket_train, DROP id_train, DROP id_reservation, CHANGE id_trajet_train idTrajetTrain INT AUTO_INCREMENT NOT NULL');
        $this->addSql('CREATE INDEX idTrain ON trajettrain (idTrain)');
        $this->addSql('CREATE INDEX idReservation ON trajettrain (idReservation)');
        $this->addSql('ALTER TABLE trajettrain ADD PRIMARY KEY (idTrajetTrain)');
        $this->addSql('ALTER TABLE utilisateur MODIFY id_utilisateur INT NOT NULL');
        $this->addSql('DROP INDEX `PRIMARY` ON utilisateur');
        $this->addSql('ALTER TABLE utilisateur ADD nomUtilisateur VARCHAR(50) NOT NULL, ADD prenomUtilisateur VARCHAR(50) NOT NULL, ADD emailUtilisateur VARCHAR(50) NOT NULL, ADD motDePasseUtilisateur VARCHAR(50) NOT NULL, ADD numeroTelephoneUtilisateur VARCHAR(20) NOT NULL, ADD roleUtilisateur VARCHAR(255) DEFAULT \'utilisateur\' NOT NULL, ADD questionSecurite VARCHAR(255) DEFAULT \'\' NOT NULL, ADD reponseSecurite VARCHAR(255) DEFAULT \'\' NOT NULL, DROP nom_utilisateur, DROP prenom_utilisateur, DROP email_utilisateur, DROP mot_de_passe_utilisateur, DROP numero_telephone_utilisateur, DROP role_utilisateur, DROP question_securite, DROP reponse_securite, CHANGE id_utilisateur idUtilisateur INT AUTO_INCREMENT NOT NULL');
        $this->addSql('ALTER TABLE utilisateur ADD PRIMARY KEY (idUtilisateur)');
        $this->addSql('ALTER TABLE utilisateur MODIFY id_utilisateur INT NOT NULL');
        $this->addSql('DROP INDEX `PRIMARY` ON utilisateur');
        $this->addSql('ALTER TABLE utilisateur ADD nomUtilisateur VARCHAR(50) NOT NULL, ADD prenomUtilisateur VARCHAR(50) NOT NULL, ADD emailUtilisateur VARCHAR(50) NOT NULL, ADD motDePasseUtilisateur VARCHAR(50) NOT NULL, ADD numeroTelephoneUtilisateur VARCHAR(20) NOT NULL, ADD roleUtilisateur VARCHAR(255) DEFAULT \'utilisateur\' NOT NULL, DROP nom_utilisateur, DROP prenom_utilisateur, DROP email_utilisateur, DROP mot_de_passe_utilisateur, DROP numero_telephone_utilisateur, DROP role_utilisateur, CHANGE id_utilisateur idUtilisateur INT AUTO_INCREMENT NOT NULL');
        $this->addSql('ALTER TABLE utilisateur ADD PRIMARY KEY (idUtilisateur)');
    }
}
