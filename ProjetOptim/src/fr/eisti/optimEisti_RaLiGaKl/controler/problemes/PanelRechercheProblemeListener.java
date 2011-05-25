
package fr.eisti.optimEisti_RaLiGaKl.controler.problemes;

import fr.eisti.optimEisti_RaLiGaKl.Main;
import fr.eisti.optimEisti_RaLiGaKl.model.BddProbleme;
import fr.eisti.optimEisti_RaLiGaKl.view.problemes.PanelRechercheProbleme;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

/**
 * Classe qui écoute les évènements qui se produisent dans le panel de recherche
 * @author Razavet Maël, Lion Cédric, Klelifa Sarah, Gallet Mériadec
 * @version 1.0
 */
public class PanelRechercheProblemeListener implements KeyListener, MouseListener {

  

    /**
     * Constructeur permettant d'initialise notre variable
     * @param panRech 
     */
    public PanelRechercheProblemeListener() {
      
    }

    public void keyTyped(KeyEvent e) {
    }

    /**
     * Redéfinition de la méthode keyPressed de l'interface KeyListener
     * @param e 
     */
    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
        //a chaque nouvelle lettre tappe, on raffraichit la liste
        //on recupere les numero des problemes a l'aide de la fonction recherche
        
        Main.fenetrePrincipale.getGauche().miseajour();
    }

    /**
     * Procedure qui permet de vider le champ de recherche lorsque l'on clique dessus
     * @param e 
     */
    public void mouseClicked(MouseEvent e) {
       
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
      
    }
}
