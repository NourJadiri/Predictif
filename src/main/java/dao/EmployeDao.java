package dao;
import metier.Employe;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class EmployeDao {

    public EmployeDao() {
    }

    public void create( Employe e ){
        JpaUtil.obtenirContextePersistance().persist(e);
    }


    public Employe findById ( Long id ){

        // obtenirContextePersistance() est une méthode protégée du package dao
        // Elle n'est appelées qu'au niveau de la dao
        return JpaUtil.obtenirContextePersistance().find(Employe.class, id);
    }

    public List<Employe> listAll(){
        // On note que les symboles de la query sont sensibles à la casse
        // On crée le String de la query
        String queryString = "select e from Employe e order by e.nom asc";

        // Puis on le balance dans une Typedquery qui l'execute
        TypedQuery<Employe> query = JpaUtil.obtenirContextePersistance().createQuery(queryString , Employe.class);

        // On retourne la liste des résultats
        return query.getResultList();
    }


    public Employe authenticate ( String login , String password ){
        String queryString = "select e from Employe e where e.login = :login and e.motDePasse = :password";

        TypedQuery<Employe> query = JpaUtil.obtenirContextePersistance().createQuery( queryString , Employe.class );
        query.setParameter("login" , login );
        query.setParameter( "password" , password );

        try{
            return query.getSingleResult();
        }
        catch ( NoResultException ex ){
            System.err.println("Nom d'utilisateur ou mot de passe incorrect");
            return null;
        }
    }
}

