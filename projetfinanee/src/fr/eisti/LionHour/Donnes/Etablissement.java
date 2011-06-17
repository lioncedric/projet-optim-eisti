package fr.eisti.LionHour.Donnes;
/******************************************************************************
 *Description: classe qui permet de définir un etablissement
 * @author HOURCLATS Teddy, LION Cédric
 * @version 1.0
 ******************************************************************************/
public class Etablissement {

    //variables de la classe
    private int id;
    private String nom;
    private String description;
    private Lieu lieu;

    /************************************************************************************************************
    *Description: permet de créer un etablissement à partir d'un id, d'un nom et d'une description
    *@param id un entier utilisé comme clé unique pour chaque etablissement
    *@param nom une chaine
    *@param description une chaine
    *************************************************************************************************************/
    public Etablissement(int id, String nom, String description) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.lieu = new Lieu();
    }

    /************************************************************************************************************
    *Description: permet de récupérer l'id de l'etablissement
    *@return retourne un entier
    *************************************************************************************************************/
    public int getId() {
        return id;
    }

    /************************************************************************************************************
    *Description: permet de récupérer la description de l'etablissement
    *@return retourne une chaine
    *************************************************************************************************************/
    public String getDescription() {
        return description;
    }

    /************************************************************************************************************
    *Description: permet de récupérer le lieu de l'etablissement
    *@return retourne un lieu
    *************************************************************************************************************/
    public Lieu getLieu() {
        return lieu;
    }

    /************************************************************************************************************
    *Description: permet de récupérer le nom de l'etablissement
    *@return retourne une chaine
    *************************************************************************************************************/
    public String getNom() {
        return nom;
    }

    /************************************************************************************************************
    *Description: permet de modifier le lieu de l'établissement en le remplaçant par la valeur placée en paramètre
    *@param lieu un lieu
    *************************************************************************************************************/
    public void setLieu(Lieu lieu) {
        this.lieu = lieu;
    }

    /************************************************************************************************************
    *Description: permet de récupérer un etablissement sous forme de chaine
    *@return retourne une chaîne
    *************************************************************************************************************/
    @Override
    public String toString() {
        return "Etablissement{" + "id=" + id + "nom=" + nom + "description=" + description + "Lieu=" + lieu + '}';
    }
    
}
