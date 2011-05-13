/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.eisti.optimEisti_RaLiGaKl.controler.problemes;

import fr.eisti.optimEisti_RaLiGaKl.Main;
import fr.eisti.optimEisti_RaLiGaKl.model.Contrainte;
import fr.eisti.optimEisti_RaLiGaKl.model.Probleme;
import fr.eisti.optimEisti_RaLiGaKl.model.Simplexe;
import fr.eisti.optimEisti_RaLiGaKl.view.problemes.PanelProbleme;
import fr.eisti.optimEisti_RaLiGaKl.view.problemes.PanelResultat;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Administrator
 */
public class PanelProblemeListener implements ChangeListener, ActionListener {

    private ArrayList<String> valeursTextFields;
    private ArrayList<Contrainte> contraintes;
    private PanelProbleme panelProbleme;

    public PanelProblemeListener(PanelProbleme panelProbleme) {
        //on initialise la liste des valeurs des textFields
        valeursTextFields = new ArrayList();
        //on initialise la liste des contraintes
        contraintes = new ArrayList<Contrainte>();
        this.panelProbleme = panelProbleme;

    }

    @Override
    public void stateChanged(ChangeEvent e) {
        //on initialise la liste des valeurs des textFields
        valeursTextFields = new ArrayList();
        //on initialise la liste des contraintes
        contraintes = new ArrayList<Contrainte>();
        //on initialise le slider
        JSlider source = (JSlider) e.getSource();

        //on stocke la valeur des textfield dans une liste pour pouvoir les récupérés si le slider change de valeur
        for (int i = 0; i < source.getValue(); i++) {
            try {
                //on recupere chaque textefield de la fonction objectif
                JTextField jtf = (JTextField) (((PanelProbleme) Main.fenetrePrincipale.getDroite().getSelectedComponent()).getPanDonnees().getComponent(3 * i + 1));
                //on ajoute la valeur du textfield correspondant à la liste
                valeursTextFields.add(jtf.getText());
            } catch (ArrayIndexOutOfBoundsException aioobe) {
            }
        }

        //on supprime tous les composants du panel contenant la fonction objectif avec tous les panels
        ((PanelProbleme) Main.fenetrePrincipale.getDroite().getSelectedComponent()).getPanDonnees().removeAll();


        ((PanelProbleme) Main.fenetrePrincipale.getDroite().getSelectedComponent()).getPanDonnees().add(new JLabel("y="));
        JTextField jtff = new JTextField(3);
        ((PanelProbleme) Main.fenetrePrincipale.getDroite().getSelectedComponent()).getPanDonnees().add(jtff);
        jtff.addKeyListener(new SaveListener());
        ((PanelProbleme) Main.fenetrePrincipale.getDroite().getSelectedComponent()).getPanDonnees().add(new JLabel("x0"));

        for (int i = 1; i < source.getValue(); i++) {
            ((PanelProbleme) Main.fenetrePrincipale.getDroite().getSelectedComponent()).ajouterVariable(i);
        }

        for (int i = 0; i < source.getValue(); i++) {
            JTextField jtf = (JTextField) (((PanelProbleme) Main.fenetrePrincipale.getDroite().getSelectedComponent()).getPanDonnees().getComponent(3 * i + 1));
            if (i < valeursTextFields.size()) {
                jtf.setText(valeursTextFields.get(i));
            }
        }

        this.contraintes = ((PanelProbleme) Main.fenetrePrincipale.getDroite().getSelectedComponent()).getPanTableau().enregistrerContraintes();
        ((PanelProbleme) Main.fenetrePrincipale.getDroite().getSelectedComponent()).raffraichitTabContrainte(this.contraintes);
        ((PanelProbleme) Main.fenetrePrincipale.getDroite().getSelectedComponent()).getPanTableau().rempliTableau(this.contraintes);
        Main.fenetrePrincipale.validate();
        Main.fenetrePrincipale.repaint();
    }

    public ArrayList<Contrainte> getContraintes() {
        return contraintes;
    }

    public void setContraintes(ArrayList<Contrainte> contraintes) {
        this.contraintes = contraintes;
    }

    public ArrayList<String> getValeursTextFields() {
        return valeursTextFields;
    }

    public void setValeursTextFields(ArrayList<String> valeursTextFields) {
        this.valeursTextFields = valeursTextFields;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.panelProbleme.getPanelResultat().getCalculer()) {
            Probleme p = ((PanelProbleme) Main.fenetrePrincipale.getDroite().getSelectedComponent()).getProbleme();
            p.renseignerProbleme((PanelProbleme) Main.fenetrePrincipale.getDroite().getSelectedComponent());
            //et on genere la solution par l'algorithme du simplexe
            
            ((PanelProbleme) Main.fenetrePrincipale.getDroite().getSelectedComponent()).getPanelResultat().miseajour();
            p.setResultat(p.resoudre());
            SaveListener.estmodifié();
        }
        if (e.getSource() == this.panelProbleme.getPanelResultat().getEffacer()) {
            PanelResultat.effacer();
        }
    }
}
