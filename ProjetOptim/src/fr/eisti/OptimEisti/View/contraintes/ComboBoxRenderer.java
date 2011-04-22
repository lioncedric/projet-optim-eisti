package fr.eisti.OptimEisti.View.contraintes;

import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
/*
 * Classe permettant de gérer la saisie dans une JCombobox d'une table
 * @author Razavet Maël, Lion Cédric, Klelifa Sarah, Gallet Meriadec
 */
public class ComboBoxRenderer extends JComboBox implements TableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
        /**
         * Constructeur d'un renderer de JCombobox
         */
	public ComboBoxRenderer(){
                //on ajoute les valeurs que l'utilisateur pourra choisir dans la liste déroulante
		this.addItem("=");
		this.addItem("<=");
		this.addItem(">=");
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,boolean isSelected, boolean hasFocus, int row, int column) {
                //on indique a la liste déroulante qu'elle cellule a été choisi
                this.setSelectedItem(value);
       		return this;
	}

}
