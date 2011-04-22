package fr.eisti.OptimEisti.Controler;
import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;

/**
 *Classe permettant d'afficher un bouton dans un JTable
 *@author Razavet Maël, Lion Cédric, Klelifa Sarah, Gallet Meriadec
 */
public class ButtonEditor extends DefaultCellEditor {

	protected JButton button;
	private LessListener bListener = new LessListener();

	/**
	 * Constructeur avec une checkBox
	 * @param checkBox
	 * @param count
	 */
	public ButtonEditor(JCheckBox checkBox) {
            //Par défaut, ce type d'objet travaille avec un JCheckBox
            super(checkBox);
	    //On crée à nouveau notre bouton
            button = new JButton();
	    button.setOpaque(true);
	    //On lui attribue un listener
	    button.addActionListener(bListener);
            button.setIcon(new ImageIcon("images/fermer.gif"));
	}

        @Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		//On définit le numéro de lignes à notre listener
		bListener.setRow(row);
		//On passe aussi le tableau pour des actions potentielles
		bListener.setTable(table);
		//On réaffecte le libellé au bouton
		button.setText( (value ==null) ? "" : value.toString() );
		//On renvoie le bouton
	    return button;
	}
}


