/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.eisti.OptimEisti.View;

import fr.eisti.OptimEisti.Controler.FileChooserListener;
import fr.eisti.OptimEisti.Model.BDDUtilisateur;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author Administrator
 */
public class JPanelFondGestionProfil extends JPanel{
    //Declaration des variables
    private static final long serialVersionUID = 1L;
    private Image image;
    private JTextField jtfNomUtilisateur;
    private JPasswordField jtfMdp;
    private JPasswordField jtfMdp2;
    private JButton boutonAvatar;
    private JLabel lLogin;
    private JLabel lPassword;
    private JLabel lPassword2;
    private JLabel lAvatar;
    private JTextField jtfAvatar;

    /**
     * Constructeur
     * @param image l'image de fond
     */
    public JPanelFondGestionProfil(Image image) {
        //initialisation de l'image
        this.image = image;
        //pas de layout
        this.setLayout(null);
        //Initialisation des variables
        this.jtfNomUtilisateur = new JTextField(BDDUtilisateur.getNomUtilisateur());
        this.jtfMdp = new JPasswordField(BDDUtilisateur.getMotDePasse());
        this.jtfMdp2 = new JPasswordField(BDDUtilisateur.getMotDePasse());
        this.boutonAvatar = new JButton("H");
        this.lLogin = new JLabel("Nom d'utilisateur : ");
        this.lPassword = new JLabel("Mot de passe : ");
        this.lPassword2 = new JLabel("Retapez mot de passe : ");
        this.lAvatar = new JLabel("Avatar / image personnelle : ");
        this.jtfAvatar = new JTextField(BDDUtilisateur.getImage());
        //Ajout des différents composants sur le panel
        this.add(this.lLogin);
        this.add(this.jtfNomUtilisateur);
        this.add(this.lPassword);
        this.add(this.jtfMdp);
        this.add(this.lPassword2);
        this.add(this.jtfMdp2);
        this.add(this.lAvatar);
        this.add(this.jtfAvatar);
        this.add(this.boutonAvatar);
        this.boutonAvatar.addActionListener(new FileChooserListener(this));
        this.jtfAvatar.setEditable(false);
    }

    /**
     * Redefinition de la methode paintComponent
     */
    public void paintComponent(Graphics g) {
        //modification de la taille des composants
        this.lLogin.setBounds(100, 70, 120, 30);
        this.jtfNomUtilisateur.setBounds(220, 70, 130, 30);
        this.lPassword.setBounds(100, 110, 120, 30);
        this.jtfMdp.setBounds(220, 110, 130, 30);

        this.lPassword2.setBounds(70, 150, 150, 30);
        this.jtfMdp2.setBounds(220, 150, 130, 30);
        this.lAvatar.setBounds(30, 190, 170, 25);
        this.jtfAvatar.setBounds(200, 190, 170, 25);
        this.boutonAvatar.setBounds(390, 190, 50, 25);
        //on dessine l'image sur le panel
        g.drawImage(image, 0, 0, this);
    }

    public void raz() {
        this.jtfNomUtilisateur.setText("");
        this.jtfMdp.setText("");
        this.jtfMdp2.setText("");
        this.jtfAvatar.setText("");
    }
    
    public JButton getBoutonAvatar() {
        return boutonAvatar;
    }

    public Image getImage() {
        return image;
    }

    public JTextField getJtfAvatar() {
        return jtfAvatar;
    }

    public JPasswordField getJtfMdp() {
        return jtfMdp;
    }

    public JPasswordField getJtfMdp2() {
        return jtfMdp2;
    }

    public JTextField getJtfNomUtilisateur() {
        return jtfNomUtilisateur;
    }
    
}
