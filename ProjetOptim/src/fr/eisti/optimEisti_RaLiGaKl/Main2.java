package fr.eisti.optimEisti_RaLiGaKl;


/**
 *
 * @author Administrator
 */
import fr.eisti.optimEisti_RaLiGaKl.model.Solution;
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
        //et on genere la solution par l'algorithme du simplexe
        Solution solution = new Solution(matrice, 3);
    }

    public static void remplirTableau() {
        int M = 10000;
        matrice[0][0] = 1 - 2 * M;
        matrice[0][1] = 2 * M - 2;
        matrice[0][2] = 2 + M;
        matrice[0][3] = 0;
        matrice[0][4] = 0;
        matrice[0][5] = -7 * M;

        matrice[1][0] = 1;
        matrice[1][1] = 1;
        matrice[1][2] = -1;
        matrice[1][3] = 1;
        matrice[1][4] = 0;
        matrice[1][5] = 3;

        matrice[2][0] = 1;
        matrice[2][1] = -3;
        matrice[2][2] = 0;
        matrice[2][3] = 0;
        matrice[2][4] = 1;
        matrice[2][5] = 4;
    }
}
