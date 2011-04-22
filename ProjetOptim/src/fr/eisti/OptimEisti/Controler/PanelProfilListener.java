/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.eisti.OptimEisti.Controler;

import fr.eisti.OptimEisti.View.Compte.GestionProfil;
import fr.eisti.OptimEisti.View.PanelProfil;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Administrator
 */
public class PanelProfilListener implements ActionListener{

    private PanelProfil panProfil;
    public PanelProfilListener(PanelProfil pProfil){
        this.panProfil=pProfil;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        GestionProfil gProfil = new GestionProfil();
        gProfil.setVisible(true);
    }

}
