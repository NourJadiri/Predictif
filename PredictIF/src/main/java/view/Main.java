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
 * @author ghembise
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
        //testerAjoutConsultation();
        testerListerConsultations();
       JpaUtil.fermerFabriquePersistance();
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
    
    public static void testerInscriptionEmploye(){
        Employe employee0 = new Employe("Irma", "Camille", 'F', "0654354354", "camille.irma@predictif.if", "motDePasse");
        Employe employee1 = new Employe("Doe", "John", 'M', "555-1234", "john.doe@example.com", "password123");
        Employe employee2 = new Employe("Smith", "Jane", 'F', "555-5678", "jane.smith@example.com", "abcdef");
        Employe employee3 = new Employe("Johnson", "Bob", 'M', "555-4321", "bob.johnson@example.com", "foobar");
        Employe employee4 = new Employe("Lee", "Alice", 'F', "555-8765", "alice.lee@example.com", "qwerty");
        Employe employee5 = new Employe("Garcia", "Juan", 'M', "555-9876", "juan.garcia@example.com", "p@ssword!");

        Service sc = new Service();
        
        sc.initEmploye(employee0);
        sc.initEmploye(employee1);
        sc.initEmploye(employee2);
        sc.initEmploye(employee3);
        sc.initEmploye(employee4);
        sc.initEmploye(employee5);

    }

    public static void testerAjoutMedium(){

        System.out.println("TEST AJOUT MEDIUM");
        System.out.println("-----------------");

        // Creating a new Spirite object
        Spirite spirite = new Spirite("The Ghost Whisperer", "Female", "I can communicate with the dead", "Crystal ball");

        // Creating a new Cartomancier object
        Cartomancier cartomancier = new Cartomancier("Madame Destiny", "Female", "I can predict your future using tarot cards");

        // Creating a new Astrologue object
        Astrologue astrologue = new Astrologue("Master of Astrology", "2020", "Professor Stars", "Male", "I can read the stars and predict your fate");

        Service sc = new Service();

        sc.ajouterMedium(spirite);
        sc.ajouterMedium(cartomancier);
        sc.ajouterMedium(astrologue);
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
        Medium medium1 = new Cartomancier("Gwen", "Female", "A tarot reading involves a deck of cards and a reader who interprets them to provide insights and guidance.");
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
        Medium medium4 = new Cartomancier("Cartomancy Reading", "Divination", "A cartomancy reading involves using a deck of cards, such as tarot or oracle cards, to provide insights and guidance.");
        String commentaire4 = "The cartomancy reading was spot on.";
        Consultation consultation4 = new Consultation(date4, heure4, employe4, client4, medium4, commentaire4);
        consultations.add(consultation4);
        // Persisting all consultation objects
        for(Consultation consultation : consultations){
            sc.ajouterConsultation(consultation);
        }
    }

    public static void testerListerConsultations(){
        Service sc = new Service();
        List<Consultation> consultations = new ArrayList<>();

        // Create the first consultation
        Date date1 = new Date();
        Date heure1 = new Date();
        Employe employe1 = new Employe("John", "Doe", 'M', "1234567890", "john.doe@example.com", "password");
        Client client1 = new Client("Jane", "Doe", new Date(), "123 Main St", "jane.doe@example.com", "1234567890", "password");
        Medium medium1 = new Cartomancier("Gwen", "Female", "A tarot reading involves a deck of cards and a reader who interprets them to provide insights and guidance.");
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
        Medium medium4 = new Cartomancier("Cartomancy Reading", "Divination", "A cartomancy reading involves using a deck of cards, such as tarot or oracle cards, to provide insights and guidance.");
        String commentaire4 = "The cartomancy reading was spot on.";
        Consultation consultation4 = new Consultation(date4, heure4, employe4, client4, medium4, commentaire4);
        consultations.add(consultation4);

        //peut pas marcher avec le cascade type (recree un client, medium et employe avec la meme id)
        //Consultation consultation5 = new Consultation(date4, heure4, employe3, client1, medium4, commentaire2);
        //consultations.add(consultation5);
        // Persisting all consultation objects
        for(Consultation consultation : consultations){
            sc.ajouterConsultation(consultation);
        }
        System.out.println(client1);
        List<Consultation> c = sc.listerConsultationRecente(client1);
        c.forEach(System.out::println);

    }
 
}
