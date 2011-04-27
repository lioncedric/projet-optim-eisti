package fr.eisti.optimEisti_RaLiGaKl.view.compte;

import fr.eisti.optimEisti_RaLiGaKl.controler.compte.GestionProfilListener;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JDialog;

/**
 * Classe permettant de gérer le profil de l'utilisateur et de le modifier si besoin
 * @author Razavet Maël, Lion Cédric, Klelifa Sarah, Gallet Mériadec
 * @version: 1.0
 */
public class GestionProfil extends JDialog {
    
    //déclaration des attributs
    private Image fond;
    private JPanelFondGestionProfil panFond;

    /**
     * Constructeur permettant de créer le jdialog
     */
    public GestionProfil() {
        init();
        ajoutImageFond();
        traitement();
    }

    /**
     * Initialise la fenêtre
     */
    public void init() {
        this.setTitle("Gestion de votre profil");
        this.setSize(450, 300);                 //on redimenssione la fenetre en cours
        this.setLocationRelativeTo(null);       //on centre la fenetre a l'ecran
        this.setResizable(false);               //on demande a ce que la fenetre ne puisse pas etre redimentionnee
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE); //permet de fermer correctement la fenetre
        this.setModal(true);                    //on desactive tout le reste
    }

    /**
     * Ajoute le panel sur la fenêtre
     */
    public void traitement() {
        this.setContentPane(panFond);
    }

    /**
     * Permet d'ajouter une image de fond sur le jdialog
     */
    public void ajoutImageFond() {
        try {
            //lecture de l'image
            fond = ImageIO.read(new File("images/creerCompte.png"));
        } catch (IOException e) {
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
