/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.eisti.optimEisti_RaLiGaKl.view.problemes;

import fr.eisti.optimEisti_RaLiGaKl.Main;
import fr.eisti.optimEisti_RaLiGaKl.view.problemes.PanelProbleme;
import java.awt.Component;

/**
 *
 * @author Administrator
 */
public class Basculer implements Runnable {

    private boolean devoilerSolution;

    public Basculer(boolean devoilerSolution) {
        this.devoilerSolution = devoilerSolution;
    }

    @Override
    public void run() {
        if (devoilerSolution) {
            PanelProbleme p = ((PanelProbleme) Main.fenetrePrincipale.getDroite().getSelectedComponent());
            p.getSlide().setEnabled(false);

            p.getMaximiser().setEnabled(false);
            p.getMinimiser().setEnabled(false);
            p.getPanTableau().getTable().setEnabled(false);
            p.getAjouter().setEnabled(false);

            for (Component c : p.getPanDonnees().getComponents()) {
                c.setEnabled(false);
            }
            try {
                while (p.getHauteur() > -270) {
                    p.setHauteur(p.getHauteur() - 1);
                    Main.fenetrePrincipale.repaint();
                    Thread.sleep(5);
                }
            } catch (InterruptedException ex) {
            }
            
        } else {
            PanelProbleme p = ((PanelProbleme) Main.fenetrePrincipale.getDroite().getSelectedComponent());
            p.getSlide().setEnabled(true);

            p.getMaximiser().setEnabled(true);
            p.getMinimiser().setEnabled(true);
            p.getPanTableau().getTable().setEnabled(true);
            p.getAjouter().setEnabled(true);

            for (Component c : p.getPanDonnees().getComponents()) {
                c.setEnabled(true);
            }
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
