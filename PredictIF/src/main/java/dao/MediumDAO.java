package dao;

import metier.modele.Medium;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import java.util.jar.JarEntry;

public class MediumDAO {
    public void create(Medium medium){
        EntityManager em = JpaUtil.obtenirContextePersistance();
        em.persist(medium);
    }

    public Medium findById(Long mediumId){
        EntityManager em = JpaUtil.obtenirContextePersistance();
        return em.find(Medium.class , mediumId);
    }
}
