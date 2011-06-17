package projetfinanee;

import java.sql.Date;

/******************************************************************************
 *Description: classe qui permet de définir un sejourP
 * @author HOURCLATS Teddy, LION Cédric
 * @version 1.0
 ******************************************************************************/
public class SejourP extends Sejour{

    //variable de la classe
    private String poste;

    /************************************************************************************************************
    *Description: permet de créer un sejourP à partir d'un établissement, d'une date de début, d'une date de fin et d'un poste
    *@param etab un etablissement
    *@param dateDebut une date
    *@param dateFin une date
    *@param poste une chaîne
    *************************************************************************************************************/
    public SejourP(Etablissement etab, Date dateDebut, Date dateFin, String poste) {
        super(etab, dateDebut, dateFin);
        this.poste = poste;
    }

     /************************************************************************************************************
    *Description: permet de récupérer le poste du sejourP
    *@return retourne une chaîne
    *************************************************************************************************************/
    public String getPoste() {
        return poste;
    }

    /************************************************************************************************************
    *Description: permet de récupérer un sejourP sous forme de chaine
    *@return retourne une chaîne
    *************************************************************************************************************/
    @Override
    public String toString() {
        return super.toString()+" "+this.poste;
    }



}
