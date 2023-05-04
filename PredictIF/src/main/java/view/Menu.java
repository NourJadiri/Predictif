package view;

import metier.modele.Client;
import metier.modele.Consultation;
import metier.modele.Employe;
import metier.modele.Medium;
import metier.service.Service;
import util.Message;
import util.Saisie;

import java.text.SimpleDateFormat;
import java.util.*;

public class Menu {

    public Menu() {
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
            if(client == null){
                client = authenticateClient();
            }
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
                "\n3. Voir vos consultations en attente" +
                "\n4. Voir votre liste de mediums préférés" +
                "\n5. Visualiser votre profil astral" +
                "\n6. Vous deconnecter", Arrays.asList(1,2,3,4,5,6));

        switch(action){
            case 1:
                startConsultationProcess(client);
                break;
            case 2:
                displayFiveLastConsultations(client);
                break;
            case 3:
                displayPendingConsultations(client);
                break;
            case 4:
                displayClientFavMediums(client);
                break;
            case 5:
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
        Client client;

        if(mail != null){
            String motDePasse = inputString("Et enfin, votre mot de passe");
            client = new Client(nom , prenom , dateNaissance , adressePostale , mail , motDePasse , numTel);
            sc.inscriptionClient(client);
        }
        else{
            client = null;
        }


        return client;
    }

    public static void displayFiveLastConsultations(Client client){
        Service sc = new Service();
        List<Consultation> recentConsultations = sc.listerConsultationsRecente(client);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");

        if(!recentConsultations.isEmpty()){
            for(Consultation c : recentConsultations){
                System.out.println("-Le " + dateFormat.format(c.getDate()) +
                        " à " + timeFormat.format(c.getDate()) + ", consultation avec " + c.getMedium().getDenomination()
                + ". Etat : " + c.getEtatConsultation().name());
            }
        }
        else{
            System.out.println("Vous n'avez aucune consultation !! Pensez à prendre rendez vous..");
        }
    }

    public static void displayClientFavMediums(Client client){
        Service sc = new Service();

        Map<Medium, Integer> favouriteMediums = sc.favouritesMediumsList(client);

        displayMapContent(favouriteMediums);

    }

    public static void displayProfilAstral(Client client){
        System.out.println(client.getProfilAstral());
    }

    public static void displayPendingConsultations(Client client){
        Service sc = new Service();
        System.out.println();
        System.out.println("Vos consultations en attente");
        System.out.println("----------------------------");

        List<Consultation> pendingConsultations = sc.getConsultationsEnAttente(client);

        for(Consultation c : pendingConsultations){
            System.out.println("Consultation avec " + c.getMedium().getDenomination());
        }
    }


    /// CHOIX DU MEDIUM
    public static void displayMediumList(List<Medium> mediumList){
        System.out.println("Les médiums qui répondent à vos filtres sont : ");
        for (int i = 0 ; i < mediumList.size() ; i++) {
            System.out.println((i+1) + ". " + mediumList.get(i).getDenomination());
            System.out.println(mediumList.get(i).getPresentation());
        }
    }

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
        types.add("Aucune préférence");

        ArrayList<String> typesChosen = new ArrayList<>();
        Integer keepFiltering = 1;
        Integer type = 99;

        while(keepFiltering == 1 && type != types.indexOf("Aucune préférence") + 1){

            System.out.println("Filtres :");
            for(int i = 0 ; i < types.size() ; i++){
                System.out.println((i+1) + " ." + types.get(i));
            }

            List<Integer> choixPossibles = new ArrayList<>();

            for(int i = 0 ; i < types.size() ; i++){
                choixPossibles.add(i + 1);
            }

            type = Saisie.lireInteger("Votre choix, client...", choixPossibles);

            String chosenType;

            if(type != types.indexOf("Aucune préférence") + 1){

                chosenType = types.get(type - 1);

                typesChosen.add(chosenType);
                types.remove(chosenType);
                type = 99;
            }
            else{
                // Si le client choisit au final de n'avoir aucune préférence on lui laisse pas les anciens filtres
                typesChosen.removeAll(typesChosen);
                chosenType = "Tout";
                keepFiltering = 2;
            }

            System.out.println("Vous avez choisi : " + chosenType);

            if(keepFiltering != 2){
                keepFiltering = Saisie.lireInteger("Voulez vous rajouter un nouveau filtre de type ?\n 1. Oui \n 2. Non" , Arrays.asList(1,2));
            }
        }


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
                "\n 4. Voir vos consultations en attentes" +
                "\n 5. Vous deconnecter", Arrays.asList(1,2,3,4,5));

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
            case 4:
                startAcceptConsultations(employe);
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

        displayMapContent(consultationsPerMedium);

    }

    public static void displayTopFiveMediums(){
        Service sc = new Service();

        System.out.println("TOP 5 DES MEDIUMS LES PLUS CONSULTES");
        System.out.println("------------------------------------");

        Map<Medium, Integer> consultationsPerMedium = sc.afficherRepartitionConsultationParMedium();

        int count = 0;

        System.out.println("Les cinq médiums les plus consultés");

        Iterator<Map.Entry<Medium, Integer>> itr = consultationsPerMedium.entrySet().iterator();

        if(consultationsPerMedium.isEmpty()){
            System.out.println("Aucun médium n'a encore été consulté");
        }

        while (itr.hasNext() && count < 5) {
            Map.Entry<Medium, Integer> entry = itr.next();

            Medium m = entry.getKey();
            Integer nbConsultations = entry.getValue();

            System.out.print(" -" + m.getDenomination() + " : " + nbConsultations);

            if(nbConsultations > 1){
                System.out.println(" consultations");
            }
            else if (nbConsultations == 1){
                System.out.println(" consultation");
            }
            count++;
        }

    }

    public static void displayClientRepartition(){
        Service sc = new Service();

        Map<Employe, Integer> clientPerEmploye = sc.afficherRepartitionClientParEmploye();

        Iterator<Map.Entry<Employe, Integer>> iter = clientPerEmploye.entrySet().iterator();

        while(iter.hasNext()){
            Map.Entry<Employe, Integer> entry = iter.next();
            Employe e = entry.getKey();
            Integer nbClients = entry.getValue();
            System.out.println(" -" + e.getPrenom() + " " + e.getNom() + " : " + nbClients);
        }
    }

    /// REPONSE EMPLOYE

    public static void alertEmploye(Consultation consultation){
        String msgEmploye = "Bonjour " + consultation.getEmploye().getPrenom() +
                ". Consultation requise pour " + consultation.getClient().getPrenom() +
                " " + consultation.getClient().getNom() + ".\n Médium à incarner : " + consultation.getMedium().getDenomination();

        Message.envoyerNotification(consultation.getEmploye().getTelephone(), msgEmploye);
    }
    private static void startAcceptConsultations(Employe employe) {
        Service sc = new Service();
        List<Integer> choix = new ArrayList<Integer>() {{
            add(1);
            add(2);
        }};
        List<Consultation> consultationList = sc.getConsultationsEnAttente(employe);
        List<Integer> choixconsultation = new ArrayList<Integer>() {{
            for (int i = 0; i < consultationList.size(); i++) {
                add(i+1);
            }
        }};

        StringBuilder s = new StringBuilder();
        System.out.println("Prendre une de vos consultations en attente :\n");
        for (Consultation consultation : consultationList) {
            s.append(consultationList.indexOf(consultation) + 1).append(". Client : ").append(consultation.getClient().getPrenom()).append(" || ").append(consultation.getClient().getNom()).append(" Medium : ").append(consultation.getMedium().getDenomination()).append("\n");
        }
        //System.out.println(consultationList);
        System.out.println(choixconsultation);

        Consultation consultation = consultationList.get(Saisie.lireInteger(String.valueOf(s), choixconsultation)-1);


        if (Saisie.lireInteger("1. Accepter \n2. Refuser", choix) == 1){

            sc.accepterConsultation(consultation);

            if (Saisie.lireInteger("En panne d'inspiration ? Obtenez trois predictions\n 1.Oui \n 2.Non", choix) == 1){
                generatePrediction(consultation.getClient());
            }

            String commentaire = Saisie.lireChaine("Un commentaire pour vos collègues : ");

            sc.finConsultation(consultation, commentaire);
        }
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


        Service sc = new Service();
        Medium medium = chooseMedium();

        Consultation consultation = sc.demanderConsultation(client, medium);
        alertEmploye(consultation);
    }



    /// UTIL
    private static void displayMapContent(Map<Medium, Integer> consultationsPerMedium) {
        Iterator<Map.Entry<Medium, Integer>> iter = consultationsPerMedium.entrySet().iterator();

        while(iter.hasNext()){
            Map.Entry<Medium, Integer> entry = iter.next();
            System.out.print(" - " + entry.getKey().getDenomination() + " : " + entry.getValue());
            if(consultationsPerMedium.size() == 1){
                System.out.println(" consultation");
            }
            else if(consultationsPerMedium.size() > 1){
                System.out.println(" consultations");
            }
        }
    }

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
                    System.out.print("oui/non");
                    yesOrNo = Saisie.lireChaine("");
                }while(!yesOrNo.equals("oui") && !yesOrNo.equals("non"));

                if(yesOrNo.equals("oui")){
                    mail = null;
                }
            }
        }while(test != null && yesOrNo.equals("non"));

        return mail;
    }
}
