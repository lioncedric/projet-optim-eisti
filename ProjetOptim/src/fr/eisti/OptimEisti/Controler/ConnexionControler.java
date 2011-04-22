package fr.eisti.OptimEisti.Controler;

import fr.eisti.OptimEisti.Main;
import fr.eisti.OptimEisti.Model.BddProbleme;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import fr.eisti.OptimEisti.View.Identification;
import fr.eisti.OptimEisti.Model.BDDUtilisateur;
import fr.eisti.OptimEisti.View.Fenetre;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


/**
 * Listener qui écoute le bouton connexion de la fenetre identification
 * @author Razavet Maël, Klelifa Sarah, Gallet Meriadec, Lion C�dric
 * @version: 1.0
 */
public class ConnexionControler implements MouseListener,KeyListener {

    //Déclaration et initialisation de panel et de composants
    private JTextField tLogin = new JTextField();
    private JPasswordField tPassword = new JPasswordField();
    private Identification maFenetre;

    /**
     * Constructeur du listener
     * @param tLogin
     * @param tPassword
     * @param maFenetre
     */
    public ConnexionControler(JTextField tLogin, JPasswordField tPassword, Identification maFenetre) {
        this.tLogin = tLogin;
        this.tPassword = tPassword;
        this.maFenetre = maFenetre;
    }

    public void seConnecter(JTextField tLogin, JPasswordField tPassword, Identification maFenetre) throws IOException {
        //Declaration des variables
        String login;
        String mdp;
        //On recupere dans les textfields le text
        login = tLogin.getText();
        mdp = tPassword.getText();
        //Si le login et le mot de passe sont corrects : alors cela veut dire que la connexion est reussie !!!!!
        if (BDDUtilisateur.existeCompte(login, mdp)) {
            //on ferme la fenetre d'identification
            maFenetre.dispose();
            //ChoixUtilisateur fenChoix;
            BddProbleme.load(login);
            //Instanciation de la fenetre principale
            Main.fenetrePrincipale =new Fenetre();
            Main.fenetrePrincipale.setVisible(true);
            this.maFenetre.dispose();
            Main.getAccueil().dispose();

        } else if (login.isEmpty() || mdp.isEmpty()) {
            //la fenetre n'est pas au premier plan
            maFenetre.setAlwaysOnTop(false);
            //on ouvre un dialogue
            JOptionPane.showMessageDialog(null, "Connexion échouée! Vous n'avez pas rentré de données dans au moins un des champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
            //on remet la fenetre au premier plan
            maFenetre.setAlwaysOnTop(true);
        } //sinon
        else {
            //la fenetre n'est pas au premier plan
            maFenetre.setAlwaysOnTop(false);
            //on réinitialise les textfield
            tLogin.setText("");
            tPassword.setText("");
            //on ouvre un dialogue
            JOptionPane.showMessageDialog(null, "Connexion échouée! Mot de passe incorrect ou nom d'utilisateur incorrect!", "Erreur", JOptionPane.ERROR_MESSAGE);
            //on remet la fenetre au premier plan
            maFenetre.setAlwaysOnTop(true);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //Si on a cliqué sur le bouton connexion
        if (e.getX() >= 168 && e.getX() <= 287 && e.getY() >= 244 && e.getY() <= 270) {
            try {
                //Appel de la fonction seConnecter
                seConnecter(this.tLogin, this.tPassword, this.maFenetre);
            } catch (IOException ex) {
                Logger.getLogger(ConnexionControler.class.getName()).log(Level.SEVERE, null, ex);
            }
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

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        //on gere le cas ou il appuie sur la touche ENTRER
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            try {
                //Appel de la fonction seConnecter
                seConnecter(this.tLogin, this.tPassword, this.maFenetre);
            } catch (IOException ex) {
                Logger.getLogger(ConnexionControler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void keyReleased(KeyEvent e) {
    }
}
