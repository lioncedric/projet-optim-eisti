package fr.eisti.LionHour.Donnes;

/******************************************************************************
 *Description: classe qui permet de définir un choix. Ce choix permet d'associer
 *             une valeur à une personne (même principe qu'une map)
 * @author HOURCLATS Teddy, LION Cédric
 * @version 1.0
 ******************************************************************************/
public class Choix implements Comparable<Choix>{

    //variables de la classe
    private Personne p;
    private double interet;

    /************************************************************************************************************
    *Description: permet de créer un choix à partir d'une personne et d'un intérêt
    *@param p une personne
    *@param interet un réel
    *************************************************************************************************************/
    public Choix(Personne p, double interet) {
        this.p = p;
        this.interet = interet;
    }

    /************************************************************************************************************
    *Description: permet de récupérer un entier qui est positif, negatif ou nul suivant le résultat de la comparaison
     *            entre l'objet qui appelle cette fonction et le paramètre
    *@return retourne un entier
    *************************************************************************************************************/
    @Override
    public int compareTo(Choix o) {
      return (int)((interet-o.getInteret())*100);
    }

    /************************************************************************************************************
    *Description: permet de récupérer un choix sous forme de chaine
    *@return retourne une chaîne
    *************************************************************************************************************/
    @Override
    public String toString() {
        return "Choix{" + "p=" + p + "interet=" + interet + '}';
    }

    /************************************************************************************************************
    *Description: permet de récupérer l'interet du choix
    *@return retourne un réel
    *************************************************************************************************************/
    public double getInteret() {
        return interet;
    }

    /************************************************************************************************************
    *Description: permet de récupérer la personne associée au choix
    *@return retourne une personne
    *************************************************************************************************************/
    public Personne getP() {
        return p;
    }
 /************************************************************************************************************
    *Description: permet de definir l'interet du choix
    *@param interet un réel evaluant la qualité du choix
    *************************************************************************************************************/
    public void setInteret(double interet) {
        this.interet = interet;
    }

}
