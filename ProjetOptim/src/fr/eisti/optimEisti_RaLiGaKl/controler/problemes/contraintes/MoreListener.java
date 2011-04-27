package fr.eisti.optimEisti_RaLiGaKl.controler.problemes.contraintes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import fr.eisti.optimEisti_RaLiGaKl.view.problemes.contraintes.ModelTab;
import fr.eisti.optimEisti_RaLiGaKl.view.problemes.contraintes.Tableau;

/**
 * Classe permettant de gerer l'ajout d'une ligne dans le tableau
 * @author Razavet Maël, Lion Cédric, Klelifa Sarah, Gallet Meriadec
 */
public class MoreListener implements ActionListener{

	private Tableau panTableau;
	private int nbVariable;

        /**
         * Constructeur de la classe
         * @param panTableau : le panel contenant le tableau auquelle on veut ajouter une ligne
         * @param nbVariable : le nombre de variable de la fonction objective
         */
	public MoreListener(Tableau panTableau,int nbVariable){
		this.panTableau = panTableau;
		this.nbVariable = nbVariable;
	}
	
	public void actionPerformed(ActionEvent e) {
            
            
            //si la ligne est bien remplie
            if(panTableau.ligneRempli()){
                //alors on ajoute une nouvelle ligne type au tableau
		Object[] donnee = new Object[nbVariable+3];
		for(int i=0; i<nbVariable; i++){
			donnee[i]=0;
		}
		
		donnee[nbVariable]=null;
		donnee[nbVariable+1]=0;
		donnee[nbVariable+2]=null;
		
		((ModelTab)panTableau.getTable().getModel()).addRow(donnee);
            }else{//sinon on informe l'JOptionutilisateur que la dernière ligne est mal remplie
                JOptionPane.showMessageDialog(null, "Vous n'avez pas remplie la dernière ligne.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
	}
}
