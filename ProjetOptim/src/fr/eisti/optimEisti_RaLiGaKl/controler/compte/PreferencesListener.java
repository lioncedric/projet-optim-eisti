package fr.eisti.optimEisti_RaLiGaKl.controler.compte;

import fr.eisti.optimEisti_RaLiGaKl.Main;
import fr.eisti.optimEisti_RaLiGaKl.view.compte.Preferences;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JColorChooser;

/**
 * Classe qui permet d'écouter les actions faites sur les couleurs de notre panel de préférences
 * @author Razavet Maël, Lion Cédric, Klelifa Sarah, Gallet Mériadec
 */
public class PreferencesListener implements MouseListener {

    private Preferences preferences;        //declaration d'un attribut de type Preferences

    /**
     * Constructeur permettant d'initialiser les preferences
     * @param p un attribut de type Préférences
     */
    public PreferencesListener(Preferences p) {
        this.preferences = p;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    /**
     * Procedure qui permet d'ouvrir un jcolorchooser et de modifier al couleur de l'attribut sur lequel on a cliqué
     * @param e l'évènement généré par le clic souris
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        //on genere un colorChooser
        Color newColor = JColorChooser.showDialog(
                this.preferences,
                "Choose Background Color",
                Main.fenetrePrincipale.getPanProfil().getBackground());
        
        //si une couleur est selectionnée
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
