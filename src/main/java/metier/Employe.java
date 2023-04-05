package metier;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Employe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nom;
    private String prenom;
    private String telephonePro;
    private String login;
    private String motDePasse;

    public Employe(String nom, String prenom, String telephonePro, String login, String motDePasse) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephonePro = telephonePro;
        this.login = login;
        this.motDePasse = motDePasse;
    }

    public Employe() {

    }

    public Long getId() {
        return id;
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

    public String getTelephonePro() {
        return telephonePro;
    }

    public void setTelephonePro(String telephonePro) {
        this.telephonePro = telephonePro;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    @Override
    public String toString() {
        return "Employe{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", telephonePro='" + telephonePro + '\'' +
                ", login='" + login + '\'' +
                ", motDePasse='" + motDePasse + '\'' +
                '}';
    }
}
