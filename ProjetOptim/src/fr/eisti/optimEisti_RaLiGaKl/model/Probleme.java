package fr.eisti.optimEisti_RaLiGaKl.model;

import fr.eisti.optimEisti_RaLiGaKl.view.problemes.PanelProbleme;
import java.util.ArrayList;
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
    protected boolean positif;
    protected ArrayList<Double> resultat = new ArrayList<Double>();
    protected ArrayList<Contrainte> contraintes = new ArrayList<Contrainte>();

    /**
     * decription de l'objet
     * @return l'objet sous forme de chaine
     */
    @Override
    public String toString() {
        return "Probleme{" + positif + "description=" + description + "titre=" + titre + "objectif=" + objectif + "coeffVariables=" + coeffVariables + "resultat=" + resultat + "contraintes=" + contraintes + '}';
    }

    /**
     * verifier l'ealité entre deux objets
     * @param obj l'objet a comparer
     * @return si les deux objets sont egaux
     */
    @Override
    public boolean equals(Object obj) {
        return obj.toString().equals(this.toString());
    }

    /**
     *Constructeur permettant de créer un problème
     */
    public Probleme() {
        positif = true;
    }

    /**
     * Constructeur permettant de créer un problème à l'aide des informations contenues dans la fenêtre passée en paramètre
     * @param pFenetre
     */
    public Probleme(PanelProbleme pFenetre) {
        positif = true;
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
        if (fenetre.getPositif().isSelected()) {
            this.positif = true;
        } else {
            this.positif = false;
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
     * @param artifices : liste des positions des variables artificielles
     * @return matrice : la matrice correspondante au problème
     */
    public double[][] formaliserProbleme(ArrayList<Integer> artifices) {
        //declaration de la matrice representant le probleme pour l'algo du simplexe
        double[][] matrice;
        //si egalité =0,inferiorité = -1, superiorité =1
        double signe;
        //varible de penalité de grande valeur
        Double M = 600000.0;
        //tableau contenant la liste des contraintes a ecrire de facon normalisées
        ArrayList<Double>[] listeContrainteNormalisés = new ArrayList[this.contraintes.size()];
        //position des variables de pénalité dans la matrice referant la ligne d'objectif par rapport aux variables d'ecart
        ArrayList<Integer> listeM = new ArrayList<Integer>();
        //coef des variables de pénalité dans la matrice referant la ligne d'objectif par rapport aux variables reelles et au constantes
        double[] coefM = new double[this.contraintes.get(0).getCoeffVariables().size() + 1];
        //on va normaliser chaque contraintes
        for (int i = 0; i < this.contraintes.size(); i++) {
            //creation de la premiere contrinte normalisé
            listeContrainteNormalisés[i] = new ArrayList<Double>();
            //3 cas
            if (this.contraintes.get(i).getInegalite().equals("Supériorité")) {
                //on etablit le signe de la contrainte
                signe = -1;
            } else if (this.contraintes.get(i).getInegalite().equals("Infériorité")) {
                //on etablit le signe de la contrainte
                signe = 1;
            } else {
                //on etablit le signe de la contrainte
                signe = 0;
            }
            //cas ou la constante est negative, il faut tout multiplier par -1
            if (this.contraintes.get(i).getConstante() < 0) {
                //chaque variable eest multiplié par -1
                signe = -signe;
                for (int j = 0; j < this.contraintes.get(i).getCoeffVariables().size(); j++) {
                    if (this.isPositif()) {
                        listeContrainteNormalisés[i].add(-this.contraintes.get(i).getCoeffVariables().get(j));
                    } else {
                        listeContrainteNormalisés[i].add(this.contraintes.get(i).getCoeffVariables().get(j));
                    }
                }
                //variable d'ecart du type e1 ou -e1 en fonction du signe
                if (signe != 0) {
                    listeContrainteNormalisés[i].add(signe);
                }
                //variable artificille du type +u1
                if (signe <= 0) {
                    listeContrainteNormalisés[i].add(1.0);
                    //on additionne les m pour les variables
                    for (int z = 0; z < this.contraintes.get(i).getCoeffVariables().size(); z++) {
                        coefM[z] += -this.contraintes.get(i).getCoeffVariables().get(z);
                    }
                    //on additionne les m pour la constante
                    coefM[this.contraintes.get(i).getCoeffVariables().size()] += -this.contraintes.get(i).getConstante();
                }
                //on ajoute la constante
                listeContrainteNormalisés[i].add(-this.contraintes.get(i).getConstante());

            } //cas ou la constante est positive
            else {
                //on additionne les m pour les variables
                for (int j = 0; j < this.contraintes.get(i).getCoeffVariables().size(); j++) {
                    if (this.isPositif()) {
                        listeContrainteNormalisés[i].add(this.contraintes.get(i).getCoeffVariables().get(j));
                    } else {
                        listeContrainteNormalisés[i].add(-this.contraintes.get(i).getCoeffVariables().get(j));
                    }
                }
                //variable d'ecart du type e1 ou -e1 en fonction du signe
                if (signe != 0) {
                    listeContrainteNormalisés[i].add(signe);
                }
                //variable artificille du type +u1
                if (signe <= 0) {
                    listeContrainteNormalisés[i].add(1.0);
                    //on additionne les m pour les variables
                    for (int z = 0; z < this.contraintes.get(i).getCoeffVariables().size(); z++) {
                        coefM[z] += this.contraintes.get(i).getCoeffVariables().get(z);
                    }
                    //on additionne les m pour la constante
                    coefM[this.contraintes.get(i).getCoeffVariables().size()] += this.contraintes.get(i).getConstante();
                }
                //on ajoute la constante
                listeContrainteNormalisés[i].add(this.contraintes.get(i).getConstante());
            }
            //pour une verif
            System.out.println(listeContrainteNormalisés[i].toString());
        }//normalisation terminée
        //calcul du nomre de variables artificielles et d'ecart
        int colonnes = 0;
        for (int i = 0; i < this.contraintes.size(); i++) {
            colonnes += listeContrainteNormalisés[i].size() - 1 - this.coeffVariables.size();
        }
        //creation de la matrice represantant un probleme normalisé
        matrice = new double[this.contraintes.size() + 1][this.coeffVariables.size() + 1 + colonnes];
        initMatrice(matrice);//on met des 0
        //variable pour decaler les variables d'ecart et artificielles
        int decalage = 0;
        //pour chaque contrainte
        for (int i = 0; i < this.contraintes.size(); i++) {
            //on memorise le decalagge en cours
            int temp = decalage;
            //on parcours la liste des contraintes
            for (int j = 0; j < listeContrainteNormalisés[i].size() - 1; j++) {
                //on commence a remplir la matrice avec les variables reeles
                if (j < this.getCoeffVariables().size()) {
                    matrice[i][j] = listeContrainteNormalisés[i].get(j);
                    //on rempli les varibles d'ecart et artificielles
                } else {
                    matrice[i][j + temp] = listeContrainteNormalisés[i].get(j);
                    //on decale 1 fois
                    decalage++;
                    if (this.contraintes.get(i).getInegalite().equals("Egalité")) {
                        //on identifie une variable artificielle
                        artifices.add(temp + j);
                    }
                    if (temp + 2 == decalage) {
                        //on ajoute un M
                        listeM.add(temp + j - 1);
                        //on identifie une variable artificielle
                        artifices.add(temp + j);
                    }
                }
            }
            //on ajoute la constante
            matrice[i][this.coeffVariables.size() + colonnes] = listeContrainteNormalisés[i].get(listeContrainteNormalisés[i].size() - 1);
        }
        //pour chaque colonne de la derniere ligne(fonction objectif)
        for (int j = 0; j < this.coeffVariables.size(); j++) {
            if (this.objectif.equals("Minimiser")) {
                if (this.isPositif()) {
                    matrice[this.contraintes.size()][j] = -this.coeffVariables.get(j) + M * coefM[j];
                } else {
                    matrice[this.contraintes.size()][j] = -(-this.coeffVariables.get(j) + M * coefM[j]);
                }
            } else {
                //on ajoute les coefficients de la fonction a maximiser
                if (this.isPositif()) {
                    matrice[this.contraintes.size()][j] = this.coeffVariables.get(j) + M * coefM[j];
                } else {
                    matrice[this.contraintes.size()][j] = -(this.coeffVariables.get(j) + M * coefM[j]);
                }
            }
        }
        //on place les M dans la foction obj
        for (int j = 0; j < listeM.size(); j++) {
            matrice[this.contraintes.size()][listeM.get(j)] = -M;
        }
        //on place la cste de la fction obj
        matrice[this.contraintes.size()][this.coeffVariables.size() + colonnes] = M * coefM[coefM.length - 1];
        afficherMatrice(matrice);
        //on retourne la matrice
        return matrice;
    }

    /**
     * Procédure permettant d'initialiser une matrice avec des 0
     * @param matrice : matrice representant le probleme
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

    /**
     * resoudre le probleme d'optimisation lineaire
     * @return toutes les valeurs optimales des xi mais aussi le resulultat optimal de la fonction en premiere valeur de la liste
     */
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

    public boolean isPositif() {
        return positif;
    }

    public void setPositif(boolean positif) {
        this.positif = positif;
    }
}
