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
//Je suis pas trop sur de ça car
// je pense pour les requetes de recherche on aura besoin de la table Medium + le ManytoOne dans consultations fonctionnera pas
//@MappedSuperclass
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="TYPE_MEDIUM")
public abstract class Medium implements Serializable {
    
    private String denomination;
    private String genre;
    private String presentation;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long mediumId;
    @OneToMany
    private final List<Consultation> consultations = new ArrayList<>();

    public Medium() {
    }

    public Medium(String denomination, String genre, String presentation) {
        this.denomination = denomination;
        this.genre = genre;
        this.presentation = presentation;
    }

    public String getDenomination() {
        return denomination;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPresentation() {
        return presentation;
    }

    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }

    public Long getMediumId() {
        return mediumId;
    }

    public void setMediumId(Long mediumId) {
        this.mediumId = mediumId;
    }
    
}
