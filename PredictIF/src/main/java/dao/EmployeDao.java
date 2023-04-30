/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public void updateDispotoIndispo(Employe e) {
        EntityManager em = JpaUtil.obtenirContextePersistance();

        String queryString = "UPDATE Employe SET dispo = :dispoemploye " +
                "WHERE id = :employeId";


        TypedQuery<Employe> query = em.createQuery(queryString, Employe.class);
        query.setParameter("dispoemploye", e.getDispo());
        query.setParameter("employeId", e.getId());
        query.executeUpdate();
    }
    
    public Map<Employe, Integer> sortEmployeByClientNumber(){
        EntityManager em = JpaUtil.obtenirContextePersistance();

        String queryString = "SELECT c.employe as employe, count(c) as nb_client " +
                "FROM Consultation c " +
                "GROUP BY c.employe ORDER BY nb_client desc";
        TypedQuery<Object[]> query = em.createQuery(queryString, Object[].class);

        List<Object[]> resultList = query.getResultList();
        Map<Employe,Integer> consultationNumberPerEmployee = new HashMap<>();

        for (Object[] objects : resultList ){
            Employe e = (Employe) objects[0];
            Integer nbConsultation = (Integer) objects[1];

            consultationNumberPerEmployee.put(e,nbConsultation);
        }

        return consultationNumberPerEmployee;
    }
    
    /*
        public Employe rqEmployeDisponible( Medium med ) {
            final String s = "select e from employe e where e.dispo != 0 and e.genre = :med.genre";
            final TypedQuery queryDispo = JpaUtil.obtenirContextePersistance().createQuery(s, Employe.class);
            queryDispo.setParameter("medium", med);
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
                final String query = JpaUtil.obtenirContextePersistance().createQuery(s, Consultation.class);
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
    */

}
