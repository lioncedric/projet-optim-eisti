package fr.eisti.OptimEisti.Controler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import fr.eisti.OptimEisti.Model.ModelTab;

/**
 * Classe permettant de gerer l'ajout d'une ligne dans le tableau
 * @author Razavet Maël, Lion Cédric, Klelifa Sarah, Gallet Meriadec
 */
public class MoreListener implements ActionListener{

	private JTable tableau;
	private int nbVariable;

        /**
         * Constructeur de la classe
         * @param tableau : le tableau auquelle on veut ajouter une ligne
         * @param nbVariable : le nombre de variable de la fonction objective
         */
	public MoreListener(JTable tableau,int nbVariable){
		this.tableau = tableau;
		this.nbVariable = nbVariable;
	}
	
	public void actionPerformed(ActionEvent e) {
            
            //on initialise un booleen qui va nous permettre de savoir si la ligne et bien rempli avant d'en ajouter une autre
            Boolean ligneRempli;
            ligneRempli = false;
            if(tableau.getRowCount()>0){
                //on va parcourir la dernière ligne du tableau afin de savoir si elle est bien rempli
                for(int i=0; i<nbVariable; i++){
                    //on récupère la valeur de chaque cellule correspondant aux variables
                    Object value = tableau.getValueAt(tableau.getRowCount()-1, i);
                    double valueString = Double.parseDouble(value.toString());
                    //on test si il y a au moins une cellule correspondant aux variables qui est différente de zéro et si le signe a bien été choisi
                    ligneRempli = ligneRempli || (!(valueString == 0) && tableau.getValueAt(tableau.getRowCount()-1, tableau.getColumnCount()-3)!=null);
                }
            }else{
                ligneRempli = true;
            }
            //si la ligne est bien remplie
            if(ligneRempli){
                //alors on ajoute une nouvelle ligne type au tableau
		Object[] donnee = new Object[nbVariable+3];
		for(int i=0; i<nbVariable; i++){
			donnee[i]=0;
		}
		
		donnee[nbVariable]=null;
		donnee[nbVariable+1]=0;
		donnee[nbVariable+2]=null;
		
		((ModelTab)tableau.getModel()).addRow(donnee);
            }else{//sinon on informe l'JOptionutilisateur que la dernière ligne est mal remplie
                JOptionPane.showMessageDialog(null, "Vous n'avez pas remplie la dernière ligne.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
	}
}
