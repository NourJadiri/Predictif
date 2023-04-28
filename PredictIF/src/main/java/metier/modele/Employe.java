/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
/**
 *
 * @author ghembise
 */

@Entity
public class Employe implements Serializable {
    public enum disponibilite{
        DISPONIBLE , INDISPONIBLE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)     
    Long id;
    
    private String nom;
    private String prenom;
    private char genre;
    private String telephone;
    private String email;
    private String motDePasse;
    private disponibilite dispo;



    public Employe() {
    }

    public Employe( String nom, String prenom, char genre, String telephone, String email, String motDePasse ) {
        this.nom = nom;
        this.prenom = prenom;
        this.genre = genre;
        this.telephone = telephone;
        this.email = email;
        this.motDePasse = motDePasse;
        this.dispo = disponibilite.DISPONIBLE;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public char getGenre() {
        return genre;
    }

    public void setGenre(char genre) {
        this.genre = genre;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public disponibilite getDispo() {
        return dispo;
    }

    public void setDispo(disponibilite dispo) {
        this.dispo = dispo;
    }

    @Override
    public String toString() {
        return "Employe{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", genre=" + genre + ", telephone=" + telephone + ", email=" + email + ", motDePasse=" + motDePasse + ", dispo=" + dispo + '}';
    }
    
}
