package fr.eisti.optimEisti_RaLiGaKl.view.problemes.contraintes;

import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * Classe permettant la gestion de bouton dans une JTable
 * @author Razavet Maël, Lion Cédric, Klelifa Sarah, Gallet Meriadec
 */
public class JButtonRenderer extends JButton implements TableCellRenderer{
	private static final long serialVersionUID = 1L;

        /**
         * Constructeur du renderer
         */
	public JButtonRenderer(){
                //on applique une image au bouton
		super(new ImageIcon("images/fermer.gif"));
	}
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean isFocus, int row, int col) {
		return this;
	}
}
