package metier.modele;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Consultation {
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

    public Consultation() {
    }

    public Consultation(Date date, Date heure, Employe employe, Client client, Medium medium, String commentaire) {
        this.date = date;
        this.heure = heure;
        this.employe = employe;
        this.client = client;
        this.medium = medium;
        this.commentaire = commentaire;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
