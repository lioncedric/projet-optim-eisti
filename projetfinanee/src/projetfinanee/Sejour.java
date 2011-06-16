
package projetfinanee;

import java.sql.Date;

public class Sejour {

    //variables de la classe
    private Etablissement etab;
    private Date dateDebut;
    private Date dateFin;

    public Sejour(Etablissement etab, Date dateDebut, Date dateFin) {
        this.etab = etab;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public Etablissement getEtab() {
        return etab;
    }

    @Override
    public String toString() {
        return "Sejour{etab=" + etab + ", dateDebut=" + dateDebut + ", dateFin=" + dateFin + '}';
    }


}
