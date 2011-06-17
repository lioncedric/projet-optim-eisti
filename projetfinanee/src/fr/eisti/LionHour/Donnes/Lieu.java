package fr.eisti.LionHour.Donnes;

/******************************************************************************
 *Description: classe qui permet de définir un lieu
 * @author HOURCLATS Teddy, LION Cédric
 * @version 1.0
 ******************************************************************************/
public class Lieu {

    //variables de la classe
    private int idLieu;
    private String pays;
    private String ville;
    private String adresse;
    private int numero;

    /************************************************************************************************************
    *Description: permet de créer un lieu à partir d'un idLieu, d'un pays, d'une ville, d'une adresse et d'un numero
    *@param idLieu un entier
    *@param pays une chaine
    *@param ville une chaine
    *@param adresse une chaine
    *@param numero un entier
    *************************************************************************************************************/
    public Lieu(int idLieu, String pays, String ville, String adresse, int numero) {
        this.idLieu = idLieu;
        this.pays = pays;
        this.ville = ville;
        this.adresse = adresse;
        this.numero = numero;
    }

    /************************************************************************************************************
    *Description: permet de créer un lieu sans aucun paramètre
    *************************************************************************************************************/
    public Lieu() {
        this.idLieu = 0;
        this.pays = "";
        this.ville = "";
        this.adresse = "";
        this.numero = 1;
    }

    /************************************************************************************************************
    *Description: permet de récupérer l'adresse du lieu
    *@return retourne une chaîne
    *************************************************************************************************************/
    public String getAdresse() {
        return adresse;
    }

    /************************************************************************************************************
    *Description: permet de récupérer l'idLieu du lieu
    *@return retourne un entier
    *************************************************************************************************************/
    public int getIdLieu() {
        return idLieu;
    }

    /************************************************************************************************************
    *Description: permet de récupérer le numero du lieu
    *@return retourne un entier
    *************************************************************************************************************/
    public int getNumero() {
        return numero;
    }

    /************************************************************************************************************
    *Description: permet de récupérer le pays du lieu
    *@return retourne une chaîne
    *************************************************************************************************************/
    public String getPays() {
        return pays;
    }

    /************************************************************************************************************
    *Description: permet de récupérer la ville du lieu
    *@return retourne une chaîne
    *************************************************************************************************************/
    public String getVille() {
        return ville;
    }

    /************************************************************************************************************
    *Description: permet de récupérer un lieu sous forme de chaine
    *@return retourne une chaîne
    *************************************************************************************************************/
    @Override
    public String toString() {
        return "au "+this.numero+" rue "+this.adresse+" à "+this.ville+" en "+this.pays;
    }




}
