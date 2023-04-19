package metier.modele;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Consultation {


    public Consultation() {
    }

    public Consultation(Date date, Date heure, String commentaire, Employe employe, Medium medium, Client client) {
        this.date = date;
        this.heure = heure;
        this.commentaire = commentaire;
        this.employe = employe;
        this.medium = medium;
        this.client = client;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Temporal(TemporalType.DATE)
    private Date date;
    @Temporal(TemporalType.TIME)
    private Date heure;
    private String commentaire;

    @ManyToOne
    private Employe employe;
    @ManyToOne
    private Medium medium;
    @ManyToOne
    private Client client;

}
