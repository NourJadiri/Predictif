package dao;

import metier.Client;

import javax.persistence.TypedQuery;

public class ClientDao {
    public ClientDao() {
    }

    public void create ( Client client ){
            JpaUtil.obtenirContextePersistance().persist(client);
    }

    public Client findById( Long id ) {
        return JpaUtil.obtenirContextePersistance().find(Client.class, id);
    }

    public Client findByMail( String mail ){

        String queryString = "select c from Client c where c.mail = :mail";

        TypedQuery<Client> query = JpaUtil.obtenirContextePersistance().createQuery( queryString , Client.class );
        query.setParameter("mail" , mail);

        return query.getSingleResult();
    }



}
