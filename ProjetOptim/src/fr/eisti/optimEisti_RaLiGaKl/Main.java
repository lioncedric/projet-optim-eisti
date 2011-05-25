/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.eisti.optimEisti_RaLiGaKl;

import fr.eisti.optimEisti_RaLiGaKl.view.compte.Accueil;
import fr.eisti.optimEisti_RaLiGaKl.view.problemes.Fenetre;

/**
 * Classe permettant de lancer le programme en appelant la page d'accueil
 * @author Razavet Maël, Lion Cédric, Klelifa Sarah, Gallet Mériadec
 */
public class Main {

    public static Fenetre fenetrePrincipale;
    public static Accueil accueil;

    /**
     * @param args
     */
    public static void main(String[] args) {
        //on initialise la page d'accueil...
        accueil = new Accueil();
        //...et on l'affiche
        accueil.setVisible(true);
    }

}
