/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.service;

import dao.*;
import metier.modele.Client;
import metier.modele.Consultation;
import metier.modele.Employe;
import metier.modele.Medium;
import util.Message;

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

    /**
     *
     * SECTION : EMPLOYE
     *
     */
    public void initEmploye ( Employe e ){
        
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

    /**
     * SECTION : MEDIUM
     */

    public void ajouterMedium(Medium medium){
        JpaUtil.creerContextePersistance();

        try{
            JpaUtil.ouvrirTransaction();
            mediumDAO.create(medium);
            JpaUtil.validerTransaction();
            System.out.println(medium);
        }catch( Exception ex ){
            JpaUtil.annulerTransaction();
            Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            JpaUtil.fermerContextePersistance();
        }

    }

    protected ClientDao clientDao = new ClientDao();
    protected EmployeDao employeDao = new EmployeDao();
    protected ConsultationDAO consultationDAO = new ConsultationDAO();

    protected MediumDAO mediumDAO = new MediumDAO();
}
