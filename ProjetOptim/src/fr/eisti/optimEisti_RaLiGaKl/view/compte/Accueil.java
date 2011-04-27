package fr.eisti.optimEisti_RaLiGaKl.view.compte;

import fr.eisti.optimEisti_RaLiGaKl.controler.compte.AccueilListener;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 * Classe qui constitue la premiere fenetre affichee a l'écran (la page d'accueil)
 * @author Razavet Maël, Lion Cédric, Klelifa Sarah, Gallet Mériadec
 * @version: 1.0
 */
public class Accueil extends JFrame {

    //declarations des variables pour mettre en place une image de fond
    private Image fond;
    private JPanelFondNormal panFond;

    /**
     * Constructeur permettant de créer la page d'accueil de notre programme
     */
    public Accueil() {
        init();
        ajoutImageFond();
        traitement();
    }

    /**
     * Initialise la fenêtre d'accueil
     */
    private void init() {
        this.setTitle("Programme OptimEisti");          //on donne un titre a la fenetre
        this.setSize(960, 620);                         //on redimenssione la fenetre en cours
        this.setLocationRelativeTo(null);               //on centre la fenetre a l'ecran
        this.setResizable(false);                       //on demande a ce que la fenetre ne puisse pas etre redimentionnee
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //permet de fermer correctement la fenetre
        this.addMouseListener(new AccueilListener());                    //on permet la gestion des clis sur la fenetre
    }

    private void traitement() {
        //on redefinit le panel de fond de la frame
        setContentPane(panFond);
    }

    /**
     * Fonction qui permet d'ajouter une image de fond à un panel
     */
    public void ajoutImageFond() {
        try {
            fond = ImageIO.read(new File("images/fond-optim3.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //on ajoute l'image au panel panFond
        panFond = new JPanelFondNormal(fond);
    }
}
