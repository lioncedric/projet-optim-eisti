/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.eisti.optimEisti_RaLiGaKl.model;

import fr.eisti.optimEisti_RaLiGaKl.Main;
import fr.eisti.optimEisti_RaLiGaKl.view.problemes.PanelProbleme;
import java.awt.Component;

/**
 *
 * @author Administrator
 */
public class Basculer implements Runnable {

    @Override
    public void run() {
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
            int i = 0;

            p.setHauteur(0);
            while (i < 270) {
                p.setHauteur(p.getHauteur() - 1);
                Main.fenetrePrincipale.repaint();
                Thread.sleep(5);

                i++;
            }


        } catch (InterruptedException ex) {
        }
    }
}
