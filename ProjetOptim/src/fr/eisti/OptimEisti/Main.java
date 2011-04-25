/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.eisti.OptimEisti;

import fr.eisti.OptimEisti.View.Compte.Accueil;
import fr.eisti.OptimEisti.View.Fenetre;

/**
 * Classe permettant de lancer le programme en appelant la page d'accueil
 * @author Razavet Maël, Lion Cédric, Klelifa Sarah, Gallet Mériadec
 * @version: 1.0
 */
public class Main {

    public static Fenetre fenetrePrincipale;
    static Accueil accueil;

    /**
     * @param args
     */
    public static void main(String[] args) {
        //on initialise la page d'accueil...
        accueil = new Accueil();
        //...et on l'affiche
        accueil.setVisible(true);
    }

    //LES GETTERS AND SETTERS
    public static Accueil getAccueil() {
        return accueil;
    }
}
