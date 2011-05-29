package fr.eisti.optimEisti_RaLiGaKl.controler.problemes.contraintes;
import java.awt.Component;
import java.awt.Image;
import java.awt.Toolkit;
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
	 * @param checkBox la checkbox a editer
	 */
	public ButtonEditor(JCheckBox checkBox) {
            //Par défaut, ce type d'objet travaille avec un JCheckBox
            super(checkBox);
	    //On crée à nouveau notre bouton
            button = new JButton();
	    button.setOpaque(true);
	    //On lui attribue un listener
	    button.addActionListener(bListener);
               Image img= Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/fermer.gif"));
            button.setIcon(new ImageIcon(img));
          
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


