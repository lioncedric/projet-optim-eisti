
package projetfinanee;

/**
 *
 * @author Administrator
 */
public class Etape {

    //variables de la classe
    private int id_Personne1;
    private int id_Personne2;
    private int valeur;
    private int type;

    public Etape(int type,int id_Personne1, int id_Personne2, int valeur) {
        this.id_Personne1 = id_Personne1;
        this.id_Personne2 = id_Personne2;
        this.valeur = valeur;
        this.type = type;
    }

    public int getId_Personne1() {
        return id_Personne1;
    }

    public void setId_Personne1(int id_Personne1) {
        this.id_Personne1 = id_Personne1;
    }

    public int getId_Personne2() {
        return id_Personne2;
    }

    public void setId_Personne2(int id_Personne2) {
        this.id_Personne2 = id_Personne2;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getValeur() {
        return valeur;
    }

    public void setValeur(int valeur) {
        this.valeur = valeur;
    }

    @Override
    public String toString() {
        return "Etape{" + "id_Personne1=" + id_Personne1 + "id_Personne2=" + id_Personne2 + "valeur=" + valeur + "type=" + type + '}';
    }

}
