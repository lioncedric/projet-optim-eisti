package fr.eisti.LionHour.Donnes;

import java.sql.Date;

/******************************************************************************
 *Description: classe qui permet de définir un sejour
 * @author HOURCLATS Teddy, LION Cédric
 * @version 1.0
 ******************************************************************************/
public class Sejour {

    //variables de la classe
    private Etablissement etab;
    private Date dateDebut;
    private Date dateFin;

    /************************************************************************************************************
    *Description: permet de créer un sejour à partir d'un établissement, d'une date de début et d'une date de fin
    *@param etab un etablissement
    *@param dateDebut une date
    *@param dateFin une date
    *************************************************************************************************************/
    public Sejour(Etablissement etab, Date dateDebut, Date dateFin) {
        this.etab = etab;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    /************************************************************************************************************
    *Description: permet de récupérer la dateDebut du sejour
    *@return retourne une date
    *************************************************************************************************************/
    public Date getDateDebut() {
        return dateDebut;
    }

    /************************************************************************************************************
    *Description: permet de récupérer la dateFin du sejour
    *@return retourne une date
    *************************************************************************************************************/
    public Date getDateFin() {
        return dateFin;
    }

    /************************************************************************************************************
    *Description: permet de récupérer l'etab du sejour
    *@return retourne un etablissement
    *************************************************************************************************************/
    public Etablissement getEtab() {
        return etab;
    }

    /************************************************************************************************************
    *Description: permet de récupérer un sejour sous forme de chaine
    *@return retourne une chaîne
    *************************************************************************************************************/
    @Override
    public String toString() {
        return "Sejour{etab=" + etab + ", dateDebut=" + dateDebut + ", dateFin=" + dateFin + '}';
    }


}
