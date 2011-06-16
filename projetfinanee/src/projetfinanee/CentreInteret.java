
package projetfinanee;

public class CentreInteret {

    //variables de la classe
    private String libelle;
    private String categorie;

    public CentreInteret(String libelle, String categorie) {
        //on initialise les variables
        this.libelle = libelle;
        this.categorie = categorie;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString(){
        return "CentreInteret{libelle="+this.libelle+", categorie="+this.categorie+"}";
    }
}
