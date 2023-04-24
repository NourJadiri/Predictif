/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import metier.modele.Client;

/**
 *
 * @author ghembise
 */
public class ClientDao {
    public void create(Client client){
        EntityManager em = JpaUtil.obtenirContextePersistance();
        em.persist(client);
    }
    
    public Client findById(Long clientId){
        EntityManager em = JpaUtil.obtenirContextePersistance();
        return em.find(Client.class, clientId);
    }
    
    public Client findByMail(String clientMail){
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Client> query = em.createQuery("SELECT c FROM Client c Where c.mail = :mail", Client.class);
        query.setParameter("mail", clientMail);

        // Extraction de la liste des clients qui ont
        List<Client> clients = query.getResultList();
        Client result = null;
        if (!clients.isEmpty()){
            result = clients.get(0);
        }
        return result;
    }
    
    
}
