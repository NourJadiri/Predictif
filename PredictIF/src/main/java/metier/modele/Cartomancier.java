/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author ghembise
 */
@Entity
@DiscriminatorValue("Cartomancier")
public class Cartomancier extends Medium{

    public Cartomancier(String denomination, String genre, String presentation) {
        super(denomination, genre, presentation);
    }

    public Cartomancier() {

    }
}
