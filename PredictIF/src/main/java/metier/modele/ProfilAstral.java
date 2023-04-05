package metier.modele;

import util.AstroNetApi;

import javax.persistence.Embeddable;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Embeddable
public class ProfilAstral {

    List<String> profil;
    AstroNetApi astroNetApi;

    public ProfilAstral() {

    }

    public ProfilAstral( String prenom , Date date) throws IOException {
        astroNetApi = new AstroNetApi();
        initProfil( prenom , date);
    }

    // Initialisation du profil astral à partir du prénom et de la date de naissance
    public void initProfil ( String prenom , Date date ) throws IOException {
        profil = astroNetApi.getProfil( prenom , date );
    }
}
