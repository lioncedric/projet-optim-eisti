package fr.eisti.OptimEisti.Controler;

import fr.eisti.OptimEisti.Model.ModelTab;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JTable;

        /**
         * Classe permettant d'enlever une ligna a un tableau
         * @author Razavet Maël, Lion Cédric, Klelifa Sarah, Gallet Meriadec
         */
	class LessListener implements ActionListener{

		  private int row;
		  private JTable table;

                  /**
                   * Fonction permettant de modifier une ligne d'un tableau
                   * @param row : la ligne a modifier
                   */
		  public void setRow(int row){this.row = row;}

                  /**
                   * Fonction permettant de modifier un tableau
                   * @param table : le tableau a modifier
                   */
		  public void setTable(JTable table){this.table = table;}

                  /**
                   * Fonction qui enleve une ligne d'un tableau
                   * @param event
                   */
		  public void actionPerformed(ActionEvent event) {
                        //si le nombre de ligne est bien supérieur a zéro
			if(table.getRowCount() > 0){
				//On enleve la ligne choisi au tableau
				((ModelTab)table.getModel()).removeRow(this.row);

			}
		  }
	  }