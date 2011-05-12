package fr.eisti.optimEisti_RaLiGaKl;


/**
 *
 * @author Administrator
 */
import fr.eisti.optimEisti_RaLiGaKl.model.Simplexe;
import fr.eisti.optimEisti_RaLiGaKl.view.compte.Accueil;
import fr.eisti.optimEisti_RaLiGaKl.view.problemes.Fenetre;

/**
 * Classe permettant de lancer le programme en appelant la page d'accueil
 * @author Razavet Maël, Lion Cédric, Klelifa Sarah, Gallet Mériadec
 */
public class Main2 {

    private static double[][] matrice;

    /**
     * @param args
     */
    public static void main(String[] args) {
        matrice = new double[3][6];
        remplirTableau();
        afficherMatrice(matrice, 3);
        //et on genere la solution par l'algorithme du simplexe
      //  Simplexe solution = new Simplexe(matrice, 3);
    }

    public static void remplirTableau() {
        int M = 10000;

        matrice[0][0] = 1;
        matrice[0][1] = 1;
        matrice[0][2] = -1;
        matrice[0][3] = 1;
        matrice[0][4] = 0;
        matrice[0][5] = 3;

        matrice[1][0] = 1;
        matrice[1][1] = -3;
        matrice[1][2] = 0;
        matrice[1][3] = 0;
        matrice[1][4] = 1;
        matrice[1][5] = 4;
        
        matrice[2][0] = 1 - 2 * M;
        matrice[2][1] = 2 * M - 2;
        matrice[2][2] = 2 + M;
        matrice[2][3] = 0;
        matrice[2][4] = 0;
        matrice[2][5] = -7 * M;
    }
    
        /**
     * Procédure qui remplit la matrice avec tous les éléments d'un problème
     * @param matrice : matrice représentant le problème
     * @param nb : nombre de variables imaginaires
     */
    public static void afficherMatrice(double[][] matrice, int nb) {
        System.out.println("DEBUT remplissage");
        //pour autant qu'il y a de contraintes (on va remplir toutes les lignes de la matrice sauf la derniere
        for (int i = 0; i < nb; i++) {
            //pour toutes les colonnes de la matrice
            for (int j = 0; j < 3 + nb; j++) {
                System.out.print(" | " + matrice[i][j]);
            }
            System.out.print("\n");
        }
        System.out.println("FIN remplissage");
        System.out.println("\n");
    }
}
