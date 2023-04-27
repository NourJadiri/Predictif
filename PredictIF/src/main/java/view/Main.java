/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.*;

import util.Saisie.java;

import dao.JpaUtil;

import metier.modele.*;
import metier.service.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ghembise mneljadiri cdjouadi
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        JpaUtil.creerFabriquePersistance();
        //testerInscriptionClient();
        //testerInscriptionEmploye();
        //testerAjoutConsultation();
        //testerAjoutMedium();
        
        testerSaisie();

        initDb();
        testerAjoutConsultation();
       JpaUtil.fermerFabriquePersistance();
    }

    public static void initDb(){
        Service sc = new Service();
        sc.initEmployes();
        sc.initClients();
        sc.initMediums();
    }

    public static void testerInscriptionClient(){
       Client ada = new Client("Lovelace", "Ada", new java.util.Date(System.currentTimeMillis()),"130 Avenue Albert Einstein","ada.lovelace@insa-lyon.fr","0669696969", "LAda");
       Service sc = new Service();
        for (int i = 0; i < 2; i++) {
            Long id = sc.inscriptionClient(ada);
            if(id != null){
                System.out.println("> Succès inscription");
            } else {
                System.out.println("> Echec inscription");
            }
        }
    }


    public static void testerAjoutMedium(){

        System.out.println("TEST AJOUT MEDIUM");
        System.out.println("-----------------");

        Service sc = new Service();

        sc.initMediums();
    }

    public static void testerFiltrageGenre(){
        Service sc = new Service();

        List<Medium> mediums = sc.filtrerMediums("F",new ArrayList<>());

        for(Medium m : mediums){
            System.out.println(m);
        }
    }

    public static void testerFiltrageType(){
        Service sc = new Service();
        ArrayList<String> types = new ArrayList<>();
        types.add("Astrologue");
        types.add("Cartomancien");

        List<Medium> mediums = sc.filtrerMediums("M", types);

        for(Medium m : mediums){
            System.out.println(m);
        }
    }

    public static void testerAjoutConsultation() {
        Service sc = new Service();

        // Create a list to hold the consultations
        List<Consultation> consultations = new ArrayList<>();

        Employe employe1 = sc.rechercherEmploye(1L);
        Client client1 = sc.rechercherClientparId(6L);
        Medium medium1 = sc.rechercherMediumParId(13L);

        Medium medium2 = sc.rechercherMediumParId(14L);

        // Create the first consultation
        Consultation consultation1 = new Consultation(new Date(), new Date(), employe1, client1, medium1, "bonsoir");
        consultations.add(consultation1);

        // Create the second consultation
        Consultation consultation2 = new Consultation(new Date(), new Date(), employe1, client1, medium1, "bonjour");
        consultations.add(consultation2);

        // Create the thrid consultation
        Consultation consultation3 = new Consultation(new Date(), new Date(), employe1, client1, medium2, "au revoir");
        consultations.add(consultation3);

        // Persisting all consultation objects
        for(Consultation consultation : consultations){
            sc.ajouterConsultation(consultation);
        }

        sc.listerConsultationsRecente(client1);
        System.out.println(sc.favouritesMediumsList(client1));
    }

    public static void testerConsultationsRecentes(){
        Service sc = new Service();

        List<Client> clients = sc.initClients();
        List<Employe> employes = sc.initEmployes();
        List<Medium> mediums = sc.initMediums();
        List<Consultation> consultations = new ArrayList<>();

        for (int i = 0 ; i < 5 ; i++){
            consultations.add( new Consultation(new Date() , new Date() , employes.get(i) , clients.get(i) , mediums.get(i) , "commentaire " + i));
            sc.ajouterConsultation(consultations.get(i));
        }

        // Ajout de la recherche des 5 consultations récentes

    }
    
    public static void testerSaisie(){
        System.out.println("Tapez 1 si vous etes un Client ou 2 si vous etes un Employe : ");
        Scanner sc = new Scanner(System.in);
        int choixInt = sc.nextInt();
        if (choixInt == 1){
            System.out.println("Tapez 1 si vous etes un ancien Client ou 2 si vous etes un nouveau Client : ");
            int choixClient = sc.nextInt();
            if (choixClient == 1){
                System.out.println("Connecte toi");
                System.out.println("Entrez votre username : ");
                String username = sc.nextLine();
                if (lireChaine(username) == null){
                    System.out.println("Username incorrect lors de la saisie.");
                }
                else{
                    System.out.println("Entrez votre mot de passe : ");
                    String mdp = sc.nextLine();
                    if (lireChaine(mdp) == null){
                        System.out.println("Mot de passe incorrect lors de la saisie.");
                    }
                    else{
                        System.out.println("Connexion réussi.");
                        //Ajouter la méthode qui permet de connecter un Client ici
                    }
                }
            }
            else if (choixClient == 2){
                System.out.println("Inscris toi : ");
                System.out.println("Choisissez votre username : ");
                String username = sc.nextLine();
                if (lireChaine(username) == null){
                    System.out.println("Username incorrect lors de la saisie.");
                }
                else{
                    System.out.println("Choisissez votre mot de passe : ");
                    String mdp = sc.nextLine();
                    if (lireChaine(mdp) == null){
                        System.out.println("Mot de passe incorrect lors de la saisie.");
                    }
                    else{
                        System.out.println("Inscription réussi.");
                    }
                }
            }
            else{
                System.out.println("Erreur, tapez 1 ou 2. Veuillez recommencer");
            }
        }
        else if (choixInt == 2){
            System.out.println("Connecte toi : ");
            //pareil que la connexion client je le ferai plus tard
        }
        else{
            System.out.println("Erreur, tapez 1 ou 2. Veuillez recommencer");
        }
    }





}
