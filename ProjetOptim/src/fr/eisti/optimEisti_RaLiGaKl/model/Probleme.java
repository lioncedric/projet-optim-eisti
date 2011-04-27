package fr.eisti.optimEisti_RaLiGaKl.model;

import fr.eisti.optimEisti_RaLiGaKl.view.problemes.PanelProbleme;
import java.util.ArrayList;
import javax.swing.JTextField;

/**
 * Classe relative à un problème
 * @author Razavet Maël, Lion Cédric, Klelifa Sarah, Gallet Mériadec
 * @version: 1.0
 */
public class Probleme {

    //déclaration des variables nécessaires pour remplir un problème
    private int numero;
    private String description = "";
    private String titre = "";
    private String objectif = "";
    private ArrayList<Double> coeffVariables = new ArrayList<Double>();
    private ArrayList<Contrainte> contraintes = new ArrayList<Contrainte>();

    @Override
    public String toString() {
        return "Probleme{ description=" + description + "titre=" + titre + "objectif=" + objectif + "coeffVariables=" + coeffVariables + "contraintes=" + contraintes + '}';
    }

    public Probleme() {
    }

    /**
     * Constructeur permettant de créer un problème à l'aide des informations contenues dans la fenêtre passée en paramètre
     * @param pFenetre 
     */
    public Probleme(PanelProbleme pFenetre) {
        renseignerProbleme(pFenetre);
    }

    /**
     * initialise le problème aux valeurs rentrées par l'utilisateur
     * @param fenetre le fenetre contenant les informations du probleme
     */
    public final void renseignerProbleme(PanelProbleme fenetre) {
        //récupération du titre du problème
        this.titre = fenetre.getJtfTitre().getText();
        //récupération de la description du problème
        this.description = fenetre.getTextfield().getText();
        this.coeffVariables.clear();
        //récupération de l'objectif du problème
        if (fenetre.getMaximiser().isSelected()) {
            this.objectif = "Maximiser";
        } else if (fenetre.getMinimiser().isSelected()) {
            this.objectif = "Minimiser";
        } else {
        }
        //récupération des valeurs des variables du problème
        for (int i = 0; i < fenetre.getSlide().getValue(); i++) {
            JTextField jtf = (JTextField) (fenetre.getPanDonnees().getComponent(3 * i + 1));
            this.coeffVariables.add(Double.valueOf(jtf.getText()));
        }
        //récupération des contraintes du problème
        this.setContraintes(fenetre.getPanTableau().enregistrerContraintes());
    }

    /**
     * Fonction qui retourne le problème sous forme de matrice pour l'utilisation de la méthode de résolution du simplexe
     * @return matrice : la matrice correspondante au problème
     */
    public double[][] formaliserProbleme() {
        //declaration de la matrice representant le probleme pour l'algo du simplexe
        double[][] matrice;
        //declaration de la variable qui stocke le nombre de variables imaginaires (les Ei)
        int nbVariablesImg;

        //appel de la fonction recuperant le nombre de variables imaginaires
        nbVariablesImg = combienVariablesImg();
        //on peut a present declarer la taille de la matrice
        matrice = new double[nbVariablesImg + 1][this.coeffVariables.size() + nbVariablesImg + 1];
        //on initialise la matrice avec des zéros
        initMatrice(matrice, nbVariablesImg);
        //on rempli la matrice avec les différentes données du problème à résoudre (variables, contraintes)
        remplirMatrice(matrice, nbVariablesImg);
        afficherMatrice(matrice, nbVariablesImg);

        //on retourne la matrice
        return matrice;
    }

    /**
     * Fonction qui retourne le nombre de variables imaginaires
     * @return nb : nombre de variables imaginaires
     */
    public int combienVariablesImg() {
        //declaration et initialisation du nombre de variables necessaires
        int nb;
        nb = 0;
        //pour chaque contrainte du probleme
        for (int i = 0; i < this.contraintes.size(); i++) {
            //si c'est une contrainted'inegalite, c'est OK, ce n'est qu'une contrainte
            if (this.contraintes.get(i).getInegalite().equals("Infériorité") || this.contraintes.get(i).getInegalite().equals("Supériorité")) {
                nb += 1;
            } else {//c'est que l'on a une egalite que l'on peut reecrire sous forme de 2 inegalites superieur ou egal et inferieur ou egal
                nb += 2;
            }
        }
        //on retourne la valeur
        return nb;
    }

    /**
     * Procédure permettant d'initialiser une matrice avec des 0
     * @param matrice : matrice representant le probleme
     * @param nb : nombre de variables imaginaires
     */
    public void initMatrice(double[][] matrice, int nb) {
        //pour chaque contrainte + 1 car il y a la ligne des variables de la fonction a maximiser en plus
        for (int i = 0; i < nb + 1; i++) {
            //pour autant qu'il y a de coefficient dans les variables du probleme a resoudre (le nombre de Xi)
            for (int j = 0; j < this.coeffVariables.size() + nb + 1; j++) {
                //on initialise a 0
                matrice[i][j] = 0;
            }
        }
    }

    /**
     * Procédure qui remplit la matrice avec tous les éléments d'un problème
     * @param matrice : matrice représentant le problème
     * @param nb : nombre de variables imaginaires
     */
    public void afficherMatrice(double[][] matrice, int nb) {
        System.out.println("DEBUT remplissage");
        //pour autant qu'il y a de contraintes (on va remplir toutes les lignes de la matrice sauf la derniere
        for (int i = 0; i < nb + 1; i++) {
            //pour toutes les colonnes de la matrice
            for (int j = 0; j < this.coeffVariables.size() + nb + 1; j++) {
                System.out.print(" | " + matrice[i][j]);
            }
            System.out.print("\n");
        }
        System.out.println("FIN remplissage");
        System.out.println("\n");
    }

    /**
     * Procedure qui remplit la matrice avec tous les elements d'un probleme
     * @param matrice : matrice representant le probleme
     * @param nb : nombre de variables imaginaires
     */
    public void remplirMatrice(double[][] matrice, int nb) {
        //pour autant qu'il y a de contraintes (on va remplir toutes les lignes de la matrice sauf la derniere
        int cpt = 0;
        for (int i = 0; i < this.contraintes.size(); i++) {
            //pour toutes les colonnes de la matrice

            for (int j = 0; j < this.coeffVariables.size(); j++) {
                //on remplit la matrice avec les coefficients de chacune des contraintes du probleme
                if (this.contraintes.get(i).getInegalite().equals("Infériorité")) {

                    matrice[cpt][j] = this.contraintes.get(i).getCoeffVariables().get(j);
                } else if (this.contraintes.get(i).getInegalite().equals("Supériorité")) {
                    matrice[cpt][j] = -this.contraintes.get(i).getCoeffVariables().get(j);

                } else {
                    matrice[cpt][j] = this.contraintes.get(i).getCoeffVariables().get(j);
                    matrice[cpt + 1][j] = -this.contraintes.get(i).getCoeffVariables().get(j);
                }


            }
            //on remplit la matrice avec des 1 en les decalant a chaque iteration d'une case de plus.
            matrice[cpt][cpt + this.coeffVariables.size()] = 1;
            //on remplit la derniere colonne de la matrice avec les bi, qui sont les constantes de chaque contrainte
            matrice[cpt][this.coeffVariables.size() + nb] = this.contraintes.get(i).getConstante();

            if (this.contraintes.get(i).getInegalite().equals("Egalité")) {
                cpt++;
                matrice[cpt][cpt + this.coeffVariables.size()] = 1;
                //on remplit la derniere colonne de la matrice avec les bi, qui sont les constantes de chaque contrainte
                matrice[cpt][this.coeffVariables.size() + nb] = this.contraintes.get(i).getConstante();
            }
            cpt++;

        }

        //pour chaque colonne de la derniere ligne
        for (int j = 0; j < this.coeffVariables.size(); j++) {
            if (this.objectif.equals("Minimiser")) {
                matrice[nb][j] = -this.coeffVariables.get(j);
            } else {
                //on ajoute les coefficients de la fonction a maximiser
                matrice[nb][j] = this.coeffVariables.get(j);
            }
        }

    }

    public ArrayList<Double> getCoeffVariables() {
        return coeffVariables;
    }

    public ArrayList<Contrainte> getContraintes() {
        return contraintes;
    }

    public String getDescription() {
        return description;
    }

    public int getNumero() {
        return numero;
    }

    public String getObjectif() {
        return objectif;
    }

    public String getTitre() {
        return titre;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setObjectif(String objectif) {
        this.objectif = objectif;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setCoeffVariables(ArrayList<Double> coeffVariables) {
        this.coeffVariables = coeffVariables;
    }

    public void setContraintes(ArrayList<Contrainte> contraintes) {
        this.contraintes = contraintes;
    }
}
