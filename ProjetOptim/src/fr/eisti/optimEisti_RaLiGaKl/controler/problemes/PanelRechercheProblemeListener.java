
package fr.eisti.optimEisti_RaLiGaKl.controler.problemes;

import fr.eisti.optimEisti_RaLiGaKl.Main;
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

    /**
     * Constructeur permettant d'initialise notre variable
     */
    public PanelRechercheProblemeListener() {
      
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Redéfinition de la méthode keyPressed de l'interface KeyListener
     * @param e : evenement du clavier
     */
    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //a chaque nouvelle lettre tappe, on raffraichit la liste
        //on recupere les numero des problemes a l'aide de la fonction recherche
        Main.fenetrePrincipale.getGauche().miseajour();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
       
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
      
    }
}
