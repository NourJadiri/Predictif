package dao;

import metier.modele.Client;
import metier.modele.Consultation;
import metier.modele.Employe;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class ConsultationDao {

    public ConsultationDao() {
    }

    public void create(Consultation consultation){
        JpaUtil.obtenirContextePersistance().persist(consultation);
    }

    // Retourne les cinq dernières consultations terminées
    public List<Consultation> listerConsultationsRecentes(Client client){
        EntityManager em = JpaUtil.obtenirContextePersistance();

        String queryString = "SELECT c FROM Consultation c " +
                            "WHERE c.client = :client " +
                            "AND c.etatConsultation = :termine "+
                            "ORDER BY c.date, c.heure desc";

        TypedQuery<Consultation> query = em.createQuery(queryString, Consultation.class);
        query.setParameter("client" , client);
        query.setParameter("termine", Consultation.etat.TERMINEE);

        return query.setMaxResults(5).getResultList();
    }

    // Retourne tous l'historique des consultations terminées d'un client
    public List<Consultation> getConsultationHistory(Client client){
        EntityManager em = JpaUtil.obtenirContextePersistance();

        String queryString = "SELECT c FROM Consultation c " +
                "WHERE c.client = :client " +
                "AND c.etatConsultation = :termine "+
                "ORDER BY c.date, c.heure desc";

        TypedQuery<Consultation> query = em.createQuery(queryString, Consultation.class);
        query.setParameter("client" , client);
        query.setParameter("termine", Consultation.etat.TERMINEE);

        return query.getResultList();
    }

    public List<Consultation> getPendingConsultations(Client client){
        EntityManager em = JpaUtil.obtenirContextePersistance();

        String queryString = "SELECT c FROM Consultation c " +
                "WHERE c.client = :client " +
                "AND c.etatConsultation = :enAttente "+
                "ORDER BY c.date, c.heure desc";

        TypedQuery<Consultation> query = em.createQuery(queryString, Consultation.class);
        query.setParameter("client" , client);
        query.setParameter("enAttente", Consultation.etat.EN_ATTENTE);

        return query.getResultList();
    }

    public void updateCommentaire(Consultation consultation) {
        EntityManager em = JpaUtil.obtenirContextePersistance();

        String queryString = "UPDATE Consultation SET commentaire = :commentaire " +
                "WHERE id = :consultationId";

        TypedQuery<Consultation> query = em.createQuery(queryString, Consultation.class);
        query.setParameter("commentaire", consultation.getCommentaire());
        query.setParameter("consultationId", consultation.getId());
        query.executeUpdate();
    }

    public void updateEtatConsultation(Consultation consultation) {
        EntityManager em = JpaUtil.obtenirContextePersistance();

        String queryString = "UPDATE Consultation SET etatConsultation = :etatConsultation " +
                "WHERE id = :consultationId";

        TypedQuery<Consultation> query = em.createQuery(queryString, Consultation.class);
        query.setParameter("etatConsultation", consultation.getEtatConsultation());
        query.setParameter("consultationId", consultation.getId());
        query.executeUpdate();
    }

    public List<Consultation> getPendingConsultations(Employe employe) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        String queryString = "SELECT c from Consultation c WHERE c.employe = :employe and c.etatConsultation = :etatConsultation" ;

        TypedQuery<Consultation> query = em.createQuery(queryString, Consultation.class);
        query.setParameter("employe",employe);
        query.setParameter("etatConsultation", Consultation.etat.EN_ATTENTE);
        return query.getResultList();
    }
}
