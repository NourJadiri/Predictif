package dao;


import metier.modele.Medium;


import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class MediumDao {

    public void creer(Medium medium){
        EntityManager em = JpaUtil.obtenirContextePersistance();
        em.persist(medium);
    }
    public List<Medium> listerMedium() {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Medium> query = em.createQuery("SELECT m from Medium m", Medium.class);
        List<Medium> m = query.getResultList();
        em.close();
        return m;
    }

}
