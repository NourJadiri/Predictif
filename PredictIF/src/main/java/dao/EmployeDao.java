/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import metier.modele.Consultation;
import metier.modele.Employe;
import metier.modele.Medium;


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

    public void updateDisponibilite(Employe e) {
        EntityManager em = JpaUtil.obtenirContextePersistance();

        String queryString = "UPDATE Employe SET dispo = :dispoEmploye " +
                "WHERE id = :employeId";


        TypedQuery<Employe> query = em.createQuery(queryString, Employe.class);
        query.setParameter("dispoEmploye", e.getDispo());
        query.setParameter("employeId", e.getId());
        query.executeUpdate();
    }


    public Map<Employe, Integer> sortEmployeByClientNumber(){
        EntityManager em = JpaUtil.obtenirContextePersistance();

        String queryString = "SELECT c.employe as employe, count(DISTINCT c) as nb_client " +
                "FROM Consultation c " +
                "GROUP BY c.employe ORDER BY nb_client desc";
        TypedQuery<Object[]> query = em.createQuery(queryString, Object[].class);

        List<Object[]> resultList = query.getResultList();
        Map<Employe,Integer> consultationNumberPerEmployee = new LinkedHashMap<>();

        for (Object[] objects : resultList ){
            Employe e = (Employe) objects[0];
            Integer nbConsultation = Math.toIntExact((Long) objects[1]);

            consultationNumberPerEmployee.put(e,nbConsultation);
        }

        return consultationNumberPerEmployee;
    }

    public Employe findAvailableEmploye(Medium medium) {
        EntityManager em = JpaUtil.obtenirContextePersistance();

        String queryString = "SELECT e, count(c) as nb_consultation from Employe e " +
                "left join Consultation c on c.employe = e " +
                "WHERE e.genre = :genreMedium AND e.dispo = :disponible " +
                "GROUP BY e ORDER BY nb_consultation";

        TypedQuery<Object[]> query = em.createQuery(queryString, Object[].class);
        query.setParameter("genreMedium", medium.getGenre());
        query.setParameter("disponible", Employe.disponibilite.DISPONIBLE);

        List<Object[]> resultList = query.setMaxResults(1).getResultList();

        return (Employe) resultList.get(0)[0];
    }


}
