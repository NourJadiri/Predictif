package dao;

import metier.modele.Client;
import metier.modele.Consultation;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class ConsultationDao {

    public ConsultationDao() {
    }

    public void create(Consultation consultation){
        JpaUtil.obtenirContextePersistance().persist(consultation);
    }

    // Permet de trouver une liste de consultations à partir de l'id du client passé en paramètre
    public List<Consultation> listerConsultationsRecentes(Client client){
        EntityManager em = JpaUtil.obtenirContextePersistance();
        System.out.println(client);
        TypedQuery<Consultation> query = em.createQuery("SELECT c from Consultation c where c.client = :client order by c.date desc ", Consultation.class);
        query.setParameter("client",client);
        return query.setMaxResults(5).getResultList();
    }
}
