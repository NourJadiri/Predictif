/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.*;

import util.Saisie;

import dao.JpaUtil;

import metier.modele.*;
import metier.service.Service;

import javax.swing.plaf.synth.SynthUI;
import java.util.ArrayList;
import java.util.Date;
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
        //testerSaisie();
        //testerAjoutConsultation();
        //getPredction();
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

        // Create a list to hold the consultations
        List<Consultation> consultations = new ArrayList<>();

        Employe employe1 = sc.rechercherEmploye(1L);
        Employe employe2 = sc.rechercherEmploye(2L);

        Client client1 = sc.rechercherClientparId(6L);
        Medium medium1 = sc.rechercherMediumParId(13L);
        Medium medium2 = sc.rechercherMediumParId(14L);

        // Create the first consultation
        Consultation consultation1 = new Consultation(new Date(), new Date(), employe1, client1, medium1, "bonsoir");
        consultations.add(consultation1);

        // Create the second consultation
        Consultation consultation2 = new Consultation(new Date(), new Date(), employe2, client1, medium1, "bonjour");
        consultations.add(consultation2);

        // Create the thrid consultation
        Consultation consultation3 = new Consultation(new Date(), new Date(), employe1, client1, medium2, "au revoir");
        consultations.add(consultation3);

        // Persisting all consultation objects
        for (Consultation consultation : consultations) {
            sc.ajouterConsultation(consultation);
        }

        sc.listerConsultationsRecente(client1);
        System.out.println(sc.favouritesMediumsList(client1));
    }

    public static void testerConsultationsRecentes() {
        Service sc = new Service();

        List<Client> clients = sc.initClients();
        List<Employe> employes = sc.initEmployes();
        List<Medium> mediums = sc.initMediums();
        List<Consultation> consultations = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            consultations.add(new Consultation(new Date(), new Date(), employes.get(i), clients.get(i), mediums.get(i), "commentaire " + i));
            sc.ajouterConsultation(consultations.get(i));
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