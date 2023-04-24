package dao;

import metier.modele.Consultation;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class ConsultationDAO {

    public ConsultationDAO() {
    }

    public void create(Consultation consultation){
        JpaUtil.obtenirContextePersistance().persist(consultation);
    }

    // Permet de trouver une liste de consultations à partir de l'id du client passé en paramètre

}
