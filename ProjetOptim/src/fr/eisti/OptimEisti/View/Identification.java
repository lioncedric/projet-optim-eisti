/**
 * 
 */
package fr.eisti.OptimEisti.View;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JDialog;

import fr.eisti.OptimEisti.Controler.ConnexionControler;


/**
 * @author Razavet Maël, Lion Cédric, Klelifa Sarah, Gallet Meriadec
 *
 */
public class Identification extends JDialog{

	/**
	 * Declaration des attributs
	 */
	private static final long serialVersionUID = 1L;
	private JPanelFondIdentification panFond;
	private Image fond;

        //Constructeur par défaut
	public Identification(){
                //Appel de la méthode qui initialise les variables
		initialiserVariables();
                //Méthode qui ajoute une image de fond
		ajoutImageFond();
                //Méthode qui ajoute le panel sur la fenêtre
		traitement();
	}
        /**
         * Initialise les variables de la fenêtre
         */
	public void initialiserVariables(){
                //ajout de titre
		this.setTitle("Identification");
		this.setSize(450,300);          //on redimenssione la fenetre en cours
		this.setLocationRelativeTo(null);//on centre la fenetre a l'ecran
		this.setResizable(false);        //on demande a ce que la fenetre ne puisse pas etre redimentionnee
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE); //permet de fermer correctement la fenetre
		this.setModal(true);       //on desactive tout le reste
                this.setUndecorated(true);
	}

        /**
         * ajoute le panel sur la fenêtre
         */
	public void traitement(){
		//on ajoute le panel sur la fenetre     
		this.setContentPane(panFond);
	}

        /**
         * ajoute l'image sur le panel de la fenêtre
         */
	public void ajoutImageFond(){
		try {
                        //ajout d'une image de fond
			fond = ImageIO.read(new File("images/identification.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
                //on la met sur le panel
		this.panFond=new JPanelFondIdentification(fond);
                //ajout d'un écouteur d'évènement sur la souris
		this.addMouseListener(new ConnexionControler(this.panFond.getJtfNomUtilisateur(), this.panFond.getJtfMdp(), this));
                //ajout d'un écouteur d'évènement pour le mot de passe
                this.panFond.getJtfMdp().addKeyListener(new ConnexionControler(this.panFond.getJtfNomUtilisateur(), this.panFond.getJtfMdp(), this));
                //ajout d'un écouteur d'évènement pour le nom d'utilisateur
                this.panFond.getJtfNomUtilisateur().addKeyListener(new ConnexionControler(this.panFond.getJtfNomUtilisateur(), this.panFond.getJtfMdp(), this));
                
	}
}
