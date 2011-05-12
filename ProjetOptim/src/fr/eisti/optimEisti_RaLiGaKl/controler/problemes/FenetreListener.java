package fr.eisti.optimEisti_RaLiGaKl.controler.problemes;

import fr.eisti.optimEisti_RaLiGaKl.model.BDDUtilisateur;
import fr.eisti.optimEisti_RaLiGaKl.model.BddProbleme;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import fr.eisti.optimEisti_RaLiGaKl.view.problemes.Fenetre;
import fr.eisti.optimEisti_RaLiGaKl.Main;
import fr.eisti.optimEisti_RaLiGaKl.model.Contrainte;
import fr.eisti.optimEisti_RaLiGaKl.model.FiltreSimple;
import fr.eisti.optimEisti_RaLiGaKl.model.Utilitaire;
import fr.eisti.optimEisti_RaLiGaKl.view.problemes.PanelProbleme;
import java.awt.event.ComponentListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import javax.swing.JFileChooser;

/**
 * Classe qui écoute les actions qui ont lieu sur la fenêtre principale
 * @author Razavet Maël, Lion Cédric, Klelifa Sarah, Gallet Mériadec
 * @version 1.0
 */
public class FenetreListener implements ChangeListener, ActionListener, ComponentListener {

    private Fenetre fenetre;
    private ArrayList<String> valeursTextFields;
    private ArrayList<Contrainte> contraintes;

    /**
     * Constructeur du listener de la fenetre
     * @param f1 : la fenetre où s'applique le listener
     */
    public FenetreListener(Fenetre f1) {
        this.fenetre = f1;
        valeursTextFields = new ArrayList();
        contraintes = new ArrayList<Contrainte>();
    }

    /**
     * Constructeur du listener de la fenetre sans parametre
     */
    public FenetreListener() {
        this(null);
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

    @Override
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


        } else if (e.getSource() == this.fenetre.getAffResHtml()) {
            try {
                /*JFileChooser fc = new JFileChooser();
                fc.addChoosableFileFilter(new FiltreSimple("Fichier HTML", ".html"));
                fc.setAcceptAllFileFilterUsed(false);
                int returnVal = fc.showSaveDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    String nom = fc.getSelectedFile().getAbsolutePath();
                    String nom2 = new File(nom).getParent();
                    if (!nom.endsWith(".html")) {
                        nom = nom + ".html";
                    }*/
                    // Utilitaire.creerHTML("bdd/" + BDDUtilisateur.getNomUtilisateur() + ".xml", "HTML/resultats.xsl", nom);
                 Utilitaire.creerHTML("bdd/" + BDDUtilisateur.getNomUtilisateur() + ".xml", "HTML/resultats.xsl", BDDUtilisateur.getNomUtilisateur() + ".html");
                     //prendre en compte les html
                    /*String inputList[];
                    File inputDirectory = new File("HTML");
                    File outputDirectory = new File(nom2+"dossier");
                    outputDirectory.mkdir();
                    inputList = inputDirectory.list();
                    for (int i = 0; i < inputList.length; i++) {
                        File inputFile = new File(inputDirectory, inputList[i]);
                        if (!inputFile.isDirectory()) {
                            try {
                                File outputFile = new File(outputDirectory, inputList[i]);
                                outputFile.createNewFile();

                                FileReader in = new FileReader(inputList[i]);
                                FileWriter out = new FileWriter(outputFile);
                                int c;

                                while ((c = in.read()) != -1) {
                                    out.write(c);
                                }

                                in.close();
                                out.close();
                            } catch (IOException e1) {
                                System.out.println(e1);
                            }
                        }

                    }*/
                //}
               
            } catch (Exception ex) {
                Logger.getLogger(FenetreListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (e.getSource() == this.fenetre.getRecharger()) {
            try {
                BddProbleme.load(BDDUtilisateur.getNomUtilisateur(), BDDUtilisateur.getImage());
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
            this.fenetre.setExtendedState(Fenetre.MAXIMIZED_BOTH);
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
