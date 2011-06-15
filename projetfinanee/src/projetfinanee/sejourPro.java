
package projetfinanee;

import java.sql.Date;


public class sejourPro extends Sejour{

    private String Poste;

    public sejourPro(Etablissement etab, Date dateDebut, Date dateFin, String Poste) {
        super(etab, dateDebut, dateFin);
        this.Poste = Poste;
    }
    
}
