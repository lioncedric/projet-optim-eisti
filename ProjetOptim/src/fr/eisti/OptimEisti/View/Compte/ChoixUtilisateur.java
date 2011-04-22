/**
 * 
 */
package fr.eisti.OptimEisti.View.Compte;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JPanel;

import fr.eisti.OptimEisti.Main;
import fr.eisti.OptimEisti.View.Fenetre;


/**
 * @author Administrator
 *
 */
public class ChoixUtilisateur extends JDialog implements MouseListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Image fond;
	private JPanel panFond;
	
	public ChoixUtilisateur(){
		initialiserVariables();
		ajoutImageFond();
		traitement();
	}
	
	

	private void initialiserVariables() {
		// TODO Auto-generated method stub
		this.setTitle("Choix de la fonction desiree");
		this.setSize(450,300);          //on redimenssione la fenetre en cours
		this.setLocationRelativeTo(null);//on centre la fenetre a l'ecran
		this.setResizable(false);        //on demande a ce que la fenetre ne puisse pas etre redimentionnee
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); //permet de fermer correctement la fenetre
		this.addMouseListener(this);
		this.setModal(true);       //on desactive tout le reste
	}
	
	
	private void traitement() {
		// TODO Auto-generated method stub
		setContentPane(panFond);//on redefinit le panel
	}


	public void ajoutImageFond(){
		try {
			fond = ImageIO.read(new File("images/choixUtilisateur.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		panFond = new JPanelFondChoixUtilisateur(fond);
	}
	
	/**
	 * Redefinition de la methode paintComponent
	 */
	public void paintComponent(Graphics g){
		g.drawImage(fond, 0, 0, this);
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getX()>119 && e.getX()<358 && e.getY()>165 && e.getY()<183){
			Fenetre fen;
			fen=new Fenetre();
			fen.setVisible(true);
			fen.setAlwaysOnTop(true);
			this.dispose();
			Main.getAccueil().dispose();
		}
		else if(e.getX()>114 && e.getX()<370 && e.getY()>215 && e.getY()<234){
                  

		}
		else{
			//do nothing
		}
	}



	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}



	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
