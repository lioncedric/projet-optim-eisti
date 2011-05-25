
package fr.eisti.optimEisti_RaLiGaKl.controler.problemes;

import fr.eisti.optimEisti_RaLiGaKl.Main;
import fr.eisti.optimEisti_RaLiGaKl.model.Probleme;
import fr.eisti.optimEisti_RaLiGaKl.view.problemes.PanelOngletProbleme;
import fr.eisti.optimEisti_RaLiGaKl.view.problemes.PanelProbleme;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 * Classe qui permet de detecter si un probleme a été modifié
 * @author Razavet Maël, Lion Cédric, Klelifa Sarah, Gallet Mériadec
 */
public class SaveListener implements ChangeListener, KeyListener, MouseListener {


    @Override
    public void stateChanged(ChangeEvent e) {
        estmodifié();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
      
    }

    @Override
    public void keyReleased(KeyEvent e) {
          estmodifié();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
      
    }

    @Override
    public void mouseReleased(MouseEvent e) {
          estmodifié();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

/**
 * fonction qui verifie si un des problemes  a été modifié
 */
    public static void estmodifié() {
        //pour chaque probleme ON FAIT LE TEST
        for (int i = 0; i < Main.fenetrePrincipale.getDroite().getTabCount(); i++) {
            //probleme affiché
            PanelProbleme jp = ((PanelProbleme) Main.fenetrePrincipale.getDroite().getComponentAt(i));
            //probleme en memoire
            Probleme p = new Probleme();
            try{
            p.renseignerProbleme(jp);
            }
            catch(Exception e){}
            //test et on agit en conséquense
            if (jp.getProbleme().equals(p)) {
                ((PanelOngletProbleme) Main.fenetrePrincipale.getDroite().getTabComponentAt(i)).getSave().setEnabled(false);

            } else {
                ((PanelOngletProbleme) Main.fenetrePrincipale.getDroite().getTabComponentAt(i)).getSave().setEnabled(true);
            }
        }
    }
}
