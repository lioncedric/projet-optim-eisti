package fr.eisti.optimEisti_RaLiGaKl.controler.problemes;

import fr.eisti.optimEisti_RaLiGaKl.model.BDDUtilisateur;
import fr.eisti.optimEisti_RaLiGaKl.model.BddProbleme;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.io.IOException;


import fr.eisti.optimEisti_RaLiGaKl.view.problemes.Fenetre;
import fr.eisti.optimEisti_RaLiGaKl.Main;
import fr.eisti.optimEisti_RaLiGaKl.view.compte.Preferences;
import java.awt.Desktop;
import java.awt.event.ComponentListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

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
        //si on a cliquer sur l'import xml
        if (e.getSource() == this.fenetre.getImportXml()) {
            //on ouvre un gestionnaire de fichier qui permet à l'utilisateur de choisir ou il veut enregistrer son importation
            try {
                JFileChooser fc = new JFileChooser();
                int returnVal = fc.showOpenDialog(this.fenetre.getDroite());
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    //on importe le probleme a l'emplacement choisit avec le nom choisit
                    BddProbleme.importerProbleme(file);
                }
            } catch (Exception ex) {
            }
            //si l'utilisateur clique sur deconnexion alors on le renvoit à la fenetre d'accueil du logiciel
        } else if (e.getSource() == this.fenetre.getDeconnexion()) {
            Main.accueil.setVisible(true);
            this.fenetre.dispose();
            //sinon si on a cliqué sur "supprimer compte"
        } else if (e.getSource() == this.fenetre.getSupprimerCompte()) {
            //nouvelle joptionPane qui demande confirmation de la suppression du compte
            int option = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer votre compte ?", "Suppression de compte", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            //si il confirme
            if (option == JOptionPane.OK_OPTION) {
                //appel de la fonction qui permet de supprimer un compte
                if (BDDUtilisateur.supprimerCompte(BDDUtilisateur.getNomUtilisateur())) {
                    //on ouvre un dialogue
                    JOptionPane.showMessageDialog(null, "Suppression réussie!", "Information", JOptionPane.INFORMATION_MESSAGE);
                    //on déconnecte la session
                    Main.accueil.setVisible(true);
                    //on ferme la fenetre
                    this.fenetre.dispose();
                } else {
                    //on ouvre un dialogue
                    JOptionPane.showMessageDialog(null, "Suppression échouée! ", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                //on ouvre un dialogue
                JOptionPane.showMessageDialog(null, "Création annulée! ", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
            //si l'utilisateur clique sur quitter alors on ferme le programme
        } else if (e.getSource() == this.fenetre.getQuitter()) {
            System.exit(0);
            //si l'utilisateur clique sur générer html alors on ouvre une page html ac le navigateur par defaut avec tous les problèmes de celui ci
        } else if (e.getSource() == this.fenetre.getAffResHtml()) {
            BddProbleme.html();
            //si l'utilisateur clique sur recharger alors on recharge le fichier de la bdd
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
        } else if (e.getSource() == this.fenetre.getPreferences()) {
            Preferences p = new Preferences();
            p.setVisible(true);
        } else if (e.getSource() == this.fenetre.getAideItem()) {   
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                InputStream resource = this.getClass().getResourceAsStream("/aide/aide.pdf");
                try {
                    File file = File.createTempFile("User_Guide", ".pdf");
                    file.deleteOnExit();
                    OutputStream out = new FileOutputStream(file);
                    try {
                        copyStreamToStream(resource, out);
                    } finally {
                        out.close();
                    }
                    desktop.open(file);
                } catch (IOException ex) {

                }
            }
        }
    }

    /**
     * Copy any input stream to output stream. Once the data will be copied
     * both streams will be closed.
     *
     * @param input  - InputStream to copy from
     * @param output - OutputStream to copy to
     * @throws IOException - io error in function
     * @throws OSSMultiException - double error in function
     */
    public static void copyStreamToStream(InputStream input, OutputStream output) throws IOException {InputStream is = null;
        OutputStream os = null;
        int ch;

        try {
            if (input instanceof BufferedInputStream) {
                is = input;
            } else {
                is = new BufferedInputStream(input);
            }
            if (output instanceof BufferedOutputStream) {
                os = output;
            } else {
                os = new BufferedOutputStream(output);
            }

            while ((ch = is.read()) != -1) {
                os.write(ch);
            }
            os.flush();
        } finally {
            IOException exec1 = null;
            IOException exec2 = null;
            try {
                // because this close can throw exception we do next close in
                // finally statement
                if (os != null) {
                    try {
                        os.close();
                    } catch (IOException exec) {
                        exec1 = exec;
                    }
                }
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException exec) {
                        exec2 = exec;
                    }
                }
            }
            if ((exec1 != null) && (exec2 != null)) {
                throw exec1;
            } else if (exec1 != null) {
                throw exec1;
            } else if (exec2 != null) {
                throw exec2;
            }
        }
    }

    /**
     * Redéfinition de la méthode componentResized de l'interface ComponentListener
     * @param e : l'evenement sur le composant
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
