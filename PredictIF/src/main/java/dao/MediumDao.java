package dao;

import metier.modele.Client;
import metier.modele.Medium;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        if(!genre.equals("all")){
            mediumGenderFilter = mediumGenderFilter + "m.genre = \"" + genre + "\" ";
            if(!types.isEmpty()){
                mediumGenderFilter += "AND";
            }
        }

        // filtre par type de médium
        // Si la liste est vide, on ne prend même pas le temps de filtrer par type
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

    public List<Medium> sortMediumsByNumberOfConsultations(Client client){
        EntityManager em = JpaUtil.obtenirContextePersistance();

        String queryString = "select c.medium as m , count(c) as nb_consultation " +
                "from Consultation c where c.client = :client group by m order by nb_consultation desc";

        TypedQuery<Object[]> query = em.createQuery(queryString, Object[].class);
        query.setParameter("client", client);
        List<Object[]> resultList = query.setMaxResults(3).getResultList();
        List<Medium> mediumList = new ArrayList<Medium>();
        for (Object[] objects : resultList) {
            Medium m = (Medium) objects[0];
            mediumList.add(m);
        }
        return mediumList;
    }
}
