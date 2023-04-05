/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.service;

import dao.ClientDao;
import dao.JpaUtil;
import metier.modele.Client;
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
    
    protected ClientDao clientDao = new ClientDao();
}
