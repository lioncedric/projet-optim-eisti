package projetfinanee;

/******************************************************************************
 *Description: classe qui permet de définir un lien d'amitié
 * @author HOURCLATS Teddy, LION Cédric
 * @version 1.0
 ******************************************************************************/
public class AreteValuee {

    //variables de la classe
    private Personne p1;
    private Personne p2;
    private int evaluation;

    /************************************************************************************************************
    *Description: permet de créer une AreteValuee en fonction d'une personne, de son ami et d'une valeur d'amitié
    *@param p1 la personne dont l'arete représente le lien d'amitié
    *@param p2 l'ami de p1
    *@param evaluation valeur de l'amitié entre p1 et p2
    *************************************************************************************************************/
    public AreteValuee(Personne p1, Personne p2, int evaluation) throws Exception {
        //si la valeur de l'évaluation n'est pas comprise entre 0 et 100
        if (evaluation > 100 || evaluation < 0) {
            //on lève une exception
            throw new Exception("une liason entre deux personnes doit etre entier et comprise entre 0 et 100");
        }
        //sinon
        else {
            this.p1 = p1;
            this.p2 = p2;
            this.evaluation = evaluation;
        }
    }

    /************************************************************************************************************
    *Description: permet de récupérer la valeur d'amitié
    *@return retourne evaluation
    *************************************************************************************************************/
    public int getEvaluation() {
        return evaluation;
    }

    /************************************************************************************************************
    *Description: permet de récupérer le sommet p1
    *@return retourne une Personne p1
    *************************************************************************************************************/
    public Personne getP1() {
        return p1;
    }

    /************************************************************************************************************
    *Description: permet de récupérer le sommet p2
    *@return retourne une Personne p2
    *************************************************************************************************************/
    public Personne getP2() {
        return p2;
    }

    /************************************************************************************************************
    *Description: permet de modifier la valeur du lien d'amitié en la remplaçant par la valeur placée en paramètre
    *@param evaluation un entier
    *************************************************************************************************************/
    public void setEvaluation(int evaluation) throws Exception   {
        //si la valeur de l'évaluation n'est pas comprise entre 0 et 100
        if (evaluation > 100 || evaluation < 0) {
            //on lève une exception
            throw new Exception("une liason entre deux personnes doit etre entier et comprise entre 0 et 100");
        }
        this.evaluation = evaluation;
    }

    /************************************************************************************************************
    *Description: permet de récupérer l'AreteValuee sous forme d'une chaine
    *@return retourne une chaine
    *************************************************************************************************************/
    @Override
    public String toString() {
        return "AreteValuee{p1=" + this.p1 + ", p2=" + this.p2 + ", valeur=" + this.evaluation + "}";
    }
}
