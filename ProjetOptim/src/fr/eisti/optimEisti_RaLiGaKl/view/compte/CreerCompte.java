package fr.eisti.optimEisti_RaLiGaKl.view.compte;

import fr.eisti.optimEisti_RaLiGaKl.controler.compte.CreerCompteListener;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JDialog;

/**
 * Classe qui permet de créer un nouveau compte d'utilisateur
 * @author Razavet Maël, Lion Cédric, Klelifa Sarah, Gallet Mériadec
 */
public class CreerCompte extends JDialog {

    //déclaration des attributs
    private Image fond;
    private JPanelFondCreerCompte panFond;

    /**
     * Constructeur par défaut
     */
    public CreerCompte() {
        init();
        ajoutImageFond();
        traitement();
    }

    /**
     * Initialise la fenêtre
     */
    private void init() {
        this.setTitle("Creation de Compte");
        this.setSize(450, 300);                 //on redimenssione la fenetre en cours
        this.setLocationRelativeTo(null);       //on centre la fenetre a l'ecran
        this.setResizable(false);               //on demande a ce que la fenetre ne puisse pas etre redimentionnee
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE); //permet de fermer correctement la fenetre
        this.setModal(true);                    //on desactive tout le reste
    }

    /**
     * Ajoute le panel sur la fenêtre
     */
    private void traitement() {
        this.setContentPane(panFond);
    }

    /**
     * Permet d'ajouter une image de fond sur le jdialog
     */
    private void ajoutImageFond() {
      
            //lecture de l'image
                
   fond =   Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/creerCompte.png"));
       
        //instanciation d'un panel
        panFond = new JPanelFondCreerCompte(fond);
        //ajout d'un écouteur de souris
        this.addMouseListener(new CreerCompteListener(this));
    }

    public JPanelFondCreerCompte getPanFond() {
        return panFond;
    }
}
