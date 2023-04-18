/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.JpaUtil;

import metier.modele.Client;
import metier.modele.Employe;
import metier.service.Service;

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
       testerInscriptionEmploye();
       JpaUtil.fermerFabriquePersistance();
    }
    
    public static void testerInscriptionClient(){
       Client ada = new Client("Lovelace", "Ada", new java.util.Date(System.currentTimeMillis()),"130 Avenue Albert Einstein","ada.lovelace@insa-lyon.fr","0669696969", "LAda");
       Service Sc = new Service();
        for (int i = 0; i < 2; i++) {
            Long id = Sc.inscriptionClient(ada);
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
    
    
}
