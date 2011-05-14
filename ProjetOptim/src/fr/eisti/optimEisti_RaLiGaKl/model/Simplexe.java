/*
 * Classe qui permet de generer la solution du simplexe
 */
package fr.eisti.optimEisti_RaLiGaKl.model;

import fr.eisti.optimEisti_RaLiGaKl.view.problemes.PanelResultat;
import java.util.ArrayList;

/**
 * Classe qui permet de générer la solution à un problème d'optimisation mathématique
 * @author Razavet Maël, Lion Cédric, Klelifa Sarah, Gallet Mériadec
 */
public class Simplexe {

    public static ArrayList<Double> start(double[][] matrice,int nbvar, String objectif) {
        /**
         * Constructeur qui permet de creer la soluton d'un problème
         * @param matrice probleme normalisé sous forme de tableau
         * @param nbVariables : nombre de variables imaginaires
         */
        //declaration du tableau destine a contenir la solution
      
        return init(matrice, nbvar, objectif);
    }

    /**
     * Procedure qui s'occupe du traitement a faire pour generer la solution au probleme en appelant les autres fonctions
     * @param matrice 
     */
    public static ArrayList<Double> init(double[][] matrice, int nbvar, String objectif) {
        //booleen permettant de savoir si le probleme est resolu ou pas
        boolean resolu = false;
        boolean noSolution = false;
        ArrayList<Double> resultat=new ArrayList<Double>();

        //on fait les operations de Gauss sur la matrice tant que le probleme n'est pas resolu
        while (!resolu) {
            int[] tabCoordPivot;
            //on recupere ces coordonnees en appelant la fonction adequate
            tabCoordPivot = chercherPivot(matrice);

            //si le probleme n'a pas tous les coefficients de la colonne pivot negatifs
            if (tabCoordPivot[0] != -50000 || tabCoordPivot[1] != -50000) {
                //on appelle la focntion qui permet de faire la reduction pivot
                reductionPivot(matrice, tabCoordPivot);
                resolu = problemeResolu(matrice);
                System.out.println("Le probleme est-il resolu ? " + resolu);
                PanelResultat.ecrire("Le probleme est-il resolu ? " + resolu);
            } else {
                resolu = true;
                noSolution = true;
            }
        }

        //si le probleme n'admet pas de solution
        if (noSolution) {
            System.out.println("Votre probleme n'admet pas de solution !");
            PanelResultat.ecrire("Votre probleme n'admet pas de solution !");
        } else {
            //on stocke les valeurs de la solution dans la variable adéquate
            resultat=exporterSolution(matrice, nbvar, objectif);
        }
        return resultat;
    }

    /**
     * Fonction qui retourne un tableau de 2 variables contenant le numero de la ligne et le numero de la colonne ou se trouve le pivot dans la matrice
     * @param matrice : matrice representant le probleme
     * @return tab : un tableau de 2 variables contenant le numero de la ligne et le numero de la colonne ou se trouve le pivot dans la matrice
     */
    public static int[] chercherPivot(double[][] matrice) {
        //declaration du tableau
        int[] tab;
        tab = new int[2];
        //declaration et initialisation de la ligne a lire (la derniere) puisqu'une matrice commence a l'indice 0
        int i = matrice.length - 1;
        //on initialise le maximum a une valeur negative grande afin que l'on trouve a coup sur une valeur plus grande
        double valeurMax = -1000000;//le million, le million, ...
        //on initialise la ligne du max a -1.
        int ligneDuMax = -1;

        //pour chaque colonne de la matrice
        for (int j = 0; j < matrice[0].length - 1; j++) {
            //si la valeur du coefficient de cette colonne et de la derniere ligne est plus grand que le max
            if (matrice[i][j] > valeurMax) {
                //on definit le nouveau max
                valeurMax = matrice[i][j];
                //on stocke la ligne contenant le max
                ligneDuMax = j;
            }
        }
        System.out.println("valeur ligne du max:" + ligneDuMax);

        //on initialise le min a une valeur tres grande afin de trouver forcement une valeur plus petite
        double valeurMin = 1000000;//le million, le million, ...
        //on initialise la ligne du min a -1.
        int ligneDuMin = -1;

        //tous les coefficients de la colonne de pivot sont ils negatifs
        boolean bool = true;

        //pour chaque ligne sauf la derniere (autrement dit, pour toutes les lignes de contraintes)
        for (i = 0; i < matrice.length - 1; i++) {
            System.out.println(matrice[i][ligneDuMax]);
            //si la colonne pivot ne contient que des elements negatifs
            if (matrice[i][ligneDuMax] > 0) {
                //si la valeur du calcul est inferieure au min, on definit la nouvelle ligne contenant le pivot
                if ((matrice[i][matrice[0].length - 1] / matrice[i][ligneDuMax]) < valeurMin) {
                    valeurMin = matrice[i][matrice[0].length - 1] / matrice[i][ligneDuMax];
                    ligneDuMin = i;
                    bool = false;
                }
            }
        }
        //si au final, tous les coefficients sont negatifs, on fixe les valeurs a -50000
        if (bool) {
            ligneDuMin = -50000;
            ligneDuMax = -50000;
        }

        //on stocke la ligne contenant le min et celle contenant le max dans le tableau
        tab[0] = ligneDuMin;
        tab[1] = ligneDuMax;
        System.out.println("valeur:" + tab[0]);
        System.out.println("valeur:" + tab[1]);
        //on retourne le tableau
        return tab;
    }

    /**
     * Fonction qui dit si un probleme est resolu ou pas
     * @param matrice : matrice representant le probleme
     * @return resolu : un booleen selon si le probleme est resolu ou pas.
     */
    public static boolean problemeResolu(double[][] matrice) {
        //initialisation du booleen
        boolean resolu = true;
        //initilisation d'un indice de boucle
        int j = 0;
        //pour chaque colonne de la matrice
        while (j < matrice[0].length - 1) {
            //on regarde si toutes les valeurs de la derniere ligne sont positives ou nulle
            resolu = resolu && matrice[matrice.length - 1][j] <= 0;
            //on incremente la colonne
            j++;
        }
        //on retourne la valeur du booleen
        return resolu;
    }

    /**
     * Procedure permettant de faire la reduction de la matrice par la methode du pivot dze Gauss
     * @param matrice : matrice representant le probleme
     * @param tab : le tableau contenant le numero de la ligne et le numero de la colonne ou se trouve le pivot dans la matrice
     */
    public static void reductionPivot(double[][] matrice, int[] tab) {
        //on recupere la ligne contenant le pivot
        int min = tab[0];
        //on recupere la colonne contenant le pivot
        int max = tab[1];

        //si la valeur du pivot est differente de 1
        if (matrice[min][max] != 1) {
            double pivot = matrice[min][max];
            System.out.println("On divise les coeff de la ligne par: " + pivot);
            PanelResultat.ecrire("On divise les coeff de la ligne par: " + pivot);
            //pour chaque colonne de la matrice
            for (int j = 0; j < matrice[0].length; j++) {
                //on divise chacun des termes de la ligne par la valeur du pivot
                matrice[min][j] = matrice[min][j] / pivot;
            }
        }
        System.out.println("Etape 1");
        PanelResultat.ecrire("Etape 1");
        //pour autant qu'il y a de contraintes (on va remplir toutes les lignes de la matrice sauf la derniere
        for (int i = 0; i < matrice.length; i++) {
            //pour toutes les colonnes de la matrice
            for (int j = 0; j < matrice[0].length; j++) {
                System.out.print(" | " + matrice[i][j]);
            }
            System.out.print("\n");
            PanelResultat.ecrire("\n");
        }
        System.out.println("FIN etape 1");
        System.out.println("\n");
        PanelResultat.ecrire("FIN etape 1");
        PanelResultat.ecrire("\n");

        //pour chaque ligne de la matrice
        for (int i = 0; i < matrice.length; i++) {
            //si la ligne n'est pas celle contenant le pivot
            if (i != min) {
                //on commence par trouver la valeur du coefficient grace a laquelle on va 
                //pouvoir mettre des 0 sur toute la colonne contenant le pivot
                double coeff = matrice[i][max];
                //pour chaque colonne
                for (int j = 0; j < matrice[0].length; j++) {
                    //on fait l'operation du pivot de Gauss pour mettre des 0
                    matrice[i][j] = matrice[i][j] - coeff * matrice[min][j];
                }
            }
        }

        //pour autant qu'il y a de contraintes (on va remplir toutes les lignes de la matrice sauf la derniere
        for (int i = 0; i < matrice.length; i++) {
            //pour toutes les colonnes de la matrice
            for (int j = 0; j < matrice[0].length; j++) {
                System.out.print(" | " + matrice[i][j]);
                PanelResultat.ecrire(" | " + matrice[i][j]);
            }
            System.out.print("\n");
            PanelResultat.ecrire("\n");
        }
        System.out.println("\n");
        System.out.println("\n");

    }

    /**
     * Procedure qui permet d'exporter la solution sous forme de tableau
     * @param matrice : matrice representant le probleme
     * @param tabSolutions : le tableau destine a contenir la solution
     */
    public static ArrayList<Double> exporterSolution(double[][] matrice, int nbvar, String objectif) {
        //declaration d'un booleen
        boolean trouve = false;
        //declaration d'une variable ligne pour stocker le numero de la ligne ou il faudra lire la valuer du Xi
        int ligne = 0;
        ArrayList<Double> resultat=new ArrayList<Double>();

        if (objectif.equalsIgnoreCase("maximiser")) {
            //le maximum est l'inverse de la valeur contenue dans la case [derniere ligne][derniere colonne] de la matrice
            resultat.add(-matrice[matrice.length - 1][matrice[0].length - 1]);
        } else {
            //le maximum est l'inverse de la valeur contenue dans la case [derniere ligne][derniere colonne] de la matrice
            resultat.add(matrice[matrice.length - 1][matrice[0].length - 1]);
        }

        //pour les premieres colonnes (celles qui sont representatives des valeurs du probleme)
        for (int j = 0; j < nbvar; j++) {
            trouve = false;
            int i = 0;
            //on cherche la solution des Xi en regardant ou se trouve le 1 de la matrice identite
            int nbreDeZero = 0;
            while (i < matrice.length && !trouve) {
                if (matrice[i][j] == 0) {
                    nbreDeZero++;
                } else {
                }
                i++;
            }

            i = 0;
            if (nbreDeZero == matrice.length - 1) {
                while (i < matrice.length && !trouve) {
                    //si sur cette ligne se trouve le 1 ...
                    if (matrice[i][j] == 1) {
                        trouve = true;
                        //on stocke la ligne
                        ligne = i;
                    } else {
                    }
                    //on incremente le numero de la ligne courante
                    i++;
                }
                //on ajoute au tableau solution lavaleur qui se trouve sur la ligne trouvee et a la derniere colonne (coefficient bi ou i=ligne)
                resultat.add(matrice[ligne][matrice[0].length - 1]);

            } else {
                while (i < matrice.length && !trouve) {
                    //si sur cette ligne se trouve le 1 ...
                    if (matrice[i][j] == 0) {
                        trouve = true;
                        //on stocke la ligne
                        ligne = i;
                    } else {
                    }
                    //on incremente le numero de la ligne courante
                    i++;
                }
                //on ajoute au tableau solution lavaleur qui se trouve sur la ligne trouvee et a la derniere colonne (coefficient bi ou i=ligne)
                resultat.add(0.0);
            }
        }
        return resultat;
    }
}
