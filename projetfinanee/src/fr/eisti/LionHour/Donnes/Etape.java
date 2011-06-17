package fr.eisti.LionHour.Donnes;

/******************************************************************************
 *Description: classe qui permet de définir une etape, c'est-à-dire une action (creation, modification ou suppression)
 * @author HOURCLATS Teddy, LION Cédric
 * @version 1.0
 ******************************************************************************/
public class Etape {

    //variables de la classe
    private int id_Personne1;
    private int id_Personne2;
    private int valeur;
    private int type;

    /************************************************************************************************************
    *Description: permet de créer une etape à partir d'un type, d'un id_Personne1, d'un id_Personne2 et d'une valeur
    *@param type un entier permettant de savoir s'il s'agit d'une creation, d'une modification ou d'une suppression
    *@param id_Personne1 un entier
    *@param id_Personne2 un entier
    *@param valeur un entier
    *************************************************************************************************************/
    public Etape(int type,int id_Personne1, int id_Personne2, int valeur) {
        this.id_Personne1 = id_Personne1;
        this.id_Personne2 = id_Personne2;
        this.valeur = valeur;
        this.type = type;
    }

    /************************************************************************************************************
    *Description: permet de récupérer l'id_Personne1 de l'etape
    *@return retourne un entier
    *************************************************************************************************************/
    public int getId_Personne1() {
        return id_Personne1;
    }

    /************************************************************************************************************
    *Description: permet de modifier l'id_Personne1 de l'étape en le remplaçant par la valeur placée en paramètre
    *@param id_Personne1 un entier
    *************************************************************************************************************/
    public void setId_Personne1(int id_Personne1) {
        this.id_Personne1 = id_Personne1;
    }

    /************************************************************************************************************
    *Description: permet de récupérer l'id_Personne2 de l'etape
    *@return retourne un entier
    *************************************************************************************************************/
    public int getId_Personne2() {
        return id_Personne2;
    }

    /************************************************************************************************************
    *Description: permet de modifier l'id_Personne2 de l'étape en le remplaçant par la valeur placée en paramètre
    *@param id_Personne2 un entier
    *************************************************************************************************************/
    public void setId_Personne2(int id_Personne2) {
        this.id_Personne2 = id_Personne2;
    }

    /************************************************************************************************************
    *Description: permet de récupérer le type de l'etape
    *@return retourne un entier
    *************************************************************************************************************/
    public int getType() {
        return type;
    }

    /************************************************************************************************************
    *Description: permet de modifier le type de l'étape en le remplaçant par la valeur placée en paramètre
    *@param type un entier
    *************************************************************************************************************/
    public void setType(int type) {
        this.type = type;
    }

    /************************************************************************************************************
    *Description: permet de récupérer la valeur de l'etape
    *@return retourne un entier
    *************************************************************************************************************/
    public int getValeur() {
        return valeur;
    }

    /************************************************************************************************************
    *Description: permet de modifier la valeur de l'étape en le remplaçant par la valeur placée en paramètre
    *@param valeur un entier
    *************************************************************************************************************/
    public void setValeur(int valeur) {
        this.valeur = valeur;
    }

    /************************************************************************************************************
    *Description: permet de récupérer une etape sous forme de chaine
    *@return retourne une chaîne
    *************************************************************************************************************/
    @Override
    public String toString() {
        return "Etape{" + "id_Personne1=" + id_Personne1 + "id_Personne2=" + id_Personne2 + "valeur=" + valeur + "type=" + type + '}';
    }

}
