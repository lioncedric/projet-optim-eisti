package fr.eisti.optimEisti_RaLiGaKl.controler.compte;

import fr.eisti.optimEisti_RaLiGaKl.Main;
import fr.eisti.optimEisti_RaLiGaKl.model.BDDUtilisateur;
import fr.eisti.optimEisti_RaLiGaKl.view.compte.GestionProfil;
import fr.eisti.optimEisti_RaLiGaKl.view.compte.JPanelFondNormal;
import fr.eisti.optimEisti_RaLiGaKl.view.problemes.PanelProfil;
import java.awt.Image;
import java.awt.Toolkit;
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
 * Classe qui écoute les actions de la fenêtre de gestion du profil
 * @author Razavet Maël, Lion Cédric, Klelifa Sarah, Gallet Mériadec
 * @version 1.0
 */
public class GestionProfilListener implements MouseListener {

    //Déclaration d'une fenêtre de type GestionProfil
    private GestionProfil maFenetre;

    /**
     * Constructeur permettant d'initialiser notre fenêtre 
     * @param maFenetre : la fenetre de gestion de profil
     */
    public GestionProfilListener(GestionProfil maFenetre) {
        this.maFenetre = maFenetre;
    }

    /**
     * Procedure permettant de modifier un compte utilisateur si toutefois les champs demandés ont été remplis correctement
     */
    public void modifierCompte() {
        //si l'image a la bonne extension (bonFormat) et la bonne taille et que le reste est bien rempli, alors on peut creer le compte
        if (bonFormatImage() && bonneTailleImage() && nomEtMdpCorrects()) {
            //opération sur le dom
            BDDUtilisateur.modifierUtilisateur(this.maFenetre.getPanFond().getJtfNomUtilisateur().getText(),
                    this.maFenetre.getPanFond().getJtfMdp().getText(), this.maFenetre.getPanFond().getJtfAvatar().getText());

            //on modifie le nom d'utilisateur dans la base de données
            BDDUtilisateur.setNomUtilisateur(this.maFenetre.getPanFond().getJtfNomUtilisateur().getText());
            //on modifie le nom d'utilisateur dès à présent afin que les chagements soient visibles par l'utilisateur dans son panel profil
            PanelProfil.setNomUtilisateur(new JLabel(BDDUtilisateur.getNomUtilisateur(), JLabel.CENTER));

            //on fait de même pour son image personnelle en gérant le risque d'erreur

            //on récupère l'adresse de l'image à importer
            Image newAvatar;


            try {
                newAvatar = ImageIO.read(new File("/" + this.maFenetre.getPanFond().getJtfAvatar().getText()));

                //on ajoute l'image au panel panImage

            } catch (IOException ex) {
                newAvatar = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/tete.jpg"));

            }
            //on remplace l'ancienne image image par la nouvelle
            Main.fenetrePrincipale.getPanProfil().setAvatar(newAvatar);
            //on modifie dès à présent l'image afin que les chagements soient visibles par l'utilisateur dans son panel profil
            PanelProfil.setPanImage(new JPanelFondNormal(newAvatar));

            //on met à jour tous les composants qui ont besoin d'être mis à jour et on réaffiche le tout
            Main.fenetrePrincipale.getPanProfil().modification();
            Main.fenetrePrincipale.getPanProfil().revalidate();
            Main.fenetrePrincipale.getPanProfil().repaint();
            //on ferme la fenetre d'identification
            this.maFenetre.dispose();
            //on ouvre un dialogue
            JOptionPane.showMessageDialog(null, "Modification réussie!", "Information", JOptionPane.INFORMATION_MESSAGE);
            this.maFenetre.dispose();
        } //si l'image n'a pas le bon format
        else if (!bonFormatImage()) {
            //la fenetre n'est pas au premier plan
            maFenetre.setAlwaysOnTop(false);
            //on ouvre un dialogue
            JOptionPane.showMessageDialog(null, "Création échouée! Vous n'avez pas selectionner une image valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            //on remet la fenetre au premier plan
            maFenetre.setAlwaysOnTop(true);
        } //si l'image n'a pas une taille correcte
        else if (!bonneTailleImage()) {
            //la fenetre n'est pas au premier plan
            maFenetre.setAlwaysOnTop(false);
            //on ouvre un dialogue
            JOptionPane.showMessageDialog(null, "Création échouée! Votre image doit avoir une taille maximale de 80x80 px", "Erreur", JOptionPane.ERROR_MESSAGE);
            //on remet la fenetre au premier plan
            maFenetre.setAlwaysOnTop(true);
        }//si au moins un des champs n'a pas été saisis
        else if (this.maFenetre.getPanFond().getJtfNomUtilisateur().getText().isEmpty() || this.maFenetre.getPanFond().getJtfMdp().getText().isEmpty()) {
            //la fenetre n'est pas au premier plan
            maFenetre.setAlwaysOnTop(false);
            //on ouvre un dialogue
            JOptionPane.showMessageDialog(null, "Création échouée! Vous n'avez pas rentré de données dans au moins un des champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
            //on remet la fenetre au premier plan
            maFenetre.setAlwaysOnTop(true);
        } //sinon si le nom d'utilisateur existe déjà dans la BDD
        else if (BDDUtilisateur.existeUtilisateur(this.maFenetre.getPanFond().getJtfNomUtilisateur().getText())) {
            //la fenetre n'est pas au premier plan
            maFenetre.setAlwaysOnTop(false);
            //on réinitialise tout
            this.maFenetre.getPanFond().raz();
            //on ouvre un dialogue
            JOptionPane.showMessageDialog(null, "Création échouée! Votre nom d'utilisateur est déjà utilisé!", "Erreur", JOptionPane.ERROR_MESSAGE);
            //on remet la fenetre au premier plan
            maFenetre.setAlwaysOnTop(true);
        } //sinon si l'utilisateur n'a pas saisi deux fois le même mot de passe
        else if (!this.maFenetre.getPanFond().getJtfMdp().getText().equals(this.maFenetre.getPanFond().getJtfMdp2().getText())) {
            //la fenetre n'est pas au premier plan
            maFenetre.setAlwaysOnTop(false);
            //on réinitialise tout
            this.maFenetre.getPanFond().raz();
            //on ouvre un dialogue
            JOptionPane.showMessageDialog(null, "Création échouée! Vous n'avez pas saisi les mêmes mots de passe!", "Erreur", JOptionPane.ERROR_MESSAGE);
            //on remet la fenetre au premier plan
            maFenetre.setAlwaysOnTop(true);
        } //sinon si on a pas saisi tous les champs
        else {
            //la fenetre n'est pas au premier plan
            maFenetre.setAlwaysOnTop(false);
            //on réinitialise tout
            this.maFenetre.getPanFond().raz();
            //on ouvre un dialogue
            JOptionPane.showMessageDialog(null, "Création échouée! Vous n'avez pas saisi tous les champs!", "Erreur", JOptionPane.ERROR_MESSAGE);
            //on remet la fenetre au premier plan
            maFenetre.setAlwaysOnTop(true);
        }
    }

    /**
     * Fonction qui permet de savoir si l'utilisateur a bien rempli son nom et son mot de passe
     * @return un booleen qui permet de savoir si les champs sont bien remplis
     */
    public boolean nomEtMdpCorrects() {
        //on verifie que les champs sont OK
        return !this.maFenetre.getPanFond().getJtfNomUtilisateur().getText().isEmpty()
                && !this.maFenetre.getPanFond().getJtfMdp().getText().isEmpty()
                && this.maFenetre.getPanFond().getJtfMdp().getText().equals(this.maFenetre.getPanFond().getJtfMdp2().getText());
    }

    /**
     * Fonction qui permet de savoir si une image a un format valide
     * @return un booleen qui permet de savoir si l'image est valide
     */
    public boolean bonFormatImage() {
        //on recupere l'adresse de l'image
        String nameFile = this.maFenetre.getPanFond().getJtfAvatar().getText();
        return (nameFile != null)
                && (nameFile.toLowerCase().endsWith(".jpg") || nameFile.toLowerCase().endsWith(".jpeg")
                || nameFile.toLowerCase().endsWith(".gif") || nameFile.toLowerCase().endsWith(".png"));
    }

    /**
     * Fonction qui permet de savoir si une image a une taille valide
     * @return un booleen qui permet de savoir si l'image est valide
     */
    public boolean bonneTailleImage() {
        //on recupere l'adresse de l'image
        String nameFile = this.maFenetre.getPanFond().getJtfAvatar().getText();


        //on lit l'image afin d'accéder a ses propriétés
        Image avatar = Toolkit.getDefaultToolkit().getImage("/" + nameFile);
        //on recupere sa hauteur et sa largeur
        int hauteur = avatar.getHeight(maFenetre);
        int largeur = avatar.getWidth(maFenetre);
        //on retourne le booleen correspondant
        return hauteur <= 80 && largeur <= 80;

    }

    /**
     * Redéfinition de la méthode mouseClicked de l'interface MouseListener
     * @param e : l'evenement
     */
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
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }
}
