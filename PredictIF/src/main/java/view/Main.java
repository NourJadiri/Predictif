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
            add(3);
        }};
        final int CLIENT = 1;
        final int EMPLOYE = 2;
        final int QUITTER = 3;
        System.out.println("Bonjour, bienvenue dans PREDICT'IF");

        boolean appOpen = true;
        do{
            int choixInt = Saisie.lireInteger("Vous êtes un : \n 1. Client \n 2. Employe \n 3.Quitter l'application", choix);
            switch(choixInt){
                case CLIENT:
                    boolean activeUser;
                    do{
                        activeUser = displayClientMenu();
                    }while(activeUser);
                    break;

                case EMPLOYE:
                    displayEmployeMenu();
                    break;

                case QUITTER:
                    appOpen = false;
                    System.exit(0);
                    break;
            }

        }while(appOpen);

    }

    public static boolean displayClientMenu(){
        List<Integer> choix = new ArrayList<Integer>() {{
            add(1);
            add(2);
            add(3);
        }};

        Client client = null;

        boolean loop = true;

        int choixClient = Saisie.lireInteger("Vous êtes un : \n 1. Client de PREDICT'IF \n 2. Nouveau Client \n 3. Revenir au menu précédent", choix);

        // Si le client est déjà présent dans la base de donnée, on l'authentifie
        if (choixClient == 1) {
            client = authenticateClient();
        }
        // Si nouveau client, on lui crée un profil
        else if (choixClient == 2) {
            client = registerClient();
        }
        else{
            loop = false;
        }

        boolean keepDisplayingClientActions;

        if(loop){
            do{
                keepDisplayingClientActions = displayClientActions(client);
            }while(keepDisplayingClientActions);
        }

        return loop;
    }

    public static boolean displayClientActions(Client client){
        boolean loop = true;
        Integer action = Saisie.lireInteger("Veuillez saisir l'action que vous voulez effectuer : " +
                "\n1. Demander une consultation" +
                "\n2. Voir vos cinq dernieres consultations" +
                "\n3. Voir votre liste de mediums préférés" +
                "\n4. Visualiser votre profil astral" +
                "\n5. Vous deconnecter",Arrays.asList(1,2,3,4,5));

        switch(action){
            case 1:
                startConsultationProcess(client);
                break;
            case 2:
                displayFiveLastConsultations(client);
                break;
            case 3:
                displayClientHistory(client);
                break;
            case 4:
                displayProfilAstral(client);
                break;
            default:
                loop = false;
                break;
        }

        return loop;
    }

    public static Client authenticateClient(){
        Client client = null;

        System.out.println("CONNEXION CLIENT");
        System.out.println("----------------");
        Service sc = new Service();

        while(client == null){
            String mail = Saisie.lireChaine("Entrez votre adresse mail : ");
            String mdp = Saisie.lireChaine("Entrez votre mot de passe : ");

            client = sc.authentifierClientmail(mail, mdp);

            if(client == null){
                System.out.println("Client non trouvé, veuillez réessayer");
            }
        }

        System.out.println("Bonjour " + client.getPrenom() + " " + client.getNom());
        return client;
    }

    public static Client registerClient(){
        Service sc = new Service();

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

        Client client = new Client(nom , prenom , dateNaissance , adressePostale , mail , motDePasse , numTel);
        sc.inscriptionClient(client);

        return client;
    }

    public static void displayFiveLastConsultations(Client client){
        Service sc = new Service();
        List<Consultation> recentConsultations = sc.listerConsultationsRecente(client);

        if(recentConsultations.size() != 0){
            for(Consultation c : recentConsultations){
                System.out.println("-Le " + c.getDate() + " à " + c.getHeure() + ", consultation avec " + c.getMedium().getDenomination());
            }
        }
        else{
            System.out.println("Vous n'avez aucune consultation !! Pensez à prendre rendez vous..");
        }
    }

    public static void displayClientHistory(Client client){
        Service sc = new Service();

        List<Consultation> clientHistory = sc.getHistoriqueClient(client);

        if(clientHistory.size() != 0){
            for(Consultation c : clientHistory){
                System.out.println("-Le " + c.getDate() + " à " + c.getHeure() + ", consultation avec " + c.getMedium().getDenomination());
            }
        }
        else{
            System.out.println("Votre historique est vide... Vous avez pensé à voir nos médiums ?");
        }

    }

    public static void displayProfilAstral(Client client){
        System.out.println(client.getProfilAstral());
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

        List<Medium> mediumList;

        do{
            ArrayList<String> mediumTypes = chooseMediumType();
            String mediumGender = chooseMediumGender();

            mediumList = sc.filtrerMediums(mediumGender , mediumTypes);

            if(mediumList.size() == 0){
                System.out.println("Désolé, mais aucun médium ne répond à vos filtres...");
            }
        }while(mediumList.size() == 0);

        List<Integer> mediumListIndexes = possibleMediumIndexes(mediumList);

        displayMediumList(mediumList);
        System.out.println("Choisissez le médium qui vous convient : ");

        Integer mediumIndex = Saisie.lireInteger("",mediumListIndexes);

        Medium medium = mediumList.get(mediumIndex - 1);
        System.out.println("Vous avez selectionné : " + medium.getDenomination());

        return medium;
    }
    public static ArrayList<String> chooseMediumType(){
        LinkedList<String> types = new LinkedList<>();
        types.add("Astrologue");
        types.add("Cartomancien");
        types.add("Spirite");
        types.add("Tout");

        ArrayList<String> typesChosen = new ArrayList<>();

        System.out.println("Quel type de médium voulez vous consulter ?");
        for(int i = 0 ; i < types.size() ; i++){
            System.out.println((i+1) + ". " + types.get(i));
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
            System.out.println((i+1) + ". " + mediumList.get(i).getDenomination());
            System.out.println(mediumList.get(i).getPresentation());
        }
    }
    public static List<Integer> possibleMediumIndexes(List<Medium> mediumList){
        List<Integer> r = new ArrayList<>();

        for (int i = 0; i < mediumList.size(); i++) {
            r.add(i+1);
        }

        return r;
    }
    /// EMPLOYE
    public static Employe authenticateEmploye(){
        Service sc = new Service();
        Employe employe = null;

        System.out.println("ESPACE EMPLOYE");
        System.out.println("--------------");

        while(employe == null){
            String mail = inputMail();
            String password = inputString("Veuillez entrer votre mot de passe");
            employe = sc.authentifierEmploye(mail, password);

            if(employe == null){
                System.out.println("Email ou mot de passe incorrect");
            }
        }

        System.out.println("Bonjour " + employe.getPrenom() + ".");
        return employe;

    }

    public static void displayEmployeMenu(){

        Employe employe = authenticateEmploye();


        boolean keepDisplayingEmployeMenu;
        do{
            keepDisplayingEmployeMenu = displayEmployeActions(employe);
        }while(keepDisplayingEmployeMenu);

    }

    public static boolean displayEmployeActions(Employe employe){

        boolean loop = true;

        Integer choixEmploye = Saisie.lireInteger("Vous souhaitez : " +
                "\n 1. Voir le nombre de consultations par medium" +
                "\n 2. Voir le top 5 des médiums choisis par les clients" +
                "\n 3. Voir la répartition des clients par employe" +
                "\n 4. Vous deconnecter", Arrays.asList(1,2,3,4));

        switch (choixEmploye){
            case 1:
                displayConsultationsPerMedium();
                break;
            case 2:
                displayTopFiveMediums();
                break;
            case 3:
                displayClientRepartition();
                break;
            default:
                loop = false;
                break;
        }

        return loop;
    }

    public static void displayConsultationsPerMedium(){
        Service sc = new Service();

        System.out.println("NOMBRE DE CONSULTATION PAR MEDIUM");
        System.out.println("---------------------------------");

        Map<Medium, Integer> consultationsPerMedium = sc.afficherRepartitionConsultationParMedium();

        for(Map.Entry<Medium, Integer> e : consultationsPerMedium.entrySet() ){
            System.out.println(" -" + e.getKey() + " : " + e.getValue());
        }
    }

    public static void displayTopFiveMediums(){
        Service sc = new Service();

        System.out.println("TOP 5 DES MEDIUMS LES PLUS CONSULTES");
        System.out.println("------------------------------------");

        Map<Medium, Integer> consultationsPerMedium = sc.afficherRepartitionConsultationParMedium();

        Set<Map.Entry<Medium, Integer>> entrySet = consultationsPerMedium.entrySet();

        int count = 0;

        System.out.println("Vos cinq médiums les plus consultés");

        for(Map.Entry<Medium, Integer> e : consultationsPerMedium.entrySet()){
            if(count < 5){
                System.out.println(" -" + e.getKey() + " : " + e.getValue());
                count++;
            }
        }

    }

    public static void displayClientRepartition(){
        Service sc = new Service();

        Client client = null;

        boolean loop = true;

        while(loop && client == null){

            Long id = Long.valueOf(Saisie.lireInteger("Saisissez l'id du client souhaité"));
            client = sc.rechercherClientparId(id);

            if(client == null){
                System.err.println("Aucun client avec l'id " + id + " n'est présent dans votre base de donnée");
                System.err.println("Veuillez réessayer svp...");
            }
        }

    }
    /// REPONSE EMPLOYE
    public static void alertEmploye(Consultation consultation){
        String msgEmploye = "Bonjour " + consultation.getEmploye().getPrenom() +
                ". Consultation requise pour " + consultation.getClient().getPrenom() +
                " " + consultation.getClient().getNom() + ".\n Médium à incarner : " + consultation.getMedium().getDenomination();

        Message.envoyerNotification(consultation.getEmploye().getTelephone(), msgEmploye);
    }
    public static void generatePrediction(Client client){
        List<Integer> notes = Arrays.asList(1,2,3,4);
        List<String> prediction;
        Service sc = new Service();

        int amour = Saisie.lireInteger("Saisir note amour",notes);
        int sante = Saisie.lireInteger("Saisir note sante",notes);
        int travail = Saisie.lireInteger("Saisir note travail",notes);
        prediction = sc.demanderPrediction(client.getProfilAstral().getCouleur(), client.getProfilAstral().getAnimal(), amour, sante, travail);
        System.out.println("Prediction Amour : " + prediction.get(0));
        System.out.println("Prediction Sante : " + prediction.get(1));
        System.out.println("Prediction Travail : " + prediction.get(2));

    }
    /// CONSULTATION
    public static void startConsultationProcess(Client client){
        List<Integer> choix = new ArrayList<Integer>() {{
            add(1);
            add(2);
        }};

        Service sc = new Service();
        Medium medium = chooseMedium();

        Consultation consultation = sc.demanderConsultation(client, medium);

        /// Section employé
        alertEmploye(consultation);

        if (Saisie.lireInteger("1. Accepter \n2. Refuser", choix) == 1){
            sc.accepterConsultation(consultation);

            String msgClient = "Bonjour " + client.getPrenom() + ". J'ai bien reçu votre demande de consultation du " + consultation.getDate() + " à " + consultation.getHeure()
                    + ".\n Vous pouvez dès à présent me contacter au " + consultation.getEmploye().getTelephone() + ". A tout de suite ! Médiumiquement vôtre, Mme Irma";
            Message.envoyerNotification(client.getNumTel(), msgClient);

            if (Saisie.lireInteger("En panne d'inspiration ? Obtenez trois predictions\n 1.Oui \n 2.Non", choix) == 1){
                generatePrediction(client);
            }

            String commentaire = Saisie.lireChaine("Un commentaire pour vos collègues : ");

            sc.finConsultation(consultation, commentaire);
        }
    }
}