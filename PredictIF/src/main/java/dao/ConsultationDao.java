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

    public List<Consultation> listerConsultationsRecentes(Client client){
        EntityManager em = JpaUtil.obtenirContextePersistance();

        String queryString = "SELECT c FROM Consultation c " +
                            "WHERE c.client = :client "+
                            "ORDER BY c.date, c.heure desc";

        TypedQuery<Consultation> query = em.createQuery(queryString, Consultation.class);
        query.setParameter("client" , client);

        return query.setMaxResults(5).getResultList();
    }

    public List<Consultation> showHistoricConsultations(Client client){
        EntityManager em = JpaUtil.obtenirContextePersistance();

        String queryString = "SELECT c FROM Consultation c " +
                "WHERE c.client = :client "+
                "ORDER BY c.date, c.heure desc";

        TypedQuery<Consultation> query = em.createQuery(queryString, Consultation.class);
        query.setParameter("client" , client);

        return query.getResultList();
    }



}
