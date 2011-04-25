package fr.eisti.OptimEisti.Controler;

import fr.eisti.OptimEisti.View.PanelProblemesUtilisateur;
import fr.eisti.OptimEisti.Main;
import fr.eisti.OptimEisti.Model.*;
import fr.eisti.OptimEisti.Model.Solution;
import fr.eisti.OptimEisti.View.ButtonTabComponent;
import fr.eisti.OptimEisti.View.JPanelProbleme;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

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
            int numero = ((JList) this.ppu.getList()).getSelectedIndex();
            //si il y en a vraiment un de sélectionné
            if (numero != -1) {
                //on recupere le probleme en question
                Probleme probleme;
                probleme = BddProbleme.getProbleme(numero);
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
            int numero = ((JList) this.ppu.getList()).getSelectedIndex();
            if (numero != -1) {
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
        } else if (e.getSource() == this.ppu.getBoutonSolution()) {
            Probleme p = ((JPanelProbleme) Main.fenetrePrincipale.getDroite().getSelectedComponent()).getProbleme();
            p.renseignerProbleme((JPanelProbleme) Main.fenetrePrincipale.getDroite().getSelectedComponent());
            //et on genere la solution par l'algorithme du simplexe
            Solution solution = new Solution(p.formaliserProbleme(), p.getCoeffVariables().size());

        } else if (e.getSource() == this.ppu.getBoutonNew()) {
            boolean nouveau = false;
            int i = 0;
            while (i < Main.fenetrePrincipale.getDroite().getTabCount() && !nouveau) {
                nouveau = Main.fenetrePrincipale.getDroite().getTitleAt(i).equals("Sans nom");
                i++;
            }
            if (!nouveau) {
                //on en crée un à la suite des autres et on lui attribue le titre "Sans nom"
                Main.fenetrePrincipale.getDroite().add("Sans nom", new JPanelProbleme(BddProbleme.nombreProblemes()));
                Main.fenetrePrincipale.getDroite().setTabComponentAt(Main.fenetrePrincipale.getDroite().getTabCount() - 1, new ButtonTabComponent(Main.fenetrePrincipale.getDroite()));
                Main.fenetrePrincipale.getDroite().setSelectedIndex(Main.fenetrePrincipale.getDroite().getTabCount() - 1);
            }
        }
    }

    /**
     * Procedure qui permet d'ouvrir un onglet et qui modifie la partie droite de la fenêtre en conséquence
     */
    public void ouvrir() {
        //on récupère le numéro du problème sélectrionné dans la liste
        int numero = ((JList) this.ppu.getList()).getSelectedIndex();

        //si il y en a un de sélectionné
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
