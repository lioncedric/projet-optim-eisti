package fr.eisti.optimEisti_RaLiGaKl.model;

import fr.eisti.optimEisti_RaLiGaKl.view.problemes.PanelResultat;
import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Classe qui permet de générer la solution à un problème d'optimisation mathématique
 * @author Razavet Maël, Lion Cédric, Klelifa Sarah, Gallet Mériadec
 */
public class Simplexe {

    public static ArrayList<Double> start(double[][] matrice, ArrayList<Integer> numVarArti, int nbvar, String objectif) {
        /**
         * Constructeur qui permet de creer la soluton d'un problème
         * @param matrice probleme normalisé sous forme de tableau
         * @param nbVariables : nombre de variables imaginaires
         */
        //declaration du tableau destine a contenir la solution
        return init(matrice, numVarArti, nbvar, objectif);
    }

    /**
     * Procedure qui s'occupe du traitement a faire pour generer la solution au probleme en appelant les autres fonctions
     * @param matrice 
     */
    public static ArrayList<Double> init(double[][] matrice, ArrayList<Integer> numVarArti, int nbvar, String objectif) {

        //booleen permettant de savoir si le probleme est resolu ou pas
        boolean resolu = false;
        boolean noSolution = false;
        ArrayList<Double> resultat = new ArrayList<Double>();


        //on fait les operations de Gauss sur la matrice tant que le probleme n'est pas resolu
        while (!resolu) {
            int[] tabCoordPivot;
            //on recupere ces coordonnees en appelant la fonction adequate
            tabCoordPivot = chercherPivot(matrice);
            System.out.println("La valeur pivot est en ligne " + (tabCoordPivot[0] + 1) + " et en colonne" + (tabCoordPivot[1] + 1));
            PanelResultat.ecrire("La valeur pivot est en ligne " + (tabCoordPivot[0] + 1) + " et en colonne" + (tabCoordPivot[1] + 1));
            System.out.println("\n");
            PanelResultat.ecrire("\n");

            //si le probleme n'a pas tous les coefficients de la colonne pivot negatifs
            if (tabCoordPivot[0] != -50000 || tabCoordPivot[1] != -50000) {
                //on appelle la focntion qui permet de faire la reduction pivot
                reductionPivot(matrice, tabCoordPivot);
                resolu = problemeResolu(matrice);
            } else {
                resolu = true;
                noSolution = true;
            }
        }

        //si le probleme n'admet pas de solution
        if (noSolution) {
            System.out.println("Votre probleme n'admet pas de solution !");
            PanelResultat.ecrire("Votre probleme n'admet pas de solution !");
            System.out.println("\n");
            PanelResultat.ecrire("\n");
        } else {
            //on stocke les valeurs de la solution dans la variable adéquate
            resultat = calculerSolution(matrice, numVarArti, nbvar, objectif);
            System.out.println("Le probleme est à présent terminé.\nOn ne peut pas itérer davantage.");
            PanelResultat.ecrire("Le probleme est à présent terminé.\nOn ne peut pas itérer davantage.");
            if (resultat.size() == 1) {
                resultat = null;
            }
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
        int colonneDuMaxDerniereLigne = -1;

        //pour chaque colonne de la matrice
        for (int j = 0; j < matrice[0].length - 1; j++) {
            //si la valeur du coefficient de cette colonne et de la derniere ligne est plus grand que le max
            if (matrice[i][j] > valeurMax) {
                //on definit le nouveau max
                valeurMax = matrice[i][j];
                //on stocke la ligne contenant le max
                colonneDuMaxDerniereLigne = j;
            }
        }

        //on initialise le min a une valeur tres grande afin de trouver forcement une valeur plus petite
        double valeurMin = 1000000;//le million, le million, ...
        //on initialise la ligne du min a -1.
        int ligneDuMinPourPivot = -1;

        //tous les coefficients de la colonne de pivot sont ils negatifs
        boolean bool = true;

        //pour chaque ligne sauf la derniere (autrement dit, pour toutes les lignes de contraintes)
        for (i = 0; i < matrice.length - 1; i++) {
            System.out.println(matrice[i][colonneDuMaxDerniereLigne]);
            //si la colonne pivot ne contient que des elements negatifs
            if (matrice[i][colonneDuMaxDerniereLigne] > 0) {
                //si la valeur du calcul est inferieure au min, on definit la nouvelle ligne contenant le pivot
                if ((matrice[i][matrice[0].length - 1] / matrice[i][colonneDuMaxDerniereLigne]) < valeurMin) {
                    valeurMin = matrice[i][matrice[0].length - 1] / matrice[i][colonneDuMaxDerniereLigne];
                    ligneDuMinPourPivot = i;
                    bool = false;
                }
            }
        }
        //si au final, tous les coefficients sont negatifs, on fixe les valeurs a -50000
        if (bool) {
            ligneDuMinPourPivot = -50000;
            colonneDuMaxDerniereLigne = -50000;
        }

        //on stocke la ligne contenant le min et celle contenant le max dans le tableau
        tab[0] = ligneDuMinPourPivot;
        tab[1] = colonneDuMaxDerniereLigne;
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
        int ligneDuMinPourPivot = tab[0];
        //on recupere la colonne contenant le pivot
        int colonneDuMaxDerniereLigne = tab[1];

        //si la valeur du pivot est differente de 1
        if (matrice[ligneDuMinPourPivot][colonneDuMaxDerniereLigne] != 1) {
            double pivot = matrice[ligneDuMinPourPivot][colonneDuMaxDerniereLigne];
            System.out.println("On divise les coeff de la ligne " + (ligneDuMinPourPivot + 1) + " par: " + pivot);
            PanelResultat.ecrire("On divise les coeff de la ligne " + (ligneDuMinPourPivot + 1) + " par: " + pivot);
            System.out.println("\n");
            PanelResultat.ecrire("\n");
            //pour chaque colonne de la matrice
            for (int j = 0; j < matrice[0].length; j++) {
                //on divise chacun des termes de la ligne par la valeur du pivot
                matrice[ligneDuMinPourPivot][j] = matrice[ligneDuMinPourPivot][j] / pivot;
            }
        }

        //pour chaque ligne de la matrice
        for (int i = 0; i < matrice.length; i++) {
            //si la ligne n'est pas celle contenant le pivot
            if (i != ligneDuMinPourPivot) {
                //on commence par trouver la valeur du coefficient grace a laquelle on va 
                //pouvoir mettre des 0 sur toute la colonne contenant le pivot
                double coeff = matrice[i][colonneDuMaxDerniereLigne];
                //pour chaque colonne
                for (int j = 0; j < matrice[0].length; j++) {
                    //on fait l'operation du pivot de Gauss pour mettre des 0
                    matrice[i][j] = matrice[i][j] - coeff * matrice[ligneDuMinPourPivot][j];
                }
            }
        }


        System.out.println("Nouveau tableau");
        PanelResultat.ecrire("Nouveau tableau");
        PanelResultat.ecrire("\n");
        //pour autant qu'il y a de contraintes (on va remplir toutes les lignes de la matrice sauf la derniere
        for (int i = 0; i < matrice.length; i++) {
            //pour toutes les colonnes de la matrice
            for (int j = 0; j < matrice[0].length; j++) {
                NumberFormat Myformat = NumberFormat.getInstance();
                Myformat.setMinimumFractionDigits(2);       //Nb de Digit mini
                Myformat.setMaximumFractionDigits(2);       //Nb de Digit Maxi
                String str = Myformat.format(matrice[i][j]);                      //Formatage str="12.45"
                System.out.print(" | " + str);
                PanelResultat.ecrire(" | " + str);
            }
            System.out.println("\n");
            PanelResultat.ecrire("\n");
        }
        PanelResultat.ecrire("\n");

    }

    /**
     * Procedure qui permet d'exporter la solution sous forme de tableau
     * @param matrice : matrice representant le probleme
     * @param numVarArti : nombre de variable artificielle
     * @param nbvar : nombre de variable
     * @param objectif : chaine qui permet de savoir si on minimise ou on maximise
     * @return le resultat du calcule
     */
    public static ArrayList<Double> calculerSolution(double[][] matrice, ArrayList<Integer> numVarArti, int nbvar, String objectif) {
        ArrayList<Double> resultat = new ArrayList<Double>();

        if (objectif.equalsIgnoreCase("maximiser")) {
            //le maximum est l'inverse de la valeur contenue dans la case [derniere ligne][derniere colonne] de la matrice
            resultat.add(-matrice[matrice.length - 1][matrice[0].length - 1]);
        } else {
            //le maximum est l'inverse de la valeur contenue dans la case [derniere ligne][derniere colonne] de la matrice
            resultat.add(matrice[matrice.length - 1][matrice[0].length - 1]);
        }

        ArrayList<Integer> listeNumeroColonnesId = new ArrayList<Integer>();
        listeNumeroColonnesId = trouverNumeroColonnesId(matrice);

        //pour toutes les colonnes sauf la derniere
        boolean sansSolution = false;
        /*int k = 0;
        while (k < listeNumeroColonnesId.size() && !sansSolution) {
            sansSolution = sansSolution || numVarArti.contains(listeNumeroColonnesId.get(k));
            k++;
            System.err.println("valeur du booleen: "+sansSolution);
        }*/

        //si le probleme admet une solution
        if (!sansSolution) {
            //pour chacune des colonnes des vraies variables
            for (int j = 0; j < nbvar; j++) {
                if (listeNumeroColonnesId.contains(j)) {
                    boolean aTrouveValeur1 = false;
                    int i = 0;
                    while (i < matrice.length && !aTrouveValeur1) {
                        if (matrice[i][j] == 1) {
                            aTrouveValeur1 = true;
                            resultat.add(matrice[i][matrice[0].length - 1]);
                        }
                        i++;
                    }
                } else {
                    resultat.add(0.0);
                }
            }
        }
        return resultat;
    }

    public static ArrayList<Integer> trouverNumeroColonnesId(double[][] matrice) {
        ArrayList<Integer> liste = new ArrayList<Integer>();
                System.out.println("taille de la matrice (nbColonnes): "+(matrice[0].length - 1));
                
        //ici, tout est inverse puisque les i sont les colonnes et les j les lignes, d'ou les matrice[j][i]
        for (int i = 0; i < matrice[0].length - 1; i++) {
            int nombreZero = 0;
            int nombreUn = 0;
            for (int j = 0; j < matrice.length; j++) {
                if (matrice[j][i] == 0) {
                    nombreZero++;
                } else if (matrice[j][i] == 1) {
                    nombreUn++;
                }
            }
            if (nombreUn == 1 && nombreZero == matrice.length - 1) {
                liste.add(i);
                System.out.println("numéro de la colonne des 1: "+i);
            }
        }
        return liste;
    }
}
