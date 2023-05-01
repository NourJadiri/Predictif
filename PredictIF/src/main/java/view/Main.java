/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import util.Message;
import util.Saisie;

import dao.JpaUtil;

import metier.modele.*;
import metier.service.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ghembise mneljadiri cdjouadi
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JpaUtil.creerFabriquePersistance();
        //testerInscriptionClient();
        //testerInscriptionEmploye();
        //testerAjoutConsultation();
        //testerAjoutMedium();


        initDb();
        testerAjoutConsultation();
        //testerSaisie();
        getPredction();
        testerAfficherRepartitionClient();
        JpaUtil.fermerFabriquePersistance();
    }

    public static void testerAfficherRepartitionClient(){
        Service sc = new Service();
        System.out.println(sc.afficherRepartitionClientParEmploye());
    }
    public static void getPredction(){
        Service sc = new Service();

        System.out.println(sc.demanderPrediction("Jaune", "Chien", 2, 3 ,4));
    }

    public static void initDb() {
        Service sc = new Service();
        sc.initEmployes();
        sc.initClients();
        sc.initMediums();
    }

    public static void testerInscriptionClient() {
        Client ada = new Client("Lovelace", "Ada", new java.util.Date(System.currentTimeMillis()), "130 Avenue Albert Einstein", "ada.lovelace@insa-lyon.fr", "0669696969", "LAda");
        Service sc = new Service();
        for (int i = 0; i < 2; i++) {
            Long id = sc.inscriptionClient(ada);
            if (id != null) {
                System.out.println("> Succès inscription");
            } else {
                System.out.println("> Echec inscription");
            }
        }
    }


    public static void testerAjoutMedium() {

        System.out.println("TEST AJOUT MEDIUM");
        System.out.println("-----------------");

        Service sc = new Service();

        sc.initMediums();
    }

    public static void testerFiltrageGenre() {
        Service sc = new Service();

        List<Medium> mediums = sc.filtrerMediums("F", new ArrayList<>());

        for (Medium m : mediums) {
            System.out.println(m);
        }
    }

    public static void testerFiltrageType() {
        Service sc = new Service();
        ArrayList<String> types = new ArrayList<>();
        types.add("Astrologue");
        types.add("Cartomancien");

        List<Medium> mediums = sc.filtrerMediums("M", types);

        for (Medium m : mediums) {
            System.out.println(m);
        }
    }

    public static void testerAjoutConsultation() {
        Service sc = new Service();

        Client client1 = sc.rechercherClientparId(6L);
        Medium medium1 = sc.rechercherMediumParId(13L);
        Medium medium2 = sc.rechercherMediumParId(14L);
        Medium medium3 = sc.rechercherMediumParId(11L);

        Consultation consultation1 = sc.demanderConsultation(client1, medium1);
        sc.accepterConsultation(consultation1);
        Consultation consultation2 = sc.demanderConsultation(client1, medium2);
        sc.accepterConsultation(consultation2);
        Consultation consultation3 = sc.demanderConsultation(client1, medium3);
        sc.accepterConsultation(consultation3);

        sc.listerConsultationsRecente(client1);
        System.out.println(sc.favouritesMediumsList(client1));
    }

    public static void testerConsultationsRecentes() {
        Service sc = new Service();

        List<Client> clients = sc.initClients();
        List<Medium> mediums = sc.initMediums();

        for (int i = 0; i < 5; i++) {
            sc.demanderConsultation(clients.get(i), mediums.get(i));
        }

        // Ajout de la recherche des 5 consultations récentes

    }

    public static void testerSaisie() {

        List<Integer> choix = new ArrayList<Integer>() {{
            add(1);
            add(2);
        }};
        Service sc = new Service();
        int choixInt = Saisie.lireInteger("Tapez 1 si vous etes un Client ou 2 si vous etes un Employe : ", choix);
        if (choixInt == 1) {
            int choixClient = Saisie.lireInteger("Tapez 1 si vous etes un ancien Client ou 2 si vous etes un nouveau Client : ", choix);
            if (choixClient == 1) {
                System.out.println("Connexion");
                String mail = Saisie.lireChaine("Entrez votre adresse mail : ");
                String mdp = Saisie.lireChaine("Entrez votre mot de passe : ");
                Client client = sc.authentifierClientmail(mail, mdp);
                System.out.println("Bonjour " + client.getPrenom() + " " + client.getNom());
                Medium medium = sc.rechercherMediumParId(11L); // appuie sur un bouton
                System.out.println("Vous avez selectionné : " + medium.getDenomination());
                Consultation consultation = sc.demanderConsultation(client, medium); // on creer une consultation et on va chercher un employe
                String msgEmploye = "Bonjour " + consultation.getEmploye().getPrenom() +
                        ". Consultation requise pour " + client.getPrenom() +
                        " " + client.getNom() + ". Médium à incarner : " + medium.getDenomination();
                Message.envoyerNotification(consultation.getEmploye().getTelephone(), msgEmploye);
                if (Saisie.lireInteger("1 - Accepter \n 2 - Refuser", choix) == 1){
                    sc.accepterConsultation(consultation);
                    String msgClient = "Bonjour " + client.getPrenom() + ". J'ai bien reçu votre demande de consultation du " + consultation.getDate() + " à " + consultation.getHeure()
                            + ".\n Vous pouvez dès à présent me contacter au " + consultation.getEmploye().getTelephone() + ". A tout de suite ! Médiumiquement vôtre, Mme Irma";
                    Message.envoyerNotification(client.getNumTel(), msgClient);
                    if (Saisie.lireInteger("En panne d'inspiration ? Obtenez trois predictions\n 1-Oui \n 2-Non", choix) == 1){
                        int amour = Saisie.lireInteger("Saisir note amour");
                        int sante = Saisie.lireInteger("Saisir note sante");
                        int travail = Saisie.lireInteger("Saisir note travail");
                        sc.demanderPrediction(client.getProfilAstral().getCouleur(), client.getProfilAstral().getAnimal(), amour, sante, travail);
                    }
                    //finConsultation apres je suppose puis les methodes de stats
                }
            } else if (choixClient == 2) {
                System.out.println("Inscription : ");
                String nom = Saisie.lireChaine("Choisissez votre nom d'utilisateur : ");
                if (nom.equals("")) {
                    System.out.println("nom d'utilisateur incorrect lors de la saisie.");
                } else {
                    String mdp = Saisie.lireChaine("Choisissez votre mot de passe : ");
                    if (mdp.equals("")) {
                        System.out.println("Mot de passe incorrect lors de la saisie.");
                    } else {
                        /*                        sc.inscriptionClient(new Client(nom)); faut dautre info pour creer un client sois le
                         * mettre en dur soit refaire la meme chose quavec le mot de passe et le nom (simuler entierement le formulaire d'inscription*/
                        System.out.println("Inscription réussi.");
                    }
                }
            }
        } else if (choixInt == 2) {
            System.out.println("Connexion : ");
            //pareil que la connexion client je le ferai plus tard
        } else {

        }
    }
}