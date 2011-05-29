/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.eisti.optimEisti_RaLiGaKl.view.problemes;

import fr.eisti.optimEisti_RaLiGaKl.Main;
import java.awt.Component;

/**
 * Classe qui permet de basculer entre l'affichage des solution et du problème
 * @author Razavet Maël, Lion Cédric, Klelifa Sarah, Gallet Mériadec
 */
public class Basculer implements Runnable {

    private boolean devoilerSolution;

    /**
     * Constructeur d'un element permettant de basculer l'affichage
     * @param devoilerSolution : boolean qui permet de savoir sur qu'elle affichage on se trouve
     */
    public Basculer(boolean devoilerSolution) {
        this.devoilerSolution = devoilerSolution;
    }
    /**
     * methode executee au lancement d'un thread
     */
    @Override
    public void run() {
        //si la solution est doit etre affichée
        //on l'affiche
        if (devoilerSolution) {
            PanelProbleme p = ((PanelProbleme) Main.fenetrePrincipale.getDroite().getSelectedComponent());
            //on desactive tout
            p.getSlide().setEnabled(false);
            p.getMaximiser().setEnabled(false);
            p.getMinimiser().setEnabled(false);
            p.getPanTableau().getTable().setEnabled(false);
            p.getAjouter().setEnabled(false);
            for (Component c : p.getPanDonnees().getComponents()) {
                c.setEnabled(false);
            }
            try {
                //on fait glisser progressivement tous les composants vers le haut en diminuant le parametre hauteur
                while (p.getHauteur() > -270) {
                    p.setHauteur(p.getHauteur() - 1);
                    Main.fenetrePrincipale.repaint();
                    Thread.sleep(5);
                }
            } catch (InterruptedException ex) {
            }
            
        } else {
            //si on doit cacher la solution
            PanelProbleme p = ((PanelProbleme) Main.fenetrePrincipale.getDroite().getSelectedComponent());
            //on réactive tous les composants
            p.getSlide().setEnabled(true);
            p.getMaximiser().setEnabled(true);
            p.getMinimiser().setEnabled(true);
            p.getPanTableau().getTable().setEnabled(true);
            p.getAjouter().setEnabled(true);

            for (Component c : p.getPanDonnees().getComponents()) {
                c.setEnabled(true);
            }
             //on fait glisser progressivement tous les composants vers le bas en augmentant le parametre hauteur
            try {
                while (p.getHauteur() < 0) {
                    p.setHauteur(p.getHauteur() + 1);
                    Main.fenetrePrincipale.repaint();
                    Thread.sleep(5);
                }
            } catch (InterruptedException ex) {
            }
        }

    }
}
