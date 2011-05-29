package fr.eisti.optimEisti_RaLiGaKl.view.compte;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

/**
 * JPanel qui permet de mettre une image en fond
 * @author Razavet Maël, Lion Cédric, Klelifa Sarah, Gallet Mériadec
 * @version 1.0
 */
public class JPanelFondNormal extends JPanel{

	//Declaration des variables
	private static final long serialVersionUID = 1L;
	private Image image;

	/**
	 * Constructeur
	 * @param image l'image de fond
	 */
	public JPanelFondNormal(Image image){
		this.image=image;
	}

	/**
	 * Redefinition de la methode paintComponent
	 */
        @Override
	public void paintComponent(Graphics g){

		g.drawImage(image, 0, 0, this);
	}
}
