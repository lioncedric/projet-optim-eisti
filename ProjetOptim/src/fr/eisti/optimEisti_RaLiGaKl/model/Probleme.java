package fr.eisti.optimEisti_RaLiGaKl.model;

import fr.eisti.optimEisti_RaLiGaKl.view.problemes.PanelProbleme;
import java.lang.Double;
import java.util.ArrayList;
import javax.swing.JTextField;

/**
 * Classe relative à un problème
 * @author Razavet Maël, Lion Cédric, Klelifa Sarah, Gallet Mériadec
 */
public class Probleme {

    //déclaration des variables nécessaires pour remplir un problème
    private int numero;
    private String description = "";
    private String titre = "";
    private String objectif = "";
    private ArrayList<Double> coeffVariables = new ArrayList<Double>();
    private ArrayList<Double> resultat = new ArrayList<Double>();
    private ArrayList<Contrainte> contraintes = new ArrayList<Contrainte>();

    @Override
    public String toString() {
        return "Probleme{description=" + description + "titre=" + titre + "objectif=" + objectif + "coeffVariables=" + coeffVariables + "contraintes=" + contraintes + '}';
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
    }

    /**
     * Fonction qui retourne le problème sous forme de matrice pour l'utilisation de la méthode de résolution du simplexe
     * @return matrice : la matrice correspondante au problème
     */
    public double[][] formaliserProbleme() {
        //declaration de la matrice representant le probleme pour l'algo du simplexe
        double[][] matrice;
        double signe = 0;
        ArrayList<Double>[] listeContrainte = new ArrayList[this.contraintes.size()];
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
                }
                listeContrainte[i].add(this.contraintes.get(i).getConstante());
            }
        }
        int colonnes = 0;
        for (int i = 0; i < this.contraintes.size(); i++) {
            colonnes += listeContrainte[i].size() - this.coeffVariables.size();
        }
        matrice = new double[this.contraintes.size() + 1][colonnes];
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
                }
            }
              matrice[i][colonnes-1] = listeContrainte[i].get(listeContrainte[i].size()-1);
        }
        //pour chaque colonne de la derniere ligne
        for (int j = 0; j < this.coeffVariables.size(); j++) {
            if (this.objectif.equals("Minimiser")) {
                matrice[this.contraintes.size()][j] = -this.coeffVariables.get(j);
            } else {
                //on ajoute les coefficients de la fonction a maximiser
                matrice[this.contraintes.size()][j] = this.coeffVariables.get(j);
            }
        }
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
}
