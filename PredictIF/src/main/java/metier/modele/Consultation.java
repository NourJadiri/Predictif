package metier.modele;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Consultation {

    public enum etat{
        EN_ATTENTE, EN_COURS, TERMINEE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Temporal(TemporalType.DATE)
    private Date date;
    @Temporal(TemporalType.TIME)
    private Date heure;
    @ManyToOne
    private Employe employe;
    @ManyToOne
    private Client client;
    @ManyToOne
    private Medium medium;

    private String commentaire;

    @Enumerated(EnumType.ORDINAL)
    private etat etatConsultation;

    public Consultation() {
    }

    public Consultation(Date date, Date heure, Employe employe, Client client, Medium medium) {
        this.date = date;
        this.heure = heure;
        this.employe = employe;
        this.client = client;
        this.medium = medium;
        this.commentaire = "";
        this.etatConsultation = etat.EN_ATTENTE;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getHeure() {
        return heure;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Long getId() {
        return id;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Medium getMedium() {
        return medium;
    }

    public void setMedium(Medium medium) {
        this.medium = medium;
    }

    public etat getEtatConsultation() {
        return etatConsultation;
    }

    public void setEtatConsultation(etat etatConsultation) {
        this.etatConsultation = etatConsultation;
    }

    @Override
    public String toString() {
        return "Consultation{" +
                "id=" + id +
                ", date=" + date +
                ", heure=" + heure +
                ", employe=" + employe +
                ", client=" + client +
                ", medium=" + medium +
                ", commentaire='" + commentaire + '\'' +
                '}';
    }
}
