
package projetfinanee;

import java.sql.Date;

public class SejourP extends Sejour{

    //variable de la classe
    private String poste;

    public SejourP(Etablissement etab, Date dateDebut, Date dateFin, String poste) {
        super(etab, dateDebut, dateFin);
        this.poste = poste;
    }

    @Override
    public String toString() {
        return super.toString()+" "+this.poste;
    }



}
