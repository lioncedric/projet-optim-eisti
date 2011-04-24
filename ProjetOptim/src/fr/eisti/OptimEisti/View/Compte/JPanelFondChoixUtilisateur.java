/**
 * 
 */
package fr.eisti.OptimEisti.View.Compte;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

/**
 * @author Administrator
 *
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
	
	/**
	 * Redefinition de la methode paintComponent
	 */
        @Override
	public void paintComponent(Graphics g){
		g.drawImage(image, 0, 0, this);
	}
	
}
