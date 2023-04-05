/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.JpaUtil;

import metier.modele.Client;
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
       testerInscriptionClient();
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
    
    
}
