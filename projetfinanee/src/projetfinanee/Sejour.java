
package projetfinanee;

import java.sql.Date;


public class Sejour {

    private Etablissement etab;
    private Date dateDebut;
    private Date dateFin;

    public Sejour(Etablissement etab, Date dateDebut, Date dateFin) {
        this.etab = etab;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }


}
