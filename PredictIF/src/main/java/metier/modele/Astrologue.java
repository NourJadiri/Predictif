/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

/**
 *
 * @author ghembise
 */
public class Astrologue extends Medium{
    
    private String formation;
    private String promotion;

    public Astrologue(String formation, String promotion, String denomination, String genre, String presentation) {
        super(denomination, genre, presentation);
        this.formation = formation;
        this.promotion = promotion;
    }
    
    
}
