/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.eisti.optimEisti_RaLiGaKl.controler.problemes;

import fr.eisti.optimEisti_RaLiGaKl.view.compte.JPanelFondCreerCompte;
import fr.eisti.optimEisti_RaLiGaKl.view.compte.JPanelFondGestionProfil;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

/**
 * Classe qui écoute l'action d'un bouton devant créer un composant de type FileChooser à l'écran
 * @author Razavet Maël, Lion Cédric, Klelifa Sarah, Gallet Mériadec
 * @version 1.0
 */
public class FileChooserListener implements ActionListener {

    //on declare un panel
    private JPanel pan;

    /**
     * Constructeur du listener
     * @param p
     */
    public FileChooserListener(JPanel p) {
        this.pan = p;
    }

    /**
     * Redéfinition de la méthode actionPerformed de l'interface ActionListener
     * @param e 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            //declaration et initialisation d'un nouveau JFileChooser
            JFileChooser fc = new JFileChooser();
            //on affiche le FileChooser à l'écran
            int returnVal = fc.showOpenDialog(this.pan);
            //on fait le traitement seulement si l'utilisateur a souhaité ouvrir un fichier
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                //on récupère le nom du fichier en question (de type image, document, ...)
                File file = fc.getSelectedFile();
                //on récupère l'adresse du fichier
                String nameFile = file.getAbsolutePath();
                //Si l'action a été déclanchée depuis la fenêtre de création de compte
                if (this.pan instanceof JPanelFondCreerCompte) {
                    //on met à jour le textField correspondant
                    ((JPanelFondCreerCompte) this.pan).getJtfAvatar().setText(nameFile);
                } //Si l'action a été déclanchée depuis la fenêtre de gestion du profil
                else if (this.pan instanceof JPanelFondGestionProfil) {
                    //on met à jour le textField correspondant
                    ((JPanelFondGestionProfil) this.pan).getJtfAvatar().setText(nameFile);
                }
            }
        } catch (Exception ex) {
        }
    }
}
