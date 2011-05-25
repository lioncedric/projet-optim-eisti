/**
 * 
 */
package fr.eisti.optimEisti_RaLiGaKl.view.compte;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

/**
 * Classe qui permet de gérer le fond d'un panel selon le choix de l'utilisateur
 * @author Razavet Maël, Lion Cédric, Klelifa Sarah, Gallet Mériadec
 */
public class JPanelFondChoixUtilisateur extends JPanel{

	//Declaration des variables
	private static final long serialVersionUID = 1L;
	private Image image;
	
	
	/**
	 * Constructeur
	 * @param image l'image de fond
	 */
	public JPanelFondChoixUtilisateur(Image image){
		
		this.image=image;
	}

        @Override
	public void paintComponent(Graphics g){
		g.drawImage(image, 0, 0, this);
	}
	
}
