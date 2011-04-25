/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.eisti.OptimEisti.Controler;

import fr.eisti.OptimEisti.View.PanelProblemesUtilisateur;
import fr.eisti.OptimEisti.Main;
import fr.eisti.OptimEisti.Model.*;
import fr.eisti.OptimEisti.Model.Solution;
import fr.eisti.OptimEisti.View.contraintes.ButtonTabComponent;
import fr.eisti.OptimEisti.View.JPanelProbleme;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Administrator
 */
public class PanelProblemesUtilisateurListener implements ActionListener, MouseListener {

    PanelProblemesUtilisateur ppu;

    public PanelProblemesUtilisateurListener(PanelProblemesUtilisateur pPpu) {
        this.ppu = pPpu;
    }

    public void SupprimerProbleme(int numero) {
        BddProbleme.supprimerProbleme(numero);
        boolean remove = false;
        int numProbRemove = 100;
        int i = 0;
        while (i < Main.fenetrePrincipale.getDroite().getTabCount() && !remove) {

            if (((JPanelProbleme) Main.fenetrePrincipale.getDroite().getComponentAt(i)).getProbleme().getNumero() == numero) {

                remove = true;
                numProbRemove = i;
                Main.fenetrePrincipale.getDroite().remove(numProbRemove);

            }
            i++;

        }
        if (remove) {

            for (int j = 0; j < Main.fenetrePrincipale.getDroite().getTabCount(); j++) {
                int numProbeCours = ((JPanelProbleme) Main.fenetrePrincipale.getDroite().getComponentAt(j)).getProbleme().getNumero();
                if (numProbeCours > numProbRemove) {
                    ((JPanelProbleme) Main.fenetrePrincipale.getDroite().getComponentAt(j)).getProbleme().setNumero(numProbeCours - 1);

                }

            }
        }
        Main.fenetrePrincipale.repaint();
        Main.fenetrePrincipale.getGauche().miseajour();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.ppu.getjMenuItemOuvrir()) {
            ouvrir();
        } else if (e.getSource() == this.ppu.getjMenuItemSuppr()) {
            int numero = ((JList) this.ppu.getList()).getSelectedIndex();
            if (numero != -1) {
                Probleme probleme;
                probleme = BddProbleme.getProbleme(numero);
                String message = "Etes vous sur de vouloir supprimer le probleme '" + probleme.getTitre();
                int reponse = JOptionPane.showConfirmDialog(null, message, "Suppression du probleme", JOptionPane.YES_NO_OPTION);
                if (reponse == JOptionPane.YES_OPTION) {
                    SupprimerProbleme(numero);
                    Main.fenetrePrincipale.getPanProfil().miseAJour();
                }

            }
        } else if (e.getSource() == this.ppu.getjMenuItemExporter()) {

            //On récupère le numéro
            int numero = ((JList) this.ppu.getList()).getSelectedIndex();
            if (numero != -1) {
                try {
                    JFileChooser fc = new JFileChooser();
                    fc.addChoosableFileFilter(new FiltreSimple("Fichier Excel", ".xls"));
                    fc.addChoosableFileFilter(new FiltreSimple("Fichier Scilab", ".sci"));
                    fc.setAcceptAllFileFilterUsed(false);
                    int returnVal = fc.showSaveDialog(null);
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        String nom = fc.getSelectedFile().getAbsolutePath();
                        String type = fc.getFileFilter().getDescription();
                        if (type.equals("Fichier Excel")) {
                            if (!nom.endsWith(".xls")) {
                                nom = nom + ".xls";
                            }
                            BddProbleme.exporterExcel(BddProbleme.getProbleme(numero), nom);
                        } else if (type.equals("Fichier Scilab")) {
                            if (!nom.endsWith(".sci")) {
                                nom = nom + ".sci";
                            }
                            BddProbleme.exporterScilab(BddProbleme.getProbleme(numero), nom);
                        }
                    }
                } catch (Exception ex) {
                }
            }
        } else if (e.getSource() == this.ppu.getBoutonSolution()) {
            Probleme p = ((JPanelProbleme) Main.fenetrePrincipale.getDroite().getSelectedComponent()).getProbleme();
            p.renseignerProbleme((JPanelProbleme) Main.fenetrePrincipale.getDroite().getSelectedComponent());
            Solution solution = new Solution(p.formaliserProbleme(), p.getCoeffVariables().size());

        } else if (e.getSource() == this.ppu.getBoutonEnregistrer()) {
            boolean titreOK;
            titreOK = !(((JPanelProbleme) Main.fenetrePrincipale.getDroite().getSelectedComponent()).getJtfTitre().getText().equals(""));
            boolean descriptionOK;
            descriptionOK = !(((JPanelProbleme) Main.fenetrePrincipale.getDroite().getSelectedComponent()).getTextfield().getText().equals(""));
            boolean ligneRempli;
            ligneRempli = ((JPanelProbleme) Main.fenetrePrincipale.getDroite().getSelectedComponent()).getPanTableau().ligneRempli();
            boolean variablesOK = true;
            for (int i = 0; i < ((JPanelProbleme) Main.fenetrePrincipale.getDroite().getSelectedComponent()).getSlide().getValue(); i++) {
                JTextField jtf = (JTextField) (((JPanelProbleme) Main.fenetrePrincipale.getDroite().getSelectedComponent()).getPanDonnees().getComponent(3 * i + 1));
                variablesOK = variablesOK && !(jtf.getText().equals("")) && !(jtf.getText().equals("0"));
                try {
                    double valeur = Double.valueOf(jtf.getText());
                } catch (NumberFormatException nfe) {
                    variablesOK = false;
                }
            }
            if (titreOK && descriptionOK && variablesOK && ligneRempli) {
                Probleme p = ((JPanelProbleme) Main.fenetrePrincipale.getDroite().getSelectedComponent()).getProbleme();
                p.renseignerProbleme((JPanelProbleme) Main.fenetrePrincipale.getDroite().getSelectedComponent());
                if (p.getNumero() < BddProbleme.nombreProblemes()) {
                    int numero = p.getNumero();
                    BddProbleme.supprimerProbleme(numero);
                    for (int i = 0; i < Main.fenetrePrincipale.getDroite().getTabCount(); i++) {
                        int numProbeCours = ((JPanelProbleme) Main.fenetrePrincipale.getDroite().getComponentAt(i)).getProbleme().getNumero();
                        if (numProbeCours > numero) {
                            ((JPanelProbleme) Main.fenetrePrincipale.getDroite().getComponentAt(i)).getProbleme().setNumero(numProbeCours - 1);

                        }

                    }
                }
                p.setNumero(BddProbleme.nombreProblemes());
                BddProbleme.addProbleme(p);
                int currentIndex = Main.fenetrePrincipale.getDroite().getSelectedIndex();
                Main.fenetrePrincipale.getDroite().setTitleAt(currentIndex, p.getTitre());
                Main.fenetrePrincipale.getPanProfil().miseAJour();
                Main.fenetrePrincipale.getGauche().miseajour();

            } else {
                JOptionPane.showMessageDialog(null, "Vous n'avez pas bien rempli tous les parametres", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == this.ppu.getBoutonNew()) {
            Main.fenetrePrincipale.getDroite().add("Sans nom", new JPanelProbleme(BddProbleme.nombreProblemes()));
            Main.fenetrePrincipale.getDroite().setTabComponentAt(Main.fenetrePrincipale.getDroite().getTabCount() - 1, new ButtonTabComponent(Main.fenetrePrincipale.getDroite()));
            Main.fenetrePrincipale.getDroite().setSelectedIndex(Main.fenetrePrincipale.getDroite().getTabCount() - 1);
        }
    }

    public void ouvrir() {
        int numero = ((JList) this.ppu.getList()).getSelectedIndex();
        if (numero >= 0) {

            boolean present = false;
            for (int i = 0; i < Main.fenetrePrincipale.getDroite().getTabCount(); i++) {
                present = present || ((JPanelProbleme) Main.fenetrePrincipale.getDroite().getComponentAt(i)).getProbleme().getNumero() == numero;
            }
            if (!present) {
                Probleme probleme;
                probleme = BddProbleme.getProbleme(numero);
                probleme.setNumero(numero);
                Main.fenetrePrincipale.getDroite().add(probleme.getTitre(), new JPanelProbleme(probleme));
                Main.fenetrePrincipale.getDroite().setTabComponentAt(Main.fenetrePrincipale.getDroite().getTabCount() - 1, new ButtonTabComponent(Main.fenetrePrincipale.getDroite()));
                Main.fenetrePrincipale.getDroite().setSelectedIndex(Main.fenetrePrincipale.getDroite().getTabCount() - 1);
            } else {
                boolean trouve = false;
                int i = 0;
                String s = new String((String) this.ppu.getList().getSelectedValue());
                while (i < Main.fenetrePrincipale.getDroite().getTabCount() && !trouve) {
                    if (Main.fenetrePrincipale.getDroite().getTitleAt(i).equals(s)) {
                        trouve = true;
                        Main.fenetrePrincipale.getDroite().setSelectedIndex(i);
                    }
                    i++;
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //JList theList = (JList) e.getSource();
        if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
            //int index = theList.locationToIndex(e.getPoint());
            ouvrir();
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            this.ppu.getJpopup().show(e.getComponent(), e.getX(), e.getY());
            this.ppu.getList().setSelectedIndex(this.ppu.getList().locationToIndex(e.getPoint()));
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
