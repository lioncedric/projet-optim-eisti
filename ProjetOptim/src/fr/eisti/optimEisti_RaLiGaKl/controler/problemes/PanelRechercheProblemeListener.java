/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.eisti.optimEisti_RaLiGaKl.controler.problemes;

import fr.eisti.optimEisti_RaLiGaKl.Main;
import fr.eisti.optimEisti_RaLiGaKl.model.BddProbleme;
import fr.eisti.optimEisti_RaLiGaKl.view.problemes.PanelRechercheProbleme;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Classe qui écoute les évènements qui se produisent dans le panel de recherche
 * @author Razavet Maël, Lion Cédric, Klelifa Sarah, Gallet Mériadec
 * @version 1.0
 */
public class PanelRechercheProblemeListener implements KeyListener, MouseListener {

    //decalarion du panel depuis lequel l'ation a été générée
    private PanelRechercheProbleme panRechProb;

    /**
     * Constructeur permettant d'initialise notre variable
     * @param panRech 
     */
    public PanelRechercheProblemeListener(PanelRechercheProbleme panRech) {
        this.panRechProb = panRech;
    }

    public void keyTyped(KeyEvent e) {
    }

    /**
     * Redéfinition de la méthode keyPressed de l'interface KeyListener
     * @param e 
     */
    public void keyPressed(KeyEvent e) {
        //on gere le cas ou il appuie sur la touche ENTRER
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            //on recupere le numero du probleme a l'aide de la fonction recherche
            int numeroProbleme = BddProbleme.rechercheProbleme(this.panRechProb.getJtfRecherche().getText());
            //si le probleme existe
            if (numeroProbleme != -1) {
                //on selectionne l'index correspondant à celui recherché dans la liste des problemes
                Main.fenetrePrincipale.getGauche().getList().setSelectedIndex(numeroProbleme);
            }
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    /**
     * Procedure qui permet de vider le champ de recherche lorsque l'on clique dessus
     * @param e 
     */
    public void mouseClicked(MouseEvent e) {
        this.panRechProb.getJtfRecherche().setText("");
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    /**
     * Procédure qui permet de remettre le champ de recherche par défaut
     * @param e 
     */
    public void mouseExited(MouseEvent e) {
        this.panRechProb.getJtfRecherche().setText("Rechercher ...");
    }
}
