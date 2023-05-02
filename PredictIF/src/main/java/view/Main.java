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

import javax.persistence.criteria.CriteriaBuilder;
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
        sc.finConsultation(consultation1, "");

        consultation1 = sc.demanderConsultation(client2, medium1);
        sc.accepterConsultation(consultation1);

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

        int choixInt = Saisie.lireInteger("Vous êtes un : \n 1. Client \n 2. Employe", choix);

        if (choixInt == 1) {
            int choixClient = Saisie.lireInteger("Vous êtes un : \n 1. Client de PREDICT'IF \n 2. Nouveau Client", choix);
            if (choixClient == 1) {
                Client client = null;

                System.out.println("CONNEXION");
                System.out.println("---------");

                while (client == null) {
                    String mail = Saisie.lireChaine("Entrez votre adresse mail : ");
                    String mdp = Saisie.lireChaine("Entrez votre mot de passe : ");

                    client = sc.authentifierClientmail(mail, mdp);

                    if (client == null) {
                        System.out.println("Client non trouvé, veuillez réessayer");
                    }
                }

                System.out.println("Bonjour " + client.getPrenom() + " " + client.getNom());

                Medium medium = chooseMedium();
                System.out.println("Vous avez selectionné : " + medium.getDenomination());

                Consultation consultation = sc.demanderConsultation(client, medium);

                /// Section employé
                String msgEmploye = "Bonjour " + consultation.getEmploye().getPrenom() +
                        ". Consultation requise pour " + client.getPrenom() +
                        " " + client.getNom() + ".\n Médium à incarner : " + medium.getDenomination();

                Message.envoyerNotification(consultation.getEmploye().getTelephone(), msgEmploye);

                if (Saisie.lireInteger("1. Accepter \n 2. Refuser", choix) == 1) {
                    sc.accepterConsultation(consultation);

                    String msgClient = "Bonjour " + client.getPrenom() + ". J'ai bien reçu votre demande de consultation du " + consultation.getDate() + " à " + consultation.getHeure()
                            + ".\n Vous pouvez dès à présent me contacter au " + consultation.getEmploye().getTelephone() + ". A tout de suite ! Médiumiquement vôtre, Mme Irma";
                    Message.envoyerNotification(client.getNumTel(), msgClient);

                    if (Saisie.lireInteger("En panne d'inspiration ? Obtenez trois predictions\n 1.Oui \n 2.Non", choix) == 1) {
                        generatePrediction(client);
                    }

                    String commentaire = Saisie.lireChaine("Un commentaire pour vos collègues : ");

                    sc.finConsultation(consultation, commentaire);
                }
            }
            // Si nouveau client
            else if (choixClient == 2) {
                System.out.println("INSCRIPTION");
                System.out.println("-----------");

                Client newClient = registerClient();
                sc.inscriptionClient(newClient);
            }
        }
    }

    public static Client registerClient(){
        System.out.println("INSCRIPTION CLIENT");
        System.out.println("------------------");

        System.out.println("On voudrait en apprendre plus sur vous...");
        String nom = inputString("Veuillez saisir votre nom");
        String prenom = inputString("Veuillez saisir votre prénom");
        Date dateNaissance = inputDate("Veuillez entrer votre date de naissance (dd/mm/yyyy)");
        String adressePostale = inputString("Veuillez saisir votre adresse postale");
        String numTel = inputString("Et enfin, votre numéro de téléphone");

        System.out.println("Maintenant qu'on en sait un peu plus sur vous, il nous faudra des credentials");

        String mail = inputMail();
        String motDePasse = inputString("Et enfin, votre mot de passe");

        return new Client(nom , prenom , dateNaissance , adressePostale , mail , motDePasse , numTel);
    }

    /// INPUTS
    public static String inputString (String label){
        String s;

        do{
            s = Saisie.lireChaine(label + " : ");
            if(s == null || s.isEmpty()){
                System.err.println("Attention, champ renseigné invalide");
            }
        }while(s == null || s.isEmpty());

        return s;
    }
    public static Date inputDate(String label){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date;

        do {
            String input = Saisie.lireChaine(label + " : ");

            try {
                date = dateFormat.parse(input);
            } catch (Exception e) {
                date = null;
                System.out.println("Format de date invalide, merci d'utiliser le format suivant dd/MM/yyyy.");
            }
        } while (date == null);

        return date;
    }
    public static String inputMail(){
        String mail;
        String yesOrNo = "oui";
        Client test = null;
        Service sc = new Service();
        do{
            mail = inputString("Veuillez entrer votre mail");
            test = sc.rechercherClientparMail(mail);

            if(test != null){
                System.out.println("Ce mail existe déjà dans notre base de donnée.. Peut être voulez vous vous connecter ?");
                do{
                    System.out.println("oui/non");
                    yesOrNo = Saisie.lireChaine("");
                }while(!yesOrNo.equals("oui") && !yesOrNo.equals("non"));
            }
        }while(test != null && yesOrNo == "non");

        return mail;
    }

    /// CHOIX DU MEDIUM
    public static Medium chooseMedium(){
        Service sc = new Service();
        ArrayList<String> mediumTypes = chooseMediumType();
        String mediumGender = chooseMediumGender();

        List<Medium> mediumList = sc.filtrerMediums(mediumGender , mediumTypes);
        List<Integer> mediumListIndexes = possibleMediumIndexes(mediumList);
        System.out.println("Choisissez le médium qui vous convient");
        displayMediumList(mediumList);

        Integer mediumIndex = Saisie.lireInteger("",mediumListIndexes);

        return mediumList.get(mediumIndex - 1);
    }
    public static ArrayList<String> chooseMediumType(){
        LinkedList<String> types = new LinkedList<>();
        types.add("1. Astrologue");
        types.add("2. Cartomancien");
        types.add("3. Spirite");
        types.add("4. Tout");

        ArrayList<String> typesChosen = new ArrayList<>();

        System.out.println("Quel type de médium voulez vous consulter ?");
        for(String type : types){
            System.out.println(type);
        }

        Integer type = Saisie.lireInteger("");
        typesChosen.add(types.get(type - 1));


        return typesChosen;
    }
    public static String chooseMediumGender(){
        String[] genders = {"H" , "F", "all"};
        System.out.println("Avez vous des préférences pour un genre en particulier ?");
        System.out.println("1. Homme");
        System.out.println("2. Femme");
        System.out.println("3. Aucune préférence");

        Integer genderIndex = Saisie.lireInteger("",Arrays.asList(1,2,3));

        return genders[genderIndex - 1];
    }
    public static void displayMediumList(List<Medium> mediumList){
        System.out.println("Les médiums qui répondent à vos filtres sont : ");
        for (int i = 0 ; i < mediumList.size() ; i++) {
            System.out.println((i+1) + " - " + mediumList.get(i).getDenomination());
            System.out.println("\n" + mediumList.get(i).getPresentation());
        }
    }
    public static List<Integer> possibleMediumIndexes(List<Medium> mediumList){
        List<Integer> r = new ArrayList<>();

        for (int i = 0; i < mediumList.size(); i++) {
            r.add(i+1);
        }

        return r;
    }

    /// REPONSE EMPLOYE
    public static void generatePrediction(Client client){
        List<String> prediction;
        Service sc = new Service();

        int amour = Saisie.lireInteger("Saisir note amour");
        int sante = Saisie.lireInteger("Saisir note sante");
        int travail = Saisie.lireInteger("Saisir note travail");
        prediction = sc.demanderPrediction(client.getProfilAstral().getCouleur(), client.getProfilAstral().getAnimal(), amour, sante, travail);
        System.out.println("Prediction Amour : " + prediction.get(0));
        System.out.println("Prediction Sante : " + prediction.get(1));
        System.out.println("Prediction Travail : " + prediction.get(2));

    }
}