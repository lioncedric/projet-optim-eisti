package fr.eisti.LionHour.Donnes;

/******************************************************************************
 *Description: classe qui permet de définir un centre d'intérêt
 * @author HOURCLATS Teddy, LION Cédric
 * @version 1.0
 ******************************************************************************/
public class CentreInteret {

    //variables de la classe
    private String libelle;
    private String categorie;

    /************************************************************************************************************
    *Description: permet de créer un centre d'intérêt à partir d'un libelle et d'une categorie
    *@param libelle une chaîne
    *@param categorie une chaîne
    *************************************************************************************************************/
    public CentreInteret(String libelle, String categorie) {
        //on initialise les variables
        this.libelle = libelle;
        this.categorie = categorie;
    }

    /************************************************************************************************************
    *Description: permet de récupérer la catégorie
    *@return retourne une chaîne dont la valeur est celle de la categorie
    *************************************************************************************************************/
    public String getCategorie() {
        return categorie;
    }

    /************************************************************************************************************
    *Description: permet de modifier la categorie en la remplaçant par la valeur placée en paramètre
    *@param categorie une chaine
    *************************************************************************************************************/
    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    /************************************************************************************************************
    *Description: permet de récupérer le libelle
    *@return retourne une chaîne dont la valeur est celle du libelle
    *************************************************************************************************************/
    public String getLibelle() {
        return libelle;
    }

    /************************************************************************************************************
    *Description: permet de modifier le libelle en le remplaçant par la valeur placée en paramètre
    *@param libelle une chaine
    *************************************************************************************************************/
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    /************************************************************************************************************
    *Description: permet de récupérer un centre d'interet sous forme de chaine
    *@return retourne une chaîne
    *************************************************************************************************************/
    @Override
    public String toString(){
        return "CentreInteret{libelle="+this.libelle+", categorie="+this.categorie+"}";
    }
}
