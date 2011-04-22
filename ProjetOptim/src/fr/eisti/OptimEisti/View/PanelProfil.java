/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.eisti.OptimEisti.View;

import fr.eisti.OptimEisti.Controler.PanelProfilListener;
import fr.eisti.OptimEisti.Model.BDDUtilisateur;
import fr.eisti.OptimEisti.Model.BddProbleme;
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
 *
 * @author Administrator
 */
public class PanelProfil extends JPanel {

    private JPanel panHaut;
    public static JPanel panImage;
    private Image avatar;
    private JPanel panDroite;
    public static JLabel nomUtilisateur;
    private JLabel nbProblemes;
    private JPanel panBouton;
    private JButton boutonProfil;

    public PanelProfil() {
        initialiserVariables();
        ajoutImageFond();
        traitement();
        mettreCouleur(Color.ORANGE);
    }

    public void initialiserVariables() {
        this.panHaut = new JPanel();
        this.panImage = new JPanel();
        this.panDroite = new JPanel();
        this.nomUtilisateur = new JLabel(BDDUtilisateur.getNomUtilisateur(), JLabel.CENTER);
        this.nbProblemes = new JLabel(String.valueOf(BddProbleme.nombreProblemes()) + " problemes", JLabel.CENTER);
        this.panBouton = new JPanel();
        this.boutonProfil = new JButton("Gérer mon profil");
    }

    public void ajoutImageFond() {
        try {
            avatar = ImageIO.read(new File(BDDUtilisateur.getImage()));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //on ajoute l'iamge au panel panFond
        panImage = new JPanelFondNormal(avatar);
    }

    public void traitement() {
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createTitledBorder(null, "Mon profil", 0, 0, new Font("Serif", Font.ITALIC, 14)));
        this.panHaut.setLayout(new BorderLayout());
        this.panDroite.setLayout(new GridLayout(2, 1));
        this.panBouton.add(this.boutonProfil);
        this.panDroite.add(this.nomUtilisateur);
        this.panDroite.add(this.nbProblemes);
        this.panImage.setPreferredSize(new Dimension(avatar.getHeight(this), avatar.getHeight(this)));
        this.panHaut.add(this.panImage, BorderLayout.WEST);
        this.panHaut.add(this.panDroite, BorderLayout.CENTER);
        this.add(this.panHaut, BorderLayout.CENTER);
        this.add(this.panBouton, BorderLayout.SOUTH);
        this.boutonProfil.addActionListener(new PanelProfilListener(this));
    }

    public void modification(){
        this.panHaut.removeAll();
        this.panDroite.removeAll();
        this.panDroite.add(this.nomUtilisateur);
        this.panDroite.add(this.nbProblemes);
        this.panImage.setPreferredSize(new Dimension(avatar.getHeight(this), avatar.getHeight(this)));
        this.panHaut.add(this.panImage, BorderLayout.WEST);
        this.panHaut.add(this.panDroite, BorderLayout.CENTER);
    }
    public void mettreCouleur(Color couleur) {
        this.panBouton.setOpaque(false);
        this.panDroite.setOpaque(false);
        this.panHaut.setOpaque(false);
        this.setBackground(couleur);
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
    
}
