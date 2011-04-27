package fr.eisti.optimEisti_RaLiGaKl.view.compte;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JDialog;

import fr.eisti.optimEisti_RaLiGaKl.controler.compte.ConnexionControler;

/**
 * Classe permettant de gérer l'identification d'un utilisateur
 * @author Razavet Maël, Lion Cédric, Klelifa Sarah, Gallet Mériadec
 * @version: 1.0
 */
public class Identification extends JDialog {

    //déclaration des attributs    
    private JPanelFondIdentification panFond;
    private Image fond;

    /**
     * Constructeur permettant de créer le jdialog
     */
    public Identification() {
        init();
        ajoutImageFond();
        traitement();
    }

    /**
     * Initialise la fenêtre
     */
    public void init() {
        this.setTitle("Identification");
        this.setSize(450, 300);                  //on redimenssione la fenetre en cours
        this.setLocationRelativeTo(null);       //on centre la fenetre a l'ecran
        this.setResizable(false);               //on demande a ce que la fenetre ne puisse pas etre redimentionnee
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE); //permet de fermer correctement la fenetre
        this.setModal(true);                    //on desactive tout le reste
    }

    /**
     * ajoute le panel sur la fenêtre
     */
    public void traitement() {
        //on ajoute le panel sur la fenetre     
        this.setContentPane(panFond);
    }

    /**
     * ajoute l'image sur le panel de la fenêtre
     */
    public void ajoutImageFond() {
        try {
            //lecture de l'image
            fond = ImageIO.read(new File("images/identification.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //on la met sur le panel
        this.panFond = new JPanelFondIdentification(fond);
        //ajout d'un écouteur d'évènement sur la souris
        this.addMouseListener(new ConnexionControler(this.panFond.getJtfNomUtilisateur(), this.panFond.getJtfMdp(), this));
        //ajout d'un écouteur d'évènement pour le mot de passe
        this.panFond.getJtfMdp().addKeyListener(new ConnexionControler(this.panFond.getJtfNomUtilisateur(), this.panFond.getJtfMdp(), this));
        //ajout d'un écouteur d'évènement pour le nom d'utilisateur
        this.panFond.getJtfNomUtilisateur().addKeyListener(new ConnexionControler(this.panFond.getJtfNomUtilisateur(), this.panFond.getJtfMdp(), this));

    }
}
