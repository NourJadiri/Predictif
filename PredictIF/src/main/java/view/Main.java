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

import java.text.SimpleDateFormat;
import java.util.*;

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
        testerSaisie();
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
        Client client2 = sc.rechercherClientparId(7L);
        Medium medium1 = sc.rechercherMediumParId(13L);
        Medium medium2 = sc.rechercherMediumParId(14L);
        Medium medium3 = sc.rechercherMediumParId(11L);

        // Le client demande une consultation, une nouvelle consultation est créée
        System.out.println(client1.getPrenom() + " demande une consultation avec " + medium1.getDenomination());
        Consultation consultation1 = sc.demanderConsultation(client1, medium1);
        System.out.println(consultation1.getEmploye().getPrenom() + " est l'employé sollicité !");
        // La consultation se fait accepter
        sc.accepterConsultation(consultation1);

        Consultation consultation2 = sc.demanderConsultation(client1, medium2);
        sc.accepterConsultation(consultation2);

        Consultation consultation3 = sc.demanderConsultation(client1, medium3);
        sc.accepterConsultation(consultation3);

        // La consultation 1 est finie, l'employé sollicité redevient libre
        sc.finConsultation(consultation1, "Ceci est un commentaire");

        consultation1 = sc.demanderConsultation(client2, medium1);
        sc.accepterConsultation(consultation1);

        sc.listerConsultationsRecente(client1);
        System.out.println(sc.favouritesMediumsList(client1));
    }


    public static void testerSaisie() {
        List<Integer> choix = new ArrayList<Integer>() {{
            add(1);
            add(2);
            add(3);
        }};
        final int CLIENT = 1;
        final int EMPLOYE = 2;
        final int QUITTER = 3;
        System.out.println("Bonjour, bienvenue dans PREDICT'IF");

        boolean appOpen = true;
        do{
            int choixInt = Saisie.lireInteger("Vous êtes un : \n 1. Client \n 2. Employe \n 3. Quitter l'application", choix);
            switch(choixInt){
                case CLIENT:
                    boolean activeUser;
                    do{
                        activeUser = Menu.displayClientMenu();
                    }while(activeUser);

                    break;

                case EMPLOYE:
                    Menu.displayEmployeMenu();
                    break;

                case QUITTER:
                    appOpen = false;
                    System.exit(0);
                    break;
            }

        }while(appOpen);

    }


}