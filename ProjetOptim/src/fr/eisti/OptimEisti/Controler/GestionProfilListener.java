/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.eisti.OptimEisti.Controler;

import fr.eisti.OptimEisti.Main;
import fr.eisti.OptimEisti.Model.BDDUtilisateur;
import fr.eisti.OptimEisti.View.Compte.GestionProfil;
import fr.eisti.OptimEisti.View.Compte.JPanelFondNormal;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public class GestionProfilListener implements MouseListener {

    //Déclaration
    private GestionProfil maFenetre;

    /**
     * Constructeur initialisé
     * @param nomUtilisateur
     * @param mdp
     * @param mdp2
     * @param maFenetre
     */
    public GestionProfilListener(GestionProfil maFenetre) {
        this.maFenetre = maFenetre;
    }

    /**
     * permet de modifier une personne dans le identification.xml si tout est OK
     */
    public void modifierCompte() {
        if (bonFormatImage() && bonneTailleImage()) {
            //opération sur le dom
            BDDUtilisateur.modifierUtilisateur(this.maFenetre.getPanFond().getJtfNomUtilisateur().getText(),
                    this.maFenetre.getPanFond().getJtfMdp().getText(), this.maFenetre.getPanFond().getJtfAvatar().getText());

            BDDUtilisateur.setNomUtilisateur(this.maFenetre.getPanFond().getJtfNomUtilisateur().getText());
            Main.fenetrePrincipale.getPanProfil().setNomUtilisateur(new JLabel(BDDUtilisateur.getNomUtilisateur(),JLabel.CENTER));
            try {
                Image newAvatar = ImageIO.read(new File(this.maFenetre.getPanFond().getJtfAvatar().getText()));
                Main.fenetrePrincipale.getPanProfil().setAvatar(newAvatar);
                Main.fenetrePrincipale.getPanProfil().setPanImage(new JPanelFondNormal(newAvatar));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Main.fenetrePrincipale.getPanProfil().modification();
            Main.fenetrePrincipale.getPanProfil().revalidate();
            Main.fenetrePrincipale.getPanProfil().repaint();
            //on ferme la fenetre d'identification
            this.maFenetre.dispose();
            //on ouvre un dialogue
            JOptionPane jop = new JOptionPane();
            jop.showMessageDialog(null, "Modification réussie!", "Information", JOptionPane.INFORMATION_MESSAGE);
            this.maFenetre.dispose();
        } else if (!bonFormatImage()) {
            //la fenetre n'est pas au premier plan
            maFenetre.setAlwaysOnTop(false);
            //on ouvre un dialogue
            JOptionPane jop = new JOptionPane();
            jop.showMessageDialog(null, "Modification échouée! Vous n'avez pas selectionner une image valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            //on remet la fenetre au premier plan
            maFenetre.setAlwaysOnTop(true);
        } else if (!bonneTailleImage()) {
            //la fenetre n'est pas au premier plan
            maFenetre.setAlwaysOnTop(false);
            //on ouvre un dialogue
            JOptionPane jop = new JOptionPane();
            jop.showMessageDialog(null, "Modification échouée! Votre image doit avoir une taille maximale de 80x80 px", "Erreur", JOptionPane.ERROR_MESSAGE);
            //on remet la fenetre au premier plan
            maFenetre.setAlwaysOnTop(true);
        }//si au moins un des champs n'a pas été saisis
        else if (this.maFenetre.getPanFond().getJtfNomUtilisateur().getText().isEmpty() || this.maFenetre.getPanFond().getJtfMdp().getText().isEmpty()) {
            //la fenetre n'est pas au premier plan
            maFenetre.setAlwaysOnTop(false);
            //on ouvre un dialogue
            JOptionPane jop = new JOptionPane();
            jop.showMessageDialog(null, "Modification échouée! Vous n'avez pas rentré de données dans au moins un des champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
            //on remet la fenetre au premier plan
            maFenetre.setAlwaysOnTop(true);
        } //sinon si les champs saisie existe déjà dans la BDD
        else if (BDDUtilisateur.existeUtilisateur(this.maFenetre.getPanFond().getJtfNomUtilisateur().getText())) {
            //la fenetre n'est pas au premier plan
            maFenetre.setAlwaysOnTop(false);
            //on réinitialise tout
            this.maFenetre.getPanFond().raz();
            //on ouvre un dialogue
            JOptionPane jop = new JOptionPane();
            jop.showMessageDialog(null, "Modification échouée! Votre nom d'utilisateur est déjà utilisé!", "Erreur", JOptionPane.ERROR_MESSAGE);
            //on remet la fenetre au premier plan
            maFenetre.setAlwaysOnTop(true);
        } else if (!this.maFenetre.getPanFond().getJtfMdp().getText().equals(this.maFenetre.getPanFond().getJtfMdp2().getText())) {
            //la fenetre n'est pas au premier plan
            maFenetre.setAlwaysOnTop(false);
            //on réinitialise tout
            this.maFenetre.getPanFond().raz();
            //on ouvre un dialogue
            JOptionPane jop = new JOptionPane();
            jop.showMessageDialog(null, "Modification échouée! Vous n'avez pas saisi les mêmes mots de passe!", "Erreur", JOptionPane.ERROR_MESSAGE);
            //on remet la fenetre au premier plan
            maFenetre.setAlwaysOnTop(true);
        } //sinon si on a pas saisis tous les champs
        else {
            //la fenetre n'est pas au premier plan
            maFenetre.setAlwaysOnTop(false);
            //on réinitialise tout
            this.maFenetre.getPanFond().raz();
            //on ouvre un dialogue
            JOptionPane jop = new JOptionPane();
            jop.showMessageDialog(null, "Modification échouée! Vous n'avez pas saisis tous les champs!", "Erreur", JOptionPane.ERROR_MESSAGE);
            //on remet la fenetre au premier plan
            maFenetre.setAlwaysOnTop(true);
        }
    }

    public boolean bonFormatImage() {
        String nameFile = this.maFenetre.getPanFond().getJtfAvatar().getText();
        boolean avatarOK = (nameFile != null)
                && (nameFile.toLowerCase().endsWith(".jpg") || nameFile.toLowerCase().endsWith(".jpeg")
                || nameFile.toLowerCase().endsWith(".gif") || nameFile.toLowerCase().endsWith(".png"));
        return avatarOK;
    }

    public boolean bonneTailleImage() {
        String nameFile = this.maFenetre.getPanFond().getJtfAvatar().getText();

        try {
            Image avatar = ImageIO.read(new File(nameFile));
            int hauteur = avatar.getHeight(maFenetre);
            int largeur = avatar.getWidth(maFenetre);
            return hauteur <= 80 && largeur <= 80;
        } catch (IOException ex) {
            Logger.getLogger(CreerCompteListener.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //Si l'utilisateur a cliqué sur le bouton valider
        if (e.getX() >= 250 && e.getX() <= 401 && e.getY() >= 269 && e.getY() <= 296) {
            //Appel de la fonction qui créée un compte
            modifierCompte();
        } //Si l'utilisateur a cliqué sur le bouton remise à zéro
        else if (e.getX() >= 57 && e.getX() <= 208 && e.getY() >= 269 && e.getY() <= 296) {
            //on réinitialise tout
            this.maFenetre.getPanFond().raz();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
    }
}
