package fr.eisti.optimEisti_RaLiGaKl.view.problemes.contraintes;

import java.awt.Component;
import javax.swing.*;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Classe permettant de gérer l'affichage de la combo box dans le teableau
 * @author Razavet Maël, Lion Cédric, Klelifa Sarah, Gallet Mériadec
 * @version 1.0
 */
public class TableComponent extends DefaultTableCellRenderer {

        @Override
	public Component getTableCellRendererComponent(JTable table,
			Object value, boolean isSelected, boolean hasFocus, int row,
			int column) {
		//Si la valeur de la cellule est un combobox, on transtype notre valeur
		if (value instanceof JComboBox){
			return (JComboBox) value;
		}
		else
			return this;
	}
}
