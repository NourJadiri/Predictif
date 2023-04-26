package dao;

import metier.modele.Medium;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class MediumDao {
    public void create(Medium medium){
        EntityManager em = JpaUtil.obtenirContextePersistance();
        em.persist(medium);
    }

    public Medium findById(Long mediumId){
        EntityManager em = JpaUtil.obtenirContextePersistance();
        return em.find(Medium.class , mediumId);
    }

    public List<Medium> filter(String genre , ArrayList<String> types){
        EntityManager em = JpaUtil.obtenirContextePersistance();

        List<Medium> resultat;

        String queryBase = "SELECT m FROM Medium m WHERE ";

        String mediumGenderFilter = "";
        String mediumTypeFilter = "";

        // filtre par genre de médium
        // Si le genre n'est pas "all" on rentre dans le filtre
        if( genre != "all"){
            mediumGenderFilter = mediumGenderFilter + "m.genre = \"" + genre + "\" ";
            if(!types.isEmpty()){
                mediumGenderFilter += "AND";
            }
        }

        // filtre par type de médium
        if(!types.isEmpty()){
            for (int i = 0 ; i < types.size() ; i++) {
                mediumTypeFilter = mediumTypeFilter + " m.type_medium = \"" + types.get(i) + "\" ";
                if(i < types.size() - 1){
                    mediumTypeFilter += "OR ";
                }
            }
        }

        String queryString = queryBase + mediumGenderFilter + mediumTypeFilter;

        TypedQuery<Medium> query = em.createQuery(queryString , Medium.class);

        resultat = query.getResultList();

        return resultat;
    }
}
