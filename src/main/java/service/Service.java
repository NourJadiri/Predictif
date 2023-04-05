package service;

import dao.ClientDao;
import dao.EmployeDao;
import dao.JpaUtil;
import metier.Client;
import metier.Employe;
import util.Message;

import java.util.List;

/*
La classe Service est la classe qui appelle toutes les fonctions qui servent
a créer les objets métier de l'application.
C'est aussi ici que sont appelées les méthodes qui servent pour ouvrir des
contextes de persistance etc...
*/

public class Service {

    EmployeDao employeDao;
    ClientDao clientDao;

    public Service() {

        employeDao = new EmployeDao();
        clientDao = new ClientDao();
    }

    public void initialiserEmployes(){

        Employe e1 = new Employe( "Hugo", "Victor", "0757575757", "vhugo", "oui");
        Employe e2 = new Employe( "Germain", "Victor", "0696969696", "vgermain", "oui");
        Employe e3 = new Employe("Favro" , "Samuel" , "0642049305" , "sfavro" , "motDePasse");
        Employe e4 = new Employe("Dekew" , "Simon" , "0713200950" , "sdekew" , "password");

        try{
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            employeDao.create(e1);
            employeDao.create(e2);
            employeDao.create(e3);
            employeDao.create(e4);
            JpaUtil.validerTransaction();
        }
        catch( Exception ex ){
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
        }
        finally{
            JpaUtil.fermerContextePersistance();
        }
    }

    public Employe trouverEmployeParId ( Long id ){
        Employe r;
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            r = employeDao.findById(id);
        }
        catch ( Exception ex ){
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            r = null;
        }
        finally{
            JpaUtil.fermerContextePersistance();
        }

        return r;
    }

    public List<Employe> listerTousEmployes(){

        List<Employe> employeList;
        try{
            // Cette ligne est importante !!
            JpaUtil.creerContextePersistance();

            employeList = employeDao.listAll();

        }
        catch (Exception ex){
            ex.printStackTrace();
            employeList = null;
        }
        finally{
            JpaUtil.fermerContextePersistance();
        }

        return employeList;
    }

    public Employe authentifierEmploye( String login , String motDePasse ){
        Employe r;

        try{
            JpaUtil.creerContextePersistance();
            r = employeDao.authenticate( login , motDePasse );
        }
        catch ( Exception ex ){
            ex.printStackTrace();
            r = null;
        }
        finally{
            JpaUtil.fermerContextePersistance();
        }

        return r;
    }

    public void inscriptionClient ( Client client ){
        try{
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            clientDao.create(client);
            Message.envoyerMail("gougle@gougle.com" , client.getMail() , "Confirmation d'inscription" , "oui");
            JpaUtil.validerTransaction();
        }
        catch (Exception ex){
            ex.printStackTrace();
            Message.envoyerMail("gougle@gougle.com" , client.getMail() , "Echec de l'inscription" , "non");
            JpaUtil.annulerTransaction();
        }
        finally{
            JpaUtil.fermerContextePersistance();
        }
    }
    public Client trouverClientParId ( Long id ){
        Client c;
        try{
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            c = clientDao.findById(id);
            System.out.println(c);
        }
        catch ( Exception ex ){
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            c = null;
        }
        finally{
            JpaUtil.fermerContextePersistance();
        }

        return c;
    }

    public Client authentifierClientParMail(String mail , String password ){
        Client r = null;

        try{
            JpaUtil.creerContextePersistance();
            r = clientDao.findByMail(mail);

            // Si le password est le meme
            if( r != null && password == r.getMotDePasse() ){
                System.out.println("Authentification réussie.");
                System.out.println("Bienvenue " + r.getPrenom());
                return r;
            }
            // Sinon
            else{
                System.err.println("Authentification echouée");
                return null;
            }
        }
        catch ( Exception ex ){
            ex.printStackTrace();
            return null;
        }
        finally{
            JpaUtil.fermerContextePersistance();
        }
    }

    public Client authentifierClientParId( Long id , String password ){
        Client r = null;

        try{
            JpaUtil.creerContextePersistance();
            r = clientDao.findById(id);

            if( r != null && password == r.getMotDePasse() ){
                System.out.println("Authentification réussie.");
                System.out.println("Bienvenue " + r.getPrenom());
                return r;
            }
            else{
                System.err.println("Authentification échouée.");
                return null;
            }
        }
        catch ( Exception ex ){
            ex.printStackTrace();
            System.out.println("Mauvais Id");
            return null;
        }
        finally{
            JpaUtil.fermerContextePersistance();
        }
    }
}