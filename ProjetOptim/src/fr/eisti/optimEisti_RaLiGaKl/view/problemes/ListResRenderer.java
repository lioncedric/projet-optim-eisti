
package fr.eisti.optimEisti_RaLiGaKl.view.problemes;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * Classe qui défini comment est peint le label
 * @author Razavet Maël, Lion Cédric, Klelifa Sarah, Gallet Mériadec
 */
public class ListResRenderer extends JLabel implements ListCellRenderer {

    public ListResRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
    
        Color background;
        Color foreground;

        if (index == 0) {
            setText("y = " + value.toString());
             foreground = new Color(255, 50, 50);
             background = Color.WHITE;
        } else {
             setText("x" + (index-1) + " = " + value.toString());
               background = Color.WHITE;
             foreground = Color.BLACK;
        }        

        setBackground(background);
        setForeground(foreground);

        return this;
    }
}
