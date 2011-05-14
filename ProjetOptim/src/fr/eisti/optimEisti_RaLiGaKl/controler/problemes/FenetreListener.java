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


import fr.eisti.optimEisti_RaLiGaKl.view.problemes.Fenetre;
import fr.eisti.optimEisti_RaLiGaKl.Main;
import fr.eisti.optimEisti_RaLiGaKl.model.FiltreSimple;
import fr.eisti.optimEisti_RaLiGaKl.model.Utilitaire;
import java.awt.event.ComponentListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import javax.swing.JFileChooser;

/**
 * Classe qui écoute les actions qui ont lieu sur la fenêtre principale
 * @author Razavet Maël, Lion Cédric, Klelifa Sarah, Gallet Mériadec
 * @version 1.0
 */
public class FenetreListener implements ActionListener, ComponentListener {

    private Fenetre fenetre;
  

    /**
     * Constructeur du listener de la fenetre
     * @param f1 : la fenetre où s'applique le listener
     */
    public FenetreListener(Fenetre f1) {
        this.fenetre = f1;
     
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
            //try {
                JFileChooser fc = new JFileChooser();
                fc.addChoosableFileFilter(new FiltreSimple("Fichier HTML", ".html"));
                fc.setAcceptAllFileFilterUsed(false);
                int returnVal = fc.showSaveDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    String nom = fc.getSelectedFile().getAbsolutePath();
                    String nom2 = new File(nom).getParent();
                    if (!nom.endsWith(".html")) {
                        nom = nom + ".html";
                    }
                try {
                    Utilitaire.creerHTML("bdd/" + BDDUtilisateur.getNomUtilisateur() + ".xml", "HTML/resultats.xsl", nom);
                } catch (Exception ex) {
                    Logger.getLogger(FenetreListener.class.getName()).log(Level.SEVERE, null, ex);
                }
                 //Utilitaire.creerHTML("bdd/" + BDDUtilisateur.getNomUtilisateur() + ".xml", "HTML/resultats.xsl", "HTML/"+BDDUtilisateur.getNomUtilisateur() + ".html");
                     //prendre en compte les html
                    String inputList[];
                    File inputDirectory = new File("HTML");
                    File outputDirectory = new File(nom2);
                    outputDirectory.mkdir();
                    inputList = inputDirectory.list();
                    for (int i = 0; i < inputList.length; i++) {
                        File inputFile = new File(inputDirectory, inputList[i]);
                        if (!inputFile.isDirectory()) {
                            try {
                                File outputFile = new File(outputDirectory, inputList[i]);
                                outputFile.createNewFile();

                                FileReader in = new FileReader(inputList[i]);
                                FileWriter out = new FileWriter(outputFile, true);
                                int c=0;

                                while ((c = in.read()) != -1) {
                                    out.write(c);
                                    System.out.println(c);
                                }

                                in.close();
                                out.close();
                            } catch (IOException e1) {
                                System.out.println(e1);
                            }
                        }

                    }
                }
               
            /*} catch (Exception ex) {
                Logger.getLogger(FenetreListener.class.getName()).log(Level.SEVERE, null, ex);
            }*/
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
