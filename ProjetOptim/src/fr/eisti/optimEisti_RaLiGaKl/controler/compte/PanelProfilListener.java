package fr.eisti.optimEisti_RaLiGaKl.controler.compte;

import fr.eisti.optimEisti_RaLiGaKl.view.compte.GestionProfil;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe qui écoute l'action du bouton de la gestion du profil dans le profil utilisateur
 * @author Razavet Maël, Lion Cédric, Klelifa Sarah, Gallet Mériadec
 * @version 1.0
 */
public class PanelProfilListener implements ActionListener {

    /**
     * Redéfinition de la méthode actionPerformed de l'interface ActionListener
     * @param e 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        //ouverture de la fenêtre permettant la modification du profil
        GestionProfil gProfil = new GestionProfil();
        gProfil.setVisible(true);
    }
}
