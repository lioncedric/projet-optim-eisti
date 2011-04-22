package fr.eisti.OptimEisti.Model;

import java.util.ArrayList;
import fr.eisti.OptimEisti.View.Fenetre;
import fr.eisti.OptimEisti.View.JPanelProbleme;
import javax.swing.JTextField;

/**
 * classe relative a probleme
 * @author Razavet Maël, Lion Cédric, Klelifa Sarah, Gallet Meriadec
 */
public class Probleme {

    private int numero;
    private String description = "";
    private String titre = "";
    private String objectif = "";
    private ArrayList<Double> coeffVariables = new ArrayList<Double>();
    private ArrayList<Contrainte> contraintes = new ArrayList<Contrainte>();

    public Probleme() {
    }

    public Probleme(JPanelProbleme pFenetre) {
        renseignerProbleme(pFenetre);
    }

    /**
     * initialise le proble aux valeurs rentrées par l'utilisateur
     * @param fenetre le fenetre contenant les informations du probleme
     */
    public final void renseignerProbleme(JPanelProbleme fenetre) {
        this.titre = fenetre.getJtfTitre().getText();
        this.description = fenetre.getTextfield().getText();
          this.coeffVariables.clear();
        if (fenetre.getMaximiser().isSelected()) {
            this.objectif = "Maximiser";
        } else if (fenetre.getMinimiser().isSelected()) {
            this.objectif = "Minimiser";
        } else {
        }
        for (int i = 0; i < fenetre.getSlide().getValue(); i++) {
            JTextField jtf = (JTextField) (fenetre.getPanDonnees().getComponent(3 * i + 1));
            this.coeffVariables.add(Double.valueOf(jtf.getText()));
        }
        this.setContraintes(fenetre.getPanTableau().enregistrerContraintes());
    }

    public double[][] formaliserProbleme() {
        //declaration de la matrice representant le probleme pour l'algo du simplexe
        double[][] matrice;
        //declaration de la variable qui stocke le nombre de variables imaginaires (les Ei)
        int nbVariablesImg;

        //appel de la fonction recuperant le nombre de variables imaginaires
        nbVariablesImg = combienVariablesImg();
        //on peut a present declarer la taille de la matrice
        matrice = new double[this.contraintes.size() + 1][this.coeffVariables.size() + nbVariablesImg + 1];
        //on initialise la matrice avec des zeros
        initMatrice(matrice, nbVariablesImg);
        //on rempli la matrice avec les differentes donnees du probleme a resoudre (variables, contraintes)
        remplirMatrice(matrice, nbVariablesImg);
        afficherMatrice(matrice, nbVariablesImg);
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
     * Procedure permettant d'initialiser une matrice avec des 0
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
     * Procedure qui remplit la matrice avec tous les elements d'un probleme
     * @param matrice : matrice representant le probleme
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

        for (int i = 0; i < nb; i++) {
            //pour toutes les colonnes de la matrice
            int cpt = 0;
            for (int j = 0; j < this.coeffVariables.size(); j++) {
                //on remplit la matrice avec les coefficients de chacune des contraintes du probleme
                if (this.contraintes.get(i).getInegalite().equals("Infériorité")) {
                    matrice[i][cpt] = this.contraintes.get(i).getCoeffVariables().get(j);
                } else if (this.contraintes.get(i).getInegalite().equals("superiorité")) {
                    matrice[i][cpt] = -this.contraintes.get(i).getCoeffVariables().get(j);
                } else {
                    matrice[i][cpt] = this.contraintes.get(i).getCoeffVariables().get(j);
                    cpt++;
                    matrice[i][cpt] = -this.contraintes.get(i).getCoeffVariables().get(j);
                }
                cpt++;

            }
            //on remplit la matrice avec des 1 en les decalant a chaque iteration d'une case de plus.
            matrice[i][cpt + i] = 1;
            //on remplit la derniere colonne de la matrice avec les bi, qui sont les constantes de chaque contrainte
            matrice[i][this.coeffVariables.size() + nb] = this.contraintes.get(i).getConstante();
        }
        //pour chaque colonne de la derniere ligne
        for (int j = 0; j < this.coeffVariables.size(); j++) {
            //on ajoute les coefficients de la fonction a maximiser
            matrice[this.contraintes.size()][j] = this.coeffVariables.get(j);
        }


    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the titre
     */
    public String getTitre() {
        return titre;
    }

    /**
     * @param titre the titre to set
     */
    public void setTitre(String titre) {
        this.titre = titre;
    }

    /**
     * @return the objectif
     */
    public String getObjectif() {
        return objectif;
    }

    /**
     * @param objectif the objectif to set
     */
    public void setObjectif(String objectif) {
        this.objectif = objectif;
    }

    /**
     * @return the coeffVariables
     */
    public ArrayList<Double> getCoeffVariables() {
        return coeffVariables;
    }

    /**
     * @param coeffVariables the coeffVariables to set
     */
    public void setCoeffVariables(ArrayList<Double> coeffVariables) {
        this.coeffVariables = coeffVariables;
    }

    /**
     * @return the contraintes
     */
    public ArrayList<Contrainte> getContraintes() {
        return contraintes;
    }

    /**
     * @param contraintes the contraintes to set
     */
    public void setContraintes(ArrayList<Contrainte> contraintes) {
        this.contraintes = contraintes;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
}
