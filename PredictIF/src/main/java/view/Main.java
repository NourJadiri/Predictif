/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

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
        initDb();
        //testerInscriptionClient();
        //testerInscriptionEmploye();
        //testerAjoutConsultation();
        //testerAjoutMedium();
        //testerAjoutConsultation();
        //testerFiltrageGenre();
        testerFiltrageType();
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

        // Create the first consultation
        Date date1 = new Date();
        Date heure1 = new Date();
        Employe employe1 = new Employe("John", "Doe", 'M', "1234567890", "john.doe@example.com", "password");
        Client client1 = new Client("Jane", "Doe", new Date(), "123 Main St", "jane.doe@example.com", "1234567890", "password");
        Medium medium1 = new Cartomancien("Gwen", "Female", "A tarot reading involves a deck of cards and a reader who interprets them to provide insights and guidance.");
        String commentaire1 = "This was a great consultation.";
        Consultation consultation1 = new Consultation(date1, heure1, employe1, client1, medium1, commentaire1);
        consultations.add(consultation1);

        // Create the second consultation
        Date date2 = new Date();
        Date heure2 = new Date();
        Employe employe2 = new Employe("Alice", "Smith", 'F', "0987654321", "alice.smith@example.com", "password");
        Client client2 = new Client("Bob", "Johnson", new Date(), "456 Main St", "bob.johnson@example.com", "0987654321", "password");
        Medium medium2 = new Astrologue("Twisted Fate", "Male","Cards are cool, Stars are the future","Divination","2020");
        String commentaire2 = "I found the astrology reading to be very insightful.";
        Consultation consultation2 = new Consultation(date2, heure2, employe2, client2, medium2, commentaire2);
        consultations.add(consultation2);

        // Create the third consultation
        Date date3 = new Date();
        Date heure3 = new Date();
        Employe employe3 = new Employe("David", "Lee", 'M', "5555555555", "david.lee@example.com", "password");
        Client client3 = new Client("Susan", "Kim", new Date(), "789 Main St", "susan.kim@example.com", "5555555555", "password");
        Medium medium3 = new Spirite("Irma","Female","bonsoir","Book");
        String commentaire3 = "The spiritual counseling helped me find inner peace.";
        Consultation consultation3 = new Consultation(date3, heure3, employe3, client3, medium3, commentaire3);
        consultations.add(consultation3);

        // Create the fourth consultation
        Date date4 = new Date();
        Date heure4 = new Date();
        Employe employe4 = new Employe("Emily", "Davis", 'F', "1111111111", "emily.davis@example.com", "password");
        Client client4 = new Client("Mike", "Jackson", new Date(), "101 Main St", "mike.jackson@example.com", "1111111111", "password");
        Medium medium4 = new Cartomancien("Leblanc", "Female", "A cartomancy reading involves using a deck of cards, such as tarot or oracle cards, to provide insights and guidance.");
        String commentaire4 = "The cartomancy reading was spot on.";
        Consultation consultation4 = new Consultation(date4, heure4, employe4, client4, medium4, commentaire4);
        consultations.add(consultation4);

        // Persisting all consultation objects
        for(Consultation consultation : consultations){
            sc.ajouterConsultation(consultation);
        }

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





}
