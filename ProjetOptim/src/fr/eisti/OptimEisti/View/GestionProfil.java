/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.eisti.OptimEisti.View;

import fr.eisti.OptimEisti.Controler.GestionProfilListener;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JDialog;

/**
 *
 * @author Administrator
 */
public class GestionProfil extends JDialog{
    //déclaration des attributs
    private static final long serialVersionUID = 1L;
    private Image fond;
    private JPanelFondGestionProfil panFond;

    //Constructeur par défaut
    public GestionProfil() {
        //Appel de la méthode qui initialise les variables
        initialiserVariables();
        //Méthode qui ajoute une image de fond
        ajoutImageFond();
        //Méthode qui ajoute le panel sur la fenêtre
        traitement();
    }

    public void initialiserVariables() {
        //Ajout d'un titre
        this.setTitle("Gestion de votre profil");
        this.setSize(450, 300);          //on redimenssione la fenetre en cours
        this.setLocationRelativeTo(null);//on centre la fenetre a l'ecran
        this.setResizable(false);        //on demande a ce que la fenetre ne puisse pas etre redimentionnee
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE); //permet de fermer correctement la fenetre
        this.setModal(true);       //on desactive tout le reste
    }

    /**
     * Ajoute le panel sur la fenêtre
     */
    public void traitement() {
        this.setContentPane(panFond);
    }

    /**
     * Permet d'ajouter une image de fond sur la jdialog
     */
    public void ajoutImageFond() {
        try {
            //lecture de l'image
            fond = ImageIO.read(new File("images/creerCompte.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //instanciation d'un panel
        panFond = new JPanelFondGestionProfil(fond);
        //ajout d'un écouteur de souris
        this.addMouseListener(new GestionProfilListener(this));
    }

    public JPanelFondGestionProfil getPanFond() {
        return panFond;
    }
    
    
}
