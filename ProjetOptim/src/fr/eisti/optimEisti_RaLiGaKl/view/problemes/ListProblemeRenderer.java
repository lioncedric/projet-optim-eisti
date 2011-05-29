
package fr.eisti.optimEisti_RaLiGaKl.view.problemes;

import fr.eisti.optimEisti_RaLiGaKl.model.BddProbleme;
import fr.eisti.optimEisti_RaLiGaKl.model.Probleme;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * Classe qui gère le rendu d'un jlabel
 * @author Razavet Maël, Lion Cédric, Klelifa Sarah, Gallet Mériadec
 */
public class ListProblemeRenderer extends JLabel implements ListCellRenderer {

    private Color couleurTexte;
    private final Color couleurFond;//valeur inchangeable
    private Color couleurFondSelection;
    private Color couleurTexteSelection;

    /**
     * Constructeur d'un renderer d'une liste de probleme
     */
    public ListProblemeRenderer() {
        setOpaque(true);
        this.couleurFond=new Color(255,255,255,0);
        this.couleurTexte=Color.BLACK;
        this.couleurFondSelection=new Color(222,240,243);
        this.couleurTexteSelection=Color.BLACK;
    }
    
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Probleme p = (Probleme) value;
        setText((BddProbleme.nombreProblemes()-p.getNumero()) + ") " + p.getTitre());
        Color background;
        Color foreground;
        // check if this cell represents the current DnD drop location
        JList.DropLocation dropLocation = list.getDropLocation();
        if (dropLocation != null
                && !dropLocation.isInsert()
                && dropLocation.getIndex() == index) {

            background = this.couleurFond;
            foreground = this.couleurTexte;

            // check if this cell is selected
        } else if (isSelected) {
            background = this.couleurFondSelection;
            foreground = this.couleurTexteSelection;

            // unselected, and not the DnD drop location
        } else {
            background = this.couleurFond;
            foreground = this.couleurTexte;
        }

        setBackground(background);
        setForeground(foreground);

        return this;
    }
    
    public void changerCouleurTexte(Color c){
        this.couleurTexte=c;
    }
    
    public void changerCouleurFondSelection(Color c){
        this.couleurFondSelection=c;
    }

    public void changerCouleurTexteSelection(Color c){
        this.couleurTexteSelection=c;
    }

    public Color getCouleurFondSelection() {
        return couleurFondSelection;
    }

    public Color getCouleurTexte() {
        return couleurTexte;
    }

    public Color getCouleurTexteSelection() {
        return couleurTexteSelection;
    }
}
