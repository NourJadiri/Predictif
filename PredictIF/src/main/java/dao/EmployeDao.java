/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import metier.modele.Employe;

/**
 * @author ghembise
 */
public class EmployeDao {

    public void create(Employe e) {
        JpaUtil.obtenirContextePersistance().persist(e);
    }

    public Employe findById(Long employeId) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        return em.find(Employe.class, employeId);
    }

    public Employe findByMail(String employeMail) {
        EntityManager em = JpaUtil.obtenirContextePersistance();

        TypedQuery<Employe> query = em.createQuery("SELECT e FROM Employe e Where e.email = :mail", Employe.class);
        query.setParameter("mail", employeMail);

        List<Employe> employes = query.getResultList();
        Employe result = null;
        if (!employes.isEmpty()) {
            result = employes.get(0);
        }
        return result;
    }

    public void UpdateDispotoIndisponible(Employe e) {
        EntityManager em = JpaUtil.obtenirContextePersistance();

        String queryString = "UPDATE Employe SET dispo = :dispoemploye " +
                "WHERE id = :employeId";


        TypedQuery<Employe> query = em.createQuery(queryString, Employe.class);
        query.setParameter("dispoemploye", e.getDispo());
        query.setParameter("employeId", e.getId());
        query.executeUpdate();
    }
    
    public Map<Employe, Long> nombreDeClientsParEmploye() {
        final String queryEmployeWithClients = "SELECT employe , COUNT(DISTINCT client) as nombreDeClientsParEmploye FROM consultation c where c.employe = :employe "; //Consultation avec C majuscule ou pas ???
        final String queryEmployeWithoutClients = "SELECT employe, 0 as nombreDeClientsParEmploye FROM Employe WHERE NOT EXISTS ( SELECT c FROM consultation c WHERE c.employe = :employe )"; //pas sure de cette requete a partir du where not exist
        final TypedQuery queryWithClients = JpaUtil.obtenirContextePersistance().createQuery(queryEmployeWithClients, (Class)Employe.class);
        final TypedQuery queryWithoutClients = JpaUtil.obtenirContextePersistance().createQuery(queryEmployeWithoutClients, (Class)Employe.class);
        final List<Object[]> resultatWithClients = (List<Object[]>)queryWithClients.getResultList();
        final List<Object[]> resultatWithoutClients = (List<Object[]>)queryWithoutClients.getResultList();
        final Map<Employe, Long> res = new HashMap<Employe, Long>();
        for (final Object[] entree : resWithClients) {
            res.put((Employe)entree[0], (Long)entree[1]);
        }
        for (final Object[] entree : resWithoutClients) {
            res.put((Employe)entree[0], 0L);
        }
        return res;
    }
    
    public Employee rqEmployeDisponible( Medium med) {
        final String s = "select e from employe where disponibilite = disponible and genre = med.genre";
        final TypedQuery queryDispo = JpaUtil.obtenirContextePersistance().createQuery(s, Employe.class);
        queryDispo.setParameter("médium", med);
    }
        final List<Employe> liste = null;
        try{
            liste = queryDispo.getResultList();
        }catch(Exception e){
            liste = null;
        }
        final Long minConsultation = 1000000L;
        final Employe emp = null;
        for (int i = 0; i < liste.size(); i++){
            s = "select COUNT(DISTINCT consultation) as nombreDeConsultationParEmploye  from Consultation c where c.Employe = :employe"; //je crois j'ai mélangé des trucs dans la requete help me
            final query = JpaUtil.obtenirContextePersistance().createQuery(s, Consultation.class);
            query.setParameter("employe", liste.get(i));
            final Long nbConsultation = 0L;
            try{
                nbConsultation = (Long) query.getSingleResult();
            }catch(Exception e){
                nbConsultation = 0L;
            }
            if (nbConsultation < minConsultation){
                minConsultation = nbConsultation;
                emp = liste.get(i);
            }
        }
        return emp;
      }

}
