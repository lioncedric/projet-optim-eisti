package fr.eisti.OptimEisti.View;

import fr.eisti.OptimEisti.Controler.PanelProfilListener;
import fr.eisti.OptimEisti.Model.BDDUtilisateur;
import fr.eisti.OptimEisti.Model.BddProbleme;
import fr.eisti.OptimEisti.View.Compte.JPanelFondNormal;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Classe permettant de créer le panel de profil de l'utilisateur
 * @author Razavet Maël, Lion Cédric, Klelifa Sarah, Gallet Mériadec
 * @version: 1.0
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

    /**
     * Constructeur permettant de créer le panel profil de l'utilisateur
     */
    public PanelProfil() {
        initialiserVariables();
        ajoutImageFond();
        traitement();
        mettreCouleur(Color.WHITE);
    }

    /**
     * Procédure permettant d'initialiser toutes les variables déclarées précédemment
     */
    public void initialiserVariables() {
        this.panHaut = new JPanel();
        this.panImage = new JPanel();
        this.panDroite = new JPanel();
        this.nomUtilisateur = new JLabel(BDDUtilisateur.getNomUtilisateur(), JLabel.CENTER);
        this.nbProblemes = new JLabel(String.valueOf(BddProbleme.nombreProblemes()) + " probleme(s)", JLabel.CENTER);
        this.panBouton = new JPanel();
        this.boutonProfil = new JButton("Gérer mon profil");
    }

    /**
     * Procédure permettant d'ajouter une image de fond à un panel
     */
    public void ajoutImageFond() {
        try {
            avatar = ImageIO.read(new File(BDDUtilisateur.getImage()));
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement de l'image");
        }
        //on ajoute l'image au panel panImage
        panImage = new JPanelFondNormal(avatar);
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
        PanelProfil.panImage.setPreferredSize(new Dimension(avatar.getHeight(this), avatar.getHeight(this)));

        this.panDroite.setLayout(new GridLayout(2, 1));
        this.panBouton.add(this.boutonProfil);
        this.panDroite.add(PanelProfil.nomUtilisateur);
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
    public void mettreCouleur(Color couleur) {
        this.panBouton.setOpaque(false);
        this.panDroite.setOpaque(false);
        this.panHaut.setOpaque(false);
        this.setBackground(couleur);
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
}
