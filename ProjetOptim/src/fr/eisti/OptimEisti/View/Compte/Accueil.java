/**
 * Classe qui constitue la premiere fenetre affichee a l'ecran (la page d'accueil)
 */
package fr.eisti.OptimEisti.View.Compte;

import fr.eisti.OptimEisti.View.Compte.Identification;
import fr.eisti.OptimEisti.View.Compte.CreerCompte;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 * @author Administrator
 *
 */
public class Accueil extends JFrame implements MouseListener {

    private static final long serialVersionUID = 1L;
    //declarations des variables pour mettre en place une image de fond
    private Image fond;
    private JPanelFondNormal panFond;

    
    public Accueil() {
        //appel des methodes
        initialiserVariables();
        ajoutImageFond();
        traitement();
    }

    
    private void initialiserVariables() {
        this.setTitle("Programme OptimEisti");//on donne un titre a la fenetre
        this.setSize(960, 620);          //on redimenssione la fenetre en cours
        this.setLocationRelativeTo(null);//on centre la fenetre a l'ecran
        this.setResizable(false);        //on demande a ce que la fenetre ne puisse pas etre redimentionnee
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //permet de fermer correctement la fenetre
        this.addMouseListener(this);//on permet la gestion des clis sur la fenetre
    }

    private void traitement() {
        //on redefinit le panel de fond de la frame
        setContentPane(panFond);//on redefinit le panel
    }

    /**
     * Fonction qui permet d'ajouter une image en fond d'un panel
     */
    public void ajoutImageFond() {
        try {
            fond = ImageIO.read(new File("images/fond-optim3.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //on ajoute l'iamge au panel panFond
        panFond = new JPanelFondNormal(fond);
    }

    /**
     * Methode qui permet de gerer les clics et de lancer la partie du programme correspondante
     * @param e 
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getX() > 685 && e.getX() < 784 && e.getY() > 38 && e.getY() < 56) {
            Identification fenIdentifier;
            fenIdentifier = new Identification();
            fenIdentifier.setVisible(true);
        } else if (e.getX() > 805 && e.getX() < 945 && e.getY() > 38 && e.getY() < 56) {
            CreerCompte fenCreerCompte;
            fenCreerCompte = new CreerCompte();
            fenCreerCompte.setVisible(true);
        } else if (e.getX() > 933 && e.getX() < 952 && e.getY() > 586 && e.getY() < 605) {
            System.exit(EXIT_ON_CLOSE);
        } else {
            //do nothing
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

    public Accueil getAccueil() {
        return this;
    }
}
