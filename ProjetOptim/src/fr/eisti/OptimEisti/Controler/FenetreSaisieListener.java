package fr.eisti.OptimEisti.Controler;

import fr.eisti.OptimEisti.Model.BDDUtilisateur;
import fr.eisti.OptimEisti.Model.BddProbleme;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import fr.eisti.OptimEisti.View.Fenetre;
import fr.eisti.OptimEisti.Main;
import fr.eisti.OptimEisti.Model.Contrainte;
import fr.eisti.OptimEisti.View.JPanelProbleme;
import java.awt.event.ComponentListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * @author Administrator
 *
 */
public class FenetreSaisieListener implements ChangeListener, ActionListener, ComponentListener {

    private Fenetre fenetre;
    private ArrayList<String> a;
    private ArrayList<Contrainte> contraintes;

    public FenetreSaisieListener(Fenetre f1) {
        this.fenetre = f1;
        a = new ArrayList();
        contraintes = new ArrayList<Contrainte>();
    }

    public FenetreSaisieListener() {
        a = new ArrayList();
        contraintes = new ArrayList<Contrainte>();
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        //Des qu'on change le slider on grise l'onglet des contraintes afin d'obliger
        //l'utilisateur à cliquer sur le bouton et à vérifier si tout les champs son bien rempli
        a = new ArrayList();
        contraintes = new ArrayList<Contrainte>();
        JSlider source = (JSlider) e.getSource();

        for (int i = 0; i < source.getValue(); i++) {
            try {
                JTextField jtf = (JTextField) (((JPanelProbleme) Main.fenetrePrincipale.getDroite().getSelectedComponent()).getPanDonnees().getComponent(3 * i + 1));
                a.add(jtf.getText());
            } catch (ArrayIndexOutOfBoundsException aioobe) {
            }
        }

        ((JPanelProbleme) Main.fenetrePrincipale.getDroite().getSelectedComponent()).getPanDonnees().removeAll();

        ((JPanelProbleme) Main.fenetrePrincipale.getDroite().getSelectedComponent()).getPanDonnees().add(new JLabel("y="));
        ((JPanelProbleme) Main.fenetrePrincipale.getDroite().getSelectedComponent()).getPanDonnees().add(new JTextField(3));
        ((JPanelProbleme) Main.fenetrePrincipale.getDroite().getSelectedComponent()).getPanDonnees().add(new JLabel("x0"));

        for (int i = 1; i < source.getValue(); i++) {
            ((JPanelProbleme) Main.fenetrePrincipale.getDroite().getSelectedComponent()).ajouterVariable(i);
        }

        for (int i = 0; i < source.getValue(); i++) {
            JTextField jtf = (JTextField) (((JPanelProbleme) Main.fenetrePrincipale.getDroite().getSelectedComponent()).getPanDonnees().getComponent(3 * i + 1));
            if (i < a.size()) {
                jtf.setText(a.get(i));
            }
        }

        this.contraintes = ((JPanelProbleme) Main.fenetrePrincipale.getDroite().getSelectedComponent()).getPanTableau().enregistrerContraintes();
        ((JPanelProbleme) Main.fenetrePrincipale.getDroite().getSelectedComponent()).raffraichitTabContrainte(this.contraintes);
        ((JPanelProbleme) Main.fenetrePrincipale.getDroite().getSelectedComponent()).getPanTableau().rempliTableau(this.contraintes);
        Main.fenetrePrincipale.validate();
        Main.fenetrePrincipale.repaint();
    }

    @Override
    @SuppressWarnings("static-access")
    public void actionPerformed(ActionEvent e) {

     if (e.getSource() == this.fenetre.getImportXml()) {
            try {
                JFileChooser fc = new JFileChooser();
                int returnVal = fc.showOpenDialog(this.fenetre.getDroite());
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    //  BddProbleme.importerProbleme(file);
                    BddProbleme.importerProbleme(file);
                }
            } catch (Exception ex) {
            }
        } else if (e.getSource() == this.fenetre.getDeconnexion()) {
            Main.getAccueil().setVisible(true);
            this.fenetre.dispose();
        } else if (e.getSource() == this.fenetre.getQuitter()) {
            System.exit(0);
       
          
        } else if (e.getSource() == this.fenetre.getSauvegarder()) {
            BddProbleme.save(BDDUtilisateur.getNomUtilisateur());
        } else if (e.getSource() == this.fenetre.getRecharger()) {
            try {
                BddProbleme.load(BDDUtilisateur.getNomUtilisateur());
                Main.fenetrePrincipale.getGauche().miseajour();
                Main.fenetrePrincipale.getDroite().removeAll();
                Main.fenetrePrincipale.repaint();
            } catch (IOException ex) {
            }
        }
        else if(e.getSource() == this.fenetre.getPetitEcran()){
            this.fenetre.setSize(this.fenetre.getMinimumSize());
            this.fenetre.setLocationRelativeTo(null);
        }
        else if(e.getSource() == this.fenetre.getPleinEcran()){
            this.fenetre.setExtendedState(this.fenetre.MAXIMIZED_BOTH);
        }
    }

    public ArrayList<Contrainte> getContraintes() {
        return contraintes;
    }

    public void setContraintes(ArrayList<Contrainte> contraintes) {
        this.contraintes = contraintes;
    }

    @Override
    public void componentResized(ComponentEvent e) {
            this.fenetre.appliquerChangementSplitPane();
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }
}
