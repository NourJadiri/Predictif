package dao;

import metier.modele.Medium;

import javax.persistence.EntityManager;

public class MediumDao {
    public void create(Medium medium){
        EntityManager em = JpaUtil.obtenirContextePersistance();
        em.persist(medium);
    }

    public Medium findById(Long mediumId){
        EntityManager em = JpaUtil.obtenirContextePersistance();
        return em.find(Medium.class , mediumId);
    }
}
