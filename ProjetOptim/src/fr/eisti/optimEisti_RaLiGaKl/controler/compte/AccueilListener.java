package fr.eisti.optimEisti_RaLiGaKl.controler.compte;

import fr.eisti.optimEisti_RaLiGaKl.view.compte.CreerCompte;
import fr.eisti.optimEisti_RaLiGaKl.view.compte.Identification;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Classe qui écoute les actions de la souris sur la page d'accueil
 * @author Razavet Maël, Lion Cédric, Klelifa Sarah, Gallet Mériadec
 */
public class AccueilListener implements MouseListener {

    /**
     * Redéfinition de la méthode mouseClicked de l'interface MouseListener
     * @param e 
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getX() > 685 && e.getX() < 784 && e.getY() > 38 && e.getY() < 56) {
            //on lance la fenêtre d'identification
            Identification fenIdentifier;
            fenIdentifier = new Identification();
            fenIdentifier.setVisible(true);
        } else if (e.getX() > 805 && e.getX() < 945 && e.getY() > 38 && e.getY() < 56) {
            //on lance le module de création de compte
            CreerCompte fenCreerCompte;
            fenCreerCompte = new CreerCompte();
            fenCreerCompte.setVisible(true);
        } else if (e.getX() > 933 && e.getX() < 952 && e.getY() > 586 && e.getY() < 605) {
            //on quitte
            System.exit(0);
        } else {
        }
    }
   
    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
}
