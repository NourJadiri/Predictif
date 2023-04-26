/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.service;

import dao.*;
import metier.modele.*;
import util.Message;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ghembise
 */

public class Service {
    
    private String mail = "contact@predict.if"; 
    
    public Service(){
        
    }

    /**
     *  SECTION CLIENT
     */

    // L'inscription du client retourne l'id du client inscrit
    public Long inscriptionClient(Client client) {

        // Message succès inscription
        String msgSucces = "Bonjour " + client.getPrenom() + 
                " nous vous confirmons votre inscription au service PREDICT'IF. "
                + "Rendez-vous vite sur notre site pour consulter votre profil astrologique et profiter des dons incroyables de nos médiums"; 

        // Message echec inscription
        String msgErreur = "Bonjour " + client.getPrenom() +
                " votre inscription au service PREDICT'IF a malencontreusement échoué... Merci de recommencer ultérieurement";

        // Variable de retour
        Long res = null;
        JpaUtil.creerContextePersistance();

        try {
            JpaUtil.ouvrirTransaction();
            clientDao.create(client);
            JpaUtil.validerTransaction();
            res = client.getId();
            Message.envoyerMail(mail, client.getMail(), "Bienvenue chez PREDICT'IF", msgSucces);
        } catch (Exception e1) {
            Message.envoyerMail(mail, client.getMail(), "Echec de l'inscription chez PREDICT'IF", msgErreur);
            JpaUtil.annulerTransaction();
            res = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return res;
    }

    // Retourne le client trouvé si l'id existe dans la base de donnée
    // Retourne nullObject sinon
    public Client rechercherClientparId(Long id){
        Client resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            resultat = clientDao.findById(id);
        } catch (Exception e){
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }

    // Retourne le client authentifié grâce à son mail et son mot de passe
    public Client authentifierClientmail(String mail, String MDP){
        Client res = null;
        JpaUtil.creerContextePersistance();
        try {
            Client client = clientDao.findByMail(mail);
            if(client != null && client.getMotDePasse().equals(MDP)){
                res= client;
            }
        } catch (Exception e){
            res = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return res;
    }

    // Permet d'authentifier un client à travers son ID et son MDP
    public Client authentifierClientId(Long id, String MDP){
        Client res = null;
         JpaUtil.creerContextePersistance();
        try {
            Client client = clientDao.findById(id);
            if(client != null && client.getMotDePasse().equals(MDP)){
                res = client;
            }
        } catch (Exception e){
            res = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return res;
    }

    // Méthode utile pour éviter de retaper le code d'initialisation à la main
    public List<Client> initClients(){
        List<Client> clients = new ArrayList<>();

        // Create 5 Client objects
        Client client1 = new Client("John", "Doe", new Date(1990, 5, 1), "123 Main St", "johndoe@example.com", "555-1234", "password1");
        Client client2 = new Client("Jane", "Smith", new Date(1985, 9, 15), "456 Oak St", "janesmith@example.com", "555-5678", "password2");
        Client client3 = new Client("Alice", "Johnson", new Date(1995, 2, 28), "789 Elm St", "alicejohnson@example.com", "555-9012", "password3");
        Client client4 = new Client("Bob", "Williams", new Date(1978, 11, 10), "12 Maple St", "bobwilliams@example.com", "555-3456", "password4");
        Client client5 = new Client("Sarah", "Lee", new Date(1989, 7, 22), "34 Pine St", "sarahlee@example.com", "555-7890", "password5");

        clients.add(client1);
        clients.add(client2);
        clients.add(client3);
        clients.add(client4);
        clients.add(client5);

        for(Client c : clients){
            clientDao.create(c);
        }

        return clients;
    }

    /**
     *
     * SECTION : EMPLOYE
     *
     */
    public void ajouterEmploye( Employe e ){
        
        JpaUtil.creerContextePersistance();
        
        try {
            JpaUtil.ouvrirTransaction();
            employeDao.create(e);
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            JpaUtil.annulerTransaction();
            Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            JpaUtil.fermerContextePersistance();
        }
    }

    public Employe rechercherEmploye ( Long employeId ){
        JpaUtil.creerContextePersistance();
        Employe employe;
        try{
            employe = employeDao.findById(employeId);
        }catch (Exception ex){
            employe = null;
        }finally {
            JpaUtil.fermerContextePersistance();
        }

        return employe;
    }

    public List<Employe> initEmployes(){
        ArrayList<Employe> employes = new ArrayList<>();

        // Create 5 Employe objects
        Employe employe1 = new Employe("Smith", "John", 'M', "555-1234", "johnsmith@example.com", "password1");
        Employe employe2 = new Employe("Johnson", "Jane", 'F', "555-5678", "janejohnson@example.com", "password2");
        Employe employe3 = new Employe("Williams", "Bob", 'M', "555-9012", "bobwilliams@example.com", "password3");
        Employe employe4 = new Employe("Lee", "Sarah", 'F', "555-3456", "sarahlee@example.com", "password4");
        Employe employe5 = new Employe("Davis", "Mike", 'M', "555-7890", "mikedavis@example.com", "password5");

        employes.add(employe1);
        employes.add(employe2);
        employes.add(employe3);
        employes.add(employe4);
        employes.add(employe5);

        for(Employe e : employes){
            employeDao.create(e);
        }

        return employes;
    }

    /**
     * SECTION : CONSULTATION
     */

    public void ajouterConsultation(Consultation consultation){

        JpaUtil.creerContextePersistance();
        try{
            JpaUtil.ouvrirTransaction();
            consultationDAO.create(consultation);
            JpaUtil.validerTransaction();
        }
        catch (Exception ex){
            JpaUtil.annulerTransaction();
            Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            JpaUtil.fermerContextePersistance();
        }
    }

    public List<Consultation> listerConsultationsRecente(Client client){

        JpaUtil.creerContextePersistance();

        List<Consultation> top5;

        try{
            top5 = consultationDAO.listerConsultationsRecentes(client);
        }catch (Exception ex){
            top5=null;
            Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            JpaUtil.fermerContextePersistance();
        }

        return top5;
    }
    /**
     * SECTION : MEDIUM
     */

    public void ajouterMedium(Medium medium){
        JpaUtil.creerContextePersistance();

        try{
            JpaUtil.ouvrirTransaction();
            mediumDAO.create(medium);
            JpaUtil.validerTransaction();
        }catch( Exception ex ){
            JpaUtil.annulerTransaction();
            Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            JpaUtil.fermerContextePersistance();
        }

    }

    public Medium rechercherMediumParId(Long id){
        Medium medium;

        JpaUtil.creerContextePersistance();

        try{
            medium = mediumDAO.findById(id);
        }catch (Exception ex){
            medium = null;
            Logger.getLogger(Service.class.getName()).log(Level.SEVERE,null,ex);
        }finally {
            JpaUtil.fermerContextePersistance();
        }

        return medium;
    }

    public List<Medium> initMediums(){
        ArrayList<Medium> mediums = new ArrayList<>();

        // Creating Astrologue
        Astrologue astrologue1 = new Astrologue("Maxime Ganachaud", "H", "Basé à Lyon, Maxime Ganachaud est un des jeunes astrologues les plus talentueux de sa génération. Passionné depuis son plus jeune âge, il pratique exclusivement l’astrologie sidérale, qui offre selon lui une bien meilleure précision que l’astrologie dite « tropicale ». Ses prévisions sont ainsi très demandées en raison de leur grande justesse.", "Ecole Normale Supérieure", "2006");
        Astrologue astrologue2 = new Astrologue("Alix Tair", "F", "Basée à Marseille, Alix Tair est une astrologue reconnue pour son approche à la fois pragmatique et spirituelle de l'astrologie. Elle pratique notamment l'astrologie chinoise et l'astrologie aztèque pour offrir une analyse approfondie et originale à ses clients.", "Université Paris Diderot", "2010");
        Astrologue astrologue3 = new Astrologue("Sophie Rey", "F", "Sophie Rey est une astrologue humaniste et passionnée, basée à Toulouse. Elle pratique l'astrologie tropicale et met son expertise au service de ses clients pour leur permettre de mieux se comprendre et de mieux appréhender leur avenir.", "Institut Astrologique de Carthage", "2012");

        // Creating Cartomancien
        Cartomancien cartomancien1 = new Cartomancien("Mme Irma", "F", "Mme Irma, la cartomancienne de la famille depuis des générations, saura répondre à toutes vos questions les plus intimes et inavouables. Découvrez votre avenir grâce à ses cartes qui ne mentent jamais !");
        Cartomancien cartomancien2 = new Cartomancien("Ludovic le Magnifique", "M", "Ludovic le Magnifique, célèbre cartomancien de la région de Nice, saura vous éblouir par sa maîtrise des cartes. Il propose des consultations très appréciées pour leur spectacle et leur interactivité avec le public.");

        // Creating Spirite
        Spirite spirite1 = new Spirite("Marie Lune", "F", "Médium spirite depuis plus de 30 ans, j'ai développé mes dons grâce à mes expériences personnelles. J'apporte des réponses à vos questions grâce à l'aide de mes guides spirituels et je suis spécialisée dans la communication avec les défunts.", "Boule de cristal");
        Spirite spirite2 = new Spirite("Morgane d'Ecosse", "F", "Médium spirite depuis plus de 20 ans, je vous propose de découvrir votre avenir grâce à ma communication avec le monde des esprits. Avec bienveillance et empathie, je vous guide vers la clarté et la sérénité que vous cherchez.", "Tableau noir");
        Spirite spirite3 = new Spirite("Antoine Lune", "M", "Médium spirite depuis plus de 30 ans, je suis spécialisé dans les contacts avec les défunts et les entités éthérées. Grâce à mes dons exceptionnels, je vous apporte les réponses que vous cherchez sur les sujets les plus délicats.", "Verre d'eau");

        mediums.add(astrologue1);
        mediums.add(astrologue2);
        mediums.add(astrologue3);

        mediums.add(spirite1);
        mediums.add(spirite2);
        mediums.add(spirite3);

        mediums.add(cartomancien1);
        mediums.add(cartomancien2);


        for (Medium m : mediums) {
            mediumDAO.create(m);
        }

        return mediums;
    }

    protected ClientDao clientDao = new ClientDao();
    protected EmployeDao employeDao = new EmployeDao();
    protected ConsultationDAO consultationDAO = new ConsultationDAO();
    protected MediumDAO mediumDAO = new MediumDAO();
}
