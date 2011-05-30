/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.eisti.optimEisti_RaLiGaKl.controler.problemes;

import fr.eisti.optimEisti_RaLiGaKl.Main;
import fr.eisti.optimEisti_RaLiGaKl.model.Contrainte;
import fr.eisti.optimEisti_RaLiGaKl.model.Probleme;
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
 * Classe qui gére l'interaction avec le panel contenant les problèmes
 * @author Razavet Maël, Lion Cédric, Klelifa Sarah, Gallet Mériadec
 * @version 1.0
 */
public class PanelProblemeListener implements ChangeListener, ActionListener {

    private ArrayList<String> valeursTextFields;
    private ArrayList<Contrainte> contraintes;
    private PanelProbleme panelProbleme;

    /**
     * Constructeur d'un listener du panel où se trouve le problème
     * @param panelProbleme : le JPanel où se trouve le probleme
     */
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

         //on ajoute la nouvelle fonction objective et les listeners qui vont avec
        ((PanelProbleme) Main.fenetrePrincipale.getDroite().getSelectedComponent()).getPanDonnees().add(new JLabel("y="));
        JTextField jtff = new JTextField(3);
        ((PanelProbleme) Main.fenetrePrincipale.getDroite().getSelectedComponent()).getPanDonnees().add(jtff);
        jtff.addKeyListener(new SaveListener());
        ((PanelProbleme) Main.fenetrePrincipale.getDroite().getSelectedComponent()).getPanDonnees().add(new JLabel("x0"));

        //on ajoute autant de variable que l'utilisateur a choisit grace au slide
        for (int i = 1; i < source.getValue(); i++) {
            ((PanelProbleme) Main.fenetrePrincipale.getDroite().getSelectedComponent()).ajouterVariable(i);
        }

        //on remet la valeur des anciennes variables deja presente avant de modifier le nombre de variable
        for (int i = 0; i < source.getValue(); i++) {
            JTextField jtf = (JTextField) (((PanelProbleme) Main.fenetrePrincipale.getDroite().getSelectedComponent()).getPanDonnees().getComponent(3 * i + 1));
            if (i < valeursTextFields.size()) {
                jtf.setText(valeursTextFields.get(i));
            }
        }

        //de même on met a jour le tableau de contrainte
        this.contraintes = ((PanelProbleme) Main.fenetrePrincipale.getDroite().getSelectedComponent()).getPanTableau().enregistrerContraintes();
        ((PanelProbleme) Main.fenetrePrincipale.getDroite().getSelectedComponent()).raffraichitTabContrainte(this.contraintes);
        ((PanelProbleme) Main.fenetrePrincipale.getDroite().getSelectedComponent()).getPanTableau().rempliTableau(this.contraintes);
        //on finit par raffraichir la page
        Main.fenetrePrincipale.validate();
        Main.fenetrePrincipale.repaint();
    }

    /**
     * Permet de récupèrer les contraintes de la fonction
     * @return une liste de toutes les contraintes de la fonction
     */
    public ArrayList<Contrainte> getContraintes() {
        return contraintes;
    }

    /**
     * Permet de modifier la liste de contrainte
     * @param contraintes : les nouvelles contraintes
     */
    public void setContraintes(ArrayList<Contrainte> contraintes) {
        this.contraintes = contraintes;
    }

    /**
     * Permet de récupèrer la valeur des champs de texte sous forme de liste
     * @return liste des champs de texte
     */
    public ArrayList<String> getValeursTextFields() {
        return valeursTextFields;
    }

    /**
     * Permet de modifier la valeur de la liste des champs de texte
     * @param valeursTextFields : la nouvelle liste de textfield
     */
    public void setValeursTextFields(ArrayList<String> valeursTextFields) {
        this.valeursTextFields = valeursTextFields;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Si l'utilisateur clique sur le bouton pour calculer les solutions du probleme alors on lance l'algorithme du simplexe
        if (e.getSource() == this.panelProbleme.getPanelResultat().getCalculer()) {
            PanelResultat.effacer();
            //on creer un nouveau problème avec les valeur du probleme en cours
            Probleme p = new  Probleme();
            p.renseignerProbleme((PanelProbleme) Main.fenetrePrincipale.getDroite().getSelectedComponent());
            //et on genere la solution par l'algorithme du simplexe
            ((PanelProbleme) Main.fenetrePrincipale.getDroite().getSelectedComponent()).getPanelResultat().miseajour(p.chercherSolutions());
        //si l'utilisateur clique sur le bouton effacer, alors on remet le panel ré-initialise le panel
        } else if (e.getSource() == this.panelProbleme.getPanelResultat().getEffacer()) {
            PanelResultat.effacer();
        }
        //on verifie si un des problemes  a été modifié
        SaveListener.estmodifié();
    }
}
