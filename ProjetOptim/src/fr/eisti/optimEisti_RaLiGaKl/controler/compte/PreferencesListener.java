/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.eisti.optimEisti_RaLiGaKl.controler.compte;

import fr.eisti.optimEisti_RaLiGaKl.Main;
import fr.eisti.optimEisti_RaLiGaKl.view.compte.Preferences;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JColorChooser;

/**
 *
 * @author Administrator
 */
public class PreferencesListener implements ActionListener{

    private Preferences preferences;
    
    public PreferencesListener(Preferences p){
        this.preferences=p;
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        //faire les colors chooser
         Color newColor = JColorChooser.showDialog(
                                       this.preferences,
                                       "Choose Background Color",
                                       Main.fenetrePrincipale.getPanProfil().getBackground());
        if (newColor != null) {
            Main.fenetrePrincipale.getPanProfil().mettreCouleur(newColor);
        }

    }

}
