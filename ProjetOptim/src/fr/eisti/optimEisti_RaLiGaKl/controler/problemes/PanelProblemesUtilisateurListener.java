package fr.eisti.optimEisti_RaLiGaKl.controler.problemes;

import fr.eisti.optimEisti_RaLiGaKl.view.problemes.PanelProblemesUtilisateur;
import fr.eisti.optimEisti_RaLiGaKl.Main;
import fr.eisti.optimEisti_RaLiGaKl.model.*;
import fr.eisti.optimEisti_RaLiGaKl.view.problemes.PanelOngletProbleme;
import fr.eisti.optimEisti_RaLiGaKl.view.problemes.PanelProbleme;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * Classe qui écoute les actions du panel de problemes utilisateur
 * @author Razavet Maël, Lion Cédric, Klelifa Sarah, Gallet Mériadec
 * @version 1.0
 */
public class PanelProblemesUtilisateurListener implements ActionListener, MouseListener {

    //declaration d'un objet ayant le même type que celui du panel depuis lequel l'action a été créée
    PanelProblemesUtilisateur ppu;

    /**
     * Constructeur permettant d'initialiser notre variable
     * @param pPpu 
     */
    public PanelProblemesUtilisateurListener(PanelProblemesUtilisateur pPpu) {
        this.ppu = pPpu;
    }

    public static void SupprimerProbleme(int numero) {
        BddProbleme.supprimerProbleme(numero);
        int i = 0;
        //suprime l'onglet du probleme correspondant
        while (i < Main.fenetrePrincipale.getDroite().getTabCount()) {
            if (((PanelProbleme) Main.fenetrePrincipale.getDroite().getComponentAt(i)).getProbleme().getNumero() == numero) {
                Main.fenetrePrincipale.getDroite().remove(i);
            }
            i++;

        }
        //met a jour l'index des autres onglets
        for (int j = 0; j < Main.fenetrePrincipale.getDroite().getTabCount(); j++) {
            int numProbeCours = ((PanelProbleme) Main.fenetrePrincipale.getDroite().getComponentAt(j)).getProbleme().getNumero();
            if (numProbeCours > numero) {
                ((PanelProbleme) Main.fenetrePrincipale.getDroite().getComponentAt(j)).getProbleme().setNumero(numProbeCours - 1);

            }
        }
        Main.fenetrePrincipale.repaint();
        Main.fenetrePrincipale.getGauche().miseajour();
    }

    /**
     * Redéfinition de la méthode actionPerformed de l'interface ActionListener
     * @param e 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        //si l'action qui a été déclenchée est ouvrir
        if (e.getSource() == this.ppu.getjMenuItemOuvrir()) {
            //on appelle la fonction ouvrir
            ouvrir();
        } //si l'action est supprimer
        else if (e.getSource() == this.ppu.getjMenuItemSuppr()) {
            //on recupere le numero de l'item qui a été sélectionné dans la liste
            int numero;
            Probleme probleme;
            probleme = (Probleme) this.ppu.getList().getSelectedValue();
            numero = probleme.getNumero();
            //si il y en a vraiment un de sélectionné
            if (numero >= 0) {

                //on vérifie que l'utilisateur ne s'est pas trompé
                String message = "Etes vous sur de vouloir supprimer le probleme '" + probleme.getTitre();
                int reponse = JOptionPane.showConfirmDialog(null, message, "Suppression du probleme", JOptionPane.YES_NO_OPTION);
                //si il veut vraiment supprimer le probleme...
                if (reponse == JOptionPane.YES_OPTION) {
                    //on le fait...
                    SupprimerProbleme(numero);
                    //et on met à jour le panel de profil qui compte le nombre de problemes de l'utilisateur
                    Main.fenetrePrincipale.getPanProfil().miseAJour();
                }

            }
        } else if (e.getSource() == this.ppu.getjMenuItemExporter()) {

            //On récupère le numéro
            int numero;
            Probleme probleme;
            probleme = (Probleme) this.ppu.getList().getSelectedValue();
            numero = probleme.getNumero();
            if (numero >= 0) {
                try {
                    JFileChooser fc = new JFileChooser();
                    fc.addChoosableFileFilter(new FiltreSimple("Fichier Excel", ".csv"));
                    fc.addChoosableFileFilter(new FiltreSimple("Fichier Scilab", ".sci"));
                    fc.setAcceptAllFileFilterUsed(false);
                    int returnVal = fc.showSaveDialog(null);
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        String nom = fc.getSelectedFile().getAbsolutePath();
                        String type = fc.getFileFilter().getDescription();
                        if (type.equals("Fichier Excel")) {
                            if (!nom.endsWith(".csv")) {
                                nom = nom + ".csv";
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
        } else if (e.getSource() == this.ppu.getBoutonHtml()) {
            try {
               //Si la création du html a réussit
                if(Utilitaire.html()){
                     //on ouvre un dialogue
                    JOptionPane.showMessageDialog(null, "Création réussie!", "Information", JOptionPane.INFORMATION_MESSAGE);
                }
                else{
                    //on ouvre un dialogue
                    JOptionPane.showMessageDialog(null, "Création annulée! ", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                Logger.getLogger(FenetreListener.class.getName()).log(Level.SEVERE, null, ex);
                 //on ouvre un dialogue
                JOptionPane.showMessageDialog(null, "Création échouée! ", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == this.ppu.getBoutonNew()) {
            boolean nouveau = false;
            int i = 0;
            while (i < Main.fenetrePrincipale.getDroite().getTabCount() && !nouveau) {
                nouveau = Main.fenetrePrincipale.getDroite().getTitleAt(i).equals("Sans nom");
                i++;
            }
            if (!nouveau) {
                //on en crée un à la suite des autres et on lui attribue le titre "Sans nom"
                Main.fenetrePrincipale.getDroite().add("Sans nom", new PanelProbleme());
                Main.fenetrePrincipale.getDroite().setTabComponentAt(Main.fenetrePrincipale.getDroite().getTabCount() - 1, new PanelOngletProbleme(Main.fenetrePrincipale.getDroite()));
                Main.fenetrePrincipale.getDroite().setSelectedIndex(Main.fenetrePrincipale.getDroite().getTabCount() - 1);
            }
        }
    }

    /**
     * Procedure qui permet d'ouvrir un onglet et qui modifie la partie droite de la fenêtre en conséquence
     */
    public void ouvrir() {
        //on récupère le numéro du problème sélectrionné dans la liste
        int numero;
        Probleme probleme;
        probleme = (Probleme) this.ppu.getList().getSelectedValue();
        numero = probleme.getNumero();
        //si il y en a un de sélectionné
        if (numero >= 0) {
            boolean present = false;
            for (int i = 0; i < Main.fenetrePrincipale.getDroite().getTabCount(); i++) {
                present = present || ((PanelProbleme) Main.fenetrePrincipale.getDroite().getComponentAt(i)).getProbleme().getNumero() == numero;
            }
            if (!present) {

                Main.fenetrePrincipale.getDroite().add(probleme.getTitre(), new PanelProbleme(probleme));
                Main.fenetrePrincipale.getDroite().setTabComponentAt(Main.fenetrePrincipale.getDroite().getTabCount() - 1, new PanelOngletProbleme(Main.fenetrePrincipale.getDroite()));
                Main.fenetrePrincipale.getDroite().setSelectedIndex(Main.fenetrePrincipale.getDroite().getTabCount() - 1);
            } else {
                boolean trouve = false;
                int i = 0;
                while (i < Main.fenetrePrincipale.getDroite().getTabCount() && !trouve) {
                    if (((PanelProbleme) Main.fenetrePrincipale.getDroite().getComponentAt(i)).getProbleme().getNumero() == numero) {
                        trouve = true;
                        Main.fenetrePrincipale.getDroite().setSelectedIndex(i);
                    }
                    i++;
                }
            }
            SaveListener.estmodifié();
        }
    }

    /**
     * Redéfinition de la fonction mouseClicked de l'interface MouseListener
     * @param e 
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        //si l'utilisateur a fait un double clic gauche
        if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
            //on ouvre le probleme
            ouvrir();
        } //si il fait un clic droit
        else if (e.getButton() == MouseEvent.BUTTON3) {
            //on affiche le menu déroulant avec les diférentes actions qui le composent
            this.ppu.getJpopup().show(e.getComponent(), e.getX(), e.getY());
            //et on sélecionne la ligne correspondant à l'endroit où il a fait le clic droit
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
