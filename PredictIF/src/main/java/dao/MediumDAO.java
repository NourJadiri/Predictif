package dao;

import metier.modele.Medium;

import javax.persistence.Entity;
import javax.persistence.EntityManager;

public class MediumDAO {
    public void create(Medium medium){
        EntityManager em = JpaUtil.obtenirContextePersistance();
        em.persist(medium);
    }
}
