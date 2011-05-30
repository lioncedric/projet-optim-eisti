package fr.eisti.optimEisti_RaLiGaKl.controler.compte;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import fr.eisti.optimEisti_RaLiGaKl.model.BDDUtilisateur;
import fr.eisti.optimEisti_RaLiGaKl.view.compte.CreerCompte;
import java.awt.Image;
import java.awt.Toolkit;

/**
 * Classe qui écoute le bouton ok de la classe CreerCompte.java
 * @author Razavet Maël, Lion Cédric, Klelifa Sarah, Gallet Mériadec
 * @version 1.0
 */
public class CreerCompteListener implements MouseListener {

    //Déclaration d'une fenêtre de type CreerCompte
    private CreerCompte maFenetre;

    /**
     * Constructeur de listener du compte
     * @param maFenetre la fenetre de creation de compte
     */
    public CreerCompteListener(CreerCompte maFenetre) {
        this.maFenetre = maFenetre;
    }

    /**
     * permet de rajouter une personne dans le identification.xml
     */
    public void creerCompte() {
        //si le nom d'utilisateur existe déjà dans la BDD
        if (BDDUtilisateur.existeUtilisateur(this.maFenetre.getPanFond().getJtfNomUtilisateur().getText())) {
            //la fenetre n'est pas au premier plan
            maFenetre.setAlwaysOnTop(false);
            //on ouvre un dialogue
            JOptionPane.showMessageDialog(null, "Création échouée! Votre nom d'utilisateur est déjà utilisé!", "Erreur", JOptionPane.ERROR_MESSAGE);
            //on remet la fenetre au premier plan
            maFenetre.setAlwaysOnTop(true);
        }//si l'image a la bonne extension (bonFormat) et la bonne taille et que le reste est bien rempli, alors on peut creer le compte
        else if (bonFormatImage() && bonneTailleImage() && nomEtMdpCorrects()) {
            //opération sur le dom
            BDDUtilisateur.ajouterUtilisateur(this.maFenetre.getPanFond().getJtfNomUtilisateur().getText(), this.maFenetre.getPanFond().getJtfMdp().getText(), this.maFenetre.getPanFond().getJtfAvatar().getText());
            //on ferme la fenetre d'identification
            this.maFenetre.dispose();
            //on ouvre un dialogue
            JOptionPane.showMessageDialog(null, "Création réussie!", "Information", JOptionPane.INFORMATION_MESSAGE);
            this.maFenetre.dispose();
        } //si on n'a pas sélectionné d'image
        else if(this.maFenetre.getPanFond().getJtfAvatar().getText().equals("")){
            //opération sur le dom
            BDDUtilisateur.ajouterUtilisateur(this.maFenetre.getPanFond().getJtfNomUtilisateur().getText(), this.maFenetre.getPanFond().getJtfMdp().getText(), "");
            //on ouvre un dialogue
            JOptionPane.showMessageDialog(null, "Création réussie!", "Information", JOptionPane.INFORMATION_MESSAGE);
            //on ferme cette fenetre
            this.maFenetre.dispose();
        }
        else if (!bonFormatImage()) {
            //la fenetre n'est pas au premier plan
            maFenetre.setAlwaysOnTop(false);
            //on ouvre un dialogue
            JOptionPane.showMessageDialog(null, "Création échouée! Vous n'avez pas selectionné une image valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
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
        } //sinon si l'utilisateur n'a pas saisi deux fois le même mot de passe
        else if (!this.maFenetre.getPanFond().getJtfMdp().getText().equals(this.maFenetre.getPanFond().getJtfMdp2().getText())) {
            //la fenetre n'est pas au premier plan
            maFenetre.setAlwaysOnTop(false);
            //on ouvre un dialogue
            JOptionPane.showMessageDialog(null, "Création échouée! Vous n'avez pas saisi les mêmes mots de passe!", "Erreur", JOptionPane.ERROR_MESSAGE);
            //on remet la fenetre au premier plan
            maFenetre.setAlwaysOnTop(true);
        } //sinon si on a pas saisi tous les champs
        else {
            //la fenetre n'est pas au premier plan
            maFenetre.setAlwaysOnTop(false);
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
            Image avatar =   Toolkit.getDefaultToolkit().getImage("/"+nameFile);
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
            creerCompte();
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
