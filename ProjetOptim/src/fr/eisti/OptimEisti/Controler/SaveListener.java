/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.eisti.OptimEisti.Controler;

import fr.eisti.OptimEisti.Main;
import fr.eisti.OptimEisti.Model.Probleme;
import fr.eisti.OptimEisti.View.ButtonTabComponent;
import fr.eisti.OptimEisti.View.JPanelProbleme;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Administrator
 */
public class SaveListener implements ChangeListener, KeyListener, MouseListener {

    public void stateChanged(ChangeEvent e) {
        estmodifié();
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
      
    }

    public void keyReleased(KeyEvent e) {
          estmodifié();
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
      
    }

    public void mouseReleased(MouseEvent e) {
          estmodifié();
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public static void estmodifié() {
        for (int i = 0; i < Main.fenetrePrincipale.getDroite().getTabCount(); i++) {
            JPanelProbleme jp = ((JPanelProbleme) Main.fenetrePrincipale.getDroite().getComponentAt(i));
            Probleme p = new Probleme();
            try{
            p.renseignerProbleme(jp);
            }
            catch(Exception e){}
            if (jp.getProbleme().toString().equals(p.toString())) {
                ((ButtonTabComponent) Main.fenetrePrincipale.getDroite().getTabComponentAt(i)).getSave().setEnabled(false);

           


            } else {
                ((ButtonTabComponent) Main.fenetrePrincipale.getDroite().getTabComponentAt(i)).getSave().setEnabled(true);

          

            }
        }
    }
}
