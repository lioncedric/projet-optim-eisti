/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.eisti.optimEisti_RaLiGaKl.controler.compte;

import fr.eisti.optimEisti_RaLiGaKl.Main;
import fr.eisti.optimEisti_RaLiGaKl.view.compte.Preferences;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JColorChooser;

/**
 *
 * @author Administrator
 */
public class PreferencesListener implements MouseListener {

    private Preferences preferences;

    public PreferencesListener(Preferences p) {
        this.preferences = p;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //faire les colors chooser

        Color newColor = JColorChooser.showDialog(
                this.preferences,
                "Choose Background Color",
                Main.fenetrePrincipale.getPanProfil().getBackground());
        if (newColor != null) {
            if (e.getSource() == this.preferences.getTab()[0]) {
                Main.fenetrePrincipale.getPanProfil().mettreCouleurFond(newColor);
                this.preferences.attribuerCouleurs();
            } else if (e.getSource() == this.preferences.getTab()[1]) {
                Main.fenetrePrincipale.getPanProfil().mettreCouleurTexte(newColor);
                this.preferences.attribuerCouleurs();
            } else if (e.getSource() == this.preferences.getTab()[2]) {
                Main.fenetrePrincipale.getGauche().changerCouleurDegrade1(newColor);
                this.preferences.attribuerCouleurs();
            } else if (e.getSource() == this.preferences.getTab()[3]) {
                Main.fenetrePrincipale.getGauche().changerCouleurDegrade2(newColor);
                this.preferences.attribuerCouleurs();
            } else if (e.getSource() == this.preferences.getTab()[4]) {
                Main.fenetrePrincipale.getGauche().getListePR().changerCouleurTexte(newColor);
                this.preferences.attribuerCouleurs();
            } else if (e.getSource() == this.preferences.getTab()[5]) {
                Main.fenetrePrincipale.getGauche().getListePR().changerCouleurFondSelection(newColor);
                this.preferences.attribuerCouleurs();
            } else if (e.getSource() == this.preferences.getTab()[6]) {
                Main.fenetrePrincipale.getGauche().getListePR().changerCouleurTexteSelection(newColor);
                this.preferences.attribuerCouleurs();
            } else if (e.getSource() == this.preferences.getTab()[7]) {
                Main.fenetrePrincipale.getGauche().changerCouleurFondPanelBoutons(newColor);
                this.preferences.attribuerCouleurs();
            } else if (e.getSource() == this.preferences.getTab()[8]) {
                Main.fenetrePrincipale.setCouleur1(newColor);
                this.preferences.attribuerCouleurs();
            } else if (e.getSource() == this.preferences.getTab()[9]) {
                Main.fenetrePrincipale.setCouleur2(newColor);
                this.preferences.attribuerCouleurs();
            } else if (e.getSource() == this.preferences.getTab()[10]) {
                Main.fenetrePrincipale.setCouleurTexte(newColor);
                this.preferences.attribuerCouleurs();
            } else if (e.getSource() == this.preferences.getTab()[11]) {
                Main.fenetrePrincipale.setCouleurComposantsTransparents(newColor);
                this.preferences.attribuerCouleurs();
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
