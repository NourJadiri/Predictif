package metier.modele;

import util.AstroNetApi;

import javax.persistence.Embeddable;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Embeddable
public class ProfilAstral implements Serializable {

    String signeZodiaque;
    String signeChinois;
    String couleur;
    String animal;

    public ProfilAstral() {

    }

    public ProfilAstral( String prenom , Date date ){
        AstroNetApi astroNetApi = new AstroNetApi();
        try {
            List<String> profil = astroNetApi.getProfil(prenom, date);
            initProfilAstral(profil);
        } catch (IOException ex) {
            Logger.getLogger(ProfilAstral.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private void initProfilAstral(List<String> profil){
        signeZodiaque = profil.get(0);
        signeChinois = profil.get(1);
        couleur = profil.get(2);
        animal = profil.get(3);
    }

    public String getSigneZodiaque() {
        return signeZodiaque;
    }

    public void setSigneZodiaque(String signeZodiaque) {
        this.signeZodiaque = signeZodiaque;
    }

    public String getSigneChinois() {
        return signeChinois;
    }

    public void setSigneChinois(String signeChinois) {
        this.signeChinois = signeChinois;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public String getAnimal() {
        return animal;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }
}
