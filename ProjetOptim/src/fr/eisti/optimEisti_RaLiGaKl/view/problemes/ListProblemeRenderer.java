/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.eisti.optimEisti_RaLiGaKl.view.problemes;

import fr.eisti.optimEisti_RaLiGaKl.model.Probleme;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class ListProblemeRenderer extends JLabel implements ListCellRenderer {

    public ListProblemeRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Probleme p = (Probleme) value;
        setText(p.getNumero() + ") " + p.getTitre());
        Color background;
        Color foreground;
        // check if this cell represents the current DnD drop location
        JList.DropLocation dropLocation = list.getDropLocation();
        if (dropLocation != null
                && !dropLocation.isInsert()
                && dropLocation.getIndex() == index) {

           background = new Color(255, 50, 50, 0);
            foreground = Color.BLACK;

            // check if this cell is selected
        } else if (isSelected) {
            background = Color.BLUE;
            foreground = Color.WHITE;

            // unselected, and not the DnD drop location
        } else {
            background = new Color(255, 50, 50, 0);
            foreground = Color.BLACK;
        }

        setBackground(background);
        setForeground(foreground);

        return this;
    }
}
