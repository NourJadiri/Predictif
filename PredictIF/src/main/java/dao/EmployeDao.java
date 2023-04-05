/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import metier.modele.Employe;

/**
 *
 * @author ghembise
 */
public class EmployeDao {
    
    public void creer(Employe e){
        JpaUtil.obtenirContextePersistance().persist(e);
    }
    
    public Employe chercherParId(Long employeId){
        EntityManager em = JpaUtil.obtenirContextePersistance();
        return em.find(Employe.class, employeId);
    }
    
    public Employe chercherParMail(String employeMail){
        EntityManager em = JpaUtil.obtenirContextePersistance();
        
        TypedQuery<Employe> query = em.createQuery("SELECT e FROM Employe e Where e.email = :mail", Employe.class);
        query.setParameter("mail", employeMail);
        
        List<Employe> employes = query.getResultList();
        Employe result = null;
        if (!employes.isEmpty()){
            result = employes.get(0);
        }
        return result;
    }
    
}
