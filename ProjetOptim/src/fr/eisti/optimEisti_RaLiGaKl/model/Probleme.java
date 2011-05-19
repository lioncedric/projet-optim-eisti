package fr.eisti.optimEisti_RaLiGaKl.model;

import fr.eisti.optimEisti_RaLiGaKl.view.problemes.PanelProbleme;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTextField;

/**
 * Classe relative à un problème
 * @author Razavet Maël, Lion Cédric, Klelifa Sarah, Gallet Mériadec
 */
public class Probleme {

    //déclaration des variables nécessaires pour remplir un problème
    protected int numero;
    protected String description = "";
    protected String titre = "";
    protected String objectif = "";
    protected ArrayList<Double> coeffVariables = new ArrayList<Double>();
    protected ArrayList<Double> resultat = new ArrayList<Double>();
    protected ArrayList<Contrainte> contraintes = new ArrayList<Contrainte>();

    @Override
    public String toString() {
        return "Probleme{" + "description=" + description + "titre=" + titre + "objectif=" + objectif + "coeffVariables=" + coeffVariables + "resultat=" + resultat + "contraintes=" + contraintes + '}';
    }

    @Override
    public boolean equals(Object obj) {
        return obj.toString().equals(this.toString());
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
        this.getResultat().clear();

        if (!(fenetre.getPanelResultat().getListModel().isEmpty())) {
            for (int i = 0; i < fenetre.getPanelResultat().getListModel().getSize(); i++) {
                try {
                    this.resultat.add(Double.parseDouble((String) fenetre.getPanelResultat().getListModel().getElementAt(i)));
                } catch (Exception e) {
                }

            }
        }

    }

    /**
     * Fonction qui retourne le problème sous forme de matrice pour l'utilisation de la méthode de résolution du simplexe
     * @return matrice : la matrice correspondante au problème
     */
    public double[][] formaliserProbleme(ArrayList<Integer> artifices) {
        //declaration de la matrice representant le probleme pour l'algo du simplexe
        double[][] matrice;
        double signe = 0;
        Double M = 600000.0;
        ArrayList<Integer> listeM = new ArrayList<Integer>();

        ArrayList<Double>[] listeContrainte = new ArrayList[this.contraintes.size()];
        double[] coefM = new double[this.contraintes.get(0).getCoeffVariables().size() + 1];
        for (int i = 0; i < this.contraintes.size(); i++) {
            listeContrainte[i] = new ArrayList<Double>();
            if (this.contraintes.get(i).getInegalite().equals("Supériorité")) {
                signe = -1;
            } else if (this.contraintes.get(i).getInegalite().equals("Infériorité")) {
                signe = 1;
            }
            if (this.contraintes.get(i).getConstante() < 0) {
                for (int j = 0; j < this.contraintes.get(i).getCoeffVariables().size(); j++) {
                    listeContrainte[i].add(-this.contraintes.get(i).getCoeffVariables().get(j));
                }
                if (signe != 0) {
                    listeContrainte[i].add(signe * -1);
                }
                if (-signe <= 0) {
                    listeContrainte[i].add(1.0);

                    for (int z = 0; z < this.contraintes.get(i).getCoeffVariables().size(); z++) {
                        coefM[z] += -this.contraintes.get(i).getCoeffVariables().get(z);
                    }
                    coefM[this.contraintes.get(i).getCoeffVariables().size()] += -this.contraintes.get(i).getConstante();
                }
                listeContrainte[i].add(-this.contraintes.get(i).getConstante());
            } else {
                for (int j = 0; j < this.contraintes.get(i).getCoeffVariables().size(); j++) {
                    listeContrainte[i].add(this.contraintes.get(i).getCoeffVariables().get(j));
                }
                if (signe != 0) {
                    listeContrainte[i].add(signe);
                }
                if (signe <= 0) {
                    listeContrainte[i].add(1.0);

                    for (int z = 0; z < this.contraintes.get(i).getCoeffVariables().size(); z++) {
                        coefM[z] += this.contraintes.get(i).getCoeffVariables().get(z);
                    }
                    coefM[this.contraintes.get(i).getCoeffVariables().size()] += this.contraintes.get(i).getConstante();
                }
                listeContrainte[i].add(this.contraintes.get(i).getConstante());
            }

            System.out.println(listeContrainte[i].toString());
        }
        int colonnes = 0;
        for (int i = 0; i < this.contraintes.size(); i++) {

            colonnes += listeContrainte[i].size() - 1 - this.coeffVariables.size();
        }
        matrice = new double[this.contraintes.size() + 1][this.coeffVariables.size() + 1 + colonnes];
        initMatrice(matrice);

        int decalage = 0;
        for (int i = 0; i < this.contraintes.size(); i++) {
            int temp = decalage;
            for (int j = 0; j < listeContrainte[i].size() - 1; j++) {
                if (j < this.getCoeffVariables().size()) {
                    matrice[i][j] = listeContrainte[i].get(j);
                } else {
                    matrice[i][j + temp] = listeContrainte[i].get(j);
                    decalage++;
                    if (this.contraintes.get(i).getInegalite().equals("Egalité")) {
                        artifices.add(temp + j);
                    }
                    if (temp + 2 == decalage) {
                        listeM.add(temp + j - 1);
                        artifices.add(temp + j);
                        //numero variable artificielle =temp + j

                    }
                }
            }
            matrice[i][this.coeffVariables.size() + colonnes] = listeContrainte[i].get(listeContrainte[i].size() - 1);
        }
        System.out.println(listeM);
        //pour chaque colonne de la derniere ligne
        for (int j = 0; j < this.coeffVariables.size(); j++) {
            if (this.objectif.equals("Minimiser")) {
                matrice[this.contraintes.size()][j] = -this.coeffVariables.get(j) + M * coefM[j];
            } else {
                //on ajoute les coefficients de la fonction a maximiser
                matrice[this.contraintes.size()][j] = this.coeffVariables.get(j) + M * coefM[j];
            }
        }
        for (int j = 0; j < listeM.size(); j++) {
            matrice[this.contraintes.size()][listeM.get(j)] = -M;
        }
        matrice[this.contraintes.size()][this.coeffVariables.size() + colonnes] = M * coefM[coefM.length - 1];
        afficherMatrice(matrice);
        //on rempli la matrice avec les différentes données du problème à résoudre (variables, contraintes)
        // afficherMatrice(matrice);

        //on retourne la matrice
        return matrice;
    }

    /**
     * Procédure permettant d'initialiser une matrice avec des 0
     * @param matrice : matrice representant le probleme
     * @param nb : nombre de variables imaginaires
     */
    public void initMatrice(double[][] matrice) {
        //pour chaque contrainte + 1 car il y a la ligne des variables de la fonction a maximiser en plus
        for (int i = 0; i < matrice.length; i++) {
            //pour autant qu'il y a de coefficient dans les variables du probleme a resoudre (le nombre de Xi)
            for (int j = 0; j < matrice[0].length; j++) {
                //on initialise a 0
                matrice[i][j] = 0;
            }
        }
    }

    /**
     * Fonction qui retourne le problème sous forme de matrice pour pouvoir l'exporter sous scilab
     * @return matrice : la matrice correspondante au problème
     */
    public double[][] formaliserProblemeScilab() {
        //declaration de la matrice representant le probleme pour l'algo du simplexe
        double[][] matrice;
        //declaration de la variable qui stocke le nombre de variables imaginaires (les Ei)
        int nbVariablesImg;

        //appel de la fonction recuperant le nombre de variables imaginaires
        nbVariablesImg = combienVariablesImg();
        //on peut a present declarer la taille de la matrice
        matrice = new double[nbVariablesImg + 1][this.coeffVariables.size() + nbVariablesImg + 1];
        //on initialise la matrice avec des zéros
        initMatrice(matrice);
        //on rempli la matrice avec les différentes données du problème à résoudre (variables, contraintes)
        remplirMatriceScilab(matrice, nbVariablesImg);
        afficherMatrice(matrice);

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

    public ArrayList<Double> chercherSolutions() {
        ArrayList<Integer> artifices = new ArrayList<Integer>();
        double[][] tableau = this.formaliserProbleme(artifices);
        return Simplexe.start(tableau, artifices, this.coeffVariables.size(), this.objectif);

    }

    /**
     * Procedure qui remplit la matrice avec tous les elements d'un probleme
     * @param matrice : matrice representant le probleme
     * @param nb : nombre de variables imaginaires
     */
    public void remplirMatriceScilab(double[][] matrice, int nb) {
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
            if (this.contraintes.get(i).getInegalite().equals("Infériorité")) {
                matrice[cpt][this.coeffVariables.size() + nb] = this.contraintes.get(i).getConstante();
            } else if (this.contraintes.get(i).getInegalite().equals("Supériorité")) {
                matrice[cpt][this.coeffVariables.size() + nb] = -this.contraintes.get(i).getConstante();
            } else {
                matrice[cpt][this.coeffVariables.size() + nb] = this.contraintes.get(i).getConstante();
                cpt++;
                //on remplit la matrice avec des 1 en les decalant a chaque iteration d'une case de plus.
                matrice[cpt][cpt + this.coeffVariables.size()] = 1;
                //on remplit la derniere colonne de la matrice avec les bi, qui sont les constantes de chaque contrainte
                matrice[cpt][this.coeffVariables.size() + nb] = -this.contraintes.get(i).getConstante();
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

    /**
     * Procédure qui remplit la matrice avec tous les éléments d'un problème
     * @param matrice : matrice représentant le problème
     * @param nb : nombre de variables imaginaires
     */
    public void afficherMatrice(double[][] matrice) {
        System.out.println("DEBUT remplissage");
        //pour autant qu'il y a de contraintes (on va remplir toutes les lignes de la matrice sauf la derniere
        for (int i = 0; i < matrice.length; i++) {
            //pour toutes les colonnes de la matrice
            for (int j = 0; j < matrice[0].length; j++) {
                System.out.print(" | " + matrice[i][j]);
            }
            System.out.print("\n");
        }
        System.out.println("FIN remplissage");
        System.out.println("\n");
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

    public ArrayList<Double> getResultat() {
        return resultat;
    }

    public void setResultat(ArrayList<Double> resultat) {
        this.resultat = resultat;
    }
}
