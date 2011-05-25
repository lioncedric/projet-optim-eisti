package fr.eisti.optimEisti_RaLiGaKl.view.compte;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * JPanel qui permet de mettre une image en fond
 * @author Razavet Maël, Lion Cédric, Klelifa Sarah, Gallet Mériadec
 * @version 1.0
 */
public class JPanelFondIdentification extends JPanel {

    //Declaration des variables
    private static final long serialVersionUID = 1L;
    private Image image;
    private JTextField jtfNomUtilisateur;
    private JPasswordField jtfMdp;
    private JLabel lLogin;
    private JLabel lPassword;

    /**
     * Constructeur
     * @param image l'image de fond
     */
    public JPanelFondIdentification(Image image) {

        this.image = image;
        this.setLayout(null);

        this.jtfNomUtilisateur = new JTextField();
        this.jtfMdp = new JPasswordField();
        this.lLogin = new JLabel("Nom d'utilisateur : ");
        this.lPassword = new JLabel("Mot de passe : ");


        this.add(this.lLogin);
        this.add(this.jtfNomUtilisateur);
        this.add(this.lPassword);
        this.add(this.jtfMdp);
    }

    /**
     * Redefinition de la methode paintComponent
     */
    @Override
    public void paintComponent(Graphics g) {
        this.lLogin.setBounds(90, 80, 120, 30);
        this.jtfNomUtilisateur.setBounds(220, 80, 130, 30);
        this.lPassword.setBounds(90, 120, 120, 30);
        this.jtfMdp.setBounds(220, 120, 130, 30);
        g.drawImage(image, 0, 0, this);
    }

    /**
     * @return the jtfNomUtilisateur
     */
    public JTextField getJtfNomUtilisateur() {
        return jtfNomUtilisateur;
    }

    /**
     * @param jtfNomUtilisateur the jtfNomUtilisateur to set
     */
    public void setJtfNomUtilisateur(JTextField jtfNomUtilisateur) {
        this.jtfNomUtilisateur = jtfNomUtilisateur;
    }

    /**
     * @return the jtfMdp
     */
    public JPasswordField getJtfMdp() {
        return jtfMdp;
    }

    /**
     * @param jtfMdp the jtfMdp to set
     */
    public void setJtfMdp(JPasswordField jtfMdp) {
        this.jtfMdp = jtfMdp;
    }

    /**
     * @return the lLogin
     */
    public JLabel getlLogin() {
        return lLogin;
    }

    /**
     * @param lLogin the lLogin to set
     */
    public void setlLogin(JLabel lLogin) {
        this.lLogin = lLogin;
    }

    /**
     * @return the lPassword
     */
    public JLabel getlPassword() {
        return lPassword;
    }

    /**
     * @param lPassword the lPassword to set
     */
    public void setlPassword(JLabel lPassword) {
        this.lPassword = lPassword;
    }
}
