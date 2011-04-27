package fr.eisti.optimEisti_RaLiGaKl.controler.problemes;

import fr.eisti.optimEisti_RaLiGaKl.controler.problemes.SaveListener;
import fr.eisti.optimEisti_RaLiGaKl.model.BDDUtilisateur;
import fr.eisti.optimEisti_RaLiGaKl.model.BddProbleme;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import fr.eisti.optimEisti_RaLiGaKl.view.problemes.Fenetre;
import fr.eisti.optimEisti_RaLiGaKl.Main;
import fr.eisti.optimEisti_RaLiGaKl.model.Contrainte;
import fr.eisti.optimEisti_RaLiGaKl.view.problemes.PanelProbleme;
import java.awt.event.ComponentListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * Classe qui écoute les actions qui ont lieu sur la fenêtre principale
 * @author Razavet Maël, Lion Cédric, Klelifa Sarah, Gallet Mériadec
 * @version 1.0
 */
public class FenetreListener implements ChangeListener, ActionListener, ComponentListener {

    private Fenetre fenetre;
    private ArrayList<String> a;
    private ArrayList<Contrainte> contraintes;

    public FenetreListener(Fenetre f1) {
        this.fenetre = f1;
        a = new ArrayList();
        contraintes = new ArrayList<Contrainte>();
    }

    public FenetreListener() {
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
                JTextField jtf = (JTextField) (((PanelProbleme) Main.fenetrePrincipale.getDroite().getSelectedComponent()).getPanDonnees().getComponent(3 * i + 1));
               
                a.add(jtf.getText());
            } catch (ArrayIndexOutOfBoundsException aioobe) {
            }
        }

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
            if (i < a.size()) {
                jtf.setText(a.get(i));
            }
        }

        this.contraintes = ((PanelProbleme) Main.fenetrePrincipale.getDroite().getSelectedComponent()).getPanTableau().enregistrerContraintes();
        ((PanelProbleme) Main.fenetrePrincipale.getDroite().getSelectedComponent()).raffraichitTabContrainte(this.contraintes);
        ((PanelProbleme) Main.fenetrePrincipale.getDroite().getSelectedComponent()).getPanTableau().rempliTableau(this.contraintes);
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
                    BddProbleme.importerProbleme(file);
                }
            } catch (Exception ex) {
            }
        } else if (e.getSource() == this.fenetre.getDeconnexion()) {
            Main.accueil.setVisible(true);
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
        } //si l'utilisateur veut mettre la fenêtre en taille minimale
        else if (e.getSource() == this.fenetre.getPetitEcran()) {
            //on applique le changement
            this.fenetre.setSize(this.fenetre.getMinimumSize());
            //et on la recentre à l'écran
            this.fenetre.setLocationRelativeTo(null);
        } //si l'utilisateur veut mettre la fenêtre en plein écran
        else if (e.getSource() == this.fenetre.getPleinEcran()) {
            //on passe tout simplement en mode 'Plein Ecran' en maximisant hauteur et largeur
            this.fenetre.setExtendedState(this.fenetre.MAXIMIZED_BOTH);
        } else if (e.getSource() == this.fenetre.getAffResHtml()) {
            //ouvrir la page html
            
        }
    }

    public ArrayList<Contrainte> getContraintes() {
        return contraintes;
    }

    public void setContraintes(ArrayList<Contrainte> contraintes) {
        this.contraintes = contraintes;
    }

    /**
     * Redéfinition de la méthode componentResized de l'interface ComponentListener
     * @param e 
     */
    @Override
    public void componentResized(ComponentEvent e) {
        //on redefinit la taille du slitPane pour ne pas qu'il soit ni trop petit ni trop grand
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
