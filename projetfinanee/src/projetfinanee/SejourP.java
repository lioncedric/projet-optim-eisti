
package projetfinanee;

import java.sql.Date;


public class SejourP extends Sejour{

    private String Poste;

    public SejourP(Etablissement etab, Date dateDebut, Date dateFin, String Poste) {
        super(etab, dateDebut, dateFin);
        this.Poste = Poste;
    }

}
