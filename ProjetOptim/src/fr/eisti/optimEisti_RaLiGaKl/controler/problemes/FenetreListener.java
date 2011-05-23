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
import java.io.File;
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
        //sinon si on a cliqué sur "supprimer compte"
        } else if (e.getSource() == this.fenetre.getSupprimerCompte()) {
            //nouvelle joptionPane qui demande confirmation de la suppression du compte
            JOptionPane jop = new JOptionPane();
            int option = jop.showConfirmDialog(null, "Voulez-vous vraiment supprimer votre compte ?", "Suppression de compte", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
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
            }
            else {
                    //on ouvre un dialogue
                    JOptionPane.showMessageDialog(null, "Création annulée! ", "Erreur", JOptionPane.ERROR_MESSAGE);
            }

        } else if (e.getSource() == this.fenetre.getQuitter()) {
            System.exit(0);

        } else if (e.getSource() == this.fenetre.getAffResHtml()) {
            try {
                //declaration de variable
                String chemin;//stocke le chemin ou est stocké le fichier html
                chemin = BddProbleme.html();
                //Si la création du html a réussit
                if (chemin != null) {
                    //on ouvre un dialogue
                    //on affiche la page html à l'utilisateur
                    // On vérifie que la classe Desktop soit bien supportée :
                    if (Desktop.isDesktopSupported()) {
                        // On récupère l'instance du desktop :
                        Desktop desktop = Desktop.getDesktop();

                        // On vérifie que la fonction browse est bien supportée :
                        if (desktop.isSupported(Desktop.Action.OPEN)) {
                            try {
                                // Et on lance l'application associé au protocole :
                                desktop.open(new File(chemin));
                                System.out.println("ouverture réussit");
                            } catch (IOException efile) {
                                System.out.println("Problème de lecture du fichier");
                            }
                        }
                    }
                } else {
                    //on ouvre un dialogue
                    JOptionPane.showMessageDialog(null, "Création annulée! ", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                Logger.getLogger(FenetreListener.class.getName()).log(Level.SEVERE, null, ex);
                //on ouvre un dialogue
                JOptionPane.showMessageDialog(null, "Création échouée! ", "Erreur", JOptionPane.ERROR_MESSAGE);
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
        } else if (e.getSource() == this.fenetre.getPreferences()) {
            Preferences p = new Preferences();
            p.setVisible(true);
        }
    }

    /**
     * Redéfinition de la méthode componentResized de l'interface ComponentListener
     * @param e
     */
    @Override
    public void componentResized(
            ComponentEvent e) {
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
