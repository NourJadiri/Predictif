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
            signeZodiaque = profil.get(0);
            signeChinois = profil.get(1);
            couleur = profil.get(2);
            animal = profil.get(3);
        } catch (IOException ex) {
            Logger.getLogger(ProfilAstral.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
