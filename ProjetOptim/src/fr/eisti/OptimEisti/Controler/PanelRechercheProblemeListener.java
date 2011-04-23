/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.eisti.OptimEisti.Controler;

import fr.eisti.OptimEisti.Main;
import fr.eisti.OptimEisti.Model.BddProbleme;
import fr.eisti.OptimEisti.View.PanelRechercheProbleme;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author Administrator
 */
public class PanelRechercheProblemeListener implements KeyListener,MouseListener{

    private PanelRechercheProbleme panRechProb;
    public PanelRechercheProblemeListener(PanelRechercheProbleme panRech){
        this.panRechProb=panRech;
    }
    
    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        //on gere le cas ou il appuie sur la touche ENTRER
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            int numeroProbleme = BddProbleme.rechercheProbleme(this.panRechProb.getJtfRecherche().getText());
            if(numeroProbleme != -1){
                Main.fenetrePrincipale.getGauche().getList().setSelectedIndex(numeroProbleme);
            }
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
        this.panRechProb.getJtfRecherche().setText("");
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
        this.panRechProb.getJtfRecherche().setText("Rechercher ...");
    }

}
