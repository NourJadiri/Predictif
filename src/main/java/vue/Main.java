package vue;

import dao.JpaUtil;
import metier.Client;
import metier.Employe;
import service.Service;

import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String [] args){
        JpaUtil.creerFabriquePersistance();
        /*testerInitialiserEmployes();
        testerTrouverEmployeParId();
        testerListerTousEmployes();
        testerAuthentifierEmploye();*/
        testerInscriptionClient();
        testerRechercherClient();
        testerAuthentifierClientParMail();
        testerAuthentifierClientParId();
        JpaUtil.fermerFabriquePersistance();
    }

    public static void testerInitialiserEmployes(){

        System.out.println();
        System.out.println("TEST DE L'INITIALISATION DES EMPLOYES");
        System.out.println("-------------------------------------");

        Service s = new Service();
        s.initialiserEmployes();
    }

    public static void testerTrouverEmployeParId(){

        System.out.println();
        System.out.println("TEST DE FETCH DES EMPLOYES PAR ID");
        System.out.println("-------------------------------------");

        Service s = new Service();
        Employe r = s.trouverEmployeParId( (long)2 );
        System.out.println(r);
    }

    public static void testerListerTousEmployes(){

        System.out.println();
        System.out.println("TEST DE LISTING DES EMPLOYES PAR ORDRE ALPHABETIQUE");
        System.out.println("-------------------------------------");

        Service s = new Service();

        List<Employe> employeList = s.listerTousEmployes();

        for ( Employe e : employeList ){
            System.out.println(e);
        }

    }

    public static void testerAuthentifierEmploye(){

        System.out.println();
        System.out.println("TEST D'AUTHENTIFICATION DES EMPLOYES");
        System.out.println("-------------------------------------");

        Service s = new Service();
        Employe r;
        r = s.authentifierEmploye( "vhugo" , "oui");

        if ( r != null ){
            System.out.println(r);
        }
    }

    public static void testerInscriptionClient(){

        System.out.println();
        System.out.println("TEST D'INSCRIPTION D'UN NOUVEAU CLIENT");
        System.out.println("-------------------------------------");

        Service s = new Service();
        Client client = new Client ("Dupont" , "Maurice" , new Date(2002,5,9), "Rue du paradis" , "maurice.dupont@client.fr" , "dupont123");

        s.inscriptionClient(client);

        System.out.println(client);
    }
    public static void testerRechercherClient(){

        System.out.println();
        System.out.println("TEST DE RECHERCHE CLIENT");
        System.out.println("-------------------------------------");

        Service s = new Service();
        s.trouverClientParId(1L);

    }
    public static void testerAuthentifierClientParMail(){

        System.out.println();
        System.out.println("TEST D'AUTHENTIFICATION CLIENT PAR MAIL");
        System.out.println("-------------------------------------");

        Service s = new Service();
        s.authentifierClientParMail("maurice.dupont@client.fr" , "dupont123");

    }
    public static void testerAuthentifierClientParId(){

        System.out.println();
        System.out.println("TEST D'AUTHENTIFICATION CLIENT PAR ID");
        System.out.println("-------------------------------------");

        Service s = new Service();
        s.authentifierClientParId(5L , "dupont123");

    }
}
