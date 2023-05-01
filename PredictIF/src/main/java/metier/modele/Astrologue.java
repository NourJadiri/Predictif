/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import javax.persistence.Entity;

/**
 *
 * @author ghembise
 */
@Entity
public class Astrologue extends Medium{
    
    private String formation;
    private String promotion;

    public Astrologue(String denomination, char genre, String presentation , String formation, String promotion) {
        super(denomination, genre, presentation);
        this.formation = formation;
        this.promotion = promotion;
    }


    public Astrologue() {

    }

}
