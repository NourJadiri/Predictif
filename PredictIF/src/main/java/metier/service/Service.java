/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.service;

import com.sun.prism.j2d.J2DPipeline;
import dao.ClientDao;
import dao.EmployeDao;
import dao.JpaUtil;
import java.util.logging.Level;
import java.util.logging.Logger;
import metier.modele.Client;
import metier.modele.Employe;
import util.Message;

/**
 *
 * @author ghembise
 */

public class Service {
    
    private String mail = "contact@predict.if"; 
    
    public Service(){
        
    }
    
    public Long inscriptionClient(Client client) {
        String msgSucces = "Bonjour " + client.getPrenom() + 
                " nous vous confirmons votre inscription au service PREDICT'IF. "
                + "Rendez-vous vite sur notre site pour consulter votre profil astrologique et profiter des dons incroyables de nos médiums"; 
        String msgErreur = "Bonjour " + client.getPrenom() +
                " votre inscription au service PREDICT'IF a malencontreusement échoué... Merci de recommencer ultérieurement";
        Long res = null;
        JpaUtil.creerContextePersistance();
        try {
            JpaUtil.ouvrirTransaction();
            clientDao.creer(client);
            JpaUtil.validerTransaction();
            res = client.getId();
            Message.envoyerMail(mail, client.getMail(), "Bienvenue chez PREDICT'IF", msgSucces);
        } catch (Exception e1){
            Message.envoyerMail(mail, client.getMail(), "Echec de l'inscription chez PREDICT'IF", msgErreur);
            JpaUtil.annulerTransaction();
            res = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return res;
    }
    
    
    
    public Client rechercherClientparId(Long id){
        Client resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            resultat = clientDao.chercherParId(id);
        } catch (Exception e){
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }
    
    public Client authentifierClientmail(String mail, String MDP){
        Client res = null;
         JpaUtil.creerContextePersistance();
        try {
            Client client = clientDao.chercherParMail(mail);
            if(client != null){
                if (client.getMotDePasse().equals(MDP)){
                    res= client;
                }
            }
            
        } catch (Exception e){
            res = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return res;
    }
    
    public Client authentifierClientId(Long id, String MDP){
        Client res = null;
         JpaUtil.creerContextePersistance();
        try {
            Client client = clientDao.chercherParId(id);
            if(client != null){
                if (client.getMotDePasse().equals(MDP)){
                    res = client;
                }
            }
        } catch (Exception e){
            res = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return res;
    }
    
    public void initEmploye ( Employe e ){
        
        JpaUtil.creerContextePersistance();
        
        try {
            JpaUtil.ouvrirTransaction();
            employeDao.creer(e);
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            JpaUtil.annulerTransaction();
            Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            JpaUtil.fermerContextePersistance();
        }
    }
    
    protected ClientDao clientDao = new ClientDao();
    protected EmployeDao employeDao = new EmployeDao();
}
