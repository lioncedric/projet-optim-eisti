package fr.eisti.optimEisti_RaLiGaKl.view.problemes;

import fr.eisti.optimEisti_RaLiGaKl.Main;
import fr.eisti.optimEisti_RaLiGaKl.controler.compte.PanelProfilListener;
import fr.eisti.optimEisti_RaLiGaKl.model.BDDUtilisateur;
import fr.eisti.optimEisti_RaLiGaKl.model.BddProbleme;
import fr.eisti.optimEisti_RaLiGaKl.view.compte.JPanelFondNormal;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Classe permettant de créer le panel de profil de l'utilisateur
 * @author Razavet Maël, Lion Cédric, Klelifa Sarah, Gallet Mériadec
 */
public class PanelProfil extends JPanel {

    private JPanel panHaut;             //declaration du panel du haut (image + panDroite)
    public static JPanel panImage;      //declaration du panel destiné à contenir uniquement l'image
    private JPanel panDroite;           //declaration du panel de droite (nom + nbProblemes)
    public static JLabel nomUtilisateur;//declaration du nom
    private JLabel nbProblemes;         //declaration du nombre de problèmes
    private JPanel panBouton;           //declaratin du panel contenant le seul bouton
    private JButton boutonProfil;       //declaration du bouton permettant de modifier son profil
    private Image avatar;               //declaration d'une image
    private Color couleurFond;
    private Color couleurTexte;

    /**
     * Constructeur permettant de créer le panel profil de l'utilisateur
     */
    public PanelProfil() {
        initialiserVariables();
        ajoutImageFond();
        traitement();
        mettreCouleurFond(this.couleurFond);
        mettreCouleurTexte(this.couleurTexte);
    }

    /**
     * Procédure permettant d'initialiser toutes les variables déclarées précédemment
     */
    public void initialiserVariables() {
        this.panHaut = new JPanel();
        panImage = new JPanel();
        this.panDroite = new JPanel();
        nomUtilisateur = new JLabel(BDDUtilisateur.getNomUtilisateur(), JLabel.CENTER);
        this.nbProblemes = new JLabel(String.valueOf(BddProbleme.nombreProblemes()) + " probleme(s)", JLabel.CENTER);
        this.panBouton = new JPanel();
        this.boutonProfil = new JButton("Gérer mon profil");
        this.couleurFond = Color.WHITE;
        this.couleurTexte = Color.BLACK;
    }

    /**
     * Procédure permettant d'ajouter une image de fond à un panel
     */
    public void ajoutImageFond() {
        try {
            avatar = ImageIO.read(new File(BDDUtilisateur.getImage()));
          
            //on ajoute l'image au panel panImage

        } catch (IOException ex) {
            
            avatar = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/tete.jpg"));

        }
        panImage = new JPanelFondNormal(avatar);

        //on met à jour tous les composants qui ont besoin d'être mis à jour et on réaffiche le tout
        this.modification();
        this.revalidate();
        this.repaint();
           panImage = new JPanelFondNormal(avatar);
        //on ferme la fenetre d'identification
    }

    /**
     * Procédure permettant de faire tous les traitements nécessaires
     */
    public void traitement() {
        this.setLayout(new BorderLayout());
        this.panHaut.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createTitledBorder(null, "Mon profil", 0, 0, new Font("Serif", Font.ITALIC, 14)));
        this.boutonProfil.addActionListener(new PanelProfilListener());

        //on adapte la hauteur du panel à la hauteur de l'image
        if (avatar != null) {
            PanelProfil.panImage.setPreferredSize(new Dimension(avatar.getHeight(this), avatar.getHeight(this)));
        }
        this.panDroite.setLayout(new GridLayout(2, 1));
        this.panBouton.add(this.boutonProfil);
        this.panDroite.add(nomUtilisateur);
        this.panDroite.add(this.nbProblemes);
        this.panHaut.add(PanelProfil.panImage, BorderLayout.WEST);
        this.panHaut.add(this.panDroite, BorderLayout.CENTER);
        this.add(this.panHaut, BorderLayout.CENTER);
        this.add(this.panBouton, BorderLayout.SOUTH);
    }

    /**
     * Procédure permettant de modifier l'image personnelle ainsi que le nom lors de la gestion du profil
     */
    public void modification() {
        //on commence par supprimer tout
        this.panHaut.removeAll();
        this.panDroite.removeAll();
        //on remet le nom et le nombre de problemes
        this.panDroite.add(PanelProfil.nomUtilisateur);
        this.panDroite.add(this.nbProblemes);
        //on réadapte la taille du panel à celle de l'image
        PanelProfil.panImage.setPreferredSize(new Dimension(avatar.getHeight(this), avatar.getHeight(this)));
        //on réajoute le tout
        this.panHaut.add(PanelProfil.panImage, BorderLayout.WEST);
        this.panHaut.add(this.panDroite, BorderLayout.CENTER);
    }

    /**
     * Procédure permetant de mettre le fond du panel en couleur
     * @param couleur : la couleur que l'on veut mettre en fond
     */
    public void mettreCouleurFond(Color couleur) {
        this.couleurFond = couleur;
        this.panBouton.setOpaque(false);
        this.panDroite.setOpaque(false);
        this.panHaut.setOpaque(false);
        this.setBackground(this.couleurFond);
    }

    public void mettreCouleurTexte(Color couleur) {
        this.couleurTexte = couleur;
        nomUtilisateur.setForeground(this.couleurTexte);
        this.nbProblemes.setForeground(this.couleurTexte);
    }

    /**
     * Procédure qui met à jour le nombre de problèmes d'un utilisateur
     */
    public void miseAJour() {
        this.nbProblemes.setText(String.valueOf(BddProbleme.nombreProblemes()) + " probleme(s)");
    }

    public static JLabel getNomUtilisateur() {
        return nomUtilisateur;
    }

    public static JPanel getPanImage() {
        return panImage;
    }

    public static void setNomUtilisateur(JLabel nomUtilisateur) {
        PanelProfil.nomUtilisateur = nomUtilisateur;
    }

    public static void setPanImage(JPanel panImage) {
        PanelProfil.panImage = panImage;
    }

    public void setAvatar(Image avatar) {
        this.avatar = avatar;
    }

    public JLabel getNbProblemes() {
        return nbProblemes;
    }

    public void setNbProblemes(JLabel nbProblemes) {
        this.nbProblemes = nbProblemes;
    }

    public Color getCouleurFond() {
        return couleurFond;
    }

    public Color getCouleurTexte() {
        return couleurTexte;
    }
}
